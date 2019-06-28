package TalkToActor.Router

import akka.actor.Actor


class Worker extends Actor{
  def receive = {
    case msg:Worker.Work =>
      println(s"I recieved a work message from actor ${self}")
  }

  object Worker {
    case class Work()
  }

}
