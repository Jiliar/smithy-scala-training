package smithy4sdemo

import cats.effect.{IO, IOApp}
import org.http4s.ember.server.EmberServerBuilder
import exporting.ExportService
import exporting.ExportDataInput
import exporting.ExportDataOutput
import exporting.ExportFormat
import java.nio.file.{Files, Paths}
import smithy4s.http4s.SimpleRestJsonBuilder

object SimpleExportServerMain extends IOApp.Simple {

    // Exportar a JSON (.json)
    // http POST http://localhost:8080/export?destination="/tmp/export-file" format=JSON

    // Exportar a CSV (.csv)
    // http POST http://localhost:8080/export?destination="/tmp/export-file" format=CSV

    // Exportar a YAML (.yml)
    // http POST http://localhost:8080/export?destination="/tmp/export-file" format=YAML

    // Exportar a Parquet (.parquet)
    // http POST http://localhost:8080/export?destination="/tmp/export-file" format=PARQUET

    // Exportar a Avro (.avro)
    // http POST http://localhost:8080/export?destination="/tmp/export-file" format=AVRO

    // Exportar a Protobuf (.bin)
    // http POST http://localhost:8080/export?destination="/tmp/export-file" format=PROTOBUF
    val impl: ExportService[IO] = new ExportService[IO] {
    
        def exportData(format: ExportFormat, destinationPath: String): IO[ExportDataOutput] = {
            for {
                _ <- IO.println(s"Exporting data to $destinationPath as $format")
                _ <- writeToFile(destinationPath, format)
            } yield ExportDataOutput("Export completed successfully")
        }

        private def writeToFile(path: String, format: ExportFormat): IO[Unit] = {
            val content = format match {
                case ExportFormat.JSON     => """{"sample": "data", "format": "json"}"""
                case ExportFormat.CSV      => "id,name,format\n1,Sample,csv"
                case ExportFormat.YAML     => "sample: data\nformat: yaml"
                case ExportFormat.PARQUET  => "Contenido de ejemplo para Parquet (formato binario)"
                case ExportFormat.AVRO     => "Contenido de ejemplo para Avro (formato binario)"
                case ExportFormat.PROTOBUF => "Contenido de ejemplo para Protobuf (formato binario)"
            }
            IO.blocking {
                val extension = format match {
                    case ExportFormat.JSON     => ".json"
                    case ExportFormat.CSV      => ".csv"
                    case ExportFormat.YAML     => ".yml"
                    case ExportFormat.PARQUET  => ".parquet"
                    case ExportFormat.AVRO     => ".avro"
                    case ExportFormat.PROTOBUF => ".bin" // .pb también es común
                }

                val inputFileName = Paths.get(path).getFileName.toString

                val baseName = if (inputFileName.contains(".")) {
                    inputFileName.substring(0, inputFileName.lastIndexOf('.'))
                } else {
                    inputFileName
                }
                val finalFileName = baseName + extension

                val userHome = System.getProperty("user.home")
                val desktopPath = Paths.get(userHome, "Desktop", finalFileName)

                println(s"Escribiendo contenido para $format en: $desktopPath")
                Files.writeString(desktopPath, content)
            }
        }
    
    }
    
    val run: IO[Unit] =
        SimpleRestJsonBuilder
        .routes(impl)
        .resource
        .flatMap { routes =>
            EmberServerBuilder.default[IO]
            .withHttpApp(routes.orNotFound)
            .build
        }
        .evalMap { server =>
            IO.println(s"Server started at ${server.address}")
        }
        .useForever
}