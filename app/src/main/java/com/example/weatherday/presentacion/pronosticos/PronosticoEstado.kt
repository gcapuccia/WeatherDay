package com.example.weatherday.presentacion.pronosticos

import com.example.weatherday.repository.modelos.Clima
import com.example.weatherday.repository.modelos.ListForecast

sealed class PronosticoEstado {
    data class Exitoso(
        val climas: List<ListForecast>,
    ) : PronosticoEstado()
    data class Error(
        val mensaje :String = "",
    ) : PronosticoEstado()

}
