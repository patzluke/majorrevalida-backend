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

	public UserAndStudent viewStudentProfile(Integer studentNo) {

		// Get the student's data via student table
		Student studentData = dslContext.selectFrom(STUDENT).where(STUDENT.STUDENT_NO.eq(studentNo))
				.fetchOneInto(Student.class);

		// Get the student's data via users table
		Users userData = dslContext.selectFrom(USERS).where(USERS.USER_ID.eq(studentData.getStudentId()))
				.fetchOneInto(Users.class);

		// Return all the information of the updated student
		UserAndStudent information = new UserAndStudent(userData.getUsername(), userData.getPassword(),
				userData.getEmail(), userData.getContactNo(), userData.getFirstName(), userData.getMiddleName(),
				userData.getLastName(), userData.getUserType(), userData.getBirthDate(), userData.getAddress(),
				userData.getCivilStatus(), userData.getGender(), userData.getNationality(),
				userData.getActiveDeactive(), userData.getImage(), studentData.getUserId(),
				studentData.getCurriculumCode(), studentData.getParentNo(), studentData.getSem(),
				studentData.getYearLevel(), studentData.getAcademicYearId());

		return information;
	}

	public UserAndStudent updateStudent(UserAndStudent student, Integer studentId) {
		/*
		 * This will add the User's data limited to: username, password, email,
		 * contactNo first_name, middle_name, last_name, birth_date, address,
		 * civil_status, gender, nationality, active_deactive, and image
		 */
		Users updatedUser = dslContext.insertInto(USERS).set(USERS.USERNAME, student.getUsername())
				.set(USERS.PASSWORD, student.getPassword()).set(USERS.EMAIL, student.getEmail())
				.set(USERS.CONTACT_NO, student.getContactNo()).set(USERS.FIRST_NAME, student.getFirstName())
				.set(USERS.MIDDLE_NAME, student.getMiddleName()).set(USERS.LAST_NAME, student.getLastName())
				.set(USERS.USER_TYPE, student.getUserType()).set(USERS.BIRTH_DATE, student.getBirthDate())
				.set(USERS.ADDRESS, student.getAddress()).set(USERS.CIVIL_STATUS, student.getCivilStatus())
				.set(USERS.GENDER, student.getGender()).set(USERS.NATIONALITY, student.getNationality())
				.set(USERS.ACTIVE_DEACTIVE, student.getActiveDeactive()).set(USERS.IMAGE, student.getImage())
				.returning().fetchOne().into(Users.class);

		/*
		 * This will add the Student's data limited to: user_id, curriculumCode,
		 * parentNo, sem, yearLevel, academicYearId
		 */
		Student updatedStudent = dslContext.insertInto(STUDENT).set(STUDENT.USER_ID, updatedUser.getUserId())
				.set(STUDENT.CURRICULUM_CODE, student.getCurriculumCode()).set(STUDENT.PARENT_NO, student.getParentNo())
				.set(STUDENT.SEM, student.getSem()).set(STUDENT.YEAR_LEVEL, student.getYearLevel())
				.set(STUDENT.ACADEMIC_YEAR_ID, student.getAcademicYearId()).returning().fetchOne().into(Student.class);

		// Return all the information of the updated student
		UserAndStudent information = new UserAndStudent(updatedUser.getUsername(), updatedUser.getPassword(),
				updatedUser.getEmail(), updatedUser.getContactNo(), updatedUser.getFirstName(),
				updatedUser.getMiddleName(), updatedUser.getLastName(), updatedUser.getUserType(),
				updatedUser.getBirthDate(), updatedUser.getAddress(), updatedUser.getCivilStatus(),
				updatedUser.getGender(), updatedUser.getNationality(), updatedUser.getActiveDeactive(),
				updatedUser.getImage(), updatedStudent.getUserId(), updatedStudent.getCurriculumCode(),
				updatedStudent.getParentNo(), updatedStudent.getSem(), updatedStudent.getYearLevel(),
				updatedStudent.getAcademicYearId());

		return information;
	}

}
