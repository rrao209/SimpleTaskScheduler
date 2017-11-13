package com.raoworks.core.scheduler.store

import com.raoworks.core.scheduler.task.{TaskItem, TaskKey}
import org.joda.time.DateTime

import scala.concurrent.Future

/**
  * Task store interface
  */
trait TaskStore {

  /**
    * Method to store task details
    *
    * @param taskItem
    */
  def storeTask(taskItem: TaskItem):Unit

  /**
    * Method to retrieve task item
    *
    * @param taskKey
    * @return
    */
  def getTask(taskKey:TaskKey):Future[Option[TaskItem]]

  /**
    * Method gives next batch of scheduled tasks to be executed
    *
    * @return
    */
  def getNextTasks(time:DateTime):Future[List[TaskItem]]
}
