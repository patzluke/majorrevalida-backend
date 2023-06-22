package org.ssglobal.training.codes.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.tables.pojos.Users;

@Repository
public class AuthenticateRepository {

	
	@Autowired
	private DSLContext dslContext;
	
	private final org.ssglobal.training.codes.tables.Users USERS = org.ssglobal.training.codes.tables.Users.USERS;
	
	public Users searchUserByUsernameAndPassword(String username, String password) {		  
		return dslContext.selectFrom(USERS)
						 .where(
							USERS.USERNAME.eq(username)
						    .and(USERS.PASSWORD.eq(password))
						 )
						 .fetchOneInto(Users.class);		 
	}
}
