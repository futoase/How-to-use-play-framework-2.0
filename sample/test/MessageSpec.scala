package test

import org.specs2.mutable._
import org.specs2.matcher._

import play.api.test._
import play.api.test.Helpers._

class MessageSpec extends Specification {
  import models._

  "Message Model" should {
    "be count of record" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase("simpledev"))){
        Message.count().toInt must be_<(3)
      }
    }

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

    "append record of message" in {
      running(FakeApplication(additionalConfiguration = inMemoryDatabase("simpledev"))){
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
          new Message("test_user_10", "おおおおおおごごご")
        )
        records.map { r =>
          Message.insert(r)
        }

        Message.count().toInt must be_<(15)

        val result = Message.getByName("test_user_1")(0)
        result must haveKey('name)
        result must haveKey('message)
        result('name).toString must beMatching("test_user_1")
        result('message).toString must beMatching("テストメッセージ")

        val range_1 = Message.getWithRange(1)
        range_1.length must be_>=(10)

        val range_15 = Message.getWithRange(15)
        range_15.length must be_<(5)
      }
    }
  }
}

// vim: set ts=4 sw=4 et:
