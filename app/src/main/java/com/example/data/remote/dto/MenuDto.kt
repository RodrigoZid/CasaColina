
package com.example.data.remote.dto

// Plato almacenado en DynamoDB
data class MenuDto(
    val platoId: String = "",
    val nombre: String = "",
    val descripcionCorta: String = "",
    val descripcionCompleta: String = "",
    val categoria: String = "",
    val precio: Double = 0.0,
    val imagenUrl: String = "",
    val disponible: Boolean = true,
    val destacadoChef: Boolean = false,
    val tiempoPreparacion: Int = 0
)

// Respuesta para consultar el menú
data class ListaMenuResponse(
    val mensaje: String? = null,
    val total: Int = 0,
    val categoria: String? = null,
    val platos: List<MenuDto> = emptyList()
)

// Disponibilidad de un plato
data class DisponibilidadPlatoDto(
    val platoId: String = "",
    val disponible: Boolean = true
)