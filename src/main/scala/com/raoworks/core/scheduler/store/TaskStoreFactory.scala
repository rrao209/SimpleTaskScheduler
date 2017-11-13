package com.raoworks.core.scheduler.store

import akka.actor.ActorSystem
import com.raoworks.core.scheduler.store.TaskStoreType.{TaskRamStoreType, TaskStoreType}

/**
  * Task store factory
  * Based on store type configured - instantiates the Task store variant
  */
object TaskStoreFactory {
  def apply(taskStoreType: TaskStoreType)(implicit actorSystem: ActorSystem):TaskStore = {
    taskStoreType match {
      case TaskRamStoreType => TaskInMemoryStore()
    }
  }
}
