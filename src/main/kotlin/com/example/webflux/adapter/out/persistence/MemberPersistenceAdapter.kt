package com.example.webflux.adapter.out.persistence

import com.example.webflux.application.port.out.LoadMemberPort
import com.example.webflux.application.port.out.SaveMemberPort
import com.example.webflux.domain.Member
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class MemberPersistenceAdapter(
    private val memberRepository: MemberRepository
) : LoadMemberPort, SaveMemberPort {

    override fun findByEmail(email: String): Mono<Member> {
        return memberRepository.findByEmail(email)
            .map { MemberMapper.toDomain(it) }
    }

    override fun save(member: Member): Mono<Member> {
        return memberRepository.save(MemberMapper.toEntity(member))
            .map { MemberMapper.toDomain(it) }
    }
} 