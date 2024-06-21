package com.example.weatherday.presentacion.ciudades

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherday.repository.modelos.Ciudad

@Composable
fun CiudadesView (
    modifier: Modifier = Modifier,
    state : CiudadesEstado,
    onAction: (CiudadesIntencion)->Unit
) {
    var value by remember{ mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize().padding(20.dp),
        //verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = "Bienvenido!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        TextField(
            value = value,
            label = { Text(text = "Buscar Nombre de Ciudad") },
            onValueChange = {
                value = it
                onAction(CiudadesIntencion.Buscar(it))
            },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )
        when(state) {
            CiudadesEstado.Cargando -> VistaCargando()
            is CiudadesEstado.Error -> ErrorView(mensaje = state.mensaje)
            is CiudadesEstado.Resultado -> CiudadesListado(state.ciudades) {
                onAction(CiudadesIntencion.Seleccionar(it))
            }
            CiudadesEstado.Vacio -> VistaVacia()
        }
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
fun VistaVacia(){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Sin Datos")
        Icon(Icons.Filled.Info, contentDescription = "Sin Datos")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CiudadesListado(ciudades: List<Ciudad>, onSelect: (Ciudad)->Unit){
    LazyColumn{
        items(items = ciudades) {
            Card (onClick = { onSelect(it)},
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ){
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = it.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary)
                    Text(text = it.country,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                }

            }
        }
    }
}