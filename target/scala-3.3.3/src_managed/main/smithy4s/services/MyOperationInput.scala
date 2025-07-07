package services

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

final case class MyOperationInput(greeting: String)

object MyOperationInput extends ShapeTag.Companion[MyOperationInput] {
  val id: ShapeId = ShapeId("services", "MyOperationInput")

  val hints: Hints = Hints.empty

  // constructor using the original order from the spec
  private def make(greeting: String): MyOperationInput = MyOperationInput(greeting)

  implicit val schema: Schema[MyOperationInput] = struct(
    string.required[MyOperationInput]("greeting", _.greeting),
  )(make).withId(id).addHints(hints)
}
