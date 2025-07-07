package aggregates

import smithy4s.Hints
import smithy4s.Newtype
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.int
import smithy4s.schema.Schema.map
import smithy4s.schema.Schema.string

object StringToNumber extends Newtype[Map[String, Int]] {
  val id: ShapeId = ShapeId("aggregates", "StringToNumber")
  val hints: Hints = Hints.empty
  val underlyingSchema: Schema[Map[String, Int]] = map(string, int).withId(id).addHints(hints)
  implicit val schema: Schema[StringToNumber] = bijection(underlyingSchema, asBijection)
}
