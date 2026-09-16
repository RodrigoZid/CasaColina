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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.Screen
import com.example.ui.components.CasaColinaBottomBar
import com.example.ui.components.CasaColinaSideDrawer
import com.example.ui.components.CasaColinaTopAppBar
import com.example.ui.components.QrPassModal
import com.example.ui.screens.CartScreen
import com.example.ui.screens.DishDetailScreen
import com.example.ui.screens.HomeScreen
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
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(uiState.snackbarMessage) {
                    uiState.snackbarMessage?.let { message ->
                        snackbarHostState.showSnackbar(message)
                        viewModel.clearSnackbar()
                    }
                }

                // Handle system back navigation
                BackHandler(enabled = uiState.currentScreen != Screen.HOME) {
                    viewModel.navigateBack()
                }

                val totalCartCount = uiState.cartItems.sumOf { it.quantity }

                Scaffold(
                    topBar = {
                        if (uiState.currentScreen != Screen.DISH_DETAIL) {
                            CasaColinaTopAppBar(
                                cartCount = totalCartCount,
                                onMenuClick = { viewModel.setDrawerOpen(true) },
                                onLogoClick = { viewModel.navigateTo(Screen.HOME) },
                                onCartClick = { viewModel.navigateTo(Screen.CART) },
                                modifier = Modifier.statusBarsPadding()
                            )
                        }
                    },
                    bottomBar = {
                        if (uiState.currentScreen != Screen.DISH_DETAIL) {
                            CasaColinaBottomBar(
                                currentScreen = uiState.currentScreen,
                                cartCount = totalCartCount,
                                onScreenSelected = { screen -> viewModel.navigateTo(screen) },
                                modifier = Modifier.navigationBarsPadding()
                            )
                        }
                    },
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState,
                            modifier = Modifier.navigationBarsPadding()
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = if (uiState.currentScreen == Screen.DISH_DETAIL) innerPadding.calculateTopPadding() else innerPadding.calculateTopPadding(),
                                bottom = innerPadding.calculateBottomPadding()
                            )
                    ) {
                        AnimatedContent(
                            targetState = uiState.currentScreen,
                            transitionSpec = { fadeIn() togetherWith fadeOut() },
                            label = "ScreenTransition"
                        ) { targetScreen ->
                            when (targetScreen) {
                                Screen.HOME -> HomeScreen(
                                    onNavigate = { screen -> viewModel.navigateTo(screen) },
                                    onFilterCategory = { cat ->
                                        viewModel.setCategory(cat)
                                        viewModel.navigateTo(Screen.MENU)
                                    },
                                    onSelectDish = { dish -> viewModel.openDishDetail(dish) },
                                    onQuickAdd = { dish -> viewModel.quickAddToCart(dish) },
                                    onOpenQr = { viewModel.setQrModalOpen(true) }
                                )

                                Screen.MENU -> MenuScreen(
                                    searchQuery = uiState.searchQuery,
                                    onSearchChange = { q -> viewModel.setSearchQuery(q) },
                                    selectedCategory = uiState.selectedCategory,
                                    onSelectCategory = { cat -> viewModel.setCategory(cat) },
                                    onSelectDish = { dish -> viewModel.openDishDetail(dish) },
                                    onQuickAdd = { dish -> viewModel.quickAddToCart(dish) }
                                )

                                Screen.DISH_DETAIL -> DishDetailScreen(
                                    dish = uiState.selectedDish,
                                    selectedDoneness = uiState.detailDoneness,
                                    onSelectDoneness = { doneness -> viewModel.setDetailDoneness(doneness) },
                                    selectedExtras = uiState.detailExtras,
                                    onToggleExtra = { name, price -> viewModel.toggleDetailExtra(name, price) },
                                    quantity = uiState.detailQuantity,
                                    onUpdateQuantity = { delta -> viewModel.updateDetailQuantity(delta) },
                                    isFavorite = uiState.favorites.contains(uiState.selectedDish.id),
                                    onToggleFavorite = { viewModel.toggleFavorite(uiState.selectedDish.id) },
                                    onBack = { viewModel.navigateBack() },
                                    onAddToCart = { viewModel.addDetailDishToCart() }
                                )

                                Screen.RESERVATIONS -> ReservationScreen(
                                    reservation = uiState.reservation,
                                    onSelectAmbiance = { amb -> viewModel.selectReservationAmbiance(amb) },
                                    onSelectDate = { d, m, today -> viewModel.selectReservationDate(d, m, today) },
                                    onSelectTime = { t -> viewModel.selectReservationTime(t) },
                                    onChangeGuests = { delta -> viewModel.changeReservationGuests(delta) },
                                    onToggleOccasion = { occ -> viewModel.toggleReservationOccasion(occ) },
                                    onConfirm = { viewModel.confirmReservation() }
                                )

                                Screen.CART -> CartScreen(
                                    cartItems = uiState.cartItems,
                                    onUpdateQuantity = { id, delta -> viewModel.updateCartItemQuantity(id, delta) },
                                    onClearCart = { viewModel.clearCart() },
                                    deliveryMode = uiState.deliveryMode,
                                    onSelectDeliveryMode = { mode -> viewModel.setDeliveryMode(mode) },
                                    paymentMethod = uiState.paymentMethod,
                                    onSelectPaymentMethod = { method -> viewModel.setPaymentMethod(method) },
                                    onCheckout = { viewModel.processCheckout() },
                                    onNavigateMenu = { viewModel.navigateTo(Screen.MENU) }
                                )

                                Screen.PROFILE -> ProfileScreen(
                                    userPoints = uiState.userPoints,
                                    onRedeemPoints = { viewModel.redeemPoints() },
                                    onOpenQr = { viewModel.setQrModalOpen(true) },
                                    onActionClicked = { msg ->
                                        // Trigger feedback
                                    }
                                )
                            }
                        }
                    }

                    // QR Code Pass Modal
                    if (uiState.isQrModalOpen) {
                        QrPassModal(
                            onDismiss = { viewModel.setQrModalOpen(false) },
                            guestName = "Rodrigo Torres",
                            guestsCount = uiState.reservation.guests,
                            ambiance = uiState.reservation.ambiance,
                            time = "${if (uiState.reservation.isToday) "Hoy" else uiState.reservation.dateDay + " " + uiState.reservation.dateMonth} ${uiState.reservation.timeSlot}"
                        )
                    }

                    // Side Drawer
                    CasaColinaSideDrawer(
                        isOpen = uiState.isDrawerOpen,
                        onClose = { viewModel.setDrawerOpen(false) },
                        onNavigate = { screen -> viewModel.navigateTo(screen) },
                        onOpenQr = { viewModel.setQrModalOpen(true) }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme { Greeting("Android") }
}
