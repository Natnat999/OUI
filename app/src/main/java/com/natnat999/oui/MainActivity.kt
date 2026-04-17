package com.natnat999.oui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.natnat999.oui.ui.theme.OUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OUITheme {
                ChatScreen()
            }
        }
    }
}

data class Message(val text: String, val isUser: Boolean)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    var messages by remember { mutableStateOf(listOf<Message>()) }
    var textState by remember { mutableStateOf(TextFieldValue("")) }
    var isDownloading by remember { mutableStateOf(true) } // Simulation du téléchargement au premier démarrage
    var progress by remember { mutableFloatStateOf(0f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("OUI") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        },
        bottomBar = {
            if (!isDownloading) {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentPadding = PaddingValues(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            value = textState,
                            onValueChange = { textState = it },
                            modifier = Modifier.weight(1f),
                            placeholder = { Text("Parlez-moi...") },
                            shape = RoundedCornerShape(24.dp),
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = {
                            if (textState.text.isNotBlank()) {
                                messages = messages + Message(textState.text, true)
                                // Simulation de réponse IA
                                messages = messages + Message("Réponse du modèle local...", false)
                                textState = TextFieldValue("")
                            }
                        }) {
                            Icon(Icons.Filled.Send, contentDescription = "Envoyer", tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    ) { padding ->
        if (isDownloading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Téléchargement du modèle local...", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    LinearProgressIndicator(progress = progress, modifier = Modifier.fillMaxWidth(0.8f))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("${(progress * 100).toInt()}%", style = MaterialTheme.typography.bodySmall)
                    
                    // Simulation du téléchargement (en prod, lié au ModelManager)
                    LaunchedEffect(Unit) {
                        for (i in 0..100) {
                            progress = i / 100f
                            kotlinx.coroutines.delay(50)
                        }
                        isDownloading = false
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item { Spacer(modifier = Modifier.height(8.dp)) }
                items(messages) { message ->
                    ChatBubble(message)
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: Message) {
    val alignment = if (message.isUser) Alignment.End else Alignment.Start
    val color = if (message.isUser) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
    val textColor = if (message.isUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSecondaryContainer
    val shape = if (message.isUser) 
        RoundedCornerShape(16.dp, 16.dp, 0.dp, 16.dp) 
    else 
        RoundedCornerShape(16.dp, 16.dp, 16.dp, 0.dp)

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = alignment) {
        Box(
            modifier = Modifier
                .clip(shape)
                .background(color)
                .padding(12.dp)
        ) {
            Text(text = message.text, color = textColor)
        }
    }
}
