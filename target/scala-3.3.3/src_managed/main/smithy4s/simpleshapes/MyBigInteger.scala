package simpleshapes

import smithy4s.Hints
import smithy4s.Newtype
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.schema.Schema.bigint
import smithy4s.schema.Schema.bijection

object MyBigInteger extends Newtype[BigInt] {
  val id: ShapeId = ShapeId("simpleshapes", "MyBigInteger")
  val hints: Hints = Hints.empty
  val underlyingSchema: Schema[BigInt] = bigint.withId(id).addHints(hints)
  implicit val schema: Schema[MyBigInteger] = bijection(underlyingSchema, asBijection)
}
