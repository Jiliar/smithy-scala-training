package hello

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

final case class City(id: CityId, name: String, country: String)

object City extends ShapeTag.Companion[City] {
  val id: ShapeId = ShapeId("hello", "City")

  val hints: Hints = Hints.empty

  // constructor using the original order from the spec
  private def make(id: CityId, name: String, country: String): City = City(id, name, country)

  implicit val schema: Schema[City] = struct(
    CityId.schema.required[City]("id", _.id),
    string.required[City]("name", _.name),
    string.required[City]("country", _.country),
  )(make).withId(id).addHints(hints)
}
