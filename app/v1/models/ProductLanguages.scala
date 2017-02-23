package v1.models

import slick.driver.PostgresDriver.api._

case class ProductLanguage(productId: Int, languageId: Int)

class ProductLanguages(tag: Tag) extends Table[ProductLanguage](tag, "product_languages") {
  private val products = TableQuery[Products]
  private val languages = TableQuery[Languages]

  // product
  def productId = column[Int]("product_id")

  def product = foreignKey("PRODUCT_FK", productId, products)

  // language
  def languageId = column[Int]("language_id")

  def language = foreignKey("LANGUAGE_FK", languageId, languages)

  // composite primary key
  def pk = primaryKey("PK", (productId, languageId))

  // mapping to case class
  def * = (productId, languageId) <> (ProductLanguage.tupled, ProductLanguage.unapply)
}
