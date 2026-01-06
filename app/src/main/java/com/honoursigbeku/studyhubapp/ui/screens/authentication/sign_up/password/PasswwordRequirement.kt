package com.honoursigbeku.studyhubapp.ui.screens.authentication.sign_up.password

data class PasswordRequirements(
    val hasMinLength: Boolean = false,
    val hasUppercase: Boolean = false,
    val hasNumber: Boolean = false
)