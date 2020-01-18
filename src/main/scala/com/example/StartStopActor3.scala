package com.example

import akka.actor.ActorSystem
import akka.actor.typed.{Behavior, PostStop, Signal}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

object StartStopActor3 {
  def apply(): Behavior[String] =     Behaviors.setup(new StartStopActor3(_))
}

class StartStopActor3(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  println("third started")

  override def onMessage(msg: String): Behavior[String] = {
    // no messages handled by this actor
    Behaviors.unhandled
  }

  override def onSignal: PartialFunction[Signal, Behavior[String]] = {
    case PostStop =>
      println("third stopped")
      this
  }
}
