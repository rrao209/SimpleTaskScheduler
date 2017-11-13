package com.raoworks.core

import akka.actor.ActorSystem

/**
  * Actor system provider
  */
trait ActorSystemProvider {
  val actorSystem: ActorSystem
}
