package com.emikhalets.convertapp.core.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.emikhalets.convertapp.R
import com.emikhalets.convertapp.core.ui.extentions.BoxPreview
import com.emikhalets.convertapp.core.ui.theme.AppTheme
import com.emikhalets.convertapp.domain.StringValue
import com.emikhalets.convertapp.domain.asString

@Composable
fun DialogError(
    message: StringValue?,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
) {
    if (message == null) return
    AppBottomDialog(
        cancelable = true,
        onDismiss = onDismiss
    ) {
        Column(modifier = modifier.fillMaxWidth()) {
            Text(
                text = message.asString(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            TextButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp)
            ) {
                Text(text = stringResource(R.string.ok))
            }
        }
    }
}

@BoxPreview
@Composable
private fun Preview() {
    AppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            DialogError(
                message = StringValue.create("Card error text message bla bla bla."),
                modifier = Modifier
            )
        }
    }
}
