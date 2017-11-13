package com.raoworks.core.scheduler.store

import java.util

import akka.actor.ActorSystem
import com.raoworks.core.scheduler.task.{RecurringTaskItem, TaskItem, TaskKey}
import org.joda.time.DateTime

import scala.concurrent.Future

/**
  * InMemory task store
  */
class TaskInMemoryStore(implicit actorSystem: ActorSystem) extends TaskStore {

  import actorSystem.dispatcher

  // sorted map based on task start time <TaskKey - <TaskItem>>
  val taskMap = new util.TreeMap[TaskKey,TaskItem]()

  /**
    * @inheritdoc
    */
  override def storeTask(taskItem: TaskItem): Unit = {
    println(s"\nScheduling Task = ${taskItem.taskName}\nstart-time: ${taskItem.startTime}\npriority: ${taskItem.priority}")
    val taskKey = TaskKey(name = taskItem.taskName, startTime = taskItem.startTime, taskPriority = taskItem.priority)
    taskMap.put(taskKey, taskItem)
  }

  /**
    * @inheritdoc
    */
  override def getTask(taskKey: TaskKey): Future[Option[TaskItem]] = {
    Future.successful{
      val task= taskMap.get(taskKey)
      Some(task)
    }
  }

  /**
    * @inheritdoc
    */
  override def getNextTasks(time:DateTime): Future[List[TaskItem]] = {
    import scala.util.control.Breaks._

    val itr = taskMap.keySet().iterator()
    val result = new scala.collection.mutable.ArrayBuffer[Future[Option[TaskItem]]]()

    breakable {
      while (itr.hasNext) {
        val item = itr.next()

        // add to result if task start time falls in bucket
        if(item.startTime.isBefore(time)) {
          val taskItemFtr = getTask(item)
          result += taskItemFtr
          itr.remove()
        }
        else {
          break
        }
      }
    }

    return Future.sequence(result).map(l => {
      val taskToBeExecuted = l.toList.flatten
      taskToBeExecuted.foreach(item => updateInternalStore(item, TaskKey(item.startTime, item.taskName)))
      taskToBeExecuted
    })
  }

  // --- private methods ----

  /*
    Method to add recurring task item to store
   */
  def updateInternalStore(taskItem: TaskItem, taskItemKey: TaskKey) = {

    // In case of recurring item calculate the next run time and reinsert to sorted tree map
    if (taskItem.isInstanceOf[RecurringTaskItem]) {
      val oldItem = taskItem.asInstanceOf[RecurringTaskItem]
      val nextRunTime = taskItem.startTime.plusSeconds(taskItem.interval.toSeconds.toInt)
      val newRecurringTaskItem = oldItem.copy(startTime = nextRunTime)
      //println(s"Re-inserting recuring task = ${newRecurringTaskItem.taskName} with new start time = ${newRecurringTaskItem.startTime.toString()}")
      storeTask(newRecurringTaskItem)
    }
  }

}

object TaskInMemoryStore {
  def apply()(implicit actorSystem: ActorSystem): TaskInMemoryStore = new TaskInMemoryStore
}