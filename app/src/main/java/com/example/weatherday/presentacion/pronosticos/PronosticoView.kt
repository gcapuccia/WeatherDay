package com.example.weatherday.presentacion.pronosticos

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.example.weatherday.repository.modelos.ListForecast
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PronosticoView(
    modifier: Modifier = Modifier,
    state : PronosticoEstado,
    onAction: (PronosticoIntencion)->Unit
) {
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        onAction(PronosticoIntencion.actualizarClima)
    }
    Text(
        text = "Pronosticos",
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(3.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(state){
            is PronosticoEstado.Error -> VistaError(mensaje = state.mensaje)
            is PronosticoEstado.Exitoso -> PronosticoView(state.climas)
            PronosticoEstado.Vacio -> VistaCargando()
            PronosticoEstado.Cargando -> VistaVacia()
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun VistaVacia(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "No hay nada por aqui")
        Icon(Icons.Filled.Refresh, contentDescription = "Sin Datos")
    }
}

@Composable
fun VistaCargando(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Cargando...")
        Icon(Icons.Filled.Refresh, contentDescription = "Cargando")
    }
}

@Composable
fun VistaError(mensaje: String){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Filled.Close, contentDescription = "Error")
        Text(text = "Error: " + mensaje)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun convertirTimestamp(timestamp: Long): String {
    val instant = Instant.ofEpochSecond(timestamp)
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")
        .withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PronosticoView(climas: List<ListForecast>){
    LazyColumn {
        items(items = climas) {
            Card(modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()) {
                val fecha = convertirTimestamp(it.dt)
                Text(text = fecha,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary)
                Text(text = "Temperatura: ${it.main.temp}º",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary)
                Text(text = "Minima: ${it.main.temp_min}º -> Maxima:${it.main.temp_max}º",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
            }
        }
    }
}


