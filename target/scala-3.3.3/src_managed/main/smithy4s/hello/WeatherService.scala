package hello

import smithy4s.Endpoint
import smithy4s.Hints
import smithy4s.Schema
import smithy4s.Service
import smithy4s.ShapeId
import smithy4s.Transformation
import smithy4s.kinds.PolyFunction5
import smithy4s.kinds.toPolyFunction5.const5
import smithy4s.schema.OperationSchema

trait WeatherServiceGen[F[_, _, _, _, _]] {
  self =>

  def getWeather(cityId: CityId, region: String = "us-east-1", units: String = "metric"): F[GetWeatherInput, Nothing, GetWeatherOutput, Nothing, Nothing]
  /** @param city
    *   The name of the city to create.
    * @param country
    *   The country where the city is located.
    */
  def createCity(city: String, country: String): F[CreateCityInput, Nothing, CreateCityOutput, Nothing, Nothing]

  final def transform: Transformation.PartiallyApplied[WeatherServiceGen[F]] = Transformation.of[WeatherServiceGen[F]](this)
}

object WeatherServiceGen extends Service.Mixin[WeatherServiceGen, WeatherServiceOperation] {

  val id: ShapeId = ShapeId("hello", "WeatherService")
  val version: String = ""

  val hints: Hints = Hints(
    alloy.SimpleRestJson(),
  ).lazily

  def apply[F[_]](implicit F: Impl[F]): F.type = F

  object ErrorAware {
    def apply[F[_, _]](implicit F: ErrorAware[F]): F.type = F
    type Default[F[+_, +_]] = Constant[smithy4s.kinds.stubs.Kind2[F]#toKind5]
  }

  val endpoints: Vector[smithy4s.Endpoint[WeatherServiceOperation, ?, ?, ?, ?, ?]] = Vector(
    WeatherServiceOperation.GetWeather,
    WeatherServiceOperation.CreateCity,
  )

  def input[I, E, O, SI, SO](op: WeatherServiceOperation[I, E, O, SI, SO]): I = op.input
  def ordinal[I, E, O, SI, SO](op: WeatherServiceOperation[I, E, O, SI, SO]): Int = op.ordinal
  override def endpoint[I, E, O, SI, SO](op: WeatherServiceOperation[I, E, O, SI, SO]) = op.endpoint
  class Constant[P[-_, +_, +_, +_, +_]](value: P[Any, Nothing, Nothing, Nothing, Nothing]) extends WeatherServiceOperation.Transformed[WeatherServiceOperation, P](reified, const5(value))
  type Default[F[+_]] = Constant[smithy4s.kinds.stubs.Kind1[F]#toKind5]
  def reified: WeatherServiceGen[WeatherServiceOperation] = WeatherServiceOperation.reified
  def mapK5[P[_, _, _, _, _], P1[_, _, _, _, _]](alg: WeatherServiceGen[P], f: PolyFunction5[P, P1]): WeatherServiceGen[P1] = new WeatherServiceOperation.Transformed(alg, f)
  def fromPolyFunction[P[_, _, _, _, _]](f: PolyFunction5[WeatherServiceOperation, P]): WeatherServiceGen[P] = new WeatherServiceOperation.Transformed(reified, f)
  def toPolyFunction[P[_, _, _, _, _]](impl: WeatherServiceGen[P]): PolyFunction5[WeatherServiceOperation, P] = WeatherServiceOperation.toPolyFunction(impl)

}

sealed trait WeatherServiceOperation[Input, Err, Output, StreamedInput, StreamedOutput] {
  def run[F[_, _, _, _, _]](impl: WeatherServiceGen[F]): F[Input, Err, Output, StreamedInput, StreamedOutput]
  def ordinal: Int
  def input: Input
  def endpoint: Endpoint[WeatherServiceOperation, Input, Err, Output, StreamedInput, StreamedOutput]
}

object WeatherServiceOperation {

  object reified extends WeatherServiceGen[WeatherServiceOperation] {
    def getWeather(cityId: CityId, region: String = "us-east-1", units: String = "metric"): GetWeather = GetWeather(GetWeatherInput(cityId, region, units))
    def createCity(city: String, country: String): CreateCity = CreateCity(CreateCityInput(city, country))
  }
  class Transformed[P[_, _, _, _, _], P1[_ ,_ ,_ ,_ ,_]](alg: WeatherServiceGen[P], f: PolyFunction5[P, P1]) extends WeatherServiceGen[P1] {
    def getWeather(cityId: CityId, region: String = "us-east-1", units: String = "metric"): P1[GetWeatherInput, Nothing, GetWeatherOutput, Nothing, Nothing] = f[GetWeatherInput, Nothing, GetWeatherOutput, Nothing, Nothing](alg.getWeather(cityId, region, units))
    def createCity(city: String, country: String): P1[CreateCityInput, Nothing, CreateCityOutput, Nothing, Nothing] = f[CreateCityInput, Nothing, CreateCityOutput, Nothing, Nothing](alg.createCity(city, country))
  }

  def toPolyFunction[P[_, _, _, _, _]](impl: WeatherServiceGen[P]): PolyFunction5[WeatherServiceOperation, P] = new PolyFunction5[WeatherServiceOperation, P] {
    def apply[I, E, O, SI, SO](op: WeatherServiceOperation[I, E, O, SI, SO]): P[I, E, O, SI, SO] = op.run(impl) 
  }
  final case class GetWeather(input: GetWeatherInput) extends WeatherServiceOperation[GetWeatherInput, Nothing, GetWeatherOutput, Nothing, Nothing] {
    def run[F[_, _, _, _, _]](impl: WeatherServiceGen[F]): F[GetWeatherInput, Nothing, GetWeatherOutput, Nothing, Nothing] = impl.getWeather(input.cityId, input.region, input.units)
    def ordinal: Int = 0
    def endpoint: smithy4s.Endpoint[WeatherServiceOperation,GetWeatherInput, Nothing, GetWeatherOutput, Nothing, Nothing] = GetWeather
  }
  object GetWeather extends smithy4s.Endpoint[WeatherServiceOperation,GetWeatherInput, Nothing, GetWeatherOutput, Nothing, Nothing] {
    val schema: OperationSchema[GetWeatherInput, Nothing, GetWeatherOutput, Nothing, Nothing] = Schema.operation(ShapeId("hello", "GetWeather"))
      .withInput(GetWeatherInput.schema)
      .withOutput(GetWeatherOutput.schema)
      .withHints(smithy.api.Http(method = smithy.api.NonEmptyString("GET"), uri = smithy.api.NonEmptyString("/cities/{cityId}/weather"), code = 200), smithy.api.Readonly())
    def wrap(input: GetWeatherInput): GetWeather = GetWeather(input)
  }
  final case class CreateCity(input: CreateCityInput) extends WeatherServiceOperation[CreateCityInput, Nothing, CreateCityOutput, Nothing, Nothing] {
    def run[F[_, _, _, _, _]](impl: WeatherServiceGen[F]): F[CreateCityInput, Nothing, CreateCityOutput, Nothing, Nothing] = impl.createCity(input.city, input.country)
    def ordinal: Int = 1
    def endpoint: smithy4s.Endpoint[WeatherServiceOperation,CreateCityInput, Nothing, CreateCityOutput, Nothing, Nothing] = CreateCity
  }
  object CreateCity extends smithy4s.Endpoint[WeatherServiceOperation,CreateCityInput, Nothing, CreateCityOutput, Nothing, Nothing] {
    val schema: OperationSchema[CreateCityInput, Nothing, CreateCityOutput, Nothing, Nothing] = Schema.operation(ShapeId("hello", "CreateCity"))
      .withInput(CreateCityInput.schema)
      .withOutput(CreateCityOutput.schema)
      .withHints(smithy.api.Http(method = smithy.api.NonEmptyString("POST"), uri = smithy.api.NonEmptyString("/cities"), code = 201))
    def wrap(input: CreateCityInput): CreateCity = CreateCity(input)
  }
}

