package org.ssglobal.training.codes.repository;

import java.util.Map;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AuthenticateRepository {

	private final org.ssglobal.training.codes.tables.Users USERS = org.ssglobal.training.codes.tables.Users.USERS;
	private final org.ssglobal.training.codes.tables.Admin ADMIN = org.ssglobal.training.codes.tables.Admin.ADMIN;
	private final org.ssglobal.training.codes.tables.Parent PARENT = org.ssglobal.training.codes.tables.Parent.PARENT;
	private final org.ssglobal.training.codes.tables.Professor PROFESSOR = org.ssglobal.training.codes.tables.Professor.PROFESSOR;
	private final org.ssglobal.training.codes.tables.Student STUDENT = org.ssglobal.training.codes.tables.Student.STUDENT;
	private final org.ssglobal.training.codes.tables.UserTokens USERTOKENS = org.ssglobal.training.codes.tables.UserTokens.USER_TOKENS;

	
	@Autowired
	private DSLContext dslContext;
		
	public Map<String, Object> searchUserByUsernameAndPassword(String username) {		  
		Map<String, Object> adminUser = dslContext.select(USERS.USER_ID.as("userId"), USERS.USERNAME, USERS.PASSWORD, USERS.USER_TYPE.as("userType"), USERS.ACTIVE_STATUS.as("activeStatus"), USERS.IMAGE, ADMIN.ADMIN_NO.as("adminNo"))
				  .from(USERS).innerJoin(ADMIN).on(USERS.USER_ID.eq(ADMIN.USER_ID))
				  .where(USERS.USERNAME.eq(username))
				  .fetchOneMap();
		
		Map<String, Object> professorUser = dslContext.select(USERS.USER_ID.as("userId"), USERS.USERNAME, USERS.PASSWORD, USERS.USER_TYPE.as("userType"), USERS.ACTIVE_STATUS.as("activeStatus"), USERS.IMAGE, PROFESSOR.PROFESSOR_NO.as("professorNo"))
				  .from(USERS).innerJoin(PROFESSOR).on(USERS.USER_ID.eq(PROFESSOR.USER_ID))
				  .where(USERS.USERNAME.eq(username))
				  .fetchOneMap();
		
		Map<String, Object> parentUser = dslContext.select(USERS.USER_ID.as("userId"), USERS.USERNAME, USERS.PASSWORD, USERS.USER_TYPE.as("userType"), USERS.ACTIVE_STATUS.as("activeStatus"), USERS.IMAGE, PARENT.PARENT_NO.as("parentNo"))
				  .from(USERS).innerJoin(PARENT).on(USERS.USER_ID.eq(PARENT.USER_ID))
				  .where(USERS.USERNAME.eq(username))
				  .fetchOneMap();
		
		Map<String, Object> studentUser = dslContext.select(USERS.USER_ID.as("userId"), USERS.USERNAME, USERS.PASSWORD, USERS.USER_TYPE.as("userType"), USERS.ACTIVE_STATUS.as("activeStatus"), USERS.IMAGE, STUDENT.STUDENT_NO.as("studentNo"))
				  .from(USERS).innerJoin(STUDENT).on(USERS.USER_ID.eq(STUDENT.USER_ID))
				  .where(USERS.USERNAME.eq(username))
				  .fetchOneMap();
		return adminUser != null ? adminUser : parentUser != null ? parentUser : studentUser != null ? studentUser : professorUser != null ? professorUser : null;
	}
	
	public boolean createToken(Integer employeeId, String token) {
		return dslContext.insertInto(USERTOKENS)
						 .set(USERTOKENS.USER_ID, employeeId)
						 .set(USERTOKENS.TOKEN, token)
						 .execute() == 1 ? true : false;
	}
	
	public boolean updateUserToken(Integer employeeId, String token) {
		return dslContext.update(USERTOKENS)
				 .set(USERTOKENS.TOKEN, token)
				 .where(USERTOKENS.USER_ID.eq(employeeId))
				 .execute() == 1 ? true : false;
	}
	
	public boolean deleteUserToken(Integer userId) {
		return dslContext.deleteFrom(USERTOKENS)
						 .where(USERTOKENS.USER_ID.eq(userId))
						 .execute() == 1 ? true : false;
	}

	public boolean isUserTokenIdExists(Integer userId) {
		return dslContext.fetchExists(USERTOKENS, USERTOKENS.USER_ID.eq(userId));
	}

	public boolean isUserTokenExists(Integer userId, String token) {
		return dslContext.fetchExists(USERTOKENS, USERTOKENS.TOKEN.eq(token), USERTOKENS.USER_ID.eq(userId));
	}
}
