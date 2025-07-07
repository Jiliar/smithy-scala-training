package simpleshapes

import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.EnumTag
import smithy4s.schema.Schema.enumeration

/** Enums are strings limited to a set to 1..N values.
  * @param GOOD_AFTERNOON
  *   Custom values can be specified.
  */
sealed abstract class MyEnum(_value: String, _name: String, _intValue: Int, _hints: Hints) extends Enumeration.Value {
  override type EnumType = MyEnum
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = _hints
  override def enumeration: Enumeration[EnumType] = MyEnum
  @inline final def widen: MyEnum = this
}
object MyEnum extends Enumeration[MyEnum] with ShapeTag.Companion[MyEnum] {
  val id: ShapeId = ShapeId("simpleshapes", "MyEnum")

  val hints: Hints = Hints(
    smithy.api.Documentation("Enums are strings limited to a set to 1..N values."),
  ).lazily

  case object HELLO extends MyEnum("HELLO", "HELLO", 0, Hints.empty)
  case object GOODBYE extends MyEnum("GOODBYE", "GOODBYE", 1, Hints.empty)
  /** Custom values can be specified. */
  case object GOOD_AFTERNOON extends MyEnum("good afternoon", "GOOD_AFTERNOON", 2, Hints.empty) {
    override val hints: Hints = Hints(smithy.api.Documentation("Custom values can be specified.")).lazily
  }

  val values: List[MyEnum] = List(
    HELLO,
    GOODBYE,
    GOOD_AFTERNOON,
  )
  val tag: EnumTag[MyEnum] = EnumTag.ClosedStringEnum
  implicit val schema: Schema[MyEnum] = enumeration(tag, values).withId(id).addHints(hints)
}
