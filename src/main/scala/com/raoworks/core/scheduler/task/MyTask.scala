package com.raoworks.core.scheduler.task

import akka.actor.ActorSystem
import org.joda.time.{DateTime, Duration}

import scala.concurrent.Future


class MyTask(val name: String, val priority:Int = 1, interval:Int) extends Task {

  override def execute(actorSystem: ActorSystem, startTime:DateTime): Future[TaskResult] = {
    import actorSystem.dispatcher

    Future.successful {
      Thread.sleep(interval)
      val endTime = new DateTime(System.currentTimeMillis())
      val duration = new Duration(startTime, endTime)
      TaskResult(TaskKey(startTime, name), true, s"Task startTime= ${startTime} ||  endTime = $endTime || Time taken = ${duration.getStandardSeconds} seconds")
    }
  }
}
