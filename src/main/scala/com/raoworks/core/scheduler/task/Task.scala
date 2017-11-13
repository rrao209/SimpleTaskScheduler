package com.raoworks.core.scheduler.task

import akka.actor.ActorSystem
import org.joda.time.DateTime

import scala.concurrent.Future

/**
  * Task interface
  */
trait Task {
  def name: String

  def priority:Int

  def execute(actorSystem: ActorSystem, startTime:DateTime): Future[TaskResult]
}
