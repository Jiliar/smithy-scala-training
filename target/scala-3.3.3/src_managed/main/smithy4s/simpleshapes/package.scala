package object simpleshapes {

  type MyBigDecimal = simpleshapes.MyBigDecimal.Type
  type MyBigInteger = simpleshapes.MyBigInteger.Type
  type MyBoolean = simpleshapes.MyBoolean.Type
  type MyByte = simpleshapes.MyByte.Type
  /** Byte arrays are also supported.
    * These are usually represented as raw bytes, or (if need be), "escaped" as a base64-encoded string (e.g. in JSON).
    */
  type MyByteArray = simpleshapes.MyByteArray.Type
  /** Documents are free-form shapes modelling essentially "schemaless" values.
    * Structurally equivalent to a JSON value (object, number, array, string, boolean, null).
    * In protocols that use JSON, documents are indeed represented as JSON values.
    */
  type MyDocument = simpleshapes.MyDocument.Type
  type MyDouble = simpleshapes.MyDouble.Type
  type MyFloat = simpleshapes.MyFloat.Type
  /** You can also define integers and other numeric types. */
  type MyInt = simpleshapes.MyInt.Type
  type MyLong = simpleshapes.MyLong.Type
  type MyShort = simpleshapes.MyShort.Type
  /** For serialization purposes, equivalent to any other string.
    * Useful for type safety or attaching traits to all references of this shape.
    */
  type MyString = simpleshapes.MyString.Type
  /** An instant in time, independent of any locale or timezone.
    * Can be represented on the wire in multiple ways, e.g. as a string (ISO-8601), or as a number (seconds since epoch).
    * In many protocols, this behavior can be controlled with the timestampFormat trait.
    */
  type MyTimestamp = simpleshapes.MyTimestamp.Type

}