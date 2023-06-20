package com.example.plugins

import com.typesafe.config.ConfigFactory
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.json.JSONObject

const val OPEN_WEATHER_API = "https://api.openweathermap.org/data/2.5/weather"


@Serializable
data class Weather(val timezone: Int, val name: String)



fun Application.configureRouting() {

    fun buildWeatherRequest(lat: Double, lon: Double): String {
        val config = HoconApplicationConfig(ConfigFactory.load())
        val appid = config.property("api.openWeatherApiKey").getString()
        return "${OPEN_WEATHER_API}?appid=${appid}&lat=${lat}&lon=${lon}&units=metric"
    }

    suspend fun getBerlinWeather(): String {
        val latBerlin = 52.520008
        val lonBerlin = 13.404954
        val weatherApiUrl = "${buildWeatherRequest(latBerlin, lonBerlin)}"
        val client = HttpClient(OkHttp)
        val response: String = client.get(weatherApiUrl).bodyAsText()

        val jsonObject = JSONObject(response)
        val temp = jsonObject["main"]

        println("****")
        println(temp)
        println("****")

        return weatherApiUrl
    }

    routing {
        get("/") {
            call.respondText(
                "Hello World! Current temperature in Berlin is ${getBerlinWeather()}"
            )
        }
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
    }
}
