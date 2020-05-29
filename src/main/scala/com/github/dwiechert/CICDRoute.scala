package com.github.dwiechert

import akka.http.scaladsl.server.Route

trait CICDRoute {
	def route: Route
}
