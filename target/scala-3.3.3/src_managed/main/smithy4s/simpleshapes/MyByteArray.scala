package simpleshapes

import smithy4s.Blob
import smithy4s.Hints
import smithy4s.Newtype
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.bytes

/** Byte arrays are also supported.
  * These are usually represented as raw bytes, or (if need be), "escaped" as a base64-encoded string (e.g. in JSON).
  */
object MyByteArray extends Newtype[Blob] {
  val id: ShapeId = ShapeId("simpleshapes", "MyByteArray")
  val hints: Hints = Hints(
    smithy.api.Documentation("Byte arrays are also supported.\nThese are usually represented as raw bytes, or (if need be), \"escaped\" as a base64-encoded string (e.g. in JSON)."),
  ).lazily
  val underlyingSchema: Schema[Blob] = bytes.withId(id).addHints(hints)
  implicit val schema: Schema[MyByteArray] = bijection(underlyingSchema, asBijection)
}
