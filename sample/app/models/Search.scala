package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

import java.util.{Date}

case class Search(word: String);

object Search {
  
  def get(word: String): List[Map[Symbol,Any]] = {
    DB.withConnection("simpledev") { implicit connection =>
      SQL(
        """
           SELECT `id`, `name`, `message`,
                  `created_at`, `updated_at`
             FROM `messages` 
             WHERE `name` LIKE {name} 
                OR `message` LIKE {message}
        """
      ).on("name" -> ("%"+word+"%"),
           "message" -> ("%"+word+"%")
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
}

// vim: set ts=4 sw=4 et:
