package com.example.weatherday.repository

import com.example.weatherday.repository.modelos.Ciudad
import com.example.weatherday.repository.modelos.Clima
import com.example.weatherday.repository.modelos.ListForecast

interface Repositorio {
    suspend fun CiudadBuscada(ciudad: String): List<Ciudad>
    suspend fun mostrarClima(lat: Float, lon: Float) : Clima
    suspend fun mostrarPronostico(nombre: String) : List<ListForecast>
}