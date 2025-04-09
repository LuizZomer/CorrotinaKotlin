package com.luiz.corrotinas

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            semaforo()
        }
    }
}

@Composable
fun semaforo() {
    var corAtual by remember { mutableStateOf(Color.Red) }
    var isAmareloPisca by remember { mutableStateOf(false) }
    var estado by remember { mutableStateOf(true) }

    // Função para alternar as cores
    fun alternarCor() {
        // Se o semáforo estiver vermelho ou verde, mude para amarelo e faça ele piscar
        if (corAtual != Color.Yellow || estado) {
            corAtual = Color.Yellow
            isAmareloPisca = true
        }
        if (!estado){
            corAtual = Color.Green
        }
    }


    LaunchedEffect(Unit) { // <- Observa apenas na primeira composição
        while (true) {
            if (isAmareloPisca) {
                for (i in 1..4) {
                    corAtual = Color.Gray
                    delay(200)
                    corAtual = Color.Yellow
                    delay(200)
                }

                corAtual = Color.Green
                isAmareloPisca = false
                estado = false
            } else {
                delay(3000)
                corAtual = when (corAtual) {
                    Color.Red -> Color.Yellow
                    Color.Yellow -> Color.Green
                    Color.Green -> Color.Red
                    else -> Color.Red
                }
                estado = true
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .padding(8.dp)
                .background(Color.Black, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            // Cores do semáforo
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Círculo vermelho
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                        .background(
                            if (corAtual == Color.Red) Color.Red else Color.Gray,
                            shape = CircleShape
                        )
                )
                // Círculo amarelo
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                        .background(
                            if (corAtual == Color.Yellow) Color.Yellow else Color.Gray,
                            shape = CircleShape
                        )
                )
                // Círculo verde
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                        .background(
                            if (corAtual == Color.Green) Color.Green else Color.Gray,
                            shape = CircleShape
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { alternarCor() }) {
            Text("Piscar Amarelo")
        }
    }
}
