package com.jerry.moneytracker.core.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.jerry.moneytracker.core.designsystem.dialog.ErrorDialog
import com.jerry.moneytracker.core.ui.R

@Composable
fun ExceptionErrorDialog(
    exception: Throwable,
    onRetryRequest: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    ErrorDialog(
        text = getMessageFromException(exception = exception),
        onRetryRequest = onRetryRequest,
        onDismissRequest = onDismissRequest,
    )
}

@Composable
private fun getMessageFromException(exception: Throwable): String{
    val errorMessage = exception.message ?: ""
    return if (errorMessage.isEmpty()){
        stringResource(id = R.string.core_ui_common_error)
    }
    else {
        errorMessage
    }
}