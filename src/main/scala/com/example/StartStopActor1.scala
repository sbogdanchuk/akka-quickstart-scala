package com.example

import akka.actor.typed.{ActorSystem, Behavior, PostStop, Signal}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}

import scala.io.StdIn


object StartStopActor1 {
  def apply(): Behavior[String] = Behaviors.setup(new StartStopActor1(_))
}

class StartStopActor1(context: ActorContext[String]) extends AbstractBehavior[String](context) {
  println("first started")
  context.spawn(StartStopActor2(), "second")

  override def onMessage(msg: String): Behavior[String] =
    msg match {
      case "stop" => Behaviors.stopped
    }

  override def onSignal: PartialFunction[Signal, Behavior[String]] = {
    case PostStop =>
      println("first stopped")
      this
  }

}

object Main0 extends App {
  val rootBehavior = Behaviors.setup[Nothing]{context =>
    val first = context.spawn(StartStopActor1(), "first")
    first ! "stop"
    Behaviors.empty
  }
  val system = ActorSystem[Nothing](rootBehavior, "root")
  StdIn.readLine
  system.terminate()
}
