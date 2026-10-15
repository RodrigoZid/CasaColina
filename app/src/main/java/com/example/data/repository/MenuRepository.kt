
package com.example.data.repository

import com.example.data.remote.api.ApiService
import com.example.data.remote.api.PlatoDto
import com.example.data.remote.api.RetrofitClient
import kotlinx.coroutines.CancellationException

class MenuRepository(
    private val api: ApiService = RetrofitClient.apiService
) {

    // Obtener todos los platos desde AWS
    suspend fun obtenerMenu(
        categoria: String? = null
    ): Result<List<PlatoDto>> {
        return try {
            val response = api.obtenerMenu(categoria)

            if (response.isSuccessful) {
                val menu = response.body()

                if (menu != null) {
                    Result.success(menu.platos)
                } else {
                    Result.failure(
                        Exception("Respuesta vacía del servidor")
                    )
                }
            } else {
                Result.failure(
                    Exception(
                        obtenerMensajeError(response.code())
                    )
                )
            }

        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Obtener un plato por su ID
    suspend fun obtenerPlato(
        platoId: String
    ): Result<Map<String, Any>> {

        if (platoId.isBlank()) {
            return Result.failure(
                IllegalArgumentException(
                    "El ID del plato no puede estar vacío"
                )
            )
        }

        return try {
            val response = api.obtenerPlato(platoId)

            if (response.isSuccessful) {
                val plato = response.body()

                if (plato != null) {
                    Result.success(plato)
                } else {
                    Result.failure(
                        Exception("Plato no encontrado")
                    )
                }
            } else {
                Result.failure(
                    Exception(
                        obtenerMensajeError(response.code())
                    )
                )
            }

        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Mensajes de error HTTP
    private fun obtenerMensajeError(
        codigo: Int
    ): String {
        return when (codigo) {
            400 -> "Solicitud incorrecta (400)"
            401 -> "Sesión no autorizada (401)"
            403 -> "Acceso denegado (403)"
            404 -> "Recurso no encontrado (404)"
            429 -> "Demasiadas solicitudes (429)"
            500 -> "Error interno del servidor AWS (500)"
            502 -> "Error de comunicación con AWS (502)"
            503 -> "Servicio temporalmente no disponible (503)"
            else -> "Error HTTP: $codigo"
        }
    }
}
