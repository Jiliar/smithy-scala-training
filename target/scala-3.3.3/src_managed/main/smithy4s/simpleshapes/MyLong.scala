package simpleshapes

import smithy4s.Hints
import smithy4s.Newtype
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.long

object MyLong extends Newtype[Long] {
  val id: ShapeId = ShapeId("simpleshapes", "MyLong")
  val hints: Hints = Hints.empty
  val underlyingSchema: Schema[Long] = long.withId(id).addHints(hints)
  implicit val schema: Schema[MyLong] = bijection(underlyingSchema, asBijection)
}
