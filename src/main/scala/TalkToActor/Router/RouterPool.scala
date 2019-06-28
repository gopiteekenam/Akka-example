package TalkToActor.Router

import TalkToActor.Router.RouterPool.Print
import akka.actor.{Actor, ActorRef, ActorSystem, Props}

class RouterPool1 extends Actor{

  var routees:List[ActorRef] = _
  override def preStart(): Unit = {
    routees = List.fill(5)(
      context.actorOf(Props[RouterPool1])
    )
  }
  def receive = {
    case msg: Print =>
      println("Iam a Router and I received a message")
  }
}

class RouterGroup(routees:List[String]) extends Actor{
  def receive = {
    case msg: Print =>
      println("Iam a Router group and I received a message")
      context.actorSelection(routees(util.Random.nextInt(routees.size))) forward msg
  }
}
object RouterPool extends App{
  case class Print()
  val system  = ActorSystem("router-group")
  system.actorOf(Props[RouterPool1],"w1")
  system.actorOf(Props[RouterPool1],"w2")
  system.actorOf(Props[RouterPool1],"w3")
  system.actorOf(Props[RouterPool1],"w4")
  system.actorOf(Props[RouterPool1],"w5")

  val workers : List[String] = List(
    "/user/w1",
    "/user/w2",
    "/user/w3",
    "/user/w4",
    "/user/w5"
  )

  val routerGroup  = system.actorOf(Props(classOf[RouterGroup],workers))

  routerGroup ! Print()
  routerGroup ! Print()

  Thread.sleep(100)
  system.terminate()
}
