package com.example.weatherday.presentacion.clima

sealed class ClimaIntencion {
    object actualizarClima: ClimaIntencion()
    data class pronosticoClima(val ciudad: String): ClimaIntencion()
}
