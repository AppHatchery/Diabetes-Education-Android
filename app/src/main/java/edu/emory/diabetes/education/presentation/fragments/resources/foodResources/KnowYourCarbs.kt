package edu.emory.diabetes.education.presentation.fragments.resources.foodResources

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.emory.diabetes.education.R
import edu.emory.diabetes.education.presentation.fragments.resources.components.ResourcesTopBar
import edu.emory.diabetes.education.presentation.theme.nunito
import kotlinx.coroutines.launch

private sealed interface CarbListRow {
    data class Header(val title: String) : CarbListRow
    data class Item(val categoryTitle: String, val food: CarbFood) : CarbListRow
}

@Composable
fun KnowYourCarbs(
    viewModel: KnowYourCarbsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit = {},
    onAddCustom: () -> Unit = {},
    onCalculateInsulin: () -> Unit = {}
) {
    val categories by viewModel.allCategories.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    val disclaimerVisible = viewModel.showDisclaimer

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    // Quick-return app bar: hidden while scrolling up (toward more items),
    // shown again as soon as the user scrolls back down.
    var topBarVisible by remember { mutableStateOf(true) }
    val appBarScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                if (available.y < -1f) topBarVisible = false
                else if (available.y > 1f) topBarVisible = true
                return Offset.Zero
            }
        }
    }

    // Sections and their foods, filtered by the search query.
    val query = searchQuery.trim()
    val rows = remember(query, categories) {
        buildList {
            categories.forEach { category ->
                val matches = if (query.isEmpty()) category.items
                else category.items.filter { it.name.contains(query, ignoreCase = true) }
                if (matches.isNotEmpty()) {
                    add(CarbListRow.Header(category.title))
                    matches.forEach { add(CarbListRow.Item(category.title, it)) }
                }
            }
        }
    }

    Scaffold(
        bottomBar = {
            if (viewModel.selected.isNotEmpty()) {
                TotalCarbsBar(
                    totalCarbs = viewModel.selectedCarbs,
                    onCalculateInsulin = onCalculateInsulin
                )
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .nestedScroll(appBarScrollConnection)
                .background(Color.White)
        ) {
            AnimatedVisibility(visible = topBarVisible) {
                ResourcesTopBar(
                    title = "",
                    color = Color.White,
                    onNavigationClick = onNavigateBack,
                    showAdd = true,
                    onEditClick = onAddCustom,
                    windowInsets = WindowInsets(0, 0, 0, 0)
                )
            }

            SearchField(
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )

            CategoryChips(
                categories = categories,
                onCategoryClick = { category ->
                    val headerIndex = rows.indexOfFirst {
                        it is CarbListRow.Header && it.title == category.title
                    }
                    if (headerIndex >= 0) {
                        val offset = if (disclaimerVisible) 1 else 0
                        scope.launch { listState.animateScrollToItem(offset + headerIndex) }
                    }
                }
            )

            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {
                if (disclaimerVisible) {
                    item(key = "disclaimer") {
                        DisclaimerCard(onDismiss = { viewModel.dismissDisclaimer() })
                    }
                }

                items(
                    items = rows,
                    key = { row ->
                        when (row) {
                            is CarbListRow.Header -> "header/${row.title}"
                            is CarbListRow.Item -> selectionKey(row.food, row.categoryTitle)
                        }
                    }
                ) { row ->
                    when (row) {
                        is CarbListRow.Header -> SectionHeader(row.title)
                        is CarbListRow.Item -> {
                            val key = selectionKey(row.food, row.categoryTitle)
                            if (row.food.isCustom) {
                                SwipeToDeleteRow(
                                    onDelete = { viewModel.deleteCustomFood(row.food.id) }
                                ) {
                                    FoodRow(
                                        food = row.food,
                                        quantity = viewModel.quantityOf(key),
                                        onAdd = { viewModel.increment(row.food, row.categoryTitle) },
                                        onRemove = { viewModel.decrement(key) }
                                    )
                                }
                            } else {
                                FoodRow(
                                    food = row.food,
                                    quantity = viewModel.quantityOf(key),
                                    onAdd = { viewModel.increment(row.food, row.categoryTitle) },
                                    onRemove = { viewModel.decrement(key) }
                                )
                            }
                        }
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
private fun SearchField(
    query: String,
    onQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = {
            Text(text = "Search", fontFamily = nunito, color = colorResource(R.color.gray_100))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = colorResource(R.color.gray_100)
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
    )
}

@Composable
private fun CategoryChips(
    categories: List<CarbCategory>,
    onCategoryClick: (CarbCategory) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        categories.forEach { category ->
            Column(
                modifier = Modifier
                    .width(64.dp)
                    .clickable { onCategoryClick(category) },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(colorResource(category.chipColor)),
                    contentAlignment = Alignment.Center
                ) {
                    category.chipImage?.let {
                        Image(
                            painter = painterResource(it),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = category.chipLabel,
                    fontSize = 12.sp,
                    fontFamily = nunito,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun DisclaimerCard(onDismiss: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(colorResource(R.color.secondary_sunset_orange_shade100))
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = null,
                tint = colorResource(R.color.secondary_sunset_orange)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Disclaimer",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = nunito,
                color = colorResource(R.color.secondary_sunset_orange),
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = "Dismiss",
                    tint = colorResource(R.color.secondary_sunset_orange)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Carb values are estimates based on common foods and popular brands. " +
                    "Actual values may vary slightly by brand, product, or serving size. " +
                    "For a more accurate value, tap Add + to enter a custom item.",
            fontSize = 14.sp,
            fontFamily = nunito,
            color = colorResource(R.color.gray_400)
        )
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = nunito,
        color = colorResource(R.color.secondary_ocean_blue),
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 8.dp)
    )
}

@Composable
private fun FoodRow(
    food: CarbFood,
    quantity: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FoodImage(food)

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = food.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = nunito,
                    color = Color.Black
                )
                Text(
                    text = food.serving,
                    fontSize = 13.sp,
                    fontFamily = nunito,
                    color = colorResource(R.color.gray_100)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${food.carbs}g",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = nunito,
                    color = colorResource(R.color.secondary_ocean_blue)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            if (quantity > 0) {
                QuantityStepper(quantity = quantity, onAdd = onAdd, onRemove = onRemove)
            } else {
                AddButton(onAdd = onAdd)
            }
        }
        HorizontalDivider(color = colorResource(R.color.gray_100_sick))
    }
}

@Composable
private fun FoodImage(food: CarbFood) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(colorResource(R.color.white)),
        contentAlignment = Alignment.Center
    ) {
        food.image?.let {
            Image(
                painter = painterResource(it),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
private fun AddButton(onAdd: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(colorResource(R.color.gray_100_sick))
            .clickable(onClick = onAdd),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add",
            tint = Color.Black,
            modifier = Modifier.size(20.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeToDeleteRow(
    onDelete: () -> Unit,
    content: @Composable () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
                true
            } else {
                false
            }
        }
    )
    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.secondary_fire_red)),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "Delete",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = nunito,
                    color = Color.White,
                    modifier = Modifier.padding(end = 32.dp)
                )
            }
        }
    ) {
        Box(modifier = Modifier.background(Color.White)) {
            content()
        }
    }
}

@Composable
private fun QuantityStepper(
    quantity: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(colorResource(R.color.gray_100_sick)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clickable(onClick = onRemove),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "–",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = nunito,
                color = Color.Black
            )
        }
        Text(
            text = quantity.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = nunito,
            color = colorResource(R.color.secondary_ocean_blue)
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .clickable(onClick = onAdd),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Add",
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun TotalCarbsBar(
    totalCarbs: Int,
    onCalculateInsulin: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(colorResource(R.color.secondary_sunset_orange_shade100))
            .navigationBarsPadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Total Carbs",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = nunito,
                color = colorResource(R.color.secondary_sunset_orange)
            )
            Text(
                text = "${totalCarbs}g",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = nunito,
                color = colorResource(R.color.secondary_sunset_orange)
            )
        }

        Button(
            onClick = onCalculateInsulin,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.secondary_sunset_orange)
            )
        ) {
            Text(
                text = "Calculate insulin",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = nunito,
                color = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
