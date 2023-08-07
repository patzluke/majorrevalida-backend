package org.ssglobal.training.codes.service.Impl;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import javax.crypto.KeyGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repository.AuthenticateRepository;
import org.ssglobal.training.codes.service.AuthenticateService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticateServiceImpl implements AuthenticateService {

	@Autowired
	private AuthenticateRepository repository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public Map<String, Object> searchUserByUsernameAndPassword(String username, String password) {
		Map<String, Object> user = repository.searchUserByUsernameAndPassword(username);
		if (user != null && new BCryptPasswordEncoder().matches(password, user.get("password").toString())) {
			return user;
		}
		return null;
	}
	
	@Override
	public Map<String, Object> checkUsernameByForgotPassword(String username) {
		Map<String, Object> user = repository.checkUsernameByForgotPassword(username);
		return user;
	}
	
	@Override
	public boolean changePasswordByForgotPassword(String username, String password) {
		boolean user = repository.changePasswordByForgotPassword(username, encoder.encode(password));
		return user;
	}
	
	@Override
	public boolean createToken(Integer employeeId, String token) {
		return repository.createToken(employeeId, token);
	}

	@Override
	public boolean updateUserToken(Integer employeeId, String token) {
		return repository.updateUserToken(employeeId, token);
	}

	@Override
	public boolean deleteUserToken(Integer userId) {
		return repository.deleteUserToken(userId);
	}

	@Override
	public boolean isUserTokenIdExists(Integer userId) {
		return repository.isUserTokenIdExists(userId);
	}

	@Override
	public boolean isUserTokenExists(Integer userId, String token) {
		return repository.isUserTokenExists(userId, token);
	}
	
	@Override
	public String generateToken(Integer userId, Integer userNo, String username, String userType, Boolean isActive) {
		KeyGenerator keyGenerator = null;
		try {
			keyGenerator = KeyGenerator.getInstance("HmacSHA256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		Key key = keyGenerator.generateKey();
		String jwtToken = Jwts.builder()
							  .claim("userId", userId)
							  .claim("userNo", userNo)
							  .claim("userType", userType)
							  .claim("username", username)
							  .claim("isActive", isActive)
							  .setIssuedAt(new Date())
							  .setExpiration(Date.from(LocalDateTime.now().plusMinutes(30L).atZone(ZoneId.systemDefault()).toInstant()))
							  .signWith(key, SignatureAlgorithm.HS256)
							  .compact();
		if (repository.isUserTokenIdExists(userId)) {
			repository.deleteUserToken(userId);
		}
		repository.createToken(userId, jwtToken);
		return jwtToken;
	}
}
