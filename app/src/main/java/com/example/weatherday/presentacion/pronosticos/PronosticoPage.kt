package com.example.weatherday.presentacion.pronosticos

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.weatherday.repository.RepositorioApi
import com.example.weatherday.router.Enrutador

@Composable
fun PronosticoPage(
    navHostController: NavHostController,
    ci: String
){
    val pronosticoViewModel : PronosticoViewModel = viewModel(
        factory = PronosticoViewModelFactory(
            repositorio = RepositorioApi(),
            router = Enrutador(navHostController),
            ci = ci
        )
    )
    Column {
        PronosticoView(
            state = pronosticoViewModel.uiState,
            onAction = { intencion ->
                pronosticoViewModel.ejecutar(intencion)
            }
        )
    }
}