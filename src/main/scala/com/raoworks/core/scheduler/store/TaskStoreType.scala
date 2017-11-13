package com.raoworks.core.scheduler.store

/**
  * Task store types enum
  * - Store type can be a InMemory store or store backed with a Datastore
  */
object TaskStoreType {

  sealed trait TaskStoreType {
    val name:String
  }

  case object TaskRamStoreType extends TaskStoreType{
    val name = "TaskRamStore"
  }

  def fromValue(name:String):TaskStoreType = {
    name match {
      case TaskRamStoreType.name => TaskRamStoreType
    }
  }
}
