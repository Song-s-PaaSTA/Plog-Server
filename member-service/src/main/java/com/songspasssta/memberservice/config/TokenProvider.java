package com.songspasssta.memberservice.config;

import com.songspasssta.common.exception.ExpiredPeriodJwtException;
import com.songspasssta.common.exception.InvalidJwtException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

import static com.songspasssta.common.exception.ExceptionCode.*;

@Service
public class TokenProvider {

    public static final String EMPTY_SUBJECT = "";
    private final Key secretKey;
    private final Long accessExpirationTime;
    private final Long refreshExpirationTime;

    public TokenProvider(
            @Value("${jwt.secret-key}") final String secretKey,
            @Value("${jwt.access-expiration-time}") final Long accessExpirationTime,
            @Value("${jwt.refresh-expiration-time}") final Long refreshExpirationTime
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.accessExpirationTime = accessExpirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
    }

    public String generateAccessToken(final String subject) {
        final String accessToken = createToken(subject, accessExpirationTime);
        return accessToken;
    }

    public String generateRefreshToken() {
        final String refreshToken = createToken(EMPTY_SUBJECT, refreshExpirationTime);
        return refreshToken;
    }

    private String createToken(final String subject, final Long expiryInMilliseconds) {
        final Date now = new Date();
        final Date expiry = new Date(now.getTime() + expiryInMilliseconds);

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(subject)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void validateRefreshToken(final String refreshToken) {
        try {
            getClaims(refreshToken);
        } catch (final ExpiredJwtException e) {
            throw new ExpiredPeriodJwtException(EXPIRED_REFRESH_TOKEN);
        } catch (final JwtException | IllegalArgumentException e) {
            throw new InvalidJwtException(INVALID_REFRESH_TOKEN);
        }
    }

    private void validateAccessToken(final String accessToken) {
        try {
            getClaims(accessToken);
        } catch (final ExpiredJwtException e) {
            throw new ExpiredPeriodJwtException(EXPIRED_ACCESS_TOKEN);
        } catch (final JwtException | IllegalArgumentException e) {
            throw new InvalidJwtException(INVALID_ACCESS_TOKEN);
        }
    }

    public boolean isValidRefreshAndInvalidAccess(final String refreshToken, final String accessToken) {
        validateRefreshToken(refreshToken);
        try {
            validateAccessToken(accessToken);
        } catch (final ExpiredPeriodJwtException e) {
            return true;
        }
        return false;
    }

    public boolean isValidRefreshAndValidAccess(final String refreshToken, final String accessToken) {
        try {
            validateRefreshToken(refreshToken);
            validateAccessToken(accessToken);
            return true;
        } catch (final JwtException e) {
            return false;
        }
    }

    public String getSubject(final String token) {
        return getClaims(token)
                .getBody()
                .getSubject();
    }

    private Jws<Claims> getClaims(final String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
    }
}