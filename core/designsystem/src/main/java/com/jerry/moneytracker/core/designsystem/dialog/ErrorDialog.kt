 package com.jerry.moneytracker.core.designsystem.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jerry.moneytracker.core.designsystem.R
import com.jerry.moneytracker.core.designsystem.theme.MoneyTrackerTheme
import com.jerry.moneytracker.core.designsystem.theme.dimens

@Composable
fun ErrorDialog(
    text: String = "this is text",
    onRetryRequest: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    CustomAlertDialog(
        icon = {
            Icon(
                modifier = Modifier.size(MaterialTheme.dimens.dimen160).padding(top = MaterialTheme.dimens.dimen32),
                tint = MaterialTheme.colorScheme.error,
                imageVector = Icons.Outlined.Error,
                contentDescription = null
            )
        },
        title = null,
        message = text,
        leftBtnStr= stringResource(R.string.core_designsystem_close),
        onLeftClick = onDismissRequest,
        rightBtnStr = stringResource(R.string.core_designsystem_retry),
        onRightClick = onRetryRequest
    )
}

@Preview
@Composable
fun ErrorDialogPreview(){
    MoneyTrackerTheme {
        ErrorDialog()
    }
}
