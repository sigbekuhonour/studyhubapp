package com.honoursigbeku.studyhubapp.feature.usecase.dto

sealed class AccountSetupResult {
    data object Success : AccountSetupResult()
    data object Failure : AccountSetupResult()
}