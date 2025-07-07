package simpleshapes

import smithy4s.Hints
import smithy4s.Newtype
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.boolean

object MyBoolean extends Newtype[Boolean] {
  val id: ShapeId = ShapeId("simpleshapes", "MyBoolean")
  val hints: Hints = Hints.empty
  val underlyingSchema: Schema[Boolean] = boolean.withId(id).addHints(hints)
  implicit val schema: Schema[MyBoolean] = bijection(underlyingSchema, asBijection)
}
