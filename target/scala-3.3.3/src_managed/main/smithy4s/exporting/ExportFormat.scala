package exporting

import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.EnumTag
import smithy4s.schema.Schema.enumeration

sealed abstract class ExportFormat(_value: String, _name: String, _intValue: Int, _hints: Hints) extends Enumeration.Value {
  override type EnumType = ExportFormat
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = _hints
  override def enumeration: Enumeration[EnumType] = ExportFormat
  @inline final def widen: ExportFormat = this
}
object ExportFormat extends Enumeration[ExportFormat] with ShapeTag.Companion[ExportFormat] {
  val id: ShapeId = ShapeId("exporting", "ExportFormat")

  val hints: Hints = Hints.empty

  case object JSON extends ExportFormat("JSON", "JSON", 0, Hints.empty)
  case object CSV extends ExportFormat("CSV", "CSV", 1, Hints.empty)
  case object YAML extends ExportFormat("YAML", "YAML", 2, Hints.empty)
  case object PARQUET extends ExportFormat("Parquet", "PARQUET", 3, Hints.empty)
  case object AVRO extends ExportFormat("Avro", "AVRO", 4, Hints.empty)
  case object PROTOBUF extends ExportFormat("Protobuf", "PROTOBUF", 5, Hints.empty)

  val values: List[ExportFormat] = List(
    JSON,
    CSV,
    YAML,
    PARQUET,
    AVRO,
    PROTOBUF,
  )
  val tag: EnumTag[ExportFormat] = EnumTag.ClosedStringEnum
  implicit val schema: Schema[ExportFormat] = enumeration(tag, values).withId(id).addHints(hints)
}
