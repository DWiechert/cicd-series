package com.github.dwiechert

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.github.dwiechert.guests.GuestBook
import com.github.dwiechert.health.HealthCheck
import com.github.dwiechert.index.Index

import scala.util.{Failure, Success}

object Main {
	def main(args: Array[String]): Unit = {
		implicit val system = ActorSystem("cicd-system")
		implicit val materializer = ActorMaterializer
		implicit val executionContext = system.dispatcher

		val routes = new Index().route ~ new HealthCheck().route ~ new GuestBook().route

		val port = system.settings.config.getInt("cicd-system.config.port")
		val host = system.settings.config.getString("cicd-system.config.host")

		val bindingFuture = Http().bindAndHandle(routes, host, port)

		bindingFuture.onComplete {
			case Success(binding) =>
				val address = binding.localAddress
				system.log.info("Server online at http://{}:{}/", address.getHostString, address.getPort)
			case Failure(ex) =>
				system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
				system.terminate()
		}
	}
}
