# 🛠️ Introducción a Smithy

**Smithy** es un lenguaje de modelado para definir servicios y APIs en entornos políglotas. Fue creado por **AWS** y está diseñado para resolver problemas de diseño, implementación y mantenimiento de APIs.

---

## 🎯 ¿Para qué sirve?

- Diseñar contratos de API de manera clara y mantenible.
- Generar código cliente/servidor en múltiples lenguajes.
- Validar y versionar interfaces de servicio.
- Separar el diseño de una API de su implementación.

---

## 🧬 Características clave

| Característica                 | Descripción                                                                 |
|-------------------------------|-----------------------------------------------------------------------------|
| **Lenguaje declarativo**      | No se escribe lógica, se describe el contrato de una API.                  |
| **Políglota**                 | Se puede usar en múltiples lenguajes a través de generación de código.     |
| **Extensible**                | Permite definir traits personalizados.                                     |
| **Modelado con shapes**       | Las entidades de datos se definen como estructuras (`structure`, `string`, etc.). |
| **Agnóstico a protocolo**     | Compatible con REST, JSON-RPC, etc. mediante traits como `@http`.          |
| **Versionamiento sencillo**   | Permite evolucionar APIs sin romper compatibilidad.                         |

---

## 🧱 Estructura básica de un modelo Smithy

### 1. **Shapes**
Los bloques de construcción del modelo:

```smithy
structure CreateUserInput {
  username: string,
  email: string,
}

structure CreateUserOutput {
  userId: string
}
```

### 2. Service

Agrupa operaciones en un servicio:

```smithy
service MyService {
  version: "1.0.0",
  operations: [CreateUser]
}
```

### 3. Operation

Define una operación que puede ejecutar el servicio:

```smithy
operation CreateUser {
  input: CreateUserInput,
  output: CreateUserOutput,
  errors: [UserAlreadyExists]
}
```

### 4. Traits

Anotaciones que modifican el comportamiento:

```smithy
@http(method: "POST", uri: "/users")
operation CreateUser { ... }
```

⸻

###  🚀 Metodología de uso
	1.	Definir modelos de datos: usando structure, enum, union, etc.
	2.	Definir operaciones: con entradas, salidas y errores.
	3.	Usar traits: como @http, @readonly, @deprecated, etc.
	4.	Definir protocolo: con traits como @restJson1.
	5.	Validar y generar código: con herramientas de Smithy CLI o plugins de Maven/Gradle.

⸻

###  🔧 Herramientas clave
	•	smithy-cli: CLI oficial para validar modelos y transformarlos.
	•	smithy-build.json: archivo para definir cómo se procesa el modelo.
	•	Plugins de Maven/Gradle: para integrar en pipelines de Java.
	•	Smithy Codegen: genera SDKs y stubs para múltiples lenguajes.

⸻

### 📘 Ejemplo completo

namespace com.example.api

```smithy
@restJson1
service UserService {
  version: "1.0",
  operations: [CreateUser]
}

@http(method: "POST", uri: "/users")
operation CreateUser {
  input: CreateUserInput,
  output: CreateUserOutput,
  errors: [UserAlreadyExists]
}

structure CreateUserInput {
  username: string,
  email: string,
}

structure CreateUserOutput {
  userId: string
}

@error("client")
structure UserAlreadyExists {
  message: string
}
```

# 🧩 Alloy de Disney Streaming

**Alloy** es una librería de código abierto creada por **Disney Streaming** que extiende a [Smithy](https://smithy.io) para soportar generación de código avanzada, transformación de modelos y validación personalizada en arquitecturas orientadas a servicios.

---

## 🎯 ¿Para qué sirve Alloy?

| Propósito                     | Descripción                                                                 |
|------------------------------|-----------------------------------------------------------------------------|
| 🔄 **Generación de código**   | Genera código fuente (Java, Kotlin, etc.) a partir de modelos Smithy.       |
| 🧩 **Transformaciones**       | Aplica transformaciones declarativas o programáticas sobre los modelos.     |
| 🔒 **Validaciones personalizadas** | Permite definir reglas adicionales sobre los modelos.                  |
| 🧪 **Pruebas contractuales** | Genera artefactos para validar que la implementación cumple el contrato.   |
| 🧱 **DSL interno**            | Permite escribir modelos en un DSL propio que se compila a Smithy.         |

---

## 🧪 ¿Cuándo usar Alloy?

Alloy es útil cuando:

- Usas **Smithy como fuente de verdad** para tus APIs.
- Necesitas **estructura personalizada** o SDKs en lenguajes específicos.
- Requieres **validaciones adicionales** durante el build o CI.
- Quieres **generar automáticamente** stubs de servidor o cliente.
- Deseas exportar modelos a diferentes lenguajes o formatos.

---

## 🔧 Tecnologías y compatibilidad

- Escrito en **Kotlin**
- Basado en **Smithy (Java)**
- Se integra mediante **plugin de Gradle** (`alloy-gradle-plugin`)
- Compatible con protocolos como `restJson1`, `awsJson1_1`, etc.
- Extensible para frameworks como **Ktor**, **Spring** o **HTTP4K**

---

## 📘 Ejemplo de uso

```kotlin
alloy {
  sourceModel = file("model.smithy")
  targets {
    httpClient {
      language = "kotlin"
      protocol = "restJson1"
    }
  }
}
```


# 🧩 Smithy4s

**Smithy4s** es una librería de **Scala** desarrollada por **Disney Streaming** que permite trabajar con **contratos definidos en Smithy** directamente desde el ecosistema Scala. Ofrece generación automática de código y una integración fluida con frameworks funcionales como Http4s, ZIO, y Cats Effect.

---

## 🚀 ¿Qué es Smithy4s?

Smithy4s es:

- Una herramienta de **codegen** (generación de código).
- Un runtime para servicios Smithy en Scala.
- Una forma segura y funcional de **implementar o consumir APIs** desde modelos `.smithy`.

---

## 🧠 Ventajas

| Característica                 | Descripción                                                                 |
|-------------------------------|-----------------------------------------------------------------------------|
| 🎯 **Tipos seguros**          | Inputs y outputs se representan como tipos Scala.                          |
| ⚡ **Codegen automático**     | Genera código desde archivos `.smithy`.                                    |
| 🔌 **Integración con Http4s** | Levanta servidores y clientes sin boilerplate.                             |
| 🧪 **Test de contratos**      | Valida que la implementación cumpla el contrato.                          |
| 🧩 **Extensible**             | Soporte para traits personalizados, middlewares, etc.                     |

---

## 🛠️ ¿Cómo se usa?

### 1. Define tu API en `.smithy`

```smithy
namespace example

@restJson1
service UserService {
  version: "1.0",
  operations: [GetUser]
}

@http(method: "GET", uri: "/user/{id}")
operation GetUser {
  input: GetUserInput,
  output: User
}

structure GetUserInput {
  @httpLabel
  id: string
}

structure User {
  id: string,
  name: string
}
```

### 2. Configura `sbt` con el plugin Smithy4s

```scala
enablePlugins(Smithy4sCodegenPlugin)

smithy4sInputDirs += baseDirectory.value / "smithy"
smithy4sAllowedNamespaces += "example"
```

Esto generará automáticamente algo como:

```scala
trait UserService[F[_]] {
  def getUser(id: String): F[User]
}
```

---

## 📦 ¿Qué incluye Smithy4s?

- **Servidor y cliente HTTP** con Http4s
- Integración con **ZIO**, **Cats Effect**, y otros
- Serialización y deserialización **automática**
- Generación de documentación OpenAPI
- Generación de tipos algebraicos para errores (`sealed trait`)
- Validación y pruebas de contrato

---

## 📚 Recursos oficiales

- GitHub: [https://github.com/disneystreaming/smithy4s](https://github.com/disneystreaming/smithy4s)
- Documentación: [https://disneystreaming.github.io/smithy4s](https://disneystreaming.github.io/smithy4s)

---

## ✅ ¿Cuándo usar Smithy4s?

Usa Smithy4s si:

- Trabajas con Scala y quieres mantener APIs **de forma declarativa y segura**.
- Buscas **generación automática de clientes y servidores**.
- Quieres **compatibilidad REST** entre servicios escritos en distintos lenguajes.
- Deseas evitar código repetitivo en servicios funcionales.
- Ya usas Smithy y quieres aprovecharlo desde Scala (incluyendo Scala.js).

# 🧪 Práctica completa en Scala con Smithy

Esta sección documenta una práctica completa desarrollada en Scala usando Smithy4s.

## 🧭 Metodología Smithy

### Configuración del archivo `.smithy`

1. Se estipula una versión, ejemplo: `$version: "2"`
2. Se define un `namespace` y un servicio.
3. Se definen operaciones usando `operation { ... }`.
4. Se crean estructuras (`structure`) que representan inputs/outputs de las operaciones.
5. Las operaciones son agregadas dentro del servicio.
6. Se pueden anotar las operaciones, por ejemplo: `@readonly`.
7. Se usa el protocolo `@simpleRestJson` para servicios JSON+REST (de Alloy).
8. Se crea el archivo de compilación `smithy-build.json` con las dependencias.
9. En cada operación se usa `@http(method, uri)` para definir el endpoint:
   - Se usa `@httpLabel` para mapear partes de la URI a campos.
   - Se usa `@httpHeader` para encabezados.
   - Se usa `@httpQuery` para query parameters.

## ⚙️ Configuración del proyecto Scala

1. Crear `build.properties` con la versión de sbt, por ejemplo:
   ```
   sbt.version=1.8.2
   ```
2. Crear `plugins.sbt` y añadir:
   ```scala
   addSbtPlugin("com.disneystreaming.smithy4s" % "smithy4s-sbt-codegen" % "0.18.38")
   ```
3. Crear `metals.sbt` si usas Metals.
4. Crear `build.sbt` con:
   ```scala
   scalaVersion := "2.13.16"
   version := "0.1.0"

   val smithy4sVersion = "0.18.38"

   lazy val root = (project in file("."))
     .enablePlugins(Smithy4sCodegenPlugin)
     .settings(
       name := "smithy-training",
       Compile / mainClass := Some("smithy4sdemo.Main"),
       Compile / run / fork := true,
       libraryDependencies ++= Seq(
         "com.disneystreaming.smithy4s" %% "smithy4s-http4s" % smithy4sVersion,
         "com.disneystreaming.smithy4s" %% "smithy4s-http4s-swagger" % smithy4sVersion,
         "org.http4s" %% "http4s-ember-server" % "0.23.30",
         "org.http4s" %% "http4s-ember-client" % "0.23.30",
         "org.typelevel" %% "cats-effect" % "3.5.2",
         "org.slf4j" % "slf4j-simple" % "2.0.13"
       )
     )
   ```
5. Crear estructura de carpetas en `src/main/scala` y `src/main/smithy`.
6. Compilar para generar código en `target`.
7. Crear clase `ServerMain.scala` que extiende `IOApp.Simple` y levanta el servidor con `EmberServerBuilder`.

## 🌐 Cliente HTTP

1. Importar `"org.http4s" %% "http4s-ember-client" % "0.23.30"`
2. Crear `ClientMain.scala` con:
   ```scala
   package smithy4sdemo

   import cats.effect.{IO, IOApp}
   import org.http4s.ember.client.EmberClientBuilder
   import smithy4s.http4s.SimpleRestJsonBuilder
   import hello._

   object ClientMain extends IOApp.Simple {
     def run: IO[Unit] = EmberClientBuilder.default[IO]
       .build
       .use { c =>
         SimpleRestJsonBuilder(WeatherService)
           .client(c)
           .resource
           .use { client =>
             client.createCity("Barcelona", "Spain").flatMap { output =>
               IO.println(s"Created city with ID: ${output.cityId.value}") *>
                 client.createCity("london", "UK")
                   .flatMap { output =>
                     client.getWeather(CityId(output.cityId.value))
                       .flatMap(IO.println(_))
                   }
             }
           }
       }
   }
   ```

## 🧩 Shapes utilizados

- **Simple shapes**: `string`, `integer`, `boolean`, etc.
- **Structure shapes**: para inputs y outputs.
- **Service shapes**: definen servicios.
- **Enum shapes** (opcional): para limitar valores permitidos.

```smithy
structure GetWeatherInput {
  @httpLabel
  cityId: CityId,

  @httpHeader("X-Region")
  region: String = "us-east-1",

  @httpQuery("units")
  units: String = "metric"
}
```

```bash
http GET localhost:8080/cities/123/weather?units=imperial X-Region:us-west-2
```
