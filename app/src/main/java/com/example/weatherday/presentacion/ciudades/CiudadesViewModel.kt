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
            is CiudadesIntencion.Buscar -> buscar(nombre = intencion.nombre)
            is CiudadesIntencion.Seleccionar -> seleccionar(ciudad = intencion.ciudad)
        }
    }

    private fun buscar( nombre: String){
        uiState = CiudadesEstado.Cargando
        viewModelScope.launch {
            try {
                ciudades = repositorio.buscarCiudad(nombre).toList()
                uiState = CiudadesEstado.Resultados(ciudades)
            } catch (exeption: Exception){
                uiState = CiudadesEstado.Error(exeption.message ?: "error desconocido")
            }
        }
    }

    private fun seleccionar(ciudad: Ciudad){
        val ruta = Ruta.Clima(
            lat = ciudad.lat,
            lon = ciudad.lon
        )
        router.navegar(ruta)
    }

    }



//FACTORY
class CiudadesViewModelFactory(
    private val repositorio: Repositorio,
    private val router: Router
) : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel>create(modelClass: Class<T>): T{
        if (modelClass.isAssignableFrom(CiudadesViewModel::class.java)){
            return CiudadesViewModel(repositorio,router) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}



