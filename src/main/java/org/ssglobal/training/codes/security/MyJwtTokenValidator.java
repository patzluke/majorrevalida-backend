package org.ssglobal.training.codes.security;

import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.ssglobal.training.codes.repository.AuthenticateRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@RequiredArgsConstructor
@Log4j2
public class MyJwtTokenValidator extends OncePerRequestFilter {
	
	@Autowired
	private final AuthenticateRepository userTokenRepository;
	
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	@Autowired
	private HttpServletResponse httpServletResponse;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			String token = authorizationHeader.substring("Bearer".length()).trim();
			if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer")) {
				response.sendError(HttpStatus.UNAUTHORIZED.value());
			}
			if (!validateToken(token)) {
				response.sendError(HttpStatus.UNAUTHORIZED.value());
			}
			filterChain.doFilter(request, response);
		} catch (NullPointerException e) {
			response.sendError(HttpStatus.UNAUTHORIZED.value());
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
		}
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
			   //for authenticate
		return request.getRequestURI().matches("/api/authenticate") ||
			   //for admin api
			   request.getRequestURI().matches("/api/admin/update/password") ||
			   //for studentapplicant api
			   request.getRequestURI().matches("/api/studentapplicant/.*") ||
			   //for studentapplicant api
			   request.getRequestURI().matches("/api/file/.*") ||
			   
			   request.getRequestURI().matches("/api/forgot-password/.*")
			   ;
	}
	
	@SuppressWarnings("unchecked")
	private boolean validateToken(String token) {
		try {
			String[] chunks = token.split("\\.");
			Decoder decoder = Base64.getUrlDecoder();			
			String payload = new String(decoder.decode(chunks[1]));
			HashMap<String,Object> result = new ObjectMapper().readValue(payload, HashMap.class);
			
			Date tokenExpiresAt = new Date(Long.parseLong(result.get("exp").toString()) * 1000L);
			int userId = Integer.parseInt(result.get("userId").toString());
			if (new Date().getTime() < tokenExpiresAt.getTime() && userTokenRepository.isUserTokenExists(userId, token)) {
				return true;
			} else if (new Date().getTime() > tokenExpiresAt.getTime()){
				userTokenRepository.deleteUserToken(userId);
			}
		} catch (JsonMappingException e) {
			log.debug("MyJwtTokenValidator Line 61 exception: %s".formatted(e.getMessage()));
		} catch (JsonProcessingException e) {
			log.debug("MyJwtTokenValidator Line 63 exception: %s".formatted(e.getMessage()));
		} catch (ArrayIndexOutOfBoundsException e) {
			log.debug("MyJwtTokenValidator Line 65 exception: %s".formatted(e.getMessage()));
		} catch (Exception e) {
			log.debug("MyJwtTokenValidator Line 67 exception: %s".formatted(e.getMessage()));
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean validateAdminUser() throws IOException {
		if (httpServletRequest.getRequestURI().equals("/api/admin/update/password")) {
			return true;
		}
		try {
			String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
			String token = authorizationHeader.substring("Bearer".length()).trim();
			String[] chunks = token.split("\\.");
			Decoder decoder = Base64.getUrlDecoder();			
			String payload = new String(decoder.decode(chunks[1]));
			HashMap<String,Object> result = new ObjectMapper().readValue(payload, HashMap.class);
			if (result.get("userType").equals("Admin")) {
				return true;
			}
		} catch (NullPointerException e) {
			httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(), "not ok");
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean validateProfessorUser() throws IOException {
		try {
			String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
			String token = authorizationHeader.substring("Bearer".length()).trim();
			String[] chunks = token.split("\\.");
			Decoder decoder = Base64.getUrlDecoder();			
			String payload = new String(decoder.decode(chunks[1]));
			HashMap<String,Object> result = new ObjectMapper().readValue(payload, HashMap.class);
			if (result.get("userType").equals("Professor")) {
				return true;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}			
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean validateStudentUser() throws ServletException, IOException {
		try {
			String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
			String token = authorizationHeader.substring("Bearer".length()).trim();
			String[] chunks = token.split("\\.");
			Decoder decoder = Base64.getUrlDecoder();			
			String payload = new String(decoder.decode(chunks[1]));
			HashMap<String,Object> result = new ObjectMapper().readValue(payload, HashMap.class);
			if (result.get("userType").equals("Student")) {
				return true;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean validateParentUser() throws IOException {
		try {
			String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
			String token = authorizationHeader.substring("Bearer".length()).trim();
			String[] chunks = token.split("\\.");
			Decoder decoder = Base64.getUrlDecoder();			
			String payload = new String(decoder.decode(chunks[1]));
			HashMap<String,Object> result = new ObjectMapper().readValue(payload, HashMap.class);
			if (result.get("userType").equals("Parent")) {
				return true;
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public boolean validateFileApiRequests() throws IOException {
		if (httpServletRequest.getRequestURI().equals("/api/admin/update/password")) {
			return true;
		}
		try {
			String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
			String token = authorizationHeader.substring("Bearer".length()).trim();
			String[] chunks = token.split("\\.");
			Decoder decoder = Base64.getUrlDecoder();			
			String payload = new String(decoder.decode(chunks[1]));
			HashMap<String,Object> result = new ObjectMapper().readValue(payload, HashMap.class);
			if (result.get("userType").equals("Admin") || result.get("userType").equals("Student") ||
				result.get("userType").equals("Professor") || result.get("userType").equals("Parent")) {
				return true;
			}
		} catch (NullPointerException e) {
			httpServletResponse.sendError(HttpStatus.NOT_FOUND.value(), "not ok");
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}		
		return false;
	}
}
