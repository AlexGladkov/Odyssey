package ru.alexgladkov.common.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController

// Delete this
@Composable
fun Screen1() {
    val rootController = LocalRootController.current

    LazyColumn {
        for (i in 0..50) {
            item {
                Text(modifier = Modifier.clickable { rootController.push("screen2", i) }
                    .fillMaxWidth().padding(16.dp), text = "City number $i")
            }
        }
    }
}

@Composable
fun Screen2(cityNumber: Int) {
    val rootController = LocalRootController.current
    var count by rememberSaveable { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "City Number $cityNumber",
            fontSize = 28.sp,
            fontWeight = FontWeight.Medium
        )

        Row {
            Text(modifier = Modifier.padding(16.dp), text = "Population is $count")
            Button(onClick = {
                count++
            }) {
                Text("Increase")
            }
        }

        Text(modifier = Modifier.padding(16.dp), text = "Navigation")

        Row(modifier = Modifier.fillMaxWidth().padding(start = 16.dp)) {
            Button(onClick = {
                rootController.popBackStack()
            }) {
                Text("Go back")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                rootController.present("flow1")
            }) {
                Text("Open modal")
            }
        }
    }
}

@Composable
fun Screen5() {
    val rootController = LocalRootController.current

    Column(modifier = Modifier.fillMaxSize()) {
        Text(modifier = Modifier.padding(16.dp), text = "Navigation")
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                rootController.popBackStack()
            }) {
                Text("Close")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                rootController.push("screen6", params = "Alex Gladkov")
            }) {
                Text("Open Profile")
            }
        }
    }
}

@Composable
fun Screen6(username: String) {
    val rootController = LocalRootController.current

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(16.dp), fontSize = 28.sp, fontWeight = FontWeight.Medium,
            text = "Hello $username"
        )

        Row(modifier = Modifier.padding(16.dp)) {
            Button(onClick = {
                rootController.popBackStack()
            }) {
                Text("Go Back")
            }

            Spacer(Modifier.width(16.dp))

            Button(onClick = {
                rootController.parentRootController?.present("screen7")
            }) {
                Text("Open Settings")
            }
        }
    }
}

@Composable
fun Screen7() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(modifier = Modifier.padding(16.dp), text = "Settings")
    }
}
