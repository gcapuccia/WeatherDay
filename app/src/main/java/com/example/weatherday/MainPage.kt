package com.example.weatherday

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherday.presentacion.ciudades.CiudadesPage
import com.example.weatherday.presentacion.clima.ClimaPage
//import com.example.weatherday.presentacion.pronosticos.PronosticoPage
import com.example.weatherday.router.Ruta

@Composable
fun MainPage(modifier: Modifier = Modifier) {


    val navHostController = rememberNavController()


    Scaffold(
        modifier = modifier,
        floatingActionButton  = {
            FloatingActionButton(
                onClick = { navHostController.navigate("ciudades")}
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Home")
            }
        },
        topBar = { MainTopAppBar() }
    ) {
        Column {
            Navegacion(
                modifier = Modifier.padding(it),
                navHostController = navHostController
            )
        }

    }

}
    @Composable
    fun Navegacion(
        modifier: Modifier,
        navHostController: NavHostController
    ){
        NavHost(
            navController = navHostController,
            startDestination = Ruta.Ciudades.id
        ) {
            composable(
                route = Ruta.Ciudades.id
            ) {
                CiudadesPage(navHostController)
            }
            composable(
                route = "clima?lat={lat}&lon={lon}&nombre={nombre}",
                arguments =  listOf(
                    navArgument("lat") { type= NavType.FloatType },
                    navArgument("lon") { type= NavType.FloatType },
                    navArgument("nombre") { type= NavType.StringType }
                )
            ) {
                val lat = it.arguments?.getFloat("lat") ?: 0.0f
                val lon = it.arguments?.getFloat("lon") ?: 0.0f
                val nombre = it.arguments?.getString("nombre") ?: ""
                ClimaPage(navHostController, lat = lat, lon = lon, nombre = nombre)
            }
        }
  }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(){
    TopAppBar(
        title = { Text(text = "WeatherDay") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
    )
}