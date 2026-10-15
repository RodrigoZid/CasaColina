package com.example.data.repository



import com.example.data.local.SessionManager
import com.example.data.remote.api.ApiService
import com.example.data.remote.api.RetrofitClient
import com.example.data.remote.dto.LoginUsuarioRequest
import com.example.data.remote.dto.RegistroUsuarioRequest
import com.example.data.remote.dto.RecuperarUsuarioRequest
import kotlinx.coroutines.CancellationException

class UsuarioRepository(
    private val api: ApiService = RetrofitClient.apiService,
    private val sessionManager: SessionManager
) {

    // Iniciar sesión
    suspend fun iniciarSesion(
        correo: String,
        password: String
    ): Result<Map<String, Any>> {
        return ejecutar {
            api.iniciarSesion(
                LoginUsuarioRequest(
                    correo = correo.trim(),
                    password = password
                )
            )
        }
    }

    // Registrar usuario
    suspend fun registrarUsuario(
        nombre: String,
        correo: String,
        password: String,
        telefono: String? = null
    ): Result<Map<String, Any>> {
        return ejecutar {
            api.registrarUsuario(
                RegistroUsuarioRequest(
                    nombre = nombre.trim(),
                    correo = correo.trim(),
                    password = password,
                    telefono = telefono
                )
            )
        }
    }

    // Recuperar contraseña
    suspend fun recuperarContrasena(
        correo: String
    ): Result<Map<String, Any>> {
        return ejecutar {
            api.recuperarContrasena(
                RecuperarUsuarioRequest(
                    correo = correo.trim()
                )
            )
        }
    }

    // Obtener perfil del usuario autenticado
    suspend fun obtenerPerfil(): Result<Map<String, Any>> {
        val token = obtenerToken()
            ?: return Result.failure(
                Exception("Debes iniciar sesión")
            )

        return ejecutar {
            api.obtenerPerfil(token)
        }
    }

    // Actualizar perfil
    suspend fun actualizarPerfil(
        datos: Map<String, Any>
    ): Result<Map<String, Any>> {
        val token = obtenerToken()
            ?: return Result.failure(
                Exception("Debes iniciar sesión")
            )

        return ejecutar {
            api.actualizarPerfil(token, datos)
        }
    }

    // Obtener datos guardados de la sesión
    fun obtenerUsuarioId(): String? =
        sessionManager.obtenerUsuarioId()

    fun obtenerNombre(): String? =
        sessionManager.obtenerNombre()

    fun obtenerCorreo(): String? =
        sessionManager.obtenerCorreo()

    fun tieneSesion(): Boolean =
        sessionManager.tieneToken()

    fun cerrarSesion() {
        sessionManager.cerrarSesion()
    }

    private fun obtenerToken(): String? =
        sessionManager.obtenerAuthorizationHeader()

    private suspend fun ejecutar(
        operacion: suspend () -> retrofit2.Response<Map<String, Any>>
    ): Result<Map<String, Any>> {
        return try {
            val response = operacion()

            if (response.isSuccessful) {
                val body = response.body()

                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(
                        Exception("Respuesta vacía del servidor")
                    )
                }
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
