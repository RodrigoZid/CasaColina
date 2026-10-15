
package com.example.model

// ============================================================
// PANTALLAS DE LA APLICACIÓN
// ============================================================

enum class Screen {
    LOGIN,
    HOME,
    MENU,
    DISH_DETAIL,
    RESERVATIONS,
    CART,
    PROFILE
}

// ============================================================
// CATEGORÍAS DEL MENÚ
// ============================================================

enum class MenuCategory(
    val displayName: String
) {
    ALL("Todos"),
    ENTRADAS("Entradas"),
    FONDOS("Fondos Criollos"),
    CAVA("De la Cava"),
    POSTRES("Postres");

    companion object {

        fun fromBackend(value: String): MenuCategory {
            val normalized = value.trim().lowercase()

            return when (normalized) {
                "entradas", "entrada" -> ENTRADAS

                "fondos", "fondo",
                "fondos criollos" -> FONDOS

                "cava", "de la cava",
                "bebidas" -> CAVA

                "postres", "postre" -> POSTRES

                else -> ALL
            }
        }
    }
}

// ============================================================
// PLATO DEL MENÚ
// ============================================================

data class Dish(
    val id: String,
    val name: String,
    val category: MenuCategory,
    val price: Double,
    val prepTime: String,
    val shortDescription: String,
    val fullDescription: String,
    val imageUrl: String,
    val badge: String? = null,
    val isChefPick: Boolean = false,
    val rating: Double = 0.0,
    val reviewsCount: Int = 0,
    val available: Boolean = true
)

// ============================================================
// ELEMENTO DEL CARRITO
// ============================================================

data class CartItem(
    val id: String,
    val dish: Dish,
    val doneness: String = "",
    val extras: List<String> = emptyList(),
    val quantity: Int = 1,
    val unitPrice: Double,
    val createdAt: String = ""
) {
    val totalPrice: Double
        get() = unitPrice * quantity
}

// ============================================================
// ESTADO DE RESERVA
// ============================================================

data class ReservationState(

    // Ambiente seleccionado
    val ambiance: String = "",

    // Valores para mostrar la fecha en pantalla
    val dateDay: String = "",
    val dateMonth: String = "",

    // Indica si corresponde a la fecha actual
    val isToday: Boolean = false,

    // Hora seleccionada
    val timeSlot: String = "",

    // Número de personas
    val guests: Int = 1,

    // Motivos de visita
    val occasions: Set<String> = emptySet(),

    // Identificador de la reserva en DynamoDB
    val reservationId: String = "",

    // Fecha completa en formato YYYY-MM-DD
    val dateIso: String = "",

    // Hora en formato HH:mm
    val time24h: String = "",

    // Observaciones adicionales
    val observations: String = "",

    // Estado devuelto por Lambda
    val status: String = "",

    // Fechas de registro y modificación
    val createdAt: String = "",
    val updatedAt: String = ""
)

// ============================================================
// HISTORIAL DE PEDIDOS
// ============================================================

data class OrderHistory(

    // Identificador del pedido
    val id: String,

    // Descripción de los productos
    val description: String,

    // Fecha del pedido
    val date: String,

    // Modalidad de entrega
    val deliveryMode: String,

    // Método de pago
    val paymentMethod: String,

    // Importe total
    val total: Double,

    // Estado del pedido
    val status: String = "",

    // Estado del pago
    val paymentStatus: String = "",

    // Dirección de entrega
    val deliveryAddress: String = "",

    // Identificador del usuario
    val userId: String = "",

    // Fecha completa del registro
    val createdAt: String = ""
)
