package com.example.webflux.adapter.`in`.web

import com.example.webflux.application.port.`in`.SignUpUseCase
import com.example.webflux.domain.Member
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/members")
class MemberController(
    private val signUpUseCase: SignUpUseCase
) {
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signUp(@RequestBody request: SignUpRequest): Mono<Member> {
        return signUpUseCase.signUp(request.toCommand())
    }
} 