package com.example.webflux.adapter.out.persistence

import com.example.webflux.domain.Member

object MemberMapper {
    fun toEntity(member: Member): MemberEntity =
        MemberEntity(
            id = member.id,
            email = member.email,
            password = member.password,
            username = member.username,
            createdAt = member.createdAt
        )

    fun toDomain(entity: MemberEntity): Member =
        Member(
            id = entity.id,
            email = entity.email,
            password = entity.password,
            username = entity.username,
            createdAt = entity.createdAt
        )
} 