package simpleshapes

import smithy4s.Hints
import smithy4s.Newtype
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.float

object MyFloat extends Newtype[Float] {
  val id: ShapeId = ShapeId("simpleshapes", "MyFloat")
  val hints: Hints = Hints.empty
  val underlyingSchema: Schema[Float] = float.withId(id).addHints(hints)
  implicit val schema: Schema[MyFloat] = bijection(underlyingSchema, asBijection)
}
