package com.honoursigbeku.studyhubapp.data.datasource.remote.mapper

import com.honoursigbeku.studyhubapp.data.datasource.remote.dto.UserDto
import com.honoursigbeku.studyhubapp.domain.model.User

fun User.toDto(): UserDto {
    return UserDto(firebaseUserId = this.firebaseUserId, email = this.email)
}

fun UserDto.toDomain(): User {
    return User(firebaseUserId = this.firebaseUserId, email = this.email)
}