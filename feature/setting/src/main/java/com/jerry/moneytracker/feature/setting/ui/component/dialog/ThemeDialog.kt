package com.jerry.moneytracker.feature.setting.ui.component.dialog

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.jerry.moneytracker.core.designsystem.theme.MoneyTrackerTheme
import com.jerry.moneytracker.core.model.data.ThemeType
import com.jerry.moneytracker.core.ui.preview.DevicePreviews
import com.jerry.moneytracker.feature.setting.R
import com.jerry.moneytracker.feature.setting.ui.ext.getString


@Composable
fun ThemeDialog(
    modifier: Modifier = Modifier,
    itemList: List<ThemeType> = listOf(),
    onDismissRequest: () -> Unit = {},
    defaultItem: ThemeType,
    onItemSelected: (item: ThemeType) -> Unit = {},
){

    CommonSettingDialog(
        onItemSelected = onItemSelected,
        modifier = modifier,
        itemList = itemList,
        onDismissRequest = onDismissRequest,
        title = stringResource(id = R.string.feature_setting_select_theme),
        headlineContent = {
            Text(
                text =  it.getString(),
                style = MaterialTheme.typography.titleMedium,
            )
        },
        defaultItem = defaultItem
    )
}

@DevicePreviews
@Composable
private fun ThemeDialogPreview(){
    MoneyTrackerTheme {
        ThemeDialog(
            itemList = listOf(
                ThemeType.LIGHT,
                ThemeType.DARK,
            ),
            defaultItem = ThemeType.DARK
        )
    }
}

