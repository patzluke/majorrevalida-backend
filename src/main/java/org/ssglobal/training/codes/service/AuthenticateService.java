package org.ssglobal.training.codes.service;

import org.ssglobal.training.codes.tables.pojos.Users;

public interface AuthenticateService {
	 Users searchUserByUsernameAndPassword(String username, String password);
}
