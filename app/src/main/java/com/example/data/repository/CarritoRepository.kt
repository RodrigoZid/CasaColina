
package com.example.data.repository

import com.example.data.local.SessionManager
import com.example.data.remote.api.ApiService
import com.example.data.remote.api.RetrofitClient
import com.example.data.remote.dto.AgregarCarritoRequest
import kotlinx.coroutines.CancellationException
import retrofit2.Response

class CarritoRepository(
    private val api: ApiService = RetrofitClient.apiService,
    private val sessionManager: SessionManager
) {

    // Consultar carrito del usuario
    suspend fun obtenerCarrito(): Result<Map<String, Any>> {
        return ejecutarConToken { token ->
            api.obtenerCarrito(token)
        }
    }

    // Agregar un plato al carrito
    suspend fun agregarProducto(
        platoId: String,
        cantidad: Int
    ): Result<Map<String, Any>> {
        val usuarioId = sessionManager.obtenerUsuarioId()
            ?.takeIf { it.isNotBlank() }
            ?: return Result.failure(
                Exception("No se encontró el usuario autenticado")
            )

        if (platoId.isBlank() || cantidad <= 0) {
            return Result.failure(
                IllegalArgumentException(
                    "El plato y la cantidad deben ser válidos"
                )
            )
        }

        return ejecutarConToken { token ->
            api.agregarCarrito(
                token = token,
                datos = AgregarCarritoRequest(
                    usuarioId = usuarioId,
                    platoId = platoId,
                    cantidad = cantidad
                )
            )
        }
    }

    // Actualizar cantidad de un producto
    suspend fun actualizarCantidad(
        itemId: String,
        cantidad: Int
    ): Result<Map<String, Any>> {
        if (itemId.isBlank() || cantidad <= 0) {
            return Result.failure(
                IllegalArgumentException(
                    "El producto y la cantidad deben ser válidos"
                )
            )
        }

        return ejecutarConToken { token ->
            api.actualizarCarrito(
                token = token,
                itemId = itemId,
                datos = mapOf("cantidad" to cantidad)
            )
        }
    }

    // Eliminar producto del carrito
    suspend fun eliminarProducto(
        itemId: String
    ): Result<Map<String, Any>> {
        if (itemId.isBlank()) {
            return Result.failure(
                IllegalArgumentException("ID de producto vacío")
            )
        }

        return ejecutarConToken { token ->
            api.eliminarItem(token, itemId)
        }
    }

    // Vaciar carrito
    suspend fun vaciarCarrito(): Result<Map<String, Any>> {
        return ejecutarConToken { token ->
            api.vaciarCarrito(token)
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

