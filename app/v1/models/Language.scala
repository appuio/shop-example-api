package v1.models

import slick.driver.PostgresDriver.api._

case class Language(id: Int, name: String)

class Languages(tag: Tag) extends Table[Language](tag, "languages") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name", O.Length(255))

  def * = (id, name) <> (Language.tupled, Language.unapply)
}
