package hello

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.int
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

final case class GetWeatherOutput(weather: String, degrees: Option[Int] = None, city: Option[City] = None)

object GetWeatherOutput extends ShapeTag.Companion[GetWeatherOutput] {
  val id: ShapeId = ShapeId("hello", "GetWeatherOutput")

  val hints: Hints = Hints(
    smithy.api.Output(),
  ).lazily

  // constructor using the original order from the spec
  private def make(weather: String, degrees: Option[Int], city: Option[City]): GetWeatherOutput = GetWeatherOutput(weather, degrees, city)

  implicit val schema: Schema[GetWeatherOutput] = struct(
    string.required[GetWeatherOutput]("weather", _.weather),
    int.optional[GetWeatherOutput]("degrees", _.degrees).addHints(smithy.api.JsonName("temperature")),
    City.schema.optional[GetWeatherOutput]("city", _.city),
  )(make).withId(id).addHints(hints)
}
