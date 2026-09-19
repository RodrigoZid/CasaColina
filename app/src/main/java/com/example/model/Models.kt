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

    POSTRES("Postres")
}


// ============================================================
// PLATO
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

    val rating: Double = 4.9,

    val reviewsCount: Int = 340
)


// ============================================================
// ELEMENTO DEL CARRITO
// ============================================================

data class CartItem(

    val id: String,

    val dish: Dish,

    val doneness: String = "Término Medio",

    val extras: List<String> = emptyList(),

    val quantity: Int = 1,

    val unitPrice: Double

) {

    val totalPrice: Double
        get() = unitPrice * quantity
}


// ============================================================
// ESTADO DE RESERVA
// ============================================================

data class ReservationState(

    // Ambiente seleccionado
    val ambiance: String =
        "Patio Virreinal",

    // Día seleccionado
    val dateDay: String =
        "24",

    // Mes / indicador mostrado
    val dateMonth: String =
        "Jue",

    // Indica si la reserva es para hoy
    val isToday: Boolean =
        true,

    // Hora seleccionada
    val timeSlot: String =
        "20:30 PM",

    // Número de personas
    val guests: Int =
        4,

    // Motivo de visita
    val occasions: Set<String> =
        setOf("Cena Casual ✨")
)


// ============================================================
// HISTORIAL DE PEDIDOS
// ============================================================
//
// Este modelo guarda los pedidos realizados desde el carrito.
//
// Se utilizará posteriormente en ProfileScreen para mostrar:
//
// - Platos comprados
// - Fecha
// - Modalidad
// - Método de pago
// - Total pagado
//
// ============================================================

data class OrderHistory(

    // Identificador único
    val id: String,

    // Ejemplo:
    // "Lomo Saltado + Ceviche Clásico"
    val description: String,

    // Ejemplo:
    // "Hoy"
    val date: String,

    // Delivery Express / Para Llevar
    val deliveryMode: String,

    // Tarjeta / Yape / Contra Entrega
    val paymentMethod: String,

    // Total pagado
    val total: Double
)