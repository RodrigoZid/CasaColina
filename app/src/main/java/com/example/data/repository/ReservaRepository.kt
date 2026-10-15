
package com.example.data.repository

import com.example.data.local.SessionManager
import com.example.data.remote.api.ApiService
import com.example.data.remote.api.RetrofitClient
import com.example.data.remote.dto.CrearReservaRequest
import kotlinx.coroutines.CancellationException
import retrofit2.Response

class ReservaRepository(
    private val api: ApiService = RetrofitClient.apiService,
    private val sessionManager: SessionManager
) {

    // Consultar disponibilidad de mesa
    suspend fun consultarDisponibilidad(
        fecha: String,
        hora: String,
        personas: Int
    ): Result<Map<String, Any>> {
        if (fecha.isBlank() || hora.isBlank() || personas <= 0) {
            return Result.failure(
                IllegalArgumentException(
                    "Debes seleccionar una fecha, hora y cantidad de personas válidas"
                )
            )
        }

        return ejecutar {
            api.consultarDisponibilidad(
                fecha = fecha,
                hora = hora,
                personas = personas
            )
        }
    }

    // Crear una reserva
    suspend fun crearReserva(
        fecha: String,
        hora: String,
        personas: Int,
        ambiente: String,
        observaciones: String? = null
    ): Result<Map<String, Any>> {
        val usuarioId = sessionManager.obtenerUsuarioId()
            ?.takeIf { it.isNotBlank() }
            ?: return Result.failure(
                Exception("No se encontró el usuario autenticado")
            )

        if (
            fecha.isBlank() ||
            hora.isBlank() ||
            personas <= 0 ||
            ambiente.isBlank()
        ) {
            return Result.failure(
                IllegalArgumentException(
                    "Completa correctamente los datos de la reserva"
                )
            )
        }

        return ejecutarConToken { token ->
            api.crearReserva(
                token = token,
                datos = CrearReservaRequest(
                    usuarioId = usuarioId,
                    fecha = fecha,
                    hora = hora,
                    personas = personas,
                    ambiente = ambiente,
                    observaciones = observaciones
                )
            )
        }
    }

    // Consultar reservas del usuario
    suspend fun obtenerReservas(): Result<Map<String, Any>> {
        return ejecutarConToken { token ->
            api.obtenerReservas(token)
        }
    }

    // Consultar detalle de una reserva
    suspend fun obtenerReserva(
        reservaId: String
    ): Result<Map<String, Any>> {
        if (reservaId.isBlank()) {
            return Result.failure(
                IllegalArgumentException("ID de reserva vacío")
            )
        }

        return ejecutarConToken { token ->
            api.obtenerReserva(token, reservaId)
        }
    }

    // Cancelar una reserva
    suspend fun cancelarReserva(
        reservaId: String
    ): Result<Map<String, Any>> {
        if (reservaId.isBlank()) {
            return Result.failure(
                IllegalArgumentException("ID de reserva vacío")
            )
        }

        return ejecutarConToken { token ->
            api.cancelarReserva(token, reservaId)
        }
    }

    private suspend fun ejecutarConToken(
        operacion: suspend (String) -> Response<Map<String, Any>>
    ): Result<Map<String, Any>> {
        val token = sessionManager.obtenerAuthorizationHeader()
            ?: return Result.failure(
                Exception("Debes iniciar sesión")
            )

        return ejecutar {
            operacion(token)
        }
    }

    private suspend fun ejecutar(
        operacion: suspend () -> Response<Map<String, Any>>
    ): Result<Map<String, Any>> {
        return try {
            val response = operacion()

            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyMap())
            } else {
                val detalle = response.errorBody()?.string()
                    ?.take(1000)
                    .orEmpty()

                Result.failure(
                    Exception(
                        "Error HTTP ${response.code()}" +
                                if (detalle.isNotBlank()) ": $detalle" else ""
                    )
                )
            }

        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}


