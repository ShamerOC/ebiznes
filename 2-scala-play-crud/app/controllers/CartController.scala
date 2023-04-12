package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.Cart
import repositories.CartRepository

@Singleton
class CartController @Inject()(cc: ControllerComponents, cartRepository: CartRepository)
  extends AbstractController(cc) {

  // GET /carts
  def getAllCarts: Action[AnyContent] = Action { implicit request =>
    val carts = cartRepository.getAllCarts
    Ok(Json.toJson(carts))
  }

  // GET /carts/:id
  def getCart(id: Long): Action[AnyContent] = Action { implicit request =>
    val cart = cartRepository.getCartById(id)
    cart match {
      case Some(c) => Ok(Json.toJson(c))
      case None => NotFound(s"Cart with id $id not found")
    }
  }

  // POST /carts
  def createCart: Action[JsValue] = Action(parse.json) { implicit request =>
    val cartResult = request.body.validate[Cart]
    cartResult.fold(
      errors => BadRequest(Json.obj("message" -> JsError.toJson(errors))),
      cart => {
        val createdCart = cartRepository.createCart(cart)
        Created(Json.toJson(createdCart)).withHeaders(LOCATION -> s"/carts/${createdCart.id}")
      }
    )
  }

  // PUT /carts/:id
  def updateCart(id: Long): Action[JsValue] = Action(parse.json) { implicit request =>
    val cartResult = request.body.validate[Cart]
    cartResult.fold(
      errors => BadRequest(Json.obj("message" -> JsError.toJson(errors))),
      cart => {
        cartRepository.updateCart(id, cart)
        NoContent
      }
    )
  }

  // DELETE /carts/:id
  def deleteCart(id: Long): Action[AnyContent] = Action { implicit request =>
    cartRepository.deleteCart(id)
    NoContent
  }
}
