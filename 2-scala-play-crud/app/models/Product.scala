package models

import play.api.libs.json.{Json, OFormat}

case class Product(id: Long, name: String, category: Category) {
}

object Product {
  implicit val productFormat: OFormat[Product] = Json.format[Product]
}
