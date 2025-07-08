package smithy4sdemo

import cats.effect.{IO, IOApp}
import org.http4s.ember.client.EmberClientBuilder
import smithy4s.http4s.SimpleRestJsonBuilder
import weather._

object WeatherClientMain extends IOApp.Simple {

    def run: IO[Unit] = EmberClientBuilder.default[IO]
      .build
      .use { c =>
            SimpleRestJsonBuilder(WeatherService)
              .client(c)
              .resource
              .use { client =>
                client.createCity("Barcelona", "Spain").flatMap { output =>
                  IO.println(s"Created city with ID: ${output.cityId.value}") *>
                    client.createCity("london", "UK")
                      .flatMap { output =>
                        client
                            .getWeather(CityId(output.cityId.value))
                            .flatMap(IO.println(_))
                      }
                }
              }
      }
}