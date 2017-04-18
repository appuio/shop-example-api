package v1.controllers

import javax.inject.Inject

import play.api.inject.ConfigurationProvider

import scala.concurrent.Future
import play.api.libs.json._
import play.api.libs.ws._
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

class UsersController @Inject()(configurationProvider: ConfigurationProvider, ws: WSClient) extends Controller {

  private val configuration = configurationProvider.get
  private val endpoint = configuration.getString("ms.users.endpoint") match {
    case Some(url) => url
    case None => "localhost"
  }

  /**
    * Get a list of all active users (proxy request to users ms)
    * TODO: remove this debug endpoint
    */
  def index = Action.async {

    val request: WSRequest =
      ws.url(endpoint + "/users")
        .withHeaders("Accept" -> "application/json")

    request.get().map { response => parseResponse(response) }

  }

  /**
    * Initiate a new user session (proxy request to users ms)
    */
  def login = Action.async(parse.json) { request =>

    // prepare the request to the users service
    val loginRequest: WSRequest =
      ws.url(endpoint + "/users/login")
        .withHeaders(
          "Accept" -> "application/json",
          "Content-Type" -> "application/json"
        )

    // execute the request and process the response
    loginRequest.post(request.body).map { response => parseResponse(response) }

  }

  /**
    * Create a new user account (proxy request to users ms)
    */
  def register = Action.async(parse.json) { request =>

    // prepare the request to the users service
    val registrationRequest: WSRequest =
      ws.url(endpoint + "/users")
        .withHeaders(
          "Accept" -> "application/json",
          "Content-Type" -> "application/json"
        )

    // execute the request and process the response
    registrationRequest.post(request.body).map { response => parseResponse(response) }

  }

  /**
    * End the user session (proxy request to users ms)
    */
  def logout = Action.async { request =>

    // check whether an auth header was included in the request
    request.headers.get("Authorization") match {

      // if there was an auth header, continue logout procedure
      case Some(auth) =>

        // prepare the request to the users service
        val logoutRequest: WSRequest =
          ws.url(endpoint + "/users/logout")
            .withHeaders(
              "Accept" -> "application/json",
              "Authorization" -> auth
            )

        // execute the request and process the response
        logoutRequest.get().map { response => parseResponse(response) }

      // if there was no auth header, the user is already logged out
      case None => Future.successful(Ok(Json.obj("success" -> true)))

    }

  }

  /**
    * Parse a response from the users ms
    * TODO: this should also allow setting response headers (i.e. login)
    */
  def parseResponse(response: WSResponse):Result = {
    Status(response.status)(response.json)
  }
}
