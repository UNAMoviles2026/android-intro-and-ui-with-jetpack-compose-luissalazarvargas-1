package com.moviles.unaroom.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MeetingRoom
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moviles.unaroom.ui.components.AppButton
import com.moviles.unaroom.ui.components.AppTextField
import com.moviles.unaroom.ui.theme.AppBackground
import com.moviles.unaroom.ui.theme.AppError
import com.moviles.unaroom.ui.theme.AppPrimary
import com.moviles.unaroom.ui.theme.AppSecondaryText
import com.moviles.unaroom.ui.theme.AppSurfaceVariant
import kotlinx.coroutines.launch

@Composable
private fun LoginHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(82.dp)
                .background(AppSurfaceVariant, shape = RoundedCornerShape(22.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Outlined.MeetingRoom, contentDescription = null,
                tint = AppPrimary, modifier = Modifier.size(38.dp))
        }
        Spacer(modifier = Modifier.height(28.dp))
        Text("UnaRoom", style = MaterialTheme.typography.headlineLarge, color = AppPrimary)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Classroom Reservation", color = AppSecondaryText,
            style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun EmailTextField(value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    AppTextField(value, label = "Email", placeholder = "student@university.edu",
        onValueChange, modifier, KeyboardOptions(keyboardType = KeyboardType.Email))
}

@Composable
fun PasswordTextField(value: String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    AppTextField(value, label = "Password", placeholder = "•••••••",
        onValueChange, modifier,
        KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = PasswordVisualTransformation())
}

@Composable
fun LoginButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    AppButton(text = "Login", onClick = onClick, modifier = modifier.fillMaxWidth())
}

// --- Pantalla principal ---

@Composable
fun LoginScreen(onLoginClick: () -> Unit, modifier: Modifier = Modifier) {
    var email    by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope    = rememberCoroutineScope()

    Scaffold(
        modifier       = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost   = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(modifier = Modifier.padding(horizontal = 18.dp, vertical = 12.dp),
                    containerColor = AppError, contentColor = AppBackground,
                    shape = RoundedCornerShape(18.dp), snackbarData = data)
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)
            .padding(horizontal = 26.dp, vertical = 24.dp)) {
            Column(modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally) {
                LoginHeader()
                Spacer(modifier = Modifier.height(56.dp))
                EmailTextField(email,{email=it})
                Spacer(modifier = Modifier.height(22.dp))
                PasswordTextField(password, { password = it })
                Spacer(modifier = Modifier.height(22.dp))
                LoginButton(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Please fill in all fields",
                                    withDismissAction = true
                                )
                            }
                        } else {
                            onLoginClick()
                        }
                    }
                )
                Spacer(modifier = Modifier.height(34.dp))
                Text("Demo: Use any email and password", color = AppSecondaryText,
                    style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
            }
        }
    }
}

@Preview
@Composable
fun  LoginScreenPreview(){
    LoginScreen(onLoginClick = {})
}
