package org.ssglobal.training.codes.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.Student;
import org.ssglobal.training.codes.tables.pojos.Users;

@Repository
public class StudentCapabilitiesRepository {

	@Autowired
	private DSLContext dslContext;

	private final org.ssglobal.training.codes.tables.Student STUDENT = org.ssglobal.training.codes.tables.Student.STUDENT;
	private final org.ssglobal.training.codes.tables.Users USERS = org.ssglobal.training.codes.tables.Users.USERS;

	public UserAndStudent updateStudent(UserAndStudent student, Integer studentId) {
		/*
		 * This will add the User's data limited to: username, password, first_name,
		 * middle_name, last_name, birth_date, address, civil_status, gender,
		 * nationality, active_deactive, image
		 */
		Users insertUser = dslContext.update(USERS).set(USERS.USERNAME, student.getUsername())
				.set(USERS.PASSWORD, student.getPassword()).set(USERS.FIRST_NAME, student.getFirst_name())
				.set(USERS.MIDDLE_NAME, student.getMiddle_name()).set(USERS.LAST_NAME, student.getLast_name())
				.set(USERS.USER_TYPE, student.getUser_type()).set(USERS.BIRTH_DATE, student.getBirth_date())
				.set(USERS.ADDRESS, student.getAddress()).set(USERS.CIVIL_STATUS, student.getCivil_status())
				.set(USERS.GENDER, student.getGender()).set(USERS.NATIONALITY, student.getNationality())
				.set(USERS.ACTIVE_DEACTIVE, student.getActive_deactive()).set(USERS.IMAGE, student.getImage())
				.where(USERS.USER_ID.eq(studentId)).returning().fetchOne().into(Users.class);

		/*
		 * This will add the Student's data limited to: user_id, sem, year_level
		 */
		Student insertStudent = dslContext.update(STUDENT).set(STUDENT.USER_ID, insertUser.getUserId())
				.set(STUDENT.SEM, student.getSem()).set(STUDENT.YEAR_LEVEL, student.getYear_level())
				.where(STUDENT.USER_ID.eq(studentId)).returning().fetchOne().into(Student.class);

		// Return all the information of the updated student
		UserAndStudent value = new UserAndStudent(insertStudent.getSem(), insertStudent.getYearLevel(),
				insertUser.getUsername(), insertUser.getPassword(), insertUser.getFirstName(),
				insertUser.getMiddleName(), insertUser.getLastName(), insertUser.getUserType(),
				insertUser.getBirthDate(), insertUser.getAddress(), insertUser.getCivilStatus(), insertUser.getGender(),
				insertUser.getNationality(), insertUser.getActiveDeactive(), insertUser.getImage());

		return value;
	}

}
