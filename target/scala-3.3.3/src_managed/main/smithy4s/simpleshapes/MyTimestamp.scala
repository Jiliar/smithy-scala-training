package simpleshapes

import smithy4s.Hints
import smithy4s.Newtype
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.Timestamp
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.timestamp

/** An instant in time, independent of any locale or timezone.
  * Can be represented on the wire in multiple ways, e.g. as a string (ISO-8601), or as a number (seconds since epoch).
  * In many protocols, this behavior can be controlled with the timestampFormat trait.
  */
object MyTimestamp extends Newtype[Timestamp] {
  val id: ShapeId = ShapeId("simpleshapes", "MyTimestamp")
  val hints: Hints = Hints(
    smithy.api.Documentation("An instant in time, independent of any locale or timezone.\nCan be represented on the wire in multiple ways, e.g. as a string (ISO-8601), or as a number (seconds since epoch).\nIn many protocols, this behavior can be controlled with the timestampFormat trait."),
    smithy.api.TimestampFormat.DATE_TIME.widen,
  ).lazily
  val underlyingSchema: Schema[Timestamp] = timestamp.withId(id).addHints(hints)
  implicit val schema: Schema[MyTimestamp] = bijection(underlyingSchema, asBijection)
}
