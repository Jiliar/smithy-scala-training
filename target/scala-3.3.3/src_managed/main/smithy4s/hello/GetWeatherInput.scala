package hello

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

final case class GetWeatherInput(cityId: CityId, region: String = "us-east-1", units: String = "metric")

object GetWeatherInput extends ShapeTag.Companion[GetWeatherInput] {
  val id: ShapeId = ShapeId("hello", "GetWeatherInput")

  val hints: Hints = Hints.empty

  // constructor using the original order from the spec
  private def make(cityId: CityId, region: String, units: String): GetWeatherInput = GetWeatherInput(cityId, region, units)

  implicit val schema: Schema[GetWeatherInput] = struct(
    CityId.schema.required[GetWeatherInput]("cityId", _.cityId).addHints(smithy.api.HttpLabel()),
    string.field[GetWeatherInput]("region", _.region).addHints(smithy.api.Default(smithy4s.Document.fromString("us-east-1")), smithy.api.HttpHeader("X-Region")),
    string.field[GetWeatherInput]("units", _.units).addHints(smithy.api.Default(smithy4s.Document.fromString("metric")), smithy.api.HttpQuery("units")),
  )(make).withId(id).addHints(hints)
}
