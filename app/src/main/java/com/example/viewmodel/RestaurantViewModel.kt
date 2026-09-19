package com.example.viewmodel

import androidx.lifecycle.ViewModel
import com.example.data.SampleData
import com.example.model.CartItem
import com.example.model.Dish
import com.example.model.MenuCategory
import com.example.model.OrderHistory
import com.example.model.ReservationState
import com.example.model.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


// ============================================================
// ESTADO GENERAL DE LA APLICACIÓN
// ============================================================

data class UiState(

    // Navegación
    val currentScreen: Screen = Screen.LOGIN,
    val previousScreen: Screen = Screen.HOME,

    // Menú
    val selectedCategory: MenuCategory = MenuCategory.ALL,
    val searchQuery: String = "",

    // Plato seleccionado
    val selectedDish: Dish = SampleData.DISHES[0],

    val detailDoneness: String =
        "Término medio (Recomendado)",

    val detailExtras: Map<String, Double> =
        emptyMap(),

    val detailQuantity: Int = 1,


    // ========================================================
    // CARRITO
    // ========================================================

    val cartItems: List<CartItem> = listOf(

        CartItem(
            id = "cart-1",
            dish = SampleData.DISHES[0],
            doneness = "Término medio",
            extras = emptyList(),
            quantity = 1,
            unitPrice = 68.00
        ),

        CartItem(
            id = "cart-2",
            dish = SampleData.DISHES[2],
            doneness = "Pesca del día",
            extras = emptyList(),
            quantity = 1,
            unitPrice = 58.00
        )
    ),


    // ========================================================
    // CONFIGURACIÓN DEL PEDIDO
    // ========================================================

    val deliveryMode: String =
        "Delivery Express",

    val paymentMethod: String =
        "Tarjeta Débito / Crédito",


    // ========================================================
    // HISTORIAL DE PEDIDOS
    // ========================================================

    val orderHistory: List<OrderHistory> =
        emptyList(),


    // ========================================================
    // RESERVACIÓN
    // ========================================================

    val reservation: ReservationState =
        ReservationState(),


    // ========================================================
    // ESTADO DE INTERFAZ
    // ========================================================

    val isQrModalOpen: Boolean = false,

    val isDrawerOpen: Boolean = false,

    val favorites: Set<String> =
        setOf("lomo"),

    val userPoints: Int = 2850,

    val snackbarMessage: String? = null
)


// ============================================================
// VIEWMODEL
// ============================================================

class RestaurantViewModel : ViewModel() {


    private val _uiState =
        MutableStateFlow(UiState())


    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()


    // ========================================================
    // NAVEGACIÓN
    // ========================================================

    fun navigateTo(screen: Screen) {

        _uiState.update { current ->

            if (
                current.currentScreen == screen &&
                screen != Screen.DISH_DETAIL
            ) {

                current

            } else {

                current.copy(
                    previousScreen =
                        current.currentScreen,

                    currentScreen =
                        screen,

                    isDrawerOpen =
                        false
                )
            }
        }
    }


    fun navigateBack() {

        _uiState.update { current ->

            current.copy(

                currentScreen =

                    if (
                        current.previousScreen !=
                        Screen.DISH_DETAIL
                    ) {

                        current.previousScreen

                    } else {

                        Screen.MENU
                    }
            )
        }
    }


    // ========================================================
    // MENÚ
    // ========================================================

    fun setCategory(
        category: MenuCategory
    ) {

        _uiState.update {

            it.copy(
                selectedCategory =
                    category
            )
        }
    }


    fun setSearchQuery(
        query: String
    ) {

        _uiState.update {

            it.copy(
                searchQuery =
                    query
            )
        }
    }


    // ========================================================
    // DETALLE DEL PLATO
    // ========================================================

    fun openDishDetail(
        dish: Dish
    ) {

        _uiState.update {

            it.copy(

                previousScreen =
                    it.currentScreen,

                currentScreen =
                    Screen.DISH_DETAIL,

                selectedDish =
                    dish,

                detailDoneness =
                    "Término medio (Recomendado)",

                detailExtras =
                    emptyMap(),

                detailQuantity =
                    1
            )
        }
    }


    fun setDetailDoneness(
        doneness: String
    ) {

        _uiState.update {

            it.copy(
                detailDoneness =
                    doneness
            )
        }
    }


    fun toggleDetailExtra(
        name: String,
        price: Double
    ) {

        _uiState.update { current ->

            val updated =
                current
                    .detailExtras
                    .toMutableMap()


            if (
                updated.containsKey(name)
            ) {

                updated.remove(name)

            } else {

                updated[name] =
                    price
            }


            current.copy(
                detailExtras =
                    updated
            )
        }
    }


    fun updateDetailQuantity(
        delta: Int
    ) {

        _uiState.update { current ->

            val newQty =
                (
                        current.detailQuantity +
                                delta
                        ).coerceAtLeast(1)


            current.copy(
                detailQuantity =
                    newQty
            )
        }
    }


    // ========================================================
    // AGREGAR PLATO AL CARRITO
    // ========================================================

    fun addDetailDishToCart() {

        val current =
            _uiState.value


        val dish =
            current.selectedDish


        val extrasSum =
            current
                .detailExtras
                .values
                .sum()


        val unitPrice =
            dish.price +
                    extrasSum


        val extraNames =
            current
                .detailExtras
                .keys
                .toList()


        val newItem =
            CartItem(

                id =
                    "cart-${System.currentTimeMillis()}",

                dish =
                    dish,

                doneness =
                    current.detailDoneness,

                extras =
                    extraNames,

                quantity =
                    current.detailQuantity,

                unitPrice =
                    unitPrice
            )


        _uiState.update {

            it.copy(

                cartItems =
                    it.cartItems +
                            newItem,

                currentScreen =
                    Screen.CART,

                snackbarMessage =
                    "¡${dish.name} agregado a tu orden!"
            )
        }
    }


    // ========================================================
    // AGREGAR RÁPIDAMENTE AL CARRITO
    // ========================================================

    fun quickAddToCart(
        dish: Dish
    ) {

        _uiState.update { current ->

            val existingIndex =
                current.cartItems.indexOfFirst {

                    it.dish.id ==
                            dish.id &&

                            it.extras.isEmpty()
                }


            val updatedList =

                if (
                    existingIndex != -1
                ) {

                    current.cartItems.mapIndexed {
                            index,
                            item ->

                        if (
                            index ==
                            existingIndex
                        ) {

                            item.copy(
                                quantity =
                                    item.quantity + 1
                            )

                        } else {

                            item
                        }
                    }

                } else {

                    current.cartItems +

                            CartItem(

                                id =
                                    "cart-${System.currentTimeMillis()}",

                                dish =
                                    dish,

                                doneness =
                                    "Receta criolla de la casa",

                                extras =
                                    emptyList(),

                                quantity =
                                    1,

                                unitPrice =
                                    dish.price
                            )
                }


            current.copy(

                cartItems =
                    updatedList,

                snackbarMessage =
                    "¡${dish.name} agregado al carrito!"
            )
        }
    }


    // ========================================================
    // MODIFICAR CANTIDAD DEL CARRITO
    // ========================================================

    fun updateCartItemQuantity(
        itemId: String,
        delta: Int
    ) {

        _uiState.update { current ->

            val updated =
                current.cartItems.mapNotNull {
                        item ->


                    if (
                        item.id ==
                        itemId
                    ) {

                        val newQty =
                            item.quantity +
                                    delta


                        if (
                            newQty > 0
                        ) {

                            item.copy(
                                quantity =
                                    newQty
                            )

                        } else {

                            null
                        }

                    } else {

                        item
                    }
                }


            current.copy(
                cartItems =
                    updated
            )
        }
    }


    // ========================================================
    // VACIAR CARRITO
    // ========================================================

    fun clearCart() {

        _uiState.update {

            it.copy(

                cartItems =
                    emptyList(),

                snackbarMessage =
                    "Carrito vaciado"
            )
        }
    }


    // ========================================================
    // MODALIDAD DEL PEDIDO
    // ========================================================

    fun setDeliveryMode(
        mode: String
    ) {

        _uiState.update {

            it.copy(
                deliveryMode =
                    mode
            )
        }
    }


    // ========================================================
    // MÉTODO DE PAGO
    // ========================================================

    fun setPaymentMethod(
        method: String
    ) {

        _uiState.update {

            it.copy(
                paymentMethod =
                    method
            )
        }
    }


    // ========================================================
    // PROCESAR PAGO
    // ========================================================

    fun processCheckout() {

        val current =
            _uiState.value


        // ----------------------------------------------------
        // VALIDAR CARRITO
        // ----------------------------------------------------

        if (
            current.cartItems.isEmpty()
        ) {

            _uiState.update {

                it.copy(
                    snackbarMessage =
                        "El carrito está vacío"
                )
            }

            return
        }


        // ----------------------------------------------------
        // CONSTRUIR DESCRIPCIÓN DEL PEDIDO
        // ----------------------------------------------------

        val description =
            current.cartItems.joinToString(
                separator = " + "
            ) { item ->

                if (
                    item.quantity > 1
                ) {

                    "${item.dish.name} x${item.quantity}"

                } else {

                    item.dish.name
                }
            }


        // ----------------------------------------------------
        // CALCULAR TOTAL
        // ----------------------------------------------------

        val total =
            current.cartItems.sumOf {

                it.totalPrice
            }


        // ----------------------------------------------------
        // CREAR PEDIDO PARA HISTORIAL
        // ----------------------------------------------------

        val newOrder =
            OrderHistory(

                id =
                    "order-${System.currentTimeMillis()}",

                description =
                    description,

                date =
                    "Hoy",

                deliveryMode =
                    current.deliveryMode,

                paymentMethod =
                    current.paymentMethod,

                total =
                    total
            )


        // ----------------------------------------------------
        // GUARDAR PEDIDO
        // ----------------------------------------------------

        _uiState.update {

            it.copy(

                // Guardar el pedido nuevo primero
                orderHistory =
                    listOf(newOrder) +
                            it.orderHistory,

                // Vaciar carrito después de guardar
                cartItems =
                    emptyList(),

                // Sumar puntos
                userPoints =
                    it.userPoints + 150,

                // No cambiamos todavía a PROFILE.
                // MainActivity puede hacerlo cuando el
                // usuario presione OK en la ventana de pago.
                snackbarMessage =
                    "¡Pago completado! Pedido registrado correctamente."
            )
        }
    }


    // ========================================================
    // RESERVACIONES
    // ========================================================

    fun selectReservationAmbiance(
        ambiance: String
    ) {

        _uiState.update {

            it.copy(

                reservation =
                    it.reservation.copy(

                        ambiance =
                            ambiance
                    )
            )
        }
    }


    fun selectReservationDate(
        day: String,
        month: String,
        isToday: Boolean
    ) {

        _uiState.update {

            it.copy(

                reservation =
                    it.reservation.copy(

                        dateDay =
                            day,

                        dateMonth =
                            month,

                        isToday =
                            isToday
                    )
            )
        }
    }


    fun selectReservationTime(
        time: String
    ) {

        _uiState.update {

            it.copy(

                reservation =
                    it.reservation.copy(

                        timeSlot =
                            time
                    )
            )
        }
    }


    fun changeReservationGuests(
        delta: Int
    ) {

        _uiState.update { current ->

            val newCount =
                (
                        current
                            .reservation
                            .guests +
                                delta
                        ).coerceIn(
                        1,
                        20
                    )


            current.copy(

                reservation =
                    current
                        .reservation
                        .copy(

                            guests =
                                newCount
                        )
            )
        }
    }


    fun toggleReservationOccasion(
        occasion: String
    ) {

        _uiState.update { current ->

            val updated =
                current
                    .reservation
                    .occasions
                    .toMutableSet()


            if (
                updated.contains(
                    occasion
                )
            ) {

                updated.remove(
                    occasion
                )

            } else {

                updated.add(
                    occasion
                )
            }


            current.copy(

                reservation =
                    current
                        .reservation
                        .copy(

                            occasions =
                                updated
                        )
            )
        }
    }


    // ========================================================
    // CONFIRMAR RESERVA
    // ========================================================

    fun confirmReservation() {

        val res =
            _uiState
                .value
                .reservation


        _uiState.update {

            it.copy(

                // La navegación a PROFILE puede realizarse
                // cuando el usuario cierre el diálogo.

                snackbarMessage =
                    "¡Reserva confirmada para " +
                            "${res.guests} personas en " +
                            "${res.ambiance}!"
            )
        }
    }


    // ========================================================
    // QR
    // ========================================================

    fun setQrModalOpen(
        open: Boolean
    ) {

        _uiState.update {

            it.copy(
                isQrModalOpen =
                    open
            )
        }
    }


    // ========================================================
    // DRAWER
    // ========================================================

    fun setDrawerOpen(
        open: Boolean
    ) {

        _uiState.update {

            it.copy(
                isDrawerOpen =
                    open
            )
        }
    }


    // ========================================================
    // FAVORITOS
    // ========================================================

    fun toggleFavorite(
        dishId: String
    ) {

        _uiState.update { current ->

            val updated =
                current
                    .favorites
                    .toMutableSet()


            val msg =

                if (
                    updated.contains(
                        dishId
                    )
                ) {

                    updated.remove(
                        dishId
                    )

                    "Eliminado de favoritos"

                } else {

                    updated.add(
                        dishId
                    )

                    "Guardado en tus platos favoritos ❤️"
                }


            current.copy(

                favorites =
                    updated,

                snackbarMessage =
                    msg
            )
        }
    }


    // ========================================================
    // CANJEAR PUNTOS
    // ========================================================

    fun redeemPoints() {

        _uiState.update { current ->

            if (
                current.userPoints >= 500
            ) {

                current.copy(

                    userPoints =
                        current.userPoints -
                                500,

                    snackbarMessage =
                        "¡500 pts canjeados por Postre de Cortesía en tu próxima visita!"
                )

            } else {

                current.copy(

                    snackbarMessage =
                        "Puntos insuficientes para canjear"
                )
            }
        }
    }


    // ========================================================
    // CERRAR SESIÓN
    // ========================================================

    fun logout() {

        _uiState.update { current ->

            current.copy(

                currentScreen =
                    Screen.LOGIN,

                previousScreen =
                    Screen.HOME,

                isDrawerOpen =
                    false,

                isQrModalOpen =
                    false,

                snackbarMessage =
                    null
            )
        }
    }


    // ========================================================
    // LIMPIAR SNACKBAR
    // ========================================================

    fun clearSnackbar() {

        _uiState.update {

            it.copy(
                snackbarMessage =
                    null
            )
        }
    }
}