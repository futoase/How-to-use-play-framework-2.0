package test

import org.specs2.mutable._
import org.specs2.matcher._

import play.api.test._
import play.api.test.Helpers._

class MessageSpec extends Specification {
  import models._

  "Message Model" should {
    "be get record all" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase("simpledev"))){
        val messages = Message.get()
        messages.map { message => 
          message must haveKey('name)
          message must haveKey('message)
        }
      }
    }

    "be get record of user name keiji matsuzaki" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase("simpledev"))){
        val message = Message.getByName("keiji matsuzaki")
        message.map { m =>
          m must haveKey('name)
          m must haveKey('message)
          m('name) must beEqualTo("keiji matsuzaki")
          m('message) must beEqualTo("書き込みテスト")
        }
      }
    }

    "be get record of message" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase("simpledev"))){
        val message = Message.getInMessage("テスト")
        message.map { m =>
          m must haveKey('name)
          m must haveKey('message)
          m('message).toString must beMatching(".*" + "テスト" + ".*")
        }
      }
    }
  }
}

// vim: set ts=4 sw=4 et:
