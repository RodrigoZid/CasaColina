package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.data.local.SessionManager
import com.example.data.repository.UsuarioRepository
import com.example.data.repository.CarritoRepository
import com.example.data.repository.PedidoRepository
import com.example.data.repository.ReservaRepository
import com.example.data.remote.dto.ItemPedidoDto
import androidx.lifecycle.viewModelScope
import com.example.data.repository.MenuRepository
import com.example.data.remote.api.PlatoDto
import kotlinx.coroutines.launch
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

    // Datos recibidos de AWS API Gateway / DynamoDB
    val remoteMenu: List<PlatoDto> = emptyList(),
    val isMenuLoading: Boolean = false,
    val menuError: String? = null,

    // Plato seleccionado
    val selectedDish: Dish = SampleData.DISHES.first(),
    val dishes: List<Dish> = emptyList(),
    val userName: String = "",
    val userEmail: String = "",
    val isCheckoutLoading: Boolean = false,
    val checkoutConfirmed: Boolean = false,
    val isReservationLoading: Boolean = false,
    val hasConfirmedReservation: Boolean = false,

    val detailDoneness: String =
        "Término medio (Recomendado)",

    val detailExtras: Map<String, Double> =
        emptyMap(),

    val detailQuantity: Int = 1,


    // ========================================================
    // CARRITO
    // ========================================================

    val cartItems: List<CartItem> = emptyList(),


    // ======================================================================================================
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
        emptySet(),

    val userPoints: Int = 0,

    val snackbarMessage: String? = null
)


// ============================================================
// VIEWMODEL
// ============================================================

class RestaurantViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application.applicationContext)
    private val menuRepository = MenuRepository()
    private val usuarioRepository = UsuarioRepository(sessionManager = sessionManager)
    private val carritoRepository = CarritoRepository(sessionManager = sessionManager)
    private val pedidoRepository = PedidoRepository(sessionManager = sessionManager)
    private val reservaRepository = ReservaRepository(sessionManager = sessionManager)

    private fun PlatoDto.toDish(): Dish = Dish(
        id = platoId,
        name = nombre,
        category = MenuCategory.fromBackend(categoria),
        price = precio,
        prepTime = tiempoPreparacion.toString(),
        shortDescription = descripcionCorta ?: descripcion.orEmpty(),
        fullDescription = descripcionCompleta ?: descripcion.orEmpty(),
        imageUrl = imagenUrl.orEmpty(),
        isChefPick = destacadoChef,
        rating = calificacion ?: 0.0,
        reviewsCount = 0,
        available = disponible
    )

    // Convierte respuestas JSON sin asumir que todos los endpoints
    // devuelven exactamente la misma envoltura.
    private fun Map<String, Any>.text(key: String): String = this[key]?.toString().orEmpty()

    fun cargarPerfilDesdeAws() {
        viewModelScope.launch {
            usuarioRepository.obtenerPerfil()
                .onSuccess { data ->
                    val usuario = (data["usuario"] as? Map<*, *>) ?: data

                    val puntos = usuario["puntos"]
                        ?.toString()
                        ?.toDoubleOrNull()
                        ?.toInt() ?: 0

                    _uiState.update {
                        it.copy(
                            userName = usuario["nombre"]?.toString().orEmpty(),
                            userEmail = (
                                    usuario["correo"] ?: usuario["email"]
                                    )?.toString().orEmpty(),
                            userPoints = puntos
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            snackbarMessage = error.message
                                ?: "No se pudo cargar el perfil"
                        )
                    }
                }
        }
    }
    fun cargarPedidosDesdeAws() {
        viewModelScope.launch {
            pedidoRepository.obtenerPedidos().onSuccess { body ->
                val raw = (body["pedidos"] ?: body["items"]) as? List<*> ?: emptyList<Any>()
                val orders = raw.mapNotNull { entry ->
                    val obj = entry as? Map<*, *> ?: return@mapNotNull null
                    val id = obj["pedidoId"]?.toString().orEmpty()
                    if (id.isBlank()) return@mapNotNull null
                    val productos = (obj["items"] as? List<*>)?.joinToString(" + ") { item ->
                        val m = item as? Map<*, *>
                        (m?.get("nombre") ?: m?.get("platoId"))?.toString().orEmpty()
                    }.orEmpty()
                    OrderHistory(
                        id = id,
                        description = productos,
                        date = obj["fechaCreacion"]?.toString().orEmpty(),
                        deliveryMode = obj["modalidadEntrega"]?.toString().orEmpty(),
                        paymentMethod = obj["metodoPago"]?.toString().orEmpty(),
                        total = (obj["total"] as? Number)?.toDouble() ?: 0.0,
                        status = obj["estado"]?.toString().orEmpty(),
                        paymentStatus = obj["estadoPago"]?.toString().orEmpty(),
                        deliveryAddress = obj["direccionEntrega"]?.toString().orEmpty(),
                        userId = obj["usuarioId"]?.toString().orEmpty(),
                        createdAt = obj["fechaCreacion"]?.toString().orEmpty()
                    )
                }
                val puntosPedidos = raw.sumOf { entry ->
                    val obj = entry as? Map<*, *>
                    (obj?.get("puntosGanados") as? Number)?.toInt() ?: 0
                }
                _uiState.update { it.copy(
                    orderHistory = orders,
                    userPoints = maxOf(it.userPoints, puntosPedidos)
                ) }
            }.onFailure { error ->
                _uiState.update { it.copy(snackbarMessage = error.message ?: "No se pudieron cargar los pedidos") }
            }
        }
    }

    fun cargarCarritoDesdeAws() {
        viewModelScope.launch {
            carritoRepository.obtenerCarrito().onSuccess { body ->
                val raw = (body["items"] ?: body["carrito"]) as? List<*> ?: emptyList<Any>()
                val dishes = _uiState.value.dishes
                val items = raw.mapNotNull { entry ->
                    val obj = entry as? Map<*, *> ?: return@mapNotNull null
                    val platoId = obj["platoId"]?.toString().orEmpty()
                    val dish = dishes.firstOrNull { it.id == platoId } ?: return@mapNotNull null
                    val qty = (obj["cantidad"] as? Number)?.toInt() ?: 1
                    CartItem(
                        id = (obj["itemId"] ?: obj["carritoId"] ?: platoId).toString(),
                        dish = dish,
                        quantity = qty,
                        unitPrice = (obj["precioUnitario"] as? Number)?.toDouble() ?: dish.price,
                        createdAt = obj["fechaCreacion"]?.toString().orEmpty()
                    )
                }
                _uiState.update { it.copy(cartItems = items) }
            }.onFailure { error ->
                _uiState.update { it.copy(snackbarMessage = error.message ?: "No se pudo cargar el carrito") }
            }
        }
    }


    // Consulta GET /menu. No sustituye los Dish de la UI hasta
    // verificar el modelo Dish y la estructura de MenuScreen.
    fun cargarMenuDesdeAws(categoria: String? = null) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isMenuLoading = true, menuError = null)
            }
            val resultado = menuRepository.obtenerMenu(categoria)
            resultado.fold(
                onSuccess = { platos ->
                    _uiState.update {
                        it.copy(
                            remoteMenu = platos,
                            dishes = platos.filter { it.disponible }.map { it.toDish() },
                            isMenuLoading = false,
                            menuError = null
                        )
                    }
                    if (sessionManager.tieneUsuario()) cargarCarritoDesdeAws()
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isMenuLoading = false,
                            menuError = error.message ?: "Error al consultar el menú"
                        )
                    }
                }
            )
        }
    }



    private val _uiState =
        MutableStateFlow(UiState())

    init {
        cargarMenuDesdeAws()
    }


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
        val state = _uiState.value
        agregarProductoRemoto(state.selectedDish, state.detailQuantity)
    }

    fun quickAddToCart(dish: Dish) {
        agregarProductoRemoto(dish, 1)
    }

    private fun agregarProductoRemoto(dish: Dish, cantidad: Int) {
        if (dish.id.isBlank() || !dish.available) {
            _uiState.update { it.copy(snackbarMessage = "Plato no disponible") }
            return
        }
        viewModelScope.launch {
            carritoRepository.agregarProducto(dish.id, cantidad)
                .onSuccess {
                    _uiState.update { it.copy(currentScreen = Screen.CART, snackbarMessage = "¡Producto agregado al carrito!") }
                    cargarCarritoDesdeAws()
                }.onFailure { error ->
                    _uiState.update { it.copy(snackbarMessage = "No se agregó al carrito: ${error.message}") }
                }
        }
    }

    fun updateCartItemQuantity(itemId: String, delta: Int) {
        val item = _uiState.value.cartItems.firstOrNull { it.id == itemId } ?: return
        val nuevaCantidad = item.quantity + delta
        viewModelScope.launch {
            val result = if (nuevaCantidad <= 0) carritoRepository.eliminarProducto(itemId)
            else carritoRepository.actualizarCantidad(itemId, nuevaCantidad)
            result.onSuccess { cargarCarritoDesdeAws() }
                .onFailure { error -> _uiState.update { it.copy(snackbarMessage = "No se actualizó el carrito: ${error.message}") } }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            carritoRepository.vaciarCarrito().onSuccess {
                _uiState.update { it.copy(cartItems = emptyList(), snackbarMessage = "Carrito vaciado correctamente") }
            }.onFailure { error ->
                _uiState.update { it.copy(snackbarMessage = "No se pudo vaciar el carrito: ${error.message}") }
            }
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

    fun consumeCheckoutConfirmation() {
        _uiState.update { it.copy(checkoutConfirmed = false) }
    }

    fun processCheckout() {
        val current = _uiState.value
        if (current.isCheckoutLoading) return
        if (current.cartItems.isEmpty()) {
            _uiState.update { it.copy(snackbarMessage = "El carrito está vacío") }
            return
        }
        _uiState.update { it.copy(isCheckoutLoading = true, checkoutConfirmed = false, snackbarMessage = null) }
        viewModelScope.launch {
            val items = current.cartItems.map {
                ItemPedidoDto(platoId = it.dish.id, cantidad = it.quantity, precioUnitario = it.unitPrice)
            }
            // La interfaz conserva sus etiquetas; AWS recibe los valores permitidos.
            val modalidadAws = when (current.deliveryMode) {
                "Delivery Express", "Delivery" -> "Delivery"
                "Para Llevar", "Recojo en local" -> "Recojo en local"
                else -> {
                    _uiState.update { it.copy(isCheckoutLoading = false, snackbarMessage = "Modalidad de entrega no válida") }
                    return@launch
                }
            }
            val metodoPagoAws = when (current.paymentMethod) {
                "Tarjeta Débito / Crédito", "Tarjeta" -> "Tarjeta"
                "Billeteras Digitales (Yape / Plin)", "Yape" -> "Yape"
                "Plin" -> "Plin"
                "Pago Contra Entrega (POS móvil)", "Efectivo" -> "Efectivo"
                else -> {
                    _uiState.update { it.copy(isCheckoutLoading = false, snackbarMessage = "Método de pago no válido") }
                    return@launch
                }
            }
            pedidoRepository.crearPedido(items, modalidadAws, metodoPagoAws)
                .onSuccess { body ->
                    val pedido = body["pedido"] as? Map<*, *> ?: body
                    val id = (pedido["pedidoId"] ?: body["pedidoId"])?.toString().orEmpty()
                    if (id.isBlank()) {
                        _uiState.update { it.copy(isCheckoutLoading = false, snackbarMessage = "No se pudo confirmar el pedido. Verifica tu historial antes de reintentar") }
                    } else {
                        val description = current.cartItems.joinToString(" + ") { "${it.dish.name} x${it.quantity}" }
                        val order = OrderHistory(
                            id = id, description = description,
                            date = pedido["fechaCreacion"]?.toString().orEmpty(),
                            deliveryMode = current.deliveryMode,
                            paymentMethod = current.paymentMethod,
                            total = (pedido["total"] as? Number)?.toDouble() ?: current.cartItems.sumOf { it.totalPrice },
                            status = pedido["estado"]?.toString().orEmpty(),
                            paymentStatus = pedido["estadoPago"]?.toString().orEmpty()
                        )
                        _uiState.update { it.copy(
                            orderHistory = listOf(order) + it.orderHistory,
                            cartItems = emptyList(), isCheckoutLoading = false, checkoutConfirmed = true,
                            snackbarMessage = "¡Pedido registrado correctamente!"
                        ) }
                        carritoRepository.vaciarCarrito().onFailure { error ->
                            _uiState.update { it.copy(snackbarMessage = "Pedido registrado; no se pudo vaciar el carrito remoto: ${error.message}") }
                        }
                    }
                }.onFailure { error ->
                    _uiState.update { it.copy(isCheckoutLoading = false, snackbarMessage = "No se registró el pedido: ${error.message}") }
                }
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


    fun selectReservationFullDate(dateIso: String) {
        _uiState.update { it.copy(reservation = it.reservation.copy(dateIso = dateIso)) }
    }

    fun selectReservationTime(
        time: String
    ) {

        _uiState.update {

            it.copy(

                reservation =
                    it.reservation.copy(

                        timeSlot =
                            time,
                        time24h = if (Regex("^([01]\\d|2[0-3]):[0-5]\\d$").matches(time)) time else it.reservation.time24h
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
                        12
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
        val res = _uiState.value.reservation
        if (_uiState.value.isReservationLoading) return
        if (res.dateIso.isBlank() || res.time24h.isBlank()) {
            _uiState.update { it.copy(snackbarMessage = "Selecciona una fecha y hora completas antes de reservar") }
            return
        }
        _uiState.update { it.copy(isReservationLoading = true, snackbarMessage = null) }
        viewModelScope.launch {
            reservaRepository.consultarDisponibilidad(res.dateIso, res.time24h, res.guests)
                .onSuccess { availability ->
                    // No inferir disponibilidad de una respuesta sin el campo explícito.
                    if (availability["disponibleReferencial"] != true) {
                        _uiState.update { it.copy(isReservationLoading = false, snackbarMessage = "No se pudo confirmar la disponibilidad") }
                        return@onSuccess
                    }
                    reservaRepository.crearReserva(
                        fecha = res.dateIso, hora = res.time24h,
                        personas = res.guests, ambiente = res.ambiance,
                        observaciones = res.observations.ifBlank { res.occasions.joinToString(", ") }
                    ).onSuccess { body ->
                        val reserva = body["reserva"] as? Map<*, *> ?: body
                        val id = (reserva["reservaId"] ?: body["reservaId"])?.toString().orEmpty()
                        if (id.isBlank()) {
                            _uiState.update { it.copy(isReservationLoading = false, snackbarMessage = "No se pudo confirmar la reserva. Verifica tu historial") }
                        } else {
                            _uiState.update { it.copy(
                                isReservationLoading = false,
                                hasConfirmedReservation = true, // AWS devolvió un ID: reserva registrada; el estado real se conserva en reservation.status
                                reservation = it.reservation.copy(reservationId = id, status = reserva["estado"]?.toString()?.takeIf { estado -> estado.isNotBlank() } ?: "Pendiente de confirmación"),
                                snackbarMessage = null // La confirmación visual se muestra en ReservationScreen
                            ) }
                        }
                    }.onFailure { error ->
                        _uiState.update { it.copy(isReservationLoading = false, snackbarMessage = "No se registró la reserva: ${error.message}") }
                    }
                }.onFailure { error ->
                    _uiState.update { it.copy(isReservationLoading = false, snackbarMessage = "No se pudo consultar disponibilidad: ${error.message}") }
                }
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
        _uiState.update { it.copy(snackbarMessage = "El canje de puntos aún no está disponible") }
    }


    // ========================================================
    // CERRAR SESIÓN
    // ========================================================

    fun logout() {
        usuarioRepository.cerrarSesion()
        _uiState.update {
            UiState(currentScreen = Screen.LOGIN, snackbarMessage = null)
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