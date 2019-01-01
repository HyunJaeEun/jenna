package com.jenna.shared;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.jenna.security.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class Utils { //모든 유틸리티 메소드 집합 

	//public userId  --아무도 추측못하게..
	private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    
    public String generateUserId(int length) {
        return generateRandomString(length);
    }
    
    
    private String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }

        return new String(returnValue);
    }
    
    public static boolean hasTokenExpired(String token) {
        boolean returnValue = false;

           Claims claims = Jwts.parser().setSigningKey(SecurityConstants.getTokenSecret()).parseClaimsJws(token)
                 .getBody();

           Date tokenExpirationDate = claims.getExpiration();
           Date todayDate = new Date();

           returnValue = tokenExpirationDate.before(todayDate);

        return returnValue;
     }
    
    public String gernerateEmailVerificationToken(String publidUserId) {
    		
    	String token = Jwts.builder()
    		    .setSubject(publidUserId)
    		    .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
    		    .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret() )
    		    .compact();
    	
    	return token;
    }
    
		    
}
