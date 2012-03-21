package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

class ApplicationSpec extends Specification {
  "Application Controller" should {

    "404 not found response" in {
      running(FakeApplication()) {
        routeAndCall(FakeRequest(GET, "/test")) must beNone
      }
    }

    "page view on /" in {
      running(FakeApplication()) {
        val index = routeAndCall(FakeRequest(GET, "/")).get
        status(index) must equalTo(OK)
        contentType(index) must beSome.which(_ == "text/html")
        contentAsString(index) must contain("Short message board")
      }
    }
  }
}

// vim: set ts=4 sw=4 et:
