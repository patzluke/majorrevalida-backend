package org.ssglobal.training.codes.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.model.UserAndAdmin;
import org.ssglobal.training.codes.model.UserAndParent;
import org.ssglobal.training.codes.model.UserAndStudent;

@Repository
public class AuthenticateRepository {

	private final org.ssglobal.training.codes.tables.Users USERS = org.ssglobal.training.codes.tables.Users.USERS;
	private final org.ssglobal.training.codes.tables.Admin ADMIN = org.ssglobal.training.codes.tables.Admin.ADMIN;
	private final org.ssglobal.training.codes.tables.Parent PARENT = org.ssglobal.training.codes.tables.Parent.PARENT;
	private final org.ssglobal.training.codes.tables.Student STUDENT = org.ssglobal.training.codes.tables.Student.STUDENT;

	
	@Autowired
	private DSLContext dslContext;
		
	public Object searchUserByUsernameAndPassword(String username, String password) {		  
		UserAndAdmin adminUser = dslContext.select(USERS.USER_ID, USERS.USERNAME, USERS.USER_TYPE, USERS.ACTIVE_DEACTIVE, USERS.IMAGE, ADMIN.ADMIN_NO)
				  .from(USERS).innerJoin(ADMIN).on(USERS.USER_ID.eq(ADMIN.USER_ID))
				  .where(USERS.USERNAME.eq(username).and(USERS.PASSWORD.eq(password)))
				  .fetchOneInto(UserAndAdmin.class);
		
		UserAndParent parentUser = dslContext.select(USERS.USER_ID, USERS.USERNAME, USERS.USER_TYPE, USERS.ACTIVE_DEACTIVE, USERS.IMAGE, PARENT.PARENT_NO)
				  .from(USERS).innerJoin(PARENT).on(USERS.USER_ID.eq(PARENT.USER_ID))
				  .where(USERS.USERNAME.eq(username).and(USERS.PASSWORD.eq(password)))
				  .fetchOneInto(UserAndParent.class);
		
		UserAndStudent studentUser = dslContext.select(USERS.USER_ID, USERS.USERNAME, USERS.USER_TYPE, USERS.ACTIVE_DEACTIVE, USERS.IMAGE, STUDENT.STUDENT_NO)
				  .from(USERS).innerJoin(STUDENT).on(USERS.USER_ID.eq(STUDENT.USER_ID))
				  .where(USERS.USERNAME.eq(username).and(USERS.PASSWORD.eq(password)))
				  .fetchOneInto(UserAndStudent.class);
		return adminUser != null ? adminUser : parentUser != null ? parentUser : studentUser != null ? studentUser : null;
	}
}
