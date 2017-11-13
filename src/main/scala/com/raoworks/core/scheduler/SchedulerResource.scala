package com.raoworks.core.scheduler

import com.raoworks.core.scheduler.store.TaskStore
import com.raoworks.core.scheduler.task.TaskItem
import org.joda.time.DateTime

/**
  * Schedule resource provider
  * - gives access to underlying task store
  *
  */
class SchedulerResource(taskStore:TaskStore) {

  /**
    * Method to add task to task-store
    * @param taskItem
    */
  def addTaskToStore(taskItem: TaskItem) = {
    taskStore.storeTask(taskItem)
  }

  /**
    * Method to get next batch of tasks to be executed
    * @return
    */
  def getNextTasks() = {
    val curDateTime = new DateTime(System.currentTimeMillis())
    taskStore.getNextTasks(curDateTime)
  }
}
