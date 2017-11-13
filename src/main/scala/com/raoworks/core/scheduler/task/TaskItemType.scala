package com.raoworks.core.scheduler.task

/**
  * Task item types enum
  */
object TaskItemType {

  sealed trait TaskItemType {
    val name:String
  }

  case object DefaultTaskItemType extends TaskItemType {
    val name = "oneTimeTask"
  }

  case object RecurringTaskItemType extends TaskItemType {
    val name = "recurringTask"
  }

  def fromValue(name:String):TaskItemType = {
    name match {
      case DefaultTaskItemType.name => DefaultTaskItemType
      case RecurringTaskItemType.name => RecurringTaskItemType
    }
  }
}
