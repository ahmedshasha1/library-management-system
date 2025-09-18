package com.library.config.security;

import com.library.model.auth.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtTokenHandler {
    private String token;
    private Duration time;
    private JwtParser jwtParser;
    private JwtBuilder jwtBuilder;

    public JwtTokenHandler(JwtConfig jwtConfig) {
        this.token=jwtConfig.getSecret();
        this.time = jwtConfig.getTime();
        Key key = Keys.hmacShaKeyFor(token.getBytes(StandardCharsets.UTF_8));
        jwtBuilder= Jwts.builder().signWith(key);
        jwtParser=Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String createToken(User user){
        Date currentDate=new Date();
        Instant instant = currentDate.toInstant().plus(time);
        Date expireDate=Date.from(instant);

        return jwtBuilder.setSubject(user.getEmail())
                         .setIssuedAt(currentDate)
                         .setExpiration(expireDate)
                         .claim("role",user.getRole()).compact();
    }

    public boolean validToken(String token){
        if(jwtParser.isSigned(token)){
            Claims claims = jwtParser.parseClaimsJws(token).getBody();
            Date issueDate=claims.getIssuedAt();
            Date expireDate=claims.getExpiration();
            return expireDate.after(new Date()) && issueDate.before(expireDate);
        }
        return false;
    }

    public String getSubject(String token) {
        return jwtParser.parseClaimsJws(token).getBody().getSubject();
    }
}
