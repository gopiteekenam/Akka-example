package TalkToActor

import TalkToActor.Checker.{BlackUser, CheckUser, WhiteUser}
import TalkToActor.Recorder.NewUser
import TalkToActor.Storage.AddUser
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.util.Timeout
import akka.pattern.ask

import scala.concurrent.duration._


case class User(username:String,email:String)

object Recorder{
  sealed trait RecorderMsg

  case class NewUser(user:User) extends RecorderMsg

  def props(checker:ActorRef, storage: ActorRef) ={
    Props(new Recorder(checker,storage))
  }
}

object Checker{
  sealed trait CheckerMsg
  case class CheckUser(user:User) extends CheckerMsg

  sealed trait CheckerResponse
  case class BlackUser(user:User) extends CheckerResponse
  case class WhiteUser(user:User) extends CheckerResponse
}



object Storage{
  sealed trait StorageMsg
  case class AddUser(user:User) extends StorageMsg
}


class Storage extends Actor {
  var users = List.empty[User]

  def receive = {
    case AddUser(user) =>
      println(s"Storage $user added")
      users = user :: users
  }
}

class Checker extends Actor {
  val blackList = List(User("Adam","adam@gmail.com"))

  def receive = {

    case CheckUser(user) if blackList.contains(user) =>

      println(s"Checker :$user in the black list")
      sender() ! BlackUser(user)

    case CheckUser(user) =>{
      println(s"Checker :$user in the white list")
      sender() ! WhiteUser(user)
    }
  }
}

class Recorder(checker: ActorRef, storage: ActorRef) extends Actor {
  import scala.concurrent.ExecutionContext.Implicits.global

  implicit val timeout = Timeout(5 seconds)

  def receive = {
    case NewUser(user) =>
      checker ? CheckUser(user) map {
        case WhiteUser(user) =>
          storage ! AddUser(user)
        case BlackUser(user) =>
          println(s"Recorder: $user in the blacklist")
      }
  }

}

object talk extends App {

  val system = ActorSystem("talk-to-actor")
  //for storage

  val storage = system.actorOf(Props[Storage],"storage")
  val checker = system.actorOf(Props[Checker],name="checker")
  val recorder = system.actorOf(Recorder.props(checker, storage), "recorder")


  recorder ! Recorder.NewUser(User("Jon", "jon@packt.com"))
  recorder ! Recorder.NewUser(User("Adam","adam@gmail.com"))

  Thread.sleep(1000)

  system.terminate()
}


class MultipleActors {

  //create system

}
