package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

import models._

class ApplicationSpec extends Specification {
  "Application Controller" should {

    "404 not found response" in {
      running(FakeApplication()) {
        routeAndCall(FakeRequest(GET, "/test")) must beNone
      }
    }

    "page view on /" in {
      running(FakeApplication()) {
        val Some(index) = routeAndCall(FakeRequest(GET, "/"))
        status(index) must equalTo(OK)
        contentType(index) must beSome.which(_ == "text/html")
        contentAsString(index) must contain("Simple message board")
      }
    }

    "pave view on /2" in {
      running(FakeApplication()) {
        val records = List(
          new Message("test_user_1", "テストメッセージ"),
          new Message("test_user_2", "ほげほげほげほげ"),
          new Message("test_user_3", "うひひひひひひひ"),
          new Message("test_user_4", "うごごごごごごご"),
          new Message("test_user_5", "おほほほほほほほ"),
          new Message("test_user_6", "どどどどどどどど"),
          new Message("test_user_7", "うおおおおおおお"),
          new Message("test_user_8", "あああああばばば"),
          new Message("test_user_9", "おおおおおおおお"),
          new Message("test_user_10", "おおおおおおごごご"),
          new Message("test_user_11", "おぼぼぼぼぼぼええ"),
          new Message("test_user_12", "うっほおおおおおおおお"),
          new Message("test_user_13", "おおおおおおおおおぼ"),
          new Message("test_user_14", "エエエエエエエエエエ"),
          new Message("test_user_15", "オオオオフフフフフフ")
        )
        records.map { r =>
          Message.insert(r)
        }

        val Some(index) = routeAndCall(FakeRequest(GET, "/2"))
        status(index) must equalTo(OK)
        contentType(index) must beSome.which(_ == "text/html")
        contentAsString(index) must contain("Simple message board")
      }
    }
  }
}

// vim: set ts=4 sw=4 et:
