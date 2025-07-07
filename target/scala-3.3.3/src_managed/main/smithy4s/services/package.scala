package object services {
  type MyService[F[_]] = smithy4s.kinds.FunctorAlgebra[MyServiceGen, F]
  val MyService = MyServiceGen

  type UserId = services.UserId.Type

}