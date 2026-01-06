package com.honoursigbeku.studyhubapp.ui.screens.authentication.sign_up

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.honoursigbeku.studyhubapp.R
import com.honoursigbeku.studyhubapp.ui.component.button.CreateAccountButton
import com.honoursigbeku.studyhubapp.ui.component.button.SignInWithGoogleButton
import com.honoursigbeku.studyhubapp.ui.component.field.EmailTextField
import com.honoursigbeku.studyhubapp.ui.component.field.PasswordRequirementField
import com.honoursigbeku.studyhubapp.ui.component.field.PasswordTextField
import com.honoursigbeku.studyhubapp.ui.screens.authentication.AuthViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    navController: NavController
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current
    val serverClientId = stringResource(R.string.default_web_client_id)



    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Welcome to Studyhub", style = MaterialTheme.typography.titleLarge
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
            PasswordRequirementField(password = password)
            CreateAccountButton(
                buttonText = "Create account", authState = authState, onClick = {
                    viewModel.signUpWithEmail(email, password)
                })
            SignInWithGoogleButton(
                text = "Create account with google", authState = authState, onClick = {
                    viewModel.signInWithGoogle(
                        context = context,
                        serverClientId = serverClientId,
                    )
                })
            Row {
                Text(text = "Do you already have an account?")
                Spacer(modifier = Modifier.padding(horizontal = 3.dp))
                Text(
                    text = "Login here",
                    modifier = Modifier.clickable { navController.navigate("loginPage") },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

        }
    }
}