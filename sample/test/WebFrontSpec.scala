package test

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._

import org.fluentlenium.core.filter.FilterConstructor._

class WebFrontSpec extends Specification {
  "WebFront" should {
    "index view within a browser" in {
      running(TestServer(3333, FakeApplication(additionalConfiguration = inMemoryDatabase("simpledev"))), HTMLUNIT) { browser =>
        browser.goTo("http://localhost:3333/")

        // confirm title text
        browser.$("title").first.getText must equalTo("Simple message board - messages")
        
        // click publish button.
        browser.$("input[type=\"submit\"]").click()
        browser.$("title").first.getText must equalTo("Simple message board - messages")

        // publish message
        browser.$("input[type=\"text\"][id=\"name\"]").text("テスト")
        browser.$("textarea[id=\"message\"]").text("テスト入力")
        browser.$("input[type=\"submit\"]").click()
        browser.$("title").first.getText must equalTo("Simple message board - messages")

        // confirm message of publish ok!
        browser.$("div.alert.alert-success").first.getText must equalTo("publish ok!")
      }
    }
  }
}

// vim: set ts=4 sw=4 et:
