package com.example.webflux.adapter.`in`.web

import com.example.webflux.application.port.`in`.SignUpCommand

data class SignUpRequest(
    val email: String,
    val password: String,
    val username: String
) {
    fun toCommand(): SignUpCommand =
        SignUpCommand(
            email = email,
            password = password,
            username = username
        )
} 