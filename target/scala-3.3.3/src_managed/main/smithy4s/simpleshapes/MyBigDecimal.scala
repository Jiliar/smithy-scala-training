package simpleshapes

import smithy4s.Hints
import smithy4s.Newtype
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.schema.Schema.bigdecimal
import smithy4s.schema.Schema.bijection

object MyBigDecimal extends Newtype[BigDecimal] {
  val id: ShapeId = ShapeId("simpleshapes", "MyBigDecimal")
  val hints: Hints = Hints.empty
  val underlyingSchema: Schema[BigDecimal] = bigdecimal.withId(id).addHints(hints)
  implicit val schema: Schema[MyBigDecimal] = bijection(underlyingSchema, asBijection)
}
