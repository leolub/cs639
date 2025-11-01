package com.example.circleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.circleapp.ui.theme.CircleAppTheme
import kotlin.math.PI

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CircleAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CircleAreaScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CircleAreaScreen(modifier: Modifier = Modifier) {
    var radius by remember { mutableStateOf("") }
    var area by remember { mutableStateOf<Double?>(null) }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(3f)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.circle),
                contentDescription = "Circle Banner",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
            )
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Siming's Circle Area Calculator",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = radius,
                    onValueChange = {
                        radius = it
                        error = null
                    },
                    label = { Text("Radius r") },
                    isError = error != null,
                    supportingText = {
                        Text(error ?: "Area = π × r²")
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = {
                        val r = radius.trim().toDoubleOrNull()
                        when {
                            r == null -> {
                                error = "Please enter a valid number."
                                area = null
                            }
                            r <= 0.0 -> {
                                error = "Radius must be > 0."
                                area = null
                            }
                            else -> {
                                error = null
                                area = PI * r * r
                            }
                        }
                    },
                    modifier = Modifier
                        .height(56.dp)
                        .widthIn(min = 110.dp)
                ) {
                    Text("Compute")
                }
            }
        }

        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Result", style = MaterialTheme.typography.titleMedium)
                    val txt = if (area != null)
                        "Area = ${"%.4f".format(area)}"
                    else
                        "Begin your calculation."
                    Text(txt)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CircleAreaPreview() {
    CircleAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            CircleAreaScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
    }
}
