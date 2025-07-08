package weather

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

/** @param city
  *   The name of the city to create.
  * @param country
  *   The country where the city is located.
  */
final case class CreateCityInput(city: String, country: String)

object CreateCityInput extends ShapeTag.Companion[CreateCityInput] {
  val id: ShapeId = ShapeId("weather", "CreateCityInput")

  val hints: Hints = Hints(
    smithy.api.Input(),
  ).lazily

  // constructor using the original order from the spec
  private def make(city: String, country: String): CreateCityInput = CreateCityInput(city, country)

  implicit val schema: Schema[CreateCityInput] = struct(
    string.required[CreateCityInput]("city", _.city).addHints(smithy.api.Documentation("The name of the city to create.")),
    string.required[CreateCityInput]("country", _.country).addHints(smithy.api.Documentation("The country where the city is located.")),
  )(make).withId(id).addHints(hints)
}
