package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

import java.text.SimpleDateFormat
import java.text.DateFormat
import java.util.{Date}

trait TimeFormat {
  def date_convert(date: Date): String = {
    (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(date)
  }
}

case class Message (name: String, message: String)

object Message extends TimeFormat{

  def count(): Long = {
    DB.withConnection("simpledev") { implicit connection =>
      SQL(
        """
          SELECT COUNT(`id`) as `count` FROM `Messages`;
        """
      ).apply().head[Long]("count")
    }
  }

  def get(): List[Map[Symbol,Any]] = {
    DB.withConnection("simpledev") { implicit connection =>
      SQL(
        """
          SELECT `id`, `name`, `message`, 
                 `created_at`, `updated_at`
            FROM `Messages` ORDER BY `updated_at` DESC;
        """
      )().map(row => 
        Map('id -> row[Int]("id"), 
            'name -> row[String]("name"),
            'message -> row[String]("message"), 
            'created_at -> date_convert(row[Date]("created_at")),
            'updated_at -> date_convert(row[Date]("updated_at"))
        )
      ).toList
    } 
  }

  def getWithRange(page: Int): List[Map[Symbol,Any]] = {

    val pageNo = page match {
      case x if x > 0 => page
      case _          => 1
    }

    DB.withConnection("simpledev") { implicit connection =>
      SQL(
        """
          SELECT `id`, `name`, `message`, 
                 `created_at`, `updated_at`
            FROM `Messages` ORDER BY `updated_at` DESC
            LIMIT {page},10;
        """
      ).on("page" -> (pageNo - 1) * 10)().map(row => 
        Map('id -> row[Int]("id"), 
            'name -> row[String]("name"),
            'message -> row[String]("message"), 
            'created_at -> date_convert(row[Date]("created_at")),
            'updated_at -> date_convert(row[Date]("updated_at"))
        )
      ).toList
    }
  }

  def getInMessage(message: String): List[Map[Symbol,Any]] = {
    DB.withConnection("simpledev") {implicit connection =>
      SQL(
        """
           SELECT `id`, `name`, `message`,
                  `created_at`, `updated_at`
             FROM `Messages` WHERE `message` LIKE {message}
             ORDER BY `updated_at` DESC;
        """
      ).on("message" -> ("%"+message+"%"))().map (row =>
        Map('id -> row[Int]("id"),
            'name -> row[String]("name"),
            'message -> row[String]("message"),
            'created_at -> row[Date]("created_at").toString,
            'updated_at -> row[Date]("updated_at").toString
        )
      ).toList
    }
  }

  def getByName(name: String): List[Map[Symbol,Any]] = {
    DB.withConnection("simpledev") {implicit connection =>
      SQL(
        """
           SELECT `id`, `name`, `message`, 
                  `created_at`, `updated_at`
             FROM `Messages` WHERE `name` = {name}
             ORDER BY `updated_at` DESC;
        """
      ).on("name" -> name
      )().map(row =>
        Map('id -> row[Int]("id"),
            'name -> row[String]("name"),
            'message -> row[String]("message"),
            'created_at -> row[Date]("created_at").toString,
            'updated_at -> row[Date]("updated_at").toString
          )
      ).toList
    }
  }

  def insert(message: Message) = {
    val name = message.name match {
      case x if x.length == 0 => "名無しさん"
      case _ => message.name
    }

    DB.withConnection("simpledev") { implicit connection =>
      SQL(
        """
          INSERT INTO Messages 
            (name, message, created_at, updated_at)
          VALUES
            ({name}, {message}, NOW(), NOW())
        """
      ).on(
        'name -> name,
        'message -> message.message
      ).executeUpdate()
    }
  }

}

// vim: set ts=4 sw=4 et:
