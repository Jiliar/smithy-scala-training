package services

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.struct

final case class GetUserInput(userId: UserId)

object GetUserInput extends ShapeTag.Companion[GetUserInput] {
  val id: ShapeId = ShapeId("services", "GetUserInput")

  val hints: Hints = Hints(
    smithy.api.Input(),
  ).lazily

  // constructor using the original order from the spec
  private def make(userId: UserId): GetUserInput = GetUserInput(userId)

  implicit val schema: Schema[GetUserInput] = struct(
    UserId.schema.required[GetUserInput]("userId", _.userId),
  )(make).withId(id).addHints(hints)
}
