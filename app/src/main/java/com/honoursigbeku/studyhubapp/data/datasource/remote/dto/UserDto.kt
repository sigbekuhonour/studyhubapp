package com.honoursigbeku.studyhubapp.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(val firebaseUserId: String, val email: String?)