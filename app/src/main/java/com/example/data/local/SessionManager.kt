
package com.example.data.local

import android.content.Context

class SessionManager(context: Context) {

    // Almacenamiento local de los datos de sesión
    private val preferences = context.getSharedPreferences(
        "CasaColinaSession",
        Context.MODE_PRIVATE
    )

    companion object {
        private const val KEY_TOKEN = "jwt_token"
        private const val KEY_USER_ID = "usuario_id"
        private const val KEY_USER_NAME = "usuario_nombre"
        private const val KEY_USER_EMAIL = "usuario_correo"
    }

    // Guardar los datos de la sesión del usuario
    fun guardarSesion(
        token: String,
        usuarioId: String,
        nombre: String,
        correo: String
    ) {
        preferences.edit()
            .putString(KEY_TOKEN, token.trim())
            .putString(KEY_USER_ID, usuarioId.trim())
            .putString(KEY_USER_NAME, nombre.trim())
            .putString(KEY_USER_EMAIL, correo.trim())
            .apply()
    }

    // Obtener el token de autenticación
    fun obtenerToken(): String? {
        return preferences.getString(KEY_TOKEN, null)
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
    }

    // Preparar el token para las solicitudes HTTP a AWS
    // Formato: Authorization: Bearer TOKEN
    fun obtenerAuthorizationHeader(): String? {
        val token = obtenerToken() ?: return null

        return if (token.startsWith("Bearer ", ignoreCase = true)) {
            token
        } else {
            "Bearer $token"
        }
    }

    // Obtener el identificador del usuario
    fun obtenerUsuarioId(): String? {
        return preferences.getString(KEY_USER_ID, null)
    }

    // Obtener el nombre del usuario
    fun obtenerNombre(): String? {
        return preferences.getString(KEY_USER_NAME, null)
    }

    // Obtener el correo del usuario
    fun obtenerCorreo(): String? {
        return preferences.getString(KEY_USER_EMAIL, null)
    }

    // Verificar si existe un token guardado
    // No significa que el token siga siendo válido
    fun tieneToken(): Boolean {
        return !obtenerToken().isNullOrBlank()
    }

    // Verificar si existe un usuario guardado
    fun tieneUsuario(): Boolean {
        return !obtenerUsuarioId().isNullOrBlank()
    }

    // Cerrar sesión y eliminar los datos locales
    fun cerrarSesion() {
        preferences.edit()
            .clear()
            .apply()
    }
}
