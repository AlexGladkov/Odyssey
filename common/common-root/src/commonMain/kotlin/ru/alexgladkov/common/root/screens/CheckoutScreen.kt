package ru.alexgladkov.common.root.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.odyssey.core.local.LocalRootController

@Composable
fun CheckoutScreen() {
    val rootController = LocalRootController.current

    Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Checkout", fontSize = 26.sp, fontWeight = FontWeight.Medium)

        Button(
            modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth(),
            onClick = {
                rootController.popBackStack()
            }) {
            Text("Finish")
        }
    }
}