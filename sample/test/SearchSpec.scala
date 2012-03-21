package test

import org.specs2.mutable._
import org.specs2.matcher._

import play.api.test._
import play.api.test.Helpers._

class SearchSpec extends Specification {
  import models._

  "Search Model" should {
    "be search name" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase("simpledev"))){
        val result = Search.get(new Search("keiji matsuzaki"))
        result.map { r =>
          r must haveKey('name)
          r must haveKey('message)
          r('name).toString must beMatching(".*"+"keiji matsuzaki"+".*")
        }
      }
    }

    "be search message" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase("simpledev"))){
        val result = Search.get(new Search("テスト"))
        result.map { r =>
          r must haveKey('name)
          r must haveKey('message)
          r('name).toString must beMatching(".*"+"keiji matsuzaki"+".*")
          r('message).toString must beMatching(".*"+"テスト"+".*")
        }
      }
    }
  }
}

// vim: set ts=4 sw=4 et:
