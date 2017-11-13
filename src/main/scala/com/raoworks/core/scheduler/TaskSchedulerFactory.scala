package com.raoworks.core.scheduler

import akka.actor.ActorSystem
import com.raoworks.core.config.TaskSchedulerConfig
import com.raoworks.core.scheduler.TaskSchedulerType.{DefaultSchedulerType, TaskSchedulerType}
import com.raoworks.core.scheduler.store.{TaskInMemoryStore, TaskStoreType}
import com.raoworks.core.scheduler.store.TaskStoreType.TaskStoreType

/**
  * Task scheduler factory
  * - based on configured scheduler type instantiates Default scheduler to execute locally
  * - Enhancements can included remote scheduler
  */
object TaskSchedulerFactory {

  def getScheduler(schedulerType:TaskSchedulerType, taskStoreType: TaskStoreType, schedulerConfig:TaskSchedulerConfig)(implicit actorSystem:ActorSystem) = {
    val store = taskStoreType match {
      case TaskStoreType.TaskRamStoreType => TaskInMemoryStore()
      case _ => null
    }
    require(store != null, "Task store needs to be provided")
    val schedulerResource = new SchedulerResource(store)
    schedulerType match {
      case DefaultSchedulerType => DefaultScheduler(schedulerResource, schedulerConfig)
    }
  }
}
