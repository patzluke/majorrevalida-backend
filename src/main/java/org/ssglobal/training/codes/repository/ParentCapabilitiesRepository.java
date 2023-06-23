package org.ssglobal.training.codes.repository;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.model.UserAndParent;
import org.ssglobal.training.codes.tables.pojos.Grades;
import org.ssglobal.training.codes.tables.pojos.Users;

@Repository
public class ParentCapabilitiesRepository {
	
	@Autowired
	private DSLContext dslContext;
	
	private final org.ssglobal.training.codes.tables.Parent PARENT = org.ssglobal.training.codes.tables.Parent.PARENT;
	private final org.ssglobal.training.codes.tables.Users USERS = org.ssglobal.training.codes.tables.Users.USERS;
	private final org.ssglobal.training.codes.tables.Grades GRADES = org.ssglobal.training.codes.tables.Grades.GRADES;
	
	public UserAndParent selectParent(Integer parentId) {
		return dslContext.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.FIRST_NAME,
				USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE,
				USERS.ADDRESS, USERS.CIVIL_STATUS, USERS.GENDER, USERS.NATIONALITY,
				USERS.ACTIVE_DEACTIVE, USERS.IMAGE, PARENT.PARENT_ID)
				.from(USERS).innerJoin(PARENT).on(USERS.USER_ID.eq(PARENT.USER_ID))
				.where(PARENT.PARENT_ID.eq(parentId))
				.fetchOneInto(UserAndParent.class);
	}
	
	public UserAndParent updateParentInfo(UserAndParent parent) {
		Users updatedUser = dslContext.update(USERS)
			    			.set(USERS.USERNAME, parent.getUsername())
			    			.set(USERS.PASSWORD, parent.getPassword())
			    			.set(USERS.FIRST_NAME, parent.getFirstName())
			    			.set(USERS.MIDDLE_NAME, parent.getMiddleName())
			    			.set(USERS.LAST_NAME, parent.getLastName())
			    			.set(USERS.USER_TYPE, parent.getUserType())
			    			.set(USERS.BIRTH_DATE, parent.getBirthDate())
			    			.set(USERS.ADDRESS, parent.getAddress())
			    			.set(USERS.CIVIL_STATUS, parent.getCivilStatus())
			    			.set(USERS.GENDER, parent.getGender())
			    			.set(USERS.NATIONALITY, parent.getNationality())
			    			.set(USERS.ACTIVE_DEACTIVE, parent.getActiveDeactive())
			    			.set(USERS.IMAGE, parent.getImage())
			    			.where(USERS.USER_ID.eq(parent.getUserId()))
			    			.returning()
			    			.fetchOne()
			    			.into(Users.class);
		if (updatedUser != null) {
			UserAndParent newParentInfo = new UserAndParent(updatedUser.getUserId(), updatedUser.getUsername(), updatedUser.getPassword(), 
					updatedUser.getFirstName(), updatedUser.getMiddleName(), updatedUser.getLastName(), 
					updatedUser.getUserType(), updatedUser.getBirthDate(), updatedUser.getAddress(), 
					updatedUser.getCivilStatus(), updatedUser.getGender(), updatedUser.getNationality(), 
					updatedUser.getActiveDeactive(), updatedUser.getImage(), parent.getParentId());
			return newParentInfo;
			
		}
		return null;
	}
	
	//---------------------------------------FOR STUDENTS
	
	public List<Grades> selectAllGrades(Integer studentId) {
//		return dslContext.select(GRADES.GRADE_ID)
//				.from(GRADES).where(GRADES.STUDENT_ID.eq(studentId));
		return null;
	}

}
