package controllers

import play.api.mvc._
import play.api.libs.json._
import models.Category
import repositories.CategoryRepository

import javax.inject.Inject

@Singleton
class CategoryController @Inject()(cc: ControllerComponents, categoryRepository: CategoryRepository)
  extends AbstractController(cc) {

  // GET /categories
  def getAllCategories: Action[AnyContent] = Action { implicit request =>
    val categories = categoryRepository.getAllCategories
    Ok(Json.toJson(categories))
  }

  // GET /categories/:id
  def getCategory(id: Long): Action[AnyContent] = Action { implicit request =>
    val category = categoryRepository.getCategoryById(id)
    category match {
      case Some(c) => Ok(Json.toJson(c))
      case None => NotFound(s"Category with id $id not found")
    }
  }

  // POST /categories
  def createCategory: Action[JsValue] = Action(parse.json) { implicit request =>
    val categoryResult = request.body.validate[Category]
    categoryResult.fold(
      errors => BadRequest(Json.obj("message" -> JsError.toJson(errors))),
      category => {
        val createdCategory = categoryRepository.createCategory(category)
        Created(Json.toJson(createdCategory)).withHeaders(LOCATION -> s"/categories/${createdCategory.id}")
      }
    )
  }

  // PUT /categories/:id
  def updateCategory(id: Long): Action[JsValue] = Action(parse.json) { implicit request =>
    val categoryResult = request.body.validate[Category]
    categoryResult.fold(
      errors => BadRequest(Json.obj("message" -> JsError.toJson(errors))),
      category => {
        categoryRepository.updateCategory(id, category)
        NoContent
      }
    )
  }

  // DELETE /categories/:id
  def deleteCategory(id: Long): Action[AnyContent] = Action { implicit request =>
    categoryRepository.deleteCategory(id)
    NoContent
  }
}
