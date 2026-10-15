package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import com.example.data.local.SessionManager
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

// IMPORTANTE: Las rutas deben coincidir con las configuradas en API Gateway.
private object UsuariosApi {
    private const val BASE_URL = "https://88qde3klqd.execute-api.us-east-1.amazonaws.com"
    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    data class ApiResult(
        val ok: Boolean,
        val message: String,
        val token: String = "",
        val usuarioId: String = "",
        val nombre: String = "",
        val correo: String = ""
    )

    suspend fun login(email: String, password: String): ApiResult =
        post("/usuarios/login", JSONObject().apply {
            put("correo", email)
            put("password", password)
        })

    suspend fun register(name: String, email: String, password: String): ApiResult =
        post("/usuarios/registro", JSONObject().apply {
            put("nombre", name)
            put("correo", email)
            put("password", password)
        })

    suspend fun recover(email: String): ApiResult =
        post("/usuarios/recuperar-password", JSONObject().apply { put("correo", email) })

    private suspend fun post(path: String, json: JSONObject): ApiResult = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder()
                .url(BASE_URL + path)
                .post(json.toString().toRequestBody("application/json; charset=utf-8".toMediaType()))
                .build()
            client.newCall(request).execute().use { response ->
                val raw = response.body?.string().orEmpty()
                val body = try { JSONObject(raw) } catch (_: Exception) { JSONObject() }
                val message = body.optString("mensaje").ifBlank {
                    body.optString("message").ifBlank {
                        if (response.isSuccessful) "Operación realizada." else "Error HTTP ${response.code}"
                    }
                }
                // Se exige respuesta 2xx y confirmación explícita si existe un campo success/exito.
                val confirmed = when {
                    body.has("success") -> body.optBoolean("success", false)
                    body.has("exito") -> body.optBoolean("exito", false)
                    else -> response.isSuccessful
                }
                if (path == "/usuarios/login" && response.isSuccessful && confirmed) {
                    // Se admite la envoltura habitual de Lambda Proxy (body como JSON string).
                    val payload = when (val inner = body.opt("body")) {
                        is String -> try { JSONObject(inner) } catch (_: Exception) { body }
                        is JSONObject -> inner
                        else -> body
                    }
                    val user = payload.optJSONObject("usuario")
                        ?: payload.optJSONObject("user")
                        ?: JSONObject()
                    val token = payload.optString("token")
                        .ifBlank { payload.optString("accessToken") }
                    val id = user.optString("usuarioId")
                        .ifBlank { payload.optString("usuarioId") }
                    if (token.isBlank() || id.isBlank()) {
                        ApiResult(false, "AWS respondió, pero no devolvió token y usuarioId. Verifica la respuesta de Lambda.")
                    } else {
                        ApiResult(true, message, token, id,
                            user.optString("nombre"),
                            user.optString("correo").ifBlank { user.optString("email") })
                    }
                } else {
                    ApiResult(response.isSuccessful && confirmed, message)
                }
            }
        } catch (_: IOException) {
            ApiResult(false, "No se pudo conectar con AWS. Comprueba tu conexión y la API.")
        } catch (e: Exception) {
            ApiResult(false, "No fue posible completar la solicitud: ${e.message.orEmpty()}")
        }
    }
}

private fun isValidEmail(email: String): Boolean =
    android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val sessionManager = remember(context) { SessionManager(context.applicationContext) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var showRegister by remember { mutableStateOf(false) }
    var showRecovery by remember { mutableStateOf(false) }
    var notice by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .padding(horizontal = 28.dp, vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("CASA COLINA", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Restaurante & Bar", fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(40.dp))
        Text("Bienvenido", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(8.dp))
        Text("Inicia sesión para continuar", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(28.dp))

        OutlinedTextField(
            value = email, onValueChange = { email = it; errorMessage = "" },
            label = { Text("Correo electrónico") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true, enabled = !loading, modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = password, onValueChange = { password = it; errorMessage = "" },
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (showPassword) "Ocultar contraseña" else "Mostrar contraseña"
                    )
                }
            },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true, enabled = !loading, modifier = Modifier.fillMaxWidth()
        )
        if (errorMessage.isNotEmpty()) {
            Spacer(Modifier.height(8.dp))
            Text(errorMessage, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
        }
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {
                when {
                    !isValidEmail(email) -> errorMessage = "Ingresa un correo electrónico válido."
                    password.isBlank() -> errorMessage = "Ingresa tu contraseña."
                    else -> scope.launch {
                        loading = true
                        errorMessage = ""
                        val result = UsuariosApi.login(email.trim(), password)
                        loading = false
                        if (result.ok) {
                            sessionManager.guardarSesion(
                                token = result.token,
                                usuarioId = result.usuarioId,
                                nombre = result.nombre,
                                correo = result.correo.ifBlank { email.trim() }
                            )
                            onLoginSuccess()
                        } else errorMessage = result.message
                    }
                }
            },
            enabled = !loading,
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            if (loading) CircularProgressIndicator(Modifier.size(22.dp), strokeWidth = 2.dp)
            else Text("INICIAR SESIÓN", fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(18.dp))
        TextButton(onClick = { showRecovery = true }, enabled = !loading) {
            Text("¿Olvidaste tu contraseña?")
        }
        TextButton(onClick = { showRegister = true }, enabled = !loading) {
            Text("¿No tienes una cuenta? Regístrate")
        }
    }

    if (notice.isNotBlank()) {
        AlertDialog(
            onDismissRequest = { notice = "" },
            title = { Text("Casa Colina") },
            text = { Text(notice) },
            confirmButton = { TextButton(onClick = { notice = "" }) { Text("Aceptar") } }
        )
    }

    if (showRegister) {
        var name by remember { mutableStateOf("") }
        var regEmail by remember { mutableStateOf("") }
        var regPassword by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        var regError by remember { mutableStateOf("") }
        var regLoading by remember { mutableStateOf(false) }
        AlertDialog(
            onDismissRequest = { if (!regLoading) showRegister = false },
            title = { Text("Crear cuenta") },
            text = {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    Text("Completa tus datos para registrarte en Casa Colina.")
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(name, { name = it; regError = "" }, label = { Text("Nombre completo") },
                        leadingIcon = { Icon(Icons.Default.Person, null) }, singleLine = true, enabled = !regLoading)
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(regEmail, { regEmail = it; regError = "" }, label = { Text("Correo electrónico") },
                        leadingIcon = { Icon(Icons.Default.Email, null) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email), singleLine = true, enabled = !regLoading)
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(regPassword, { regPassword = it; regError = "" }, label = { Text("Contraseña") },
                        leadingIcon = { Icon(Icons.Default.Lock, null) }, visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), singleLine = true, enabled = !regLoading)
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(confirmPassword, { confirmPassword = it; regError = "" },
                        label = { Text("Confirmar contraseña") }, visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password), singleLine = true, enabled = !regLoading)
                    if (regError.isNotBlank()) {
                        Spacer(Modifier.height(8.dp))
                        Text(regError, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    when {
                        name.isBlank() -> regError = "Ingresa tu nombre."
                        !isValidEmail(regEmail) -> regError = "Ingresa un correo válido."
                        regPassword.length < 12 -> regError = "La contraseña debe tener al menos 12 caracteres."
                        regPassword != confirmPassword -> regError = "Las contraseñas no coinciden."
                        else -> scope.launch {
                            regLoading = true
                            val result = UsuariosApi.register(name.trim(), regEmail.trim(), regPassword)
                            regLoading = false
                            if (result.ok) {
                                email = regEmail.trim()
                                password = ""
                                showRegister = false
                                notice = "Cuenta creada correctamente. Ya puedes iniciar sesión."
                            } else regError = result.message
                        }
                    }
                }, enabled = !regLoading) {
                    if (regLoading) CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
                    else Text("Crear cuenta")
                }
            },
            dismissButton = { TextButton(onClick = { showRegister = false }, enabled = !regLoading) { Text("Cancelar") } }
        )
    }

    if (showRecovery) {
        var recoveryEmail by remember { mutableStateOf(email) }
        var recoveryError by remember { mutableStateOf("") }
        var recoveryLoading by remember { mutableStateOf(false) }
        AlertDialog(
            onDismissRequest = { if (!recoveryLoading) showRecovery = false },
            title = { Text("Recuperar contraseña") },
            text = {
                Column {
                    Text("Ingresa el correo asociado a tu cuenta.")
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(recoveryEmail, { recoveryEmail = it; recoveryError = "" },
                        label = { Text("Correo electrónico") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        singleLine = true, enabled = !recoveryLoading)
                    if (recoveryError.isNotBlank()) {
                        Text(recoveryError, color = MaterialTheme.colorScheme.error, fontSize = 12.sp)
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (!isValidEmail(recoveryEmail)) recoveryError = "Ingresa un correo válido."
                    else scope.launch {
                        recoveryLoading = true
                        val result = UsuariosApi.recover(recoveryEmail.trim())
                        recoveryLoading = false
                        if (result.ok) {
                            showRecovery = false
                            notice = result.message
                        } else recoveryError = result.message
                    }
                }, enabled = !recoveryLoading) {
                    if (recoveryLoading) CircularProgressIndicator(Modifier.size(20.dp), strokeWidth = 2.dp)
                    else Text("Enviar enlace")
                }
            },
            dismissButton = { TextButton(onClick = { showRecovery = false }, enabled = !recoveryLoading) { Text("Cancelar") } }
        )
    }
}
