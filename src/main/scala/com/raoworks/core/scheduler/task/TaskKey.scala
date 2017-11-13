package com.raoworks.core.scheduler.task

import org.joda.time.DateTime

/**
  * Task item key
  * - Sorts on start time first
  * - if start is same then sort on Task priority ( this is to take care of task dependency scenario)
  */
case class TaskKey(startTime:DateTime, name:String, taskPriority:Int = 1)  extends Ordered[TaskKey] {
  override def compare(that: TaskKey): Int = {
    if(startTime.isBefore(that.startTime)){
      return -1
    }
    else if(startTime.isAfter(that.startTime)){
      return 1
    }
    else {
      return (taskPriority - that.taskPriority)
    }
  }
}