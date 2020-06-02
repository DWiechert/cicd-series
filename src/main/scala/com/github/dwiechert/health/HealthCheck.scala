package com.github.dwiechert.health

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.github.dwiechert.CICDRoute

class HealthCheck extends CICDRoute {
	override def route: Route = path("health") {
		get {
			complete(StatusCodes.OK)
		}
	}
}
