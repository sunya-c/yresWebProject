package com.sunya.yresWebProject.accountSecurity.JWT;

import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.Date;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.sunya.yresWebProject.PrintError;

import io.jsonwebtoken.Jwts;

@Service
public class ServiceJWT
{
	private SecretKey skey;
	/**
	 * Age of the token in minutes
	 */
	public static final int tokenAge = 90;
	public static final int renewAt = 60;
	
	public ServiceJWT() throws NoSuchAlgorithmException
	{
		KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
		skey = keyGen.generateKey();
		System.err.println("SecretKey="+skey.getAlgorithm()+" => "+new String(skey.getEncoded()));
	}

	public boolean validateToken(String token)
	{
		try
		{
			return Jwts.parser()
				.verifyWith(skey)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getExpiration().after(new Date());
		}
		catch (Exception e)
		{
			PrintError.println(e.toString());
		}
		return false;
	}

	public String getUsername(String token)
	{
		String username = Jwts.parser()
			.verifyWith(skey)
			.build().parseSignedClaims(token)
			.getPayload()
			.getSubject();
		return username;
	}

	public String generateToken(String username)
	{
		String token = Jwts.builder()
			.subject(username)
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + Duration.ofMinutes(tokenAge).toMillis()))
			.signWith(skey, Jwts.SIG.HS256)
			.compact();
		System.err.println("token="+token);
		return token;
	}
	
	public boolean almostExpire(String token)
	{
		boolean almostExpire;
		try
		{
			almostExpire = Jwts.parser()
				.verifyWith(skey)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getExpiration().before(new Date(System.currentTimeMillis() + Duration.ofMinutes(tokenAge-renewAt).toMillis()));
		}
		catch (Exception e)
		{
			almostExpire = true;
		}
		System.err.println("almostExpire="+almostExpire);
		return almostExpire;
	}
}
