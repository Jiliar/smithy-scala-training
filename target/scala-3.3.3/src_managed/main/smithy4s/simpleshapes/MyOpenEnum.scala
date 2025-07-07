package simpleshapes

import smithy4s.Enumeration
import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.EnumTag
import smithy4s.schema.Schema.enumeration

/** @param GOOD_AFTERNOON
  *   Custom values can be specified.
  */
sealed abstract class MyOpenEnum(_value: String, _name: String, _intValue: Int, _hints: Hints) extends Enumeration.Value {
  override type EnumType = MyOpenEnum
  override val value: String = _value
  override val name: String = _name
  override val intValue: Int = _intValue
  override val hints: Hints = _hints
  override def enumeration: Enumeration[EnumType] = MyOpenEnum
  @inline final def widen: MyOpenEnum = this
}
object MyOpenEnum extends Enumeration[MyOpenEnum] with ShapeTag.Companion[MyOpenEnum] {
  val id: ShapeId = ShapeId("simpleshapes", "MyOpenEnum")

  val hints: Hints = Hints(
    alloy.OpenEnum(),
  ).lazily

  case object HELLO extends MyOpenEnum("HELLO", "HELLO", 0, Hints.empty)
  case object GOODBYE extends MyOpenEnum("GOODBYE", "GOODBYE", 1, Hints.empty)
  /** Custom values can be specified. */
  case object GOOD_AFTERNOON extends MyOpenEnum("good afternoon", "GOOD_AFTERNOON", 2, Hints.empty) {
    override val hints: Hints = Hints(smithy.api.Documentation("Custom values can be specified.")).lazily
  }
  final case class $Unknown(str: String) extends MyOpenEnum(str, "$Unknown", -1, Hints.empty)

  val $unknown: String => MyOpenEnum = $Unknown(_)

  def fromStringOrUnknown(s: String): MyOpenEnum = fromString(s).getOrElse($unknown(s))

  val values: List[MyOpenEnum] = List(
    HELLO,
    GOODBYE,
    GOOD_AFTERNOON,
  )
  val tag: EnumTag[MyOpenEnum] = EnumTag.OpenStringEnum($unknown)
  implicit val schema: Schema[MyOpenEnum] = enumeration(tag, values).withId(id).addHints(hints)
}
