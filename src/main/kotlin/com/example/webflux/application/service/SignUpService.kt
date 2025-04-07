package com.example.webflux.application.service

import com.example.webflux.application.port.`in`.SignUpCommand
import com.example.webflux.application.port.`in`.SignUpUseCase
import com.example.webflux.application.port.out.LoadMemberPort
import com.example.webflux.application.port.out.SaveMemberPort
import com.example.webflux.domain.Member
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

@Service
class SignUpService(
    private val loadMemberPort: LoadMemberPort,
    private val saveMemberPort: SaveMemberPort
) : SignUpUseCase {

    override fun signUp(command: SignUpCommand): Mono<Member> {
        return loadMemberPort.findByEmail(command.email)
            .flatMap {
                Mono.error<Member>(RuntimeException("이미 존재하는 이메일입니다."))
            }
            .switchIfEmpty {
                saveMemberPort.save(
                    Member(
                        email = command.email,
                        password = command.password,
                        username = command.username
                    )
                )
            }
    }
} 