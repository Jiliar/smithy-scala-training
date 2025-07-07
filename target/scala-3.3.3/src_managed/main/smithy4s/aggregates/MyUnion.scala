package aggregates

import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.boolean
import smithy4s.schema.Schema.int
import smithy4s.schema.Schema.string
import smithy4s.schema.Schema.union

/** A union is a collection of 0..N named members, of which only one can be present in the input.
  * Essentially an evolution of enums, but with more flexibility (the possible values can be any shape, not just strings).
  */
sealed trait MyUnion extends scala.Product with scala.Serializable { self =>
  @inline final def widen: MyUnion = this
  def $ordinal: Int

  object project {
    def i: Option[Int] = MyUnion.ICase.alt.project.lift(self).map(_.i)
    def s: Option[String] = MyUnion.SCase.alt.project.lift(self).map(_.s)
    def b: Option[Boolean] = MyUnion.BCase.alt.project.lift(self).map(_.b)
    def ms: Option[MyStructure] = MyUnion.MsCase.alt.project.lift(self).map(_.ms)
  }

  def accept[A](visitor: MyUnion.Visitor[A]): A = this match {
    case value: MyUnion.ICase => visitor.i(value.i)
    case value: MyUnion.SCase => visitor.s(value.s)
    case value: MyUnion.BCase => visitor.b(value.b)
    case value: MyUnion.MsCase => visitor.ms(value.ms)
  }
}
object MyUnion extends ShapeTag.Companion[MyUnion] {

  /** By default, most protocols encode unions as tagged unions, where the tag is the member name.
    * For example, a possible encoding of the "i" case with the integer 42 could be:
    * { "i": 42 }
    */
  def i(i: Int): MyUnion = ICase(i)
  def s(s: String): MyUnion = SCase(s)
  def b(b: Boolean): MyUnion = BCase(b)
  def ms(ms: MyStructure): MyUnion = MsCase(ms)

  val id: ShapeId = ShapeId("aggregates", "MyUnion")

  val hints: Hints = Hints(
    smithy.api.Documentation("A union is a collection of 0..N named members, of which only one can be present in the input.\nEssentially an evolution of enums, but with more flexibility (the possible values can be any shape, not just strings)."),
  ).lazily

  /** By default, most protocols encode unions as tagged unions, where the tag is the member name.
    * For example, a possible encoding of the "i" case with the integer 42 could be:
    * { "i": 42 }
    */
  final case class ICase(i: Int) extends MyUnion { final def $ordinal: Int = 0 }
  final case class SCase(s: String) extends MyUnion { final def $ordinal: Int = 1 }
  final case class BCase(b: Boolean) extends MyUnion { final def $ordinal: Int = 2 }
  final case class MsCase(ms: MyStructure) extends MyUnion { final def $ordinal: Int = 3 }

  object ICase {
    val hints: Hints = Hints(
      smithy.api.Documentation("By default, most protocols encode unions as tagged unions, where the tag is the member name.\nFor example, a possible encoding of the \"i\" case with the integer 42 could be:\n{ \"i\": 42 }"),
    ).lazily
    val schema: Schema[MyUnion.ICase] = bijection(int.addHints(hints), MyUnion.ICase(_), _.i)
    val alt = schema.oneOf[MyUnion]("i")
  }
  object SCase {
    val hints: Hints = Hints.empty
    val schema: Schema[MyUnion.SCase] = bijection(string.addHints(hints), MyUnion.SCase(_), _.s)
    val alt = schema.oneOf[MyUnion]("s")
  }
  object BCase {
    val hints: Hints = Hints.empty
    val schema: Schema[MyUnion.BCase] = bijection(boolean.addHints(hints), MyUnion.BCase(_), _.b)
    val alt = schema.oneOf[MyUnion]("b")
  }
  object MsCase {
    val hints: Hints = Hints.empty
    val schema: Schema[MyUnion.MsCase] = bijection(MyStructure.schema.addHints(hints), MyUnion.MsCase(_), _.ms)
    val alt = schema.oneOf[MyUnion]("ms")
  }

  trait Visitor[A] {
    def i(value: Int): A
    def s(value: String): A
    def b(value: Boolean): A
    def ms(value: MyStructure): A
  }

  object Visitor {
    trait Default[A] extends Visitor[A] {
      def default: A
      def i(value: Int): A = default
      def s(value: String): A = default
      def b(value: Boolean): A = default
      def ms(value: MyStructure): A = default
    }
  }

  implicit val schema: Schema[MyUnion] = union(
    MyUnion.ICase.alt,
    MyUnion.SCase.alt,
    MyUnion.BCase.alt,
    MyUnion.MsCase.alt,
  ){
    _.$ordinal
  }.withId(id).addHints(hints)
}
