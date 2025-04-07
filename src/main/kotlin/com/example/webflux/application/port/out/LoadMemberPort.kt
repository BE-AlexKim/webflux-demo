package com.example.webflux.application.port.out

import com.example.webflux.domain.Member
import reactor.core.publisher.Mono

interface LoadMemberPort {
    fun findByEmail(email: String): Mono<Member>
} 