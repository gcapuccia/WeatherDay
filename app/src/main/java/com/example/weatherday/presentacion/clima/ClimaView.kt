package com.example.weatherday.presentacion.clima

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import com.example.weatherday.ui.theme.WeatherDayTheme


@Composable
fun ClimaView(
    modifier: Modifier = Modifier.padding(top = 60.dp),
    state : ClimaEstado,
    onAction: (ClimaIntencion)->Unit
) {

    var dataToShare by remember { mutableStateOf("¡Comparte este dato!") }

    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        onAction(ClimaIntencion.dameClima)
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(text = "Clima Actual",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.padding(5.dp))
            ShareButton(textToShare = dataToShare)
            /*Button(
                onClick = { *//*TODO Codigo para Compartir Datos*//*
            }) {
                Icon(
                    Icons.Filled.Share,
                    contentDescription = "Compartir Info",
                    tint = LocalContentColor.current
                )
            }*/
        }

        when(state){
            is ClimaEstado.Error -> ErrorView(mensaje = state.mensaje)
            is ClimaEstado.Exitoso -> ClimaView(
                ciudad = state.ciudad,
                temperatura = state.temperatura,
                descripcion = state.descripcion,
                st = state.st
            )
            ClimaEstado.Vacio -> VistaCargando()
            ClimaEstado.Cargando -> VistaVacia()
        }
    }
}

@Composable
fun ShareButton(textToShare: String) {
    val context = LocalContext.current
    val shareLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { _ -> context }

    Button(
        onClick = {
            val sendIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, textToShare)
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            shareLauncher.launch(shareIntent)
        },
        modifier = Modifier.padding(1.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = "Compartir",
            tint = LocalContentColor.current
        )
    }
}




@Composable
fun ErrorView(mensaje: String){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Filled.Close, contentDescription = "Error")
        Text(text = "Error: " + mensaje)
    }
}

@Composable
fun ClimaView(ciudad: String, temperatura: Double, descripcion: String, st:Double){
    Column(modifier = Modifier.padding(16.dp)
    ) {
        Card(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()){
            Column(modifier = Modifier.padding(10.dp)) {
                Text(text = ciudad, style = MaterialTheme.typography.titleMedium)
                Text(text = "${temperatura}°", style = MaterialTheme.typography.titleLarge)
                Text(text = descripcion, style = MaterialTheme.typography.bodyMedium)
                Text(text = "Sensación térmica: ${st}°", style = MaterialTheme.typography.bodyMedium)
            }
        }
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

@Preview(showBackground = true)
@Composable
fun ClimaPreviewVacio() {
    WeatherDayTheme {
        ClimaView(state = ClimaEstado.Vacio, onAction = {})
    }
}



@Preview(showBackground = true)
@Composable
fun ClimaPreviewError() {
    WeatherDayTheme {
        ClimaView(state = ClimaEstado.Error(""), onAction = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ClimaPreviewExitoso() {
    WeatherDayTheme {
        ClimaView(state = ClimaEstado.Exitoso(ciudad = "Mendoza", temperatura = 0.0), onAction = {})
    }
}