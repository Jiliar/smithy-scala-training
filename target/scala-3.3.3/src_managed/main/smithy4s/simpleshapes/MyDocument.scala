package simpleshapes

import smithy4s.Document
import smithy4s.Hints
import smithy4s.Newtype
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.document

/** Documents are free-form shapes modelling essentially "schemaless" values.
  * Structurally equivalent to a JSON value (object, number, array, string, boolean, null).
  * In protocols that use JSON, documents are indeed represented as JSON values.
  */
object MyDocument extends Newtype[Document] {
  val id: ShapeId = ShapeId("simpleshapes", "MyDocument")
  val hints: Hints = Hints(
    smithy.api.Documentation("Documents are free-form shapes modelling essentially \"schemaless\" values.\nStructurally equivalent to a JSON value (object, number, array, string, boolean, null).\nIn protocols that use JSON, documents are indeed represented as JSON values."),
  ).lazily
  val underlyingSchema: Schema[Document] = document.withId(id).addHints(hints)
  implicit val schema: Schema[MyDocument] = bijection(underlyingSchema, asBijection)
}
