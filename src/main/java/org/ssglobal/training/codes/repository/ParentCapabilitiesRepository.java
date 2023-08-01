package org.ssglobal.training.codes.repository;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.model.UserAndParent;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.Grades;
import org.ssglobal.training.codes.tables.pojos.Parent;
import org.ssglobal.training.codes.tables.pojos.Users;

@Repository
public class ParentCapabilitiesRepository {

	@Autowired
	private DSLContext dslContext;

	private final org.ssglobal.training.codes.tables.Parent PARENT = org.ssglobal.training.codes.tables.Parent.PARENT;
	private final org.ssglobal.training.codes.tables.Users USERS = org.ssglobal.training.codes.tables.Users.USERS;
	private final org.ssglobal.training.codes.tables.Grades GRADES = org.ssglobal.training.codes.tables.Grades.GRADES;
	private final org.ssglobal.training.codes.tables.Student STUDENT = org.ssglobal.training.codes.tables.Student.STUDENT;
	private final org.ssglobal.training.codes.tables.Curriculum CURRICULUM = org.ssglobal.training.codes.tables.Curriculum.CURRICULUM;
	private final org.ssglobal.training.codes.tables.Major MAJOR = org.ssglobal.training.codes.tables.Major.MAJOR;
	private final org.ssglobal.training.codes.tables.Course COURSE = org.ssglobal.training.codes.tables.Course.COURSE;

	public UserAndParent selectParent(Integer parentNo) {
		return dslContext
				.select(USERS.USER_ID, USERS.USERNAME, USERS.EMAIL, USERS.CONTACT_NO, USERS.BIRTH_DATE,
						USERS.CIVIL_STATUS, USERS.GENDER, USERS.FIRST_NAME, USERS.MIDDLE_NAME, USERS.LAST_NAME,
						USERS.USER_TYPE, USERS.BIRTH_DATE, USERS.ADDRESS, USERS.CIVIL_STATUS, USERS.GENDER,
						USERS.NATIONALITY, USERS.ACTIVE_STATUS, USERS.ACTIVE_DEACTIVE, USERS.IMAGE, PARENT.PARENT_ID)
				.from(USERS).innerJoin(PARENT).on(USERS.USER_ID.eq(PARENT.USER_ID)).where(PARENT.PARENT_NO.eq(parentNo))
				.fetchOneInto(UserAndParent.class);
	}

	public UserAndParent updateParentInfo(UserAndParent parent) {

		Users currentUserData = dslContext.selectFrom(USERS).where(USERS.USER_ID.eq(parent.getUserId()))
				.fetchOneInto(Users.class);

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		String bcryptPassword = passwordEncoder.encode(parent.getPassword());

		Users updatedUser = dslContext.update(USERS).set(USERS.USERNAME, parent.getUsername())
				.set(USERS.PASSWORD, bcryptPassword).set(USERS.FIRST_NAME, parent.getFirstName())
				.set(USERS.MIDDLE_NAME, parent.getMiddleName()).set(USERS.LAST_NAME, parent.getLastName())
				.set(USERS.ADDRESS, parent.getAddress()).set(USERS.IMAGE, parent.getImage())
				.where(USERS.USER_ID.eq(parent.getUserId())).returning().fetchOne().into(Users.class);

		// if the sent password data is blank update back the current password
		if (parent.getPassword().isBlank()) {
			dslContext.update(USERS).set(USERS.PASSWORD, currentUserData.getPassword())
					.where(USERS.USER_ID.eq(updatedUser.getUserId())).execute();
		}

		// if the sent image data is blank update back the current image
		if (parent.getImage().equals("http://localhost:8080/api/file/images/undefined")) {
			dslContext.update(USERS).set(USERS.IMAGE, currentUserData.getImage())
					.where(USERS.USER_ID.eq(updatedUser.getUserId())).execute();
		}

		Parent updatedParent = dslContext.selectFrom(PARENT)
				.where(PARENT.USER_ID.eq(updatedUser.getUserId()))
				.fetchOne().into(Parent.class);

		if (updatedUser != null && updatedParent != null) {
			UserAndParent newParentInfo = dslContext
					.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.EMAIL, USERS.CONTACT_NO,
							USERS.FIRST_NAME, USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE,
							USERS.ADDRESS, USERS.CIVIL_STATUS, USERS.GENDER, USERS.NATIONALITY, USERS.ACTIVE_STATUS,
							USERS.ACTIVE_DEACTIVE, USERS.IMAGE, PARENT.PARENT_ID)
					.from(USERS).innerJoin(PARENT).on(USERS.USER_ID.eq(PARENT.USER_ID))
					.where(USERS.USER_ID.eq(parent.getUserId())).fetchOne().into(UserAndParent.class);

			return newParentInfo;

		}
		return null;
	}

	// ---------------------------------------FOR STUDENTS

	public List<UserAndStudent> selectAllChildren(Integer parentNo) {
		List<UserAndStudent> studentList = dslContext
				.select(STUDENT.STUDENT_ID, STUDENT.USER_ID, STUDENT.STUDENT_NO, STUDENT.CURRICULUM_CODE,
						STUDENT.PARENT_NO, STUDENT.ACADEMIC_YEAR_ID, STUDENT.YEAR_LEVEL, USERS.USERNAME, USERS.EMAIL,
						USERS.CONTACT_NO, USERS.FIRST_NAME, USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.USER_TYPE,
						USERS.BIRTH_DATE, USERS.ADDRESS, USERS.CIVIL_STATUS, USERS.GENDER, USERS.NATIONALITY,
						USERS.ACTIVE_STATUS, USERS.ACTIVE_DEACTIVE, USERS.IMAGE, CURRICULUM.CURRICULUM_NAME,
						COURSE.COURSE_CODE, COURSE.COURSE_TITLE, MAJOR.MAJOR_CODE, MAJOR.MAJOR_TITLE)
				.from(STUDENT).join(USERS).on(STUDENT.USER_ID.eq(USERS.USER_ID)).innerJoin(CURRICULUM)
				.on(STUDENT.CURRICULUM_CODE.eq(CURRICULUM.CURRICULUM_CODE)).innerJoin(MAJOR)
				.on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE)).innerJoin(COURSE)
				.on(MAJOR.COURSE_CODE.eq(COURSE.COURSE_CODE)).where(STUDENT.PARENT_NO.eq(parentNo))
				.fetchInto(UserAndStudent.class);
		if (studentList != null) {
			return studentList;
		}
		return null;
	}

	public List<Grades> selectAllGrades(Integer studentNo) {
		return dslContext.selectFrom(GRADES)
				.where(GRADES.STUDENT_NO.eq(studentNo))
				.fetchInto(Grades.class);
	}

}
