package simpleshapes

import smithy4s.Hints
import smithy4s.Newtype
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.double

object MyDouble extends Newtype[Double] {
  val id: ShapeId = ShapeId("simpleshapes", "MyDouble")
  val hints: Hints = Hints.empty
  val underlyingSchema: Schema[Double] = double.withId(id).addHints(hints)
  implicit val schema: Schema[MyDouble] = bijection(underlyingSchema, asBijection)
}
