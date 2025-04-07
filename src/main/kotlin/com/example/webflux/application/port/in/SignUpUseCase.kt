package com.example.webflux.application.port.`in`

import com.example.webflux.domain.Member
import reactor.core.publisher.Mono

interface SignUpUseCase {
    fun signUp(command: SignUpCommand): Mono<Member>
}

data class SignUpCommand(
    val email: String,
    val password: String,
    val username: String
) 