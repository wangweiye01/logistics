package com.yatai.filter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

	// 由字符串生成加密key
	public static SecretKey generalKey() {
		String stringKey = "fsdf7o8hifdjskh1";
		byte[] encodedKey = Base64.decodeBase64(stringKey);
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return key;
	}

	// 创建jwt
	public static String createJWT(String id, String subject, long ttlMillis) throws Exception {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		SecretKey key = generalKey();
		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).signWith(signatureAlgorithm,
				key);
		if (ttlMillis >= 0) {
			long expMillis = nowMillis + ttlMillis;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp);
		}
		return builder.compact();
	}

	// 解密jwt
	public static Claims parseJWT(String jwt) throws Exception {
		SecretKey key = generalKey();
		Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
		return claims;
	}

	// 生成subject信息
	public static String generalSubject() {
		Gson gson = new Gson();
		Map<String, Integer> subject = new HashMap<String, Integer>();
		subject.put("userId", 110);
		subject.put("roleId", 120);
		return gson.toJson(subject);
	}
	
	public static void main(String[] args) throws Exception {
		String subject = JwtUtil.generalSubject();
		String s = JwtUtil.createJWT("11", subject, 2000 * 90L);
		System.out.println(s);
		
		System.out.println(JwtUtil.parseJWT(s));
	}
}