
package com.example.data.remote.api

import com.example.data.remote.dto.AgregarCarritoRequest
import com.example.data.remote.dto.CrearPedidoRequest
import com.example.data.remote.dto.CrearReservaRequest
import com.example.data.remote.dto.LoginUsuarioRequest
import com.example.data.remote.dto.RecuperarUsuarioRequest
import com.example.data.remote.dto.RegistroUsuarioRequest

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // USUARIOS Y AUTENTICACIÓN

    @POST("usuarios/login")
    suspend fun iniciarSesion(
        @Body datos: LoginUsuarioRequest
    ): Response<Map<String, Any>>

    @POST("usuarios/registro")
    suspend fun registrarUsuario(
        @Body datos: RegistroUsuarioRequest
    ): Response<Map<String, Any>>

    @POST("usuarios/recuperar")
    suspend fun recuperarContrasena(
        @Body datos: RecuperarUsuarioRequest
    ): Response<Map<String, Any>>

    @POST("usuarios")
    suspend fun crearUsuario(
        @Header("Authorization") token: String,
        @Body datos: Map<String, @JvmSuppressWildcards Any>
    ): Response<Map<String, Any>>

    @GET("usuarios/me")
    suspend fun obtenerPerfil(
        @Header("Authorization") token: String
    ): Response<Map<String, Any>>

    @PUT("usuarios/me")
    suspend fun actualizarPerfil(
        @Header("Authorization") token: String,
        @Body datos: Map<String, @JvmSuppressWildcards Any>
    ): Response<Map<String, Any>>

    @DELETE("usuarios/me")
    suspend fun desactivarPerfil(
        @Header("Authorization") token: String
    ): Response<Map<String, Any>>

    // MENÚ

    @GET("menu")
    suspend fun obtenerMenu(
        @Query("categoria") categoria: String? = null
    ): Response<MenuResponse>

    @GET("menu/{platoId}")
    suspend fun obtenerPlato(
        @Path("platoId") platoId: String
    ): Response<Map<String, Any>>

    @POST("menu")
    suspend fun crearPlato(
        @Header("Authorization") token: String,
        @Body datos: Map<String, @JvmSuppressWildcards Any>
    ): Response<Map<String, Any>>

    @PUT("menu/{platoId}")
    suspend fun actualizarPlato(
        @Header("Authorization") token: String,
        @Path("platoId") platoId: String,
        @Body datos: Map<String, @JvmSuppressWildcards Any>
    ): Response<Map<String, Any>>

    @DELETE("menu/{platoId}")
    suspend fun desactivarPlato(
        @Header("Authorization") token: String,
        @Path("platoId") platoId: String
    ): Response<Map<String, Any>>

    // CARRITO

    @GET("carrito")
    suspend fun obtenerCarrito(
        @Header("Authorization") token: String
    ): Response<Map<String, Any>>

    @POST("carrito")
    suspend fun agregarCarrito(
        @Header("Authorization") token: String,
        @Body datos: AgregarCarritoRequest
    ): Response<Map<String, Any>>

    @PUT("carrito/{itemId}")
    suspend fun actualizarCarrito(
        @Header("Authorization") token: String,
        @Path("itemId") itemId: String,
        @Body datos: Map<String, @JvmSuppressWildcards Any>
    ): Response<Map<String, Any>>

    @DELETE("carrito/{itemId}")
    suspend fun eliminarItem(
        @Header("Authorization") token: String,
        @Path("itemId") itemId: String
    ): Response<Map<String, Any>>

    @DELETE("carrito")
    suspend fun vaciarCarrito(
        @Header("Authorization") token: String
    ): Response<Map<String, Any>>

    // PEDIDOS

    @GET("pedidos")
    suspend fun obtenerPedidos(
        @Header("Authorization") token: String
    ): Response<Map<String, Any>>

    @POST("pedidos")
    suspend fun crearPedido(
        @Header("Authorization") token: String,
        @Body datos: CrearPedidoRequest
    ): Response<Map<String, Any>>

    @GET("pedidos/{pedidoId}")
    suspend fun obtenerPedido(
        @Header("Authorization") token: String,
        @Path("pedidoId") pedidoId: String
    ): Response<Map<String, Any>>

    @PUT("pedidos/{pedidoId}/cancelar")
    suspend fun cancelarPedido(
        @Header("Authorization") token: String,
        @Path("pedidoId") pedidoId: String
    ): Response<Map<String, Any>>

    @PUT("pedidos/{pedidoId}/estado")
    suspend fun actualizarEstadoPedido(
        @Header("Authorization") token: String,
        @Path("pedidoId") pedidoId: String,
        @Body datos: Map<String, @JvmSuppressWildcards Any>
    ): Response<Map<String, Any>>

    // RESERVAS

    @GET("reservas")
    suspend fun obtenerReservas(
        @Header("Authorization") token: String
    ): Response<Map<String, Any>>

    @POST("reservas")
    suspend fun crearReserva(
        @Header("Authorization") token: String,
        @Body datos: CrearReservaRequest
    ): Response<Map<String, Any>>

    @GET("reservas/disponibilidad")
    suspend fun consultarDisponibilidad(
        @Query("fecha") fecha: String,
        @Query("hora") hora: String,
        @Query("personas") personas: Int
    ): Response<Map<String, Any>>

    @GET("reservas/{reservaId}")
    suspend fun obtenerReserva(
        @Header("Authorization") token: String,
        @Path("reservaId") reservaId: String
    ): Response<Map<String, Any>>

    @PUT("reservas/{reservaId}")
    suspend fun actualizarReserva(
        @Header("Authorization") token: String,
        @Path("reservaId") reservaId: String,
        @Body datos: Map<String, @JvmSuppressWildcards Any>
    ): Response<Map<String, Any>>

    @PUT("reservas/{reservaId}/cancelar")
    suspend fun cancelarReserva(
        @Header("Authorization") token: String,
        @Path("reservaId") reservaId: String
    ): Response<Map<String, Any>>
}

// RESPUESTA DEL MENÚ

data class MenuResponse(
    val mensaje: String? = null,
    val total: Int = 0,
    val categoria: String? = null,
    val platos: List<PlatoDto> = emptyList()
)

// MODELO DEL PLATO

data class PlatoDto(
    val platoId: String = "",
    val nombre: String = "",
    val descripcion: String? = null,
    val descripcionCorta: String? = null,
    val descripcionCompleta: String? = null,
    val precio: Double = 0.0,
    val categoria: String = "",
    val imagenUrl: String? = null,
    val disponible: Boolean = true,
    val destacadoChef: Boolean = false,
    val tiempoPreparacion: String? = "",
    val calificacion: Double? = null,
    val cantidadResenas: Int = 0
)