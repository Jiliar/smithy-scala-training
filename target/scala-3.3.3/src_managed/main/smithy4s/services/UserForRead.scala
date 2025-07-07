package services

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

final case class UserForRead(name: Option[String] = None)

object UserForRead extends ShapeTag.Companion[UserForRead] {
  val id: ShapeId = ShapeId("services", "UserForRead")

  val hints: Hints = Hints.empty

  // constructor using the original order from the spec
  private def make(name: Option[String]): UserForRead = UserForRead(name)

  implicit val schema: Schema[UserForRead] = struct(
    string.optional[UserForRead]("name", _.name),
  )(make).withId(id).addHints(hints)
}
