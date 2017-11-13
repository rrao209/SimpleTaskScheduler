package com.raoworks.core.config

import com.typesafe.config.Config

import scala.concurrent.duration._

/**
  * Scheduler config component
  */
trait TaskSchedulerConfigComponent {
val taskSchedulerConfig:TaskSchedulerConfig
}

/**
  * Task scheduler config
  *
  * @param schedulerTypeValue
  * @param storeTypeValue
  * @param schedulerInterval
  */
case class TaskSchedulerConfig(schedulerTypeValue:String, storeTypeValue:String, schedulerInterval:FiniteDuration)

object TaskSchedulerConfig {
  def apply(config:Config):TaskSchedulerConfig = {
    val schedulerConfig = config.getConfig("scheduler")
    val schedulerType = schedulerConfig.getString("schedulerType")
    val taskStoreType = schedulerConfig.getString("storeType")
    val schedulerInterval = schedulerConfig.getLong("schedulerInterval")
    TaskSchedulerConfig(schedulerType, taskStoreType, schedulerInterval seconds)
  }
}