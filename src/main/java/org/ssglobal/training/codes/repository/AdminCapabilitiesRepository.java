package org.ssglobal.training.codes.repository;

import java.time.LocalDate;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Row;
import org.jooq.Row14;
import org.jooq.Row3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.model.UserAndAdmin;
import org.ssglobal.training.codes.tables.pojos.Admin;
import org.ssglobal.training.codes.tables.pojos.Users;

@Repository
public class AdminCapabilitiesRepository {

	
	@Autowired
	private DSLContext dslContext;
	
	private final org.ssglobal.training.codes.tables.Users USERS = org.ssglobal.training.codes.tables.Users.USERS;
	private final org.ssglobal.training.codes.tables.Admin ADMIN = org.ssglobal.training.codes.tables.Admin.ADMIN;

	
	public UserAndAdmin createOTP(UserAndAdmin userAdmin) {
		Row14<Integer, String, String, String, String, String, String, LocalDate, String, String, String, String, Boolean, String> 
									   insertedUser =  dslContext.insertInto(USERS)
																 .set(USERS.USERNAME, userAdmin.getUsername())
																 .set(USERS.PASSWORD, userAdmin.getPassword())
																 .set(USERS.FIRST_NAME, userAdmin.getFirstName())
																 .set(USERS.MIDDLE_NAME, userAdmin.getMiddleName())
																 .set(USERS.LAST_NAME, userAdmin.getLastName())
																 .set(USERS.BIRTH_DATE, userAdmin.getBirthDate())
																 .set(USERS.ADDRESS, userAdmin.getAddress())
																 .set(USERS.CIVIL_STATUS, userAdmin.getCivilStatus())
																 .set(USERS.GENDER, userAdmin.getGender())
																 .set(USERS.NATIONALITY, userAdmin.getNationality())
																 .set(USERS.ACTIVE_DEACTIVE, userAdmin.getActiveDeactive())
																 .set(USERS.IMAGE, userAdmin.getImage())
																 .returning()
																 .fetchOne()
																 .valuesRow();
		
		 Row3<Integer, Integer, Integer> insertedAdmin = dslContext.insertInto(ADMIN)
																  .set(ADMIN.USER_ID, insertedUser.field1())
																  .returning()
																  .fetchOne()
																  .valuesRow();
//		if (insertedUser != null && insertedAdmin != null) {
//			UserAndAdmin newUserAdmin = new UserAndAdmin(insertedUser.field1(), insertedUser.field2(), insertedUser.field3(), insertedUser.field4(), 
//														 insertedUser.field5(), insertedUser.field6(), insertedUser.field7(), insertedUser.field8(), 
//														 insertedUser.field9(), insertedUser.field10(), insertedUser.field11(), insertedUser.field13(), 
//														 insertedUser.field14(), insertedAdmin.field1(), insertedAdmin.field3());
//		}
		 return null;
	}

}
