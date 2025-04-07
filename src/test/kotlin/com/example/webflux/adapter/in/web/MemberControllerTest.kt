package com.example.webflux.adapter.`in`.web

import com.example.webflux.application.port.`in`.SignUpCommand
import com.example.webflux.application.port.`in`.SignUpUseCase
import com.example.webflux.domain.Member
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@WebFluxTest(MemberController::class)
class MemberControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var signUpUseCase: SignUpUseCase

    @Test
    fun `회원가입 API 성공 테스트`() {
        // given
        val request = SignUpRequest(
            email = "test@example.com",
            password = "password123",
            username = "testuser"
        )

        val command = request.toCommand()

        val savedMember = Member(
            id = 1L,
            email = command.email,
            password = command.password,
            username = command.username,
            createdAt = LocalDateTime.now()
        )

        `when`(signUpUseCase.signUp(command)).thenReturn(Mono.just(savedMember))

        // when & then
        webTestClient.post()
            .uri("/api/members/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.id").isEqualTo(savedMember.id)
            .jsonPath("$.email").isEqualTo(savedMember.email)
            .jsonPath("$.username").isEqualTo(savedMember.username)
    }

    @Test
    fun `회원가입 API 실패 테스트 - 이미 존재하는 이메일`() {
        // given
        val request = SignUpRequest(
            email = "test@example.com",
            password = "password123",
            username = "testuser"
        )

        val command = request.toCommand()

        `when`(signUpUseCase.signUp(command))
            .thenReturn(Mono.error(RuntimeException("이미 존재하는 이메일입니다.")))

        // when & then
        webTestClient.post()
            .uri("/api/members/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().is5xxServerError
    }
} 