
package com.example.data.repository

import com.example.data.local.SessionManager
import com.example.data.remote.api.ApiService
import com.example.data.remote.api.RetrofitClient
import com.example.data.remote.dto.CrearPedidoRequest
import com.example.data.remote.dto.ItemPedidoDto
import kotlinx.coroutines.CancellationException
import retrofit2.Response

class PedidoRepository(
    private val api: ApiService = RetrofitClient.apiService,
    private val sessionManager: SessionManager
) {

    // Consultar historial de pedidos
    suspend fun obtenerPedidos(): Result<Map<String, Any>> {
        return ejecutarConToken { token ->
            api.obtenerPedidos(token)
        }
    }

    // Crear un nuevo pedido
    suspend fun crearPedido(
        items: List<ItemPedidoDto>,
        modalidadEntrega: String,
        metodoPago: String,
        direccionEntrega: String? = null
    ): Result<Map<String, Any>> {
        val usuarioId = sessionManager.obtenerUsuarioId()
            ?.takeIf { it.isNotBlank() }
            ?: return Result.failure(
                Exception("No se encontró el usuario autenticado")
            )

        if (items.isEmpty()) {
            return Result.failure(
                IllegalArgumentException(
                    "No puedes registrar un pedido sin productos"
                )
            )
        }

        if (modalidadEntrega.isBlank() || metodoPago.isBlank()) {
            return Result.failure(
                IllegalArgumentException(
                    "Selecciona una modalidad de entrega y un método de pago"
                )
            )
        }

        return ejecutarConToken { token ->
            api.crearPedido(
                token = token,
                datos = CrearPedidoRequest(
                    usuarioId = usuarioId,
                    items = items,
                    modalidadEntrega = modalidadEntrega,
                    metodoPago = metodoPago,
                    direccionEntrega = direccionEntrega
                )
            )
        }
    }

    // Consultar detalle de un pedido
    suspend fun obtenerPedido(
        pedidoId: String
    ): Result<Map<String, Any>> {
        if (pedidoId.isBlank()) {
            return Result.failure(
                IllegalArgumentException("ID de pedido vacío")
            )
        }

        return ejecutarConToken { token ->
            api.obtenerPedido(token, pedidoId)
        }
    }

    // Cancelar pedido
    suspend fun cancelarPedido(
        pedidoId: String
    ): Result<Map<String, Any>> {
        if (pedidoId.isBlank()) {
            return Result.failure(
                IllegalArgumentException("ID de pedido vacío")
            )
        }

        return ejecutarConToken { token ->
            api.cancelarPedido(token, pedidoId)
        }
    }

    private suspend fun ejecutarConToken(
        operacion: suspend (String) -> Response<Map<String, Any>>
    ): Result<Map<String, Any>> {
        val token = sessionManager.obtenerAuthorizationHeader()
            ?: return Result.failure(
                Exception("Debes iniciar sesión")
            )

        return try {
            val response = operacion(token)

            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyMap())
            } else {
                Result.failure(
                    Exception("Error HTTP ${response.code()}")
                )
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

