package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.Dish
import com.example.model.MenuCategory
import com.example.ui.theme.CriolloBackground
import com.example.ui.theme.CriolloError
import com.example.ui.theme.CriolloOnPrimary
import com.example.ui.theme.CriolloOnSurface
import com.example.ui.theme.CriolloOnSurfaceVariant
import com.example.ui.theme.CriolloOutline
import com.example.ui.theme.CriolloOutlineVariant
import com.example.ui.theme.CriolloPrimary
import com.example.ui.theme.CriolloPrimaryContainer
import com.example.ui.theme.CriolloSurfaceContainer
import com.example.ui.theme.CriolloSurfaceContainerLowest

@Composable
fun DishDetailScreen(
    dish: Dish,
    selectedDoneness: String,
    onSelectDoneness: (String) -> Unit,
    selectedExtras: Map<String, Double>,
    onToggleExtra: (String, Double) -> Unit,
    quantity: Int,
    onUpdateQuantity: (Int) -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onBack: () -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    val extrasSum = selectedExtras.values.sum()
    val unitPrice = dish.price + extrasSum
    val finalTotal = unitPrice * quantity

    val availableSides = listOf(
        Pair("Papas nativas extras crocantes", 12.00),
        Pair("Plátano frito maduro", 10.00),
        Pair("Tacu tacu criollo de pallares", 18.00)
    )

    val donenessOptions = listOf(
        Pair("Término medio (Recomendado)", "Jugoso y tierno"),
        Pair("Tres cuartos", "Punto equilibrado"),
        Pair("Bien cocido", "Completamente dorado")
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CriolloBackground),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // Hero Image with Overlays
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
            ) {
                AsyncImage(
                    model = dish.imageUrl,
                    contentDescription = dish.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Black.copy(alpha = 0.4f),
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.85f)
                                )
                            )
                        )
                )

                // Top Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                            .testTag("detail_back_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }

                    IconButton(
                        onClick = onToggleFavorite,
                        modifier = Modifier
                            .size(40.dp)
                            .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                            .testTag("detail_favorite_btn")
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (isFavorite) CriolloError else Color.White
                        )
                    }
                }

                // Title and badges on bottom of hero
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .background(CriolloPrimaryContainer, RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "PLATO EMBLEMÁTICO",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.6.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = dish.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color(0xFFFFC107),
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "${dish.rating} (${dish.reviewsCount})",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.9f),
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Text("•", color = Color.White.copy(alpha = 0.7f))
                        Text(
                            text = "${dish.prepTime} preparación",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                        Text("•", color = Color.White.copy(alpha = 0.7f))
                        Text(
                            text = "Criollo Fino",
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }

        // Details Body
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = dish.fullDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CriolloOnSurfaceVariant,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Precio base:",
                        style = MaterialTheme.typography.titleSmall,
                        color = CriolloOnSurface
                    )
                    Text(
                        text = "S/ ${"%.2f".format(dish.price)}",
                        style = MaterialTheme.typography.headlineSmall,
                        fontFamily = FontFamily.Serif,
                        color = CriolloPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
                Divider(color = CriolloOutlineVariant.copy(alpha = 0.4f))
            }
        }

        // Selección Obligatoria: Término de la Carne (para carnes / lomo)
        if (dish.category == MenuCategory.FONDOS) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Término de la Carne",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = CriolloOnSurface
                        )
                        Box(
                            modifier = Modifier
                                .background(CriolloPrimary.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Obligatorio",
                                fontSize = 11.sp,
                                color = CriolloPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    donenessOptions.forEach { (option, subtitle) ->
                        val isSelected = selectedDoneness == option
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .background(
                                    if (isSelected) CriolloPrimary.copy(alpha = 0.08f) else CriolloSurfaceContainerLowest,
                                    RoundedCornerShape(10.dp)
                                )
                                .border(
                                    1.dp,
                                    if (isSelected) CriolloPrimary else CriolloOutlineVariant.copy(alpha = 0.5f),
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { onSelectDoneness(option) }
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { onSelectDoneness(option) },
                                    colors = RadioButtonDefaults.colors(selectedColor = CriolloPrimary)
                                )
                                Text(
                                    text = option,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                            Text(
                                text = subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = CriolloOnSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        // Guarniciones Adicionales
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Guarniciones Adicionales",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = CriolloOnSurface
                    )
                    Text(
                        text = "Opcional",
                        style = MaterialTheme.typography.labelSmall,
                        color = CriolloOutline
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                availableSides.forEach { (sideName, sidePrice) ->
                    val isChecked = selectedExtras.containsKey(sideName)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .background(CriolloSurfaceContainerLowest, RoundedCornerShape(10.dp))
                            .border(
                                1.dp,
                                if (isChecked) CriolloPrimary else CriolloOutlineVariant.copy(alpha = 0.4f),
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { onToggleExtra(sideName, sidePrice) }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = { onToggleExtra(sideName, sidePrice) },
                                colors = CheckboxDefaults.colors(checkedColor = CriolloPrimary)
                            )
                            Text(
                                text = sideName,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = if (isChecked) FontWeight.SemiBold else FontWeight.Normal
                            )
                        }
                        Text(
                            text = "+S/ ${"%.2f".format(sidePrice)}",
                            style = MaterialTheme.typography.titleSmall,
                            color = CriolloPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Selector de Cantidad & Botón Agregar
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Quantity Counter
                Row(
                    modifier = Modifier
                        .height(48.dp)
                        .background(CriolloSurfaceContainerLowest, RoundedCornerShape(10.dp))
                        .border(1.dp, CriolloOutlineVariant.copy(alpha = 0.6f), RoundedCornerShape(10.dp))
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "−",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = CriolloPrimary,
                        modifier = Modifier
                            .clickable { onUpdateQuantity(-1) }
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                    )
                    Text(
                        text = quantity.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Text(
                        text = "+",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = CriolloPrimary,
                        modifier = Modifier
                            .clickable { onUpdateQuantity(1) }
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                    )
                }

                // Add to Cart Button
                Button(
                    onClick = onAddToCart,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CriolloPrimaryContainer),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("detail_add_cart_btn")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Agregar al Carrito", fontWeight = FontWeight.Bold, color = CriolloOnPrimary)
                        Text("S/ ${"%.2f".format(finalTotal)}", fontWeight = FontWeight.Bold, color = CriolloOnPrimary)
                    }
                }
            }
        }
    }
}
