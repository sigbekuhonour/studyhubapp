package com.honoursigbeku.studyhubapp.ui.screens.authentication.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.honoursigbeku.studyhubapp.R
import com.honoursigbeku.studyhubapp.ui.component.button.CreateAccountButton
import com.honoursigbeku.studyhubapp.ui.component.button.SignInWithGoogleButton
import com.honoursigbeku.studyhubapp.ui.component.field.EmailTextField
import com.honoursigbeku.studyhubapp.ui.component.field.PasswordTextField
import com.honoursigbeku.studyhubapp.ui.screens.authentication.AuthState
import com.honoursigbeku.studyhubapp.ui.screens.authentication.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier, viewModel: AuthViewModel, navController: NavController
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val serverClientId = rememberSaveable {
        context.getString(R.string.default_web_client_id)
    }
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authenticated -> {
                Toast.makeText(context, "Authentication successful", Toast.LENGTH_SHORT).show()
                navController.navigate("LandingPage")
            }

            is AuthState.Loading -> {
                Toast.makeText(context, "Verifying credentials", Toast.LENGTH_SHORT).show()
            }

            is AuthState.Unauthenticated -> {}
            is AuthState.Error -> {
                Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Welcome to Studyhub", style = MaterialTheme.typography.titleLarge
                )
            },
            navigationIcon = {
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    modifier = Modifier.clickable {
                        navController.navigate("signupPage")
                    },
                    contentDescription = null
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.onPrimary),
            scrollBehavior = scrollBehavior
        )
    }, containerColor = MaterialTheme.colorScheme.onPrimary) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(space = 20.dp)
        ) {

            EmailTextField(title = email) {
                email = it
            }
            PasswordTextField(title = password) {
                password = it
            }
            CreateAccountButton(
                buttonText = "Login into account", authState = authState
            ) {
                viewModel.signInWithEmail(email, password)
            }
            SignInWithGoogleButton(
                text = "Sign in with google", authState = authState, onClick = {
                    viewModel.signInWithGoogle(
                        context = context,
                        serverClientId = serverClientId,
                    )
                })
        }
    }
}