package com.example.webflux.adapter.out.persistence

import com.example.webflux.domain.Member
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest
import org.springframework.context.annotation.Import
import reactor.test.StepVerifier
import java.time.LocalDateTime

@DataR2dbcTest
@Import(MemberPersistenceAdapter::class)
class MemberPersistenceAdapterTest {

    @Autowired
    private lateinit var memberPersistenceAdapter: MemberPersistenceAdapter

    @Test
    fun `회원 저장 및 조회 테스트`() {
        // given
        val member = Member(
            email = "test@example.com",
            password = "password123",
            username = "testuser",
            createdAt = LocalDateTime.now()
        )

        // when & then
        StepVerifier.create(memberPersistenceAdapter.save(member))
            .expectNextMatches { savedMember ->
                savedMember.id != null &&
                savedMember.email == member.email &&
                savedMember.password == member.password &&
                savedMember.username == member.username
            }
            .verifyComplete()

        StepVerifier.create(memberPersistenceAdapter.findByEmail(member.email))
            .expectNextMatches { foundMember ->
                foundMember.email == member.email &&
                foundMember.password == member.password &&
                foundMember.username == member.username
            }
            .verifyComplete()
    }

    @Test
    fun `존재하지 않는 이메일로 회원 조회 테스트`() {
        // when & then
        StepVerifier.create(memberPersistenceAdapter.findByEmail("nonexistent@example.com"))
            .verifyComplete()
    }
} 