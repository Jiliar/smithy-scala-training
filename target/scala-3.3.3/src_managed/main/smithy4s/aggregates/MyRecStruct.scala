package aggregates

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.recursive
import smithy4s.schema.Schema.struct

/** @param u
  *   Aggregate shapes can be (also mutually) recursive.
  *   The only catch is that Smithy must be able to tell that a value of the shape can actually be constructed,
  *   i.e. it doesn't only contain self-recursion.
  * @param self
  *   This wouldn't be allowed as a required field because no value of the struct could be constructed
  */
final case class MyRecStruct(u: MyRecUnion, self: Option[aggregates.MyRecStruct] = None)

object MyRecStruct extends ShapeTag.Companion[MyRecStruct] {
  val id: ShapeId = ShapeId("aggregates", "MyRecStruct")

  val hints: Hints = Hints.empty

  // constructor using the original order from the spec
  private def make(u: MyRecUnion, self: Option[aggregates.MyRecStruct]): MyRecStruct = MyRecStruct(u, self)

  implicit val schema: Schema[MyRecStruct] = recursive(struct(
    MyRecUnion.schema.required[MyRecStruct]("u", _.u),
    aggregates.MyRecStruct.schema.optional[MyRecStruct]("self", _.self).addHints(smithy.api.Documentation("This wouldn\'t be allowed as a required field because no value of the struct could be constructed")),
  )(make).withId(id).addHints(hints))
}
