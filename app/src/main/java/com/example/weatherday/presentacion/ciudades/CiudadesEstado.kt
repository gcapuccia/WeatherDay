package com.example.weatherday.presentacion.ciudades

import com.example.weatherday.repository.modelos.Ciudad

sealed class CiudadesEstado {
    data class Resultado( val ciudades : List<Ciudad> ) : CiudadesEstado()


    data object Cargando: CiudadesEstado()

    data class Error(val mensaje: String): CiudadesEstado()
    data object Vacio: CiudadesEstado()
}