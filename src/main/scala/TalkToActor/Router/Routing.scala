package TalkToActor.Router

import TalkToActor.Router.Master.Worker
import akka.actor.{Actor, ActorSystem, Props, Terminated}
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}

class Master extends Actor{
  var router = {
    val routees = Vector.fill(5){
      val r = context.actorOf(Props[Worker])
      context.watch(r)
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(),routees)
  }

  def receive = {
    case w: Worker =>
      router.route(w,sender())

    case Terminated(a) =>
      router = router.removeRoutee(a)
      val r = context.actorOf(Props[Worker])
      context.watch(r)
      router = router.addRoutee(r)
  }

}

object Master{
  case class Worker()
}

object Routing extends App{
  val system = ActorSystem("router")
  val route = system.actorOf(Props[Router])
  route ! Worker()
  route ! Worker()
  route ! Worker()
  route ! Worker()
  route ! Worker()

  Thread.sleep(1000)
  system.terminate()
}
