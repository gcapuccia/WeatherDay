package com.example.weatherday.repository

import com.example.weatherday.repository.modelos.Ciudad
import com.example.weatherday.repository.modelos.Clima
import com.example.weatherday.repository.modelos.ForecastDTO
import com.example.weatherday.repository.modelos.ListForecast
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class RepositorioApi : Repositorio{


    private val apiKey = "126835e06d14f7a38659ee4f96841da8"

    private val cliente = HttpClient(){
        install(ContentNegotiation){
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    override suspend fun CiudadBuscada(ciudad: String): List<Ciudad> {
        val query = cliente.get("https://api.openweathermap.org/geo/1.0/direct"){
            parameter("q",ciudad)
            parameter("limit", 2)
            parameter("appid", apiKey)
        }
        if (query.status == HttpStatusCode.OK){
            val ciudades = query.body<List<Ciudad>>()
            return ciudades
        }else{
            throw Exception()
        }
    }


    override suspend fun mostrarClima(lat: Float, lon: Float): Clima {
        val query = cliente.get("https://api.openweathermap.org/data/2.5/weather"){
            parameter("lat",lat)
            parameter("lon",lon)
            parameter("lang","sp")
            parameter("units","metric")
            parameter("appid",apiKey)
        }
        if (query.status == HttpStatusCode.OK){
            val clima = query.body<Clima>()
            return clima
        }else{
            throw Exception()
        }
    }

    override suspend fun mostrarPronostico(nombre: String): List<ListForecast> {
        val query = cliente.get("https://api.openweathermap.org/data/2.5/forecast"){
            parameter("q",nombre)
            parameter("units","metric")
            parameter("cnt", 20)
            parameter("lang", "sp")
            parameter("appid",apiKey)
        }
        if (query.status == HttpStatusCode.OK){
            val forecast = query.body<ForecastDTO>()
            return forecast.list
        }else{
            throw Exception()
        }
    }

}