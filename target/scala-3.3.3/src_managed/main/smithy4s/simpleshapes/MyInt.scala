package simpleshapes

import smithy4s.Hints
import smithy4s.Newtype
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.int

/** You can also define integers and other numeric types. */
object MyInt extends Newtype[Int] {
  val id: ShapeId = ShapeId("simpleshapes", "MyInt")
  val hints: Hints = Hints(
    smithy.api.Documentation("You can also define integers and other numeric types."),
  ).lazily
  val underlyingSchema: Schema[Int] = int.withId(id).addHints(hints)
  implicit val schema: Schema[MyInt] = bijection(underlyingSchema, asBijection)
}
