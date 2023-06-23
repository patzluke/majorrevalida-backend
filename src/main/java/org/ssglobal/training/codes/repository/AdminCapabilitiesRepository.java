package org.ssglobal.training.codes.repository;

import java.util.List;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.model.UserAndAdmin;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.Admin;
import org.ssglobal.training.codes.tables.pojos.Student;
import org.ssglobal.training.codes.tables.pojos.Users;

@Repository
public class AdminCapabilitiesRepository {

	@Autowired
	private DSLContext dslContext;

	private final org.ssglobal.training.codes.tables.Users USERS = org.ssglobal.training.codes.tables.Users.USERS;
	private final org.ssglobal.training.codes.tables.Admin ADMIN = org.ssglobal.training.codes.tables.Admin.ADMIN;
	private final org.ssglobal.training.codes.tables.Student STUDENT = org.ssglobal.training.codes.tables.Student.STUDENT;

	// ------------------------FOR ADMIN
	public UserAndAdmin insertAdminUser(UserAndAdmin userAdmin) {
		Users insertedUser = dslContext.insertInto(USERS).set(USERS.USERNAME, userAdmin.getUsername())
				.set(USERS.PASSWORD, userAdmin.getPassword()).set(USERS.FIRST_NAME, userAdmin.getFirstName())
				.set(USERS.MIDDLE_NAME, userAdmin.getMiddleName()).set(USERS.LAST_NAME, userAdmin.getLastName())
				.set(USERS.USER_TYPE, userAdmin.getUserType()).set(USERS.BIRTH_DATE, userAdmin.getBirthDate())
				.set(USERS.ADDRESS, userAdmin.getAddress()).set(USERS.CIVIL_STATUS, userAdmin.getCivilStatus())
				.set(USERS.GENDER, userAdmin.getGender()).set(USERS.NATIONALITY, userAdmin.getNationality())
				.set(USERS.ACTIVE_DEACTIVE, userAdmin.getActiveDeactive()).set(USERS.IMAGE, userAdmin.getImage())
				.returning().fetchOne().into(Users.class);

		Admin insertedAdmin = dslContext.insertInto(ADMIN).set(ADMIN.USER_ID, insertedUser.getUserId()).returning()
				.fetchOne().into(Admin.class);

		if (insertedUser != null && insertedAdmin != null) {
			UserAndAdmin newUserAdmin = new UserAndAdmin(insertedUser.getUserId(), insertedUser.getUsername(),
					insertedUser.getPassword(), insertedUser.getFirstName(), insertedUser.getMiddleName(),
					insertedUser.getLastName(), insertedUser.getUserType(), insertedUser.getBirthDate(),
					insertedUser.getAddress(), insertedUser.getCivilStatus(), insertedUser.getGender(),
					insertedUser.getNationality(), insertedUser.getActiveDeactive(), insertedUser.getImage(),
					insertedAdmin.getAdminId(), insertedAdmin.getAdminNo());
			return newUserAdmin;
		}

		return null;
	}

	public UserAndAdmin updateAdminUser(UserAndAdmin userAdmin) {
		Users updatedUser = dslContext.update(USERS).set(USERS.USERNAME, userAdmin.getUsername())
				.set(USERS.PASSWORD, userAdmin.getPassword()).set(USERS.FIRST_NAME, userAdmin.getFirstName())
				.set(USERS.MIDDLE_NAME, userAdmin.getMiddleName()).set(USERS.LAST_NAME, userAdmin.getLastName())
				.set(USERS.USER_TYPE, userAdmin.getUserType()).set(USERS.BIRTH_DATE, userAdmin.getBirthDate())
				.set(USERS.ADDRESS, userAdmin.getAddress()).set(USERS.CIVIL_STATUS, userAdmin.getCivilStatus())
				.set(USERS.GENDER, userAdmin.getGender()).set(USERS.NATIONALITY, userAdmin.getNationality())
				.set(USERS.ACTIVE_DEACTIVE, userAdmin.getActiveDeactive()).set(USERS.IMAGE, userAdmin.getImage())
				.where(USERS.USER_ID.eq(userAdmin.getUserId())).returning().fetchOne().into(Users.class);

		if (updatedUser != null) {
			UserAndAdmin newUserAdmin = new UserAndAdmin(updatedUser.getUserId(), updatedUser.getUsername(),
					updatedUser.getPassword(), updatedUser.getFirstName(), updatedUser.getMiddleName(),
					updatedUser.getLastName(), updatedUser.getUserType(), updatedUser.getBirthDate(),
					updatedUser.getAddress(), updatedUser.getCivilStatus(), updatedUser.getGender(),
					updatedUser.getNationality(), updatedUser.getActiveDeactive(), updatedUser.getImage(),
					userAdmin.getAdminId(), userAdmin.getAdminNo());
			return newUserAdmin;
		}

		return null;
	}

	public UserAndAdmin deleteAdminUser(Integer userAdminId) {
		Users deletedUser = dslContext.update(USERS).set(USERS.ACTIVE_DEACTIVE, false)
				.where(USERS.USER_ID.eq(userAdminId)).returning().fetchOne().into(Users.class);

		Admin deletedAdmin = dslContext.selectFrom(ADMIN).where(ADMIN.USER_ID.eq(deletedUser.getUserId())).fetchOne()
				.into(Admin.class);

		if (deletedUser != null && deletedAdmin != null) {
			UserAndAdmin newUserAdmin = new UserAndAdmin(deletedUser.getUserId(), deletedUser.getUsername(),
					deletedUser.getPassword(), deletedUser.getFirstName(), deletedUser.getMiddleName(),
					deletedUser.getLastName(), deletedUser.getUserType(), deletedUser.getBirthDate(),
					deletedUser.getAddress(), deletedUser.getCivilStatus(), deletedUser.getGender(),
					deletedUser.getNationality(), deletedUser.getActiveDeactive(), deletedUser.getImage(),
					deletedAdmin.getAdminId(), deletedAdmin.getAdminNo());
			return newUserAdmin;
		}

		return null;
	}

	public List<UserAndAdmin> selectAllAdmin() {
		return dslContext.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.FIRST_NAME, USERS.MIDDLE_NAME,
				USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE, USERS.ADDRESS, USERS.CIVIL_STATUS, USERS.GENDER,
				USERS.NATIONALITY, USERS.ACTIVE_DEACTIVE, USERS.IMAGE, ADMIN.ADMIN_ID, ADMIN.ADMIN_NO).from(USERS)
				.innerJoin(ADMIN).on(USERS.USER_ID.eq(ADMIN.USER_ID)).fetchInto(UserAndAdmin.class);
	}

	public UserAndAdmin selectAdmin(Integer adminNo) {
		return dslContext
				.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.FIRST_NAME, USERS.MIDDLE_NAME,
						USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE, USERS.ADDRESS, USERS.CIVIL_STATUS,
						USERS.GENDER, USERS.NATIONALITY, USERS.ACTIVE_DEACTIVE, USERS.IMAGE, ADMIN.ADMIN_ID,
						ADMIN.ADMIN_NO)
				.from(USERS).innerJoin(ADMIN).on(USERS.USER_ID.eq(ADMIN.USER_ID)).where(ADMIN.ADMIN_NO.eq(adminNo))
				.fetchOneInto(UserAndAdmin.class);
	}

	// ------------------------FOR STUDENTS

	// Return all the student's data
	public List<Student> selectAllStudent() {
		List<Student> students = dslContext.selectFrom(STUDENT).fetchInto(Student.class);
		return students;
	}

	public UserAndStudent insertStudent(UserAndStudent student) {
		/*
		 * This will add the User's data limited to: username, password, first_name,
		 * middle_name, last_name, birth_date, address, civil_status, gender,
		 * nationality, active_deactive, and image
		 */
		Users insertUser = dslContext.insertInto(USERS).set(USERS.USERNAME, student.getUsername())
				.set(USERS.PASSWORD, student.getPassword()).set(USERS.FIRST_NAME, student.getFirst_name())
				.set(USERS.MIDDLE_NAME, student.getMiddle_name()).set(USERS.LAST_NAME, student.getLast_name())
				.set(USERS.USER_TYPE, student.getUser_type()).set(USERS.BIRTH_DATE, student.getBirth_date())
				.set(USERS.ADDRESS, student.getAddress()).set(USERS.CIVIL_STATUS, student.getCivil_status())
				.set(USERS.GENDER, student.getGender()).set(USERS.NATIONALITY, student.getNationality())
				.set(USERS.ACTIVE_DEACTIVE, student.getActive_deactive()).set(USERS.IMAGE, student.getImage())
				.returning().fetchOne().into(Users.class);

		/*
		 * This will add the Student's data limited to: user_id, sem, and year_level
		 * NOTE: NEED TO ADD curriculum_id and academic_year_id 
		 */
		Student insertStudent = dslContext.insertInto(STUDENT).set(STUDENT.USER_ID, insertUser.getUserId())
				.set(STUDENT.SEM, student.getSem()).set(STUDENT.YEAR_LEVEL, student.getYear_level()).returning()
				.fetchOne().into(Student.class);

		// Return all the information of the student
		UserAndStudent value = new UserAndStudent(insertStudent.getSem(), insertStudent.getYearLevel(),
				insertUser.getUsername(), insertUser.getPassword(), insertUser.getFirstName(),
				insertUser.getMiddleName(), insertUser.getLastName(), insertUser.getUserType(),
				insertUser.getBirthDate(), insertUser.getAddress(), insertUser.getCivilStatus(), insertUser.getGender(),
				insertUser.getNationality(), insertUser.getActiveDeactive(), insertUser.getImage());

		return value;
	}
}
