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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CartItem
import com.example.ui.theme.CriolloBackground
import com.example.ui.theme.CriolloError
import com.example.ui.theme.CriolloOnPrimary
import com.example.ui.theme.CriolloOnSurface
import com.example.ui.theme.CriolloOnSurfaceVariant
import com.example.ui.theme.CriolloOutline
import com.example.ui.theme.CriolloOutlineVariant
import com.example.ui.theme.CriolloPrimary
import com.example.ui.theme.CriolloPrimaryContainer
import com.example.ui.theme.CriolloSurfaceContainerLow
import com.example.ui.theme.CriolloSurfaceContainerLowest
import com.example.ui.theme.CriolloTertiary

@Composable
fun CartScreen(
    cartItems: List<CartItem>,
    onUpdateQuantity: (String, Int) -> Unit,
    onClearCart: () -> Unit,
    deliveryMode: String,
    onSelectDeliveryMode: (String) -> Unit,
    paymentMethod: String,
    onSelectPaymentMethod: (String) -> Unit,
    onCheckout: () -> Unit,
    onNavigateMenu: () -> Unit,
    onPaymentCompleted: () -> Unit,
    modifier: Modifier = Modifier
) {

    val subtotal = cartItems.sumOf { it.totalPrice }
    val tax = subtotal * 0.18

    // Controla la ventana de pago completado
    var showPaymentSuccess by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(CriolloBackground)
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(
                top = 16.dp,
                bottom = 32.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // =====================================================
            // HEADER
            // =====================================================

            item {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "Tu Orden Criolla",
                        style = MaterialTheme.typography.headlineLarge,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = CriolloPrimary
                    )

                    if (cartItems.isNotEmpty()) {

                        TextButton(
                            onClick = onClearCart
                        ) {

                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = CriolloError,
                                modifier = Modifier.size(16.dp)
                            )

                            Spacer(
                                modifier = Modifier.width(4.dp)
                            )

                            Text(
                                text = "Vaciar",
                                color = CriolloError,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }


            // =====================================================
            // CARRITO VACÍO
            // =====================================================

            if (cartItems.isEmpty()) {

                item {

                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = CriolloSurfaceContainerLowest
                        ),
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = Brush.linearGradient(
                                listOf(
                                    CriolloOutlineVariant.copy(alpha = 0.4f),
                                    CriolloOutlineVariant.copy(alpha = 0.4f)
                                )
                            )
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Icon(
                                imageVector = Icons.Default.ShoppingBasket,
                                contentDescription = null,
                                tint = CriolloOutline,
                                modifier = Modifier.size(54.dp)
                            )

                            Spacer(
                                modifier = Modifier.height(10.dp)
                            )

                            Text(
                                text = "Tu bolsa de delicias está vacía",
                                style = MaterialTheme.typography.titleMedium,
                                fontFamily = FontFamily.Serif,
                                color = CriolloOnSurface
                            )

                            Text(
                                text = "Explora nuestra carta tradicional criolla y añade tus platos favoritos.",
                                style = MaterialTheme.typography.bodySmall,
                                color = CriolloOnSurfaceVariant,
                                modifier = Modifier.padding(top = 4.dp)
                            )

                            Spacer(
                                modifier = Modifier.height(16.dp)
                            )

                            Button(
                                onClick = onNavigateMenu,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = CriolloPrimary
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {

                                Text(
                                    text = "Ir a la Carta",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

            } else {

                // =====================================================
                // PRODUCTOS DEL CARRITO
                // =====================================================

                items(cartItems) { item ->

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = CriolloSurfaceContainerLowest
                        ),
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = Brush.linearGradient(
                                listOf(
                                    CriolloOutlineVariant.copy(alpha = 0.4f),
                                    CriolloOutlineVariant.copy(alpha = 0.4f)
                                )
                            )
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = item.dish.name,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Bold,
                                    color = CriolloOnSurface
                                )

                                val detailsText =
                                    if (item.extras.isNotEmpty()) {
                                        "${item.doneness} + ${
                                            item.extras.joinToString(", ")
                                        }"
                                    } else {
                                        item.doneness
                                    }

                                Text(
                                    text = detailsText,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = CriolloOnSurfaceVariant,
                                    maxLines = 1
                                )

                                Text(
                                    text = "S/ ${"%.2f".format(item.unitPrice)} c/u",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CriolloPrimary,
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                            }


                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                                Row(
                                    modifier = Modifier
                                        .background(
                                            CriolloSurfaceContainerLow,
                                            RoundedCornerShape(6.dp)
                                        )
                                        .border(
                                            1.dp,
                                            CriolloOutlineVariant.copy(alpha = 0.4f),
                                            RoundedCornerShape(6.dp)
                                        )
                                        .padding(
                                            horizontal = 4.dp,
                                            vertical = 2.dp
                                        ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = "−",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = CriolloPrimary,
                                        modifier = Modifier
                                            .clickable {
                                                onUpdateQuantity(
                                                    item.id,
                                                    -1
                                                )
                                            }
                                            .padding(horizontal = 6.dp)
                                    )

                                    Text(
                                        text = item.quantity.toString(),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(
                                            horizontal = 4.dp
                                        )
                                    )

                                    Text(
                                        text = "+",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = CriolloPrimary,
                                        modifier = Modifier
                                            .clickable {
                                                onUpdateQuantity(
                                                    item.id,
                                                    1
                                                )
                                            }
                                            .padding(horizontal = 6.dp)
                                    )
                                }

                                Text(
                                    text = "S/ ${"%.2f".format(item.totalPrice)}",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = CriolloOnSurface
                                )
                            }
                        }
                    }
                }


                // =====================================================
                // MODALIDAD DEL PEDIDO
                // =====================================================

                item {

                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = CriolloSurfaceContainerLowest
                        ),
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = Brush.linearGradient(
                                listOf(
                                    CriolloOutlineVariant.copy(alpha = 0.4f),
                                    CriolloOutlineVariant.copy(alpha = 0.4f)
                                )
                            )
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier.padding(14.dp)
                        ) {

                            Text(
                                text = "Modalidad del Pedido",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = CriolloOnSurface
                            )

                            Spacer(
                                modifier = Modifier.height(10.dp)
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                                val isDelivery =
                                    deliveryMode == "Delivery Express"

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .background(
                                            if (isDelivery)
                                                CriolloPrimary.copy(alpha = 0.08f)
                                            else
                                                CriolloSurfaceContainerLowest,
                                            RoundedCornerShape(8.dp)
                                        )
                                        .border(
                                            1.dp,
                                            if (isDelivery)
                                                CriolloPrimary
                                            else
                                                CriolloOutlineVariant.copy(alpha = 0.4f),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable {
                                            onSelectDeliveryMode(
                                                "Delivery Express"
                                            )
                                        }
                                        .padding(10.dp)
                                ) {

                                    Column {

                                        Text(
                                            text = "Delivery Express",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )

                                        Text(
                                            text = "35-45 min a tu puerta",
                                            fontSize = 10.sp,
                                            color = CriolloOnSurfaceVariant
                                        )
                                    }
                                }


                                val isTakeout =
                                    deliveryMode == "Para Llevar"

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .background(
                                            if (isTakeout)
                                                CriolloPrimary.copy(alpha = 0.08f)
                                            else
                                                CriolloSurfaceContainerLowest,
                                            RoundedCornerShape(8.dp)
                                        )
                                        .border(
                                            1.dp,
                                            if (isTakeout)
                                                CriolloPrimary
                                            else
                                                CriolloOutlineVariant.copy(alpha = 0.4f),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable {
                                            onSelectDeliveryMode(
                                                "Para Llevar"
                                            )
                                        }
                                        .padding(10.dp)
                                ) {

                                    Column {

                                        Text(
                                            text = "Para Llevar",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )

                                        Text(
                                            text = "Retiro en Miraflores",
                                            fontSize = 10.sp,
                                            color = CriolloOnSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }


                // =====================================================
                // MÉTODO DE PAGO
                // =====================================================

                item {

                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = CriolloSurfaceContainerLowest
                        ),
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = Brush.linearGradient(
                                listOf(
                                    CriolloOutlineVariant.copy(alpha = 0.4f),
                                    CriolloOutlineVariant.copy(alpha = 0.4f)
                                )
                            )
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier.padding(14.dp)
                        ) {

                            Text(
                                text = "Método de Pago",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = CriolloOnSurface
                            )

                            Spacer(
                                modifier = Modifier.height(10.dp)
                            )

                            val methods = listOf(
                                Triple(
                                    "Tarjeta Débito / Crédito",
                                    "VISA / MC",
                                    null
                                ),
                                Triple(
                                    "Billeteras Digitales (Yape / Plin)",
                                    "Inmediato",
                                    Icons.Default.QrCode
                                ),
                                Triple(
                                    "Pago Contra Entrega (POS móvil)",
                                    "Al recibir",
                                    Icons.Default.PointOfSale
                                )
                            )

                            methods.forEach { (name, _, icon) ->

                                val isSelected =
                                    paymentMethod == name

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .background(
                                            if (isSelected)
                                                CriolloPrimary.copy(alpha = 0.08f)
                                            else
                                                CriolloSurfaceContainerLowest,
                                            RoundedCornerShape(8.dp)
                                        )
                                        .border(
                                            1.dp,
                                            if (isSelected)
                                                CriolloPrimary
                                            else
                                                CriolloOutlineVariant.copy(alpha = 0.4f),
                                            RoundedCornerShape(8.dp)
                                        )
                                        .clickable {
                                            onSelectPaymentMethod(name)
                                        }
                                        .padding(
                                            horizontal = 10.dp,
                                            vertical = 8.dp
                                        ),
                                    horizontalArrangement =
                                        Arrangement.SpaceBetween,
                                    verticalAlignment =
                                        Alignment.CenterVertically
                                ) {

                                    Row(
                                        verticalAlignment =
                                            Alignment.CenterVertically,
                                        horizontalArrangement =
                                            Arrangement.spacedBy(8.dp)
                                    ) {

                                        RadioButton(
                                            selected = isSelected,
                                            onClick = {
                                                onSelectPaymentMethod(
                                                    name
                                                )
                                            },
                                            colors =
                                                RadioButtonDefaults.colors(
                                                    selectedColor =
                                                        CriolloPrimary
                                                )
                                        )

                                        Text(
                                            text = name,
                                            fontSize = 12.sp,
                                            fontWeight =
                                                if (isSelected)
                                                    FontWeight.Bold
                                                else
                                                    FontWeight.Medium
                                        )
                                    }


                                    if (icon != null) {

                                        Icon(
                                            imageVector = icon,
                                            contentDescription = null,
                                            tint = CriolloTertiary,
                                            modifier = Modifier.size(18.dp)
                                        )

                                    } else {

                                        Row(
                                            horizontalArrangement =
                                                Arrangement.spacedBy(4.dp)
                                        ) {

                                            Box(
                                                modifier = Modifier
                                                    .background(
                                                        Color.White,
                                                        RoundedCornerShape(3.dp)
                                                    )
                                                    .border(
                                                        1.dp,
                                                        CriolloOutlineVariant.copy(
                                                            alpha = 0.4f
                                                        ),
                                                        RoundedCornerShape(3.dp)
                                                    )
                                                    .padding(
                                                        horizontal = 4.dp,
                                                        vertical = 2.dp
                                                    )
                                            ) {

                                                Text(
                                                    text = "VISA",
                                                    fontSize = 9.sp,
                                                    fontWeight =
                                                        FontWeight.Bold
                                                )
                                            }

                                            Box(
                                                modifier = Modifier
                                                    .background(
                                                        Color.White,
                                                        RoundedCornerShape(3.dp)
                                                    )
                                                    .border(
                                                        1.dp,
                                                        CriolloOutlineVariant.copy(
                                                            alpha = 0.4f
                                                        ),
                                                        RoundedCornerShape(3.dp)
                                                    )
                                                    .padding(
                                                        horizontal = 4.dp,
                                                        vertical = 2.dp
                                                    )
                                            ) {

                                                Text(
                                                    text = "MC",
                                                    fontSize = 9.sp,
                                                    fontWeight =
                                                        FontWeight.Bold
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


                // =====================================================
                // RESUMEN
                // =====================================================

                item {

                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = CriolloSurfaceContainerLowest
                        ),
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = Brush.linearGradient(
                                listOf(
                                    CriolloOutlineVariant.copy(alpha = 0.4f),
                                    CriolloOutlineVariant.copy(alpha = 0.4f)
                                )
                            )
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement =
                                Arrangement.spacedBy(8.dp)
                        ) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement =
                                    Arrangement.SpaceBetween
                            ) {

                                Text(
                                    text = "Subtotal Platos",
                                    fontSize = 12.sp,
                                    color = CriolloOnSurfaceVariant
                                )

                                Text(
                                    text = "S/ ${"%.2f".format(subtotal)}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }


                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement =
                                    Arrangement.SpaceBetween
                            ) {

                                Text(
                                    text = "IGV (18% incluido)",
                                    fontSize = 12.sp,
                                    color = CriolloOnSurfaceVariant
                                )

                                Text(
                                    text = "S/ ${"%.2f".format(tax)}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }


                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement =
                                    Arrangement.SpaceBetween
                            ) {

                                Text(
                                    text = "Costo de Envío",
                                    fontSize = 12.sp,
                                    color = CriolloOnSurfaceVariant
                                )

                                Text(
                                    text = "Gratis",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = CriolloTertiary
                                )
                            }


                            Divider(
                                color =
                                    CriolloOutlineVariant.copy(
                                        alpha = 0.4f
                                    )
                            )


                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement =
                                    Arrangement.SpaceBetween,
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "Total a Pagar",
                                    style =
                                        MaterialTheme.typography.titleMedium,
                                    fontFamily = FontFamily.Serif,
                                    color = CriolloOnSurface
                                )

                                Text(
                                    text = "S/ ${"%.2f".format(subtotal)}",
                                    style =
                                        MaterialTheme.typography.headlineMedium,
                                    fontFamily = FontFamily.Serif,
                                    color = CriolloPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }


                // =====================================================
                // BOTÓN PAGAR
                // =====================================================

                item {

                    Button(
                        onClick = {

                            // Solo mostramos la confirmación.
                            // Todavía NO cambiamos de pantalla.
                            showPaymentSuccess = true
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor =
                                CriolloPrimaryContainer
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("checkout_order_btn")
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.ShoppingBag,
                            contentDescription = null,
                            tint = CriolloOnPrimary,
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        Text(
                            text = "Confirmar Pedido y Pagar",
                            style =
                                MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = CriolloOnPrimary
                        )
                    }
                }
            }
        }


        // =========================================================
        // POPUP PAGO COMPLETADO
        // =========================================================

        if (showPaymentSuccess) {

            AlertDialog(

                // No se cierra tocando afuera.
                // Debe pulsar OK.
                onDismissRequest = {},

                icon = {

                    Icon(
                        imageVector =
                            Icons.Default.CheckCircle,
                        contentDescription =
                            "Pago completado",
                        tint = CriolloPrimary,
                        modifier = Modifier.size(56.dp)
                    )
                },

                title = {

                    Text(
                        text = "¡Pago completado!",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = CriolloOnSurface
                    )
                },

                text = {

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Text(
                            text =
                                "Tu pago se realizó correctamente.",
                            color =
                                CriolloOnSurfaceVariant
                        )

                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        Text(
                            text =
                                "S/ ${"%.2f".format(subtotal)}",
                            fontSize = 28.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold,
                            color = CriolloPrimary
                        )

                        Spacer(
                            modifier = Modifier.height(16.dp)
                        )

                        Text(
                            text = "Método de pago",
                            fontSize = 11.sp,
                            color = CriolloOutline
                        )

                        Text(
                            text = paymentMethod,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = CriolloOnSurface
                        )

                        Spacer(
                            modifier = Modifier.height(18.dp)
                        )

                        Text(
                            text =
                                "Tu pedido ha sido confirmado.",
                            fontWeight = FontWeight.Bold,
                            color = CriolloOnSurface
                        )

                        Spacer(
                            modifier = Modifier.height(6.dp)
                        )

                        Text(
                            text =
                                "Enviamos los detalles de tu pedido a tu correo registrado.",
                            fontSize = 12.sp,
                            color =
                                CriolloOnSurfaceVariant
                        )
                    }
                },

                confirmButton = {

                    Button(
                        onClick = {

                            // 1. Cerramos el popup
                            showPaymentSuccess = false

                            // 2. Procesamos pedido
                            onCheckout()

                            // 3. Navegamos al perfil
                            onPaymentCompleted()
                        },
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor =
                                    CriolloPrimaryContainer
                            ),
                        shape =
                            RoundedCornerShape(8.dp),
                        modifier =
                            Modifier.fillMaxWidth()
                    ) {

                        Text(
                            text = "OK",
                            color = CriolloOnPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
        }
    }
}