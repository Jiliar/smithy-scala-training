$version: "2"
namespace hello

use alloy#simpleRestJson

@simpleRestJson
service WeatherService {
  operations: [GetWeather, CreateCity]
}

@http(method: "GET", uri: "/cities/{cityId}/weather")
@readonly
operation GetWeather {
  input: GetWeatherInput
  output := {
    @required
    weather: String
    @jsonName("temperature")
    degrees: Integer
    city: City
  }
}

structure GetWeatherInput {
  @required
  @httpLabel
  cityId: CityId
  @httpHeader("X-Region")
  region: String = "us-east-1"
  @httpQuery("units")
  units: String = "metric"
}

@http(method: "POST", uri: "/cities", code: 201)
operation CreateCity {
  input := {
     @documentation("The name of the city to create.")
     @required city: String
     @documentation("The country where the city is located.")
     @required country: String
  }
  output := {
     @required cityId: CityId
  }
}

string CityId

structure City{
  @required
  id: CityId
  @required
  name: String
  @required
  country: String
}