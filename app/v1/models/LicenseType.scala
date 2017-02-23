package v1.models

import slick.driver.PostgresDriver.api._

case class LicenseType(id: Int, name: String)

class LicenseTypes(tag: Tag) extends Table[LicenseType](tag, "license_types") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name", O.Length(255))

  def * = (id, name) <> (LicenseType.tupled, LicenseType.unapply)
}
