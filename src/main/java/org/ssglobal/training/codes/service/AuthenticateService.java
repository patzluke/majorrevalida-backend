package org.ssglobal.training.codes.service;


public interface AuthenticateService {
	
	Object searchUserByUsernameAndPassword(String username, String password);
}
