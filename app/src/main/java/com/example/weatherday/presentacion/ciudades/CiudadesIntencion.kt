package com.example.weatherday.presentacion.ciudades

import com.example.weatherday.repository.modelos.Ciudad


sealed class CiudadesIntencion {
    data class Buscar( val nombre:String ) : CiudadesIntencion()
    data class Seleccionar(val ciudad: Ciudad) : CiudadesIntencion()
}

