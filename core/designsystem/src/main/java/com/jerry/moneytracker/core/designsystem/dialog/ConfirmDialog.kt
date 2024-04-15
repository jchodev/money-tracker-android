package com.jerry.moneytracker.core.designsystem.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.jerry.moneytracker.core.designsystem.R
import com.jerry.moneytracker.core.designsystem.theme.MoneyTrackerTheme
import com.jerry.moneytracker.core.designsystem.theme.dimens

@Composable
fun ConfirmDialog(
    title: String? = "this is title",
    text: String = "this is text",
    onConfirmRequest: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    CustomAlertDialog(
        icon = {
            Icon(
                modifier = Modifier.size(MaterialTheme.dimens.dimen160).padding(top = MaterialTheme.dimens.dimen32),
                tint = MaterialTheme.colorScheme.primary,
                imageVector = ImageVector.vectorResource(R.drawable.question),
                contentDescription = null
            )
        },
        title = title,
        message = text,
        leftBtnStr= stringResource(R.string.core_designsystem_cancel),
        onLeftClick = onDismissRequest,
        rightBtnStr = stringResource(R.string.core_designsystem_confirm),
        onRightClick = onConfirmRequest
    )
}

@Preview
@Composable
fun ConfirmDialogPreview(){
    MoneyTrackerTheme {
        ConfirmDialog()
    }
}
