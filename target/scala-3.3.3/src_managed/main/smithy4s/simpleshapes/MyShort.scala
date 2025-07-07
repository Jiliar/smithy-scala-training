package simpleshapes

import smithy4s.Hints
import smithy4s.Newtype
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.short

object MyShort extends Newtype[Short] {
  val id: ShapeId = ShapeId("simpleshapes", "MyShort")
  val hints: Hints = Hints.empty
  val underlyingSchema: Schema[Short] = short.withId(id).addHints(hints)
  implicit val schema: Schema[MyShort] = bijection(underlyingSchema, asBijection)
}
