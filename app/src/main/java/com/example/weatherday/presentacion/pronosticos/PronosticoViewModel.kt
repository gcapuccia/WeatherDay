package com.example.weatherday.presentacion.pronosticos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weatherday.repository.Repositorio
import com.example.weatherday.router.Router
import kotlinx.coroutines.launch

class PronosticoViewModel(
val respositorio: Repositorio,
val router: Router,
val nombre: String
) : ViewModel() {

    var uiState by mutableStateOf<PronosticoEstado>(PronosticoEstado.Error(""))

    fun ejecutar(intencion: PronosticoIntencion){
        when(intencion){
            PronosticoIntencion.actualizarPronostico -> traerPronostico()
        }
    }

    private fun traerPronostico() {
        // uiState = PronosticoEstado.Cargando
        viewModelScope.launch {
            try {
                val forecast = respositorio.traerPronostico(nombre).filter {
                     true
                 }
                uiState = PronosticoEstado.Exitoso(forecast)
            }catch ( exception: Exception){
                uiState = PronosticoEstado.Error(exception.localizedMessage ?: "error" )
            }
        }
    }


}

class PronosticoViewModelFactory(
    private val repositorio: Repositorio,
    private val router: Router,
    private val ci: String,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PronosticoViewModel::class.java)) {
            return PronosticoViewModel(repositorio,router,ci) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}