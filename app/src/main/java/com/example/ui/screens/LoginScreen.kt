package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.MyApplicationTheme


@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var showPassword by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    // =========================================================
    // CONTROL DE VENTANAS
    // =========================================================

    var showForgotPasswordDialog by remember {
        mutableStateOf(false)
    }

    var showRegisterDialog by remember {
        mutableStateOf(false)
    }

    var showResetConfirmation by remember {
        mutableStateOf(false)
    }

    var showRegisterConfirmation by remember {
        mutableStateOf(false)
    }


    // =========================================================
    // LOGIN
    // =========================================================

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "CASA COLINA",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Restaurante & Bar",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(40.dp)
        )

        Text(
            text = "Bienvenido",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Inicia sesión para continuar",
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(28.dp)
        )


        // =====================================================
        // CORREO
        // =====================================================

        OutlinedTextField(

            value = email,

            onValueChange = {
                email = it
                errorMessage = ""
            },

            label = {
                Text("Correo electrónico")
            },

            leadingIcon = {

                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null
                )
            },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),

            singleLine = true,

            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
        )


        Spacer(
            modifier = Modifier.height(16.dp)
        )


        // =====================================================
        // CONTRASEÑA
        // =====================================================

        OutlinedTextField(

            value = password,

            onValueChange = {
                password = it
                errorMessage = ""
            },

            label = {
                Text("Contraseña")
            },

            leadingIcon = {

                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null
                )
            },

            trailingIcon = {

                IconButton(
                    onClick = {
                        showPassword = !showPassword
                    }
                ) {

                    Icon(
                        imageVector =
                            if (showPassword)
                                Icons.Default.VisibilityOff
                            else
                                Icons.Default.Visibility,

                        contentDescription =
                            "Mostrar contraseña"
                    )
                }
            },

            visualTransformation =
                if (showPassword)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),

            singleLine = true,

            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
        )


        // =====================================================
        // ERROR
        // =====================================================

        if (errorMessage.isNotEmpty()) {

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp
            )
        }


        Spacer(
            modifier = Modifier.height(24.dp)
        )


        // =====================================================
        // INICIAR SESIÓN
        // =====================================================

        Button(

            onClick = {

                when {

                    email.isBlank() -> {

                        errorMessage =
                            "Ingresa tu correo electrónico."
                    }

                    !email.contains("@") -> {

                        errorMessage =
                            "Ingresa un correo electrónico válido."
                    }

                    password.isBlank() -> {

                        errorMessage =
                            "Ingresa tu contraseña."
                    }

                    else -> {

                        onLoginSuccess()
                    }
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),

            shape =
                MaterialTheme.shapes.extraLarge
        ) {

            Text(
                text = "INICIAR SESIÓN",
                fontWeight = FontWeight.Bold
            )
        }


        Spacer(
            modifier = Modifier.height(18.dp)
        )


        // =====================================================
        // OLVIDÉ MI CONTRASEÑA
        // =====================================================

        TextButton(

            onClick = {

                showForgotPasswordDialog = true
            }

        ) {

            Text(
                text = "¿Olvidaste tu contraseña?"
            )
        }


        Spacer(
            modifier = Modifier.height(4.dp)
        )


        // =====================================================
        // REGISTRARSE
        // =====================================================

        TextButton(

            onClick = {

                showRegisterDialog = true
            }

        ) {

            Text(
                text = "¿No tienes una cuenta? Regístrate"
            )
        }
    }


    // =========================================================
    // RECUPERAR CONTRASEÑA
    // =========================================================

    if (showForgotPasswordDialog) {

        ForgotPasswordDialog(

            initialEmail = email,

            onDismiss = {

                showForgotPasswordDialog = false
            },

            onSend = { recoveryEmail ->

                email = recoveryEmail

                showForgotPasswordDialog = false

                showResetConfirmation = true
            }
        )
    }


    // =========================================================
    // CONFIRMACIÓN DE RECUPERACIÓN
    // =========================================================

    if (showResetConfirmation) {

        AlertDialog(

            onDismissRequest = {
                // Se queda abierto hasta pulsar Aceptar
            },

            title = {

                Text(
                    text = "Correo enviado",
                    fontWeight = FontWeight.Bold
                )
            },

            text = {

                Text(
                    text =
                        "Hemos enviado un enlace de recuperación a:\n\n$email\n\n" +
                                "Revisa tu bandeja de entrada para restablecer tu contraseña."
                )
            },

            confirmButton = {

                Button(

                    onClick = {

                        showResetConfirmation = false
                    }

                ) {

                    Text("Aceptar")
                }
            }
        )
    }


    // =========================================================
    // REGISTRO
    // =========================================================

    if (showRegisterDialog) {

        RegisterDialog(

            onDismiss = {

                showRegisterDialog = false
            },

            onRegister = { registeredEmail ->

                email = registeredEmail

                showRegisterDialog = false

                showRegisterConfirmation = true
            }
        )
    }


    // =========================================================
    // CONFIRMACIÓN DE REGISTRO
    // =========================================================

    if (showRegisterConfirmation) {

        AlertDialog(

            onDismissRequest = {
                // Mantener abierto hasta Aceptar
            },

            title = {

                Text(
                    text = "Cuenta creada",
                    fontWeight = FontWeight.Bold
                )
            },

            text = {

                Text(
                    text =
                        "Tu cuenta fue registrada correctamente.\n\n" +
                                "Ya puedes iniciar sesión con tu correo."
                )
            },

            confirmButton = {

                Button(

                    onClick = {

                        showRegisterConfirmation = false
                    }

                ) {

                    Text("Ir a iniciar sesión")
                }
            }
        )
    }
}


// =============================================================
// RECUPERAR CONTRASEÑA
// =============================================================

@Composable
private fun ForgotPasswordDialog(

    initialEmail: String,

    onDismiss: () -> Unit,

    onSend: (String) -> Unit
) {

    var recoveryEmail by remember {

        mutableStateOf(initialEmail)
    }

    var error by remember {

        mutableStateOf("")
    }


    AlertDialog(

        onDismissRequest = onDismiss,

        title = {

            Text(
                text = "Recuperar contraseña",
                fontWeight = FontWeight.Bold
            )
        },

        text = {

            Column {

                Text(
                    text =
                        "Ingresa el correo asociado a tu cuenta. " +
                                "Te enviaremos un enlace para restablecer tu contraseña."
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                OutlinedTextField(

                    value = recoveryEmail,

                    onValueChange = {

                        recoveryEmail = it
                        error = ""
                    },

                    label = {

                        Text("Correo electrónico")
                    },

                    leadingIcon = {

                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null
                        )
                    },

                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType =
                                KeyboardType.Email
                        ),

                    singleLine = true,

                    isError = error.isNotEmpty(),

                    supportingText = {

                        if (error.isNotEmpty()) {

                            Text(error)
                        }
                    },

                    modifier =
                        Modifier.fillMaxWidth()
                )
            }
        },

        confirmButton = {

            Button(

                onClick = {

                    when {

                        recoveryEmail.isBlank() -> {

                            error =
                                "Ingresa tu correo electrónico."
                        }

                        !recoveryEmail.contains("@") -> {

                            error =
                                "Ingresa un correo válido."
                        }

                        else -> {

                            onSend(recoveryEmail)
                        }
                    }
                }

            ) {

                Text(
                    text = "Enviar enlace"
                )
            }
        },

        dismissButton = {

            TextButton(

                onClick = onDismiss

            ) {

                Text(
                    text = "Cancelar"
                )
            }
        }
    )
}


// =============================================================
// REGISTRAR NUEVA CUENTA
// =============================================================

@Composable
private fun RegisterDialog(

    onDismiss: () -> Unit,

    onRegister: (String) -> Unit
) {

    var name by remember {
        mutableStateOf("")
    }

    var registerEmail by remember {
        mutableStateOf("")
    }

    var registerPassword by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    var showRegisterPassword by remember {
        mutableStateOf(false)
    }

    var error by remember {
        mutableStateOf("")
    }


    AlertDialog(

        onDismissRequest = onDismiss,

        title = {

            Text(
                text = "Crear cuenta",
                fontWeight = FontWeight.Bold
            )
        },

        text = {

            Column {

                Text(
                    text =
                        "Completa tus datos para registrarte en Casa Colina."
                )


                Spacer(
                    modifier = Modifier.height(16.dp)
                )


                // NOMBRE

                OutlinedTextField(

                    value = name,

                    onValueChange = {
                        name = it
                        error = ""
                    },

                    label = {
                        Text("Nombre completo")
                    },

                    leadingIcon = {

                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null
                        )
                    },

                    singleLine = true,

                    modifier =
                        Modifier.fillMaxWidth()
                )


                Spacer(
                    modifier = Modifier.height(10.dp)
                )


                // CORREO

                OutlinedTextField(

                    value = registerEmail,

                    onValueChange = {

                        registerEmail = it
                        error = ""
                    },

                    label = {

                        Text("Correo electrónico")
                    },

                    leadingIcon = {

                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null
                        )
                    },

                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType =
                                KeyboardType.Email
                        ),

                    singleLine = true,

                    modifier =
                        Modifier.fillMaxWidth()
                )


                Spacer(
                    modifier = Modifier.height(10.dp)
                )


                // CONTRASEÑA

                OutlinedTextField(

                    value = registerPassword,

                    onValueChange = {

                        registerPassword = it
                        error = ""
                    },

                    label = {

                        Text("Contraseña")
                    },

                    leadingIcon = {

                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null
                        )
                    },

                    trailingIcon = {

                        IconButton(

                            onClick = {

                                showRegisterPassword =
                                    !showRegisterPassword
                            }

                        ) {

                            Icon(

                                imageVector =
                                    if (showRegisterPassword)
                                        Icons.Default.VisibilityOff
                                    else
                                        Icons.Default.Visibility,

                                contentDescription =
                                    "Mostrar contraseña"
                            )
                        }
                    },

                    visualTransformation =
                        if (showRegisterPassword)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),

                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType =
                                KeyboardType.Password
                        ),

                    singleLine = true,

                    modifier =
                        Modifier.fillMaxWidth()
                )


                Spacer(
                    modifier = Modifier.height(10.dp)
                )


                // CONFIRMAR CONTRASEÑA

                OutlinedTextField(

                    value = confirmPassword,

                    onValueChange = {

                        confirmPassword = it
                        error = ""
                    },

                    label = {

                        Text("Confirmar contraseña")
                    },

                    leadingIcon = {

                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null
                        )
                    },

                    visualTransformation =
                        PasswordVisualTransformation(),

                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType =
                                KeyboardType.Password
                        ),

                    singleLine = true,

                    modifier =
                        Modifier.fillMaxWidth()
                )


                if (error.isNotEmpty()) {

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = error,
                        color =
                            MaterialTheme.colorScheme.error,

                        fontSize = 12.sp
                    )
                }
            }
        },

        confirmButton = {

            Button(

                onClick = {

                    when {

                        name.isBlank() -> {

                            error =
                                "Ingresa tu nombre."
                        }

                        registerEmail.isBlank() -> {

                            error =
                                "Ingresa tu correo."
                        }

                        !registerEmail.contains("@") -> {

                            error =
                                "Ingresa un correo válido."
                        }

                        registerPassword.length < 6 -> {

                            error =
                                "La contraseña debe tener al menos 6 caracteres."
                        }

                        registerPassword != confirmPassword -> {

                            error =
                                "Las contraseñas no coinciden."
                        }

                        else -> {

                            onRegister(
                                registerEmail
                            )
                        }
                    }
                }

            ) {

                Text(
                    text = "Crear cuenta"
                )
            }
        },

        dismissButton = {

            TextButton(

                onClick = onDismiss

            ) {

                Text(
                    text = "Cancelar"
                )
            }
        }
    )
}