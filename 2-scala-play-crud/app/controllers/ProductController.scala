package controllers

import play.api.mvc.{AnyContent, BaseController, ControllerComponents, Request}

import javax.inject.{Inject, Singleton}

@Singleton
class ProductController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  def getAll() = Action { implicit request: Request[AnyContent] =>
    NoContent
  }

}
