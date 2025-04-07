package com.example.webflux.adapter.out.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("members")
data class MemberEntity(
    @Id
    val id: Long? = null,
    val email: String,
    val password: String,
    val username: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
) 