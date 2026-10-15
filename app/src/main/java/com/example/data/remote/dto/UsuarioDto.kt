
package com.example.data.remote.dto

// Solicitud para registrar un usuario
data class RegistroUsuarioRequest(
    val nombre: String,
    val correo: String,
    val password: String,
    val telefono: String? = null
)

// Solicitud para iniciar sesión
data class LoginUsuarioRequest(
    val correo: String,
    val password: String
)

// Solicitud para recuperar contraseña
data class RecuperarUsuarioRequest(
    val correo: String
)

// Datos del usuario en DynamoDB
data class UsuarioDto(
    val usuarioId: String = "",
    val nombre: String = "",
    val correo: String = "",
    val telefono: String? = null,
    val rol: String = "cliente",
    val fechaRegistro: String = "",
    val estado: String = "activo"
)

// Respuesta de autenticación de Lambda
data class AutenticacionResponse(
    val mensaje: String? = null,
    val token: String? = null,
    val usuario: UsuarioDto? = null
)