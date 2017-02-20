package v1.models

import slick.driver.PostgresDriver.api._

case class Product(id: Int, name: String, price: Float)

class Products(tag: Tag) extends Table[Product](tag, "products") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name", O.Length(255))

  def price = column[Float]("price", O.Length(10))

  def * = (id, name, price) <> (Product.tupled, Product.unapply)
}
