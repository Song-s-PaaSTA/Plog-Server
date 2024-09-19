package com.songspasssta.memberservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class BlackList {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "invalid_refresh_token")
    private String invalidRefreshToken;

    public BlackList(final String invalidRefreshToken) {
        this.invalidRefreshToken = invalidRefreshToken;
    }
}