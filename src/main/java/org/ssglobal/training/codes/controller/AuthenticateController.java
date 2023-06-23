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
		Object authenticatedUser = service.searchUserByUsernameAndPassword(username, password);
		if (authenticatedUser != null) {
			List<Object> usertoken = new ArrayList<>();
//			String token = userService
//					.generateToken(Integer.valueOf(authenticatedUser.get("employeeId").toString()), 
//												   authenticatedUser.get("username").toString(), 
//												   authenticatedUser.get("userType").toString());
//			usertoken.add(token);
			System.out.println(authenticatedUser);
			return new ResponseEntity<>(usertoken, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
	}
}
