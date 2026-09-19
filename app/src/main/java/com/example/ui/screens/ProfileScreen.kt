package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.SampleData
import com.example.model.OrderHistory
import com.example.model.ReservationState
import com.example.ui.theme.CriolloBackground
import com.example.ui.theme.CriolloOnSurface
import com.example.ui.theme.CriolloOnSurfaceVariant
import com.example.ui.theme.CriolloOutlineVariant
import com.example.ui.theme.CriolloPrimary
import com.example.ui.theme.CriolloPrimaryContainer
import com.example.ui.theme.CriolloSecondaryFixed
import com.example.ui.theme.CriolloSurfaceContainer
import com.example.ui.theme.CriolloSurfaceContainerLowest
import com.example.ui.theme.CriolloTertiary


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(

    userPoints: Int,

    // Reserva actual
    reservation: ReservationState,

    // Historial dinámico de pedidos
    orderHistory: List<OrderHistory>,

    onRedeemPoints: () -> Unit,
    onOpenQr: () -> Unit,
    onActionClicked: (String) -> Unit,
    onLogout: () -> Unit,

    modifier: Modifier = Modifier
) {

    val preferences = listOf(
        "Picante moderado",
        "Sin mariscos crudos",
        "Mesa cerca a terraza"
    )


    LazyColumn(

        modifier = modifier
            .fillMaxSize()
            .background(CriolloBackground)
            .padding(horizontal = 16.dp),

        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 32.dp
        ),

        verticalArrangement =
            Arrangement.spacedBy(16.dp)

    ) {


        // =====================================================
        // TARJETA VIP
        // =====================================================

        item {

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(

                        Brush.linearGradient(

                            listOf(
                                CriolloPrimary,
                                CriolloPrimaryContainer
                            )
                        )
                    )
                    .padding(18.dp)
            ) {

                Column {

                    Row(

                        verticalAlignment =
                            Alignment.CenterVertically,

                        horizontalArrangement =
                            Arrangement.spacedBy(14.dp)
                    ) {

                        Box(

                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .border(
                                    2.dp,
                                    Color.White.copy(
                                        alpha = 0.7f
                                    ),
                                    CircleShape
                                )
                        ) {

                            AsyncImage(

                                model =
                                    SampleData.PROFILE_AVATAR_URL,

                                contentDescription =
                                    "Rodrigo Torres",

                                contentScale =
                                    ContentScale.Crop,

                                modifier =
                                    Modifier.fillMaxSize()
                            )
                        }


                        Column {

                            Text(
                                text = "Rodrigo Torres",

                                style =
                                    MaterialTheme.typography.titleLarge,

                                fontFamily =
                                    FontFamily.Serif,

                                color =
                                    Color.White,

                                fontWeight =
                                    FontWeight.Bold
                            )


                            Text(
                                text =
                                    "Socio Gourmet Nivel Oro",

                                fontSize = 12.sp,

                                color =
                                    CriolloSecondaryFixed,

                                fontWeight =
                                    FontWeight.SemiBold
                            )


                            Text(
                                text =
                                    "Miembro desde Octubre 2021",

                                fontSize = 10.sp,

                                color =
                                    Color.White.copy(
                                        alpha = 0.7f
                                    )
                            )
                        }
                    }


                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )


                    Divider(
                        color =
                            Color.White.copy(
                                alpha = 0.2f
                            )
                    )


                    Spacer(
                        modifier =
                            Modifier.height(12.dp)
                    )


                    Row(

                        modifier =
                            Modifier.fillMaxWidth(),

                        horizontalArrangement =
                            Arrangement.SpaceBetween,

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Column {

                            Text(
                                text =
                                    "PUNTOS ACUMULADOS",

                                fontSize = 10.sp,

                                color =
                                    Color.White.copy(
                                        alpha = 0.8f
                                    ),

                                fontWeight =
                                    FontWeight.Bold,

                                letterSpacing =
                                    0.6.sp
                            )


                            Text(
                                text =
                                    "$userPoints pts",

                                style =
                                    MaterialTheme.typography.headlineMedium,

                                fontFamily =
                                    FontFamily.Serif,

                                color =
                                    Color.White,

                                fontWeight =
                                    FontWeight.Bold
                            )
                        }


                        Button(

                            onClick =
                                onRedeemPoints,

                            colors =
                                ButtonDefaults.buttonColors(

                                    containerColor =
                                        Color.White,

                                    contentColor =
                                        CriolloPrimary
                                ),

                            shape =
                                RoundedCornerShape(8.dp),

                            contentPadding =
                                PaddingValues(
                                    horizontal = 12.dp,
                                    vertical = 6.dp
                                )
                        ) {

                            Text(
                                text =
                                    "Canjear Puntos",

                                fontSize = 11.sp,

                                fontWeight =
                                    FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }


        // =====================================================
        // PASE DIGITAL DE RESERVA
        // =====================================================

        item {

            Card(

                shape =
                    RoundedCornerShape(14.dp),

                colors =
                    CardDefaults.cardColors(
                        containerColor =
                            CriolloSurfaceContainerLowest
                    ),

                border =
                    CardDefaults
                        .outlinedCardBorder()
                        .copy(

                            brush =
                                Brush.linearGradient(

                                    listOf(

                                        CriolloOutlineVariant
                                            .copy(
                                                alpha = 0.4f
                                            ),

                                        CriolloOutlineVariant
                                            .copy(
                                                alpha = 0.4f
                                            )
                                    )
                                )
                        ),

                elevation =
                    CardDefaults.cardElevation(
                        defaultElevation = 1.dp
                    ),

                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Column(

                    modifier =
                        Modifier.padding(14.dp)
                ) {


                    Row(

                        modifier =
                            Modifier.fillMaxWidth(),

                        horizontalArrangement =
                            Arrangement.SpaceBetween,

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {


                        Text(
                            text =
                                "Pase Digital de Reserva Activa",

                            style =
                                MaterialTheme.typography.titleSmall,

                            fontFamily =
                                FontFamily.Serif,

                            fontWeight =
                                FontWeight.Bold,

                            color =
                                CriolloOnSurface
                        )


                        Box(

                            modifier = Modifier
                                .background(

                                    CriolloTertiary.copy(
                                        alpha = 0.12f
                                    ),

                                    RoundedCornerShape(4.dp)
                                )

                                .padding(
                                    horizontal = 6.dp,
                                    vertical = 2.dp
                                )
                        ) {

                            Text(
                                text =
                                    "Confirmada",

                                fontSize = 11.sp,

                                color =
                                    CriolloTertiary,

                                fontWeight =
                                    FontWeight.Bold
                            )
                        }
                    }


                    Spacer(
                        modifier =
                            Modifier.height(10.dp)
                    )


                    Row(

                        modifier = Modifier
                            .fillMaxWidth()

                            .background(

                                CriolloSurfaceContainer.copy(
                                    alpha = 0.6f
                                ),

                                RoundedCornerShape(10.dp)
                            )

                            .clickable(
                                onClick =
                                    onOpenQr
                            )

                            .padding(12.dp),

                        verticalAlignment =
                            Alignment.CenterVertically,

                        horizontalArrangement =
                            Arrangement.spacedBy(12.dp)
                    ) {


                        // =========================================
                        // QR
                        // =========================================

                        Box(

                            modifier = Modifier
                                .size(56.dp)

                                .background(
                                    Color.White,
                                    RoundedCornerShape(8.dp)
                                )

                                .border(
                                    1.dp,

                                    CriolloOutlineVariant.copy(
                                        alpha = 0.4f
                                    ),

                                    RoundedCornerShape(8.dp)
                                ),

                            contentAlignment =
                                Alignment.Center
                        ) {

                            Icon(

                                imageVector =
                                    Icons.Default.QrCode2,

                                contentDescription =
                                    "Pase QR",

                                tint =
                                    CriolloPrimary,

                                modifier =
                                    Modifier.size(42.dp)
                            )
                        }


                        // =========================================
                        // RESERVA DINÁMICA
                        // =========================================

                        Column(

                            modifier =
                                Modifier.weight(1f)
                        ) {


                            Text(

                                text =
                                    "Mesa para ${reservation.guests} comensales",

                                style =
                                    MaterialTheme.typography.titleSmall,

                                fontWeight =
                                    FontWeight.Bold
                            )


                            Text(

                                text = buildString {

                                    append(
                                        reservation.ambiance
                                    )

                                    append(" • ")


                                    if (
                                        reservation.isToday
                                    ) {

                                        append("Hoy")

                                    } else {

                                        append(
                                            "${reservation.dateDay} ${reservation.dateMonth}"
                                        )
                                    }


                                    append(" • ")

                                    append(
                                        reservation.timeSlot
                                    )

                                    append(" hrs")
                                },

                                style =
                                    MaterialTheme.typography.bodySmall,

                                color =
                                    CriolloOnSurfaceVariant
                            )


                            if (
                                reservation.occasions
                                    .isNotEmpty()
                            ) {

                                Text(

                                    text =
                                        reservation.occasions
                                            .joinToString(", "),

                                    fontSize = 10.sp,

                                    color =
                                        CriolloOnSurfaceVariant,

                                    modifier =
                                        Modifier.padding(
                                            top = 2.dp
                                        )
                                )
                            }


                            Text(

                                text =
                                    "Toca el código QR para ampliar",

                                fontSize = 10.sp,

                                color =
                                    CriolloPrimary,

                                fontWeight =
                                    FontWeight.SemiBold,

                                modifier =
                                    Modifier.padding(
                                        top = 2.dp
                                    )
                            )
                        }
                    }
                }
            }
        }


        // =====================================================
        // PREFERENCIAS
        // =====================================================

        item {

            Card(

                shape =
                    RoundedCornerShape(14.dp),

                colors =
                    CardDefaults.cardColors(

                        containerColor =
                            CriolloSurfaceContainerLowest
                    ),

                border =
                    CardDefaults
                        .outlinedCardBorder()
                        .copy(

                            brush =
                                Brush.linearGradient(

                                    listOf(

                                        CriolloOutlineVariant.copy(
                                            alpha = 0.4f
                                        ),

                                        CriolloOutlineVariant.copy(
                                            alpha = 0.4f
                                        )
                                    )
                                )
                        ),

                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Column(

                    modifier =
                        Modifier.padding(14.dp)
                ) {


                    Text(
                        text =
                            "Preferencias Registradas",

                        style =
                            MaterialTheme.typography.titleSmall,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            CriolloOnSurface
                    )


                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )


                    FlowRow(

                        horizontalArrangement =
                            Arrangement.spacedBy(8.dp),

                        verticalArrangement =
                            Arrangement.spacedBy(6.dp)
                    ) {

                        preferences.forEach { pref ->

                            Box(

                                modifier = Modifier

                                    .background(

                                        CriolloSurfaceContainer,

                                        RoundedCornerShape(16.dp)
                                    )

                                    .padding(
                                        horizontal = 10.dp,
                                        vertical = 5.dp
                                    )
                            ) {

                                Text(
                                    text = pref,

                                    fontSize = 11.sp,

                                    color =
                                        CriolloOnSurfaceVariant,

                                    fontWeight =
                                        FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }


        // =====================================================
        // ÚLTIMOS PEDIDOS DINÁMICOS
        // =====================================================

        item {

            Card(

                shape =
                    RoundedCornerShape(14.dp),

                colors =
                    CardDefaults.cardColors(

                        containerColor =
                            CriolloSurfaceContainerLowest
                    ),

                border =
                    CardDefaults
                        .outlinedCardBorder()
                        .copy(

                            brush =
                                Brush.linearGradient(

                                    listOf(

                                        CriolloOutlineVariant.copy(
                                            alpha = 0.4f
                                        ),

                                        CriolloOutlineVariant.copy(
                                            alpha = 0.4f
                                        )
                                    )
                                )
                        ),

                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Column(

                    modifier =
                        Modifier.padding(14.dp),

                    verticalArrangement =
                        Arrangement.spacedBy(10.dp)
                ) {


                    Text(

                        text =
                            "Últimos Pedidos",

                        style =
                            MaterialTheme.typography.titleSmall,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            CriolloOnSurface
                    )


                    // =============================================
                    // SI TODAVÍA NO HAY PEDIDOS
                    // =============================================

                    if (orderHistory.isEmpty()) {

                        Text(

                            text =
                                "Todavía no tienes pedidos realizados.",

                            style =
                                MaterialTheme.typography.bodySmall,

                            color =
                                CriolloOnSurfaceVariant,

                            modifier =
                                Modifier.padding(
                                    vertical = 8.dp
                                )
                        )

                    } else {


                        // =========================================
                        // MOSTRAR PEDIDOS DINÁMICAMENTE
                        // =========================================

                        orderHistory.forEachIndexed { index, order ->


                            Row(

                                modifier =
                                    Modifier.fillMaxWidth(),

                                horizontalArrangement =
                                    Arrangement.SpaceBetween,

                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {


                                Column(

                                    modifier =
                                        Modifier.weight(1f)
                                ) {


                                    // NOMBRE / DESCRIPCIÓN

                                    Text(

                                        text =
                                            order.description,

                                        fontSize = 12.sp,

                                        fontWeight =
                                            FontWeight.SemiBold,

                                        color =
                                            CriolloOnSurface
                                    )


                                    Spacer(
                                        modifier =
                                            Modifier.height(2.dp)
                                    )


                                    // FECHA + MODALIDAD

                                    Text(

                                        text =
                                            "${order.date} • ${order.deliveryMode}",

                                        fontSize = 10.sp,

                                        color =
                                            CriolloOnSurfaceVariant
                                    )


                                    // MÉTODO DE PAGO

                                    Text(

                                        text =
                                            order.paymentMethod,

                                        fontSize = 9.sp,

                                        color =
                                            CriolloOnSurfaceVariant.copy(
                                                alpha = 0.8f
                                            )
                                    )
                                }


                                // =================================
                                // PRECIO
                                // =================================

                                Text(

                                    text =
                                        "S/ %.2f".format(
                                            order.total
                                        ),

                                    fontSize = 12.sp,

                                    fontWeight =
                                        FontWeight.Bold,

                                    color =
                                        CriolloPrimary,

                                    modifier =
                                        Modifier.padding(
                                            start = 10.dp
                                        )
                                )
                            }


                            // =====================================
                            // DIVISOR
                            // No mostrar después del último
                            // =====================================

                            if (
                                index <
                                orderHistory.lastIndex
                            ) {

                                Divider(

                                    color =
                                        CriolloOutlineVariant
                                            .copy(
                                                alpha = 0.3f
                                            )
                                )
                            }
                        }
                    }
                }
            }
        }


        // =====================================================
        // ACCIONES DE CUENTA
        // =====================================================

        item {

            Column(

                verticalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {


                // =================================================
                // MÉTODOS DE PAGO
                // =================================================

                Card(

                    shape =
                        RoundedCornerShape(10.dp),

                    colors =
                        CardDefaults.cardColors(

                            containerColor =
                                CriolloSurfaceContainerLowest
                        ),

                    border =
                        CardDefaults
                            .outlinedCardBorder()
                            .copy(

                                brush =
                                    Brush.linearGradient(

                                        listOf(

                                            CriolloOutlineVariant
                                                .copy(
                                                    alpha = 0.4f
                                                ),

                                            CriolloOutlineVariant
                                                .copy(
                                                    alpha = 0.4f
                                                )
                                        )
                                    )
                            ),

                    modifier = Modifier
                        .fillMaxWidth()

                        .clickable {

                            onActionClicked(
                                "Abriendo métodos de pago guardados"
                            )
                        }
                ) {

                    Row(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),

                        horizontalArrangement =
                            Arrangement.SpaceBetween,

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {


                        Row(

                            verticalAlignment =
                                Alignment.CenterVertically,

                            horizontalArrangement =
                                Arrangement.spacedBy(10.dp)
                        ) {


                            Icon(

                                imageVector =
                                    Icons.Default.CreditCard,

                                contentDescription =
                                    null,

                                tint =
                                    CriolloPrimary,

                                modifier =
                                    Modifier.size(20.dp)
                            )


                            Text(

                                text =
                                    "Métodos de Pago Guardados",

                                fontSize = 12.sp,

                                fontWeight =
                                    FontWeight.SemiBold
                            )
                        }


                        Icon(

                            imageVector =
                                Icons.AutoMirrored
                                    .Filled
                                    .KeyboardArrowRight,

                            contentDescription =
                                null,

                            tint =
                                CriolloOnSurfaceVariant,

                            modifier =
                                Modifier.size(18.dp)
                        )
                    }
                }


                // =================================================
                // CONCIERGE
                // =================================================

                Card(

                    shape =
                        RoundedCornerShape(10.dp),

                    colors =
                        CardDefaults.cardColors(

                            containerColor =
                                CriolloSurfaceContainerLowest
                        ),

                    border =
                        CardDefaults
                            .outlinedCardBorder()
                            .copy(

                                brush =
                                    Brush.linearGradient(

                                        listOf(

                                            CriolloOutlineVariant
                                                .copy(
                                                    alpha = 0.4f
                                                ),

                                            CriolloOutlineVariant
                                                .copy(
                                                    alpha = 0.4f
                                                )
                                        )
                                    )
                            ),

                    modifier = Modifier
                        .fillMaxWidth()

                        .clickable {

                            onActionClicked(

                                "Abriendo soporte VIP de WhatsApp Casa Colina"
                            )
                        }
                ) {

                    Row(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),

                        horizontalArrangement =
                            Arrangement.SpaceBetween,

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {


                        Row(

                            verticalAlignment =
                                Alignment.CenterVertically,

                            horizontalArrangement =
                                Arrangement.spacedBy(10.dp)
                        ) {


                            Icon(

                                imageVector =
                                    Icons.Default.SupportAgent,

                                contentDescription =
                                    null,

                                tint =
                                    CriolloTertiary,

                                modifier =
                                    Modifier.size(20.dp)
                            )


                            Text(

                                text =
                                    "Concierge VIP de Reservas",

                                fontSize = 12.sp,

                                fontWeight =
                                    FontWeight.SemiBold
                            )
                        }


                        Icon(

                            imageVector =
                                Icons.AutoMirrored
                                    .Filled
                                    .KeyboardArrowRight,

                            contentDescription =
                                null,

                            tint =
                                CriolloOnSurfaceVariant,

                            modifier =
                                Modifier.size(18.dp)
                        )
                    }
                }


                // =================================================
                // CERRAR SESIÓN
                // =================================================

                Card(

                    shape =
                        RoundedCornerShape(10.dp),

                    colors =
                        CardDefaults.cardColors(

                            containerColor =
                                MaterialTheme
                                    .colorScheme
                                    .errorContainer
                        ),

                    modifier = Modifier
                        .fillMaxWidth()

                        .clickable {

                            onLogout()
                        }
                ) {

                    Row(

                        modifier = Modifier
                            .fillMaxWidth()

                            .padding(
                                horizontal = 12.dp,
                                vertical = 14.dp
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
                                Arrangement.spacedBy(10.dp)
                        ) {


                            Icon(

                                imageVector =
                                    Icons.AutoMirrored
                                        .Filled
                                        .Logout,

                                contentDescription =
                                    "Cerrar sesión",

                                tint =
                                    MaterialTheme
                                        .colorScheme
                                        .error,

                                modifier =
                                    Modifier.size(20.dp)
                            )


                            Text(

                                text =
                                    "Cerrar sesión",

                                fontSize = 12.sp,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    MaterialTheme
                                        .colorScheme
                                        .error
                            )
                        }


                        Icon(

                            imageVector =
                                Icons.AutoMirrored
                                    .Filled
                                    .KeyboardArrowRight,

                            contentDescription =
                                null,

                            tint =
                                MaterialTheme
                                    .colorScheme
                                    .error,

                            modifier =
                                Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}