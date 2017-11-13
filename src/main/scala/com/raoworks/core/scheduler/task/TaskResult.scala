package com.raoworks.core.scheduler.task

/**
  * Task Result
  */
case class TaskResult(taskKey:TaskKey, success:Boolean, message:String)