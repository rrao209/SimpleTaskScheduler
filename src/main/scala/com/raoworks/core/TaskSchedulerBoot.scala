package com.raoworks.core

import akka.actor.ActorSystem
import com.raoworks.core.config.{TaskSchedulerConfig, TaskSchedulerConfigComponent}
import com.raoworks.core.scheduler.store.TaskStoreType
import com.raoworks.core.scheduler.task._
import com.raoworks.core.scheduler.{TaskSchedulerFactory, TaskSchedulerType}
import com.typesafe.config.{Config, ConfigFactory}
import org.joda.time.{DateTime}

import scala.concurrent.duration._

/**
  * Config wiring component
  */

trait TaskSchedulerConfigWiring extends TaskSchedulerConfigComponent {
  implicit val config: Config
  override lazy val taskSchedulerConfig = TaskSchedulerConfig(config)
}

/**
  * Scheduler wiring component
  * - uses AKKA actor's for thread execution context
  */
trait TaskSchedulerBootWiring
  extends TaskSchedulerConfigWiring
    with ActorSystemProvider {
  lazy val config = ConfigFactory.load()
  lazy val actorSystem = ActorSystem("TaskSchedulerSystem")

  val schedulerType = TaskSchedulerType.fromValue(taskSchedulerConfig.schedulerTypeValue)
  val taskStoreType = TaskStoreType.fromValue(taskSchedulerConfig.storeTypeValue)
  val scheduler = TaskSchedulerFactory.getScheduler(schedulerType, taskStoreType, taskSchedulerConfig)(actorSystem)
}

/**
  * Scheduler Boot
  */
object TaskSchedulerBoot extends App with TaskSchedulerBootWiring {
  var curDateTime = new DateTime(System.currentTimeMillis())

  val task1 = new MyTask("Task1", 1, 500)
  val scheduleOnceTaskItem1 = TaskBuilder
    .setTaskItemType(TaskItemType.fromValue("oneTimeTask"))
    .setStartTime(curDateTime)
    .setTaskName(task1.name)
    .build(task1)

  val task2 = new MyTask("Task2", 2, 500)
  val scheduleOnceTaskItem2 = TaskBuilder
    .setTaskItemType(TaskItemType.fromValue("oneTimeTask"))
    .setStartTime(curDateTime)
    .setTaskName(task2.name)
    .build(task2)

  println(s"*****************")
  println(s"Adding 2 one time task- Task1, Task2 with same start time with different priority")
  println(s"Priority signifies order of execution")
  println(s"*****************")
  scheduler.schedule(scheduleOnceTaskItem2)
  scheduler.schedule(scheduleOnceTaskItem1)

  Thread.sleep(4000)

  println(s"*****************")
  println(s"Adding recurring task - Task3 with interval of 2 seconds")
  println(s"*****************")
  curDateTime = new DateTime(System.currentTimeMillis())
  val recurringTask = new MyTask("Task3", 1, 2000)
  val scheduleRecurringTaskItem1 = TaskBuilder
    .setTaskItemType(TaskItemType.fromValue("recurringTask"))
    .setStartTime(curDateTime)
    .setIntervalSeconds(2 seconds)
    .setTaskName(recurringTask.name).build(recurringTask)
  scheduler.schedule(scheduleRecurringTaskItem1)

  scheduler.boot()
}