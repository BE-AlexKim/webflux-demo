package com.example.webflux.application.service

import com.example.webflux.application.port.`in`.LoginCommand
import com.example.webflux.application.port.`in`.LoginUseCase
import com.example.webflux.application.port.out.LoadMemberPort
import com.example.webflux.domain.Member
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class LoginService(
    private val loadMemberPort: LoadMemberPort,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) : LoginUseCase {

    override fun login(command: LoginCommand): Mono<String> {
        return loadMemberPort.findByEmail(command.email)
            .flatMap { member ->
                if (passwordEncoder.matches(command.password, member.password)) {
                    Mono.just(jwtTokenProvider.createToken(member.email))
                } else {
                    Mono.error(RuntimeException("비밀번호가 일치하지 않습니다."))
                }
            }
            .switchIfEmpty {
                Mono.error(RuntimeException("존재하지 않는 이메일입니다."))
            }
    }
} 