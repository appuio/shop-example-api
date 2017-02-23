package v1.controllers

import javax.inject.Inject

import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json._
import play.api.mvc._
import slick.driver.JdbcProfile
import v1.models.{Category, Categories, Language, Languages, LicenseType, LicenseTypes, ProductLanguages, Product, Products, Publisher, Publishers}

import scala.concurrent.ExecutionContext.Implicits.global

class ProductsController @Inject()(dbConfigProvider: DatabaseConfigProvider) extends Controller {
  val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig.driver.api._

  private val products = TableQuery[Products]
  private val categories = TableQuery[Categories]
  private val licenseTypes = TableQuery[LicenseTypes]
  private val publishers = TableQuery[Publishers]

  // implicit formats for the case classes
  // generates mapping of case class to JSON and reverse
  implicit val productFormat: Format[Product] = Json.format[Product]
  implicit val categoryFormat: Format[Category] = Json.format[Category]
  implicit val licenseTypeFormat: Format[LicenseType] = Json.format[LicenseType]
  implicit val publisherFormat: Format[Publisher] = Json.format[Publisher]

  def index = Action.async {
    val allProductsQuery = for {
      (((p, c), l), pb) <- ((products join categories on (_.categoryId === _.id)) join licenseTypes on (_._1.licenseTypeId === _.id)) join publishers on (_._1._1.publisherId === _.id)
    } yield (p, c, l, pb)

    for {
      result <- dbConfig.db.run(allProductsQuery.result)
    } yield {
      Ok(Json.obj(
        "success" -> true,
        "items" -> result.map {
          row =>
            Json.obj(
              "product" -> row._1,
              "category" -> row._2,
              "licenseType" -> row._3,
              "publisher" -> row._4
            )
        }
      ))
    }
  }

  def read(id: Int) = Action.async {
    val productQuery = products.filter(_.id === id)

    val extendedProductQuery = for {
      (((p, c), l), pb) <- ((productQuery join categories on (_.categoryId === _.id)) join licenseTypes on (_._1.licenseTypeId === _.id)) join publishers on (_._1._1.publisherId === _.id)
    } yield (p, c, l, pb)

    for {
      result <- dbConfig.db.run(extendedProductQuery.result.head)
    } yield {
      println(result)
      Ok(Json.obj(
        "success" -> true,
        "data" -> Json.obj(
          "product" -> result._1,
          "category" -> result._2,
          "licenseType" -> result._3,
          "publisher" -> result._4
        )
      ))
    }
  }
}
