package com.example.weatherday.presentacion.ciudades

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
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
fun CiudadesView(
    modifier: Modifier = Modifier,
    state: CiudadesEstado,
    onAction:(CiudadesIntencion)->Unit
){

    var value by remember{mutableStateOf("")}

    Column(modifier = modifier
        .fillMaxSize()
        .padding(20.dp),
        //verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text(text = "Bienvenido!",
            modifier = Modifier.padding(10.dp)
        )
        TextField(
            value = value,
            onValueChange = {
                value = it
                onAction(CiudadesIntencion.Buscar(value))},
            label = { Text(text = "Buscar Ciudad")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )
        when(state){
            CiudadesEstado.Cargando -> Text(text = "Cargando...")
            is CiudadesEstado.Error -> Text(text = state.mensaje)
            is CiudadesEstado.Resultados -> ListaDeCiudades(state.ciudades) {
                onAction(
                    CiudadesIntencion.Seleccionar(it)
                )
            }
            CiudadesEstado.Vacio -> Text(text = "Sin Resultados")
        }
        //Button(onClick = { onAction(CiudadesIntencion.Seleccionar(0)) }) {
        //    Text(text = "Siguiente")
        //}
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaDeCiudades(ciudades: List<Ciudad>, onSelect: (Ciudad)->Unit){
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