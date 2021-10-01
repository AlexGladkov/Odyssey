package ru.alexgladkov.common.compose.screens.chain

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.common.compose.NavigationTree
import ru.alexgladkov.odyssey.core.RootController

@Composable
fun ChainScreen(
    rootController: RootController,
    number: Int, flowName: String
) {
    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        Text(flowName, fontSize = 36.sp, fontWeight = FontWeight.Medium, color = Color.Black)
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "Current Number -> $number",
            color = Color.Black,
            fontSize = 14.sp
        )
        Button(
            modifier = Modifier.padding(top = 24.dp).fillMaxWidth(),
            onClick = {
                rootController.launch(NavigationTree.Container.Chain, number + 1)
            }
        ) {
            Text("Open next")
        }

        Button(
            modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
            onClick = {
                rootController.popBackStack()
            }
        ) {
            Text("Go previous")
        }
    }
}