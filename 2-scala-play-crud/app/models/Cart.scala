package models

import play.api.libs.json.{Json, OFormat}

case class Cart (id: Long, products: List[Product]) {
}

object Cart {
  implicit val cartFormat: OFormat[Cart] = Json.format[Cart]
}
