package sample.hello

import akka.actor.{Actor, ActorSystem, Props}

case class Greet (who:String)

class Greeter extends Actor{
  def receive = {
    case Greet(who) => println(s"hello $who")

  }
}
object WhoToGreet extends App {

  //Create the 'Hello akka' actor system
  val system = ActorSystem("Hello-Akka")

  //Create the greeter actor
  val greeter = system.actorOf(Props[Greeter],"greeter")

  greeter ! Greet("sateesh")

}
