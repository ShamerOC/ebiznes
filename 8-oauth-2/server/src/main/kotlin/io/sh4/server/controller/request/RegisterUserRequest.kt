package io.sh4.server.controller.request


data class RegisterUserRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)

