package com.raoworks.core.scheduler

import akka.actor.ActorSystem
import com.raoworks.core.config.TaskSchedulerConfig
import com.raoworks.core.scheduler.task.{TaskItem, TaskResult}

import scala.concurrent.Future
import scala.util.{Failure, Success}

/**
  * Default Scheduler
  * - used as scheduler engine to schedule and execute task
  */
class DefaultScheduler(schedulerResource:SchedulerResource, schedulerConfig:TaskSchedulerConfig)(implicit actorSystem:ActorSystem)
  extends TaskScheduler
    with TaskExecutor {

  import actorSystem.dispatcher

  def boot() = {
    executeTasks()
  }


  /** @inheritdoc */
  override def schedule(taskItem: TaskItem): Unit = {
    schedulerResource.addTaskToStore(taskItem)
  }

  /** @inheritdoc */
  override def executeTasks() = {
    while(true) {

      //get next batch of task to execute
      val taskResultFutr = schedulerResource.getNextTasks()
        .flatMap( tasks =>Future.sequence(tasks.map(task => task.execute(actorSystem))))

      taskResultFutr.onComplete {
        case Success(taskResults:List[TaskResult]) => {
          if(!taskResults.isEmpty) {
            taskResults.foreach(result => {
              println("============")
              println(s"Task Completed: ${result.taskKey.name}\nsuccess status : ${result.success}\nmessage: ${result.message}")
              println("============")
            })
          }
        }
        case Failure(ex) => println(s"Failed to execute Task message:${ex.getMessage} \n stackTrace:${ex.getStackTrace}")
      }
      Thread.sleep(1000)
    }
  }
}

object DefaultScheduler {
  def apply(schedulerResource: SchedulerResource,schedulerConfig:TaskSchedulerConfig)(implicit actorSystem:ActorSystem) = {
    new DefaultScheduler(schedulerResource, schedulerConfig)
  }
}