package com.example.studyhubapp.ui.screens.authentication.login

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.studyhubapp.R
import com.example.studyhubapp.ui.component.button.CreateAccountButton
import com.example.studyhubapp.ui.component.button.SignInWithGoogleButton
import com.example.studyhubapp.ui.component.field.EmailTextField
import com.example.studyhubapp.ui.component.field.PasswordTextField
import com.example.studyhubapp.ui.screens.authentication.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val serverClientId = remember {
        context.getString(R.string.default_web_client_id)
    }
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Welcome to Studyhub",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            navigationIcon = {
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    modifier = Modifier.clickable { navController.navigate("sign_up") },
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
                buttonText = "Login into account"
            ) {
                viewModel.signInWithEmail(email, password)
            }
            SignInWithGoogleButton(
                text = "Sign in with google",
                onClick = {
                    viewModel.signInWithGoogle(
                        context = context,
                        serverClientId = serverClientId,
                    )
                }
            )
        }
    }
}