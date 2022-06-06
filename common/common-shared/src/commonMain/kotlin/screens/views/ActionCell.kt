package screens.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.Odyssey

@Composable
fun ActionCell(text: String, icon: ImageVector?, onClick: () -> Unit) {
    Column(modifier = Modifier
        .clickable { onClick.invoke() }
        .fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            Text(text = text, color = Odyssey.color.primaryText, fontSize = 18.sp)
            Spacer(modifier = Modifier.weight(1f))

            icon?.let {
                Icon(icon, contentDescription = "", tint = Odyssey.color.controlColor)
            }
        }
    }
}