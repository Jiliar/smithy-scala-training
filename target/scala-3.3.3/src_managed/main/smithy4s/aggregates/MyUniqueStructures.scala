package aggregates

import smithy4s.Hints
import smithy4s.Newtype
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.set

/** Other kinds of sequences are supported, too.
  * In order to get a "Set" shape, you can use the `{@literal @}uniqueItems` trait.
  * In smithy4s, there's also `{@literal @}vector` and `{@literal @}indexedSeq` for other kinds of sequences.
  * @param member
  *   A structure is a collection of 0..N named members.
  *   By default, all members are considered optional, and they can be made required with the `{@literal @}required` trait.
  */
object MyUniqueStructures extends Newtype[Set[MyStructure]] {
  val id: ShapeId = ShapeId("aggregates", "MyUniqueStructures")
  val hints: Hints = Hints(
    smithy.api.Documentation("Other kinds of sequences are supported, too.\nIn order to get a \"Set\" shape, you can use the `@uniqueItems` trait.\nIn smithy4s, there\'s also `@vector` and `@indexedSeq` for other kinds of sequences."),
    smithy.api.UniqueItems(),
  ).lazily
  val underlyingSchema: Schema[Set[MyStructure]] = set(MyStructure.schema).withId(id).addHints(hints)
  implicit val schema: Schema[MyUniqueStructures] = bijection(underlyingSchema, asBijection)
}
