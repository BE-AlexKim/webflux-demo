package com.example.webflux.adapter.`in`.web

import com.example.webflux.application.port.`in`.LoginCommand

data class LoginRequest(
    val email: String,
    val password: String
) {
    fun toCommand(): LoginCommand =
        LoginCommand(
            email = email,
            password = password
        )
} 