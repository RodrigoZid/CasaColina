package com.example.model

enum class Screen {
    HOME,
    MENU,
    DISH_DETAIL,
    RESERVATIONS,
    CART,
    PROFILE
}

enum class MenuCategory(val displayName: String) {
    ALL("Todos"),
    ENTRADAS("Entradas"),
    FONDOS("Fondos Criollos"),
    CAVA("De la Cava"),
    POSTRES("Postres")
}

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

data class ReservationState(
    val ambiance: String = "Patio Virreinal",
    val dateDay: String = "24",
    val dateMonth: String = "Jue",
    val isToday: Boolean = true,
    val timeSlot: String = "20:30 PM",
    val guests: Int = 4,
    val occasions: Set<String> = setOf("Cena Casual ✨")
)
