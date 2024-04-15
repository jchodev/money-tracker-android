package com.jerry.moneytracker.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.jerry.moneytracker.core.designsystem.theme.MoneyTrackerTheme
import com.jerry.moneytracker.core.designsystem.theme.dimens
import com.jerry.moneytracker.core.model.data.Category
import com.jerry.moneytracker.core.model.data.CategoryType
import com.jerry.moneytracker.core.model.data.TransactionType
import com.jerry.moneytracker.core.ui.ext.getColors
import com.jerry.moneytracker.core.ui.ext.getImageVector
import com.jerry.moneytracker.core.ui.ext.getString
import com.jerry.moneytracker.core.ui.preview.DevicePreviews

@Composable
fun CategoryGroupBox(
    modifier: Modifier = Modifier.fillMaxWidth(),
    transactionType: TransactionType = TransactionType.INCOME,
    category: Category,
    onCategorySelected: (Category) -> Unit = {}
){
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            //.background(color = Color.Green) // for testing
            .padding(MaterialTheme.dimens.dimen16)
    ) {
        //Main Category
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .padding(MaterialTheme.dimens.dimen16)
                .clickable {
                    onCategorySelected.invoke(category)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {

            CategoryIcon(
                size = MaterialTheme.dimens.dimen56,
                icon = category.type.getImageVector(),
                contentDescription = category.type.getString(),
                bgColor = transactionType.getColors().second,
                iconColor = transactionType.getColors().first
            )

            Spacer(modifier = Modifier.width(MaterialTheme.dimens.dimen16))

            Text(
                text = category.type.getString(),
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                textAlign = TextAlign.Center
            )
        }

        //sub category
        if (category.items.isNotEmpty()){
            LazyRow(Modifier.padding(MaterialTheme.dimens.dimen16)) {
                items(category.items.size) { index ->
                    val item = category.items[index]
                    CategoryItem(
                        bgColor = transactionType.getColors().second,
                        iconColor = transactionType.getColors().first,
                        category = item,
                        textColor = MaterialTheme.colorScheme.onTertiaryContainer,
                        onCategorySelected = onCategorySelected,
                    )

                    if (index < category.items.size - 1) {
                        Spacer(modifier = Modifier.width(MaterialTheme.dimens.dimen8))
                    }
                }
            }
        }

    }
}

@DevicePreviews
@Composable
private fun CategoryGroupPreview(){
    MoneyTrackerTheme {
        CategoryGroupBox(
            category = Category(
                type = CategoryType.FOOD_AND_BEVERAGES,
                items = listOf(
                    Category(type = CategoryType.FOOD_AND_BEVERAGES),
                    Category(type = CategoryType.FOOD_AND_BEVERAGES),
                    Category(type = CategoryType.FOOD_AND_BEVERAGES),
                )
            ),
        )
    }
}

@DevicePreviews
@Composable
private fun CategoryGroupSinglePreview(){
    MoneyTrackerTheme {
        CategoryGroupBox(
            category = Category(
                type = CategoryType.FOOD_AND_BEVERAGES
            ),
        )
    }
}

@DevicePreviews
@Composable //EXPENSES
private fun CategoryGroupExpensesPreview(){
    MoneyTrackerTheme {
        CategoryGroupBox(
            transactionType = TransactionType.EXPENSES,
            category = Category(
                type = CategoryType.FOOD_AND_BEVERAGES,
                items = listOf(
                    Category(type = CategoryType.FOOD_AND_BEVERAGES),
                    Category(type = CategoryType.FOOD_AND_BEVERAGES),
                    Category(type = CategoryType.FOOD_AND_BEVERAGES),
                )
            ),
        )
    }
}

@DevicePreviews
@Composable
private fun CategoryGroupExpensesSinglePreview(){
    MoneyTrackerTheme {
        CategoryGroupBox(
            transactionType = TransactionType.EXPENSES,
            category = Category(
                type = CategoryType.FOOD_AND_BEVERAGES
            ),
        )
    }
}