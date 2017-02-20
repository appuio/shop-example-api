package v1.controllers

import javax.inject.Inject

import play.api.mvc._
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json._
import play.api.libs.ws.WSClient
import slick.driver.JdbcProfile
import v1.models.{Product, Products}

class ProductsController @Inject()(dbConfigProvider: DatabaseConfigProvider, wsClient: WSClient) extends Controller {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig.driver.api._

  private val products = TableQuery[Products]

  implicit val productFormat: Format[Product] = Json.format[Product]

  def index = Action.async {
    val allProductsQuery = products.result

    for {
      result <- dbConfig.db.run(allProductsQuery)
    } yield {
      Ok(Json.obj(
        "success" -> true,
        "data" -> Json.toJson(result)
      ))
    }
  }

  def read(id: Int) = Action.async {
    val productQuery = products.filter(_.id === id).result

    for {
      result <- dbConfig.db.run(productQuery)
    } yield {
      Ok(Json.obj(
        "success" -> true,
        "data" -> Json.toJson(result)
      ))
    }
  }
}
