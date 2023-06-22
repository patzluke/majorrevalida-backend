package org.ssglobal.training.codes.repository;

import org.jooq.DSLContext;
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

	
	//------------------------FOR ADMIN
	public UserAndAdmin insertAdminUser(UserAndAdmin userAdmin) {
		Users insertedUser =  dslContext.insertInto(USERS)
									    .set(USERS.USERNAME, userAdmin.getUsername())
										.set(USERS.PASSWORD, userAdmin.getPassword())
										.set(USERS.FIRST_NAME, userAdmin.getFirstName())
										.set(USERS.MIDDLE_NAME, userAdmin.getMiddleName())
										.set(USERS.LAST_NAME, userAdmin.getLastName())
										.set(USERS.USER_TYPE, userAdmin.getUserType())
										.set(USERS.BIRTH_DATE, userAdmin.getBirthDate())
										.set(USERS.ADDRESS, userAdmin.getAddress())
										.set(USERS.CIVIL_STATUS, userAdmin.getCivilStatus())
										.set(USERS.GENDER, userAdmin.getGender())
										.set(USERS.NATIONALITY, userAdmin.getNationality())
										.set(USERS.ACTIVE_DEACTIVE, userAdmin.getActiveDeactive())
										.set(USERS.IMAGE, userAdmin.getImage())
										.returning()
										.fetchOne()
										.into(Users.class);
		
		 Admin insertedAdmin = dslContext.insertInto(ADMIN)
										.set(ADMIN.USER_ID, insertedUser.getUserId())
										.returning()
										.fetchOne()
										.into(Admin.class);
																  
		if (insertedUser != null && insertedAdmin != null) {
			UserAndAdmin newUserAdmin = new UserAndAdmin(insertedUser.getUserId(), insertedUser.getUsername(), insertedUser.getPassword(), 
														 insertedUser.getFirstName(), insertedUser.getMiddleName(), insertedUser.getLastName(), 
														 insertedUser.getUserType(), insertedUser.getBirthDate(), insertedUser.getAddress(), 
														 insertedUser.getCivilStatus(), insertedUser.getGender(), insertedUser.getNationality(), 
														 insertedUser.getActiveDeactive(), insertedUser.getImage(), insertedAdmin.getAdminId(), 
														 insertedAdmin.getAdminNo());
			return newUserAdmin;
		}
		
		return null;
	}
	
	public UserAndAdmin updateAdminUser(UserAndAdmin userAdmin) {
		Users updatedUser =  dslContext.update(USERS)
									    .set(USERS.USERNAME, userAdmin.getUsername())
										.set(USERS.PASSWORD, userAdmin.getPassword())
										.set(USERS.FIRST_NAME, userAdmin.getFirstName())
										.set(USERS.MIDDLE_NAME, userAdmin.getMiddleName())
										.set(USERS.LAST_NAME, userAdmin.getLastName())
										.set(USERS.USER_TYPE, userAdmin.getUserType())
										.set(USERS.BIRTH_DATE, userAdmin.getBirthDate())
										.set(USERS.ADDRESS, userAdmin.getAddress())
										.set(USERS.CIVIL_STATUS, userAdmin.getCivilStatus())
										.set(USERS.GENDER, userAdmin.getGender())
										.set(USERS.NATIONALITY, userAdmin.getNationality())
										.set(USERS.ACTIVE_DEACTIVE, userAdmin.getActiveDeactive())
										.set(USERS.IMAGE, userAdmin.getImage())
										.where(USERS.USER_ID.eq(userAdmin.getUserId()))
										.returning()
										.fetchOne()
										.into(Users.class);
																  
		if (updatedUser != null) {
			UserAndAdmin newUserAdmin = new UserAndAdmin(updatedUser.getUserId(), updatedUser.getUsername(), updatedUser.getPassword(), 
														updatedUser.getFirstName(), updatedUser.getMiddleName(), updatedUser.getLastName(), 
														updatedUser.getUserType(), updatedUser.getBirthDate(), updatedUser.getAddress(), 
														updatedUser.getCivilStatus(), updatedUser.getGender(), updatedUser.getNationality(), 
														updatedUser.getActiveDeactive(), updatedUser.getImage(), userAdmin.getAdminId(), 
														userAdmin.getAdminNo());
			return newUserAdmin;
		}
		
		return null;
	}
	
	public UserAndAdmin deleteAdminUser(Integer userAdminId) {
		Users deletedUser =  dslContext.update(USERS)
										.set(USERS.ACTIVE_DEACTIVE, false)
										.where(USERS.USER_ID.eq(userAdminId))
										.returning()
										.fetchOne()
										.into(Users.class);
		
		Admin deletedAdmin = dslContext.selectFrom(ADMIN)
				.where(ADMIN.USER_ID.eq(deletedUser.getUserId()))
				.fetchOne()
				.into(Admin.class);												
		
		if (deletedUser != null && deletedAdmin != null) {
			UserAndAdmin newUserAdmin = new UserAndAdmin(deletedUser.getUserId(), deletedUser.getUsername(), deletedUser.getPassword(), 
														deletedUser.getFirstName(), deletedUser.getMiddleName(), deletedUser.getLastName(), 
														deletedUser.getUserType(), deletedUser.getBirthDate(), deletedUser.getAddress(), 
														deletedUser.getCivilStatus(), deletedUser.getGender(), deletedUser.getNationality(), 
														deletedUser.getActiveDeactive(), deletedUser.getImage(), deletedAdmin.getAdminId(), 
														deletedAdmin.getAdminNo());
			return newUserAdmin;
		}
		
		return null;
	}
	//------------------------FOR STUDENTS
}
