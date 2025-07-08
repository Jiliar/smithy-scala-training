$version: "2"
namespace exporting

use alloy#simpleRestJson

@simpleRestJson
service ExportService {
  version: "1.0",
  operations: [ExportData]
}

@http(method: "POST", uri: "/export")
operation ExportData {
  input: ExportDataInput,
  output: ExportDataOutput
}

structure ExportDataInput {
  @required
  format: ExportFormat,
  
  @required
  @httpQuery("destination")
  destinationPath: String
}

structure ExportDataOutput {
  @required
  message: String
}

enum ExportFormat {

  @enumValue("JSON")
  JSON,

  @enumValue("CSV")
  CSV

  @enumValue("YAML")
  YAML

  @enumValue("Parquet")
  PARQUET

  @enumValue("Avro")
  AVRO

  @enumValue("Protobuf")
  PROTOBUF
  
}