package object aggregates {

  /** Sequences are also supported, with list shapes.
    * A list shape must declare exactly one member, with the name "member". That's the shape of the list's elements.
    * By default, all elements are non-nullable. This can be controlled with the `{@literal @}sparse` trait (if the protocol supports it).
    * @param member
    *   A structure is a collection of 0..N named members.
    *   By default, all members are considered optional, and they can be made required with the `{@literal @}required` trait.
    */
  type MyStructures = aggregates.MyStructures.Type
  /** Other kinds of sequences are supported, too.
    * In order to get a "Set" shape, you can use the `{@literal @}uniqueItems` trait.
    * In smithy4s, there's also `{@literal @}vector` and `{@literal @}indexedSeq` for other kinds of sequences.
    * @param member
    *   A structure is a collection of 0..N named members.
    *   By default, all members are considered optional, and they can be made required with the `{@literal @}required` trait.
    */
  type MyUniqueStructures = aggregates.MyUniqueStructures.Type
  type StringToNumber = aggregates.StringToNumber.Type

}