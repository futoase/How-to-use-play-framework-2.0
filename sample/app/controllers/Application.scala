package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._

import views._
import models._

object Application extends Controller {

  val siteHome = Redirect(routes.Application.index())

  val searchForm = Form(
    mapping(
      "query" -> text
    )(Search.apply)(Search.unapply)
  )

  val messageForm = Form(
    mapping(
      "name" -> text,
      "message" -> nonEmptyText
    )(Message.apply)(Message.unapply)
  )

  def index = Action { implicit request =>
    Ok(html.index("Index", Message.get(), messageForm))
  }

  def save = Action { implicit request =>
    messageForm.bindFromRequest.fold(
      fromWithErrors => BadRequest(html.index("Index", Message.get(), messageForm)),
      message => {
        Message.insert(message)
        siteHome.flashing(
          "success" -> "publish success!"
        )
      }
    )
  }

  def search = Action { implicit request =>
    searchForm.bindFromRequest.fold(
      fromWithErrors => BadRequest(html.index("Index", Message.get(), messageForm)),
      search => {
        val result = Search.get(search)
        Ok(html.search("Search", result))
      }
    )
  }
}
