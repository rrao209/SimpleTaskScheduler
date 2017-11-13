package com.raoworks.core.scheduler.task

import akka.actor.ActorSystem
import org.joda.time.DateTime

import scala.concurrent.Future
import scala.concurrent.duration.FiniteDuration

/**
  * Task item
  */
trait TaskItem{

  def taskName:String

  def startTime: DateTime

  def interval: FiniteDuration

  def repeatCount: Int

  def priority:Int

  def visited:Boolean

  def execute(actorSystem:ActorSystem):Future[TaskResult]

}
