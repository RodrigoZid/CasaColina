package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.SampleData
import com.example.model.Dish
import com.example.model.MenuCategory
import com.example.ui.theme.CriolloBackground
import com.example.ui.theme.CriolloOnPrimary
import com.example.ui.theme.CriolloOnSurface
import com.example.ui.theme.CriolloOnSurfaceVariant
import com.example.ui.theme.CriolloOutline
import com.example.ui.theme.CriolloOutlineVariant
import com.example.ui.theme.CriolloPrimary
import com.example.ui.theme.CriolloPrimaryContainer
import com.example.ui.theme.CriolloSecondaryFixed
import com.example.ui.theme.CriolloSurfaceContainer
import com.example.ui.theme.CriolloSurfaceContainerLowest

@Composable
fun MenuScreen(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    selectedCategory: MenuCategory,
    onSelectCategory: (MenuCategory) -> Unit,
    onSelectDish: (Dish) -> Unit,
    onQuickAdd: (Dish) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredDishes = SampleData.DISHES.filter { dish ->
        val matchesCategory = selectedCategory == MenuCategory.ALL || dish.category == selectedCategory
        val matchesQuery = searchQuery.isBlank() ||
                dish.name.contains(searchQuery, ignoreCase = true) ||
                dish.shortDescription.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesQuery
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CriolloBackground)
    ) {
        // Sticky Search and Filter Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(CriolloBackground)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchChange,
                placeholder = {
                    Text(
                        "Buscar ceviches, lomos, postres...",
                        fontSize = 14.sp,
                        color = CriolloOutline
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar plato",
                        tint = CriolloOutline
                    )
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CriolloSurfaceContainerLowest,
                    unfocusedContainerColor = CriolloSurfaceContainerLowest,
                    focusedBorderColor = CriolloPrimary,
                    unfocusedBorderColor = CriolloOutlineVariant.copy(alpha = 0.6f)
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_dish_input")
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Categories horizontal scrolling
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 2.dp)
            ) {
                items(MenuCategory.values()) { category ->
                    val isSelected = category == selectedCategory
                    FilterChip(
                        selected = isSelected,
                        onClick = { onSelectCategory(category) },
                        label = {
                            Text(
                                text = category.displayName,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CriolloPrimaryContainer,
                            selectedLabelColor = Color.White,
                            containerColor = CriolloSurfaceContainer,
                            labelColor = CriolloOnSurface
                        ),
                        border = null,
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.testTag("chip_${category.name.lowercase()}")
                    )
                }
            }
        }

        // Dishes List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (filteredDishes.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "No se encontraron delicias",
                                style = MaterialTheme.typography.titleMedium,
                                color = CriolloOnSurfaceVariant
                            )
                            Text(
                                text = "Intenta con otra categoría o término de búsqueda.",
                                style = MaterialTheme.typography.bodySmall,
                                color = CriolloOutline,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            } else {
                items(filteredDishes) { dish ->
                    MenuDishItemCard(
                        dish = dish,
                        onDetailClick = { onSelectDish(dish) },
                        onQuickAdd = { onQuickAdd(dish) }
                    )
                }
            }
        }
    }
}

@Composable
fun MenuDishItemCard(
    dish: Dish,
    onDetailClick: () -> Unit,
    onQuickAdd: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CriolloSurfaceContainerLowest),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(CriolloOutlineVariant.copy(alpha = 0.4f), CriolloOutlineVariant.copy(alpha = 0.4f)))),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(onClick = onDetailClick)
            ) {
                AsyncImage(
                    model = dish.imageUrl,
                    contentDescription = dish.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = dish.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = CriolloOnSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .clickable(onClick = onDetailClick)
                    )

                    Box(
                        modifier = Modifier
                            .background(CriolloSecondaryFixed.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = dish.prepTime,
                            fontSize = 11.sp,
                            color = CriolloPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Text(
                    text = dish.shortDescription,
                    style = MaterialTheme.typography.bodySmall,
                    color = CriolloOnSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "S/ ${"%.2f".format(dish.price)}",
                        style = MaterialTheme.typography.titleMedium,
                        color = CriolloPrimary,
                        fontWeight = FontWeight.Bold
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        OutlinedButton(
                            onClick = onDetailClick,
                            shape = RoundedCornerShape(6.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                            border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.linearGradient(listOf(CriolloOutlineVariant, CriolloOutlineVariant))),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text("Personalizar", fontSize = 11.sp, color = CriolloPrimary, fontWeight = FontWeight.Bold)
                        }

                        IconButton(
                            onClick = onQuickAdd,
                            modifier = Modifier
                                .size(30.dp)
                                .background(CriolloPrimary, RoundedCornerShape(6.dp))
                                .testTag("menu_add_${dish.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Añadir a la orden",
                                tint = CriolloOnPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
