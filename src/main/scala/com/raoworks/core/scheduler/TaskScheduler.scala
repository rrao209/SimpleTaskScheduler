package com.raoworks.core.scheduler

import com.raoworks.core.scheduler.task.TaskItem

/**
  * Task scheduler interface
  */
trait TaskScheduler {

  /**
    * Method to schedule task item
    *
    * @param taskItem
    */
  def schedule(taskItem:TaskItem):Unit

  /**
    * Method to boot scheduler
    */
  def boot
}
