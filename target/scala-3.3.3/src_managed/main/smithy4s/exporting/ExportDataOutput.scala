package exporting

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.struct

final case class ExportDataOutput(message: String)

object ExportDataOutput extends ShapeTag.Companion[ExportDataOutput] {
  val id: ShapeId = ShapeId("exporting", "ExportDataOutput")

  val hints: Hints = Hints.empty

  // constructor using the original order from the spec
  private def make(message: String): ExportDataOutput = ExportDataOutput(message)

  implicit val schema: Schema[ExportDataOutput] = struct(
    string.required[ExportDataOutput]("message", _.message),
  )(make).withId(id).addHints(hints)
}
