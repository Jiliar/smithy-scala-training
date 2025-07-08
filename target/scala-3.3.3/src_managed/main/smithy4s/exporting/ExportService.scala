package exporting

import smithy4s.Endpoint
import smithy4s.Hints
import smithy4s.Schema
import smithy4s.Service
import smithy4s.ShapeId
import smithy4s.Transformation
import smithy4s.kinds.PolyFunction5
import smithy4s.kinds.toPolyFunction5.const5
import smithy4s.schema.OperationSchema

trait ExportServiceGen[F[_, _, _, _, _]] {
  self =>

  def exportData(format: ExportFormat, destinationPath: String): F[ExportDataInput, Nothing, ExportDataOutput, Nothing, Nothing]

  final def transform: Transformation.PartiallyApplied[ExportServiceGen[F]] = Transformation.of[ExportServiceGen[F]](this)
}

object ExportServiceGen extends Service.Mixin[ExportServiceGen, ExportServiceOperation] {

  val id: ShapeId = ShapeId("exporting", "ExportService")
  val version: String = "1.0"

  val hints: Hints = Hints(
    alloy.SimpleRestJson(),
  ).lazily

  def apply[F[_]](implicit F: Impl[F]): F.type = F

  object ErrorAware {
    def apply[F[_, _]](implicit F: ErrorAware[F]): F.type = F
    type Default[F[+_, +_]] = Constant[smithy4s.kinds.stubs.Kind2[F]#toKind5]
  }

  val endpoints: Vector[smithy4s.Endpoint[ExportServiceOperation, ?, ?, ?, ?, ?]] = Vector(
    ExportServiceOperation.ExportData,
  )

  def input[I, E, O, SI, SO](op: ExportServiceOperation[I, E, O, SI, SO]): I = op.input
  def ordinal[I, E, O, SI, SO](op: ExportServiceOperation[I, E, O, SI, SO]): Int = op.ordinal
  override def endpoint[I, E, O, SI, SO](op: ExportServiceOperation[I, E, O, SI, SO]) = op.endpoint
  class Constant[P[-_, +_, +_, +_, +_]](value: P[Any, Nothing, Nothing, Nothing, Nothing]) extends ExportServiceOperation.Transformed[ExportServiceOperation, P](reified, const5(value))
  type Default[F[+_]] = Constant[smithy4s.kinds.stubs.Kind1[F]#toKind5]
  def reified: ExportServiceGen[ExportServiceOperation] = ExportServiceOperation.reified
  def mapK5[P[_, _, _, _, _], P1[_, _, _, _, _]](alg: ExportServiceGen[P], f: PolyFunction5[P, P1]): ExportServiceGen[P1] = new ExportServiceOperation.Transformed(alg, f)
  def fromPolyFunction[P[_, _, _, _, _]](f: PolyFunction5[ExportServiceOperation, P]): ExportServiceGen[P] = new ExportServiceOperation.Transformed(reified, f)
  def toPolyFunction[P[_, _, _, _, _]](impl: ExportServiceGen[P]): PolyFunction5[ExportServiceOperation, P] = ExportServiceOperation.toPolyFunction(impl)

}

sealed trait ExportServiceOperation[Input, Err, Output, StreamedInput, StreamedOutput] {
  def run[F[_, _, _, _, _]](impl: ExportServiceGen[F]): F[Input, Err, Output, StreamedInput, StreamedOutput]
  def ordinal: Int
  def input: Input
  def endpoint: Endpoint[ExportServiceOperation, Input, Err, Output, StreamedInput, StreamedOutput]
}

object ExportServiceOperation {

  object reified extends ExportServiceGen[ExportServiceOperation] {
    def exportData(format: ExportFormat, destinationPath: String): ExportData = ExportData(ExportDataInput(format, destinationPath))
  }
  class Transformed[P[_, _, _, _, _], P1[_ ,_ ,_ ,_ ,_]](alg: ExportServiceGen[P], f: PolyFunction5[P, P1]) extends ExportServiceGen[P1] {
    def exportData(format: ExportFormat, destinationPath: String): P1[ExportDataInput, Nothing, ExportDataOutput, Nothing, Nothing] = f[ExportDataInput, Nothing, ExportDataOutput, Nothing, Nothing](alg.exportData(format, destinationPath))
  }

  def toPolyFunction[P[_, _, _, _, _]](impl: ExportServiceGen[P]): PolyFunction5[ExportServiceOperation, P] = new PolyFunction5[ExportServiceOperation, P] {
    def apply[I, E, O, SI, SO](op: ExportServiceOperation[I, E, O, SI, SO]): P[I, E, O, SI, SO] = op.run(impl) 
  }
  final case class ExportData(input: ExportDataInput) extends ExportServiceOperation[ExportDataInput, Nothing, ExportDataOutput, Nothing, Nothing] {
    def run[F[_, _, _, _, _]](impl: ExportServiceGen[F]): F[ExportDataInput, Nothing, ExportDataOutput, Nothing, Nothing] = impl.exportData(input.format, input.destinationPath)
    def ordinal: Int = 0
    def endpoint: smithy4s.Endpoint[ExportServiceOperation,ExportDataInput, Nothing, ExportDataOutput, Nothing, Nothing] = ExportData
  }
  object ExportData extends smithy4s.Endpoint[ExportServiceOperation,ExportDataInput, Nothing, ExportDataOutput, Nothing, Nothing] {
    val schema: OperationSchema[ExportDataInput, Nothing, ExportDataOutput, Nothing, Nothing] = Schema.operation(ShapeId("exporting", "ExportData"))
      .withInput(ExportDataInput.schema)
      .withOutput(ExportDataOutput.schema)
      .withHints(smithy.api.Http(method = smithy.api.NonEmptyString("POST"), uri = smithy.api.NonEmptyString("/export"), code = 200))
    def wrap(input: ExportDataInput): ExportData = ExportData(input)
  }
}

