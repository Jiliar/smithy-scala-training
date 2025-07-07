package aggregates

import MyRecUnion.EndCaseAlt
import smithy4s.Hints
import smithy4s.Schema
import smithy4s.ShapeId
import smithy4s.ShapeTag
import smithy4s.schema.Schema.bijection
import smithy4s.schema.Schema.recursive
import smithy4s.schema.Schema.union

/** Aggregate shapes can be (also mutually) recursive.
  * The only catch is that Smithy must be able to tell that a value of the shape can actually be constructed,
  * i.e. it doesn't only contain self-recursion.
  */
sealed trait MyRecUnion extends scala.Product with scala.Serializable { self =>
  @inline final def widen: MyRecUnion = this
  def $ordinal: Int

  object project {
    def rec: Option[MyRecStruct] = MyRecUnion.RecCase.alt.project.lift(self).map(_.rec)
    def end: Option[MyRecUnion.EndCase.type] = EndCaseAlt.project.lift(self)
  }

  def accept[A](visitor: MyRecUnion.Visitor[A]): A = this match {
    case value: MyRecUnion.RecCase => visitor.rec(value.rec)
    case value: MyRecUnion.EndCase.type => visitor.end(value)
  }
}
object MyRecUnion extends ShapeTag.Companion[MyRecUnion] {

  def rec(rec: MyRecStruct): MyRecUnion = RecCase(rec)
  def end(): MyRecUnion = MyRecUnion.EndCase

  val id: ShapeId = ShapeId("aggregates", "MyRecUnion")

  val hints: Hints = Hints(
    smithy.api.Documentation("Aggregate shapes can be (also mutually) recursive.\nThe only catch is that Smithy must be able to tell that a value of the shape can actually be constructed,\ni.e. it doesn\'t only contain self-recursion."),
  ).lazily

  final case class RecCase(rec: MyRecStruct) extends MyRecUnion { final def $ordinal: Int = 0 }
  case object EndCase extends MyRecUnion { final def $ordinal: Int = 1 }
  private val EndCaseAlt = Schema.constant(MyRecUnion.EndCase).oneOf[MyRecUnion]("end").addHints(hints)

  object RecCase {
    val hints: Hints = Hints.empty
    val schema: Schema[MyRecUnion.RecCase] = bijection(MyRecStruct.schema.addHints(hints), MyRecUnion.RecCase(_), _.rec)
    val alt = schema.oneOf[MyRecUnion]("rec")
  }

  trait Visitor[A] {
    def rec(value: MyRecStruct): A
    def end(value: MyRecUnion.EndCase.type): A
  }

  object Visitor {
    trait Default[A] extends Visitor[A] {
      def default: A
      def rec(value: MyRecStruct): A = default
      def end(value: MyRecUnion.EndCase.type): A = default
    }
  }

  implicit val schema: Schema[MyRecUnion] = recursive(union(
    MyRecUnion.RecCase.alt,
    EndCaseAlt,
  ){
    _.$ordinal
  }.withId(id).addHints(hints))
}
