package com.example.weatherday.presentacion.clima

sealed class ClimaIntencion {
    object BorrarTodo: ClimaIntencion()
    object MostrarCordoba: ClimaIntencion()
    object MostrarCaba: ClimaIntencion()
    object MostrarError: ClimaIntencion()
    object actualizarClima: ClimaIntencion()
}
