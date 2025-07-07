
# 🛠️ Introduction to Smithy

**Smithy** is a modeling language for defining services and APIs in polyglot environments. It was created by **AWS** and is designed to solve challenges in the design, implementation, and maintenance of APIs.

---

## 🎯 What is it used for?

- Designing API contracts clearly and maintainably.
- Generating client/server code in multiple programming languages.
- Validating and versioning service interfaces.
- Separating the design of an API from its implementation.

---

## 🧬 Key Features

| Feature                    | Description                                                                 |
|----------------------------|-----------------------------------------------------------------------------|
| **Declarative language**   | Focuses on describing API contracts, not logic.                            |
| **Polyglot**               | Usable across multiple languages through code generation.                  |
| **Extensible**             | Supports custom traits and extensions.                                     |
| **Protocol-agnostic**      | Works with REST, JSON-RPC, or custom protocols via traits.                 |
| **Code generation ready**  | Enables code generation for servers and clients.                           |
| **Versionable**            | Makes it easy to evolve APIs safely.                                       |

---

## 🧱 Basic Structure of a Smithy Model

### 1. Shapes (Data Structures)

```smithy
structure CreateUserInput {
  username: string,
  email: string,
}

structure CreateUserOutput {
  userId: string
}
```

2. Service Definition

```smithy
service UserService {
  version: "1.0",
  operations: [CreateUser]
}
```

3. Operation Definition

```smithy
operation CreateUser {
  input: CreateUserInput,
  output: CreateUserOutput,
  errors: [UserAlreadyExists]
}
```

4. Traits

```smithy
@http(method: "POST", uri: "/users")
operation CreateUser { ... }
```

⸻

🛠 Methodology:

	- Define models using structure, enum, union, etc.
	- Declare operations with inputs, outputs, and possible errors.
	- Annotate with traits like @http or @readonly.
	- Define the protocol using traits such as @restJson1.
	- Validate and generate code with the Smithy CLI or codegen plugins.

⸻

📘 Full Example

```smithy
namespace com.example.api

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

# 🧩 Alloy by Disney Streaming

**Alloy** is an open-source library created by **Disney Streaming** that extends [Smithy](https://smithy.io) to support advanced code generation, model transformation, and validation for service-oriented architectures.

---

## 🎯 What is Alloy for?

| Purpose                        | Description                                                                 |
|-------------------------------|-----------------------------------------------------------------------------|
| 🔄 **Custom Codegen**         | Generates source code (e.g. Java, Kotlin) from Smithy models.               |
| 🧩 **Model Transformations**   | Applies declarative or programmable transformations to Smithy models.       |
| 🔒 **Custom Validations**     | Define custom validation rules on API models.                              |
| 🧪 **Contract Testing**       | Produces artifacts for validating that implementations match the contract.  |
| 🧱 **Internal DSL**           | Supports writing models in a proprietary DSL and compiling to Smithy.       |

---

## 🧪 When to Use Alloy

Alloy is useful when:

- You treat **Smithy as the source of truth** for your APIs.
- You need **custom project structures** or **language-specific SDKs**.
- You require **additional validation** during build or CI.
- You want to **automatically generate** server or client stubs.
- You want to export models in different formats/languages.

---

## 🔧 Technology Overview

- Written in **Kotlin**
- Built on top of **Smithy (Java)**
- Usable via **Gradle plugin** (`alloy-gradle-plugin`)
- Protocol support: `restJson1`, `awsJson1_1`, etc.
- Extendable to generate code for frameworks like **Ktor**, **Spring**, or **HTTP4K**

---

## 📘 Example Usage

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

**Smithy4s** is a **Scala library** developed by **Disney Streaming** that allows working with **Smithy-defined contracts** directly from the Scala ecosystem. It provides automatic code generation and seamless integration with functional frameworks like Http4s, ZIO, and Cats Effect.

---

## 🚀 What is Smithy4s?

Smithy4s is:

- A **code generation tool**.
- A runtime for Smithy services in Scala.
- A type-safe and functional way to **implement or consume APIs** from `.smithy` models.

---

## 🧠 Benefits

| Feature                        | Description                                                                  |
|--------------------------------|------------------------------------------------------------------------------|
| 🎯 **Type Safety**             | Inputs and outputs are represented as Scala types.                           |
| ⚡ **Automatic Codegen**       | Generates code from `.smithy` files.                                        |
| 🔌 **Http4s Integration**      | Build servers and clients with minimal boilerplate.                         |
| 🧪 **Contract Testing**        | Validates that implementations conform to the API contract.                 |
| 🧩 **Extensible**              | Supports custom traits, middleware, and more.                               |

---

## 🛠️ How to Use

### 1. Define your API in `.smithy`

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

### 2. Configure `sbt` with the Smithy4s plugin

```scala
enablePlugins(Smithy4sCodegenPlugin)

smithy4sInputDirs += baseDirectory.value / "smithy"
smithy4sAllowedNamespaces += "example"
```

This will automatically generate something like:

```scala
trait UserService[F[_]] {
  def getUser(id: String): F[User]
}
```

---

## 📦 What’s Included in Smithy4s?

- **HTTP server and client** via Http4s
- Integration with **ZIO**, **Cats Effect**, and others
- Automatic **serialization and deserialization**
- OpenAPI documentation generation
- Algebraic data types for errors (`sealed trait`)
- Contract validation and test support

---

## 📚 Official Resources

- GitHub: [https://github.com/disneystreaming/smithy4s](https://github.com/disneystreaming/smithy4s)
- Documentation: [https://disneystreaming.github.io/smithy4s](https://disneystreaming.github.io/smithy4s)

---

## ✅ When to Use Smithy4s

Use Smithy4s if you:

- Work with Scala and want **declarative, type-safe API definitions**.
- Need **automatic client/server code generation**.
- Want **REST interoperability** with services in other languages.
- Aim to avoid boilerplate in functional service implementations.
- Already use Smithy and want to leverage it in Scala (including Scala.js).

# 🧪 Full Scala Practice with Smithy

This section documents a complete hands-on exercise using Smithy4s and Scala.

## 🧭 Smithy Methodology

### Smithy file configuration

1. Declare version: `$version: "2"`
2. Define a `namespace` and a `service`.
3. Declare operations using `operation { ... }`.
4. Define `structure` shapes for inputs and outputs.
5. Add operations to the service block.
6. Use annotations like `@readonly` to add metadata to operations.
7. Use the `@simpleRestJson` protocol from Alloy (REST + JSON).
8. Create `smithy-build.json` with input directories and dependencies.
9. Use traits like:
   - `@http(method, uri)` to define HTTP behavior
   - `@httpLabel` to match URI segments
   - `@httpHeader` for headers
   - `@httpQuery` for query parameters

## ⚙️ Scala Project Configuration

1. Create `build.properties` with sbt version:
   ```
   sbt.version=1.8.2
   ```
2. Add `plugins.sbt` with:
   ```scala
   addSbtPlugin("com.disneystreaming.smithy4s" % "smithy4s-sbt-codegen" % "0.18.38")
   ```
3. (Optional) Create `metals.sbt` if using Metals.
4. Create `build.sbt` with:
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
5. Create the source structure: `src/main/scala` and `src/main/smithy`.
6. Run `sbt compile` to generate code into `target/`.
7. Create `ServerMain.scala` extending `IOApp.Simple` and launching the service.

## 🌐 HTTP Client Example

1. Import dependency:
   ```scala
   "org.http4s" %% "http4s-ember-client" % "0.23.30"
   ```
2. Sample `ClientMain.scala`:
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

## 🧩 Shape Types Used

- **Simple shapes**: `string`, `integer`, `boolean`, etc.
- **Structure shapes**: define input/output types.
- **Service shapes**: define the contract.
- **Enum shapes** (optional): define accepted values.

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