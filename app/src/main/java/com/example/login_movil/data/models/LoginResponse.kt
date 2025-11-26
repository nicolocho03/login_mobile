package com.example.login_movil.data.models

data class LoginResponse(
    val success: Boolean,
    val token: String?,
    val message: String?,
    val user: User?
)

data class User(
    val id: Int,
    val email: String,
    val name: String?
)
