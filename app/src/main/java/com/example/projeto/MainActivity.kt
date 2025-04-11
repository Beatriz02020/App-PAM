package com.example.projeto

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projeto.ui.theme.ProjetoTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.initialize

class MainActivity : ComponentActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.initialize(this)
        enableEdgeToEdge()
        setContent {
            ProjetoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppAula()
                }
            }
        }
    }
}

@Composable
fun AppAula() {
    var nome by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }
    
    val context = LocalContext.current
    val db = Firebase.firestore

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 24.dp),
            Arrangement.Center
        ) {
            Text(
                text = "App Agendamento",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        OutlinedTextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)
        )
        
        OutlinedTextField(
            value = endereco,
            onValueChange = { endereco = it },
            label = { Text("Endereço") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)
        )
        
        OutlinedTextField(
            value = bairro,
            onValueChange = { bairro = it },
            label = { Text("Bairro") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)
        )
        
        OutlinedTextField(
            value = cep,
            onValueChange = { cep = it },
            label = { Text("CEP") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)
        )
        
        OutlinedTextField(
            value = cidade,
            onValueChange = { cidade = it },
            label = { Text("Cidade") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)
        )
        
        OutlinedTextField(
            value = estado,
            onValueChange = { estado = it },
            label = { Text("Estado") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp, 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 18.dp),
            Arrangement.Center
        ) {
            Column(modifier = Modifier.fillMaxWidth(0.35f)) {
                Button(
                    onClick = {
                        if (nome.isBlank() || endereco.isBlank() || bairro.isBlank() || 
                            cep.isBlank() || cidade.isBlank() || estado.isBlank()) {
                            Toast.makeText(context, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        
                        val usuario = hashMapOf(
                            "nome" to nome,
                            "endereco" to endereco,
                            "bairro" to bairro,
                            "cep" to cep,
                            "cidade" to cidade,
                            "estado" to estado


                        )
                        
                        // Add a new document with a generated ID
                        db.collection("usuarios")
                            .add(usuario)
                            .addOnSuccessListener { documentReference ->
                                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                                Toast.makeText(context, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show()
                                
                                // Limpar os campos após salvar
                                nome = ""
                                endereco = ""
                                bairro = ""
                                cep = ""
                                cidade = ""
                                estado = ""
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                                Toast.makeText(context, "Erro ao salvar: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                ) {
                    Text(
                        text = "Salvar",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAulaPreview() {
    ProjetoTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            AppAula()
        }
    }
}