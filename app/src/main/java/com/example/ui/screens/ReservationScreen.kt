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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.TableRestaurant
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.SampleData
import com.example.model.ReservationState
import com.example.ui.theme.CriolloBackground
import com.example.ui.theme.CriolloOnPrimary
import com.example.ui.theme.CriolloOnSurface
import com.example.ui.theme.CriolloOnSurfaceVariant
import com.example.ui.theme.CriolloOutline
import com.example.ui.theme.CriolloOutlineVariant
import com.example.ui.theme.CriolloPrimary
import com.example.ui.theme.CriolloPrimaryContainer
import com.example.ui.theme.CriolloSurfaceContainer
import com.example.ui.theme.CriolloSurfaceContainerLowest


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ReservationScreen(
    reservation: ReservationState,
    onSelectAmbiance: (String) -> Unit,
    onSelectDate: (String, String, Boolean) -> Unit,
    onSelectTime: (String) -> Unit,
    onChangeGuests: (Int) -> Unit,
    onToggleOccasion: (String) -> Unit,
    onConfirm: () -> Unit,
    onReservationCompleted: () -> Unit,
    modifier: Modifier = Modifier
) {

    // =========================================================
    // ESTADOS FRONTEND
    // =========================================================

    var selectedDay by remember(reservation.dateDay) {
        mutableStateOf(reservation.dateDay)
    }

    var selectedMonth by remember(reservation.dateMonth) {
        mutableStateOf(reservation.dateMonth)
    }

    var selectedIsToday by remember(reservation.isToday) {
        mutableStateOf(reservation.isToday)
    }

    var selectedTime by remember(reservation.timeSlot) {
        mutableStateOf(reservation.timeSlot)
    }

    var showConfirmationDialog by remember {
        mutableStateOf(false)
    }


    // =========================================================
    // DATOS FRONTEND
    // =========================================================

    val dateOptions = listOf(
        Triple("24", "Oct", true),
        Triple("25", "Oct", false),
        Triple("26", "Oct", false),
        Triple("27", "Oct", false),
        Triple("28", "Oct", false),
        Triple("29", "Oct", false),
        Triple("30", "Oct", false)
    )

    val timeSlots = listOf(
        "13:00",
        "14:30",
        "20:30",
        "21:30",
        "22:00"
    )

    val occasions = listOf(
        "Cumpleaños 🎂",
        "Aniversario 🥂",
        "Reunión de Negocios 💼",
        "Cena Casual ✨"
    )


    // =========================================================
    // CONTENIDO PRINCIPAL
    // =========================================================

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CriolloBackground)
            .padding(horizontal = 16.dp),

        contentPadding = PaddingValues(
            top = 16.dp,
            bottom = 32.dp
        ),

        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        // =====================================================
        // HEADER
        // =====================================================

        item {

            Column {

                Text(
                    text = "EXPERIENCIA EXCLUSIVA",
                    style = MaterialTheme.typography.labelSmall,
                    color = CriolloPrimary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Text(
                    text = "Reserva tu Mesa",
                    style = MaterialTheme.typography.headlineLarge,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = CriolloPrimary
                )

                Text(
                    text = "Selecciona tu salón preferido y vive una velada virreinal inigualable.",
                    style = MaterialTheme.typography.bodySmall,
                    color = CriolloOnSurfaceVariant,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }


        // =====================================================
        // 1. AMBIENTE
        // =====================================================

        item {

            Column {

                Text(
                    text = "1. Selecciona el Ambiente",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = CriolloOnSurface
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    AmbianceOptionCard(
                        title = "Patio Virreinal",
                        subtitle = "Al aire libre & acústico",
                        imageUrl = SampleData.PATIO_VIRREINAL_URL,
                        isSelected =
                            reservation.ambiance == "Patio Virreinal",

                        onClick = {
                            onSelectAmbiance("Patio Virreinal")
                        },

                        modifier = Modifier.weight(1f)
                    )

                    AmbianceOptionCard(
                        title = "Cava de Vinos",
                        subtitle = "Íntimo & climatizado",
                        imageUrl = SampleData.CAVA_DE_VINOS_URL,
                        isSelected =
                            reservation.ambiance == "Cava de Vinos",

                        onClick = {
                            onSelectAmbiance("Cava de Vinos")
                        },

                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }


        // =====================================================
        // 2. FECHA
        // =====================================================

        item {

            Column {

                Text(
                    text = "2. Selecciona Fecha (Octubre)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = CriolloOnSurface
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    items(dateOptions.size) { index ->

                        val (day, month, isToday) =
                            dateOptions[index]

                        val isSelected =
                            selectedDay == day

                        Card(
                            shape = RoundedCornerShape(12.dp),

                            colors = CardDefaults.cardColors(
                                containerColor =
                                    if (isSelected) {
                                        CriolloPrimaryContainer
                                    } else {
                                        CriolloSurfaceContainerLowest
                                    }
                            ),

                            modifier = Modifier
                                .size(
                                    width = 58.dp,
                                    height = 68.dp
                                )
                                .clickable {

                                    selectedDay = day
                                    selectedMonth = month
                                    selectedIsToday = isToday

                                    onSelectDate(
                                        day,
                                        month,
                                        isToday
                                    )
                                }
                        ) {

                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment =
                                    Alignment.CenterHorizontally,
                                verticalArrangement =
                                    Arrangement.Center
                            ) {

                                Text(
                                    text =
                                        if (isToday) {
                                            "HOY"
                                        } else {
                                            month.uppercase()
                                        },

                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,

                                    color =
                                        if (isSelected) {
                                            Color.White.copy(
                                                alpha = 0.9f
                                            )
                                        } else {
                                            CriolloOnSurfaceVariant
                                        }
                                )

                                Text(
                                    text = day,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,

                                    color =
                                        if (isSelected) {
                                            Color.White
                                        } else {
                                            CriolloOnSurface
                                        }
                                )

                                Text(
                                    text =
                                        if (isToday) {
                                            "Jue"
                                        } else {
                                            "Oct"
                                        },

                                    fontSize = 10.sp,

                                    color =
                                        if (isSelected) {
                                            Color.White.copy(
                                                alpha = 0.8f
                                            )
                                        } else {
                                            CriolloOutline
                                        }
                                )
                            }
                        }
                    }
                }
            }
        }


        // =====================================================
        // 3. HORA
        // =====================================================

        item {

            Column {

                Text(
                    text = "3. Turno y Hora",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = CriolloOnSurface
                )

                Spacer(modifier = Modifier.height(10.dp))

                FlowRow(
                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp),

                    verticalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {

                    timeSlots.forEach { time ->

                        val isSelected =
                            selectedTime == time

                        Box(
                            modifier = Modifier
                                .background(
                                    if (isSelected) {
                                        CriolloPrimary.copy(
                                            alpha = 0.12f
                                        )
                                    } else {
                                        CriolloSurfaceContainerLowest
                                    },

                                    RoundedCornerShape(8.dp)
                                )

                                .border(
                                    width =
                                        if (isSelected) {
                                            2.dp
                                        } else {
                                            1.dp
                                        },

                                    color =
                                        if (isSelected) {
                                            CriolloPrimary
                                        } else {
                                            CriolloOutlineVariant.copy(
                                                alpha = 0.5f
                                            )
                                        },

                                    shape =
                                        RoundedCornerShape(8.dp)
                                )

                                .clickable {

                                    selectedTime = time

                                    onSelectTime(time)
                                }

                                .padding(
                                    horizontal = 14.dp,
                                    vertical = 8.dp
                                )
                        ) {

                            Text(
                                text = time,
                                fontSize = 12.sp,

                                fontWeight =
                                    if (isSelected) {
                                        FontWeight.Bold
                                    } else {
                                        FontWeight.Medium
                                    },

                                color =
                                    if (isSelected) {
                                        CriolloPrimary
                                    } else {
                                        CriolloOnSurface
                                    }
                            )
                        }
                    }
                }
            }
        }


        // =====================================================
        // 4. COMENSALES
        // =====================================================

        item {

            Column {

                Text(
                    text = "4. Número de Comensales",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = CriolloOnSurface
                )

                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    shape = RoundedCornerShape(12.dp),

                    colors = CardDefaults.cardColors(
                        containerColor =
                            CriolloSurfaceContainerLowest
                    ),

                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),

                        horizontalArrangement =
                            Arrangement.SpaceBetween,

                        verticalAlignment =
                            Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Cantidad de personas:",
                            style =
                                MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )

                        Row(
                            verticalAlignment =
                                Alignment.CenterVertically,

                            horizontalArrangement =
                                Arrangement.spacedBy(10.dp)
                        ) {

                            // RESTAR

                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(
                                        CriolloSurfaceContainer,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable {

                                        if (reservation.guests > 1) {
                                            onChangeGuests(-1)
                                        }
                                    },

                                contentAlignment =
                                    Alignment.Center
                            ) {

                                Text(
                                    text = "−",
                                    fontWeight = FontWeight.Bold,
                                    color = CriolloPrimary,
                                    fontSize = 18.sp
                                )
                            }


                            // CANTIDAD

                            Text(
                                text =
                                    reservation.guests.toString(),

                                style =
                                    MaterialTheme.typography.titleLarge,

                                fontWeight =
                                    FontWeight.Bold,

                                color =
                                    CriolloOnSurface
                            )


                            // SUMAR

                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(
                                        CriolloSurfaceContainer,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable {

                                        if (reservation.guests < 12) {
                                            onChangeGuests(1)
                                        }
                                    },

                                contentAlignment =
                                    Alignment.Center
                            ) {

                                Text(
                                    text = "+",
                                    fontWeight = FontWeight.Bold,
                                    color = CriolloPrimary,
                                    fontSize = 18.sp
                                )
                            }
                        }
                    }
                }
            }
        }


        // =====================================================
        // 5. MOTIVO
        // =====================================================

        item {

            Column {

                Text(
                    text = "5. Motivo de Visita (Opcional)",
                    style =
                        MaterialTheme.typography.titleMedium,
                    fontWeight =
                        FontWeight.Bold,
                    color =
                        CriolloOnSurface
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                FlowRow(
                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp),

                    verticalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {

                    occasions.forEach { tag ->

                        val isSelected =
                            reservation.occasions.contains(tag)

                        Box(
                            modifier = Modifier
                                .background(
                                    if (isSelected) {
                                        CriolloPrimaryContainer
                                    } else {
                                        CriolloSurfaceContainerLowest
                                    },

                                    RoundedCornerShape(20.dp)
                                )

                                .border(
                                    width = 1.dp,

                                    color =
                                        if (isSelected) {
                                            CriolloPrimaryContainer
                                        } else {
                                            CriolloOutlineVariant.copy(
                                                alpha = 0.4f
                                            )
                                        },

                                    shape =
                                        RoundedCornerShape(20.dp)
                                )

                                .clickable {
                                    onToggleOccasion(tag)
                                }

                                .padding(
                                    horizontal = 14.dp,
                                    vertical = 7.dp
                                )
                        ) {

                            Text(
                                text = tag,
                                fontSize = 12.sp,

                                fontWeight =
                                    if (isSelected) {
                                        FontWeight.Bold
                                    } else {
                                        FontWeight.Normal
                                    },

                                color =
                                    if (isSelected) {
                                        Color.White
                                    } else {
                                        CriolloOnSurface
                                    }
                            )
                        }
                    }
                }
            }
        }


        // =====================================================
        // CONFIRMAR RESERVA
        // =====================================================

        item {

            Column(
                modifier = Modifier.padding(top = 8.dp)
            ) {

                Button(
                    onClick = {

                        // Primero mostramos el diálogo.
                        // Todavía NO navegamos.
                        showConfirmationDialog = true
                    },

                    shape =
                        RoundedCornerShape(10.dp),

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                CriolloPrimaryContainer
                        ),

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag(
                            "confirm_reservation_btn"
                        )
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.TableRestaurant,

                        contentDescription = null,

                        tint =
                            CriolloOnPrimary,

                        modifier =
                            Modifier.size(20.dp)
                    )

                    Spacer(
                        modifier =
                            Modifier.width(8.dp)
                    )

                    Text(
                        text =
                            "Confirmar Reserva Inmediata",

                        style =
                            MaterialTheme.typography.titleSmall,

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            CriolloOnPrimary
                    )
                }


                Text(
                    text =
                        "No cobramos depósito previo. " +
                                "Cancelación gratuita con 2h de anticipación.",

                    style =
                        MaterialTheme.typography.bodySmall,

                    color =
                        CriolloOutline,

                    fontSize = 11.sp,

                    modifier = Modifier
                        .padding(top = 6.dp)
                        .align(
                            Alignment.CenterHorizontally
                        )
                )
            }
        }
    }


    // =========================================================
    // VENTANA RESERVA CONFIRMADA
    // =========================================================

    if (showConfirmationDialog) {

        AlertDialog(

            // No se cierra tocando afuera.
            onDismissRequest = {},

            icon = {

                Icon(
                    imageVector =
                        Icons.Default.CheckCircle,

                    contentDescription =
                        "Reserva confirmada",

                    tint =
                        CriolloPrimary,

                    modifier =
                        Modifier.size(48.dp)
                )
            },

            title = {

                Text(
                    text = "¡Reserva confirmada!",
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = CriolloPrimary
                )
            },

            text = {

                Column(
                    modifier =
                        Modifier.fillMaxWidth()
                ) {

                    Text(
                        text =
                            "Tu reserva ha sido registrada correctamente.",

                        color =
                            CriolloOnSurface
                    )

                    Spacer(
                        modifier =
                            Modifier.height(18.dp)
                    )


                    // FECHA

                    Text(
                        text =
                            if (selectedIsToday) {

                                "Fecha: Hoy, $selectedDay de $selectedMonth"

                            } else {

                                "Fecha: $selectedDay de $selectedMonth"
                            },

                        fontWeight =
                            FontWeight.Medium,

                        color =
                            CriolloOnSurface
                    )


                    Spacer(
                        modifier =
                            Modifier.height(6.dp)
                    )


                    // HORA

                    Text(
                        text =
                            "Hora: $selectedTime",

                        fontWeight =
                            FontWeight.Medium,

                        color =
                            CriolloOnSurface
                    )


                    Spacer(
                        modifier =
                            Modifier.height(6.dp)
                    )


                    // PERSONAS

                    Text(
                        text =
                            "Personas: ${reservation.guests}",

                        fontWeight =
                            FontWeight.Medium,

                        color =
                            CriolloOnSurface
                    )


                    Spacer(
                        modifier =
                            Modifier.height(6.dp)
                    )


                    // AMBIENTE

                    Text(
                        text =
                            "Ambiente: ${reservation.ambiance}",

                        fontWeight =
                            FontWeight.Medium,

                        color =
                            CriolloOnSurface
                    )


                    // MOTIVO

                    if (reservation.occasions.isNotEmpty()) {

                        Spacer(
                            modifier =
                                Modifier.height(6.dp)
                        )

                        Text(
                            text =
                                "Motivo: ${
                                    reservation.occasions.joinToString(
                                        ", "
                                    )
                                }",

                            fontWeight =
                                FontWeight.Medium,

                            color =
                                CriolloOnSurface
                        )
                    }


                    Spacer(
                        modifier =
                            Modifier.height(20.dp)
                    )


                    // CORREO

                    Text(
                        text =
                            "La confirmación fue enviada a tu correo registrado.",

                        style =
                            MaterialTheme.typography.bodyMedium,

                        fontWeight =
                            FontWeight.SemiBold,

                        color =
                            CriolloPrimary
                    )


                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )


                    Text(
                        text =
                            "También podrás consultar esta reserva desde tu perfil.",

                        style =
                            MaterialTheme.typography.bodySmall,

                        color =
                            CriolloOnSurfaceVariant
                    )
                }
            },


            // =================================================
            // ACEPTAR Y VER RESERVA
            // =================================================

            confirmButton = {

                Button(
                    onClick = {

                        // 1. Cerramos la ventana
                        showConfirmationDialog = false

                        // 2. Confirmamos la reserva
                        onConfirm()

                        // 3. Navegamos al perfil
                        onReservationCompleted()
                    },

                    modifier =
                        Modifier.fillMaxWidth(),

                    colors =
                        ButtonDefaults.buttonColors(
                            containerColor =
                                CriolloPrimaryContainer
                        )
                ) {

                    Text(
                        text =
                            "Aceptar y ver mi reserva",

                        fontWeight =
                            FontWeight.Bold,

                        color =
                            CriolloOnPrimary
                    )
                }
            }
        )
    }
}


// =============================================================
// TARJETA DE AMBIENTE
// =============================================================

@Composable
fun AmbianceOptionCard(
    title: String,
    subtitle: String,
    imageUrl: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Card(
        shape =
            RoundedCornerShape(12.dp),

        colors =
            CardDefaults.cardColors(
                containerColor =
                    if (isSelected) {

                        CriolloPrimary.copy(
                            alpha = 0.05f
                        )

                    } else {

                        CriolloSurfaceContainerLowest
                    }
            ),

        border =
            if (isSelected) {

                CardDefaults
                    .outlinedCardBorder()
                    .copy(
                        brush =
                            Brush.linearGradient(
                                listOf(
                                    CriolloPrimary,
                                    CriolloPrimary
                                )
                            ),

                        width = 2.dp
                    )

            } else {

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
                    )
            },

        modifier =
            modifier.clickable(
                onClick = onClick
            )
    ) {

        Column(
            modifier =
                Modifier.padding(8.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(84.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
            ) {

                AsyncImage(
                    model =
                        imageUrl,

                    contentDescription =
                        title,

                    contentScale =
                        ContentScale.Crop,

                    modifier =
                        Modifier.fillMaxSize()
                )
            }


            Spacer(
                modifier =
                    Modifier.height(6.dp)
            )


            Text(
                text = title,

                style =
                    MaterialTheme.typography.titleSmall,

                fontFamily =
                    FontFamily.Serif,

                fontWeight =
                    FontWeight.Bold,

                color =
                    if (isSelected) {
                        CriolloPrimary
                    } else {
                        CriolloOnSurface
                    }
            )


            Text(
                text = subtitle,

                fontSize = 10.sp,

                color =
                    CriolloOnSurfaceVariant
            )
        }
    }
}