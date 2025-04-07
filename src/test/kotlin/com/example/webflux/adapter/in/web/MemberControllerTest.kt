package com.example.webflux.adapter.`in`.web

import com.example.webflux.application.port.`in`.LoginUseCase
import com.example.webflux.application.port.`in`.SignUpUseCase
import com.example.webflux.domain.Member
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
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

    @MockBean
    private lateinit var loginUseCase: LoginUseCase

    @Test
    fun `회원가입 성공 테스트`() {
        // given
        val request = SignUpRequest(
            email = "test@example.com",
            password = "password123",
            username = "테스트"
        )

        val member = Member(
            id = 1L,
            email = request.email,
            password = request.password,
            username = request.username,
            createdAt = LocalDateTime.now()
        )

        whenever(signUpUseCase.signUp(any())).thenReturn(Mono.just(member))

        // when & then
        webTestClient.post()
            .uri("/api/members/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.id").isEqualTo(1)
            .jsonPath("$.email").isEqualTo(request.email)
            .jsonPath("$.username").isEqualTo(request.username)
    }

    @Test
    fun `로그인 성공 테스트`() {
        // given
        val request = LoginRequest(
            email = "test@example.com",
            password = "password123"
        )

        val token = "test.jwt.token"
        whenever(loginUseCase.login(any())).thenReturn(Mono.just(token))

        // when & then
        webTestClient.post()
            .uri("/api/members/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.token").isEqualTo(token)
    }
} 