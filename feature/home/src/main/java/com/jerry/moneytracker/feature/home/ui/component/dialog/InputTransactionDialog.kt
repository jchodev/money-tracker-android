package com.jerry.moneytracker.feature.home.ui.component.dialog

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.AvTimer
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jerry.moneytracker.core.common.uistate.UIState
import com.jerry.moneytracker.core.designsystem.button.MmasButton
import com.jerry.moneytracker.core.designsystem.dialog.DatePickerPromptDialog
import com.jerry.moneytracker.core.designsystem.dialog.SuccessDialog
import com.jerry.moneytracker.core.designsystem.dialog.TimePickerPromptDialog
import com.jerry.moneytracker.core.designsystem.edittext.MmasTextEdit
import com.jerry.moneytracker.core.designsystem.text.AutoResizedText
import com.jerry.moneytracker.core.designsystem.theme.MoneyTrackerTheme
import com.jerry.moneytracker.core.designsystem.theme.dimens
import com.jerry.moneytracker.core.designsystem.topbar.MmaTopBar
import com.jerry.moneytracker.core.designsystem.utils.CurrencyAmountInputVisualTransformation
import com.jerry.moneytracker.core.model.data.Category
import com.jerry.moneytracker.core.model.data.Setting
import com.jerry.moneytracker.core.model.data.TransactionType
import com.jerry.moneytracker.core.ui.component.ExceptionErrorDialog
import com.jerry.moneytracker.core.ui.component.LoadingCompose
import com.jerry.moneytracker.core.ui.ext.convertMillisToDate
import com.jerry.moneytracker.core.ui.ext.displayHourMinute
import com.jerry.moneytracker.core.ui.ext.formatAmount
import com.jerry.moneytracker.core.ui.ext.getColors
import com.jerry.moneytracker.core.ui.ext.getImageVector
import com.jerry.moneytracker.core.ui.ext.getString
import com.jerry.moneytracker.core.ui.preview.DevicePreviews
import com.jerry.moneytracker.feature.home.R
import com.jerry.moneytracker.feature.home.ui.data.InputTransactionData
import com.jerry.moneytracker.feature.home.ui.viewmodel.InputDialogViewModel

import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTransactionDialog(
    modifier: Modifier = Modifier,
    viewModel: InputDialogViewModel = hiltViewModel(),
    setting: Setting,
    onDismissRequest: () -> Unit = {},
    properties: DialogProperties = DialogProperties().let {
        DialogProperties(
            dismissOnBackPress = it.dismissOnBackPress,
            dismissOnClickOutside = it.dismissOnClickOutside,
            securePolicy = it.securePolicy,
            usePlatformDefaultWidth = false,
        )
    }
){
    val uiState = viewModel.uiState.collectAsStateWithLifecycle().value

    InputTransactionDialog(
        modifier = modifier,
        uiState = uiState,
        setting = setting,
        onDismissRequest = onDismissRequest,

        properties = properties,

        //form level
        expensesCategories = viewModel.getExpenseCategories(),
        incomeCategories = viewModel.getIncomeCategories(),
        onDescriptionChange = viewModel::onDescriptionChange,
        onDateSelected = viewModel::onDateSelected,
        onTimeSelected = viewModel::onTimeSelected,
        onCategorySelected = viewModel::onCategorySelected,
        onAmountChange = viewModel::onAmountChange,
        onSaveClick = viewModel::saveTransaction,
        onSelectedUri = viewModel::onSelectedUri,

        clearException = viewModel::clearException,
        retry = viewModel::saveTransaction
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTransactionDialog(
    modifier: Modifier = Modifier,
    uiState: UIState<InputTransactionData> = UIState(data = InputTransactionData()),
    setting: Setting,
    onDismissRequest: () -> Unit = {},

    properties: DialogProperties = DialogProperties().let {
        DialogProperties(
            dismissOnBackPress = it.dismissOnBackPress,
            dismissOnClickOutside = it.dismissOnClickOutside,
            securePolicy = it.securePolicy,
            usePlatformDefaultWidth = false,
        )
    },

    incomeCategories: List<Category> = listOf(),
    expensesCategories: List<Category> = listOf(),
    onDescriptionChange: (String)-> Unit = {},
    onDateSelected: (Long) -> Unit = {},
    onTimeSelected: (Int, Int) -> Unit = {
            hour, minute ->
    },
    onCategorySelected: (Category) -> Unit = {},
    onAmountChange: (String) -> Unit = {},
    onSaveClick: () -> Unit ={},
    onSelectedUri: (Uri) -> Unit = {},

    //error state
    clearException: () -> Unit = {},
    retry:() -> Unit = {}
) {

    val transactionDate = uiState.data

    //DatePickerPromptDialog
    var datePickerDialogVisible by remember { mutableStateOf(false) }
    if (datePickerDialogVisible){
        DatePickerPromptDialog(
            onDateSelected = {
                it?.let(onDateSelected)
            },
            onDismiss = { datePickerDialogVisible = false }
        )
    }
    //TimePicker
    var timePickerDialogVisible by remember { mutableStateOf(false) }
    if (timePickerDialogVisible){
        TimePickerPromptDialog(
            defaultHour = transactionDate.transaction.hour ?: Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            defaultMin = transactionDate.transaction.minute ?: Calendar.getInstance().get(Calendar.MINUTE),
            onSelected = { hour, minute ->
                onTimeSelected.invoke(hour, minute)
            },
            onDismiss = { timePickerDialogVisible = false}
        )
    }
    //category select dialog()
    val transactionType = transactionDate.transaction.type ?: TransactionType.EXPENSES
    var categorySelectDialogVisible by remember { mutableStateOf(false) }
    if (categorySelectDialogVisible) {
        CategorySelectDialog(
            transactionType = transactionType,
            list = if (transactionType == TransactionType.EXPENSES) expensesCategories else incomeCategories,
            onDismissRequest = { categorySelectDialogVisible = false },
            onCategorySelected = {
                onCategorySelected.invoke(it)
                categorySelectDialogVisible = false
            }
        )
    }

    BasicAlertDialog(
        modifier = modifier.fillMaxSize(),
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            InputTransactionContent(
                transactionData = uiState.data,
                setting = setting,
                onTopBarLeftClick = onDismissRequest,

                //dialog level
                //dialog level
                showCategoryDialog = {
                    categorySelectDialogVisible = true
                },
                showDateDialog = {
                    datePickerDialogVisible = true
                },
                showTimeDialog = {
                    timePickerDialogVisible = true
                },
                //form level

                onDescriptionChange = onDescriptionChange,
                onAmountChange = onAmountChange,
                onSaveClick = onSaveClick,
                onSelectedUri = onSelectedUri,
            )
            if (uiState.loading){
                LoadingCompose()
            }
            uiState.exception?.let {
                ExceptionErrorDialog(
                    exception = it,
                    onDismissRequest = clearException,
                    onRetryRequest = retry
                )
            }
            if (uiState.data.isSuccess){
                SuccessDialog(
                    onDismissRequest = onDismissRequest
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InputTransactionContent(
    transactionData: InputTransactionData = InputTransactionData(),
    setting: Setting = Setting(),
    onTopBarLeftClick: () -> Unit = {},

    //dialog level
    showCategoryDialog: ()->Unit = {},
    showDateDialog: ()->Unit = {},
    showTimeDialog: ()->Unit = {},

    onDescriptionChange: (String)-> Unit = {},
    onAmountChange: (String) -> Unit = {},
    onSaveClick: () -> Unit ={},
    onSelectedUri: (Uri) -> Unit = {},
){
    val transactionType = transactionData.transaction.type ?: TransactionType.EXPENSES

    Scaffold (
        containerColor = transactionType.getColors().first,
        topBar = {
            MmaTopBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = transactionType.getColors().first,
                    navigationIconContentColor = Color.White,
                    titleContentColor= Color.White,
                ),
                title = if (transactionType == TransactionType.INCOME){
                    stringResource(id = R.string.feature_home_income)
                } else {
                    stringResource(id = R.string.feature_home_expenses)
                },
                onCloseClick = onTopBarLeftClick
            )
        },
        bottomBar = {
            Box(modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .padding(MaterialTheme.dimens.dimen16)) {
                MmasButton(
                    modifier = Modifier.height(MaterialTheme.dimens.dimen56),
                    text = stringResource(id = R.string.feature_home_save),
                    onClick = onSaveClick
                )
            }

        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Column (modifier = Modifier
                .padding(horizontal = MaterialTheme.dimens.dimen32)){
                //how much
                Text(
                    text = stringResource(id = R.string.feature_home_how_much),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White,
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))
                //amount
                AutoResizedText(
                    text =  transactionData.transaction.amount.formatAmount(setting = setting, withPlus = false),
                    //text = state.amountString,
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White,
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))
            Column(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = MaterialTheme.dimens.dimen16,
                            topEnd = MaterialTheme.dimens.dimen16
                        )
                    )
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.dimen32)
            ){

                MmasTextEdit(
                    value = if (transactionData.transaction.category != null) {
                        transactionData.transaction.category!!.type.getString()
                    } else {
                        ""
                    },
                    error = transactionData.categoryError?.let { stringResource(id = it) },
                    placeHolder = stringResource(id = R.string.feature_home_category),
                    readOnly = true,
                    leadingIcon = {
                        if (transactionData.transaction.category != null) {
                            Icon(
                                imageVector = transactionData.transaction.category!!.type.getImageVector(),
                                contentDescription = transactionData.transaction.category!!.type.getString(),
                                modifier = Modifier.size(
                                    MaterialTheme.dimens.dimen24
                                ),
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Category,
                                contentDescription = stringResource(id = R.string.feature_home_select_category),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    trailingIcon = {
                        IconButton(onClick = showCategoryDialog) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                )


                Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))
                MmasTextEdit(
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Description,
                            contentDescription = stringResource(id = R.string.feature_home_description),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    value = transactionData.transaction.description,
                    error = transactionData.descriptionError?.let { stringResource(id = it) },
                    placeHolder = stringResource(id = R.string.feature_home_description),
                    onValueChange = onDescriptionChange
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))

                Row (Modifier.fillMaxWidth()){
                    MmasTextEdit(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = MaterialTheme.dimens.dimen8),
                        value = transactionData.transaction.date?.convertMillisToDate(setting.dateFormat) ?: "",
                        error = transactionData.dateError?.let { stringResource(id = it) },
                        placeHolder = stringResource(id = R.string.feature_home_date),
                        readOnly = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = stringResource(id = R.string.feature_home_date),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        textEditOnClick = showDateDialog
                    )
                    MmasTextEdit(
                        value = transactionData.transaction.displayHourMinute(setting = setting),
                        error = transactionData.timeError?.let { stringResource(id = it) } ,
                        modifier = Modifier.weight(1f),
                        placeHolder = stringResource(id = R.string.feature_home_time),
                        readOnly = true,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.AvTimer,
                                contentDescription = stringResource(id = R.string.feature_home_time),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        },
                        textEditOnClick = showTimeDialog
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))
                MmasTextEdit(
                    value = transactionData.amountString,
                    error = transactionData.amountError?.let { stringResource(id = it) },
                    placeHolder = stringResource(id = R.string.feature_home_amount),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.MonetizationOn,
                            contentDescription = stringResource(id = R.string.feature_home_amount),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    onValueChange = {
                        onAmountChange (
                            if (it.startsWith("0")) {
                                ""
                            } else {
                                it
                            }
                        )
                    },
                    visualTransformation = CurrencyAmountInputVisualTransformation(),
                    keyboardType = KeyboardType.NumberPassword
                )

                //Spacer(modifier = Modifier.height(MaterialTheme.dimens.dimen16))

//                AddAttachmentRow(
//                    uri = if (state.transaction.uri.isEmpty()){
//                        Uri.EMPTY
//                    } else {
//                        state.transaction.uri.toUri()
//                    },
//                    onSelectedUri = onSelectedUri,
//                    onDelete = {
//                        onSelectedUri.invoke(Uri.EMPTY)
//                    }
//                )

            }
        }
    }
}

@DevicePreviews
@Composable
private fun InputTransactionDialogContentPreview(){
    MoneyTrackerTheme {
        InputTransactionContent()
    }
}