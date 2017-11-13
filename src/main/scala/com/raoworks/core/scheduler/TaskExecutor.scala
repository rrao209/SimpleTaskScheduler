package com.raoworks.core.scheduler

import com.raoworks.core.scheduler.task.TaskResult

import scala.concurrent.Future

/**
  * Task executor interface
  */
trait TaskExecutor {
  def executeTasks()
}
