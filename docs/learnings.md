### Configuration

* [Ktor docs](https://ktor.io/docs/configuration-file.html)
* Add vars to env: IntelliJ Run ...
* Use [configuration files](../src/main/kotlin/resources/application.conf)
* Use configuration from file:
   
    ```kotlin
    val config = HoconApplicationConfig(ConfigFactory.load())
    val appid = config.property("api.openWeatherApiKey").getString()
    ```
  
### Call external api

* [Ktor docs](https://ktor.io/docs/http-client-engines.html)

  ```kotlin
  // build.gradle.kts
  
  implementation("io.ktor:ktor-client-core:$ktor_version")
  implementation("io.ktor:ktor-client-okhttp:$ktor_version")
  ```

  ```kotlin
  import io.ktor.client.*
  import io.ktor.client.engine.okhttp.*
  
  val client = HttpClient(OkHttp)
  val response: String = client.get(weatherApiUrl).toString()
  ```