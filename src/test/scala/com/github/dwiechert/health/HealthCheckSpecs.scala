package com.github.dwiechert.health

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{FlatSpec, Matchers}

class HealthCheckSpecs extends FlatSpec with Matchers with ScalatestRouteTest {
	behavior of "HealthCheck"

	val healthCheck = new HealthCheck

	it should "return OK for the health check" in {
		Get("/health") ~> healthCheck.route ~> check {
			response.status shouldBe StatusCodes.OK
		}
	}
}
