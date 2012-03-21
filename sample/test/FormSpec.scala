package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class FormSpec extends Specification {

  import controllers.Application.messageForm;

  "Message form" should {
    "require field" in {
      val form = messageForm.bind(Map.empty[String,String])
      form.hasErrors must beTrue
      form.errors.size must equalTo(2)

      form("name").hasErrors must beTrue
      form("message").hasErrors must beTrue
    }

    "set name field" in {
      val form = messageForm.bind(Map("name" -> "test_user"))
      form.hasErrors must beTrue
      form.errors.size must equalTo(1)

      form("name").hasErrors must beFalse
      form("message").hasErrors must beTrue
    }

    "set name and message field" in {
      val form = messageForm.bind(Map("name" -> "test_user",
                                      "message" -> "test message"))
      form.hasErrors must beFalse
      form.errors.size must beEqualTo(0)

      form("name").hasErrors must beFalse
      form("message").hasErrors must beFalse
    }
  }
}


// vim: set ts=4 sw=4 et:
