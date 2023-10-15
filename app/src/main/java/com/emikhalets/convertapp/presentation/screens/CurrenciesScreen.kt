package com.emikhalets.convertapp.presentation.screens
// TODO render error dialog with effect

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emikhalets.convertapp.R
import com.emikhalets.convertapp.core.ui.CurrencyVisualTransformation
import com.emikhalets.convertapp.core.ui.extentions.ScreenPreview
import com.emikhalets.convertapp.core.ui.theme.AppTheme
import com.emikhalets.convertapp.presentation.screens.CurrenciesContract.Action
import com.emikhalets.core.common.date.formatFullDate
import com.emikhalets.core.common.date.localDate
import com.emikhalets.core.common.date.timestamp
import com.emikhalets.core.ui.component.AppSwipeToDelete
import com.emikhalets.core.ui.component.AppSwipeToRefresh
import com.emikhalets.core.ui.component.DialogError
import com.emikhalets.core.ui.component.FloatingButtonBox
import java.util.Date

@Composable
internal fun CurrenciesScreen(
    viewModel: CurrenciesViewModel,
) {
    val state by viewModel.state.collectAsState()

    ScreenContent(
        state = state,
        onActionSent = viewModel::setAction,
    )
}

@Composable
private fun ScreenContent(
    state: CurrenciesContract.State,
    onActionSent: (Action) -> Unit,
) {
    AppSwipeToRefresh(
        isLoading = state.isLoading,
        onRefresh = { onActionSent(Action.UpdateExchanges) }
    ) {
        FloatingButtonBox(
            onClick = { onActionSent(Action.Input.NewCurrencyVisible(true)) },
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                ExchangesDateBox(
                    timestamp = state.date,
                    isOldExchanges = state.isOldExchanges,
                )
                CurrenciesList(
                    currencies = state.currencies,
                    baseCode = state.baseCode,
                    onCurrencyDeleteClick = { onActionSent(Action.DeleteCurrency(it)) },
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                )
            }
        }
        BaseCurrencyBox(
            currencies = state.currencies,
            baseCode = state.baseCode,
            baseValue = state.baseValue,
            onBaseCodeClick = { onActionSent(Action.Input.BaseCodeClick(it)) },
            onBaseValueChange = { onActionSent(Action.Input.BaseValueChange(it)) },
        )
    }

    NewCurrencyBox(
        code = state.newCurrencyCode,
        isVisible = state.newCurrencyVisible,
        onCodeChange = { onActionSent(Action.Input.NewCurrencyChange(it)) },
        onVisibleChange = { onActionSent(Action.Input.NewCurrencyVisible(it)) },
        onSaveClick = { onActionSent(Action.AddCurrency) }
    )

    DialogError(
        message = state.error,
        onDismiss = { onActionSent(Action.DropError) }
    )
}

@Composable
private fun ExchangesDateBox(
    timestamp: Long,
    isOldExchanges: Boolean,
) {
    if (timestamp > 0) {
        val dateText = if (isOldExchanges) {
            stringResource(R.string.old_values_warning, timestamp.formatFullDate())
        } else {
            stringResource(R.string.valid_values, timestamp.formatFullDate())
        }

        val backColor = if (isOldExchanges) MaterialTheme.colors.error else Color.Transparent

        Text(
            text = dateText,
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(backColor)
                .padding(8.dp)
        )
    }
}

@Composable
private fun CurrenciesList(
    currencies: List<Pair<String, Long>>,
    baseCode: String,
    onCurrencyDeleteClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier.padding(8.dp)) {
        items(currencies, key = { it.first }) { (code, value) ->
            AppSwipeToDelete(
                onDeleteClick = { onCurrencyDeleteClick(code) },
                modifier = Modifier.fillMaxWidth()
            ) {
                val isBase = code == baseCode
                Text(
                    text = "$code :",
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 24.sp,
                    fontWeight = if (isBase) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                )
                Text(
                    text = formatValue(value),
                    color = MaterialTheme.colors.onBackground,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(3f)
                )
            }
        }
    }
}

@Composable
private fun BaseCurrencyBox(
    currencies: List<Pair<String, Long>>,
    baseValue: String,
    baseCode: String,
    onBaseCodeClick: (String) -> Unit,
    onBaseValueChange: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    Column {
        CurrenciesChooser(
            currencies = currencies,
            baseCode = baseCode,
            onBaseCodeClick = {
                onBaseCodeClick(it)
                onBaseValueChange("")
            },
        )
        OutlinedTextField(
            value = baseValue,
            onValueChange = { onBaseValueChange(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            visualTransformation = CurrencyVisualTransformation(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colors.onBackground,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        )
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CurrenciesChooser(
    currencies: List<Pair<String, Long>>,
    baseCode: String,
    onBaseCodeClick: (String) -> Unit,
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp, 2.dp)
    ) {
        currencies.forEach { (code, _) ->
            val backColor = if (code == baseCode) {
                MaterialTheme.colors.secondary
            } else {
                MaterialTheme.colors.secondary.copy(alpha = 0.4f)
            }
            Text(
                text = code,
                fontSize = 12.sp,
                fontWeight = if (code == baseCode) FontWeight.SemiBold else FontWeight.Normal,
                modifier = Modifier
                    .padding(4.dp, 2.dp)
                    .background(backColor, RoundedCornerShape(40))
                    .clip(RoundedCornerShape(40))
                    .clickable { onBaseCodeClick(code) }
                    .padding(4.dp)
            )
        }
    }
}

private fun formatValue(value: Long): String {
    val firstPart = value / 100
    val secondPart = value % 100
    return "$firstPart.$secondPart"
}

@ScreenPreview
@Composable
private fun Preview() {
    AppTheme {
        ScreenContent(
            state = CurrenciesContract.State(
                currencies = listOf("USD" to 1200, "RUB" to 100000, "VND" to 750000000),
                baseCode = "RUB",
                baseValue = "1200",
                date = Date().time.localDate().timestamp(),
                isLoading = true,
            ),
            onActionSent = {},
        )
    }
}
