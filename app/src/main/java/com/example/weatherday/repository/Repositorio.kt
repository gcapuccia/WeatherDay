package com.example.weatherday.repository

import com.example.weatherday.repository.modelos.Ciudad
import com.example.weatherday.repository.modelos.Clima

interface Repositorio {
    suspend fun buscarCiudad(ciudad: String): List<Ciudad>
    suspend fun traerClima(lat: Float, lon: Float) : Clima
    suspend fun traerPronostico(ciudad: Ciudad) : List<Clima>
}