package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.TableRestaurant
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.Screen
import com.example.ui.theme.CriolloOnPrimary
import com.example.ui.theme.CriolloOnSurfaceVariant
import com.example.ui.theme.CriolloOutlineVariant
import com.example.ui.theme.CriolloPrimary
import com.example.ui.theme.CriolloPrimaryContainer
import com.example.ui.theme.CriolloSurface
import com.example.ui.theme.CriolloSurfaceContainer
import com.example.ui.theme.CriolloSurfaceContainerLow
import com.example.ui.theme.CriolloSurfaceContainerLowest
import com.example.ui.theme.CriolloTertiary
import com.example.ui.theme.DiamondGold
import com.example.ui.theme.DiamondGreen
import com.example.ui.theme.DiamondRed
import com.example.ui.theme.DiamondSand

@Composable
fun CasaColinaLogo(modifier: Modifier = Modifier, onClick: (() -> Unit)? = null) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier.then(
            if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier
        )
    ) {
        // 4 Rotated colored diamonds
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .rotate(45f)
                    .background(DiamondGreen)
            )
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .rotate(45f)
                    .background(DiamondRed)
            )
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .rotate(45f)
                    .background(DiamondGold)
            )
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .rotate(45f)
                    .background(DiamondSand)
            )
        }
        Text(
            text = "CASA COLINA",
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = CriolloPrimary
        )
    }
}

@Composable
fun CasaColinaTopAppBar(
    cartCount: Int,
    onMenuClick: () -> Unit,
    onLogoClick: () -> Unit,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = CriolloSurface.copy(alpha = 0.95f),
        shadowElevation = 2.dp,
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier.testTag("menu_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menú principal",
                    tint = CriolloPrimary
                )
            }

            CasaColinaLogo(onClick = onLogoClick)

            IconButton(
                onClick = onCartClick,
                modifier = Modifier.testTag("cart_top_button")
            ) {
                Box {
                    Icon(
                        imageVector = Icons.Default.ShoppingBag,
                        contentDescription = "Carrito de compras",
                        tint = CriolloPrimary
                    )
                    if (cartCount > 0) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(16.dp)
                                .background(CriolloPrimaryContainer, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = cartCount.toString(),
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CasaColinaBottomBar(
    currentScreen: Screen,
    cartCount: Int,
    onScreenSelected: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        containerColor = CriolloSurface,
        tonalElevation = 6.dp,
        modifier = modifier
    ) {
        val items = listOf(
            Triple(Screen.HOME, "Inicio", Icons.Default.Home),
            Triple(Screen.MENU, "Carta", Icons.Default.RestaurantMenu),
            Triple(Screen.RESERVATIONS, "Reservas", Icons.Default.TableRestaurant),
            Triple(Screen.CART, "Carrito", Icons.Default.ShoppingBag),
            Triple(Screen.PROFILE, "Perfil", Icons.Default.Person)
        )

        items.forEach { (screen, label, icon) ->
            val isSelected = currentScreen == screen
            NavigationBarItem(
                selected = isSelected,
                onClick = { onScreenSelected(screen) },
                icon = {
                    if (screen == Screen.CART && cartCount > 0) {
                        Box {
                            Icon(icon, contentDescription = label)
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .size(14.dp)
                                    .background(CriolloPrimaryContainer, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = cartCount.toString(),
                                    color = Color.White,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    } else {
                        Icon(icon, contentDescription = label)
                    }
                },
                label = {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = CriolloPrimary,
                    selectedTextColor = CriolloPrimary,
                    unselectedIconColor = CriolloOnSurfaceVariant,
                    unselectedTextColor = CriolloOnSurfaceVariant,
                    indicatorColor = CriolloPrimary.copy(alpha = 0.12f)
                ),
                modifier = Modifier.testTag("nav_${screen.name.lowercase()}")
            )
        }
    }
}

@Composable
fun QrPassModal(
    onDismiss: () -> Unit,
    guestName: String = "Rodrigo Torres",
    guestsCount: Int = 4,
    ambiance: String = "Patio Virreinal",
    time: String = "Hoy 20:30 hrs"
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CriolloSurfaceContainerLowest),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Pase de Reserva Digital",
                    style = MaterialTheme.typography.titleLarge,
                    color = CriolloPrimary
                )
                Text(
                    text = "Muestra este código al llegar a la casona",
                    style = MaterialTheme.typography.bodySmall,
                    color = CriolloOnSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .size(190.dp)
                        .background(CriolloSurfaceContainerLow, RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.QrCode2,
                        contentDescription = "Código QR de Reserva",
                        modifier = Modifier.size(150.dp),
                        tint = CriolloPrimary
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "$guestName • $guestsCount Personas",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$ambiance • $time",
                    style = MaterialTheme.typography.bodySmall,
                    color = CriolloTertiary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CriolloPrimaryContainer),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("close_qr_button")
                ) {
                    Text("Cerrar Pase", color = CriolloOnPrimary, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun CasaColinaSideDrawer(
    isOpen: Boolean,
    onClose: () -> Unit,
    onNavigate: (Screen) -> Unit,
    onOpenQr: () -> Unit
) {
    if (!isOpen) return

    Dialog(onDismissRequest = onClose) {
        Surface(
            shape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
            color = CriolloSurface,
            modifier = Modifier
                .width(280.dp)
                .padding(vertical = 40.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CasaColinaLogo()
                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Cerrar menú",
                            tint = CriolloOnSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider(color = CriolloOutlineVariant.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.height(16.dp))

                DrawerMenuItem(
                    icon = Icons.Default.Home,
                    title = "Inicio",
                    onClick = { onNavigate(Screen.HOME); onClose() }
                )
                DrawerMenuItem(
                    icon = Icons.AutoMirrored.Filled.MenuBook,
                    title = "Carta Completa",
                    onClick = { onNavigate(Screen.MENU); onClose() }
                )
                DrawerMenuItem(
                    icon = Icons.Default.CalendarMonth,
                    title = "Reservar Mesa",
                    onClick = { onNavigate(Screen.RESERVATIONS); onClose() }
                )
                DrawerMenuItem(
                    icon = Icons.Default.QrCode2,
                    title = "Mi Pase QR",
                    onClick = { onOpenQr(); onClose() }
                )
                DrawerMenuItem(
                    icon = Icons.Default.Person,
                    title = "Perfil & Puntos",
                    onClick = { onNavigate(Screen.PROFILE); onClose() }
                )

                Spacer(modifier = Modifier.weight(1f))
                Divider(color = CriolloOutlineVariant.copy(alpha = 0.4f))
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Casona Solariega Miraflores",
                    style = MaterialTheme.typography.titleSmall,
                    color = CriolloPrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Lima, Perú • Abierto hoy hasta las 23:00",
                    style = MaterialTheme.typography.bodySmall,
                    color = CriolloOnSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun DrawerMenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = CriolloPrimary,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}
