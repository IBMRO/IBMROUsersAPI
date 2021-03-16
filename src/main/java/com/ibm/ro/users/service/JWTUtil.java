package com.ibm.ro.users.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

@Service
public class JWTUtil {

	@Autowired
	Environment environment;

	public Map<String, String> doGenerateRefreshToken(Object claimsObj) {
		String tokenSecret = environment.getProperty("token.secret");
		String tokenExpirationTime = environment.getProperty("token.expiration_time");
		Map<String, String> outMap = new HashMap<String, String>();
		Map<String, Object> claims = getMapFromIoJsonwebtokenClaims((DefaultClaims) claimsObj);
		String token = Jwts.builder().setClaims(claims).setSubject(claims.get("sub").toString())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(tokenExpirationTime)))
				.signWith(SignatureAlgorithm.HS512, tokenSecret).compact();
		outMap.put("token", token);
		outMap.put("userId", claims.get("sub").toString());
		return outMap;

	}

	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}
}
