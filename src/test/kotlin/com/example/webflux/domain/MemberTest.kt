package com.example.webflux.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class MemberTest {

    @Test
    fun `Member 객체 생성 테스트`() {
        // given
        val email = "test@example.com"
        val password = "password123"
        val username = "testuser"
        val now = LocalDateTime.now()

        // when
        val member = Member(
            email = email,
            password = password,
            username = username,
            createdAt = now
        )

        // then
        assertThat(member.email).isEqualTo(email)
        assertThat(member.password).isEqualTo(password)
        assertThat(member.username).isEqualTo(username)
        assertThat(member.createdAt).isEqualTo(now)
    }
} 