package com.songspasssta.memberservice.domain;

import lombok.Getter;

@Getter
public class Accessor {

    private final Long memberId;

    public Accessor(final Long memberId) {
        this.memberId = memberId;
    }

    public static Accessor member(final Long memberId) {
        return new Accessor(memberId);
    }
}