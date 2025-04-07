package com.example.webflux.application.port.out

import com.example.webflux.domain.Member
import reactor.core.publisher.Mono

interface SaveMemberPort {
    fun save(member: Member): Mono<Member>
} 