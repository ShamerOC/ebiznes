package models

import play.api.libs.json.{Json, OFormat}

case class Category (id: Long, name: String) {
}

object Category {
  implicit val categoryFormat: OFormat[Category] = Json.format[Category]
}
