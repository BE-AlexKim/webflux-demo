package com.example.webflux.domain

import java.time.LocalDateTime

data class Member(
    val id: Long? = null,
    val email: String,
    val password: String,
    val username: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
) 