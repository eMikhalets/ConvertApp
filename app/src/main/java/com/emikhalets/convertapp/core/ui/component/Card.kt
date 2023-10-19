package com.emikhalets.convertapp.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.convertapp.core.ui.extentions.BoxPreview
import com.emikhalets.convertapp.core.ui.theme.AppTheme

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Card(
        elevation = 4.dp,
        content = { Column { content() } },
        modifier = modifier,
    )
}

@Composable
fun AppHeaderCardColumn(
    header: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    AppCard(modifier) {
        Text(
            text = header,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Divider()
        Column(modifier = Modifier.padding(8.dp)) {
            content()
        }
    }
}

@BoxPreview
@Composable
private fun Preview() {
    AppTheme {
        AppHeaderCardColumn(
            header = "Test header",
            modifier = Modifier.padding(16.dp),
        ) {
            Text(text = "Test content")
        }
    }
}
