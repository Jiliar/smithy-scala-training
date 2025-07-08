package exporting

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

final case class ExportDataInput(format: ExportFormat, destinationPath: String)

object ExportDataInput extends ShapeTag.Companion[ExportDataInput] {
  val id: ShapeId = ShapeId("exporting", "ExportDataInput")

  val hints: Hints = Hints.empty

  // constructor using the original order from the spec
  private def make(format: ExportFormat, destinationPath: String): ExportDataInput = ExportDataInput(format, destinationPath)

  implicit val schema: Schema[ExportDataInput] = struct(
    ExportFormat.schema.required[ExportDataInput]("format", _.format),
    string.required[ExportDataInput]("destinationPath", _.destinationPath).addHints(smithy.api.HttpQuery("destination")),
  )(make).withId(id).addHints(hints)
}
