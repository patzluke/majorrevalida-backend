package org.ssglobal.training.codes.repository;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.model.UserAndParent;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.Grades;
import org.ssglobal.training.codes.tables.pojos.Users;

@Repository
public class ParentCapabilitiesRepository {
	
	@Autowired
	private DSLContext dslContext;
	
	private final org.ssglobal.training.codes.tables.Parent PARENT = org.ssglobal.training.codes.tables.Parent.PARENT;
	private final org.ssglobal.training.codes.tables.Users USERS = org.ssglobal.training.codes.tables.Users.USERS;
	private final org.ssglobal.training.codes.tables.Grades GRADES = org.ssglobal.training.codes.tables.Grades.GRADES;
	private final org.ssglobal.training.codes.tables.Student STUDENT = org.ssglobal.training.codes.tables.Student.STUDENT;
	
	public UserAndParent selectParent(Integer parentNo) {
		return dslContext.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.FIRST_NAME,
				USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE,
				USERS.ADDRESS, USERS.CIVIL_STATUS, USERS.GENDER, USERS.NATIONALITY,
				USERS.ACTIVE_DEACTIVE, USERS.IMAGE, PARENT.PARENT_ID)
				.from(USERS).innerJoin(PARENT).on(USERS.USER_ID.eq(PARENT.USER_ID))
				.where(PARENT.PARENT_ID.eq(parentNo))
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
	
	public List<UserAndStudent> selectAllChildren(Integer parentNo) {
		List<UserAndStudent> studentList = dslContext.select(STUDENT.STUDENT_ID,STUDENT.USER_ID, STUDENT.STUDENT_NO, 
															STUDENT.CURRICULUM_CODE, STUDENT.PARENT_NO, STUDENT.SEM, 
															STUDENT.YEAR_LEVEL, STUDENT.ACADEMIC_YEAR_ID,
															USERS.FIRST_NAME, USERS.MIDDLE_NAME, USERS.LAST_NAME)
															.from(STUDENT).join(USERS).on(STUDENT.USER_ID.eq(USERS.USER_ID))
															.where(STUDENT.PARENT_NO.eq(parentNo)).fetchInto(UserAndStudent.class);
		if (studentList != null) {
			return studentList;
		}
		return null;
	}
	
	public List<Grades> selectAllGrades(Integer studentNo) {
		return dslContext.selectFrom(GRADES).where(GRADES.STUDENT_NO.eq(studentNo)).fetchInto(Grades.class);
		
	}

}
