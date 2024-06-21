package com.example.weatherday.repository

import com.example.weatherday.repository.modelos.Ciudad
import com.example.weatherday.repository.modelos.Clima
import com.example.weatherday.repository.modelos.ListForecast

class RepositorioMock : Repositorio{
    override suspend fun CiudadBuscada(ciudad: String): List<Ciudad> {
        TODO("Not yet implemented")
    }

    override suspend fun mostrarClima(lat: Float, lon: Float): Clima {
        TODO("Not yet implemented")
    }

    override suspend fun mostrarPronostico(ciudad: String): List<ListForecast> {
        TODO("Not yet implemented")
    }

}