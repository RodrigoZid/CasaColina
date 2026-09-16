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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.TableRestaurant
import androidx.compose.material.icons.filled.WineBar
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.example.model.Screen
import com.example.ui.theme.CriolloBackground
import com.example.ui.theme.CriolloOnPrimary
import com.example.ui.theme.CriolloOnSecondaryFixed
import com.example.ui.theme.CriolloOnSurface
import com.example.ui.theme.CriolloOnSurfaceVariant
import com.example.ui.theme.CriolloOutlineVariant
import com.example.ui.theme.CriolloPrimary
import com.example.ui.theme.CriolloPrimaryContainer
import com.example.ui.theme.CriolloSecondaryContainer
import com.example.ui.theme.CriolloSecondaryFixed
import com.example.ui.theme.CriolloSurfaceContainer
import com.example.ui.theme.CriolloSurfaceContainerLow
import com.example.ui.theme.CriolloSurfaceContainerLowest
import com.example.ui.theme.CriolloTertiary
import com.example.ui.theme.CriolloTertiaryFixed

@Composable
fun HomeScreen(
    onNavigate: (Screen) -> Unit,
    onFilterCategory: (MenuCategory) -> Unit,
    onSelectDish: (Dish) -> Unit,
    onQuickAdd: (Dish) -> Unit,
    onOpenQr: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CriolloBackground),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // 1. Saludo & Próxima Reserva
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Jueves, 24 de Octubre",
                            style = MaterialTheme.typography.labelMedium,
                            color = CriolloOnSurfaceVariant
                        )
                        Text(
                            text = "Buenas tardes, Rodrigo",
                            style = MaterialTheme.typography.headlineMedium,
                            color = CriolloPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .background(CriolloSecondaryFixed, RoundedCornerShape(16.dp))
                            .border(1.dp, CriolloOutlineVariant.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.WorkspacePremium,
                            contentDescription = null,
                            tint = CriolloPrimary,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "Socio Gourmet",
                            style = MaterialTheme.typography.labelSmall,
                            color = CriolloOnSecondaryFixed,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Card Próxima Reserva
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = CriolloSurfaceContainerLowest),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(CriolloOutlineVariant.copy(alpha = 0.5f), CriolloOutlineVariant.copy(alpha = 0.2f)))),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(CriolloSecondaryFixed.copy(alpha = 0.4f), RoundedCornerShape(10.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.TableRestaurant,
                                    contentDescription = null,
                                    tint = CriolloPrimary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = "PRÓXIMA RESERVA",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = CriolloPrimary,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.5.sp
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(5.dp)
                                            .background(CriolloTertiary, CircleShape)
                                    )
                                    Text(
                                        text = "Hoy 8:30 PM",
                                        fontSize = 11.sp,
                                        color = CriolloTertiary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Text(
                                    text = "Mesa para 4 • Patio Virreinal",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = CriolloOnSurface,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }
                        }

                        IconButton(
                            onClick = onOpenQr,
                            modifier = Modifier
                                .size(38.dp)
                                .background(CriolloSurfaceContainer, RoundedCornerShape(10.dp))
                                .testTag("home_qr_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.QrCode2,
                                contentDescription = "Ver Pase QR",
                                tint = CriolloPrimary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
            }
        }

        // 2. HERO EDITORIAL BANNER
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .height(250.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                AsyncImage(
                    model = SampleData.HERO_BANNER_URL,
                    contentDescription = "Casona solariega Lima 1892",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Dark gradient overlay for editorial readability
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Black.copy(alpha = 0.2f),
                                    Color.Black.copy(alpha = 0.5f),
                                    Color.Black.copy(alpha = 0.9f)
                                )
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(18.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = "CASONA SOLARIEGA • LIMA 1892",
                        style = MaterialTheme.typography.labelSmall,
                        color = CriolloSecondaryFixed,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Sabores con historia,\ntradición con estilo",
                        style = MaterialTheme.typography.headlineLarge,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 32.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Una experiencia gastronómica criolla única en el corazón de la ciudad.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.85f),
                        maxLines = 2
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(
                            onClick = { onNavigate(Screen.MENU) },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = CriolloPrimaryContainer),
                            modifier = Modifier.height(38.dp)
                        ) {
                            Text("Ver Carta", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(15.dp)
                            )
                        }

                        OutlinedButton(
                            onClick = { onNavigate(Screen.RESERVATIONS) },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                            border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.linearGradient(listOf(Color.White.copy(alpha = 0.8f), Color.White.copy(alpha = 0.8f)))),
                            modifier = Modifier.height(38.dp)
                        ) {
                            Text("Reservar", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                        }
                    }
                }
            }
        }

        // 3. Accesos Rápidos Táctiles (4 columnas)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                QuickActionButton(
                    icon = Icons.Default.CalendarMonth,
                    label = "Reservas",
                    bgColor = CriolloSecondaryFixed.copy(alpha = 0.5f),
                    iconColor = CriolloPrimary,
                    onClick = { onNavigate(Screen.RESERVATIONS) },
                    modifier = Modifier.weight(1f)
                )
                QuickActionButton(
                    icon = Icons.Default.MenuBook,
                    label = "La Carta",
                    bgColor = CriolloSecondaryFixed.copy(alpha = 0.6f),
                    iconColor = CriolloPrimary,
                    onClick = { onNavigate(Screen.MENU) },
                    modifier = Modifier.weight(1f)
                )
                QuickActionButton(
                    icon = Icons.Default.WineBar,
                    label = "La Cava",
                    bgColor = CriolloTertiaryFixed.copy(alpha = 0.6f),
                    iconColor = CriolloTertiary,
                    onClick = { onFilterCategory(MenuCategory.CAVA) },
                    modifier = Modifier.weight(1f)
                )
                QuickActionButton(
                    icon = Icons.Default.QrCodeScanner,
                    label = "Pase QR",
                    bgColor = CriolloSecondaryContainer.copy(alpha = 0.4f),
                    iconColor = CriolloPrimary,
                    onClick = onOpenQr,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // 4. SELECCIÓN DEL CHEF
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "RECOMENDACIÓN DE TEMPORADA",
                            style = MaterialTheme.typography.labelSmall,
                            color = CriolloPrimary,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.6.sp
                        )
                        Text(
                            text = "Selección del Chef",
                            style = MaterialTheme.typography.headlineSmall,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            color = CriolloOnSurface
                        )
                    }

                    TextButton(onClick = { onNavigate(Screen.MENU) }) {
                        Text(
                            text = "Ver todos",
                            color = CriolloPrimary,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = CriolloPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Cards de Selección del Chef
                SampleData.DISHES.filter { it.isChefPick }.forEach { dish ->
                    ChefDishCard(
                        dish = dish,
                        onDetailClick = { onSelectDish(dish) },
                        onAddClick = { onQuickAdd(dish) },
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }
            }
        }

        // 5. BANNER PROMOCIÓN PISQUERA
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(CriolloTertiary, Color(0xFF16363A))
                        )
                    )
                    .padding(18.dp)
            ) {
                Column {
                    Text(
                        text = "TRADICIÓN PISQUERA",
                        style = MaterialTheme.typography.labelSmall,
                        color = CriolloTertiaryFixed,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Pisco Sour 2x1 en Terraza",
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = FontFamily.Serif,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Disfruta de nuestros cócteles de autor elaborados con pisco quebranta puro todos los jueves y viernes de 5pm a 8pm.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White.copy(alpha = 0.85f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { onNavigate(Screen.RESERVATIONS) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CriolloSurfaceContainerLowest,
                            contentColor = CriolloTertiary
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text(
                            text = "Reservar Mesa Cóctel",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // 6. INFORMACIÓN Y UBICACIÓN
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Casa Colina Colonial",
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = FontFamily.Serif,
                    color = CriolloPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Av. Mariscal La Mar 920, Miraflores • Lima, Perú",
                    style = MaterialTheme.typography.bodySmall,
                    color = CriolloOnSurfaceVariant,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CriolloSurfaceContainerLow,
                            contentColor = CriolloPrimary
                        ),
                        border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.linearGradient(listOf(CriolloOutlineVariant.copy(alpha = 0.4f), CriolloOutlineVariant.copy(alpha = 0.4f)))),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("(01) 445-8920", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CriolloSurfaceContainerLow,
                            contentColor = CriolloPrimary
                        ),
                        border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.linearGradient(listOf(CriolloOutlineVariant.copy(alpha = 0.4f), CriolloOutlineVariant.copy(alpha = 0.4f)))),
                        shape = RoundedCornerShape(20.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(Icons.Default.NearMe, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Cómo llegar", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

@Composable
fun QuickActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    bgColor: Color,
    iconColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CriolloSurfaceContainerLowest),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(CriolloOutlineVariant.copy(alpha = 0.3f), CriolloOutlineVariant.copy(alpha = 0.3f)))),
        modifier = modifier
            .clickable(onClick = onClick)
            .testTag("quick_${label.lowercase()}")
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(bgColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = label, tint = iconColor, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = CriolloOnSurface,
                fontWeight = FontWeight.SemiBold,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun ChefDishCard(
    dish: Dish,
    onDetailClick: () -> Unit,
    onAddClick: () -> Unit,
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
            // Thumbnail with optional badge
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
                dish.badge?.let { badgeText ->
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(4.dp)
                            .background(
                                if (badgeText == "Top #1") CriolloPrimary else CriolloTertiary,
                                RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = badgeText,
                            color = Color.White,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = dish.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = CriolloOnSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.clickable(onClick = onDetailClick)
                )

                Text(
                    text = dish.shortDescription,
                    style = MaterialTheme.typography.bodySmall,
                    color = CriolloOnSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

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
                            border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.linearGradient(listOf(CriolloPrimary, CriolloPrimary))),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Text("Ver detalle", fontSize = 11.sp, color = CriolloPrimary, fontWeight = FontWeight.Bold)
                        }

                        IconButton(
                            onClick = onAddClick,
                            modifier = Modifier
                                .size(30.dp)
                                .background(CriolloPrimary, RoundedCornerShape(6.dp))
                                .testTag("quick_add_${dish.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Añadir al carrito",
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
