package TalkToActor.Router

import akka.actor.{Actor, ActorRef, Props}

class Router extends Actor{

  var routees: List[ActorRef] = _

  override def preStart(): Unit = {
    routees = List.fill(5)(context.actorOf(Props[Worker]))

  }
  def receive = {
    case msg =>
      println("I am router and I received a message")
      routees(util.Random.nextInt(routees.size)) forward msg

  }


}
