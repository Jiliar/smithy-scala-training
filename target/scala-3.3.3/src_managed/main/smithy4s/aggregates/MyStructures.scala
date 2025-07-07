package aggregates

import smithy4s.Hints
import smithy4s.Newtype
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.list

/** Sequences are also supported, with list shapes.
  * A list shape must declare exactly one member, with the name "member". That's the shape of the list's elements.
  * By default, all elements are non-nullable. This can be controlled with the `{@literal @}sparse` trait (if the protocol supports it).
  * @param member
  *   A structure is a collection of 0..N named members.
  *   By default, all members are considered optional, and they can be made required with the `{@literal @}required` trait.
  */
object MyStructures extends Newtype[List[MyStructure]] {
  val id: ShapeId = ShapeId("aggregates", "MyStructures")
  val hints: Hints = Hints(
    smithy.api.Documentation("Sequences are also supported, with list shapes.\nA list shape must declare exactly one member, with the name \"member\". That\'s the shape of the list\'s elements.\nBy default, all elements are non-nullable. This can be controlled with the `@sparse` trait (if the protocol supports it)."),
  ).lazily
  val underlyingSchema: Schema[List[MyStructure]] = list(MyStructure.schema).withId(id).addHints(hints)
  implicit val schema: Schema[MyStructures] = bijection(underlyingSchema, asBijection)
}
