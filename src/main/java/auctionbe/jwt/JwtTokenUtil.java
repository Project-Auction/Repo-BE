package auctionbe.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    /* set private key */
    @Value("${jwt.secret}")
    private String secret;

    /* Receive any information from token we will need to the secret key */
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /* Create function to implements get all claims from token */
    public <T> T getClaimFromToken(String token , Function<Claims , T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /* Retrieve username from jwt token */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token , Claims::getSubject);
    }

    /* Retrieve expiration date from token */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token , Claims::getExpiration);
    }

    /* Check token existing */
    public boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        /* false if current date before date token */
        return expiration.before(new Date());
    }

    /* Generate token for user
     *  get UserDetail from database with username and password
     * */
    public String generateToken(UserDetails userDetails) {
        Map<String , Object> claims = new HashMap<>();
        return doGenerateToken(claims , userDetails.getUsername());
    }

    /* while generate token -
     * 1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
     * 2. Sign the JWT using the HS512 algorithm and secret key.
     * Subject u can pass any info but in the case of pass username */
    private String doGenerateToken(Map<String , Object> claims , String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512 , secret).compact();
    }

    /* Validate token */
    public boolean validateToken(String token , UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        /* get username from database */
        final String usernameDB = userDetails.getUsername();
        boolean isTokenExpired = isTokenExpired(token);

        if(!isTokenExpired) {
            return (username.equals(usernameDB) && isTokenExpired);
        }
        return false;
    }
}
