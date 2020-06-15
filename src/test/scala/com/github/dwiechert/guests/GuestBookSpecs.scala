package com.github.dwiechert.guests

import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.{ContentTypes, MessageEntity, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.github.dwiechert.guests.GuestBook.{Guest, Guests}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{FlatSpec, Matchers}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

class GuestBookSpecs extends FlatSpec with Matchers with ScalatestRouteTest with ScalaFutures {
	behavior of "GuestBook"

	val guest = Guest("Dan", 31)
	val guestEntity = Marshal(guest).to[MessageEntity].futureValue

	it should "return no guests by default" in {
		val guestBook = new GuestBook

		Get("/guests") ~> guestBook.route ~> check {
			status shouldBe StatusCodes.OK
			contentType shouldBe ContentTypes.`application/json`
			entityAs[Guests] shouldBe Guests(List())
		}
	}

	it should "add a guest" in {
		val guestBook = new GuestBook

		Post("/guests").withEntity(guestEntity) ~> guestBook.route ~> check {
			status shouldBe StatusCodes.Created
		}

		Get("/guests") ~> guestBook.route ~> check {
			status shouldBe StatusCodes.OK
			contentType shouldBe ContentTypes.`application/json`
			entityAs[Guests] shouldBe Guests(List(guest))
		}
	}

	it should "conflict if a guest is added twice" in {
		val guestBook = new GuestBook

		Post("/guests").withEntity(guestEntity) ~> guestBook.route ~> check {
			status shouldBe StatusCodes.Created
		}

		Post("/guests").withEntity(guestEntity) ~> guestBook.route ~> check {
			status shouldBe StatusCodes.Conflict
		}
	}

	it should "return not found if the guest to delete doesn't exist" in {
		val guestBook = new GuestBook

		Delete("/guests/Dan") ~> guestBook.route ~> check {
			status shouldBe StatusCodes.NotFound
		}
	}

	it should "delete a guest" in {
		val guestBook = new GuestBook

		Post("/guests").withEntity(guestEntity) ~> guestBook.route ~> check {
			status shouldBe StatusCodes.Created
		}

		Get("/guests") ~> guestBook.route ~> check {
			status shouldBe StatusCodes.OK
			contentType shouldBe ContentTypes.`application/json`
			entityAs[Guests] shouldBe Guests(List(guest))
		}

		Delete("/guests/Dan") ~> guestBook.route ~> check {
			status shouldBe StatusCodes.NoContent
		}

		Get("/guests") ~> guestBook.route ~> check {
			status shouldBe StatusCodes.OK
			contentType shouldBe ContentTypes.`application/json`
			entityAs[Guests] shouldBe Guests(List())
		}
	}

	it should "clear all guests" in {
		val guestBook = new GuestBook

		Post("/guests").withEntity(guestEntity) ~> guestBook.route ~> check {
			status shouldBe StatusCodes.Created
		}

		val otherGuest = Guest("Other", 31)
		val otherGuestEntity = Marshal(otherGuest).to[MessageEntity].futureValue

		Post("/guests").withEntity(otherGuestEntity) ~> guestBook.route ~> check {
			status shouldBe StatusCodes.Created
		}

		Get("/guests") ~> guestBook.route ~> check {
			status shouldBe StatusCodes.OK
			contentType shouldBe ContentTypes.`application/json`
			entityAs[Guests] shouldBe Guests(List(guest, otherGuest))
		}

		Delete("/guests/all") ~> guestBook.route ~> check {
			status shouldBe StatusCodes.NoContent
		}

		Get("/guests") ~> guestBook.route ~> check {
			status shouldBe StatusCodes.OK
			contentType shouldBe ContentTypes.`application/json`
			entityAs[Guests] shouldBe Guests(List())
		}
	}
}
