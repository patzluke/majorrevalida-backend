package org.ssglobal.training.codes.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repository.AuthenticateRepository;
import org.ssglobal.training.codes.service.AuthenticateService;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {

	@Autowired
	private AuthenticateRepository repository;

	@Override
	public Object searchUserByUsernameAndPassword(String username, String password) {
		Object user = repository.searchUserByUsernameAndPassword(username, password);
		return user;
	}
}
