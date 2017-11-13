package com.raoworks.core.scheduler

/**
  * Task Schedular types enum
  */
object TaskSchedulerType {

  sealed trait TaskSchedulerType {
    val name:String
  }

  case object DefaultSchedulerType extends TaskSchedulerType{
    val name = "DefaultScheduler"
  }

  def fromValue(name:String):TaskSchedulerType = {
    name match {
      case DefaultSchedulerType.name => DefaultSchedulerType
    }
  }
}
