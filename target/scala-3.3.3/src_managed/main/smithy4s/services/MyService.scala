package services

import smithy4s.Endpoint
import smithy4s.Hints
import smithy4s.Schema
import smithy4s.Service
import smithy4s.ShapeId
import smithy4s.Transformation
import smithy4s.kinds.PolyFunction5
import smithy4s.kinds.toPolyFunction5.const5
import smithy4s.schema.OperationSchema
import smithy4s.schema.Schema.unit

/** Service shapes (the real ones :)) are logical grouping of operations.
  * If operation shapes are function signatures, services are interface definitions that declare these functions.
  */
trait MyServiceGen[F[_, _, _, _, _]] {
  self =>

  /** An operation is a single action that can be performed by a service (usually one service, although you're free to reuse operations).
    * It can be thought of as a function signature, with input and output shapes.
    */
  def myOperation(greeting: String): F[MyOperationInput, Nothing, MyOperationOutput, Nothing, Nothing]
  def getUser(userId: UserId): F[GetUserInput, Nothing, UserForRead, Nothing, Nothing]
  def myOtherOperation(): F[Unit, Nothing, Unit, Nothing, Nothing]

  final def transform: Transformation.PartiallyApplied[MyServiceGen[F]] = Transformation.of[MyServiceGen[F]](this)
}

object MyServiceGen extends Service.Mixin[MyServiceGen, MyServiceOperation] {

  val id: ShapeId = ShapeId("services", "MyService")
  val version: String = "1.0"

  val hints: Hints = Hints(
    smithy.api.Documentation("Service shapes (the real ones :)) are logical grouping of operations.\nIf operation shapes are function signatures, services are interface definitions that declare these functions."),
  ).lazily

  def apply[F[_]](implicit F: Impl[F]): F.type = F

  object ErrorAware {
    def apply[F[_, _]](implicit F: ErrorAware[F]): F.type = F
    type Default[F[+_, +_]] = Constant[smithy4s.kinds.stubs.Kind2[F]#toKind5]
  }

  val endpoints: Vector[smithy4s.Endpoint[MyServiceOperation, ?, ?, ?, ?, ?]] = Vector(
    MyServiceOperation.MyOperation,
    MyServiceOperation.GetUser,
    MyServiceOperation.MyOtherOperation,
  )

  def input[I, E, O, SI, SO](op: MyServiceOperation[I, E, O, SI, SO]): I = op.input
  def ordinal[I, E, O, SI, SO](op: MyServiceOperation[I, E, O, SI, SO]): Int = op.ordinal
  override def endpoint[I, E, O, SI, SO](op: MyServiceOperation[I, E, O, SI, SO]) = op.endpoint
  class Constant[P[-_, +_, +_, +_, +_]](value: P[Any, Nothing, Nothing, Nothing, Nothing]) extends MyServiceOperation.Transformed[MyServiceOperation, P](reified, const5(value))
  type Default[F[+_]] = Constant[smithy4s.kinds.stubs.Kind1[F]#toKind5]
  def reified: MyServiceGen[MyServiceOperation] = MyServiceOperation.reified
  def mapK5[P[_, _, _, _, _], P1[_, _, _, _, _]](alg: MyServiceGen[P], f: PolyFunction5[P, P1]): MyServiceGen[P1] = new MyServiceOperation.Transformed(alg, f)
  def fromPolyFunction[P[_, _, _, _, _]](f: PolyFunction5[MyServiceOperation, P]): MyServiceGen[P] = new MyServiceOperation.Transformed(reified, f)
  def toPolyFunction[P[_, _, _, _, _]](impl: MyServiceGen[P]): PolyFunction5[MyServiceOperation, P] = MyServiceOperation.toPolyFunction(impl)

}

sealed trait MyServiceOperation[Input, Err, Output, StreamedInput, StreamedOutput] {
  def run[F[_, _, _, _, _]](impl: MyServiceGen[F]): F[Input, Err, Output, StreamedInput, StreamedOutput]
  def ordinal: Int
  def input: Input
  def endpoint: Endpoint[MyServiceOperation, Input, Err, Output, StreamedInput, StreamedOutput]
}

object MyServiceOperation {

  object reified extends MyServiceGen[MyServiceOperation] {
    def myOperation(greeting: String): MyOperation = MyOperation(MyOperationInput(greeting))
    def getUser(userId: UserId): GetUser = GetUser(GetUserInput(userId))
    def myOtherOperation(): MyOtherOperation = MyOtherOperation()
  }
  class Transformed[P[_, _, _, _, _], P1[_ ,_ ,_ ,_ ,_]](alg: MyServiceGen[P], f: PolyFunction5[P, P1]) extends MyServiceGen[P1] {
    def myOperation(greeting: String): P1[MyOperationInput, Nothing, MyOperationOutput, Nothing, Nothing] = f[MyOperationInput, Nothing, MyOperationOutput, Nothing, Nothing](alg.myOperation(greeting))
    def getUser(userId: UserId): P1[GetUserInput, Nothing, UserForRead, Nothing, Nothing] = f[GetUserInput, Nothing, UserForRead, Nothing, Nothing](alg.getUser(userId))
    def myOtherOperation(): P1[Unit, Nothing, Unit, Nothing, Nothing] = f[Unit, Nothing, Unit, Nothing, Nothing](alg.myOtherOperation())
  }

  def toPolyFunction[P[_, _, _, _, _]](impl: MyServiceGen[P]): PolyFunction5[MyServiceOperation, P] = new PolyFunction5[MyServiceOperation, P] {
    def apply[I, E, O, SI, SO](op: MyServiceOperation[I, E, O, SI, SO]): P[I, E, O, SI, SO] = op.run(impl) 
  }
  final case class MyOperation(input: MyOperationInput) extends MyServiceOperation[MyOperationInput, Nothing, MyOperationOutput, Nothing, Nothing] {
    def run[F[_, _, _, _, _]](impl: MyServiceGen[F]): F[MyOperationInput, Nothing, MyOperationOutput, Nothing, Nothing] = impl.myOperation(input.greeting)
    def ordinal: Int = 0
    def endpoint: smithy4s.Endpoint[MyServiceOperation,MyOperationInput, Nothing, MyOperationOutput, Nothing, Nothing] = MyOperation
  }
  object MyOperation extends smithy4s.Endpoint[MyServiceOperation,MyOperationInput, Nothing, MyOperationOutput, Nothing, Nothing] {
    val schema: OperationSchema[MyOperationInput, Nothing, MyOperationOutput, Nothing, Nothing] = Schema.operation(ShapeId("services", "MyOperation"))
      .withInput(MyOperationInput.schema)
      .withOutput(MyOperationOutput.schema)
      .withHints(smithy.api.Documentation("An operation is a single action that can be performed by a service (usually one service, although you\'re free to reuse operations).\nIt can be thought of as a function signature, with input and output shapes."))
    def wrap(input: MyOperationInput): MyOperation = MyOperation(input)
  }
  final case class GetUser(input: GetUserInput) extends MyServiceOperation[GetUserInput, Nothing, UserForRead, Nothing, Nothing] {
    def run[F[_, _, _, _, _]](impl: MyServiceGen[F]): F[GetUserInput, Nothing, UserForRead, Nothing, Nothing] = impl.getUser(input.userId)
    def ordinal: Int = 1
    def endpoint: smithy4s.Endpoint[MyServiceOperation,GetUserInput, Nothing, UserForRead, Nothing, Nothing] = GetUser
  }
  object GetUser extends smithy4s.Endpoint[MyServiceOperation,GetUserInput, Nothing, UserForRead, Nothing, Nothing] {
    val schema: OperationSchema[GetUserInput, Nothing, UserForRead, Nothing, Nothing] = Schema.operation(ShapeId("services", "GetUser"))
      .withInput(GetUserInput.schema)
      .withOutput(UserForRead.schema)
      .withHints(smithy.api.Readonly())
    def wrap(input: GetUserInput): GetUser = GetUser(input)
  }
  final case class MyOtherOperation() extends MyServiceOperation[Unit, Nothing, Unit, Nothing, Nothing] {
    def run[F[_, _, _, _, _]](impl: MyServiceGen[F]): F[Unit, Nothing, Unit, Nothing, Nothing] = impl.myOtherOperation()
    def ordinal: Int = 2
    def input: Unit = ()
    def endpoint: smithy4s.Endpoint[MyServiceOperation,Unit, Nothing, Unit, Nothing, Nothing] = MyOtherOperation
  }
  object MyOtherOperation extends smithy4s.Endpoint[MyServiceOperation,Unit, Nothing, Unit, Nothing, Nothing] {
    val schema: OperationSchema[Unit, Nothing, Unit, Nothing, Nothing] = Schema.operation(ShapeId("services", "MyOtherOperation"))
      .withInput(unit)
      .withOutput(unit)
    def wrap(input: Unit): MyOtherOperation = MyOtherOperation()
  }
}

