package org.ssglobal.training.codes.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repository.AuthenticateRepository;
import org.ssglobal.training.codes.service.AuthenticateService;
import org.ssglobal.training.codes.tables.pojos.Users;


@Service
public class AuthenticateServiceImpl implements AuthenticateService {
	
	@Autowired
	private AuthenticateRepository repository;

	@Override
	public Users searchUserByUsernameAndPassword(String username, String password) {
		return repository.searchUserByUsernameAndPassword(username, password);
	}
}
