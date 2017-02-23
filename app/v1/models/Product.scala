package v1.models

import slick.driver.PostgresDriver.api._

case class Product(id: Int, uuid: String, categoryId: Int, licenseTypeId: Int, publisherId: Int, name: String, price: Float, description: String)

class Products(tag: Tag) extends Table[Product](tag, "products") {
  private val categories = TableQuery[Categories]
  private val licenseTypes = TableQuery[LicenseTypes]
  private val publishers = TableQuery[Publishers]

  // basic columns
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def uuid = column[String]("uuid")

  def name = column[String]("name", O.Length(255))

  def price = column[Float]("price", O.Length(10))

  def description = column[String]("description")

  // category
  def categoryId = column[Int]("category_id")

  def category = foreignKey("CATEGORY_FK", categoryId, categories)(_.id)

  // license type
  def licenseTypeId = column[Int]("license_type_id")

  def licenseType = foreignKey("LICENSE_TYPE_FK", licenseTypeId, licenseTypes)(_.id)

  // publisher
  def publisherId = column[Int]("publisher_id")

  def publisher = foreignKey("PUBLISHER_FK", publisherId, publishers)(_.id)

  // mapping to case class
  def * = (id, uuid, categoryId, licenseTypeId, publisherId, name, price, description) <> (Product.tupled, Product.unapply)
}
