package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.Product
import repositories.ProductRepository

@Singleton
class ProductController @Inject()(cc: ControllerComponents, productRepository: ProductRepository)
  extends AbstractController(cc) {

  // GET /products
  def getAllProducts: Action[AnyContent] = Action { implicit request =>
    val products = productRepository.getAllProducts
    Ok(Json.toJson(products))
  }

  // GET /products/:id
  def getProduct(id: Long): Action[AnyContent] = Action { implicit request =>
    val product = productRepository.getProductById(id)
    product match {
      case Some(p) => Ok(Json.toJson(p))
      case None => NotFound(s"Product with id $id not found")
    }
  }

  // POST /products
  def createProduct: Action[JsValue] = Action(parse.json) { implicit request =>
    val productResult = request.body.validate[Product]
    productResult.fold(
      errors => BadRequest(Json.obj("message" -> JsError.toJson(errors))),
      product => {
        val createdProduct = productRepository.createProduct(product)
        Created(Json.toJson(createdProduct)).withHeaders(LOCATION -> s"/products/${createdProduct.id}")
      }
    )
  }

  // PUT /products/:id
  def updateProduct(id: Long): Action[JsValue] = Action(parse.json) { implicit request =>
    val productResult = request.body.validate[Product]
    productResult.fold(
      errors => BadRequest(Json.obj("message" -> JsError.toJson(errors))),
      product => {
        productRepository.updateProduct(id, product)
        NoContent
      }
    )
  }

  // DELETE /products/:id
  def deleteProduct(id: Long): Action[AnyContent] = Action { implicit request =>
    productRepository.deleteProduct(id)
    NoContent
  }
}