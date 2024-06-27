@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterial3Api::class
)

package com.example.composechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val messageList = remember {
                mutableStateListOf<String>()
            }

            Box(
                Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                        .padding(top = 50.dp, start = 10.dp, end = 10.dp)
                ) {
                    itemsIndexed(messageList) { index, item ->
                        Message(item, index % 2 == 0)

                    }
                }
                Box(
                    modifier = Modifier
                        .padding(bottom = 50.dp)
                        .align(Alignment.BottomCenter)
                )
                {
                    Type(onClick = { text ->
                        messageList.add(text)

                    })
                }
            }
        }
    }
}

@Composable
fun Message(message: String, isSender: Boolean) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isSender) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = if (isSender) 48f else 0f,
                        topEnd = if (isSender) 0f else 48f,
                        bottomStart = 48f,
                        bottomEnd = 48f,
                    )
                )
                .background(
                    if (isSender) Color.Green else Color.White
                )
                .padding(16.dp)

        ) {
            Text(text = message)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Type(onClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(start = 20.dp, end = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        val textValue = remember { mutableStateOf(value = "") }
        TextField(
            value = textValue.value,
            onValueChange = { newText ->
                textValue.value = newText
            },
            shape = RoundedCornerShape(24.dp),
            placeholder = {
                Text(
                    text = "Type something...", color = Color.LightGray
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.LightGray,
                unfocusedIndicatorColor = Color.LightGray
            ),
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = {
                onClick(textValue.value)
                textValue.value = ""
            },
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.White)
                .align(Alignment.CenterVertically)
        ) {
            Icon(imageVector = Icons.Filled.Send, contentDescription = "Send")
        }
    }
}