package com.raoworks.core.scheduler.task

import com.raoworks.core.scheduler.task.TaskItemType.TaskItemType
import org.joda.time.DateTime

import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration

/**
  * Task Builder
  */
object TaskBuilder {
  var taskName:String = null
  var taskItemType: TaskItemType = null
  var startDateTime: DateTime = null
  var intervalInSeconds: FiniteDuration = null
  var repeatCount: Int = 0

  def setTaskName(name:String) = {
    taskName = name
    this
  }
  def setTaskItemType(taskType: TaskItemType) = {
    taskItemType = taskType
    this
  }

  def setStartTime(startTime:DateTime) = {
    startDateTime = startTime
    this
  }

  def setIntervalSeconds(interval:FiniteDuration) = {
    intervalInSeconds = interval
    this
  }

  def setRepeatCount(repeatCountValue:Int) = {
    repeatCount = repeatCountValue
    this
  }

  def build(taskInstance: Task):TaskItem = {
    taskItemType match {
      case TaskItemType.DefaultTaskItemType => DefaultTaskItem(taskName,
        startTime = startDateTime,
        task = taskInstance)

      case TaskItemType.RecurringTaskItemType => RecurringTaskItem(taskName,
        startTime = startDateTime,
        interval = intervalInSeconds,
        task = taskInstance)
    }
  }
}
