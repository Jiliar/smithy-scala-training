package aggregates

import simpleshapes.MyString
import simpleshapes.MyTimestamp
import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

/** A structure is a collection of 0..N named members.
  * By default, all members are considered optional, and they can be made required with the `{@literal @}required` trait.
  * @param addressee
  *   Your custom shapes can be used as members!
  * @param createdAt
  *   An instant in time, independent of any locale or timezone.
  *   Can be represented on the wire in multiple ways, e.g. as a string (ISO-8601), or as a number (seconds since epoch).
  *   In many protocols, this behavior can be controlled with the timestampFormat trait.
  * @param identifier
  *   Structure members can have default values: if the input doesn't include a given member, that default can be used.
  */
final case class MyStructure(greeting: String, identifier: String = "", addressee: Option[MyString] = None, createdAt: Option[MyTimestamp] = None)

object MyStructure extends ShapeTag.Companion[MyStructure] {
  val id: ShapeId = ShapeId("aggregates", "MyStructure")

  val hints: Hints = Hints(
    smithy.api.Documentation("A structure is a collection of 0..N named members.\nBy default, all members are considered optional, and they can be made required with the `@required` trait."),
  ).lazily

  // constructor using the original order from the spec
  private def make(greeting: String, addressee: Option[MyString], createdAt: Option[MyTimestamp], identifier: String): MyStructure = MyStructure(greeting, identifier, addressee, createdAt)

  implicit val schema: Schema[MyStructure] = struct(
    string.required[MyStructure]("greeting", _.greeting),
    MyString.schema.optional[MyStructure]("addressee", _.addressee).addHints(smithy.api.Documentation("Your custom shapes can be used as members!")),
    MyTimestamp.schema.optional[MyStructure]("createdAt", _.createdAt),
    string.field[MyStructure]("identifier", _.identifier).addHints(smithy.api.Default(smithy4s.Document.fromString("")), smithy.api.Documentation("Structure members can have default values: if the input doesn\'t include a given member, that default can be used.")),
  )(make).withId(id).addHints(hints)
}
