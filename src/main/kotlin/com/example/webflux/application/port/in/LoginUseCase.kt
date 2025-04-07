package com.example.webflux.application.port.`in`

import com.example.webflux.domain.Member
import reactor.core.publisher.Mono

interface LoginUseCase {
    fun login(command: LoginCommand): Mono<String>  // JWT 토큰을 반환
}

data class LoginCommand(
    val email: String,
    val password: String
) 