package com.jerry.moneytracker.core.designsystem.edittext


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.jerry.moneytracker.core.designsystem.text.ErrorText
import com.jerry.moneytracker.core.designsystem.theme.MoneyTrackerTheme


@Composable
fun MmasTextEdit(
    modifier : Modifier = Modifier,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(
        disabledBorderColor = MaterialTheme.colorScheme.onSurface
    ),
    value: String = "",
    error: String? = null,
    onValueChange: (String) -> Unit = {},
    placeHolder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    readOnly: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxChar: Int = Int.MAX_VALUE,
    textEditOnClick: (() -> Unit)? = null
) {
    Column (
        modifier = modifier.fillMaxWidth()
    ) {

        OutlinedTextField(
                modifier = Modifier.fillMaxWidth().clickable {
                    textEditOnClick?.let {
                        textEditOnClick()
                    }
                },
                enabled = (textEditOnClick == null),
                readOnly = readOnly,
                singleLine = true,
                value = value,
                colors = colors,
                onValueChange = {
                    if (it.length <= maxChar) {
                        onValueChange.invoke(it)
                    }
                },
                placeholder = {
                    Text(
                        text = placeHolder,
                        color = MaterialTheme.colorScheme.outline,
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                textStyle = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                ),
                trailingIcon = trailingIcon,
                leadingIcon = leadingIcon,
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = imeAction
                ),
                visualTransformation = visualTransformation,
            )
        error?.let {
            ErrorText(error = error)
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0xFF0000FF)
@Composable
private fun MmasTextEditWithTextValuePreview(){
    MoneyTrackerTheme {
        MmasTextEdit(
            modifier = Modifier.fillMaxWidth(),
            value = "this is text",
            placeHolder = "this is place holder"
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0000FF)
@Composable
private fun GleyTextEditWithPlaceHolderPreview(){
    MoneyTrackerTheme {
        MmasTextEdit(
            modifier = Modifier.fillMaxWidth(),
            placeHolder = "this is place holder"
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0000FF)
@Composable
private fun GleyTextEditWithErrorPreview(){
    MoneyTrackerTheme {
        MmasTextEdit(
            modifier = Modifier.fillMaxWidth(),
            placeHolder = "this is place holder",
            error = "this is error"
        )
    }
}

