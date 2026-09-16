package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.SampleData
import com.example.model.CartItem
import com.example.model.Dish
import com.example.model.MenuCategory
import com.example.model.ReservationState
import com.example.model.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState(
    val currentScreen: Screen = Screen.HOME,
    val previousScreen: Screen = Screen.HOME,
    val selectedCategory: MenuCategory = MenuCategory.ALL,
    val searchQuery: String = "",
    val selectedDish: Dish = SampleData.DISHES[0],
    val detailDoneness: String = "Término medio (Recomendado)",
    val detailExtras: Map<String, Double> = emptyMap(),
    val detailQuantity: Int = 1,
    val cartItems: List<CartItem> = listOf(
        CartItem(
            id = "cart-1",
            dish = SampleData.DISHES[0], // Lomo Saltado
            doneness = "Término medio",
            extras = emptyList(),
            quantity = 1,
            unitPrice = 68.00
        ),
        CartItem(
            id = "cart-2",
            dish = SampleData.DISHES[2], // Ceviche Clásico
            doneness = "Pesca del día",
            extras = emptyList(),
            quantity = 1,
            unitPrice = 58.00
        )
    ),
    val deliveryMode: String = "Delivery Express",
    val paymentMethod: String = "Tarjeta Débito / Crédito",
    val reservation: ReservationState = ReservationState(),
    val isQrModalOpen: Boolean = false,
    val isDrawerOpen: Boolean = false,
    val favorites: Set<String> = setOf("lomo"),
    val userPoints: Int = 2850,
    val snackbarMessage: String? = null
)

class RestaurantViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun navigateTo(screen: Screen) {
        _uiState.update { current ->
            if (current.currentScreen == screen && screen != Screen.DISH_DETAIL) current
            else current.copy(
                previousScreen = current.currentScreen,
                currentScreen = screen,
                isDrawerOpen = false
            )
        }
    }

    fun navigateBack() {
        _uiState.update { current ->
            current.copy(
                currentScreen = if (current.previousScreen != Screen.DISH_DETAIL) current.previousScreen else Screen.MENU
            )
        }
    }

    fun setCategory(category: MenuCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun openDishDetail(dish: Dish) {
        _uiState.update {
            it.copy(
                previousScreen = it.currentScreen,
                currentScreen = Screen.DISH_DETAIL,
                selectedDish = dish,
                detailDoneness = "Término medio (Recomendado)",
                detailExtras = emptyMap(),
                detailQuantity = 1
            )
        }
    }

    fun setDetailDoneness(doneness: String) {
        _uiState.update { it.copy(detailDoneness = doneness) }
    }

    fun toggleDetailExtra(name: String, price: Double) {
        _uiState.update { current ->
            val updated = current.detailExtras.toMutableMap()
            if (updated.containsKey(name)) {
                updated.remove(name)
            } else {
                updated[name] = price
            }
            current.copy(detailExtras = updated)
        }
    }

    fun updateDetailQuantity(delta: Int) {
        _uiState.update { current ->
            val newQty = (current.detailQuantity + delta).coerceAtLeast(1)
            current.copy(detailQuantity = newQty)
        }
    }

    fun addDetailDishToCart() {
        val current = _uiState.value
        val dish = current.selectedDish
        val extrasSum = current.detailExtras.values.sum()
        val unitPrice = dish.price + extrasSum
        val extraNames = current.detailExtras.keys.toList()

        val newItem = CartItem(
            id = "cart-${System.currentTimeMillis()}",
            dish = dish,
            doneness = current.detailDoneness,
            extras = extraNames,
            quantity = current.detailQuantity,
            unitPrice = unitPrice
        )

        _uiState.update {
            it.copy(
                cartItems = it.cartItems + newItem,
                currentScreen = Screen.CART,
                snackbarMessage = "¡${dish.name} agregado a tu orden!"
            )
        }
    }

    fun quickAddToCart(dish: Dish) {
        _uiState.update { current ->
            val existingIndex = current.cartItems.indexOfFirst { it.dish.id == dish.id && it.extras.isEmpty() }
            val updatedList = if (existingIndex != -1) {
                current.cartItems.mapIndexed { index, item ->
                    if (index == existingIndex) item.copy(quantity = item.quantity + 1)
                    else item
                }
            } else {
                current.cartItems + CartItem(
                    id = "cart-${System.currentTimeMillis()}",
                    dish = dish,
                    doneness = "Receta criolla de la casa",
                    extras = emptyList(),
                    quantity = 1,
                    unitPrice = dish.price
                )
            }
            current.copy(
                cartItems = updatedList,
                snackbarMessage = "¡${dish.name} agregado al carrito!"
            )
        }
    }

    fun updateCartItemQuantity(itemId: String, delta: Int) {
        _uiState.update { current ->
            val updated = current.cartItems.mapNotNull { item ->
                if (item.id == itemId) {
                    val newQty = item.quantity + delta
                    if (newQty > 0) item.copy(quantity = newQty) else null
                } else item
            }
            current.copy(cartItems = updated)
        }
    }

    fun clearCart() {
        _uiState.update {
            it.copy(
                cartItems = emptyList(),
                snackbarMessage = "Carrito vaciado"
            )
        }
    }

    fun setDeliveryMode(mode: String) {
        _uiState.update { it.copy(deliveryMode = mode) }
    }

    fun setPaymentMethod(method: String) {
        _uiState.update { it.copy(paymentMethod = method) }
    }

    fun processCheckout() {
        _uiState.update {
            it.copy(
                cartItems = emptyList(),
                currentScreen = Screen.PROFILE,
                userPoints = it.userPoints + 150,
                snackbarMessage = "¡Pedido confirmado! Tu orden criolla está en preparación."
            )
        }
    }

    // Reservations
    fun selectReservationAmbiance(ambiance: String) {
        _uiState.update { it.copy(reservation = it.reservation.copy(ambiance = ambiance)) }
    }

    fun selectReservationDate(day: String, month: String, isToday: Boolean) {
        _uiState.update {
            it.copy(
                reservation = it.reservation.copy(
                    dateDay = day,
                    dateMonth = month,
                    isToday = isToday
                )
            )
        }
    }

    fun selectReservationTime(time: String) {
        _uiState.update { it.copy(reservation = it.reservation.copy(timeSlot = time)) }
    }

    fun changeReservationGuests(delta: Int) {
        _uiState.update { current ->
            val newCount = (current.reservation.guests + delta).coerceIn(1, 20)
            current.copy(reservation = current.reservation.copy(guests = newCount))
        }
    }

    fun toggleReservationOccasion(occasion: String) {
        _uiState.update { current ->
            val updated = current.reservation.occasions.toMutableSet()
            if (updated.contains(occasion)) updated.remove(occasion)
            else updated.add(occasion)
            current.copy(reservation = current.reservation.copy(occasions = updated))
        }
    }

    fun confirmReservation() {
        val res = _uiState.value.reservation
        _uiState.update {
            it.copy(
                currentScreen = Screen.PROFILE,
                snackbarMessage = "¡Reserva confirmada para ${res.guests} personas en ${res.ambiance}!"
            )
        }
    }

    // Modal & Drawer
    fun setQrModalOpen(open: Boolean) {
        _uiState.update { it.copy(isQrModalOpen = open) }
    }

    fun setDrawerOpen(open: Boolean) {
        _uiState.update { it.copy(isDrawerOpen = open) }
    }

    fun toggleFavorite(dishId: String) {
        _uiState.update { current ->
            val updated = current.favorites.toMutableSet()
            val msg = if (updated.contains(dishId)) {
                updated.remove(dishId)
                "Eliminado de favoritos"
            } else {
                updated.add(dishId)
                "Guardado en tus platos favoritos ❤️"
            }
            current.copy(favorites = updated, snackbarMessage = msg)
        }
    }

    fun redeemPoints() {
        _uiState.update { current ->
            if (current.userPoints >= 500) {
                current.copy(
                    userPoints = current.userPoints - 500,
                    snackbarMessage = "¡500 pts canjeados por Postre de Cortesía en tu próxima visita!"
                )
            } else {
                current.copy(snackbarMessage = "Puntos insuficientes para canjear")
            }
        }
    }

    fun clearSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }
}
