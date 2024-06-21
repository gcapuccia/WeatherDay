package com.example.weatherday.presentacion.ciudades

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherday.repository.Repositorio
import com.example.weatherday.repository.modelos.Ciudad
import com.example.weatherday.router.Router
import com.example.weatherday.router.Ruta
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class CiudadesViewModel(
    val repositorio: Repositorio,
    val router: Router
) : ViewModel(){

    var uiState by mutableStateOf<CiudadesEstado>(CiudadesEstado.Vacio)
    var ciudades : List<Ciudad> = emptyList()

    fun ejecutar(intencion: CiudadesIntencion){
        when(intencion){
            is CiudadesIntencion.Buscar -> CiudadesBuscar(nombre = intencion.nombre)
            is CiudadesIntencion.Seleccionar -> SeleccionarCiudad(ciudad = intencion.ciudad)
        }
    }

    private fun SeleccionarCiudad(ciudad: Ciudad){
        val ruta = Ruta.Clima(
            lat = ciudad.lat,
            lon = ciudad.lon,
            nombre = ciudad.name
        )
        router.navegar(ruta)
    }

    private fun CiudadesBuscar(nombre: String){

        uiState = CiudadesEstado.Cargando
        viewModelScope.launch {
            try {
                ciudades = repositorio.CiudadBuscada(nombre)
                if (ciudades.isEmpty()) {
                    uiState = CiudadesEstado.Vacio
                } else {
                    uiState = CiudadesEstado.Resultado(ciudades)
                }
            } catch (exeption: Exception){
                uiState = CiudadesEstado.Error(exeption.message ?: "error desconocido")
            }
        }
    }

}


class CiudadesViewModelFactory(
    private val repositorio: Repositorio,
    private val router: Router
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CiudadesViewModel::class.java)) {
            return CiudadesViewModel(repositorio,router) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


