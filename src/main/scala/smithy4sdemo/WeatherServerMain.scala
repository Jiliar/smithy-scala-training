package smithy4sdemo

import cats.effect.{IO, IOApp}
import org.http4s.ember.server.EmberServerBuilder
import weather._
import smithy4s.http4s.SimpleRestJsonBuilder

object WeatherServerMain extends IOApp.Simple {

  val impl: WeatherService[IO] = new WeatherService[IO] {

    //http GET 'localhost:8080/cities/123/weather?units=Celcius' X-Region:us-west-2 -v
    def getWeather(cityId: CityId, region: String, units:String): IO[GetWeatherOutput] =
      IO.println(s"Getting weather for city: $cityId, region: $region, units $units") *>
        IO.pure(GetWeatherOutput("Sunny", Some(25), city = Some(
          City(
            id = cityId,
            name = "London",
            country = "UK"
          )
        )))

    //http post localhost:8080/cities city=London -v
    def createCity(
      city: String, 
      country: String
      ): IO[CreateCityOutput] =
      IO.println(s"Creating city: ${city}") *>
        IO.pure(CreateCityOutput(CityId("123")))
        
  }

  val run: IO[Unit] =
    SimpleRestJsonBuilder
      .routes(impl)
      .resource
      .flatMap { routes =>
        EmberServerBuilder.default[IO]
          .withHttpApp(routes.orNotFound)
          .build
      }
      .evalMap { server =>
        IO.println(s"Server started at ${server.address}")
      }
      .useForever
}