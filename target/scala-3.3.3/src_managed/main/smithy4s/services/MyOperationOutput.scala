package services

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

/** @param id
  *   The := syntax (inline input/output) defines a structure shape for more concise definitions.
  *   Most of the time, an input/output is only used once, so there's no need to declare that shape explicitly.
  */
final case class MyOperationOutput(id: String)

object MyOperationOutput extends ShapeTag.Companion[MyOperationOutput] {
  val id: ShapeId = ShapeId("services", "MyOperationOutput")

  val hints: Hints = Hints(
    smithy.api.Output(),
  ).lazily

  // constructor using the original order from the spec
  private def make(id: String): MyOperationOutput = MyOperationOutput(id)

  implicit val schema: Schema[MyOperationOutput] = struct(
    string.required[MyOperationOutput]("id", _.id).addHints(smithy.api.Documentation("The := syntax (inline input/output) defines a structure shape for more concise definitions.\nMost of the time, an input/output is only used once, so there\'s no need to declare that shape explicitly.")),
  )(make).withId(id).addHints(hints)
}
