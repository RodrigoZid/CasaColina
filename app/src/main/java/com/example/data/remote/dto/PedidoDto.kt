
package com.example.data.remote.dto

// Producto incluido dentro de un pedido
data class ItemPedidoDto(
    val platoId: String = "",
    val cantidad: Int = 1,
    val precioUnitario: Double = 0.0
)

// Solicitud para crear un pedido
data class CrearPedidoRequest(
    val usuarioId: String,
    val items: List<ItemPedidoDto>,
    val modalidadEntrega: String,
    val metodoPago: String,
    val direccionEntrega: String? = null
)

// Pedido almacenado en DynamoDB
data class PedidoDto(
    val pedidoId: String = "",
    val usuarioId: String = "",
    val items: List<ItemPedidoDto> = emptyList(),
    val total: Double = 0.0,
    val estado: String = "pendiente",
    val modalidadEntrega: String = "",
    val metodoPago: String = "",
    val estadoPago: String = "pendiente",
    val direccionEntrega: String? = null,
    val fechaCreacion: String = ""
)

// Respuesta al crear un pedido
data class PedidoResponse(
    val mensaje: String? = null,
    val pedido: PedidoDto? = null
)