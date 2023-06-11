package com.example.plugins

import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val OPEN_WEATHER_API = "https://api.openweathermap.org/data/2.5/weather"

fun Application.configureRouting() {

    fun buildWeatherRequest(lat: Double, lon: Double): String {
        val config = HoconApplicationConfig(ConfigFactory.load())
        val appid = config.property("api.openWeatherApiKey").getString()
        return "${OPEN_WEATHER_API}?appid=${appid}&lat=${lat}&lon=${lon}&units=metric"
    }

    fun getBerlinWeather(): String {
        val latBerlin = 52.520008
        val lonBerlin = 13.404954
        return "${buildWeatherRequest(latBerlin, lonBerlin)}"
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
