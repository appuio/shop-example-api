package v1.controllers

import javax.inject.Inject

import play.api.inject.ConfigurationProvider

import scala.concurrent.Future
import play.api.libs.json._
import play.api.libs.ws._
import play.api.mvc._
import play.api.Logger._

import scala.concurrent.ExecutionContext.Implicits.global

class OrdersController @Inject()(configurationProvider: ConfigurationProvider, ws: WSClient) extends Controller {

  private val configuration = configurationProvider.get
  private val endpoint = configuration.getString("ms.orders.endpoint") match {
    case Some(url) => url
    case None => "localhost"
  }
  private val invalidToken = Future.successful(
    Unauthorized(Json.obj(
      "success" -> false,
      "messages" -> List("INVALID_TOKEN")
    ))
  )

  /**
    * Get a list of all orders that belong to the user (proxy request to orders ms)
    */
  def index = Action.async { request =>

    // check whether an auth header was included in the request
    request.headers.get("Authorization") match {

      // if there was an auth header, request the orders
      case Some(auth) =>

        // prepare the request to the users service
        val indexRequest: WSRequest =
          ws.url(endpoint + "/orders")
            .withHeaders(
              "Accept" -> "application/json",
              "Authorization" -> auth
            )

        // execute the request and process the response
        indexRequest.get().map { response => parseResponse(response) }

      // if there was no auth header, return an error
      case None => invalidToken

    }

  }

  def read(id: Int) = Action.async { request =>

    // check whether an auth header was included in the request
    request.headers.get("Authorization") match {

      // if there was an auth header, request the order
      case Some(auth) =>

        // prepare the request to the users service
        val readRequest: WSRequest =
          ws.url(endpoint + "/orders/" + id)
            .withHeaders(
              "Accept" -> "application/json",
              "Authorization" -> auth
            )

        // execute the request and process the response
        readRequest.get().map { response => parseResponse(response) }

      // if there was no auth header, return an error
      case None => invalidToken

    }

  }

  def create = Action.async(parse.json) { request =>

    // check whether an auth header was included in the request
    request.headers.get("Authorization") match {

      // if there was an auth header, create a new order
      case Some(auth) =>

        // prepare the request to the users service
        val createRequest: WSRequest =
          ws.url(endpoint + "/orders")
            .withHeaders(
              "Accept" -> "application/json",
              "Authorization" -> auth,
              "Content-Type" -> "application/json"
            )

        // execute the request and process the response
        createRequest.post(request.body).map { response => parseResponse(response) }

      // if there was no auth header, return an error
      case None => invalidToken

    }

  }

  private def parseResponse(response: WSResponse): Result = {
    Status(response.status)(response.json)
  }

}
