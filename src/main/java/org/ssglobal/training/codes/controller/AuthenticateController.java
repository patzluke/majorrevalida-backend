package org.ssglobal.training.codes.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.training.codes.service.AuthenticateService;

@RestController
@RequestMapping(value = "/api")
public class AuthenticateController {
	
	@Autowired
	private AuthenticateService service;

	@PostMapping(value = "/authenticate", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Object>> authenticate(@RequestBody Map<String, String> payload) {
		String username = payload.get("username");
		String password = payload.get("password");
		Map<String, Object> authenticatedUser = service.searchUserByUsernameAndPassword(username, password);
		if (authenticatedUser != null) {
			List<Object> usertoken = new ArrayList<>();
			Integer userNo = authenticatedUser.get("adminNo") != null ? Integer.valueOf(authenticatedUser.get("adminNo").toString())
							 : authenticatedUser.get("professorNo") != null ? Integer.valueOf(authenticatedUser.get("professorNo").toString())
							 : authenticatedUser.get("parentNo") != null ? Integer.valueOf(authenticatedUser.get("parentNo").toString())
							 : authenticatedUser.get("studentNo") != null ? Integer.valueOf(authenticatedUser.get("studentNo").toString())
							 : null;
			
			String token = service
					.generateToken(Integer.valueOf(authenticatedUser.get("userId").toString()),
								   				   userNo,
												   authenticatedUser.get("username").toString(), 
												   authenticatedUser.get("userType").toString(),
												   Boolean.valueOf(authenticatedUser.get("activeStatus").toString())
								  );
			
			usertoken.add(token);
			return new ResponseEntity<>(usertoken, HttpStatus.OK);
		}
		System.out.println("hey");
		return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/forgot-password/checkUsername", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity checkUsername(@RequestBody Map<String, Object> payload) {
		String username = payload.get("username").toString();
		Map<String, Object> user = service.checkUsernameByForgotPassword(username);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.badRequest().body("ERROR: Username didn't exist");
		}
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/forgot-password/change-password", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity changePassword(@RequestBody Map<String, Object> payload) {
		String username = payload.get("username").toString();
		String password = payload.get("password").toString();
		boolean user = service.changePasswordByForgotPassword(username, password);
		if (user) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.badRequest().body("ERROR: Change Password Error");
		}
	}
}
