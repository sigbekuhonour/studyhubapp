package com.example.studyhubapp.domain.model

import android.net.Uri


sealed class User {
    data class GoogleUser(
        val id: String,
        val name: String?,
        val phoneNumber: String?,
        val profilePictureUri: Uri?
    ) : User()

    data class EmailUser(
        val id: String,
        val name: String?,
        val email: String?,
    ) : User()
}
