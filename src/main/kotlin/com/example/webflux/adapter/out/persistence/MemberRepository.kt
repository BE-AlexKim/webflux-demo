package com.example.webflux.adapter.out.persistence

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface MemberRepository : ReactiveCrudRepository<MemberEntity, Long> {
    fun findByEmail(email: String): Mono<MemberEntity>
} 