package org.ssglobal.training.codes.repository;

import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.model.UserAndAdmin;
import org.ssglobal.training.codes.model.UserAndParent;
import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.AcademicYear;
import org.ssglobal.training.codes.tables.pojos.Admin;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.Curriculum;
import org.ssglobal.training.codes.tables.pojos.Department;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.MajorSubject;
import org.ssglobal.training.codes.tables.pojos.MinorSubject;
import org.ssglobal.training.codes.tables.pojos.Parent;
import org.ssglobal.training.codes.tables.pojos.Professor;
import org.ssglobal.training.codes.tables.pojos.ProfessorLoad;
import org.ssglobal.training.codes.tables.pojos.Program;
import org.ssglobal.training.codes.tables.pojos.Room;
import org.ssglobal.training.codes.tables.pojos.Section;
import org.ssglobal.training.codes.tables.pojos.Student;
import org.ssglobal.training.codes.tables.pojos.StudentApplicant;
import org.ssglobal.training.codes.tables.pojos.Subject;
import org.ssglobal.training.codes.tables.pojos.Users;

@Repository
public class AdminCapabilitiesRepository {

	@Autowired
	private DSLContext dslContext;

	private final org.ssglobal.training.codes.tables.Users USERS = org.ssglobal.training.codes.tables.Users.USERS;
	private final org.ssglobal.training.codes.tables.Admin ADMIN = org.ssglobal.training.codes.tables.Admin.ADMIN;
	private final org.ssglobal.training.codes.tables.Student STUDENT = org.ssglobal.training.codes.tables.Student.STUDENT;

	private final org.ssglobal.training.codes.tables.Professor PROFESSOR = org.ssglobal.training.codes.tables.Professor.PROFESSOR;
	private final org.ssglobal.training.codes.tables.ProfessorLoad PROFESSOR_LOAD = org.ssglobal.training.codes.tables.ProfessorLoad.PROFESSOR_LOAD;
	private final org.ssglobal.training.codes.tables.Parent PARENT = org.ssglobal.training.codes.tables.Parent.PARENT;
	private final org.ssglobal.training.codes.tables.StudentApplicant STUDENT_APPLICANT = org.ssglobal.training.codes.tables.StudentApplicant.STUDENT_APPLICANT;
	private final org.ssglobal.training.codes.tables.AcademicYear ACADEMIC_YEAR = org.ssglobal.training.codes.tables.AcademicYear.ACADEMIC_YEAR;
	private final org.ssglobal.training.codes.tables.Program PROGRAM = org.ssglobal.training.codes.tables.Program.PROGRAM;
	private final org.ssglobal.training.codes.tables.Department DEPARTMENT = org.ssglobal.training.codes.tables.Department.DEPARTMENT;
	private final org.ssglobal.training.codes.tables.Course COURSE = org.ssglobal.training.codes.tables.Course.COURSE;
	private final org.ssglobal.training.codes.tables.Major MAJOR = org.ssglobal.training.codes.tables.Major.MAJOR;
	private final org.ssglobal.training.codes.tables.Curriculum CURRICULUM = org.ssglobal.training.codes.tables.Curriculum.CURRICULUM;
	private final org.ssglobal.training.codes.tables.Grades GRADES = org.ssglobal.training.codes.tables.Grades.GRADES;
	private final org.ssglobal.training.codes.tables.TSubjectDetailHistory T_SUBJECT_DETAIL_HISTORY = org.ssglobal.training.codes.tables.TSubjectDetailHistory.T_SUBJECT_DETAIL_HISTORY;
	private final org.ssglobal.training.codes.tables.Subject SUBJECT = org.ssglobal.training.codes.tables.Subject.SUBJECT;
	private final org.ssglobal.training.codes.tables.MinorSubject MINOR_SUBJECT = org.ssglobal.training.codes.tables.MinorSubject.MINOR_SUBJECT;
	private final org.ssglobal.training.codes.tables.MajorSubject MAJOR_SUBJECT = org.ssglobal.training.codes.tables.MajorSubject.MAJOR_SUBJECT;
	private final org.ssglobal.training.codes.tables.Section SECTION = org.ssglobal.training.codes.tables.Section.SECTION;
	private final org.ssglobal.training.codes.tables.Room ROOM = org.ssglobal.training.codes.tables.Room.ROOM;

	// ------------------------FOR ALL
	public List<Users> selectAllUsers() {
		return dslContext.selectFrom(USERS).fetchInto(Users.class);
	}

	public boolean changePassword(String password, String username) {
		boolean updateUser = dslContext.update(USERS).set(USERS.PASSWORD, password).where(USERS.USERNAME.eq(username))
				.execute() == 1;
		if (updateUser) {
			return true;
		}
		return false;
	}

	// ------------------------FOR ADMIN
	public List<UserAndAdmin> selectAllAdmin() {
		return dslContext
				.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.EMAIL, USERS.CONTACT_NO, USERS.FIRST_NAME,
						USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE, USERS.ADDRESS,
						USERS.CIVIL_STATUS, USERS.GENDER, USERS.NATIONALITY, USERS.ACTIVE_STATUS, USERS.ACTIVE_DEACTIVE,
						USERS.IMAGE, ADMIN.ADMIN_ID, ADMIN.ADMIN_NO)
				.from(USERS).innerJoin(ADMIN).on(USERS.USER_ID.eq(ADMIN.USER_ID)).where(USERS.ACTIVE_DEACTIVE.eq(true))
				.fetchInto(UserAndAdmin.class);
	}

	public UserAndAdmin selectAdmin(Integer adminNo) {
		return dslContext
				.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.EMAIL, USERS.CONTACT_NO, USERS.FIRST_NAME,
						USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE, USERS.ADDRESS,
						USERS.CIVIL_STATUS, USERS.GENDER, USERS.NATIONALITY, USERS.ACTIVE_STATUS, USERS.ACTIVE_DEACTIVE,
						USERS.IMAGE, ADMIN.ADMIN_ID, ADMIN.ADMIN_NO)
				.from(USERS).innerJoin(ADMIN).on(USERS.USER_ID.eq(ADMIN.USER_ID))
				.where(USERS.ACTIVE_DEACTIVE.eq(true).and(ADMIN.ADMIN_NO.eq(adminNo))).fetchOneInto(UserAndAdmin.class);
	}

	public UserAndAdmin insertAdminUser(UserAndAdmin userAdmin) {
		Users insertedUser = dslContext.insertInto(USERS).set(USERS.USERNAME, userAdmin.getUsername())
				.set(USERS.PASSWORD, userAdmin.getPassword()).set(USERS.EMAIL, userAdmin.getEmail())
				.set(USERS.CONTACT_NO, userAdmin.getContactNo()).set(USERS.FIRST_NAME, userAdmin.getFirstName())
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
					insertedUser.getPassword(), insertedUser.getEmail(), insertedUser.getContactNo(),
					insertedUser.getFirstName(), insertedUser.getMiddleName(), insertedUser.getLastName(),
					insertedUser.getUserType(), insertedUser.getBirthDate(), insertedUser.getAddress(),
					insertedUser.getCivilStatus(), insertedUser.getGender(), insertedUser.getNationality(),
					insertedUser.getActiveStatus(), insertedUser.getActiveDeactive(), insertedUser.getImage(),
					insertedAdmin.getAdminId(), insertedAdmin.getAdminNo());
			return newUserAdmin;
		}
		return null;
	}

	public UserAndAdmin updateAdminUser(UserAndAdmin userAdmin) {
		Users updatedUser = dslContext.update(USERS).set(USERS.USERNAME, userAdmin.getUsername())
				.set(USERS.EMAIL, userAdmin.getEmail()).set(USERS.CONTACT_NO, userAdmin.getContactNo())
				.set(USERS.FIRST_NAME, userAdmin.getFirstName()).set(USERS.MIDDLE_NAME, userAdmin.getMiddleName())
				.set(USERS.LAST_NAME, userAdmin.getLastName()).set(USERS.USER_TYPE, userAdmin.getUserType())
				.set(USERS.BIRTH_DATE, userAdmin.getBirthDate()).set(USERS.ADDRESS, userAdmin.getAddress())
				.set(USERS.CIVIL_STATUS, userAdmin.getCivilStatus()).set(USERS.GENDER, userAdmin.getGender())
				.set(USERS.NATIONALITY, userAdmin.getNationality()).set(USERS.IMAGE, userAdmin.getImage())
				.where(USERS.USER_ID.eq(userAdmin.getUserId())).returning().fetchOne().into(Users.class);

		if (!userAdmin.getPassword().isBlank()) {
			dslContext.update(USERS).set(USERS.PASSWORD, userAdmin.getPassword())
					.where(USERS.USER_ID.eq(userAdmin.getUserId())).execute();
		}

		Admin updatedAdmin = dslContext.selectFrom(ADMIN).where(ADMIN.USER_ID.eq(updatedUser.getUserId())).fetchOne()
				.into(Admin.class);

		if (updatedUser != null && updatedAdmin != null) {
			UserAndAdmin newUserAdmin = new UserAndAdmin(updatedUser.getUserId(), updatedUser.getUsername(),
					updatedUser.getPassword(), updatedUser.getEmail(), updatedUser.getContactNo(),
					updatedUser.getFirstName(), updatedUser.getMiddleName(), updatedUser.getLastName(),
					updatedUser.getUserType(), updatedUser.getBirthDate(), updatedUser.getAddress(),
					updatedUser.getCivilStatus(), updatedUser.getGender(), updatedUser.getNationality(),
					updatedUser.getActiveStatus(), updatedUser.getActiveDeactive(), updatedUser.getImage(),
					updatedAdmin.getAdminId(), updatedAdmin.getAdminNo());

			return newUserAdmin;
		}

		return null;
	}

	public UserAndAdmin changeAdminAccountStatus(Integer userId, Boolean status) {
		Users deactivatedUser = dslContext.update(USERS).set(USERS.ACTIVE_STATUS, status)
				.where(USERS.USER_ID.eq(userId)).returning().fetchOne().into(Users.class);

		Admin deactivatedAdmin = dslContext.selectFrom(ADMIN).where(ADMIN.USER_ID.eq(deactivatedUser.getUserId()))
				.fetchOne().into(Admin.class);

		if (deactivatedUser != null && deactivatedAdmin != null) {
			UserAndAdmin deactivatedUserAdmin = new UserAndAdmin(deactivatedUser.getUserId(),
					deactivatedUser.getUsername(), deactivatedUser.getPassword(), deactivatedUser.getEmail(),
					deactivatedUser.getContactNo(), deactivatedUser.getFirstName(), deactivatedUser.getMiddleName(),
					deactivatedUser.getLastName(), deactivatedUser.getUserType(), deactivatedUser.getBirthDate(),
					deactivatedUser.getAddress(), deactivatedUser.getCivilStatus(), deactivatedUser.getGender(),
					deactivatedUser.getNationality(), deactivatedUser.getActiveStatus(),
					deactivatedUser.getActiveDeactive(), deactivatedUser.getImage(), deactivatedAdmin.getAdminId(),
					deactivatedAdmin.getAdminNo());
			return deactivatedUserAdmin;
		}
		return null;
	}

	public UserAndAdmin deleteAdminUser(Integer userId) {
		Users deletedUser = dslContext.update(USERS).set(USERS.ACTIVE_STATUS, false).set(USERS.ACTIVE_DEACTIVE, false)
				.where(USERS.USER_ID.eq(userId)).returning().fetchOne().into(Users.class);

		Admin deletedAdmin = dslContext.selectFrom(ADMIN).where(ADMIN.USER_ID.eq(deletedUser.getUserId())).fetchOne()
				.into(Admin.class);

		if (deletedUser != null && deletedAdmin != null) {
			UserAndAdmin newUserAdmin = new UserAndAdmin(deletedUser.getUserId(), deletedUser.getUsername(),
					deletedUser.getPassword(), deletedUser.getEmail(), deletedUser.getContactNo(),
					deletedUser.getFirstName(), deletedUser.getMiddleName(), deletedUser.getLastName(),
					deletedUser.getUserType(), deletedUser.getBirthDate(), deletedUser.getAddress(),
					deletedUser.getCivilStatus(), deletedUser.getGender(), deletedUser.getNationality(),
					deletedUser.getActiveStatus(), deletedUser.getActiveDeactive(), deletedUser.getImage(),
					deletedAdmin.getAdminId(), deletedAdmin.getAdminNo());
			return newUserAdmin;
		}

		return null;
	}

	// ------------------------FOR STUDENTS

	// Return all the student's data
	public List<UserAndStudent> selectAllStudent() {
		/*
		 * This will select the Student's data limited to: user_id, parent_no,
		 * curriculumCode, and academicYearId
		 */
		return dslContext
				.select(USERS.USER_ID, USERS.USERNAME, USERS.EMAIL, USERS.CONTACT_NO, USERS.FIRST_NAME,
						USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE, USERS.ADDRESS,
						USERS.CIVIL_STATUS, USERS.GENDER, USERS.NATIONALITY, USERS.ACTIVE_STATUS, USERS.ACTIVE_DEACTIVE,
						USERS.IMAGE, STUDENT.STUDENT_ID, STUDENT.STUDENT_NO, STUDENT.PARENT_NO, STUDENT.CURRICULUM_CODE,
						CURRICULUM.CURRICULUM_NAME, STUDENT.ACADEMIC_YEAR_ID, COURSE.COURSE_CODE, COURSE.COURSE_TITLE,
						MAJOR.MAJOR_CODE, MAJOR.MAJOR_TITLE, STUDENT.YEAR_LEVEL)
				.from(USERS).innerJoin(STUDENT).on(USERS.USER_ID.eq(STUDENT.USER_ID)).innerJoin(CURRICULUM)
				.on(STUDENT.CURRICULUM_CODE.eq(CURRICULUM.CURRICULUM_CODE)).innerJoin(MAJOR)
				.on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE)).innerJoin(COURSE)
				.on(MAJOR.COURSE_CODE.eq(COURSE.COURSE_CODE)).where(USERS.ACTIVE_DEACTIVE.eq(true))
				.fetchInto(UserAndStudent.class);
	}

	public UserAndStudent insertStudent(UserAndStudent student) {
		/*
		 * This will add the User's data limited to: username, password, email,
		 * contactNo first_name, middle_name, last_name, user_type, birth_date, address,
		 * civil_status, gender, nationality, active_deactive, and image
		 */
		Users insertUser = dslContext.insertInto(USERS).set(USERS.USERNAME, student.getUsername())
				.set(USERS.PASSWORD, student.getPassword()).set(USERS.EMAIL, student.getEmail())
				.set(USERS.CONTACT_NO, student.getContactNo()).set(USERS.FIRST_NAME, student.getFirstName())
				.set(USERS.MIDDLE_NAME, student.getMiddleName()).set(USERS.LAST_NAME, student.getLastName())
				.set(USERS.USER_TYPE, student.getUserType()).set(USERS.BIRTH_DATE, student.getBirthDate())
				.set(USERS.ADDRESS, student.getAddress()).set(USERS.CIVIL_STATUS, student.getCivilStatus())
				.set(USERS.GENDER, student.getGender()).set(USERS.NATIONALITY, student.getNationality())
				.set(USERS.ACTIVE_DEACTIVE, student.getActiveDeactive()).set(USERS.IMAGE, student.getImage())
				.returning().fetchOne().into(Users.class);

		/*
		 * This will add the Student's data limited to: user_id, parent_no,
		 * curriculumCode, and academicYearId
		 */

		Student insertStudent = dslContext.insertInto(STUDENT).set(STUDENT.USER_ID, insertUser.getUserId())
				.set(STUDENT.PARENT_NO, student.getParentNo()).set(STUDENT.CURRICULUM_CODE, student.getCurriculumCode())
				.set(STUDENT.ACADEMIC_YEAR_ID, student.getAcademicYearId())
				.set(STUDENT.YEAR_LEVEL, student.getYearLevel()).returning().fetchOne().into(Student.class);

		if (insertUser != null && insertStudent != null) {
			UserAndStudent information = dslContext
					.select(USERS.USER_ID, USERS.USERNAME, USERS.EMAIL, USERS.CONTACT_NO, USERS.FIRST_NAME,
							USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE, USERS.ADDRESS,
							USERS.CIVIL_STATUS, USERS.GENDER, USERS.NATIONALITY, USERS.ACTIVE_STATUS,
							USERS.ACTIVE_DEACTIVE, USERS.IMAGE, STUDENT.STUDENT_ID, STUDENT.STUDENT_NO,
							STUDENT.PARENT_NO, STUDENT.CURRICULUM_CODE, CURRICULUM.CURRICULUM_NAME,
							STUDENT.ACADEMIC_YEAR_ID, COURSE.COURSE_CODE, COURSE.COURSE_TITLE, MAJOR.MAJOR_CODE,
							MAJOR.MAJOR_TITLE, STUDENT.YEAR_LEVEL)
					.from(USERS).innerJoin(STUDENT).on(USERS.USER_ID.eq(STUDENT.USER_ID)).innerJoin(CURRICULUM)
					.on(STUDENT.CURRICULUM_CODE.eq(CURRICULUM.CURRICULUM_CODE)).innerJoin(MAJOR)
					.on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE)).innerJoin(COURSE)
					.on(MAJOR.COURSE_CODE.eq(COURSE.COURSE_CODE))
					.where(STUDENT.STUDENT_NO.eq(insertStudent.getStudentNo())).fetchOneInto(UserAndStudent.class);

			return information;
		}
		return null;

	}

	public UserAndStudent updateStudent(UserAndStudent student) {
		Users updatedUser = dslContext.update(USERS).set(USERS.USERNAME, student.getUsername())
				.set(USERS.PASSWORD, student.getPassword()).set(USERS.EMAIL, student.getEmail())
				.set(USERS.CONTACT_NO, student.getContactNo()).set(USERS.FIRST_NAME, student.getFirstName())
				.set(USERS.MIDDLE_NAME, student.getMiddleName()).set(USERS.LAST_NAME, student.getLastName())
				.set(USERS.USER_TYPE, student.getUserType()).set(USERS.BIRTH_DATE, student.getBirthDate())
				.set(USERS.ADDRESS, student.getAddress()).set(USERS.CIVIL_STATUS, student.getCivilStatus())
				.set(USERS.GENDER, student.getGender()).set(USERS.NATIONALITY, student.getNationality())
				.set(USERS.IMAGE, student.getImage()).where(USERS.USER_ID.eq(student.getUserId())).returning()
				.fetchOne().into(Users.class);

		Student updatedStudent = dslContext.update(STUDENT).set(STUDENT.PARENT_NO, student.getParentNo())
				.set(STUDENT.CURRICULUM_CODE, student.getCurriculumCode())
				.set(STUDENT.ACADEMIC_YEAR_ID, student.getAcademicYearId())
				.set(STUDENT.YEAR_LEVEL, student.getYearLevel()).where(STUDENT.STUDENT_NO.eq(student.getStudentNo()))
				.returning().fetchOne().into(Student.class);

		if (updatedUser != null && updatedStudent != null) {
			UserAndStudent information = dslContext
					.select(USERS.USER_ID, USERS.USERNAME, USERS.EMAIL, USERS.CONTACT_NO, USERS.FIRST_NAME,
							USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE, USERS.ADDRESS,
							USERS.CIVIL_STATUS, USERS.GENDER, USERS.NATIONALITY, USERS.ACTIVE_STATUS,
							USERS.ACTIVE_DEACTIVE, USERS.IMAGE, STUDENT.STUDENT_ID, STUDENT.STUDENT_NO,
							STUDENT.PARENT_NO, STUDENT.CURRICULUM_CODE, CURRICULUM.CURRICULUM_NAME,
							STUDENT.ACADEMIC_YEAR_ID, COURSE.COURSE_CODE, COURSE.COURSE_TITLE, MAJOR.MAJOR_CODE,
							MAJOR.MAJOR_TITLE, STUDENT.YEAR_LEVEL)
					.from(USERS).innerJoin(STUDENT).on(USERS.USER_ID.eq(STUDENT.USER_ID)).innerJoin(CURRICULUM)
					.on(STUDENT.CURRICULUM_CODE.eq(CURRICULUM.CURRICULUM_CODE)).innerJoin(MAJOR)
					.on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE)).innerJoin(COURSE)
					.on(MAJOR.COURSE_CODE.eq(COURSE.COURSE_CODE))
					.where(STUDENT.STUDENT_NO.eq(updatedStudent.getStudentNo())).fetchOneInto(UserAndStudent.class);

			return information;
		}
		return null;
	}

	public UserAndStudent changeStudentAccountStatus(Integer userId, Boolean status) {
		Users deactivatedUser = dslContext.update(USERS).set(USERS.ACTIVE_STATUS, status)
				.where(USERS.USER_ID.eq(userId)).returning().fetchOne().into(Users.class);

		Student deactivatedStudent = dslContext.selectFrom(STUDENT)
				.where(STUDENT.USER_ID.eq(deactivatedUser.getUserId())).fetchOne().into(Student.class);

		if (deactivatedUser != null && deactivatedStudent != null) {
			UserAndStudent information = dslContext
					.select(USERS.USER_ID, USERS.USERNAME, USERS.EMAIL, USERS.CONTACT_NO, USERS.FIRST_NAME,
							USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE, USERS.ADDRESS,
							USERS.CIVIL_STATUS, USERS.GENDER, USERS.NATIONALITY, USERS.ACTIVE_STATUS,
							USERS.ACTIVE_DEACTIVE, USERS.IMAGE, STUDENT.STUDENT_ID, STUDENT.STUDENT_NO,
							STUDENT.PARENT_NO, STUDENT.CURRICULUM_CODE, CURRICULUM.CURRICULUM_NAME,
							STUDENT.ACADEMIC_YEAR_ID, COURSE.COURSE_CODE, COURSE.COURSE_TITLE, MAJOR.MAJOR_CODE,
							MAJOR.MAJOR_TITLE, STUDENT.YEAR_LEVEL)
					.from(USERS).innerJoin(STUDENT).on(USERS.USER_ID.eq(STUDENT.USER_ID)).innerJoin(CURRICULUM)
					.on(STUDENT.CURRICULUM_CODE.eq(CURRICULUM.CURRICULUM_CODE)).innerJoin(MAJOR)
					.on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE)).innerJoin(COURSE)
					.on(MAJOR.COURSE_CODE.eq(COURSE.COURSE_CODE))
					.where(STUDENT.STUDENT_NO.eq(deactivatedStudent.getStudentNo())).fetchOneInto(UserAndStudent.class);

			return information;
		}
		return null;
	}

	public UserAndStudent deleteStudent(Integer userId) {
		Users deletedUser = dslContext.update(USERS).set(USERS.ACTIVE_STATUS, false).set(USERS.ACTIVE_DEACTIVE, false)
				.where(USERS.USER_ID.eq(userId)).returning().fetchOne().into(Users.class);

		Student deactivatedStudent = dslContext.selectFrom(STUDENT).where(STUDENT.USER_ID.eq(deletedUser.getUserId()))
				.fetchOne().into(Student.class);

		if (deletedUser != null && deactivatedStudent != null) {
			UserAndStudent information = new UserAndStudent(deletedUser.getUserId(), deletedUser.getUsername(),
					deletedUser.getPassword(), deletedUser.getEmail(), deletedUser.getContactNo(),
					deletedUser.getFirstName(), deletedUser.getMiddleName(), deletedUser.getLastName(),
					deletedUser.getUserType(), deletedUser.getBirthDate(), deletedUser.getAddress(),
					deletedUser.getCivilStatus(), deletedUser.getGender(), deletedUser.getNationality(),
					deletedUser.getActiveStatus(), deletedUser.getActiveDeactive(), deletedUser.getImage(),
					deactivatedStudent.getStudentNo(), deactivatedStudent.getUserId(), deactivatedStudent.getParentNo(),
					deactivatedStudent.getCurriculumCode(), deactivatedStudent.getYearLevel(),
					deactivatedStudent.getAcademicYearId());
			return information;
		}
		return null;
	}

	// ------------------------FOR PROFESSOR

	public List<UserAndProfessor> selectAllProfessor() {
		return dslContext
				.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.EMAIL, USERS.CONTACT_NO, USERS.FIRST_NAME,
						USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE, USERS.ADDRESS,
						USERS.CIVIL_STATUS, USERS.GENDER, USERS.NATIONALITY, USERS.ACTIVE_STATUS, USERS.ACTIVE_DEACTIVE,
						USERS.IMAGE, PROFESSOR.PROFESSOR_ID, PROFESSOR.PROFESSOR_NO, PROFESSOR.WORK)
				.from(USERS).innerJoin(PROFESSOR).on(USERS.USER_ID.eq(PROFESSOR.USER_ID)).fetchInto(UserAndProfessor.class);
	}

	public UserAndProfessor insertProfessor(UserAndProfessor userAndProfessor) {
		Users insertedUser = dslContext.insertInto(USERS).set(USERS.USERNAME, userAndProfessor.getUsername())
				.set(USERS.PASSWORD, userAndProfessor.getPassword()).set(USERS.EMAIL, userAndProfessor.getEmail())
				.set(USERS.CONTACT_NO, userAndProfessor.getContactNo())
				.set(USERS.FIRST_NAME, userAndProfessor.getFirstName())
				.set(USERS.MIDDLE_NAME, userAndProfessor.getMiddleName())
				.set(USERS.LAST_NAME, userAndProfessor.getLastName())
				.set(USERS.USER_TYPE, userAndProfessor.getUserType())
				.set(USERS.BIRTH_DATE, userAndProfessor.getBirthDate())
				.set(USERS.ADDRESS, userAndProfessor.getAddress())
				.set(USERS.CIVIL_STATUS, userAndProfessor.getCivilStatus())
				.set(USERS.GENDER, userAndProfessor.getGender())
				.set(USERS.NATIONALITY, userAndProfessor.getNationality())
				.set(USERS.ACTIVE_DEACTIVE, userAndProfessor.getActiveDeactive())
				.set(USERS.IMAGE, userAndProfessor.getImage()).returning().fetchOne().into(Users.class);

		Professor insertedProfessor = dslContext.insertInto(PROFESSOR).set(PROFESSOR.USER_ID, insertedUser.getUserId())
				.set(PROFESSOR.WORK, userAndProfessor.getWork()).returning().fetchOne().into(Professor.class);

		if (insertedUser != null && insertedProfessor != null) {
			UserAndProfessor newuserAndProfessor = new UserAndProfessor(insertedUser.getUserId(),
					insertedUser.getUsername(), insertedUser.getPassword(), insertedUser.getEmail(),
					insertedUser.getContactNo(), insertedUser.getFirstName(), insertedUser.getMiddleName(),
					insertedUser.getLastName(), insertedUser.getUserType(), insertedUser.getBirthDate(),
					insertedUser.getAddress(), insertedUser.getCivilStatus(), insertedUser.getGender(),
					insertedUser.getNationality(), insertedUser.getActiveStatus(), insertedUser.getActiveDeactive(),
					insertedUser.getImage(), insertedProfessor.getProfessorId(), insertedProfessor.getProfessorNo(),
					insertedProfessor.getWork());
			return newuserAndProfessor;
		}
		return null;
	}

	public UserAndProfessor updateProfessor(UserAndProfessor userAndProfessor) {
		Users updatedUser = dslContext.update(USERS).set(USERS.USERNAME, userAndProfessor.getUsername())
				.set(USERS.EMAIL, userAndProfessor.getEmail()).set(USERS.CONTACT_NO, userAndProfessor.getContactNo())
				.set(USERS.FIRST_NAME, userAndProfessor.getFirstName())
				.set(USERS.MIDDLE_NAME, userAndProfessor.getMiddleName())
				.set(USERS.LAST_NAME, userAndProfessor.getLastName())
				.set(USERS.USER_TYPE, userAndProfessor.getUserType())
				.set(USERS.BIRTH_DATE, userAndProfessor.getBirthDate())
				.set(USERS.ADDRESS, userAndProfessor.getAddress())
				.set(USERS.CIVIL_STATUS, userAndProfessor.getCivilStatus())
				.set(USERS.GENDER, userAndProfessor.getGender())
				.set(USERS.NATIONALITY, userAndProfessor.getNationality()).set(USERS.IMAGE, userAndProfessor.getImage())
				.where(USERS.USER_ID.eq(userAndProfessor.getUserId())).returning().fetchOne().into(Users.class);

		Professor updatedProfessor = dslContext.update(PROFESSOR).set(PROFESSOR.WORK, userAndProfessor.getWork())
				.where(PROFESSOR.PROFESSOR_NO.eq(userAndProfessor.getProfessorNo())).returning().fetchOne()
				.into(Professor.class);

		if (updatedUser != null && updatedProfessor != null) {
			UserAndProfessor newuserAndProfessor = new UserAndProfessor(updatedUser.getUserId(),
					updatedUser.getUsername(), updatedUser.getPassword(), updatedUser.getEmail(),
					updatedUser.getContactNo(), updatedUser.getFirstName(), updatedUser.getMiddleName(),
					updatedUser.getLastName(), updatedUser.getUserType(), updatedUser.getBirthDate(),
					updatedUser.getAddress(), updatedUser.getCivilStatus(), updatedUser.getGender(),
					updatedUser.getNationality(), updatedUser.getActiveStatus(), updatedUser.getActiveDeactive(),
					updatedUser.getImage(), updatedProfessor.getProfessorId(), updatedProfessor.getProfessorNo(),
					updatedProfessor.getWork());
			return newuserAndProfessor;
		}
		return null;
	}

	public UserAndProfessor changeProfessorAccountStatus(Integer userId, Boolean status) {
		Users deactivatedUser = dslContext.update(USERS).set(USERS.ACTIVE_STATUS, status)
				.where(USERS.USER_ID.eq(userId)).returning().fetchOne().into(Users.class);

		Professor deactivatedProfessor = dslContext.selectFrom(PROFESSOR)
				.where(PROFESSOR.USER_ID.eq(deactivatedUser.getUserId())).fetchOne().into(Professor.class);

		if (deactivatedUser != null && deactivatedProfessor != null) {
			UserAndProfessor deactivatedUserProfessor = new UserAndProfessor(deactivatedUser.getUserId(),
					deactivatedUser.getUsername(), deactivatedUser.getPassword(), deactivatedUser.getEmail(),
					deactivatedUser.getContactNo(), deactivatedUser.getFirstName(), deactivatedUser.getMiddleName(),
					deactivatedUser.getLastName(), deactivatedUser.getUserType(), deactivatedUser.getBirthDate(),
					deactivatedUser.getAddress(), deactivatedUser.getCivilStatus(), deactivatedUser.getGender(),
					deactivatedUser.getNationality(), deactivatedUser.getActiveStatus(),
					deactivatedUser.getActiveDeactive(), deactivatedUser.getImage(),
					deactivatedProfessor.getProfessorId(), deactivatedProfessor.getProfessorNo(),
					deactivatedProfessor.getWork());
			return deactivatedUserProfessor;
		}
		return null;
	}

	public UserAndProfessor deleteProfessor(Integer userId) {
		Users deactivatedUser = dslContext.update(USERS).set(USERS.ACTIVE_STATUS, false)
				.set(USERS.ACTIVE_DEACTIVE, false).where(USERS.USER_ID.eq(userId)).returning().fetchOne()
				.into(Users.class);

		Professor deletedProfessor = dslContext.selectFrom(PROFESSOR)
				.where(PROFESSOR.USER_ID.eq(deactivatedUser.getUserId())).fetchOne().into(Professor.class);

		if (deactivatedUser != null && deletedProfessor != null) {
			UserAndProfessor information = new UserAndProfessor(deactivatedUser.getUserId(),
					deactivatedUser.getUsername(), deactivatedUser.getPassword(), deactivatedUser.getEmail(),
					deactivatedUser.getContactNo(), deactivatedUser.getFirstName(), deactivatedUser.getMiddleName(),
					deactivatedUser.getLastName(), deactivatedUser.getUserType(), deactivatedUser.getBirthDate(),
					deactivatedUser.getAddress(), deactivatedUser.getCivilStatus(), deactivatedUser.getGender(),
					deactivatedUser.getNationality(), deactivatedUser.getActiveDeactive(),
					deactivatedUser.getActiveDeactive(), deactivatedUser.getImage(), deletedProfessor.getProfessorId(),
					deletedProfessor.getProfessorNo(), deletedProfessor.getWork());
			return information;
		}
		return null;
	}

	// ------------------------FOR PROFESSOR LOAD
	public List<Map<String, Object>> selectAllProfessorsLoad() {
		return dslContext
				.select(PROFESSOR_LOAD.LOAD_ID.as("loadId"), PROFESSOR_LOAD.PROFESSOR_NO.as("professorNo"),
						PROFESSOR_LOAD.SUBJECT_CODE.as("subjectCode"), SUBJECT.SUBJECT_TITLE.as("subjectTitle"),
						SECTION.SECTION_ID.as("sectionId"), SECTION.SECTION_NAME.as("sectionName"),
						ROOM.ROOM_ID.as("roomId"), ROOM.ROOM_NO.as("roomNo"), DEPARTMENT.DEPT_CODE.as("deptCode"),
						DEPARTMENT.DEPT_NAME.as("deptName"), PROFESSOR_LOAD.DAY,
						PROFESSOR_LOAD.START_TIME.as("startTime"), PROFESSOR_LOAD.END_TIME.as("endTime"),
						PROFESSOR_LOAD.ACTIVE_DEACTIVE.as("activeDeactive"))
				.from(PROFESSOR_LOAD).innerJoin(PROFESSOR).on(PROFESSOR_LOAD.PROFESSOR_NO.eq(PROFESSOR.PROFESSOR_NO))
				.innerJoin(SUBJECT).on(PROFESSOR_LOAD.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE)).innerJoin(SECTION)
				.on(PROFESSOR_LOAD.SECTION_ID.eq(SECTION.SECTION_ID)).innerJoin(ROOM)
				.on(PROFESSOR_LOAD.ROOM_ID.eq(ROOM.ROOM_ID)).innerJoin(DEPARTMENT)
				.on(PROFESSOR_LOAD.DEPT_CODE.eq(DEPARTMENT.DEPT_CODE)).where(PROFESSOR_LOAD.ACTIVE_DEACTIVE.eq(true))
				.fetchMaps();
	}

	public List<Map<String, Object>> selectProfessorLoad(Integer professorNo) {
		return dslContext
				.select(PROFESSOR_LOAD.LOAD_ID.as("loadId"), PROFESSOR_LOAD.PROFESSOR_NO.as("professorNo"),
						PROFESSOR_LOAD.SUBJECT_CODE.as("subjectCode"), SUBJECT.SUBJECT_TITLE.as("subjectTitle"),
						SECTION.SECTION_ID.as("sectionId"), SECTION.SECTION_NAME.as("sectionName"),
						ROOM.ROOM_ID.as("roomId"), ROOM.ROOM_NO.as("roomNo"), DEPARTMENT.DEPT_CODE.as("deptCode"),
						DEPARTMENT.DEPT_NAME.as("deptName"), PROFESSOR_LOAD.DAY,
						PROFESSOR_LOAD.START_TIME.as("startTime"), PROFESSOR_LOAD.END_TIME.as("endTime"),
						PROFESSOR_LOAD.ACTIVE_DEACTIVE.as("activeDeactive"))
				.from(PROFESSOR_LOAD).innerJoin(PROFESSOR).on(PROFESSOR_LOAD.PROFESSOR_NO.eq(PROFESSOR.PROFESSOR_NO))
				.innerJoin(SUBJECT).on(PROFESSOR_LOAD.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE)).innerJoin(SECTION)
				.on(PROFESSOR_LOAD.SECTION_ID.eq(SECTION.SECTION_ID)).innerJoin(ROOM)
				.on(PROFESSOR_LOAD.ROOM_ID.eq(ROOM.ROOM_ID)).innerJoin(DEPARTMENT)
				.on(PROFESSOR_LOAD.DEPT_CODE.eq(DEPARTMENT.DEPT_CODE))
				.where(PROFESSOR_LOAD.PROFESSOR_NO.eq(professorNo)).fetchMaps();
	}

	public Map<String, Object> insertProfessorLoad(ProfessorLoad professorLoad) {
		ProfessorLoad insertProfessorLoad = dslContext.insertInto(PROFESSOR_LOAD)
				.set(PROFESSOR_LOAD.PROFESSOR_NO, professorLoad.getProfessorNo())
				.set(PROFESSOR_LOAD.SUBJECT_CODE, professorLoad.getSubjectCode())
				.set(PROFESSOR_LOAD.SECTION_ID, professorLoad.getSectionId())
				.set(PROFESSOR_LOAD.ROOM_ID, professorLoad.getRoomId())
				.set(PROFESSOR_LOAD.DEPT_CODE, professorLoad.getDeptCode())
				.set(PROFESSOR_LOAD.DAY, professorLoad.getDay())
				.set(PROFESSOR_LOAD.START_TIME, professorLoad.getStartTime())
				.set(PROFESSOR_LOAD.END_TIME, professorLoad.getEndTime())
				.set(PROFESSOR_LOAD.ACTIVE_DEACTIVE, professorLoad.getActiveDeactive()).returning().fetchOne()
				.into(ProfessorLoad.class);

		Map<String, Object> insertedprofessorLoad = dslContext
				.select(PROFESSOR_LOAD.LOAD_ID.as("loadId"), PROFESSOR_LOAD.PROFESSOR_NO.as("professorNo"),
						PROFESSOR_LOAD.SUBJECT_CODE.as("subjectCode"), SUBJECT.SUBJECT_TITLE.as("subjectTitle"),
						SECTION.SECTION_ID.as("sectionId"), SECTION.SECTION_NAME.as("sectionName"),
						ROOM.ROOM_ID.as("roomId"), ROOM.ROOM_NO.as("roomNo"), DEPARTMENT.DEPT_CODE.as("deptCode"),
						DEPARTMENT.DEPT_NAME.as("deptName"), PROFESSOR_LOAD.DAY,
						PROFESSOR_LOAD.START_TIME.as("startTime"), PROFESSOR_LOAD.END_TIME.as("endTime"),
						PROFESSOR_LOAD.ACTIVE_DEACTIVE.as("activeDeactive"))
				.from(PROFESSOR_LOAD).innerJoin(PROFESSOR).on(PROFESSOR_LOAD.PROFESSOR_NO.eq(PROFESSOR.PROFESSOR_NO))
				.innerJoin(SUBJECT).on(PROFESSOR_LOAD.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE)).innerJoin(SECTION)
				.on(PROFESSOR_LOAD.SECTION_ID.eq(SECTION.SECTION_ID)).innerJoin(ROOM)
				.on(PROFESSOR_LOAD.ROOM_ID.eq(ROOM.ROOM_ID)).innerJoin(DEPARTMENT)
				.on(PROFESSOR_LOAD.DEPT_CODE.eq(DEPARTMENT.DEPT_CODE))
				.where(PROFESSOR_LOAD.LOAD_ID.eq(insertProfessorLoad.getLoadId())).fetchOneMap();

		return insertedprofessorLoad;
	}

	public Map<String, Object> updateProfessorLoad(ProfessorLoad professorLoad) {
		ProfessorLoad insertedProfessorLoad = dslContext.update(PROFESSOR_LOAD)
				.set(PROFESSOR_LOAD.PROFESSOR_NO, professorLoad.getProfessorNo())
				.set(PROFESSOR_LOAD.SUBJECT_CODE, professorLoad.getSubjectCode())
				.set(PROFESSOR_LOAD.SECTION_ID, professorLoad.getSectionId())
				.set(PROFESSOR_LOAD.ROOM_ID, professorLoad.getRoomId())
				.set(PROFESSOR_LOAD.DEPT_CODE, professorLoad.getDeptCode())
				.set(PROFESSOR_LOAD.DAY, professorLoad.getDay())
				.set(PROFESSOR_LOAD.START_TIME, professorLoad.getStartTime())
				.set(PROFESSOR_LOAD.END_TIME, professorLoad.getEndTime())
				.where(PROFESSOR_LOAD.LOAD_ID.eq(professorLoad.getLoadId())).returning().fetchOne()
				.into(ProfessorLoad.class);

		Map<String, Object> insertedprofessorLoad = dslContext
				.select(PROFESSOR_LOAD.LOAD_ID.as("loadId"), PROFESSOR_LOAD.PROFESSOR_NO.as("professorNo"),
						PROFESSOR_LOAD.SUBJECT_CODE.as("subjectCode"), SUBJECT.SUBJECT_TITLE.as("subjectTitle"),
						SECTION.SECTION_ID.as("sectionId"), SECTION.SECTION_NAME.as("sectionName"),
						ROOM.ROOM_ID.as("roomId"), ROOM.ROOM_NO.as("roomNo"), DEPARTMENT.DEPT_CODE.as("deptCode"),
						DEPARTMENT.DEPT_NAME.as("deptName"), PROFESSOR_LOAD.DAY,
						PROFESSOR_LOAD.START_TIME.as("startTime"), PROFESSOR_LOAD.END_TIME.as("endTime"),
						PROFESSOR_LOAD.ACTIVE_DEACTIVE.as("activeDeactive"))
				.from(PROFESSOR_LOAD).innerJoin(PROFESSOR).on(PROFESSOR_LOAD.PROFESSOR_NO.eq(PROFESSOR.PROFESSOR_NO))
				.innerJoin(SUBJECT).on(PROFESSOR_LOAD.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE)).innerJoin(SECTION)
				.on(PROFESSOR_LOAD.SECTION_ID.eq(SECTION.SECTION_ID)).innerJoin(ROOM)
				.on(PROFESSOR_LOAD.ROOM_ID.eq(ROOM.ROOM_ID)).innerJoin(DEPARTMENT)
				.on(PROFESSOR_LOAD.DEPT_CODE.eq(DEPARTMENT.DEPT_CODE))
				.where(PROFESSOR_LOAD.LOAD_ID.eq(insertedProfessorLoad.getLoadId())).fetchOneMap();

		return insertedprofessorLoad;
	}

	public Map<String, Object> deleteProfessorLoad(Integer loadId) {
		ProfessorLoad deletedLoad = dslContext.update(PROFESSOR_LOAD).set(PROFESSOR_LOAD.ACTIVE_DEACTIVE, false)
				.where(PROFESSOR_LOAD.LOAD_ID.eq(loadId)).returning().fetchOne().into(ProfessorLoad.class);

		Map<String, Object> deletedprofessorLoad = dslContext
				.select(PROFESSOR_LOAD.LOAD_ID.as("loadId"), PROFESSOR_LOAD.PROFESSOR_NO.as("professorNo"),
						PROFESSOR_LOAD.SUBJECT_CODE.as("subjectCode"), SUBJECT.SUBJECT_TITLE.as("subjectTitle"),
						SECTION.SECTION_ID.as("sectionId"), SECTION.SECTION_NAME.as("sectionName"),
						ROOM.ROOM_ID.as("roomId"), ROOM.ROOM_NO.as("roomNo"), DEPARTMENT.DEPT_CODE.as("deptCode"),
						DEPARTMENT.DEPT_NAME.as("deptName"), PROFESSOR_LOAD.DAY,
						PROFESSOR_LOAD.START_TIME.as("startTime"), PROFESSOR_LOAD.END_TIME.as("endTime"),
						PROFESSOR_LOAD.ACTIVE_DEACTIVE.as("activeDeactive"))
				.from(PROFESSOR_LOAD).innerJoin(PROFESSOR).on(PROFESSOR_LOAD.PROFESSOR_NO.eq(PROFESSOR.PROFESSOR_NO))
				.innerJoin(SUBJECT).on(PROFESSOR_LOAD.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE)).innerJoin(SECTION)
				.on(PROFESSOR_LOAD.SECTION_ID.eq(SECTION.SECTION_ID)).innerJoin(ROOM)
				.on(PROFESSOR_LOAD.ROOM_ID.eq(ROOM.ROOM_ID)).innerJoin(DEPARTMENT)
				.on(PROFESSOR_LOAD.DEPT_CODE.eq(DEPARTMENT.DEPT_CODE))
				.where(PROFESSOR_LOAD.LOAD_ID.eq(deletedLoad.getLoadId())).fetchOneMap();

		return deletedprofessorLoad;
	}

	// ------------------------FOR Parent
	public List<UserAndParent> selectAllParent() {
		return dslContext
				.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.FIRST_NAME, USERS.MIDDLE_NAME,
						USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE, USERS.ADDRESS, USERS.CIVIL_STATUS,
						USERS.GENDER, USERS.NATIONALITY, USERS.ACTIVE_DEACTIVE, USERS.IMAGE, PARENT.PARENT_ID)
				.from(USERS).innerJoin(PARENT).on(USERS.USER_ID.eq(PARENT.USER_ID))
				.where(USERS.ACTIVE_DEACTIVE.eq(true)).fetchInto(UserAndParent.class);
	}

	public UserAndParent updateParentInfo(UserAndParent parent) {
		Users updatedUser = dslContext.update(USERS).set(USERS.USERNAME, parent.getUsername())
				.set(USERS.PASSWORD, parent.getPassword()).set(USERS.FIRST_NAME, parent.getFirstName())
				.set(USERS.MIDDLE_NAME, parent.getMiddleName()).set(USERS.LAST_NAME, parent.getLastName())
				.set(USERS.USER_TYPE, parent.getUserType()).set(USERS.BIRTH_DATE, parent.getBirthDate())
				.set(USERS.ADDRESS, parent.getAddress()).set(USERS.CIVIL_STATUS, parent.getCivilStatus())
				.set(USERS.GENDER, parent.getGender()).set(USERS.NATIONALITY, parent.getNationality())
				.set(USERS.ACTIVE_DEACTIVE, parent.getActiveDeactive()).set(USERS.IMAGE, parent.getImage())
				.where(USERS.USER_ID.eq(parent.getUserId())).returning().fetchOne().into(Users.class);
		if (updatedUser != null) {
			UserAndParent newParentInfo = new UserAndParent(updatedUser.getUserId(), updatedUser.getUsername(),
					updatedUser.getPassword(), updatedUser.getFirstName(), updatedUser.getMiddleName(),
					updatedUser.getLastName(), updatedUser.getUserType(), updatedUser.getBirthDate(),
					updatedUser.getAddress(), updatedUser.getCivilStatus(), updatedUser.getGender(),
					updatedUser.getNationality(), updatedUser.getActiveStatus(), updatedUser.getActiveDeactive(),
					updatedUser.getImage(), parent.getParentId());
			return newParentInfo;

		}
		return null;
	}

	public UserAndParent changeParentAccountStatus(Integer userId, Boolean status) {
		Users deactivatedUser = dslContext.update(USERS).set(USERS.ACTIVE_STATUS, status)
				.where(USERS.USER_ID.eq(userId)).returning().fetchOne().into(Users.class);

		Parent deactivatedParent = dslContext.selectFrom(PARENT).where(PARENT.USER_ID.eq(deactivatedUser.getUserId()))
				.fetchOne().into(Parent.class);

		if (deactivatedUser != null && deactivatedParent != null) {
			UserAndParent deactivatedUserParent = new UserAndParent(deactivatedUser.getUserId(),
					deactivatedUser.getUsername(), deactivatedUser.getPassword(), deactivatedUser.getFirstName(),
					deactivatedUser.getMiddleName(), deactivatedUser.getLastName(), deactivatedUser.getUserType(),
					deactivatedUser.getBirthDate(), deactivatedUser.getAddress(), deactivatedUser.getCivilStatus(),
					deactivatedUser.getGender(), deactivatedUser.getNationality(), deactivatedUser.getActiveStatus(),
					deactivatedUser.getActiveDeactive(), deactivatedUser.getImage(), deactivatedParent.getParentId());
			return deactivatedUserParent;
		}
		return null;
	}

	public UserAndParent deleteParent(Integer userId) {
		Users deactivatedUser = dslContext.update(USERS).set(USERS.ACTIVE_STATUS, false)
				.set(USERS.ACTIVE_DEACTIVE, false).where(USERS.USER_ID.eq(userId)).returning().fetchOne()
				.into(Users.class);

		Parent deactivatedParent = dslContext.selectFrom(PARENT).where(PARENT.USER_ID.eq(deactivatedUser.getUserId()))
				.fetchOne().into(Parent.class);

		if (deactivatedUser != null && deactivatedParent != null) {
			UserAndParent deactivatedUserParent = new UserAndParent(deactivatedUser.getUserId(),
					deactivatedUser.getUsername(), deactivatedUser.getPassword(), deactivatedUser.getFirstName(),
					deactivatedUser.getMiddleName(), deactivatedUser.getLastName(), deactivatedUser.getUserType(),
					deactivatedUser.getBirthDate(), deactivatedUser.getAddress(), deactivatedUser.getCivilStatus(),
					deactivatedUser.getGender(), deactivatedUser.getNationality(), deactivatedUser.getActiveStatus(),
					deactivatedUser.getActiveDeactive(), deactivatedUser.getImage(), deactivatedParent.getParentId());
			return deactivatedUserParent;
		}
		return null;
	}

	// ------------------------FOR Applicants
	public List<StudentApplicant> selectAllStudentApplicants() {
		List<StudentApplicant> students = dslContext.selectFrom(STUDENT_APPLICANT).fetchInto(StudentApplicant.class);
		return students;
	}

	public StudentApplicant updateStudentApplicantStatus(StudentApplicant studentApplicant) {
		/*
		 * This will add the User's data limited to: username, password, first_name,
		 * middle_name, last_name, birth_date, address, civil_status, gender,
		 * nationality, active_deactive, image
		 */
		StudentApplicant applicant = dslContext.update(STUDENT_APPLICANT)
				.set(STUDENT_APPLICANT.DATE_ACCEPTED, studentApplicant.getDateAccepted())
				.set(STUDENT_APPLICANT.ACCEPTANCE_STATUS, studentApplicant.getAcceptanceStatus())
				.where(STUDENT_APPLICANT.STUDENT_APPLICANT_ID.eq(studentApplicant.getStudentApplicantId())).returning()
				.fetchOne().into(StudentApplicant.class);
		return applicant;
	}

	// -------------------------- FOR ACADEMIC YEAR
	public AcademicYear addAcademicYear(AcademicYear academicYear) {
		/*
		 * The academic data added is limited to: academic_year and status
		 */
		AcademicYear addedAcademicYear = dslContext.insertInto(ACADEMIC_YEAR)
				.set(ACADEMIC_YEAR.ACADEMIC_YEAR_, academicYear.getAcademicYear())
				.set(ACADEMIC_YEAR.STATUS, academicYear.getStatus()).returning().fetchOne().into(AcademicYear.class);
		return addedAcademicYear;
	}

	public AcademicYear updateAcademicYearStatus(AcademicYear academicYear) {
		AcademicYear addedAcademicYear = dslContext.update(ACADEMIC_YEAR)
				.set(ACADEMIC_YEAR.STATUS, academicYear.getStatus())
				.where(ACADEMIC_YEAR.ACADEMIC_YEAR_ID.eq(academicYear.getAcademicYearId())).returning().fetchOne()
				.into(AcademicYear.class);
		return addedAcademicYear;
	}

	// -------------------------- FOR DEPARTMENT
	public Department addDepartment(Department department) {
		/*
		 * The program data added is limited to: department_name
		 */
		return dslContext.insertInto(DEPARTMENT).set(DEPARTMENT.DEPT_NAME, department.getDeptName()).returning()
				.fetchOne().into(Department.class);
	}

	public Department editDepartment(Department updatedDepartment) {
		/*
		 * The program data added is limited to: department_name
		 */
		return dslContext.update(DEPARTMENT).set(DEPARTMENT.DEPT_NAME, updatedDepartment.getDeptName())
				.where(DEPARTMENT.DEPT_CODE.eq(updatedDepartment.getDeptCode())).returning().fetchOne()
				.into(Department.class);
	}

	// -------------------------- FOR PROGRAM
	public List<Program> selectAllProgram() {
		return dslContext.selectFrom(PROGRAM).fetchInto(Program.class);
	}

	public Program addProgram(Program program) {
		/*
		 * The program data added is limited to: program_title
		 */
		Program addedProgram = dslContext.insertInto(PROGRAM).set(PROGRAM.PROGRAM_TITLE, program.getProgramTitle())
				.returning().fetchOne().into(Program.class);

		return addedProgram;
	}

	public Program editProgram(Program program) {
		/*
		 * The program data added is limited to: program_title
		 */
		Program editProgram = dslContext.update(PROGRAM).set(PROGRAM.PROGRAM_TITLE, program.getProgramTitle())
				.where(PROGRAM.PROGRAM_CODE.eq(program.getProgramCode())).returning().fetchOne().into(Program.class);
		return editProgram;
	}

	// -------------------------- FOR DEPARTMENT
	public List<Department> selectAllDepartment() {
		return dslContext.selectFrom(DEPARTMENT).fetchInto(Department.class);
	}

	// -------------------------- FOR COURSE
	public List<Map<String, Object>> selectAllCourses() {
		List<Map<String, Object>> query = dslContext
				.select(COURSE.COURSE_ID.as("courseId"), COURSE.COURSE_CODE.as("courseCode"),
						COURSE.COURSE_TITLE.as("courseTitle"), DEPARTMENT.DEPT_CODE.as("deptCode"),
						DEPARTMENT.DEPT_NAME.as("deptName"), PROGRAM.PROGRAM_CODE.as("programCode"),
						PROGRAM.PROGRAM_TITLE.as("programTitle"))
				.from(COURSE).innerJoin(DEPARTMENT).on(COURSE.DEPT_CODE.eq(DEPARTMENT.DEPT_CODE)).innerJoin(PROGRAM)
				.on(COURSE.PROGRAM_CODE.eq(PROGRAM.PROGRAM_CODE)).fetchMaps();
		return !query.isEmpty() ? query : null;
	}

	public Map<String, Object> addCourse(Course course) {
		Course addedCourse = dslContext.insertInto(COURSE).set(COURSE.PROGRAM_CODE, course.getProgramCode())
				.set(COURSE.DEPT_CODE, course.getDeptCode()).set(COURSE.COURSE_TITLE, course.getCourseTitle())
				.returning().fetchOne().into(Course.class);
		Map<String, Object> query = dslContext
				.select(COURSE.COURSE_ID.as("courseId"), COURSE.COURSE_CODE.as("courseCode"),
						COURSE.COURSE_TITLE.as("courseTitle"), DEPARTMENT.DEPT_CODE.as("deptCode"),
						DEPARTMENT.DEPT_NAME.as("deptName"), PROGRAM.PROGRAM_CODE.as("programCode"),
						PROGRAM.PROGRAM_TITLE.as("programTitle"))
				.from(COURSE).innerJoin(DEPARTMENT).on(COURSE.DEPT_CODE.eq(DEPARTMENT.DEPT_CODE)).innerJoin(PROGRAM)
				.on(COURSE.PROGRAM_CODE.eq(PROGRAM.PROGRAM_CODE))
				.where(COURSE.COURSE_CODE.eq(addedCourse.getCourseCode())).fetchOneMap();

		return query;
	}

	public Map<String, Object> editCourse(Course course) {
		Course editedCourse = dslContext.update(COURSE).set(COURSE.PROGRAM_CODE, course.getProgramCode())
				.set(COURSE.DEPT_CODE, course.getDeptCode()).set(COURSE.COURSE_TITLE, course.getCourseTitle())
				.where(COURSE.COURSE_CODE.eq(course.getCourseCode())).returning().fetchOne().into(Course.class);

		Map<String, Object> query = dslContext
				.select(COURSE.COURSE_ID.as("courseId"), COURSE.COURSE_CODE.as("courseCode"),
						COURSE.COURSE_TITLE.as("courseTitle"), DEPARTMENT.DEPT_CODE.as("deptCode"),
						DEPARTMENT.DEPT_NAME.as("deptName"), PROGRAM.PROGRAM_CODE.as("programCode"),
						PROGRAM.PROGRAM_TITLE.as("programTitle"))
				.from(COURSE).innerJoin(DEPARTMENT).on(COURSE.DEPT_CODE.eq(DEPARTMENT.DEPT_CODE)).innerJoin(PROGRAM)
				.on(COURSE.PROGRAM_CODE.eq(PROGRAM.PROGRAM_CODE))
				.where(COURSE.COURSE_CODE.eq(editedCourse.getCourseCode())).fetchOneMap();
		return query;
	}

	// -------------------------- FOR MAJOR
	public List<Major> selectAllMajor() {
		return dslContext.selectFrom(MAJOR).fetchInto(Major.class);
	}

	// -------------------------- FOR CURRICULUM
	public List<Map<String, Object>> selectAllCurriculum() {
		List<Map<String, Object>> query = dslContext
				.select(CURRICULUM.CURRICULUM_ID.as("curriculumId"), CURRICULUM.CURRICULUM_CODE.as("curriculumCode"),
						CURRICULUM.CURRICULUM_NAME.as("curriculumName"), MAJOR.MAJOR_CODE.as("majorCode"),
						MAJOR.MAJOR_TITLE.as("majorTitle"), COURSE.COURSE_CODE.as("courseCode"),
						COURSE.COURSE_TITLE.as("courseTitle"), CURRICULUM.ACTIVE_DEACTIVE.as("activeDeactive"))
				.from(CURRICULUM).innerJoin(MAJOR).on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE)).innerJoin(COURSE)
				.on(MAJOR.COURSE_CODE.eq(COURSE.COURSE_CODE))
				.where(CURRICULUM.ACTIVE_DEACTIVE.eq(true).and(MAJOR.ACTIVE_DEACTIVE.eq(true))).fetchMaps();
		return !query.isEmpty() ? query : null;
	}

	// -------------------------- FOR CURRICULUM AND MAJOR
	public Map<String, Object> addCurriculumAndMajor(Map<String, Object> payload) {
		Major addedMajor = dslContext.insertInto(MAJOR)
				.set(MAJOR.COURSE_CODE, Integer.valueOf(payload.get("courseCode").toString()))
				.set(MAJOR.MAJOR_TITLE, payload.get("majorTitle").toString()).set(MAJOR.ACTIVE_DEACTIVE, true)
				.returning().fetchOne().into(Major.class);

		Curriculum addedCurriculum = dslContext.insertInto(CURRICULUM)
				.set(CURRICULUM.MAJOR_CODE, addedMajor.getMajorCode())
				.set(CURRICULUM.CURRICULUM_NAME, payload.get("curriculumName").toString())
				.set(CURRICULUM.ACTIVE_DEACTIVE, true).returning().fetchOne().into(Curriculum.class);

		Map<String, Object> query = dslContext
				.select(CURRICULUM.CURRICULUM_ID.as("curriculumId"), CURRICULUM.CURRICULUM_CODE.as("curriculumCode"),
						CURRICULUM.CURRICULUM_NAME.as("curriculumName"), MAJOR.MAJOR_CODE.as("majorCode"),
						MAJOR.MAJOR_TITLE.as("majorTitle"), COURSE.COURSE_CODE.as("courseCode"),
						COURSE.COURSE_TITLE.as("courseTitle"), CURRICULUM.ACTIVE_DEACTIVE.as("activeDeactive"))
				.from(CURRICULUM).innerJoin(MAJOR).on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE)).innerJoin(COURSE)
				.on(MAJOR.COURSE_CODE.eq(COURSE.COURSE_CODE))
				.where(CURRICULUM.CURRICULUM_CODE.eq(addedCurriculum.getCurriculumCode())).fetchOneMap();

		return query;
	}

	public Map<String, Object> editCurriculumAndMajor(Map<String, Object> payload) {
		Major updatedMajor = dslContext.update(MAJOR)
				.set(MAJOR.COURSE_CODE, Integer.valueOf(payload.get("courseCode").toString()))
				.set(MAJOR.MAJOR_TITLE, payload.get("majorTitle").toString()).set(MAJOR.ACTIVE_DEACTIVE, true)
				.where(MAJOR.MAJOR_CODE.eq(Integer.valueOf(payload.get("majorCode").toString()))).returning().fetchOne()
				.into(Major.class);

		Curriculum updatedCurriculum = dslContext.update(CURRICULUM)
				.set(CURRICULUM.MAJOR_CODE, updatedMajor.getMajorCode())
				.set(CURRICULUM.CURRICULUM_NAME, payload.get("curriculumName").toString())
				.set(CURRICULUM.ACTIVE_DEACTIVE, true)
				.where(CURRICULUM.CURRICULUM_CODE.eq(Integer.valueOf(payload.get("curriculumCode").toString())))
				.returning().fetchOne().into(Curriculum.class);

		Map<String, Object> query = dslContext
				.select(CURRICULUM.CURRICULUM_ID.as("curriculumId"), CURRICULUM.CURRICULUM_CODE.as("curriculumCode"),
						CURRICULUM.CURRICULUM_NAME.as("curriculumName"), MAJOR.MAJOR_CODE.as("majorCode"),
						MAJOR.MAJOR_TITLE.as("majorTitle"), COURSE.COURSE_CODE.as("courseCode"),
						COURSE.COURSE_TITLE.as("courseTitle"), CURRICULUM.ACTIVE_DEACTIVE.as("activeDeactive"))
				.from(CURRICULUM).innerJoin(MAJOR).on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE)).innerJoin(COURSE)
				.on(MAJOR.COURSE_CODE.eq(COURSE.COURSE_CODE))
				.where(CURRICULUM.CURRICULUM_CODE.eq(updatedCurriculum.getCurriculumCode())).fetchOneMap();

		return query;
	}

	public Map<String, Object> deleteCurriculumAndMajor(Integer curriculumCode) {
		Curriculum deletedCurriculum = dslContext.update(CURRICULUM).set(CURRICULUM.ACTIVE_DEACTIVE, false)
				.where(CURRICULUM.CURRICULUM_CODE.eq(curriculumCode)).returning().fetchOne().into(Curriculum.class);

		dslContext.update(MAJOR).set(MAJOR.ACTIVE_DEACTIVE, false)
				.where(MAJOR.MAJOR_CODE.eq(deletedCurriculum.getMajorCode())).returning().fetchOne()
				.into(Curriculum.class);

		Map<String, Object> query = dslContext
				.select(CURRICULUM.CURRICULUM_ID.as("curriculumId"), CURRICULUM.CURRICULUM_CODE.as("curriculumCode"),
						CURRICULUM.CURRICULUM_NAME.as("curriculumName"), MAJOR.MAJOR_CODE.as("majorCode"),
						MAJOR.MAJOR_TITLE.as("majorTitle"), COURSE.COURSE_CODE.as("courseCode"),
						COURSE.COURSE_TITLE.as("courseTitle"), CURRICULUM.ACTIVE_DEACTIVE.as("activeDeactive"))
				.from(CURRICULUM).innerJoin(MAJOR).on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE)).innerJoin(COURSE)
				.on(MAJOR.COURSE_CODE.eq(COURSE.COURSE_CODE))
				.where(CURRICULUM.CURRICULUM_CODE.eq(deletedCurriculum.getCurriculumCode())).fetchOneMap();
		return query;
	}

	// -------------------------- FOR SUBJECTS
	public List<Subject> selectAllSubject() {
		List<Subject> query = dslContext.selectFrom(SUBJECT).where(SUBJECT.SUBJECT_CODE.greaterThan(9000))
				.fetchInto(Subject.class);
		return !query.isEmpty() ? query : null;
	}

	// -------------------------- FOR SECTION
	public List<Section> selectAllSection() {
		List<Section> query = dslContext.selectFrom(SECTION).fetchInto(Section.class);
		return !query.isEmpty() ? query : null;
	}

	// -------------------------- FOR ROOM
	public List<Room> selectAllRoom() {
		List<Room> query = dslContext.selectFrom(ROOM).fetchInto(Room.class);
		return !query.isEmpty() ? query : null;
	}

	// Get All Minor Subject
	public List<Map<String, Object>> selectAllMinorSubjects() {
		List<Map<String, Object>> query = dslContext
				.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
						SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
						MINOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MINOR_SUBJECT.SEM.as("sem"),
						MINOR_SUBJECT.PRE_REQUISITES.as("preRequisites"), SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"),
						SUBJECT.ACTIVE_STATUS.as("activeStatus"))
				.from(SUBJECT).innerJoin(MINOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MINOR_SUBJECT.SUBJECT_CODE))
				.fetchMaps();

		return !query.isEmpty() ? query : null;
	}

	public Map<String, Object> inserMinorSubject(Map<String, Object> payload) {
		Subject subjectQuery = dslContext.insertInto(SUBJECT)
				.set(SUBJECT.ABBREVATION, payload.get("abbreviation").toString())
				.set(SUBJECT.SUBJECT_TITLE, payload.get("subjectTitle").toString())
				.set(SUBJECT.UNITS, Double.valueOf(payload.get("units").toString()))
				.set(SUBJECT.ACTIVE_STATUS, Boolean.valueOf(payload.get("activeStatus").toString()))
				.set(SUBJECT.ACTIVE_DEACTIVE, Boolean.valueOf(payload.get("activeDeactive").toString())).returning()
				.fetchOne().into(Subject.class);
		MinorSubject minorQuery = dslContext.insertInto(MINOR_SUBJECT)
				.set(MINOR_SUBJECT.SUBJECT_CODE, subjectQuery.getSubjectCode())
				.set(MINOR_SUBJECT.YEAR_LEVEL, Integer.valueOf(payload.get("yearLevel").toString()))
				.set(MINOR_SUBJECT.PRE_REQUISITES, Integer.valueOf(payload.get("preRequisites").toString()))
				.set(MINOR_SUBJECT.SEM, Integer.valueOf(payload.get("sem").toString())).returning().fetchOne()
				.into(MinorSubject.class);
		Map<String, Object> query = dslContext
				.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbrevation"),
						SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
						MINOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MINOR_SUBJECT.SEM.as("sem"),
						MINOR_SUBJECT.PRE_REQUISITES.as("preRequisites"), SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"),
						SUBJECT.ACTIVE_STATUS.as("activeStatus"))
				.from(SUBJECT).innerJoin(MINOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MINOR_SUBJECT.SUBJECT_CODE))
				.where(SUBJECT.SUBJECT_CODE.eq(minorQuery.getSubjectCode())).fetchOneMap();

		return !query.isEmpty() ? query : null;
	}

	public Map<String, Object> editMinorSubject(Map<String, Object> payload) {
		Subject updatedSubject = dslContext.update(SUBJECT)
				.set(SUBJECT.ABBREVATION, payload.get("abbreviation").toString())
				.set(SUBJECT.SUBJECT_TITLE, payload.get("subjectTitle").toString())
				.set(SUBJECT.UNITS, Double.valueOf(payload.get("units").toString()))
				.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("subjectCode").toString()))).returning()
				.fetchOne().into(Subject.class);

		dslContext.update(MINOR_SUBJECT)
				.set(MINOR_SUBJECT.PRE_REQUISITES, Integer.valueOf(payload.get("preRequisites").toString()))
				.set(MINOR_SUBJECT.YEAR_LEVEL, Integer.valueOf(payload.get("yearLevel").toString()))
				.set(MINOR_SUBJECT.SEM, Integer.valueOf(payload.get("sem").toString()))
				.where(MINOR_SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).returning().fetchOne()
				.into(MinorSubject.class);

		Map<String, Object> query = dslContext
				.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbrevation"),
						SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
						MINOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MINOR_SUBJECT.SEM.as("sem"),
						MINOR_SUBJECT.PRE_REQUISITES.as("preRequisites"), SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"),
						SUBJECT.ACTIVE_STATUS.as("activeStatus"))
				.from(SUBJECT).innerJoin(MINOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MINOR_SUBJECT.SUBJECT_CODE))
				.where(SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).fetchOneMap();
		return query;
	}

	public Map<String, Object> changeMinorSubjectStatus(Integer subjectCode, Boolean activeStatus) {
		Subject update = dslContext.update(SUBJECT).set(SUBJECT.ACTIVE_STATUS, activeStatus)
				.where(SUBJECT.SUBJECT_CODE.eq(subjectCode)).returning().fetchOne().into(Subject.class);
		if (update != null) {
			Map<String, Object> query = dslContext
					.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbrevation"),
							SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
							MINOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MINOR_SUBJECT.SEM.as("sem"),
							SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"))
					.from(SUBJECT).innerJoin(MINOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MINOR_SUBJECT.SUBJECT_CODE))
					.where(SUBJECT.SUBJECT_CODE.eq(subjectCode)).fetchOneMap();
			return !query.isEmpty() ? query : null;
		}
		return null;
	}
	
	//Get All MAJOR SUBJECTS BY CURRICULUM
	public List<Map<String, Object>> selecAllMajorSubjects() {
		List<Map<String, Object>> query = dslContext.select(
									MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"),
									MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"),
									MAJOR_SUBJECT.SEM.as("sem"),
									SUBJECT.SUBJECT_CODE.as("subjectCode"), 
									SUBJECT.ABBREVATION.as("abbreviation"),
									SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
									SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"),
									SUBJECT.ACTIVE_STATUS.as("activeStatus"),
									MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites")
									)
							.from(SUBJECT)
							.join(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
//							.where(MAJOR_SUBJECT.CURRICULUM_CODE.eq(5001))
							.groupBy(
							        MAJOR_SUBJECT.CURRICULUM_CODE,
							        MAJOR_SUBJECT.SUBJECT_CODE,
							        MAJOR_SUBJECT.YEAR_LEVEL,
							        MAJOR_SUBJECT.SEM,
							        MAJOR_SUBJECT.PRE_REQUISITES,
							        SUBJECT.SUBJECT_CODE, 
									SUBJECT.ABBREVATION,
									SUBJECT.SUBJECT_TITLE, SUBJECT.UNITS,
									SUBJECT.ACTIVE_DEACTIVE,
									SUBJECT.ACTIVE_STATUS
							    )
							.having(DSL.count(MAJOR_SUBJECT.SUBJECT_CODE).eq(1))
							.orderBy(MAJOR_SUBJECT.YEAR_LEVEL, MAJOR_SUBJECT.SEM)
							.fetchMaps();
		return query;
	}
	// Get All MAJOR SUBJECTS BY CURRICULUM
	public List<Map<String, Object>> selectAllMajorSubjects() {
		List<Map<String, Object>> query = dslContext
				.select(MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"), MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"),
						MAJOR_SUBJECT.SEM.as("sem"), SUBJECT.SUBJECT_CODE.as("subjectCode"),
						SUBJECT.ABBREVATION.as("abbrevation"), SUBJECT.SUBJECT_TITLE.as("subjectTitle"),
						SUBJECT.UNITS.as("units"), SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"),
						SUBJECT.ACTIVE_STATUS.as("activeStatus"))
				.from(SUBJECT).join(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
//							.where(MAJOR_SUBJECT.CURRICULUM_CODE.eq(5001))
				.groupBy(MAJOR_SUBJECT.CURRICULUM_CODE, MAJOR_SUBJECT.SUBJECT_CODE, MAJOR_SUBJECT.YEAR_LEVEL,
						MAJOR_SUBJECT.SEM, SUBJECT.SUBJECT_CODE, SUBJECT.ABBREVATION, SUBJECT.SUBJECT_TITLE,
						SUBJECT.UNITS, SUBJECT.ACTIVE_DEACTIVE, SUBJECT.ACTIVE_STATUS)
				.having(DSL.count(MAJOR_SUBJECT.SUBJECT_CODE).eq(1)).fetchMaps();
		return query;
	}

	public Map<String, Object> changeMajorSubjectStatus(Integer subjectCode, Boolean activeStatus) {
		Subject update = dslContext.update(SUBJECT).set(SUBJECT.ACTIVE_STATUS, activeStatus)
				.where(SUBJECT.SUBJECT_CODE.eq(subjectCode)).returning().fetchOne().into(Subject.class);
		if (update != null) {
			Map<String, Object> query = dslContext
					.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
							SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
							MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM.as("sem"),
							MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"),
							MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
							SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"))
					.from(SUBJECT).innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
					.where(SUBJECT.SUBJECT_CODE.eq(subjectCode)).fetchOneMap();
			return !query.isEmpty() ? query : null;
		}
		return null;
	}

	public Map<String, Object> editMajorSubject(Map<String, Object> payload) {
		if (Integer.valueOf(payload.get("subjectCode").toString()) != 9000) {
			Map<String, Object> checkQuery = dslContext
					.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
							SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
							MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM.as("sem"),
							MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
							MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"),
							SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"),
							SUBJECT.ACTIVE_STATUS.as("activeStatus"))
							.from(SUBJECT)
							.innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
							.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("preRequisites").toString())))
							.fetchOneMap();
			return checkQuery;
		}
		
		Subject updatedSubject = dslContext.update(SUBJECT)
				.set(SUBJECT.ABBREVATION, payload.get("abbreviation").toString())
				.set(SUBJECT.SUBJECT_TITLE, payload.get("subjectTitle").toString())
				.set(SUBJECT.UNITS, Double.valueOf(payload.get("units").toString()))
				.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("subjectCode").toString()))).returning()
				.fetchOne().into(Subject.class);
		dslContext.update(MAJOR_SUBJECT)
				.set(MAJOR_SUBJECT.PRE_REQUISITES, Integer.valueOf(payload.get("preRequisites").toString()))
				.set(MAJOR_SUBJECT.YEAR_LEVEL, Integer.valueOf(payload.get("yearLevel").toString()))
				.set(MAJOR_SUBJECT.SEM, Integer.valueOf(payload.get("sem").toString()))
				.where(MAJOR_SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).returning().fetchOne()
				.into(MajorSubject.class);

		Map<String, Object> query = dslContext
				.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
						SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
						MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM.as("sem"),
						MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"), SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"),
						SUBJECT.ACTIVE_STATUS.as("activeStatus"))
				.from(SUBJECT).innerJoin(MINOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MINOR_SUBJECT.SUBJECT_CODE))
				.where(SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).fetchOneMap();
		return query;
	}

	/*
	 * For 2nd Year and above Get All the student's passed major subject
	 * 
	 * The data getting from the student using studentNo are the ff:
	 * Curriculum_Code, Remarks, Subject_Title, Year_Level, Sem
	 * 
	 */
	public List<Map<String, Object>> selectStudentPassedMajorSubject(Integer studentApplicantId) {
		List<Map<String, Object>> student = dslContext
				.select(STUDENT.CURRICULUM_CODE.as("curriculumCode"), GRADES.REMARKS.as("remarks"),
						SUBJECT.SUBJECT_TITLE.as("subject_title"), MAJOR_SUBJECT.YEAR_LEVEL.as("year_level"),
						MAJOR_SUBJECT.SEM.as("sem"))
				.from(GRADES).innerJoin(STUDENT).on(GRADES.STUDENT_NO.eq(STUDENT.STUDENT_NO))
				.innerJoin(T_SUBJECT_DETAIL_HISTORY)
				.on(GRADES.SUBJECT_DETAIL_HIS_ID.eq(T_SUBJECT_DETAIL_HISTORY.SUBJECT_DETAIL_HIS_ID)).innerJoin(SUBJECT)
				.on(T_SUBJECT_DETAIL_HISTORY.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE)).innerJoin(MAJOR_SUBJECT)
				.on(MAJOR_SUBJECT.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE))
				.where(STUDENT.CURRICULUM_CODE.eq(MAJOR_SUBJECT.CURRICULUM_CODE)
						.and(STUDENT.YEAR_LEVEL.eq(MAJOR_SUBJECT.YEAR_LEVEL)
								.and(STUDENT.YEAR_LEVEL.ne(1).and(MAJOR_SUBJECT.SEM.ne(1)))))
				.groupBy(STUDENT.CURRICULUM_CODE, GRADES.REMARKS, SUBJECT.SUBJECT_TITLE, MAJOR_SUBJECT.YEAR_LEVEL,
						MAJOR_SUBJECT.SEM)
				.fetchMaps();
		return student;
	}

	/*
	 * For 2nd Year and above Get All the student's passed minor subject
	 * 
	 * The data getting from the student using studentNo are the ff:
	 * Curriculum_Code, Remarks, Subject_Title, Year_Level, Sem
	 * 
	 */
	public List<Map<String, Object>> selectStudentPassedMinorSubject(Integer studentApplicantId) {
		List<Map<String, Object>> student = dslContext
				.select(STUDENT.CURRICULUM_CODE.as("curriculumCode"), GRADES.REMARKS.as("remarks"),
						SUBJECT.SUBJECT_TITLE.as("subject_title"), MINOR_SUBJECT.YEAR_LEVEL.as("year_level"),
						MINOR_SUBJECT.SEM.as("sem"))
				.from(GRADES).innerJoin(STUDENT).on(GRADES.STUDENT_NO.eq(STUDENT.STUDENT_NO))
				.innerJoin(T_SUBJECT_DETAIL_HISTORY)
				.on(GRADES.SUBJECT_DETAIL_HIS_ID.eq(T_SUBJECT_DETAIL_HISTORY.SUBJECT_DETAIL_HIS_ID)).innerJoin(SUBJECT)
				.on(T_SUBJECT_DETAIL_HISTORY.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE)).innerJoin(MINOR_SUBJECT)
				.on(MINOR_SUBJECT.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE))
				.where(STUDENT.YEAR_LEVEL.eq(MINOR_SUBJECT.YEAR_LEVEL)
						.and(MINOR_SUBJECT.YEAR_LEVEL.ne(1).and(MINOR_SUBJECT.SEM.ne(1))))
				.groupBy(STUDENT.CURRICULUM_CODE, GRADES.REMARKS, SUBJECT.SUBJECT_TITLE, MINOR_SUBJECT.YEAR_LEVEL,
						MINOR_SUBJECT.SEM)
				.fetchMaps();
		return student;
	}

	/*
	 * For 1st Year Get All the student's major subject
	 * 
	 * The data getting from the student using studentNo are the ff:
	 * Curriculum_Code, Subject_Title, Year_Level, Sem
	 * 
	 */
	public List<Map<String, Object>> selectFreshManStudentMajorSubject(Integer studentApplicantId) {
		List<Map<String, Object>> student = dslContext
				.select(STUDENT.CURRICULUM_CODE.as("curriculumCode"), SUBJECT.SUBJECT_TITLE.as("subject_title"),
						MAJOR_SUBJECT.YEAR_LEVEL.as("year_level"), MAJOR_SUBJECT.SEM.as("sem"))
				.from(GRADES).innerJoin(STUDENT).on(GRADES.STUDENT_NO.eq(STUDENT.STUDENT_NO))
				.innerJoin(T_SUBJECT_DETAIL_HISTORY)
				.on(GRADES.SUBJECT_DETAIL_HIS_ID.eq(T_SUBJECT_DETAIL_HISTORY.SUBJECT_DETAIL_HIS_ID)).innerJoin(SUBJECT)
				.on(T_SUBJECT_DETAIL_HISTORY.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE)).innerJoin(MAJOR_SUBJECT)
				.on(MAJOR_SUBJECT.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE))
				.where(STUDENT.CURRICULUM_CODE.eq(MAJOR_SUBJECT.CURRICULUM_CODE)
						.and(STUDENT.YEAR_LEVEL.eq(MAJOR_SUBJECT.YEAR_LEVEL)
								.and(STUDENT.YEAR_LEVEL.eq(1).and(MAJOR_SUBJECT.SEM.eq(1)))))
				.groupBy(STUDENT.CURRICULUM_CODE, GRADES.REMARKS, SUBJECT.SUBJECT_TITLE, MAJOR_SUBJECT.YEAR_LEVEL,
						MAJOR_SUBJECT.SEM)
				.fetchMaps();
		return student;
	}

	/*
	 * For 1st Year Get All the student's minor subject
	 * 
	 * The data getting from the student using studentNo are the ff:
	 * Curriculum_Code, Subject_Title, Year_Level, Sem
	 * 
	 */
	public List<Map<String, Object>> selectFreshManStudentMinorSubject(Integer studentApplicantId) {
		List<Map<String, Object>> student = dslContext
				.select(STUDENT.CURRICULUM_CODE.as("curriculumCode"), SUBJECT.SUBJECT_TITLE.as("subject_title"),
						MINOR_SUBJECT.YEAR_LEVEL.as("year_level"), MINOR_SUBJECT.SEM.as("sem"))
				.from(GRADES).innerJoin(STUDENT).on(GRADES.STUDENT_NO.eq(STUDENT.STUDENT_NO))
				.innerJoin(T_SUBJECT_DETAIL_HISTORY)
				.on(GRADES.SUBJECT_DETAIL_HIS_ID.eq(T_SUBJECT_DETAIL_HISTORY.SUBJECT_DETAIL_HIS_ID)).innerJoin(SUBJECT)
				.on(T_SUBJECT_DETAIL_HISTORY.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE)).innerJoin(MINOR_SUBJECT)
				.on(MINOR_SUBJECT.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE))
				.where(STUDENT.YEAR_LEVEL.eq(MINOR_SUBJECT.YEAR_LEVEL)
						.and(MINOR_SUBJECT.YEAR_LEVEL.eq(1).and(MINOR_SUBJECT.SEM.eq(1))))
				.groupBy(STUDENT.CURRICULUM_CODE, GRADES.REMARKS, SUBJECT.SUBJECT_TITLE, MINOR_SUBJECT.YEAR_LEVEL,
						MINOR_SUBJECT.SEM)
				.fetchMaps();
		return student;
	}

}
