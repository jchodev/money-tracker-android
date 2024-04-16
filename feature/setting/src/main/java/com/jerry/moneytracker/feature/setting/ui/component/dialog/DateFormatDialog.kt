package com.jerry.moneytracker.feature.setting.ui.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.jerry.moneytracker.core.designsystem.theme.MoneyTrackerTheme
import com.jerry.moneytracker.core.ui.ext.convertMillisToDate
import com.jerry.moneytracker.core.ui.preview.DevicePreviews
import com.jerry.moneytracker.feature.setting.R

import java.util.Calendar


@Composable
fun DateFormatDialog(
    modifier: Modifier = Modifier,
    itemList: List<String> = listOf(),
    defaultItem: String = "yyyy-MM-dd",
    onDismissRequest: () -> Unit = {},
    onItemSelected: (item: String) -> Unit = {},
){
    CommonSettingDialog(
        onItemSelected = onItemSelected,
        modifier = modifier,
        itemList = itemList,
        onDismissRequest = onDismissRequest,
        title = stringResource(id = R.string.feature_setting_select_date_format),
        headlineContent = {
            Column{
                Text(
                    text =  it,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text =  "e.g " + Calendar.getInstance().timeInMillis.convertMillisToDate(it),
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        },
        defaultItem = defaultItem
    )
}

@DevicePreviews
@Composable
private fun DateFormatDialogPreview(){
    MoneyTrackerTheme {
        DateFormatDialog(
            itemList = listOf(
                "dd-MM-yyyy",
                "dd MMM yyyy",
                "dd/MM/yyyy",
                "yyyy-MM-dd"
            ),
            defaultItem = "yyyy-MM-dd"
        )
    }
}