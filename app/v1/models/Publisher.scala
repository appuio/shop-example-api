package v1.models

import slick.driver.PostgresDriver.api._

case class Publisher(id: Int, name: String)

class Publishers(tag: Tag) extends Table[Publisher](tag, "publishers") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name", O.Length(255))

  def * = (id, name) <> (Publisher.tupled, Publisher.unapply)
}
