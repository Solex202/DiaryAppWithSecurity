package com.technophiles.Diaryapppro.security.jwt;

import com.technophiles.Diaryapppro.service.UserService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TokenProviderImpl implements TokenProvider {

    //TODO move jwt props to configServer

    private final int ACCESS_TOKEN_VALIDITY = 9 * 3_600_000;//9hrs

    private  static Long TOKEN_VALIDITY_PERIOD = (long)(24 * 10 * 3600);

    private String SIGNING_KEY = System.getenv("SECRET_KEY");

    private String AUTHORITIES_KEY = System.getenv("AUTHORITY_KEY");


//    @Autowired
//    private TokenRepository tokenRepository;

    @Autowired
    private UserService userService;

    @Override
    public String getUsernameFromJWTToken(String token) {
        return getClaimFromJWTToken(token, Claims::getSubject);
    }

    @Override
    public Date getExpirationDateFromJWTToken(String token) {
        return getClaimFromJWTToken(token, Claims::getExpiration);
    }

    @Override
    public <T> T getClaimFromJWTToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromJWTToken(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public Header<?> getHeaderFromJWTToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getHeader();
    }

    @Override
    public Claims getAllClaimsFromJWTToken(String token) {
        return Jwts.parser()
                .setSigningKey(SIGNING_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Boolean isJWTTokenExpired(String token) {
        final Date expirationDate = getExpirationDateFromJWTToken(token);
        return expirationDate.before(new Date());
    }

    @Override
    public String generateJWTToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY_PERIOD * 1000))
                .signWith(SignatureAlgorithm.HS256, SIGNING_KEY)
                .compact();
    }

    @Override
    public Boolean validateJWTToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromJWTToken(token);
        return (username.equals(userDetails.getUsername()) && !isJWTTokenExpired(token));
    }

    @Override
    public UsernamePasswordAuthenticationToken getAuthenticationToken(final String token, final Authentication existingAuth,
                                                                      final UserDetails userDetails) {
        final JwtParser jwtParser = Jwts.parser().setSigningKey(SIGNING_KEY);
        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        final Claims claims = claimsJws.getBody();

        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

}
