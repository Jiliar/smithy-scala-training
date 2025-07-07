package simpleshapes

import smithy4s.Hints
import smithy4s.Newtype
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.string

/** For serialization purposes, equivalent to any other string.
  * Useful for type safety or attaching traits to all references of this shape.
  */
object MyString extends Newtype[String] {
  val id: ShapeId = ShapeId("simpleshapes", "MyString")
  val hints: Hints = Hints(
    smithy.api.Documentation("For serialization purposes, equivalent to any other string.\nUseful for type safety or attaching traits to all references of this shape."),
  ).lazily
  val underlyingSchema: Schema[String] = string.withId(id).addHints(hints).validated(smithy.api.Length(min = Some(1L), max = None))
  implicit val schema: Schema[MyString] = bijection(underlyingSchema, asBijection)
}
