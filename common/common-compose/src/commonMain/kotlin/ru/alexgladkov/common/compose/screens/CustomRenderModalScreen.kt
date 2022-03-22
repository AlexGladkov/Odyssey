package ru.alexgladkov.common.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.alexgladkov.odyssey.compose.helpers.noRippleClickable
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.bottom_sheet_navigation.ModalSheetConfiguration

@Composable
fun CustomRenderModalScreen(username: String) {
    val modalSheetController = LocalRootController.current.findModalController()

    Box(modifier = Modifier.fillMaxSize()) {
        Screamer(0.2f) { modalSheetController.removeTopScreen() }


        Card(
            modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth().fillMaxHeight(0.7f),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp
            )
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Stores for $username",
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                    Text(
                        modifier = Modifier.clickable {
                            modalSheetController.removeTopScreen()
                        },
                        text = "Close", fontSize = 14.sp
                    )
                }


                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    for (i in 0..20) {
                        item {
                            Text(modifier = Modifier.clickable {
                                val modalSheetConfiguration =
                                    ModalSheetConfiguration(maxHeight = null, cornerRadius = 16)
                                modalSheetController.presentNew(modalSheetConfiguration) {
                                    DepartmentScreen(storeId = i)
                                }
                            }.fillMaxWidth().padding(16.dp), text = "Store $i")
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(50.dp)
                .background(color = Color.White),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = "Some message for modal")
        }
    }
}


@Composable
private fun Screamer(alpha: Float, onCloseClick: () -> Unit) {
    Box(modifier = Modifier
        .noRippleClickable { onCloseClick.invoke() }
        .fillMaxSize().background(Color.Black.copy(alpha = alpha)))
}