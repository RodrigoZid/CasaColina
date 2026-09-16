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
import androidx.compose.material.icons.filled.TableRestaurant
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
    modifier: Modifier = Modifier
) {
    val dateOptions = listOf(
        Triple("24", "Jue", true),
        Triple("25", "Oct", false),
        Triple("26", "Oct", false),
        Triple("27", "Oct", false),
        Triple("28", "Oct", false)
    )

    val timeSlots = listOf("13:00 PM", "14:30 PM", "20:30 PM", "21:30 PM", "22:00 PM")
    val occasions = listOf("Cumpleaños 🎂", "Aniversario 🥂", "Reunión de Negocios 💼", "Cena Casual ✨")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(CriolloBackground)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Header
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

        // 1. Selector de Ambientes
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
                        isSelected = reservation.ambiance == "Patio Virreinal",
                        onClick = { onSelectAmbiance("Patio Virreinal") },
                        modifier = Modifier.weight(1f)
                    )
                    AmbianceOptionCard(
                        title = "Cava de Vinos",
                        subtitle = "Íntimo & climatizado",
                        imageUrl = SampleData.CAVA_DE_VINOS_URL,
                        isSelected = reservation.ambiance == "Cava de Vinos",
                        onClick = { onSelectAmbiance("Cava de Vinos") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // 2. Fecha de Reserva
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
                    items(dateOptions.size) { idx ->
                        val (day, month, isToday) = dateOptions[idx]
                        val isSelected = reservation.dateDay == day
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) CriolloPrimaryContainer else CriolloSurfaceContainerLowest
                            ),
                            border = if (isSelected) null else CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(CriolloOutlineVariant.copy(alpha = 0.4f), CriolloOutlineVariant.copy(alpha = 0.4f)))),
                            modifier = Modifier
                                .size(width = 58.dp, height = 68.dp)
                                .clickable { onSelectDate(day, month, isToday) }
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = if (isToday) "HOY" else month.uppercase(),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White.copy(alpha = 0.9f) else CriolloOnSurfaceVariant
                                )
                                Text(
                                    text = day,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) Color.White else CriolloOnSurface
                                )
                                Text(
                                    text = if (isToday) "Jue" else "Oct",
                                    fontSize = 10.sp,
                                    color = if (isSelected) Color.White.copy(alpha = 0.8f) else CriolloOutline
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3. Horario y Turno
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
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    timeSlots.forEach { time ->
                        val isSelected = reservation.timeSlot == time
                        Box(
                            modifier = Modifier
                                .background(
                                    if (isSelected) CriolloPrimary.copy(alpha = 0.12f) else CriolloSurfaceContainerLowest,
                                    RoundedCornerShape(8.dp)
                                )
                                .border(
                                    if (isSelected) 2.dp else 1.dp,
                                    if (isSelected) CriolloPrimary else CriolloOutlineVariant.copy(alpha = 0.5f),
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { onSelectTime(time) }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = time,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) CriolloPrimary else CriolloOnSurface
                            )
                        }
                    }
                }
            }
        }

        // 4. Número de Comensales
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
                    colors = CardDefaults.cardColors(containerColor = CriolloSurfaceContainerLowest),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(CriolloOutlineVariant.copy(alpha = 0.4f), CriolloOutlineVariant.copy(alpha = 0.4f)))),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Cantidad de personas:",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(CriolloSurfaceContainer, RoundedCornerShape(8.dp))
                                    .clickable { onChangeGuests(-1) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text("−", fontWeight = FontWeight.Bold, color = CriolloPrimary, fontSize = 18.sp)
                            }

                            Text(
                                text = reservation.guests.toString(),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = CriolloOnSurface
                            )

                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(CriolloSurfaceContainer, RoundedCornerShape(8.dp))
                                    .clickable { onChangeGuests(1) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text("+", fontWeight = FontWeight.Bold, color = CriolloPrimary, fontSize = 18.sp)
                            }
                        }
                    }
                }
            }
        }

        // 5. Motivo de Visita (Opcional)
        item {
            Column {
                Text(
                    text = "5. Motivo de Visita (Opcional)",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = CriolloOnSurface
                )
                Spacer(modifier = Modifier.height(10.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    occasions.forEach { tag ->
                        val isSelected = reservation.occasions.contains(tag)
                        Box(
                            modifier = Modifier
                                .background(
                                    if (isSelected) CriolloPrimaryContainer else CriolloSurfaceContainerLowest,
                                    RoundedCornerShape(20.dp)
                                )
                                .border(
                                    1.dp,
                                    if (isSelected) CriolloPrimaryContainer else CriolloOutlineVariant.copy(alpha = 0.4f),
                                    RoundedCornerShape(20.dp)
                                )
                                .clickable { onToggleOccasion(tag) }
                                .padding(horizontal = 14.dp, vertical = 7.dp)
                        ) {
                            Text(
                                text = tag,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.White else CriolloOnSurface
                            )
                        }
                    }
                }
            }
        }

        // Botón Confirmar Reserva
        item {
            Column(modifier = Modifier.padding(top = 8.dp)) {
                Button(
                    onClick = onConfirm,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = CriolloPrimaryContainer),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("confirm_reservation_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.TableRestaurant,
                        contentDescription = null,
                        tint = CriolloOnPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Confirmar Reserva Inmediata",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = CriolloOnPrimary
                    )
                }

                Text(
                    text = "No cobramos depósito previo. Cancelación gratuita con 2h de anticipación.",
                    style = MaterialTheme.typography.bodySmall,
                    color = CriolloOutline,
                    fontSize = 11.sp,
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

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
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) CriolloPrimary.copy(alpha = 0.05f) else CriolloSurfaceContainerLowest
        ),
        border = if (isSelected) CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(CriolloPrimary, CriolloPrimary)), width = 2.dp)
        else CardDefaults.outlinedCardBorder().copy(brush = Brush.linearGradient(listOf(CriolloOutlineVariant.copy(alpha = 0.4f), CriolloOutlineVariant.copy(alpha = 0.4f)))),
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(84.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) CriolloPrimary else CriolloOnSurface
            )

            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = CriolloOnSurfaceVariant
            )
        }
    }
}
