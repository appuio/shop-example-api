package v1.models

import slick.driver.PostgresDriver.api._

case class Category(id: Int, name: String)

class Categories(tag: Tag) extends Table[Category](tag, "categories") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name", O.Length(255))

  def * = (id, name) <> (Category.tupled, Category.unapply)
}
