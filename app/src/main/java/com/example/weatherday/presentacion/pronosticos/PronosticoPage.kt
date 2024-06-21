package com.example.weatherday.presentacion.pronosticos

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.weatherday.repository.RepositorioApi
import com.example.weatherday.router.Enrutador


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PronosticoPage(
    navHostController: NavHostController,
    nombre: String
){
    val pronosticoViewModel : PronosticoViewModel = viewModel(
        factory = PronosticoViewModelFactory(
            repositorio = RepositorioApi(),
            router = Enrutador(navHostController),
            nombre = nombre

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
