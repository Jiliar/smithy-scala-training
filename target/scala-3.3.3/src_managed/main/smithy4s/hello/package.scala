package object hello {
  type WeatherService[F[_]] = smithy4s.kinds.FunctorAlgebra[WeatherServiceGen, F]
  val WeatherService = WeatherServiceGen

  type CityId = hello.CityId.Type

}