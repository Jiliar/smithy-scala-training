package simpleshapes

import smithy4s.Hints
import smithy4s.Newtype
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.byte

object MyByte extends Newtype[Byte] {
  val id: ShapeId = ShapeId("simpleshapes", "MyByte")
  val hints: Hints = Hints.empty
  val underlyingSchema: Schema[Byte] = byte.withId(id).addHints(hints)
  implicit val schema: Schema[MyByte] = bijection(underlyingSchema, asBijection)
}
