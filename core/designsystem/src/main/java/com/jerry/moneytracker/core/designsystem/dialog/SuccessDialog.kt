package com.jerry.moneytracker.core.designsystem.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jerry.moneytracker.core.designsystem.theme.MoneyTrackerTheme
import com.jerry.moneytracker.core.designsystem.theme.dimens
import com.jerry.moneytracker.core.designsystem.R

@Composable
fun SuccessDialog(
    text: String = stringResource(id = R.string.core_designsystem_completed),
    onDismissRequest: () -> Unit = {},
) {
    CustomAlertDialog(
        icon = {
            Icon(
                modifier = Modifier
                    .size(MaterialTheme.dimens.dimen160)
                    .padding(top = MaterialTheme.dimens.dimen32),
                tint = MaterialTheme.colorScheme.primary,
                imageVector = Icons.Rounded.CheckCircleOutline,
                contentDescription = null
            )
        },
        title = null,
        message = text,
        leftBtnStr= stringResource(android.R.string.ok),
        onLeftClick = onDismissRequest,
        rightBtnStr = null,
    )
}

@Preview
@Composable
fun SuccessDialogPreview(){
    MoneyTrackerTheme {
        SuccessDialog()
    }
}