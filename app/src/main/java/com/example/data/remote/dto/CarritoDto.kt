
package com.example.data.remote.dto

// Solicitud para agregar un plato al carrito
data class AgregarCarritoRequest(
    val usuarioId: String,
    val platoId: String,
    val cantidad: Int
)

// Solicitud para actualizar cantidad
data class ActualizarCarritoRequest(
    val cantidad: Int
)

// Producto almacenado en DynamoDB
data class ItemCarritoDto(
    val usuarioId: String = "",
    val platoId: String = "",
    val cantidad: Int = 1,
    val precioUnitario: Double = 0.0,
    val subtotal: Double = 0.0,
    val fechaCreacion: String = ""
)

// Respuesta de consulta del carrito
data class CarritoResponse(
    val mensaje: String? = null,
    val items: List<ItemCarritoDto> = emptyList(),
    val total: Double = 0.0
)