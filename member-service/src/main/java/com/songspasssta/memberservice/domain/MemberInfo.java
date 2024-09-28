package com.songspasssta.memberservice.domain;

import lombok.Getter;

@Getter
public class MemberInfo {

    private final Member member;
    private final Boolean isNewMember;

    public MemberInfo(
            final Member member,
            final Boolean isNewMember
    ) {
        this.member = member;
        this.isNewMember = isNewMember;
    }
}
