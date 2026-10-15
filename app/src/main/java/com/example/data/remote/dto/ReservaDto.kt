
package com.example.data.remote.dto

// Solicitud para crear una reserva
data class CrearReservaRequest(
    val usuarioId: String,
    val fecha: String,
    val hora: String,
    val personas: Int,
    val ambiente: String,
    val observaciones: String? = null
)

// Reserva almacenada en DynamoDB
data class ReservaDto(
    val reservaId: String = "",
    val usuarioId: String = "",
    val fecha: String = "",
    val hora: String = "",
    val personas: Int = 1,
    val observaciones: String? = null,
    val estado: String = "pendiente",
    val fechaCreacion: String = "",
    val fechaModificacion: String = ""
)

// Respuesta al registrar una reserva
data class ReservaResponse(
    val mensaje: String? = null,
    val reserva: ReservaDto? = null
)

// Respuesta para verificar disponibilidad
data class DisponibilidadReservaResponse(
    val disponibleReferencial: Boolean = false,
    val reservasRegistradas: Int = 0,
    val cupoReferencial: Int = 0,
    val aviso: String? = null,
    val fecha: String? = null,
    val hora: String? = null,
    val mensaje: String? = null
)