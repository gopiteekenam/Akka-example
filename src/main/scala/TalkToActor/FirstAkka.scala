package TalkToActor

import TalkToActor.MusicController.Play
import TalkToActor.MusicController.Stop
import TalkToActor.MusicPlayer.{StartMusic, StopMusic}
import akka.actor.{Actor, ActorSystem, Props}

//Music controller messages
object MusicController{
  sealed trait ControllerMsg
  object Play extends ControllerMsg
  object Stop extends ControllerMsg
}

//Music controller
class MusicController extends Actor {
  def receive = {
    case Play =>
      println("Music started ...")
    case Stop =>
      println("Music sopped...")
  }
}

//Music player messages
object MusicPlayer{
sealed trait PlayMsg
  object StartMusic extends PlayMsg
  object StopMusic extends PlayMsg
}

//Music player
class MusicPlayer extends Actor{
  def receive = {
    case StopMusic =>
      println("I don't want to stop music")
    case StartMusic =>
      val controller = context.actorOf(Props[MusicController],"controller")
      controller ! Play
    case _ =>
      println("unknown message")
  }
}

object Creation extends App{
  val system = ActorSystem("ceration")
  val player = system.actorOf(Props[MusicPlayer],"player")
  player ! StartMusic
}



class FirstAkka {

}
