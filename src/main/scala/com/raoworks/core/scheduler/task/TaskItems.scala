package com.raoworks.core.scheduler.task


import akka.actor.ActorSystem
import org.joda.time.DateTime

import scala.concurrent.Future
import scala.concurrent.duration._

/**
  * one time task item which is not recurring
  */
case class DefaultTaskItem(taskName: String,
                           startTime: DateTime,
                           interval: FiniteDuration = 0 seconds,
                           repeatCount: Int = 0,
                           task:Task,
                           val visited:Boolean = false) extends TaskItem {

  override def execute(actorSystem: ActorSystem): Future[TaskResult] = {
    task.execute(actorSystem, startTime)
  }

  override def priority: Int = task.priority
}

/**
  * Recurring task item which is recurring
  */
case class RecurringTaskItem(taskName: String,
                             startTime: DateTime,
                             interval: FiniteDuration = RecurringTaskAttr.defaultRepeatInterval,
                             repeatCount: Int = RecurringTaskAttr.repeatForever,
                             task: Task,
                             val visited:Boolean = false) extends TaskItem {

  override def execute(actorSystem: ActorSystem): Future[TaskResult] = {
    task.execute(actorSystem, startTime)
  }

  override def priority: Int = task.priority
}

object RecurringTaskAttr {
  val repeatForever = -1
  val defaultRepeatInterval = 2.seconds
}