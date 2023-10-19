package com.emikhalets.convertapp.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.emikhalets.convertapp.R
import com.emikhalets.convertapp.core.ui.extentions.BoxPreview
import com.emikhalets.convertapp.core.ui.theme.AppTheme
import com.emikhalets.convertapp.core.ui.component.AppDialog

@Composable
fun NewCurrencyBox(
    code: String,
    isVisible: Boolean,
    onCodeChange: (String) -> Unit,
    onVisibleChange: (Boolean) -> Unit,
    onSaveClick: (String) -> Unit,
    onDismiss: () -> Unit = {},
) {
    if (!isVisible) return
    val focusRequester = remember { FocusRequester() }
    AppDialog(onDismiss = onDismiss) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            OutlinedTextField(
                value = code,
                onValueChange = onCodeChange,
                placeholder = { Text(text = stringResource(R.string.currency_code_hint)) },
                singleLine = true,
                keyboardActions = KeyboardActions(onDone = { onSaveClick(code) }),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .focusRequester(focusRequester)
            )
            Icon(
                painter = painterResource(R.drawable.round_save_24),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clickable { onSaveClick(code) }
                    .padding(12.dp)
            )
            Icon(
                painter = painterResource(R.drawable.round_close_24),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .clickable { onVisibleChange(false) }
                    .padding(12.dp)
            )
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@BoxPreview
@Composable
private fun Preview() {
    AppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            NewCurrencyBox(
                code = "USD",
                isVisible = true,
                onCodeChange = {},
                onVisibleChange = {},
                onSaveClick = {},
            )
        }
    }
}
