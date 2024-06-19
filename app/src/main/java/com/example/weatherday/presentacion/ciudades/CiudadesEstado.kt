package com.example.weatherday.presentacion.ciudades

import com.example.weatherday.repository.modelos.Ciudad

sealed class CiudadesEstado{
    data object Vacio: CiudadesEstado()
    data class Resultados(val ciudades : List<Ciudad>) : CiudadesEstado()
    data class Error(val mensaje: String): CiudadesEstado()
    data object Cargando : CiudadesEstado()

}