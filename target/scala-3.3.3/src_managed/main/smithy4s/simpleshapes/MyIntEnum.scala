package simpleshapes

import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.EnumTag
import smithy4s.schema.Schema.enumeration

/** intEnums are like enums, but represented as integers.
  * Each value of an int enum must explicitly specify its integer value.
  */
sealed abstract class MyIntEnum(_value: String, _name: String, _intValue: Int, _hints: Hints) extends Enumeration.Value {
  override type EnumType = MyIntEnum
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = _hints
  override def enumeration: Enumeration[EnumType] = MyIntEnum
  @inline final def widen: MyIntEnum = this
}
object MyIntEnum extends Enumeration[MyIntEnum] with ShapeTag.Companion[MyIntEnum] {
  val id: ShapeId = ShapeId("simpleshapes", "MyIntEnum")

  val hints: Hints = Hints(
    smithy.api.Documentation("intEnums are like enums, but represented as integers.\nEach value of an int enum must explicitly specify its integer value."),
  ).lazily

  case object HELLO extends MyIntEnum("HELLO", "HELLO", 1, Hints.empty)
  case object GOODBYE extends MyIntEnum("GOODBYE", "GOODBYE", 2, Hints.empty)

  val values: List[MyIntEnum] = List(
    HELLO,
    GOODBYE,
  )
  val tag: EnumTag[MyIntEnum] = EnumTag.ClosedIntEnum
  implicit val schema: Schema[MyIntEnum] = enumeration(tag, values).withId(id).addHints(hints)
}
