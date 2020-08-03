package com.woowacourse.pelotonbackend.support;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.woowacourse.pelotonbackend.common.ErrorCode;
import com.woowacourse.pelotonbackend.common.exception.TokenInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {
    private final String secretKey;
    private final long validityInMilliseconds;

    public JwtTokenProvider(@Value("${secrets.jwt.token.secret-key}") final String secretKey,
        @Value("${secrets.jwt.token.expire-length}") final long validityInMilliseconds) {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public String createToken(final String subject) {
        final Claims claims = Jwts.claims().setSubject(subject);

        final Date now = new Date();
        final Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public String getSubject(final String token) {
        return validateToken(token).getBody().getSubject();
    }

    private Jws<Claims> validateToken(final String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        } catch (MalformedJwtException | SignatureException | IllegalArgumentException| UnsupportedJwtException e) {
            throw new TokenInvalidException(ErrorCode.INVALID_TOKEN, "Token is Invalid. Login again.");
        } catch (ExpiredJwtException e) {
            throw new TokenInvalidException(ErrorCode.TOKEN_EXPIRED, "Token is expired. Login again.");
        }
    }
}
