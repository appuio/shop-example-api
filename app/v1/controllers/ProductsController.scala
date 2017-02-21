package v1.controllers

import javax.inject.Inject

import play.api.mvc._
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json._
import slick.driver.JdbcProfile
import v1.models.{Product, Products}

import scala.concurrent.ExecutionContext.Implicits.global

class ProductsController @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Controller {
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
        "items" -> Json.toJson(result)
      ))
    }
  }

  def read(id: Int) = Action.async {
    val productQuery = products.filter(_.id === id).result.head

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
