package com.github.dwiechert.index

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.github.dwiechert.CICDRoute

class Index extends CICDRoute {
	override def route: Route = pathSingleSlash {
		get {
			complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>CI/CD Series</h1>"))
		}
	}
}
