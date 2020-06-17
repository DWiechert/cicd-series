package com.github.dwiechert.guests

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.github.dwiechert.CICDRoute
import spray.json.DefaultJsonProtocol._
import spray.json.RootJsonFormat

import scala.collection.mutable.ListBuffer
import scala.concurrent.Future

object GuestBook {

	final case class Guest(name: String, age: Int)

	implicit val guestFormat: RootJsonFormat[Guest] = jsonFormat2(Guest)

	final case class Guests(guests: List[Guest])

	implicit val guestsFormat: RootJsonFormat[Guests] = jsonFormat1(Guests)
}

class GuestBook extends CICDRoute {

	import GuestBook._

	private val guests = new ListBuffer[Guest]

	override def route: Route = listGuestsRoute() ~ addGuestRoute() ~ deleteGuestRoute() ~ clearRoute()

	private def listGuestsRoute(): Route = {
		path("guests") {
			get {
				complete(Guests(guests.toList))
			}
		}
	}

	private def addGuestRoute(): Route = {
		path("guests") {
			post {
				entity(as[Guest]) { guest =>
					val maybeAdd = addGuest(guest)
					onSuccess(maybeAdd) {
						case true => complete(StatusCodes.Created)
						case false => complete(StatusCodes.Conflict)
					}
				}
			}
		}
	}

	private def addGuest(guest: Guest): Future[Boolean] = {
		if (guests.contains(guest)) Future.successful(false)
		else {
			guests += guest
			Future.successful(true)
		}
	}

	private def deleteGuestRoute(): Route = {
		path("guests" / Segment) { name =>
			delete {
				val maybeDelete = deleteGuest(name)
				onSuccess(maybeDelete) {
					case true => complete(StatusCodes.NoContent)
					case false => complete(StatusCodes.NotFound)
				}
			}
		}
	}

	private def deleteGuest(name: String): Future[Boolean] = {
		val found = guests.filter(_.name == name)
		if (found.isEmpty) Future.successful(false)
		else {
			found.foreach(f => guests -= f)
			Future.successful(true)
		}
	}

	private def clearRoute(): Route = {
		path("clear") {
			delete {
				val maybeClear = clearGuests()
				onSuccess(maybeClear) {
					case true => complete(StatusCodes.NoContent)
					case false => complete(StatusCodes.InternalServerError)
				}
			}
		}
	}

	private def clearGuests(): Future[Boolean] = {
		guests.clear()
		Future.successful(true)
	}
}
