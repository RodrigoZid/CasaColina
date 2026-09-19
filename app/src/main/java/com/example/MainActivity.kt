package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

import com.example.model.Screen
import com.example.ui.components.CasaColinaBottomBar
import com.example.ui.components.CasaColinaSideDrawer
import com.example.ui.components.CasaColinaTopAppBar
import com.example.ui.components.QrPassModal
import com.example.ui.screens.CartScreen
import com.example.ui.screens.DishDetailScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.MenuScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ReservationScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.RestaurantViewModel


class MainActivity : ComponentActivity() {

    private val viewModel: RestaurantViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            MyApplicationTheme {

                val uiState by
                viewModel.uiState.collectAsStateWithLifecycle()

                val snackbarHostState =
                    remember {
                        SnackbarHostState()
                    }


                // =====================================================
                // SNACKBAR
                // =====================================================

                LaunchedEffect(
                    uiState.snackbarMessage
                ) {

                    uiState.snackbarMessage?.let { message ->

                        snackbarHostState.showSnackbar(
                            message
                        )

                        viewModel.clearSnackbar()
                    }
                }


                // =====================================================
                // BOTÓN ATRÁS
                // =====================================================

                BackHandler(

                    enabled =
                        uiState.currentScreen != Screen.HOME &&
                                uiState.currentScreen != Screen.LOGIN

                ) {

                    viewModel.navigateBack()
                }


                // =====================================================
                // CANTIDAD TOTAL DEL CARRITO
                // =====================================================

                val totalCartCount =
                    uiState.cartItems.sumOf {
                        it.quantity
                    }


                // =====================================================
                // SCAFFOLD PRINCIPAL
                // =====================================================

                Scaffold(

                    // =================================================
                    // TOP BAR
                    // =================================================

                    topBar = {

                        if (
                            uiState.currentScreen !=
                            Screen.DISH_DETAIL &&
                            uiState.currentScreen !=
                            Screen.LOGIN
                        ) {

                            CasaColinaTopAppBar(

                                cartCount =
                                    totalCartCount,

                                onMenuClick = {

                                    viewModel
                                        .setDrawerOpen(
                                            true
                                        )
                                },

                                onLogoClick = {

                                    viewModel.navigateTo(
                                        Screen.HOME
                                    )
                                },

                                onCartClick = {

                                    viewModel.navigateTo(
                                        Screen.CART
                                    )
                                },

                                modifier =
                                    Modifier
                                        .statusBarsPadding()
                            )
                        }
                    },


                    // =================================================
                    // BOTTOM BAR
                    // =================================================

                    bottomBar = {

                        if (
                            uiState.currentScreen !=
                            Screen.DISH_DETAIL &&
                            uiState.currentScreen !=
                            Screen.LOGIN
                        ) {

                            CasaColinaBottomBar(

                                currentScreen =
                                    uiState.currentScreen,

                                cartCount =
                                    totalCartCount,

                                onScreenSelected = { screen ->

                                    viewModel.navigateTo(
                                        screen
                                    )
                                },

                                modifier =
                                    Modifier
                                        .navigationBarsPadding()
                            )
                        }
                    },


                    // =================================================
                    // SNACKBAR
                    // =================================================

                    snackbarHost = {

                        SnackbarHost(

                            hostState =
                                snackbarHostState,

                            modifier =
                                Modifier
                                    .navigationBarsPadding()
                        )
                    },


                    modifier =
                        Modifier.fillMaxSize()

                ) { innerPadding ->


                    // =================================================
                    // CONTENIDO
                    // =================================================

                    Box(

                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(

                                    top =
                                        innerPadding
                                            .calculateTopPadding(),

                                    bottom =
                                        innerPadding
                                            .calculateBottomPadding()
                                )
                    ) {


                        // =============================================
                        // NAVEGACIÓN
                        // =============================================

                        AnimatedContent(

                            targetState =
                                uiState.currentScreen,

                            transitionSpec = {

                                fadeIn() togetherWith
                                        fadeOut()
                            },

                            label =
                                "ScreenTransition"

                        ) { targetScreen ->


                            when (targetScreen) {


                                // =====================================
                                // LOGIN
                                // =====================================

                                Screen.LOGIN ->

                                    LoginScreen(

                                        onLoginSuccess = {

                                            viewModel
                                                .navigateTo(
                                                    Screen.HOME
                                                )
                                        }
                                    )


                                // =====================================
                                // HOME
                                // =====================================

                                Screen.HOME ->

                                    HomeScreen(

                                        onNavigate = { screen ->

                                            viewModel.navigateTo(
                                                screen
                                            )
                                        },


                                        onFilterCategory = { category ->

                                            viewModel.setCategory(
                                                category
                                            )

                                            viewModel.navigateTo(
                                                Screen.MENU
                                            )
                                        },


                                        onSelectDish = { dish ->

                                            viewModel
                                                .openDishDetail(
                                                    dish
                                                )
                                        },


                                        onQuickAdd = { dish ->

                                            viewModel
                                                .quickAddToCart(
                                                    dish
                                                )
                                        },


                                        onOpenQr = {

                                            viewModel
                                                .setQrModalOpen(
                                                    true
                                                )
                                        }
                                    )


                                // =====================================
                                // MENÚ
                                // =====================================

                                Screen.MENU ->

                                    MenuScreen(

                                        searchQuery =
                                            uiState.searchQuery,


                                        onSearchChange = { query ->

                                            viewModel
                                                .setSearchQuery(
                                                    query
                                                )
                                        },


                                        selectedCategory =
                                            uiState.selectedCategory,


                                        onSelectCategory = { category ->

                                            viewModel
                                                .setCategory(
                                                    category
                                                )
                                        },


                                        onSelectDish = { dish ->

                                            viewModel
                                                .openDishDetail(
                                                    dish
                                                )
                                        },


                                        onQuickAdd = { dish ->

                                            viewModel
                                                .quickAddToCart(
                                                    dish
                                                )
                                        }
                                    )


                                // =====================================
                                // DETALLE DEL PLATO
                                // =====================================

                                Screen.DISH_DETAIL ->

                                    DishDetailScreen(

                                        dish =
                                            uiState.selectedDish,


                                        selectedDoneness =
                                            uiState.detailDoneness,


                                        onSelectDoneness = { doneness ->

                                            viewModel
                                                .setDetailDoneness(
                                                    doneness
                                                )
                                        },


                                        selectedExtras =
                                            uiState.detailExtras,


                                        onToggleExtra = { name, price ->

                                            viewModel
                                                .toggleDetailExtra(
                                                    name,
                                                    price
                                                )
                                        },


                                        quantity =
                                            uiState.detailQuantity,


                                        onUpdateQuantity = { delta ->

                                            viewModel
                                                .updateDetailQuantity(
                                                    delta
                                                )
                                        },


                                        isFavorite =
                                            uiState.favorites.contains(
                                                uiState
                                                    .selectedDish
                                                    .id
                                            ),


                                        onToggleFavorite = {

                                            viewModel
                                                .toggleFavorite(
                                                    uiState
                                                        .selectedDish
                                                        .id
                                                )
                                        },


                                        onBack = {

                                            viewModel
                                                .navigateBack()
                                        },


                                        onAddToCart = {

                                            viewModel
                                                .addDetailDishToCart()
                                        }
                                    )


                                // =====================================
                                // RESERVACIONES
                                // =====================================

                                Screen.RESERVATIONS ->

                                    ReservationScreen(

                                        reservation =
                                            uiState.reservation,


                                        onSelectAmbiance = { ambiance ->

                                            viewModel
                                                .selectReservationAmbiance(
                                                    ambiance
                                                )
                                        },


                                        onSelectDate = {
                                                day,
                                                month,
                                                today ->

                                            viewModel
                                                .selectReservationDate(
                                                    day,
                                                    month,
                                                    today
                                                )
                                        },


                                        onSelectTime = { time ->

                                            viewModel
                                                .selectReservationTime(
                                                    time
                                                )
                                        },


                                        onChangeGuests = { delta ->

                                            viewModel
                                                .changeReservationGuests(
                                                    delta
                                                )
                                        },


                                        onToggleOccasion = { occasion ->

                                            viewModel
                                                .toggleReservationOccasion(
                                                    occasion
                                                )
                                        },


                                        // =============================
                                        // CONFIRMAR RESERVA
                                        // =============================

                                        onConfirm = {

                                            viewModel
                                                .confirmReservation()
                                        },


                                        // =============================
                                        // DESPUÉS DE ACEPTAR
                                        // =============================

                                        onReservationCompleted = {

                                            viewModel.navigateTo(
                                                Screen.PROFILE
                                            )
                                        }
                                    )


                                // =====================================
                                // CARRITO
                                // =====================================

                                Screen.CART ->

                                    CartScreen(

                                        cartItems =
                                            uiState.cartItems,


                                        onUpdateQuantity = {
                                                id,
                                                delta ->

                                            viewModel
                                                .updateCartItemQuantity(
                                                    id,
                                                    delta
                                                )
                                        },


                                        onClearCart = {

                                            viewModel
                                                .clearCart()
                                        },


                                        deliveryMode =
                                            uiState.deliveryMode,


                                        onSelectDeliveryMode = { mode ->

                                            viewModel
                                                .setDeliveryMode(
                                                    mode
                                                )
                                        },


                                        paymentMethod =
                                            uiState.paymentMethod,


                                        onSelectPaymentMethod = { method ->

                                            viewModel
                                                .setPaymentMethod(
                                                    method
                                                )
                                        },


                                        // =============================
                                        // PROCESAR PAGO
                                        // =============================

                                        onCheckout = {

                                            /*
                                             * AQUÍ processCheckout()
                                             * debe guardar el pedido
                                             * dentro de orderHistory.
                                             */
                                            viewModel
                                                .processCheckout()
                                        },


                                        // =============================
                                        // VOLVER A MENÚ
                                        // =============================

                                        onNavigateMenu = {

                                            viewModel.navigateTo(
                                                Screen.MENU
                                            )
                                        },


                                        // =============================
                                        // PAGO COMPLETADO
                                        // =============================

                                        onPaymentCompleted = {

                                            /*
                                             * Cuando el usuario
                                             * presiona OK en
                                             * "Pago completado",
                                             * va al perfil.
                                             */
                                            viewModel.navigateTo(
                                                Screen.PROFILE
                                            )
                                        }
                                    )


                                // =====================================
                                // PERFIL
                                // =====================================

                                Screen.PROFILE ->

                                    ProfileScreen(

                                        // =============================
                                        // PUNTOS
                                        // =============================

                                        userPoints =
                                            uiState.userPoints,


                                        // =============================
                                        // RESERVA ACTUAL
                                        // =============================

                                        reservation =
                                            uiState.reservation,


                                        // =============================
                                        // NUEVO:
                                        // HISTORIAL DE PEDIDOS
                                        // =============================

                                        orderHistory =
                                            uiState.orderHistory,


                                        // =============================
                                        // CANJEAR PUNTOS
                                        // =============================

                                        onRedeemPoints = {

                                            viewModel
                                                .redeemPoints()
                                        },


                                        // =============================
                                        // ABRIR QR
                                        // =============================

                                        onOpenQr = {

                                            viewModel
                                                .setQrModalOpen(
                                                    true
                                                )
                                        },


                                        // =============================
                                        // ACCIONES DEL PERFIL
                                        // =============================

                                        onActionClicked = { message ->

                                            /*
                                             * Solo frontend.
                                             *
                                             * Luego puedes utilizar
                                             * este callback para
                                             * mostrar ventanas.
                                             */
                                        },


                                        // =============================
                                        // CERRAR SESIÓN
                                        // =============================

                                        onLogout = {

                                            viewModel
                                                .setDrawerOpen(
                                                    false
                                                )

                                            viewModel
                                                .setQrModalOpen(
                                                    false
                                                )

                                            viewModel
                                                .navigateTo(
                                                    Screen.LOGIN
                                                )
                                        }
                                    )
                            }
                        }


                        // =============================================
                        // MODAL QR
                        // =============================================

                        if (
                            uiState.isQrModalOpen &&
                            uiState.currentScreen !=
                            Screen.LOGIN
                        ) {

                            QrPassModal(

                                onDismiss = {

                                    viewModel
                                        .setQrModalOpen(
                                            false
                                        )
                                },


                                guestName =
                                    "Rodrigo Torres",


                                guestsCount =
                                    uiState
                                        .reservation
                                        .guests,


                                ambiance =
                                    uiState
                                        .reservation
                                        .ambiance,


                                time =
                                    "${
                                        if (
                                            uiState
                                                .reservation
                                                .isToday
                                        ) {

                                            "Hoy"

                                        } else {

                                            uiState
                                                .reservation
                                                .dateDay +
                                                    " " +
                                                    uiState
                                                        .reservation
                                                        .dateMonth
                                        }
                                    } ${
                                        uiState
                                            .reservation
                                            .timeSlot
                                    }"
                            )
                        }


                        // =============================================
                        // DRAWER
                        // =============================================

                        if (
                            uiState.currentScreen !=
                            Screen.LOGIN
                        ) {

                            CasaColinaSideDrawer(

                                isOpen =
                                    uiState.isDrawerOpen,


                                onClose = {

                                    viewModel
                                        .setDrawerOpen(
                                            false
                                        )
                                },


                                onNavigate = { screen ->

                                    viewModel.navigateTo(
                                        screen
                                    )
                                },


                                onOpenQr = {

                                    viewModel
                                        .setQrModalOpen(
                                            true
                                        )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}