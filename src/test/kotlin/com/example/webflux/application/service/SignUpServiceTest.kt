package com.example.webflux.application.service

import com.example.webflux.application.port.`in`.SignUpCommand
import com.example.webflux.application.port.out.LoadMemberPort
import com.example.webflux.application.port.out.SaveMemberPort
import com.example.webflux.domain.Member
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class SignUpServiceTest {

    @Mock
    private lateinit var loadMemberPort: LoadMemberPort

    @Mock
    private lateinit var saveMemberPort: SaveMemberPort

    private lateinit var signUpService: SignUpService

    @BeforeEach
    fun setUp() {
        signUpService = SignUpService(loadMemberPort, saveMemberPort)
    }

    @Test
    fun `회원가입 성공 테스트`() {
        // given
        val command = SignUpCommand(
            email = "test@example.com",
            password = "password123",
            username = "testuser"
        )

        val savedMember = Member(
            id = 1L,
            email = command.email,
            password = command.password,
            username = command.username,
            createdAt = LocalDateTime.now()
        )

        whenever(loadMemberPort.findByEmail(command.email)).thenReturn(Mono.empty())
        whenever(saveMemberPort.save(any())).thenReturn(Mono.just(savedMember))

        // when & then
        StepVerifier.create(signUpService.signUp(command))
            .expectNext(savedMember)
            .verifyComplete()
    }

    @Test
    fun `이미 존재하는 이메일로 회원가입 시도 시 실패 테스트`() {
        // given
        val command = SignUpCommand(
            email = "test@example.com",
            password = "password123",
            username = "testuser"
        )

        val existingMember = Member(
            id = 1L,
            email = command.email,
            password = "other_password",
            username = "other_user",
            createdAt = LocalDateTime.now()
        )

        whenever(loadMemberPort.findByEmail(command.email)).thenReturn(Mono.just(existingMember))

        // when & then
        StepVerifier.create(signUpService.signUp(command))
            .expectError(RuntimeException::class.java)
            .verify()
    }
} 