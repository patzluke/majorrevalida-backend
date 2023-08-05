package org.ssglobal.training.codes.repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.model.EnrollmentData;
import org.ssglobal.training.codes.model.UserAndAdmin;
import org.ssglobal.training.codes.model.UserAndParent;
import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.AcademicYear;
import org.ssglobal.training.codes.tables.pojos.Admin;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.Curriculum;
import org.ssglobal.training.codes.tables.pojos.Department;
import org.ssglobal.training.codes.tables.pojos.Grades;
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
import org.ssglobal.training.codes.tables.pojos.StudentEnrollment;
import org.ssglobal.training.codes.tables.pojos.StudentSubjectEnrolled;
import org.ssglobal.training.codes.tables.pojos.Subject;
import org.ssglobal.training.codes.tables.pojos.SubmittedSubjectsForEnrollment;
import org.ssglobal.training.codes.tables.pojos.TSubjectDetailHistory;
import org.ssglobal.training.codes.tables.pojos.Users;
import org.ssglobal.training.codes.tables.records.StudentAttendanceRecord;
import org.ssglobal.training.codes.tables.records.StudentScheduleRecord;

@Repository
public class AdminCapabilitiesRepository {

	@Autowired
	private DSLContext dslContext;

	private final org.ssglobal.training.codes.tables.Users USERS = org.ssglobal.training.codes.tables.Users.USERS;
	private final org.ssglobal.training.codes.tables.Admin ADMIN = org.ssglobal.training.codes.tables.Admin.ADMIN;
	private final org.ssglobal.training.codes.tables.Student STUDENT = org.ssglobal.training.codes.tables.Student.STUDENT;
	private final org.ssglobal.training.codes.tables.StudentEnrollment STUDENT_ENROLLMENT = org.ssglobal.training.codes.tables.StudentEnrollment.STUDENT_ENROLLMENT;
	private final org.ssglobal.training.codes.tables.StudentSubjectEnrolled STUDENT_SUBJECT_ENROLLED = org.ssglobal.training.codes.tables.StudentSubjectEnrolled.STUDENT_SUBJECT_ENROLLED;

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
	private final org.ssglobal.training.codes.tables.SubmittedSubjectsForEnrollment SUBMITTED_SUBJECTS_FOR_ENROLLMENT = org.ssglobal.training.codes.tables.SubmittedSubjectsForEnrollment.SUBMITTED_SUBJECTS_FOR_ENROLLMENT;

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
				.orderBy(ADMIN.ADMIN_NO).fetchInto(UserAndAdmin.class);
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
						MAJOR.MAJOR_CODE, MAJOR.MAJOR_TITLE, STUDENT.YEAR_LEVEL, SECTION.SECTION_NAME)
				.from(USERS).innerJoin(STUDENT).on(USERS.USER_ID.eq(STUDENT.USER_ID)).innerJoin(CURRICULUM)
				.on(STUDENT.CURRICULUM_CODE.eq(CURRICULUM.CURRICULUM_CODE)).innerJoin(MAJOR)
				.on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE)).innerJoin(COURSE)
				.on(MAJOR.COURSE_CODE.eq(COURSE.COURSE_CODE)).innerJoin(STUDENT_ENROLLMENT)
				.on(STUDENT.STUDENT_NO.eq(STUDENT_ENROLLMENT.STUDENT_NO)).innerJoin(SECTION)
				.on(STUDENT_ENROLLMENT.SECTION_ID.eq(SECTION.SECTION_ID))
				.where(USERS.ACTIVE_DEACTIVE.eq(true).and(USERS.USER_TYPE.eq("Student"))).orderBy(STUDENT.STUDENT_NO)
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

	// -------------------------- GETTING THE NUMBER OF STUDENT PER YEAR
	/*
	 * This query will return the ff: studentNo, academicYearId, academicYear,
	 * status
	 * 
	 */

	public List<Map<String, Object>> getAllStudentWithAcademicYear() {
		List<Map<String, Object>> student = dslContext
				.select(STUDENT_ENROLLMENT.STUDENT_NO.as("studentNo"),
						STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID.as("academicYearId"),
						ACADEMIC_YEAR.ACADEMIC_YEAR_.as("academicYear"), ACADEMIC_YEAR.STATUS.as("status"))
				.from(STUDENT_ENROLLMENT).innerJoin(ACADEMIC_YEAR)
				.on(STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID.eq(ACADEMIC_YEAR.ACADEMIC_YEAR_ID))
				.groupBy(STUDENT_ENROLLMENT.STUDENT_NO, STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID,
						ACADEMIC_YEAR.ACADEMIC_YEAR_, ACADEMIC_YEAR.STATUS)
				.fetchMaps();
		return student;
	}

	// Get all the enrollment data needed
	/*
	 * The data is limited to the studentNo, firstName, middleName, LastName
	 * majorCode, majorTitle, courseCode, courseTitle, status
	 */
	public List<Map<String, Object>> getAllEnrollmentData() {
		return dslContext
				.select(STUDENT_ENROLLMENT.ENROLLMENT_ID.as("enrollmentId"),
						STUDENT_ENROLLMENT.STUDENT_NO.as("studentNo"), USERS.FIRST_NAME.as("firstName"),
						USERS.MIDDLE_NAME.as("middleName"), USERS.LAST_NAME.as("lastName"),
						MAJOR.MAJOR_CODE.as("majorCode"), MAJOR.MAJOR_TITLE.as("majorTitle"),
						COURSE.COURSE_CODE.as("courseCode"), COURSE.COURSE_TITLE.as("courseTitle"),
						STUDENT.YEAR_LEVEL.as("yearLevel"), ACADEMIC_YEAR.SEMESTER,
						STUDENT_ENROLLMENT.STATUS.as("status"))
				.from(STUDENT_ENROLLMENT).innerJoin(ACADEMIC_YEAR)
				.on(STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID.eq(ACADEMIC_YEAR.ACADEMIC_YEAR_ID)).innerJoin(STUDENT)
				.on(STUDENT_ENROLLMENT.STUDENT_NO.eq(STUDENT.STUDENT_NO)).innerJoin(USERS)
				.on(STUDENT.USER_ID.eq(USERS.USER_ID)).innerJoin(CURRICULUM)
				.on(STUDENT.CURRICULUM_CODE.eq(CURRICULUM.CURRICULUM_CODE)).innerJoin(MAJOR)
				.on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE)).innerJoin(COURSE)
				.on(MAJOR.COURSE_CODE.eq(COURSE.COURSE_CODE)).fetchMaps();
	}

	// ------------------------FOR STUDENT_ENROLLMENT
	public UserAndStudent selectStudent(Integer studentNo) {
		return dslContext.select(STUDENT.STUDENT_NO.as("studentNo"), USERS.EMAIL.as("email")).from(STUDENT).join(USERS)
				.on(STUDENT.USER_ID.eq(USERS.USER_ID)).where(STUDENT.STUDENT_NO.eq(studentNo))
				.fetchOneInto(UserAndStudent.class);
	}

	public Map<String, Object> selectParentByStudent(Integer studentNo) {
		Map<String, Object> parentNo = dslContext.select(STUDENT.PARENT_NO.as("parentNo")).from(STUDENT)
				.where(STUDENT.STUDENT_NO.eq(studentNo)).fetchOneMap();
		Map<String, Object> parentInfo = dslContext.select(PARENT.PARENT_NO.as("parentNo"), USERS.EMAIL.as("email"))
				.from(PARENT).join(USERS).on(PARENT.USER_ID.eq(USERS.USER_ID))
				.where(PARENT.PARENT_NO.eq(Integer.valueOf(parentNo.get("parentNo").toString()))).fetchOneMap();
		return parentInfo;
	}

	public StudentEnrollment insertStudentEnrollmentData(StudentApplicant studentApplicant) {

		// Get the academic_year_id
		// The applicant can be applied to the academic year that is on process and
		// cannot be in
		// finished or on going academic school year

		AcademicYear applicantAcademicYear = dslContext.select(ACADEMIC_YEAR.ACADEMIC_YEAR_ID.as("academicYearId"))
				.from(ACADEMIC_YEAR)
				.where(ACADEMIC_YEAR.SEMESTER.eq(studentApplicant.getSemester()).and(ACADEMIC_YEAR.ACADEMIC_YEAR_
						.eq(studentApplicant.getSchoolYear()).and(ACADEMIC_YEAR.STATUS.eq("Process"))))
				.fetchOneInto(AcademicYear.class);

		LocalDate birthDate = studentApplicant.getBirthDate();
		String formattedBirthDate = null;

		if (birthDate != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			formattedBirthDate = birthDate.format(formatter);
		} else {
			System.out.println("Birthdate is null");
		}

		// Replace the white space with underscore
		String applicantLastName = studentApplicant.getLastName().replace(" ", "_");

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		// Combine formatted birthdate and last name with an underscore
		String password = "%s%s".formatted(formattedBirthDate, applicantLastName);
		System.out.println("User Password: %s".formatted(password));

		// Secure the password using bcrypt
		String bcryptPassword = passwordEncoder.encode(password);

		Users applicantUserData = dslContext.insertInto(USERS).set(USERS.USERNAME, "pendingUserName")
				.set(USERS.PASSWORD, bcryptPassword).set(USERS.EMAIL, studentApplicant.getEmail())
				.set(USERS.CONTACT_NO, studentApplicant.getMobileNo())
				.set(USERS.FIRST_NAME, studentApplicant.getFirstName())
				.set(USERS.MIDDLE_NAME, studentApplicant.getMiddleName())
				.set(USERS.LAST_NAME, studentApplicant.getLastName()).set(USERS.USER_TYPE, "Student")
				.set(USERS.BIRTH_DATE, studentApplicant.getBirthDate())
				.set(USERS.ADDRESS, studentApplicant.getAddress())
				.set(USERS.CIVIL_STATUS, studentApplicant.getCivilStatus())
				.set(USERS.GENDER, studentApplicant.getGender())
				.set(USERS.NATIONALITY, studentApplicant.getCitizenship()).set(USERS.ACTIVE_STATUS, true)
				.set(USERS.ACTIVE_DEACTIVE, true).returning().fetchOne().into(Users.class);

		// Get the userId of the current user by their username, firstname, lastname,
		// birthdate, type
		Users userId = dslContext.select(USERS.USER_ID.as("userId")).from(USERS)
				.where(USERS.USERNAME.eq(applicantUserData.getUsername())
						.and(USERS.FIRST_NAME.eq(applicantUserData.getFirstName()))
						.and(USERS.LAST_NAME.eq(applicantUserData.getLastName()))
						.and(USERS.BIRTH_DATE.eq(applicantUserData.getBirthDate()))
						.and(USERS.USER_TYPE.eq(applicantUserData.getUserType())))
				.fetchOneInto(Users.class);

		// --------------------- FOR PARENT

		// Current Updating code

		// Check if the parent exist
		Users userParent = dslContext.select(USERS.USER_ID.as("userId")).from(USERS)
				.where(USERS.FIRST_NAME.eq(studentApplicant.getGuardianFirstName())
						.and(USERS.MIDDLE_NAME.eq(studentApplicant.getGuardianMiddleName()))
						.and(USERS.LAST_NAME.eq(studentApplicant.getGuardianLastName()))
						.and(USERS.EMAIL.eq(studentApplicant.getGuardianEmail())))
				.fetchOneInto(Users.class);

		Users parentUserId = null;
		Curriculum curriculumCode = null;

		if (userParent == null) {

			// default password
			String defaultParentPassword = "123456";

			// Create User Parent
			dslContext.insertInto(USERS).set(USERS.USERNAME, "pendingParentUsername")
					.set(USERS.PASSWORD, defaultParentPassword).set(USERS.EMAIL, studentApplicant.getGuardianEmail())
					.set(USERS.CONTACT_NO, studentApplicant.getGuardianMobileNo())
					.set(USERS.FIRST_NAME, studentApplicant.getGuardianFirstName())
					.set(USERS.MIDDLE_NAME, studentApplicant.getGuardianMiddleName())
					.set(USERS.LAST_NAME, studentApplicant.getGuardianLastName()).set(USERS.USER_TYPE, "Parent")
					.set(USERS.ADDRESS, studentApplicant.getAddress()).set(USERS.ACTIVE_STATUS, true)
					.set(USERS.ACTIVE_DEACTIVE, true).returning().fetchOne().into(Users.class);

			// Get the parentUserId and lastName from user
			parentUserId = dslContext.select(USERS.USER_ID.as("userId"), USERS.LAST_NAME.as("lastName")).from(USERS)
					.where(USERS.EMAIL.equalIgnoreCase(studentApplicant.getGuardianEmail())
							.and(USERS.CONTACT_NO.eq(studentApplicant.getGuardianMobileNo()))
							.and(USERS.FIRST_NAME.equalIgnoreCase(studentApplicant.getGuardianFirstName()))
							.and(USERS.MIDDLE_NAME.equalIgnoreCase(studentApplicant.getGuardianMiddleName()))
							.and(USERS.LAST_NAME.equalIgnoreCase(studentApplicant.getGuardianLastName()))
							.and(USERS.USER_TYPE.equalIgnoreCase("Parent")))
					.fetchOneInto(Users.class);

			// Insert data to parent
			dslContext.insertInto(PARENT).set(PARENT.USER_ID, parentUserId.getUserId())
					.set(PARENT.OCCUPATION, studentApplicant.getGuardianOccupation())
					.set(PARENT.RELATION, studentApplicant.getGuardianRelation()).returning().fetchOne()
					.into(Parent.class);

			// Select parentId and parentNo from Parent
			Parent parentId = dslContext
					.select(PARENT.PARENT_ID.as("parentId"), PARENT.PARENT_NO.as("parentNo"),
							PARENT.USER_ID.as("userId"))
					.from(PARENT).where(PARENT.USER_ID.eq(parentUserId.getUserId())).fetchOneInto(Parent.class);

			// Select Curriculum code
			curriculumCode = dslContext.select(CURRICULUM.CURRICULUM_CODE.as("curriculumCode")).from(CURRICULUM)
					.where(CURRICULUM.MAJOR_CODE.eq(studentApplicant.getSelectedMajorCode()))
					.fetchOneInto(Curriculum.class);

			// Add the details to the student
			dslContext.insertInto(STUDENT).set(STUDENT.USER_ID, userId.getUserId())
					.set(STUDENT.PARENT_NO, parentId.getParentNo())
					.set(STUDENT.CURRICULUM_CODE, curriculumCode.getCurriculumCode())
					.set(STUDENT.ACADEMIC_YEAR_ID, applicantAcademicYear.getAcademicYearId())
					.set(STUDENT.YEAR_LEVEL, studentApplicant.getYearLevel()).returning().fetchOne()
					.into(Student.class);

			// Select the Student and get the studentNo
			Student studentNo = dslContext.select(STUDENT.STUDENT_NO.as("studentNo")).from(STUDENT)
					.where(STUDENT.USER_ID.eq(userId.getUserId())).fetchOneInto(Student.class);

			// Update the Username of the Parent
			String parentUsername = "%s_%s".formatted(String.valueOf(studentNo.getStudentNo()), "Parent");

			String parentPassword = "%s%s".formatted(String.valueOf(parentId.getParentNo()),
					parentUserId.getLastName().replace(" ", "_"));

			System.out.println("Parent Password: %s".formatted(parentPassword));

			BCryptPasswordEncoder parentPasswordEncoder = new BCryptPasswordEncoder();

			// Secure the password using bcrypt
			String bcryptParentPassword = parentPasswordEncoder.encode(parentPassword);

			dslContext.update(USERS).set(USERS.USERNAME, parentUsername).set(USERS.PASSWORD, bcryptParentPassword)
					.where(USERS.USER_ID.eq(parentId.getUserId())).returning().fetchOne().into(Users.class);

		} else {
			Parent checkParent = dslContext.select(PARENT.PARENT_NO).from(PARENT)
					.where(PARENT.USER_ID.eq(userParent.getUserId())).fetchOneInto(Parent.class);

			// Select Curriculum code
			curriculumCode = dslContext.select(CURRICULUM.CURRICULUM_CODE.as("curriculumCode")).from(CURRICULUM)
					.where(CURRICULUM.MAJOR_CODE.eq(studentApplicant.getSelectedMajorCode()))
					.fetchOneInto(Curriculum.class);

			// Add the details to the student
			dslContext.insertInto(STUDENT).set(STUDENT.USER_ID, userId.getUserId())
					.set(STUDENT.PARENT_NO, checkParent.getParentNo())
					.set(STUDENT.CURRICULUM_CODE, curriculumCode.getCurriculumCode())
					.set(STUDENT.ACADEMIC_YEAR_ID, applicantAcademicYear.getAcademicYearId())
					.set(STUDENT.YEAR_LEVEL, studentApplicant.getYearLevel()).returning().fetchOne()
					.into(Student.class);
		}

		// Select the Student and get the studentNo
		Student studentNo = dslContext.select(STUDENT.STUDENT_NO.as("studentNo")).from(STUDENT)
				.where(STUDENT.USER_ID.eq(userId.getUserId())).fetchOneInto(Student.class);

		// Update the Username of the User Applicant
		dslContext.update(USERS).set(USERS.USERNAME, String.valueOf(studentNo.getStudentNo()))
				.where(USERS.USER_ID.eq(userId.getUserId())).returning().fetchOne().into(Users.class);

		// From pending application to Not Enrolled
		studentApplicant.setAcceptanceStatus("Not Enrolled");

		return dslContext.insertInto(STUDENT_ENROLLMENT).set(STUDENT_ENROLLMENT.STUDENT_NO, studentNo.getStudentNo())
				.set(STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID, applicantAcademicYear.getAcademicYearId())
				.set(STUDENT_ENROLLMENT.STATUS, studentApplicant.getAcceptanceStatus()).returning().fetchOne()
				.into(StudentEnrollment.class);
	}

	// ------------------------Update the studentEnrollment's section and
	// paymentStatus
	public EnrollmentData fullyEnrollStudent(EnrollmentData student) {
		// update the data of the student to make them fully enrolled
		dslContext.update(STUDENT_ENROLLMENT).set(STUDENT_ENROLLMENT.SECTION_ID, student.getSectionId())
				.set(STUDENT_ENROLLMENT.PAYMENT_STATUS, student.getPaymentStatus())
				.set(STUDENT_ENROLLMENT.STATUS, "Enrolled")
				.where(STUDENT_ENROLLMENT.STUDENT_NO.eq(student.getStudentNo())).returning().fetchOne()
				.into(EnrollmentData.class);

		// return the data that is edited
		return dslContext
				.select(STUDENT_ENROLLMENT.ENROLLMENT_ID.as("enrollmentId"),
						STUDENT_ENROLLMENT.STUDENT_NO.as("studentNo"), STUDENT.CURRICULUM_CODE.as("curriculumCode"),
						USERS.FIRST_NAME.as("firstName"), USERS.MIDDLE_NAME.as("middleName"),
						USERS.LAST_NAME.as("lastName"), COURSE.COURSE_CODE.as("courseCode"),
						COURSE.COURSE_TITLE.as("courseTitle"), MAJOR.MAJOR_TITLE.as("majorTitle"),
						STUDENT.YEAR_LEVEL.as("yearLevel"), STUDENT_ENROLLMENT.STATUS.as("status"),
						STUDENT_ENROLLMENT.SECTION_ID.as("sectionId"),
						STUDENT_ENROLLMENT.PAYMENT_STATUS.as("paymentStatus"), STUDENT.YEAR_LEVEL.as("yearLevel"),
						ACADEMIC_YEAR.SEMESTER, STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID.as("academicYearId"))
				.from(STUDENT_ENROLLMENT).innerJoin(STUDENT).on(STUDENT_ENROLLMENT.STUDENT_NO.eq(STUDENT.STUDENT_NO))
				.innerJoin(ACADEMIC_YEAR).on(STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID.eq(ACADEMIC_YEAR.ACADEMIC_YEAR_ID))
				.innerJoin(USERS).on(STUDENT.USER_ID.eq(USERS.USER_ID)).innerJoin(CURRICULUM)
				.on(STUDENT.CURRICULUM_CODE.eq(CURRICULUM.CURRICULUM_CODE)).innerJoin(MAJOR)
				.on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE)).innerJoin(COURSE)
				.on(MAJOR.COURSE_CODE.eq(COURSE.COURSE_CODE))
				.where(STUDENT_ENROLLMENT.STUDENT_NO.eq(student.getStudentNo())).fetchOne().into(EnrollmentData.class);
	}

	public StudentSubjectEnrolled fullyEnrollStudentSubjects(Integer loadId, Integer enrollmentId) {
		// update the data of the student to make them fully enrolled
		StudentSubjectEnrolled studentSubjectEnrolled = dslContext.insertInto(STUDENT_SUBJECT_ENROLLED)
				.set(STUDENT_SUBJECT_ENROLLED.LOAD_ID, loadId).set(STUDENT_SUBJECT_ENROLLED.ENROLLMENT_ID, enrollmentId)
				.returning().fetchOne().into(StudentSubjectEnrolled.class);
		return studentSubjectEnrolled;
	}

	// -------------------------FOR Student Attendance
	public AcademicYear selectEnrolledSchoolYearOfStudent(Integer studentNo) {
		return dslContext.select(ACADEMIC_YEAR.START_DATE, ACADEMIC_YEAR.END_DATE, ACADEMIC_YEAR.ACADEMIC_YEAR_)
				.from(STUDENT_ENROLLMENT).innerJoin(ACADEMIC_YEAR)
				.on(STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID.eq(ACADEMIC_YEAR.ACADEMIC_YEAR_ID))
				.where(STUDENT_ENROLLMENT.STUDENT_NO.eq(studentNo))
				.orderBy(ACADEMIC_YEAR.ACADEMIC_YEAR_, ACADEMIC_YEAR.SEMESTER).fetchOneInto(AcademicYear.class);
	}

	public boolean batchInsertStudentAttendanceBySubject(List<StudentAttendanceRecord> studentAttendanceRecords) {
		dslContext.batchInsert(studentAttendanceRecords).execute();
		return true;
	}

	// -------------------------FOR Student Schedule
	public boolean batchInsertStudentScheduleBySubject(List<StudentScheduleRecord> studentScheduleRecords) {
		dslContext.batchInsert(studentScheduleRecords).execute();
		return true;
	}

	// ------------------------FOR PROFESSOR

	public List<UserAndProfessor> selectAllProfessor() {
		return dslContext
				.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.EMAIL, USERS.CONTACT_NO, USERS.FIRST_NAME,
						USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE, USERS.ADDRESS,
						USERS.CIVIL_STATUS, USERS.GENDER, USERS.NATIONALITY, USERS.ACTIVE_STATUS, USERS.ACTIVE_DEACTIVE,
						USERS.IMAGE, PROFESSOR.PROFESSOR_ID, PROFESSOR.PROFESSOR_NO, PROFESSOR.WORK)
				.from(USERS).innerJoin(PROFESSOR).on(USERS.USER_ID.eq(PROFESSOR.USER_ID))
				.orderBy(PROFESSOR.PROFESSOR_NO).fetchInto(UserAndProfessor.class);
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
				.orderBy(PROFESSOR_LOAD.LOAD_ID).fetchMaps();
	}

	public List<Map<String, Object>> selectProfessorLoadWithMajorSubjectBySectionAndCurriculumCode(Integer sectionId,
			Integer curriculumCode, Integer yearLevel, Integer sem) {
		return dslContext
				.select(PROFESSOR_LOAD.LOAD_ID.as("loadId"), PROFESSOR_LOAD.PROFESSOR_NO.as("professorNo"),
						PROFESSOR_LOAD.SUBJECT_CODE.as("subjectCode"), SUBJECT.SUBJECT_TITLE.as("subjectTitle"),
						SECTION.SECTION_NAME.as("sectionName"), MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"),
						MAJOR_SUBJECT.SEM)
				.from(PROFESSOR_LOAD).innerJoin(SECTION).on(PROFESSOR_LOAD.SECTION_ID.eq(SECTION.SECTION_ID))
				.innerJoin(SUBJECT).on(PROFESSOR_LOAD.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE)).innerJoin(MAJOR_SUBJECT)
				.on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
				.where(SECTION.SECTION_ID.eq(sectionId).and(MAJOR_SUBJECT.CURRICULUM_CODE.eq(curriculumCode))
						.and(MAJOR_SUBJECT.YEAR_LEVEL.eq(yearLevel)).and(MAJOR_SUBJECT.SEM.eq(sem))
						.and(PROFESSOR_LOAD.ACTIVE_DEACTIVE.eq(true)))
				.fetchMaps();
	}

	public List<Map<String, Object>> selectProfessorLoadWithMinorSubjectBySectionAndCurriculumCode(Integer sectionId,
			Integer yearLevel, Integer sem) {
		return dslContext
				.select(PROFESSOR_LOAD.LOAD_ID.as("loadId"), PROFESSOR_LOAD.PROFESSOR_NO.as("professorNo"),
						PROFESSOR_LOAD.SUBJECT_CODE.as("subjectCode"), SUBJECT.SUBJECT_TITLE.as("subjectTitle"),
						SECTION.SECTION_ID.as("sectionId"), SECTION.SECTION_NAME.as("sectionName"), PROFESSOR_LOAD.DAY,
						PROFESSOR_LOAD.START_TIME.as("startTime"), PROFESSOR_LOAD.END_TIME.as("endTime"),
						PROFESSOR_LOAD.ACTIVE_DEACTIVE.as("activeDeactive"))
				.from(PROFESSOR_LOAD).innerJoin(SECTION).on(PROFESSOR_LOAD.SECTION_ID.eq(SECTION.SECTION_ID))
				.innerJoin(SUBJECT).on(PROFESSOR_LOAD.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE)).innerJoin(MINOR_SUBJECT)
				.on(SUBJECT.SUBJECT_CODE.eq(MINOR_SUBJECT.SUBJECT_CODE)).where(SECTION.SECTION_ID.eq(sectionId)
						.and(MINOR_SUBJECT.YEAR_LEVEL.eq(yearLevel)).and(MINOR_SUBJECT.SEM.eq(sem)))
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
		System.out.println(professorLoad);
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
				.where(USERS.ACTIVE_DEACTIVE.eq(true)).orderBy(PARENT.PARENT_NO).fetchInto(UserAndParent.class);
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
		List<StudentApplicant> students = dslContext.selectFrom(STUDENT_APPLICANT)
				.orderBy(STUDENT_APPLICANT.DATE_APPLIED).fetchInto(StudentApplicant.class);
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
	public List<AcademicYear> selectAllAcademicYear() {
		return dslContext.selectFrom(ACADEMIC_YEAR).orderBy(ACADEMIC_YEAR.ACADEMIC_YEAR_.desc())
				.fetchInto(AcademicYear.class);
	}

	public AcademicYear addNewAcademicYear(AcademicYear academicYear) {
		return dslContext.insertInto(ACADEMIC_YEAR).set(ACADEMIC_YEAR.ACADEMIC_YEAR_, academicYear.getAcademicYear())
				.set(ACADEMIC_YEAR.START_DATE, academicYear.getStartDate())
				.set(ACADEMIC_YEAR.END_DATE, academicYear.getEndDate())
				.set(ACADEMIC_YEAR.SEMESTER, academicYear.getSemester())
				.set(ACADEMIC_YEAR.STATUS, academicYear.getStatus()).returning().fetchOne().into(AcademicYear.class);
	}

	public AcademicYear updateNewAcademicYear(AcademicYear academicYear) {
		return dslContext.update(ACADEMIC_YEAR).set(ACADEMIC_YEAR.ACADEMIC_YEAR_, academicYear.getAcademicYear())
				.set(ACADEMIC_YEAR.START_DATE, academicYear.getStartDate())
				.set(ACADEMIC_YEAR.END_DATE, academicYear.getEndDate())
				.set(ACADEMIC_YEAR.SEMESTER, academicYear.getSemester())
				.set(ACADEMIC_YEAR.STATUS, academicYear.getStatus())
				.where(ACADEMIC_YEAR.ACADEMIC_YEAR_ID.eq(academicYear.getAcademicYearId())).returning().fetchOne()
				.into(AcademicYear.class);
	}

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
		return dslContext.selectFrom(PROGRAM).where(PROGRAM.ACTIVE_DEACTIVE.eq(true)).orderBy(PROGRAM.PROGRAM_CODE)
				.fetchInto(Program.class);
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
				.set(PROGRAM.ACTIVE_DEACTIVE, program.getActiveDeactive())
				.where(PROGRAM.PROGRAM_CODE.eq(program.getProgramCode())).returning().fetchOne().into(Program.class);
		return editProgram;
	}

	// -------------------------- FOR DEPARTMENT
	public List<Department> selectAllDepartment() {
		return dslContext.selectFrom(DEPARTMENT).where(DEPARTMENT.ACTIVE_DEACTIVE.eq(true))
				.orderBy(DEPARTMENT.DEPT_CODE).fetchInto(Department.class);
	}

	public Department updateDepartment(Department department) {
		Department updated = dslContext.update(DEPARTMENT).set(DEPARTMENT.DEPT_NAME, department.getDeptName())
				.where(DEPARTMENT.DEPT_CODE.eq(department.getDeptCode())).returning().fetchOne().into(Department.class);
		if (updated.getDeptCode() != null) {
			return dslContext.selectFrom(DEPARTMENT).where(DEPARTMENT.DEPT_CODE.eq(updated.getDeptCode())).fetchOne()
					.into(Department.class);
		} else {
			return null;
		}

	}

	public Department insertDepartment(Department department) {
		Department updated = dslContext.insertInto(DEPARTMENT).set(DEPARTMENT.DEPT_NAME, department.getDeptName())
				.returning().fetchOne().into(Department.class);
		if (updated.getDeptCode() != null) {
			return dslContext.selectFrom(DEPARTMENT).where(DEPARTMENT.DEPT_CODE.eq(updated.getDeptCode())).fetchOne()
					.into(Department.class);
		} else {
			return null;
		}

	}

	public Department deleteDepartment(Department department) {
		Department updated = dslContext.update(DEPARTMENT).set(DEPARTMENT.ACTIVE_DEACTIVE, Boolean.valueOf(false))
				.where(DEPARTMENT.DEPT_CODE.eq(department.getDeptCode())).returning().fetchOne().into(Department.class);
		if (updated.getDeptCode() != null) {
			return dslContext.selectFrom(DEPARTMENT).where(DEPARTMENT.DEPT_CODE.eq(updated.getDeptCode())).fetchOne()
					.into(Department.class);
		} else {
			return null;
		}
	}

	// -------------------------- FOR COURSE
	public List<Map<String, Object>> selectAllCourses() {
		List<Map<String, Object>> query = dslContext
				.select(COURSE.COURSE_ID.as("courseId"), COURSE.COURSE_CODE.as("courseCode"),
						COURSE.COURSE_TITLE.as("courseTitle"), DEPARTMENT.DEPT_CODE.as("deptCode"),
						DEPARTMENT.DEPT_NAME.as("deptName"), PROGRAM.PROGRAM_CODE.as("programCode"),
						PROGRAM.PROGRAM_TITLE.as("programTitle"))
				.from(COURSE).innerJoin(DEPARTMENT).on(COURSE.DEPT_CODE.eq(DEPARTMENT.DEPT_CODE)).innerJoin(PROGRAM)
				.on(COURSE.PROGRAM_CODE.eq(PROGRAM.PROGRAM_CODE)).orderBy(COURSE.COURSE_CODE).fetchMaps();
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
		return dslContext.selectFrom(MAJOR).orderBy(MAJOR.MAJOR_CODE).fetchInto(Major.class);
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
				.where(CURRICULUM.ACTIVE_DEACTIVE.eq(true).and(MAJOR.ACTIVE_DEACTIVE.eq(true)))
				.orderBy(CURRICULUM.CURRICULUM_CODE).fetchMaps();
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

		List<Curriculum> allCurriculum = dslContext
				.select(CURRICULUM.CURRICULUM_CODE, CURRICULUM.CURRICULUM_ID, CURRICULUM.CURRICULUM_NAME,
						CURRICULUM.MAJOR_CODE, CURRICULUM.ACTIVE_DEACTIVE)
				.from(CURRICULUM).join(MAJOR).on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE)).where(MAJOR.COURSE_CODE
						.eq(Integer.valueOf(payload.get("courseCode").toString())).and(MAJOR.ACTIVE_DEACTIVE.eq(true)))
				.fetchInto(Curriculum.class);

		if (!allCurriculum.isEmpty()) {
			List<Map<String, Object>> majorSubjects = dslContext
					.select(SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.ABBREVATION.as("abbreviation"),
							SUBJECT.UNITS.as("units"), SUBJECT.ACTIVE_STATUS.as("activeStatus"),
							SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), MAJOR_SUBJECT.SUBJECT_CODE.as("subjectCode"),
							MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
							MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"),
							MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM.as("sem"))
					.from(SUBJECT).join(MAJOR_SUBJECT).on(
							SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
					.join(CURRICULUM).on(MAJOR_SUBJECT.CURRICULUM_CODE.eq(CURRICULUM.CURRICULUM_CODE)).join(MAJOR)
					.on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE))
					.where(MAJOR_SUBJECT.SUBJECT_CODE
							.in(dslContext.selectDistinct(MAJOR_SUBJECT.SUBJECT_CODE).from(MAJOR_SUBJECT)
									.groupBy(MAJOR_SUBJECT.SUBJECT_CODE).having(DSL.count().greaterThan(1)))
							.and(MAJOR.COURSE_CODE.eq(Integer.valueOf(payload.get("courseCode").toString())))
							.and(MAJOR_SUBJECT.CURRICULUM_CODE.eq(allCurriculum.get(0).getCurriculumCode()))
							.and(SUBJECT.ACTIVE_DEACTIVE.eq(true)))
					.fetchMaps();
			System.out.println(majorSubjects);
			if (allCurriculum.size() > 1 && !majorSubjects.isEmpty()) {
				majorSubjects.forEach((subject) -> {
					dslContext.insertInto(MAJOR_SUBJECT)
							.set(MAJOR_SUBJECT.SUBJECT_CODE, Integer.valueOf(subject.get("subjectCode").toString()))
							.set(MAJOR_SUBJECT.PRE_REQUISITES, Integer.valueOf(subject.get("preRequisites").toString()))
							.set(MAJOR_SUBJECT.CURRICULUM_CODE, Integer.valueOf(addedCurriculum.getCurriculumCode()))
							.set(MAJOR_SUBJECT.SEM, Integer.valueOf(subject.get("sem").toString()))
							.set(MAJOR_SUBJECT.YEAR_LEVEL, Integer.valueOf(subject.get("yearLevel").toString()))
							.returning().fetchOne().into(MajorSubject.class);
					System.out.println(subject);
				});

			}
		}

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
		List<Subject> query = dslContext.selectFrom(SUBJECT)
				.where(SUBJECT.SUBJECT_CODE.greaterThan(9000).and(SUBJECT.ACTIVE_DEACTIVE.eq(true)))
				.orderBy(SUBJECT.SUBJECT_CODE).fetchInto(Subject.class);
		return !query.isEmpty() ? query : null;
	}

	// -------------------------- FOR SECTION
	public List<Map<String, Object>> selectAllSection() {
		List<Map<String, Object>> query = dslContext
				.select(SECTION.SECTION_ID.as("sectionId"), SECTION.MAJOR_CODE.as("majorCode"),
						SECTION.SECTION_NAME.as("sectionName"), MAJOR.COURSE_CODE.as("courseCode"),
						MAJOR.MAJOR_TITLE.as("majorTitle"), COURSE.COURSE_TITLE.as("courseTitle"))
				.from(SECTION).innerJoin(MAJOR).on(SECTION.MAJOR_CODE.eq(MAJOR.MAJOR_CODE)).join(COURSE)
				.on(MAJOR.COURSE_CODE.eq(COURSE.COURSE_CODE)).orderBy(SECTION.SECTION_ID).fetchMaps();
		return !query.isEmpty() ? query : null;
	}

	public Map<String, Object> addSection(Section section) {
		Section addSection = dslContext.insertInto(SECTION)
				.set(SECTION.SECTION_NAME, section.getSectionName())
				.set(SECTION.MAJOR_CODE, section.getMajorCode())
				.returning().fetchOne().into(Section.class);
		Map<String, Object> query = dslContext
				.select(SECTION.SECTION_ID.as("sectionId"), SECTION.MAJOR_CODE.as("majorCode"),
						SECTION.SECTION_NAME.as("sectionName"), MAJOR.COURSE_CODE.as("courseCode"),
						MAJOR.MAJOR_TITLE.as("majorTitle"), COURSE.COURSE_TITLE.as("courseTitle"))
				.from(SECTION).innerJoin(MAJOR).on(SECTION.MAJOR_CODE.eq(MAJOR.MAJOR_CODE)).join(COURSE)
				.on(MAJOR.COURSE_CODE.eq(COURSE.COURSE_CODE)).where(SECTION.SECTION_ID.eq(addSection.getSectionId()))
				.fetchOneMap();
		return !query.isEmpty() ? query : null;
	}

	public Map<String, Object> updateSection(Section section) {
		Section addSection = dslContext.update(SECTION).set(SECTION.SECTION_NAME, section.getSectionName())
				.set(SECTION.MAJOR_CODE, section.getMajorCode()).where(SECTION.SECTION_ID.eq(section.getSectionId()))
				.returning().fetchOne().into(Section.class);
		Map<String, Object> query = dslContext
				.select(SECTION.SECTION_ID.as("sectionId"), SECTION.MAJOR_CODE.as("majorCode"),
						SECTION.SECTION_NAME.as("sectionName"), MAJOR.COURSE_CODE.as("courseCode"),
						MAJOR.MAJOR_TITLE.as("majorTitle"), COURSE.COURSE_TITLE.as("courseTitle"))
				.from(SECTION).innerJoin(MAJOR).on(SECTION.MAJOR_CODE.eq(MAJOR.MAJOR_CODE)).join(COURSE)
				.on(MAJOR.COURSE_CODE.eq(COURSE.COURSE_CODE)).where(SECTION.SECTION_ID.eq(addSection.getSectionId()))
				.fetchOneMap();
		return !query.isEmpty() ? query : null;
	}

	// -------------------------- FOR ROOM
	public List<Room> selectAllRoom() {
		List<Room> query = dslContext.selectFrom(ROOM).orderBy(ROOM.ROOM_ID).fetchInto(Room.class);
		return !query.isEmpty() ? query : null;
	}

	// ------------ FOR GRADES
	public List<Map<String, Object>> selectAllStudentsBySection() {
		return dslContext
				.select(GRADES.GRADE_ID.as("gradeId"), GRADES.STUDENT_NO.as("studentNo"),
						USERS.FIRST_NAME.as("firstName"), USERS.MIDDLE_NAME.as("middleName"),
						USERS.LAST_NAME.as("lastName"), USERS.EMAIL, GRADES.PRELIM_GRADE.as("prelimGrade"),
						GRADES.FINALS_GRADE.as("finalsGrade"), GRADES.COMMENT, GRADES.REMARKS,
						SECTION.SECTION_NAME.as("sectionName"), T_SUBJECT_DETAIL_HISTORY.SUBJECT_CODE.as("subjectCode"),
						SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.ABBREVATION)
				.from(GRADES).innerJoin(STUDENT).on(GRADES.STUDENT_NO.eq(STUDENT.STUDENT_NO)).innerJoin(USERS)
				.on(STUDENT.USER_ID.eq(USERS.USER_ID)).innerJoin(STUDENT_ENROLLMENT)
				.on(STUDENT.STUDENT_NO.eq(STUDENT_ENROLLMENT.STUDENT_NO)).innerJoin(SECTION)
				.on(STUDENT_ENROLLMENT.SECTION_ID.eq(SECTION.SECTION_ID)).innerJoin(T_SUBJECT_DETAIL_HISTORY)
				.on(GRADES.SUBJECT_DETAIL_HIS_ID.eq(T_SUBJECT_DETAIL_HISTORY.SUBJECT_DETAIL_HIS_ID)).innerJoin(SUBJECT)
				.on(T_SUBJECT_DETAIL_HISTORY.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE)).where(GRADES.IS_SUBMITTED.eq(true))
				.orderBy(GRADES.GRADE_ID).fetchMaps();
	}

	public List<Map<String, Object>> selectAllBatchYearBySection(Integer sectionId) {
		return dslContext.select(ACADEMIC_YEAR.ACADEMIC_YEAR_.as("academicYear"), ACADEMIC_YEAR.SEMESTER.as("semester"))
				.from(ACADEMIC_YEAR)
				.whereExists(DSL.selectOne().from(STUDENT_ENROLLMENT).where(ACADEMIC_YEAR.ACADEMIC_YEAR_ID
						.eq(STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID).and(STUDENT_ENROLLMENT.SECTION_ID.eq(sectionId))))
				.fetchMaps();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean insertGradesAndt_subject_detail_history(Integer professorNo, Integer subjectCode,
			Integer academicYearId, Integer studentNo, Integer enrollSubjectId) {
		
		List<Map<String, Object>> gradesAndSubjectHistory = selectSubjectDetailHistoryInnerjoinGradesByStudentNoAndAcademicYear(academicYearId, studentNo);
		if (gradesAndSubjectHistory != null) {
			for (Iterator iterator = gradesAndSubjectHistory.iterator(); iterator.hasNext();) {
				Map<String, Object> data = (Map<String, Object>) iterator.next();
				if (Integer.valueOf(data.get("subjectCode").toString()).equals(subjectCode)) {
					return false;
				}
			}
		}
		
		TSubjectDetailHistory insertedTSubjectDetailHistory = dslContext.insertInto(T_SUBJECT_DETAIL_HISTORY)
				.set(T_SUBJECT_DETAIL_HISTORY.PROFESSOR_NO, professorNo)
				.set(T_SUBJECT_DETAIL_HISTORY.SUBJECT_CODE, subjectCode)
				.set(T_SUBJECT_DETAIL_HISTORY.ACADEMIC_YEAR_ID, academicYearId).returning().fetchOne()
				.into(TSubjectDetailHistory.class);

		Grades grade = dslContext.insertInto(GRADES).set(GRADES.STUDENT_NO, studentNo)
				.set(GRADES.SUBJECT_DETAIL_HIS_ID, insertedTSubjectDetailHistory.getSubjectDetailHisId())
				.set(GRADES.ENROLL_SUBJECT_ID, enrollSubjectId).returning().fetchOne().into(Grades.class);

		if (insertedTSubjectDetailHistory != null && grade != null) {
			return true;
		}
		return false;
	}
	
	public List<Map<String, Object>> selectSubjectDetailHistoryInnerjoinGradesByStudentNoAndAcademicYear(Integer academicYearId, Integer studentNo) {
		List<Map<String, Object>> query = dslContext
				.select(T_SUBJECT_DETAIL_HISTORY.SUBJECT_DETAIL_HIS_ID.as("subjectDetailHisId"), T_SUBJECT_DETAIL_HISTORY.PROFESSOR_NO.as("professorNo"),
						T_SUBJECT_DETAIL_HISTORY.SUBJECT_CODE.as("subjectCode"), T_SUBJECT_DETAIL_HISTORY.ACADEMIC_YEAR_ID.as("academicYearId"),
						GRADES.GRADE_ID.as("gradeId"), GRADES.STUDENT_NO.as("studentNo"))
				.from(GRADES)
				.innerJoin(T_SUBJECT_DETAIL_HISTORY).on(GRADES.SUBJECT_DETAIL_HIS_ID.eq(T_SUBJECT_DETAIL_HISTORY.SUBJECT_DETAIL_HIS_ID))
				.where(T_SUBJECT_DETAIL_HISTORY.ACADEMIC_YEAR_ID.eq(academicYearId)
						.and(GRADES.STUDENT_NO.eq(studentNo)))
				.fetchMaps();
		return !query.isEmpty() ? query : null;
	}

	// -------------------------- Get All Minor Subject
	public List<Map<String, Object>> selectAllMinorSubjects() {
		List<Map<String, Object>> query = dslContext
				.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
						SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"), SUBJECT.PRICE.as("price"),
						MINOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MINOR_SUBJECT.SEM.as("sem"),
						MINOR_SUBJECT.PRE_REQUISITES.as("preRequisites"), SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"),
						SUBJECT.ACTIVE_STATUS.as("activeStatus"))
				.from(SUBJECT).innerJoin(MINOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MINOR_SUBJECT.SUBJECT_CODE))
				.where(SUBJECT.ACTIVE_DEACTIVE.eq(true)).orderBy(MINOR_SUBJECT.YEAR_LEVEL, MINOR_SUBJECT.SEM)
				.fetchMaps();

		return !query.isEmpty() ? query : null;
	}

	public Map<String, Object> inserMinorSubject(Map<String, Object> payload) throws Exception {

		Map<String, Object> preRequisites = dslContext
				.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.SUBJECT_TITLE.as("subjectTitle"),
						MINOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MINOR_SUBJECT.SEM.as("sem"))
				.from(SUBJECT).join(MINOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MINOR_SUBJECT.SUBJECT_CODE))
				.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("preRequisites").toString()))).fetchOneMap();

		if (preRequisites != null) {
			Integer preYear = Integer.valueOf(preRequisites.get("yearLevel").toString());
			Integer payloadYear = Integer.valueOf(payload.get("yearLevel").toString());
			Integer preSem = Integer.valueOf(preRequisites.get("sem").toString());
			Integer payloadSem = Integer.valueOf(payload.get("sem").toString());

			if (preYear < payloadYear || (preYear == payloadYear && preSem < payloadSem)) {
				Subject subjectQuery = dslContext.insertInto(SUBJECT)
						.set(SUBJECT.ABBREVATION, payload.get("abbreviation").toString())
						.set(SUBJECT.SUBJECT_TITLE, payload.get("subjectTitle").toString())
						.set(SUBJECT.UNITS, Double.valueOf(payload.get("units").toString()))
						.set(SUBJECT.PRICE, Double.valueOf(payload.get("price").toString()))
						.set(SUBJECT.ACTIVE_STATUS, Boolean.valueOf(payload.get("activeStatus").toString()))
						.set(SUBJECT.ACTIVE_DEACTIVE, Boolean.valueOf(payload.get("activeDeactive").toString()))
						.returning().fetchOne().into(Subject.class);
				MinorSubject minorQuery = dslContext.insertInto(MINOR_SUBJECT)
						.set(MINOR_SUBJECT.SUBJECT_CODE, subjectQuery.getSubjectCode())
						.set(MINOR_SUBJECT.YEAR_LEVEL, Integer.valueOf(payload.get("yearLevel").toString()))
						.set(MINOR_SUBJECT.PRE_REQUISITES, Integer.valueOf(payload.get("preRequisites").toString()))
						.set(MINOR_SUBJECT.SEM, Integer.valueOf(payload.get("sem").toString())).returning().fetchOne()
						.into(MinorSubject.class);
				Map<String, Object> query = dslContext
						.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
								SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
								SUBJECT.PRICE.as("price"), MINOR_SUBJECT.YEAR_LEVEL.as("yearLevel"),
								MINOR_SUBJECT.SEM.as("sem"), MINOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
								SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"))
						.from(SUBJECT).innerJoin(MINOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MINOR_SUBJECT.SUBJECT_CODE))
						.where(SUBJECT.SUBJECT_CODE.eq(minorQuery.getSubjectCode())).fetchOneMap();

				return !query.isEmpty() ? query : null;
			} else {
				throw new Exception(
						"%s is at year %s and sem %s".formatted(preRequisites.get("subjectTitle").toString(),
								preRequisites.get("yearLevel").toString(), preRequisites.get("sem").toString()));
			}
		} else {
			Subject subjectQuery = dslContext.insertInto(SUBJECT)
					.set(SUBJECT.ABBREVATION, payload.get("abbreviation").toString())
					.set(SUBJECT.SUBJECT_TITLE, payload.get("subjectTitle").toString())
					.set(SUBJECT.UNITS, Double.valueOf(payload.get("units").toString()))
					.set(SUBJECT.PRICE, Double.valueOf(payload.get("price").toString()))
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
					.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
							SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
							SUBJECT.PRICE.as("price"), MINOR_SUBJECT.YEAR_LEVEL.as("yearLevel"),
							MINOR_SUBJECT.SEM.as("sem"), MINOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
							SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"))
					.from(SUBJECT).innerJoin(MINOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MINOR_SUBJECT.SUBJECT_CODE))
					.where(SUBJECT.SUBJECT_CODE.eq(minorQuery.getSubjectCode())).fetchOneMap();

			return !query.isEmpty() ? query : null;
		}

	}

	public Map<String, Object> editMinorSubject(Map<String, Object> payload) throws Exception {
		Map<String, Object> preRequisites = dslContext
				.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.SUBJECT_TITLE.as("subjectTitle"),
						MINOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MINOR_SUBJECT.SEM.as("sem"))
				.from(SUBJECT).join(MINOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MINOR_SUBJECT.SUBJECT_CODE))
				.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("preRequisites").toString()))).fetchOneMap();
		Map<String, Object> subSubject = dslContext
				.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.SUBJECT_TITLE.as("subjectTitle"),
						MINOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MINOR_SUBJECT.SEM.as("sem"))
				.from(SUBJECT).join(MINOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MINOR_SUBJECT.SUBJECT_CODE))
				.where(MINOR_SUBJECT.PRE_REQUISITES.eq(Integer.valueOf(payload.get("subjectCode").toString())))
				.orderBy(SUBJECT.SUBJECT_CODE).fetchAnyMap();

		if (preRequisites == null && subSubject == null) {
			Subject updatedSubject = dslContext.update(SUBJECT)
					.set(SUBJECT.ABBREVATION, payload.get("abbreviation").toString())
					.set(SUBJECT.SUBJECT_TITLE, payload.get("subjectTitle").toString())
					.set(SUBJECT.UNITS, Double.valueOf(payload.get("units").toString()))
					.set(SUBJECT.PRICE, Double.valueOf(payload.get("price").toString()))
					.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("subjectCode").toString()))).returning()
					.fetchOne().into(Subject.class);

			dslContext.update(MINOR_SUBJECT)
					.set(MINOR_SUBJECT.PRE_REQUISITES, Integer.valueOf(payload.get("preRequisites").toString()))
					.set(MINOR_SUBJECT.YEAR_LEVEL, Integer.valueOf(payload.get("yearLevel").toString()))
					.set(MINOR_SUBJECT.SEM, Integer.valueOf(payload.get("sem").toString()))
					.where(MINOR_SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).returning().fetchOne()
					.into(MinorSubject.class);

			Map<String, Object> query = dslContext
					.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
							SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
							SUBJECT.PRICE.as("price"), MINOR_SUBJECT.YEAR_LEVEL.as("yearLevel"),
							MINOR_SUBJECT.SEM.as("sem"), MINOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
							SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"))
					.from(SUBJECT).innerJoin(MINOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MINOR_SUBJECT.SUBJECT_CODE))
					.where(SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).fetchOneMap();
			return query;
		} else if (preRequisites == null && subSubject != null) {
			Integer subYear = Integer.valueOf(subSubject.get("yearLevel").toString());
			Integer payloadYear = Integer.valueOf(payload.get("yearLevel").toString());
			Integer subSem = Integer.valueOf(subSubject.get("sem").toString());
			Integer payloadSem = Integer.valueOf(payload.get("sem").toString());

			if (subYear > payloadYear || (subYear == payloadYear && payloadSem < subSem)) {
				Subject updatedSubject = dslContext.update(SUBJECT)
						.set(SUBJECT.ABBREVATION, payload.get("abbreviation").toString())
						.set(SUBJECT.SUBJECT_TITLE, payload.get("subjectTitle").toString())
						.set(SUBJECT.UNITS, Double.valueOf(payload.get("units").toString()))
						.set(SUBJECT.PRICE, Double.valueOf(payload.get("price").toString()))
						.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("subjectCode").toString())))
						.returning().fetchOne().into(Subject.class);
				;

				dslContext.update(MINOR_SUBJECT)
						.set(MINOR_SUBJECT.PRE_REQUISITES, Integer.valueOf(payload.get("preRequisites").toString()))
						.set(MINOR_SUBJECT.YEAR_LEVEL, Integer.valueOf(payload.get("yearLevel").toString()))
						.set(MINOR_SUBJECT.SEM, Integer.valueOf(payload.get("sem").toString()))
						.where(MINOR_SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).returning().fetchOne()
						.into(MinorSubject.class);

				Map<String, Object> query = dslContext
						.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
								SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
								SUBJECT.PRICE.as("price"), MINOR_SUBJECT.YEAR_LEVEL.as("yearLevel"),
								MINOR_SUBJECT.SEM.as("sem"), MINOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
								SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"))
						.from(SUBJECT).innerJoin(MINOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MINOR_SUBJECT.SUBJECT_CODE))
						.where(SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).fetchOneMap();
				return query;
			} else {
				throw new Exception("%s is at year %s and sem %s".formatted(subSubject.get("subjectTitle").toString(),
						subSubject.get("yearLevel").toString(), subSubject.get("sem").toString()));
			}

		} else if (preRequisites != null && subSubject != null) {
			Integer subYear = Integer.valueOf(subSubject.get("yearLevel").toString());
			Integer payloadYear = Integer.valueOf(payload.get("yearLevel").toString());
			Integer subSem = Integer.valueOf(subSubject.get("sem").toString());
			Integer payloadSem = Integer.valueOf(payload.get("sem").toString());

			if (subYear > payloadYear || (subYear == payloadYear && payloadSem < subSem)) {
				Integer preYear = Integer.valueOf(preRequisites.get("yearLevel").toString());
				Integer payload2Year = Integer.valueOf(payload.get("yearLevel").toString());
				Integer preSem = Integer.valueOf(preRequisites.get("sem").toString());
				Integer payload2Sem = Integer.valueOf(payload.get("sem").toString());
				if (preYear < payload2Year || (preYear == payload2Year && preSem < payload2Sem)) {
					Subject updatedSubject = dslContext.update(SUBJECT)
							.set(SUBJECT.ABBREVATION, payload.get("abbreviation").toString())
							.set(SUBJECT.SUBJECT_TITLE, payload.get("subjectTitle").toString())
							.set(SUBJECT.UNITS, Double.valueOf(payload.get("units").toString()))
							.set(SUBJECT.PRICE, Double.valueOf(payload.get("price").toString()))
							.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("subjectCode").toString())))
							.returning().fetchOne().into(Subject.class);

					dslContext.update(MINOR_SUBJECT)
							.set(MINOR_SUBJECT.PRE_REQUISITES, Integer.valueOf(payload.get("preRequisites").toString()))
							.set(MINOR_SUBJECT.YEAR_LEVEL, Integer.valueOf(payload.get("yearLevel").toString()))
							.set(MINOR_SUBJECT.SEM, Integer.valueOf(payload.get("sem").toString()))
							.where(MINOR_SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).returning()
							.fetchOne().into(MinorSubject.class);

					Map<String, Object> query = dslContext
							.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
									SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
									SUBJECT.PRICE.as("price"), MINOR_SUBJECT.YEAR_LEVEL.as("yearLevel"),
									MINOR_SUBJECT.SEM.as("sem"), MINOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
									SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"),
									SUBJECT.ACTIVE_STATUS.as("activeStatus"))
							.from(SUBJECT).innerJoin(MINOR_SUBJECT)
							.on(SUBJECT.SUBJECT_CODE.eq(MINOR_SUBJECT.SUBJECT_CODE))
							.where(SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).fetchOneMap();
					return query;
				} else {
					throw new Exception(
							"%s is at year %s and sem %s".formatted(preRequisites.get("subjectTitle").toString(),
									preRequisites.get("yearLevel").toString(), preRequisites.get("sem").toString()));
				}
			} else {
				throw new Exception("%s is at year %s and sem %s".formatted(subSubject.get("subjectTitle").toString(),
						subSubject.get("yearLevel").toString(), subSubject.get("sem").toString()));
			}
		} else if (preRequisites != null && subSubject == null) {
			Integer preYear = Integer.valueOf(preRequisites.get("yearLevel").toString());
			Integer payload2Year = Integer.valueOf(payload.get("yearLevel").toString());
			Integer preSem = Integer.valueOf(preRequisites.get("sem").toString());
			Integer payload2Sem = Integer.valueOf(payload.get("sem").toString());
			if (preYear < payload2Year || (preYear == payload2Year && preSem < payload2Sem)) {
				Subject updatedSubject = dslContext.update(SUBJECT)
						.set(SUBJECT.ABBREVATION, payload.get("abbreviation").toString())
						.set(SUBJECT.SUBJECT_TITLE, payload.get("subjectTitle").toString())
						.set(SUBJECT.UNITS, Double.valueOf(payload.get("units").toString()))
						.set(SUBJECT.PRICE, Double.valueOf(payload.get("price").toString()))
						.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("subjectCode").toString())))
						.returning().fetchOne().into(Subject.class);

				dslContext.update(MINOR_SUBJECT)
						.set(MINOR_SUBJECT.PRE_REQUISITES, Integer.valueOf(payload.get("preRequisites").toString()))
						.set(MINOR_SUBJECT.YEAR_LEVEL, Integer.valueOf(payload.get("yearLevel").toString()))
						.set(MINOR_SUBJECT.SEM, Integer.valueOf(payload.get("sem").toString()))
						.where(MINOR_SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).returning().fetchOne()
						.into(MinorSubject.class);

				Map<String, Object> query = dslContext
						.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
								SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
								SUBJECT.PRICE.as("price"), MINOR_SUBJECT.YEAR_LEVEL.as("yearLevel"),
								MINOR_SUBJECT.SEM.as("sem"), MINOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
								SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"))
						.from(SUBJECT).innerJoin(MINOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MINOR_SUBJECT.SUBJECT_CODE))
						.where(SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).fetchOneMap();
				return query;
			} else {
				throw new Exception(
						"%s is at year %s and sem %s".formatted(preRequisites.get("subjectTitle").toString(),
								preRequisites.get("yearLevel").toString(), preRequisites.get("sem").toString()));
			}
		} else {
			return null;
		}
	}

	public Map<String, Object> changeMinorSubjectStatus(Integer subjectCode, Boolean activeStatus) {
		Subject update = dslContext.update(SUBJECT).set(SUBJECT.ACTIVE_STATUS, activeStatus)
				.where(SUBJECT.SUBJECT_CODE.eq(subjectCode)).returning().fetchOne().into(Subject.class);
		if (update != null) {
			Map<String, Object> query = dslContext
					.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
							SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
							MINOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MINOR_SUBJECT.SEM.as("sem"),
							SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.PRICE.as("price"),
							SUBJECT.ACTIVE_STATUS.as("activeStatus"))
					.from(SUBJECT).innerJoin(MINOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MINOR_SUBJECT.SUBJECT_CODE))
					.where(SUBJECT.SUBJECT_CODE.eq(subjectCode)).fetchOneMap();
			return !query.isEmpty() ? query : null;
		}
		return null;
	}

	public Map<String, Object> deleteMinorSubject(Integer subjectCode, Boolean activeStatus) throws Exception {
		Subject update = dslContext.update(SUBJECT).set(SUBJECT.ACTIVE_DEACTIVE, activeStatus)
				.where(SUBJECT.SUBJECT_CODE.eq(subjectCode)).returning().fetchOne().into(Subject.class);
		Map<String, Object> subSubject = dslContext
				.select(SUBJECT.ABBREVATION.as("abbreviation"), SUBJECT.SUBJECT_TITLE.as("subjectTitle")).from(SUBJECT)
				.join(MINOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MINOR_SUBJECT.SUBJECT_CODE))
				.where(MINOR_SUBJECT.PRE_REQUISITES.eq(subjectCode)).fetchOneMap();

		if (update != null && subSubject == null) {
			Map<String, Object> query = dslContext
					.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbrevation"),
							SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
							MINOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MINOR_SUBJECT.SEM.as("sem"),
							SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"))
					.from(SUBJECT).innerJoin(MINOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MINOR_SUBJECT.SUBJECT_CODE))
					.where(SUBJECT.SUBJECT_CODE.eq(subjectCode)).fetchOneMap();
			return !query.isEmpty() ? query : null;
		} else {
			throw new Exception("%s is pre-requisites by this Subject".formatted(subSubject.get("subjectTitle")));
		}
	}

	// -------------------------- Get All MAJOR SUBJECTS BY CURRICULUM
	public List<Map<String, Object>> selectAllMajorSubjects() {
		List<Map<String, Object>> query = dslContext
				.select(MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"), MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"),
						MAJOR_SUBJECT.SEM.as("sem"), MAJOR_SUBJECT.SUBJECT_CODE.as("subjectCode"),
						SUBJECT.ABBREVATION.as("abbreviation"), SUBJECT.SUBJECT_TITLE.as("subjectTitle"),
						SUBJECT.UNITS.as("units"), SUBJECT.PRICE.as("price"),
						SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"),
						MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"))
				.from(SUBJECT).join(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
				.where(MAJOR_SUBJECT.SUBJECT_CODE.in(dslContext.select(MAJOR_SUBJECT.SUBJECT_CODE).from(MAJOR_SUBJECT)
						.groupBy(MAJOR_SUBJECT.SUBJECT_CODE).having(DSL.count(MAJOR_SUBJECT.SUBJECT_CODE).eq(1)))
						.and(SUBJECT.ACTIVE_DEACTIVE.eq(true)))
				.orderBy(MAJOR_SUBJECT.YEAR_LEVEL, MAJOR_SUBJECT.SEM).fetchMaps();

		System.out.println(query);
		return query;
	}

	public List<Map<String, Object>> selectAllMajorSubjectsByCourse() {
		List<Map<String, Object>> query = dslContext
				.select(MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"), MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"),
						MAJOR_SUBJECT.SEM.as("sem"), SUBJECT.SUBJECT_CODE.as("subjectCode"),
						SUBJECT.ABBREVATION.as("abbreviation"), SUBJECT.SUBJECT_TITLE.as("subjectTitle"),
						SUBJECT.UNITS.as("units"), SUBJECT.PRICE.as("price"),
						SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"),
						MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"))
				.from(SUBJECT).join(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
				.groupBy(MAJOR_SUBJECT.CURRICULUM_CODE, MAJOR_SUBJECT.SUBJECT_CODE, MAJOR_SUBJECT.YEAR_LEVEL,
						MAJOR_SUBJECT.SEM, MAJOR_SUBJECT.PRE_REQUISITES, SUBJECT.SUBJECT_CODE, SUBJECT.ABBREVATION,
						SUBJECT.SUBJECT_TITLE, SUBJECT.UNITS, SUBJECT.ACTIVE_DEACTIVE, SUBJECT.ACTIVE_STATUS)
				.having(DSL.count(MAJOR_SUBJECT.SUBJECT_CODE).eq(1))
				.orderBy(MAJOR_SUBJECT.YEAR_LEVEL, MAJOR_SUBJECT.SEM).fetchMaps();
		return query;
	}

	public List<Map<String, Object>> selectAllMajorSubjectsByAllCourse(Integer courseCode) {
		List<Curriculum> allCurriculum = dslContext
				.select(CURRICULUM.CURRICULUM_CODE, CURRICULUM.CURRICULUM_ID, CURRICULUM.CURRICULUM_NAME,
						CURRICULUM.MAJOR_CODE, CURRICULUM.ACTIVE_DEACTIVE)
				.from(CURRICULUM).join(MAJOR).on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE))
				.where(MAJOR.COURSE_CODE.eq(courseCode)).fetchInto(Curriculum.class);

		List<Map<String, Object>> query = dslContext
				.selectDistinct(MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"),
						MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM.as("sem"),
						MAJOR_SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
						SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"), SUBJECT.PRICE.as("price"),
						SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"),
						MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"), MAJOR.COURSE_CODE.as("courseCode"))
				.from(SUBJECT).join(MAJOR_SUBJECT).on(
						SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
				.join(CURRICULUM).on(MAJOR_SUBJECT.CURRICULUM_CODE.eq(CURRICULUM.CURRICULUM_CODE)).join(MAJOR)
				.on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE))
				.where(MAJOR_SUBJECT.SUBJECT_CODE
						.in(dslContext.selectDistinct(MAJOR_SUBJECT.SUBJECT_CODE).from(MAJOR_SUBJECT)
								.groupBy(MAJOR_SUBJECT.SUBJECT_CODE).having(DSL.count().greaterThan(1)))
						.and(MAJOR.COURSE_CODE.eq(courseCode))
						.and(MAJOR_SUBJECT.CURRICULUM_CODE.eq(allCurriculum.get(0).getCurriculumCode()))
						.and(SUBJECT.ACTIVE_DEACTIVE.eq(true)))
				.orderBy(MAJOR_SUBJECT.YEAR_LEVEL, MAJOR_SUBJECT.SEM).fetchMaps();

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
							MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"), SUBJECT.PRICE.as("price"),
							SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"))
					.from(SUBJECT).innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
					.where(SUBJECT.SUBJECT_CODE.eq(subjectCode)).fetchOneMap();
			return !query.isEmpty() ? query : null;
		}
		return null;
	}

	public Map<String, Object> changeMajorSubjectStatusByCourse(Integer subjectCode, Boolean activeStatus,
			Integer courseCode) {
		Subject update = dslContext.update(SUBJECT).set(SUBJECT.ACTIVE_STATUS, activeStatus)
				.where(SUBJECT.SUBJECT_CODE.eq(subjectCode)).returning().fetchOne().into(Subject.class);
		if (update != null) {
			List<Curriculum> allCurriculum = dslContext
					.select(CURRICULUM.CURRICULUM_CODE, CURRICULUM.CURRICULUM_ID, CURRICULUM.CURRICULUM_NAME,
							CURRICULUM.MAJOR_CODE, CURRICULUM.ACTIVE_DEACTIVE)
					.from(CURRICULUM).join(MAJOR).on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE))
					.where(MAJOR.COURSE_CODE.eq(courseCode)).fetchInto(Curriculum.class);
			Map<String, Object> query = dslContext
					.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
							SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
							MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM.as("sem"),
							MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"),
							MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"), SUBJECT.PRICE.as("price"),
							SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"),
							MAJOR.COURSE_CODE.as("courseCode"))
					.from(SUBJECT).innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
					.join(CURRICULUM).on(MAJOR_SUBJECT.CURRICULUM_CODE.eq(CURRICULUM.CURRICULUM_CODE)).join(MAJOR)
					.on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE))
					.where(SUBJECT.SUBJECT_CODE.eq(subjectCode)
							.and(MAJOR_SUBJECT.CURRICULUM_CODE.eq(allCurriculum.get(0).getCurriculumCode())))
					.fetchOneMap();
			return !query.isEmpty() ? query : null;
		}
		return null;
	}

	public Map<String, Object> addMajorSubjectByAll(Map<String, Object> payload, Integer courseCode) throws Exception {
		Map<String, Object> preRequites = dslContext
				.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
						SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
						MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM.as("sem"),
						MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
						MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"),
						SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"))
				.from(SUBJECT).innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
				.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("preRequisites").toString())).and(
						MAJOR_SUBJECT.CURRICULUM_CODE.eq(Integer.valueOf(payload.get("curriculumCode").toString()))))
				.fetchOneMap();
		List<Curriculum> allCurriculum = dslContext
				.select(CURRICULUM.CURRICULUM_CODE, CURRICULUM.CURRICULUM_ID, CURRICULUM.CURRICULUM_NAME,
						CURRICULUM.MAJOR_CODE, CURRICULUM.ACTIVE_DEACTIVE)
				.from(CURRICULUM).join(MAJOR).on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE))
				.where(MAJOR.COURSE_CODE.eq(courseCode)).fetchInto(Curriculum.class);
		if (preRequites != null) {
			Integer preYear = Integer.valueOf(preRequites.get("yearLevel").toString());
			Integer payloadYear = Integer.valueOf(payload.get("yearLevel").toString());
			Integer preSem = Integer.valueOf(preRequites.get("sem").toString());
			Integer payloadSem = Integer.valueOf(payload.get("sem").toString());
			if (preYear < payloadYear || (preYear == payloadYear && preSem < payloadSem)) {
				Subject addedSUbject = dslContext.insertInto(SUBJECT)
						.set(SUBJECT.ABBREVATION, payload.get("abbreviation").toString())
						.set(SUBJECT.SUBJECT_TITLE, payload.get("subjectTitle").toString())
						.set(SUBJECT.UNITS, Double.valueOf(payload.get("units").toString()))
						.set(SUBJECT.PRICE, Double.valueOf(payload.get("price").toString()))
						.set(SUBJECT.ACTIVE_DEACTIVE, Boolean.valueOf(payload.get("activeDeactive").toString()))
						.set(SUBJECT.ACTIVE_STATUS, Boolean.valueOf(payload.get("activeStatus").toString())).returning()
						.fetchOne().into(Subject.class);
				allCurriculum.forEach((curriculum) -> {
					dslContext.insertInto(MAJOR_SUBJECT).set(MAJOR_SUBJECT.SUBJECT_CODE, addedSUbject.getSubjectCode())
							.set(MAJOR_SUBJECT.PRE_REQUISITES, Integer.valueOf(payload.get("preRequisites").toString()))
							.set(MAJOR_SUBJECT.CURRICULUM_CODE, curriculum.getCurriculumCode())
							.set(MAJOR_SUBJECT.SEM, Integer.valueOf(payload.get("sem").toString()))
							.set(MAJOR_SUBJECT.YEAR_LEVEL, Integer.valueOf(payload.get("yearLevel").toString()))
							.returning().fetchOne().into(MajorSubject.class);
				});

				Map<String, Object> query = dslContext
						.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
								SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
								SUBJECT.PRICE.as("price"), MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"),
								MAJOR_SUBJECT.SEM.as("sem"), MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"),
								MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
								SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"),
								MAJOR.COURSE_CODE.as("courseCode"))
						.from(SUBJECT).innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
						.join(CURRICULUM).on(MAJOR_SUBJECT.CURRICULUM_CODE.eq(CURRICULUM.CURRICULUM_CODE)).join(MAJOR)
						.on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE))
						.where(MAJOR_SUBJECT.CURRICULUM_CODE.eq(allCurriculum.get(0).getCurriculumCode())
								.and(MAJOR_SUBJECT.SUBJECT_CODE.eq(addedSUbject.getSubjectCode())))
						.fetchOneMap();
				return query;
			} else {
				throw new Exception("%s is at year %d and sem %d".formatted(preRequites.get("subjectTitle").toString(),
						preYear, preSem));
			}

		} else {
			Subject addedSUbject = dslContext.insertInto(SUBJECT)
					.set(SUBJECT.ABBREVATION, payload.get("abbreviation").toString())
					.set(SUBJECT.SUBJECT_TITLE, payload.get("subjectTitle").toString())
					.set(SUBJECT.UNITS, Double.valueOf(payload.get("units").toString()))
					.set(SUBJECT.PRICE, Double.valueOf(payload.get("price").toString()))
					.set(SUBJECT.ACTIVE_DEACTIVE, Boolean.valueOf(payload.get("activeDeactive").toString()))
					.set(SUBJECT.ACTIVE_STATUS, Boolean.valueOf(payload.get("activeStatus").toString())).returning()
					.fetchOne().into(Subject.class);
			allCurriculum.forEach((curriculum) -> {
				dslContext.insertInto(MAJOR_SUBJECT).set(MAJOR_SUBJECT.SUBJECT_CODE, addedSUbject.getSubjectCode())
						.set(MAJOR_SUBJECT.PRE_REQUISITES, Integer.valueOf(payload.get("preRequisites").toString()))
						.set(MAJOR_SUBJECT.CURRICULUM_CODE, curriculum.getCurriculumCode())
						.set(MAJOR_SUBJECT.SEM, Integer.valueOf(payload.get("sem").toString()))
						.set(MAJOR_SUBJECT.YEAR_LEVEL, Integer.valueOf(payload.get("yearLevel").toString())).returning()
						.fetchOne().into(MajorSubject.class);
			});

			Map<String, Object> query = dslContext
					.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
							SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
							SUBJECT.PRICE.as("price"), MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"),
							MAJOR_SUBJECT.SEM.as("sem"), MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"),
							MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
							SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"),
							MAJOR.COURSE_CODE.as("courseCode"))
					.from(SUBJECT).innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
					.join(CURRICULUM).on(MAJOR_SUBJECT.CURRICULUM_CODE.eq(CURRICULUM.CURRICULUM_CODE)).join(MAJOR)
					.on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE))
					.where(MAJOR_SUBJECT.CURRICULUM_CODE.eq(allCurriculum.get(0).getCurriculumCode())
							.and(MAJOR_SUBJECT.SUBJECT_CODE.eq(addedSUbject.getSubjectCode())))
					.fetchOneMap();
			System.out.println(query);
			return query;
		}
	}

	public Map<String, Object> addMajorSubjectByMajor(Map<String, Object> payload) throws Exception {
		if (Integer.valueOf(payload.get("preRequisites").toString()) != 9000) {
			Map<String, Object> preRequites = dslContext
					.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
							SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
							MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM.as("sem"),
							MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
							MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"),
							SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"))
					.from(SUBJECT).innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
					.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("preRequisites").toString())))
					.fetchOneMap();
			Integer preYear = Integer.valueOf(preRequites.get("yearLevel").toString());
			Integer payloadYear = Integer.valueOf(payload.get("yearLevel").toString());
			Integer preSem = Integer.valueOf(preRequites.get("sem").toString());
			Integer payloadSem = Integer.valueOf(payload.get("sem").toString());
			if (preYear < payloadYear || (preYear == payloadYear && preSem < payloadSem)) {
				Subject updatedSubject = dslContext.update(SUBJECT)
						.set(SUBJECT.ABBREVATION, payload.get("abbreviation").toString())
						.set(SUBJECT.SUBJECT_TITLE, payload.get("subjectTitle").toString())
						.set(SUBJECT.UNITS, Double.valueOf(payload.get("units").toString()))
						.set(SUBJECT.PRICE, Double.valueOf(payload.get("price").toString()))
						.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("subjectCode").toString())))
						.returning().fetchOne().into(Subject.class);
				dslContext.update(MAJOR_SUBJECT)
						.set(MAJOR_SUBJECT.PRE_REQUISITES, Integer.valueOf(payload.get("preRequisites").toString()))
						.set(MAJOR_SUBJECT.YEAR_LEVEL, Integer.valueOf(payload.get("yearLevel").toString()))
						.set(MAJOR_SUBJECT.SEM, Integer.valueOf(payload.get("sem").toString()))
						.where(MAJOR_SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).returning().fetchOne()
						.into(MajorSubject.class);

				Map<String, Object> query = dslContext
						.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
								SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
								SUBJECT.PRICE.as("price"), MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"),
								MAJOR_SUBJECT.SEM.as("sem"), MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
								SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"),
								MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"))
						.from(SUBJECT).innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
						.where(SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).fetchOneMap();
				return query;
			}
			throw new Exception("%s is at year %d and sem %d".formatted(preRequites.get("subjectTitle").toString(),
					preYear, preSem));
		} else {
			Subject subjectQuery = dslContext.insertInto(SUBJECT)
					.set(SUBJECT.ABBREVATION, payload.get("abbreviation").toString())
					.set(SUBJECT.SUBJECT_TITLE, payload.get("subjectTitle").toString())
					.set(SUBJECT.UNITS, Double.valueOf(payload.get("units").toString()))
					.set(SUBJECT.PRICE, Double.valueOf(payload.get("price").toString()))
					.set(SUBJECT.ACTIVE_STATUS, Boolean.valueOf(payload.get("activeStatus").toString()))
					.set(SUBJECT.ACTIVE_DEACTIVE, Boolean.valueOf(payload.get("activeDeactive").toString())).returning()
					.fetchOne().into(Subject.class);
			MajorSubject majorQuery = dslContext.insertInto(MAJOR_SUBJECT)
					.set(MAJOR_SUBJECT.SUBJECT_CODE, subjectQuery.getSubjectCode())
					.set(MAJOR_SUBJECT.YEAR_LEVEL, Integer.valueOf(payload.get("yearLevel").toString()))
					.set(MAJOR_SUBJECT.PRE_REQUISITES, Integer.valueOf(payload.get("preRequisites").toString()))
					.set(MAJOR_SUBJECT.CURRICULUM_CODE, Integer.valueOf(payload.get("curriculumCode").toString()))
					.set(MAJOR_SUBJECT.SEM, Integer.valueOf(payload.get("sem").toString())).returning().fetchOne()
					.into(MajorSubject.class);
			Map<String, Object> query = dslContext
					.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
							SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
							SUBJECT.PRICE.as("price"), MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"),
							MAJOR_SUBJECT.SEM.as("sem"), MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
							SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"),
							MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"))
					.from(SUBJECT).innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
					.where(SUBJECT.SUBJECT_CODE.eq(majorQuery.getSubjectCode())).fetchOneMap();
			return !query.isEmpty() ? query : null;
		}

	}

	public Map<String, Object> editMajorSubjectByAll(Map<String, Object> payload) throws Exception {
		if (Boolean.valueOf(payload.get("activeDeactive").toString()) == false) {
			Map<String, Object> subSubject = dslContext
					.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
							SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
							SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"),
							MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM.as("sem"),
							MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
							MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"))
					.from(SUBJECT).join(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE)).where(
							MAJOR_SUBJECT.PRE_REQUISITES.eq(Integer.valueOf(payload.get("subjectCode").toString()))
									.and(SUBJECT.ACTIVE_DEACTIVE.eq(true))
									.and(MAJOR_SUBJECT.CURRICULUM_CODE
											.eq(Integer.valueOf(payload.get("curriculumCode").toString()))))
					.fetchOneMap();
			if (subSubject != null) {
				throw new Exception("%s is pre-requisites by %s".formatted(payload.get("subjectTitle").toString(),
						subSubject.get("subjectTitle").toString()));
			} else {
				dslContext.update(SUBJECT).set(SUBJECT.ACTIVE_DEACTIVE, Boolean.valueOf(false))
						.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("subjectCode").toString())))
						.returning().fetch();
				Map<String, Object> query = dslContext
						.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
								SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
								MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM.as("sem"),
								MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
								SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"),
								MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"))
						.from(SUBJECT).innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
						.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("subjectCode").toString()))
								.and(MAJOR_SUBJECT.CURRICULUM_CODE
										.eq(Integer.valueOf(payload.get("curriculumCode").toString()))))
						.fetchOneMap();
				return query;
			}
		}
		if (Integer.valueOf(payload.get("preRequisites").toString()) != 9000) {
			Map<String, Object> preRequites = dslContext
					.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
							SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
							MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM.as("sem"),
							MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
							MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"),
							SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"))
					.from(SUBJECT).innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
					.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("preRequisites").toString()))).limit(1)
					.fetchOneMap();
			Integer preYear = Integer.valueOf(preRequites.get("yearLevel").toString());
			Integer payloadYear = Integer.valueOf(payload.get("yearLevel").toString());
			Integer preSem = Integer.valueOf(preRequites.get("sem").toString());
			Integer payloadSem = Integer.valueOf(payload.get("sem").toString());
			System.out.println(preRequites + "preeq");
			if (preYear < payloadYear || (preYear == payloadYear && preSem < payloadSem)) {
				Subject updatedSubject = dslContext.update(SUBJECT)
						.set(SUBJECT.ABBREVATION, payload.get("abbreviation").toString())
						.set(SUBJECT.SUBJECT_TITLE, payload.get("subjectTitle").toString())
						.set(SUBJECT.UNITS, Double.valueOf(payload.get("units").toString()))
						.set(SUBJECT.PRICE, Double.valueOf(payload.get("price").toString()))
						.set(SUBJECT.ACTIVE_DEACTIVE, Boolean.valueOf(payload.get("activeDeactive").toString()))
						.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("subjectCode").toString())))
						.returning().fetchOne().into(Subject.class);
				dslContext.update(MAJOR_SUBJECT)
						.set(MAJOR_SUBJECT.PRE_REQUISITES, Integer.valueOf(payload.get("preRequisites").toString()))
						.set(MAJOR_SUBJECT.YEAR_LEVEL, Integer.valueOf(payload.get("yearLevel").toString()))
						.set(MAJOR_SUBJECT.SEM, Integer.valueOf(payload.get("sem").toString()))
						.where(MAJOR_SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).returning().fetch();
				Map<String, Object> query = dslContext
						.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
								SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
								SUBJECT.PRICE.as("price"), MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"),
								MAJOR_SUBJECT.SEM.as("sem"), MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
								SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"),
								MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"), MAJOR.COURSE_CODE.as("courseCode"))
						.from(SUBJECT).innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
						.join(CURRICULUM).on(MAJOR_SUBJECT.CURRICULUM_CODE.eq(CURRICULUM.CURRICULUM_CODE)).join(MAJOR)
						.on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE)).where(
								SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())
										.and(MAJOR_SUBJECT.CURRICULUM_CODE
												.eq(Integer.valueOf(payload.get("curriculumCode").toString()))))
						.fetchOneMap();
				return query;
			} else {
				throw new Exception("%s is at year %d and sem %d".formatted(preRequites.get("subjectTitle").toString(),
						preYear, preSem));
			}
		} else {
			Map<String, Object> subSubject = dslContext
					.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
							SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
							MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM.as("sem"),
							MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
							MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"),
							SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"))
					.from(SUBJECT).innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
					.where(MAJOR_SUBJECT.PRE_REQUISITES.eq(Integer.valueOf(payload.get("subjectCode").toString())))
					.limit(1).fetchOneMap();
			System.out.println(subSubject + "sub");
			if (subSubject != null) {
				Integer preYear = Integer.valueOf(subSubject.get("yearLevel").toString());
				Integer payloadYear = Integer.valueOf(payload.get("yearLevel").toString());
				Integer preSem = Integer.valueOf(subSubject.get("sem").toString());
				Integer payloadSem = Integer.valueOf(payload.get("sem").toString());
				if (preYear > payloadYear || (preYear == payloadYear && preSem > payloadSem)) {
					Subject updatedSubject = dslContext.update(SUBJECT)
							.set(SUBJECT.ABBREVATION, payload.get("abbreviation").toString())
							.set(SUBJECT.SUBJECT_TITLE, payload.get("subjectTitle").toString())
							.set(SUBJECT.UNITS, Double.valueOf(payload.get("units").toString()))
							.set(SUBJECT.PRICE, Double.valueOf(payload.get("price").toString()))
							.set(SUBJECT.ACTIVE_DEACTIVE, Boolean.valueOf(payload.get("activeDeactive").toString()))
							.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("subjectCode").toString())))
							.returning().fetchOne().into(Subject.class);
					dslContext.update(MAJOR_SUBJECT)
							.set(MAJOR_SUBJECT.PRE_REQUISITES, Integer.valueOf(payload.get("preRequisites").toString()))
							.set(MAJOR_SUBJECT.YEAR_LEVEL, Integer.valueOf(payload.get("yearLevel").toString()))
							.set(MAJOR_SUBJECT.SEM, Integer.valueOf(payload.get("sem").toString()))
							.where(MAJOR_SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).returning().fetch();

					Map<String, Object> query = dslContext
							.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
									SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
									SUBJECT.PRICE.as("price"), MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"),
									MAJOR_SUBJECT.SEM.as("sem"), MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
									SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"),
									SUBJECT.ACTIVE_STATUS.as("activeStatus"),
									MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"),
									MAJOR.COURSE_CODE.as("courseCode"))
							.from(SUBJECT).innerJoin(MAJOR_SUBJECT)
							.on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE)).join(CURRICULUM)
							.on(MAJOR_SUBJECT.CURRICULUM_CODE.eq(CURRICULUM.CURRICULUM_CODE)).join(MAJOR)
							.on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE))
							.where(SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())
									.and(MAJOR_SUBJECT.CURRICULUM_CODE
											.eq(Integer.valueOf(payload.get("curriculumCode").toString()))))
							.fetchOneMap();
					return query;
				} else {
					throw new Exception("%s is at year %d and sem %d"
							.formatted(subSubject.get("subjectTitle").toString(), preYear, preSem));
				}
			} else {

				Subject updatedSubject = dslContext.update(SUBJECT)
						.set(SUBJECT.ABBREVATION, payload.get("abbreviation").toString())
						.set(SUBJECT.SUBJECT_TITLE, payload.get("subjectTitle").toString())
						.set(SUBJECT.UNITS, Double.valueOf(payload.get("units").toString()))
						.set(SUBJECT.PRICE, Double.valueOf(payload.get("price").toString()))
						.set(SUBJECT.ACTIVE_DEACTIVE, Boolean.valueOf(payload.get("activeDeactive").toString()))
						.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("subjectCode").toString())))
						.returning().fetchOne().into(Subject.class);
				dslContext.update(MAJOR_SUBJECT)
						.set(MAJOR_SUBJECT.PRE_REQUISITES, Integer.valueOf(payload.get("preRequisites").toString()))
						.set(MAJOR_SUBJECT.YEAR_LEVEL, Integer.valueOf(payload.get("yearLevel").toString()))
						.set(MAJOR_SUBJECT.SEM, Integer.valueOf(payload.get("sem").toString()))
						.where(MAJOR_SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).returning().fetch();

				Map<String, Object> query = dslContext
						.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
								SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"), SUBJECT.PRICE,
								MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM.as("sem"),
								MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
								SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"),
								MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"), MAJOR.COURSE_CODE.as("courseCode"))
						.from(SUBJECT).innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
						.join(CURRICULUM).on(MAJOR_SUBJECT.CURRICULUM_CODE.eq(CURRICULUM.CURRICULUM_CODE)).join(MAJOR)
						.on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE)).where(
								SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())
										.and(MAJOR_SUBJECT.CURRICULUM_CODE
												.eq(Integer.valueOf(payload.get("curriculumCode").toString()))))
						.fetchOneMap();
				System.out.println(query + "return val");
				return query;
			}
		}

	}

	public Map<String, Object> editMajorSubject(Map<String, Object> payload) throws Exception {
		if (Integer.valueOf(payload.get("preRequisites").toString()) != 9000) {
			Map<String, Object> preRequites = dslContext
					.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
							SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
							MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM.as("sem"),
							MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
							MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"),
							SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"))
					.from(SUBJECT).innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
					.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("preRequisites").toString())))
					.fetchOneMap();
			Integer preYear = Integer.valueOf(preRequites.get("yearLevel").toString());
			Integer payloadYear = Integer.valueOf(payload.get("yearLevel").toString());
			Integer preSem = Integer.valueOf(preRequites.get("sem").toString());
			Integer payloadSem = Integer.valueOf(payload.get("sem").toString());
			if (preYear < payloadYear || (preYear == payloadYear && preSem < payloadSem)) {
				Subject updatedSubject = dslContext.update(SUBJECT)
						.set(SUBJECT.ABBREVATION, payload.get("abbreviation").toString())
						.set(SUBJECT.SUBJECT_TITLE, payload.get("subjectTitle").toString())
						.set(SUBJECT.UNITS, Double.valueOf(payload.get("units").toString()))
						.set(SUBJECT.PRICE, Double.valueOf(payload.get("price").toString()))
						.set(SUBJECT.ACTIVE_DEACTIVE, Boolean.valueOf(payload.get("activeDeactive").toString()))
						.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("subjectCode").toString())))
						.returning().fetchOne().into(Subject.class);
				dslContext.update(MAJOR_SUBJECT)
						.set(MAJOR_SUBJECT.PRE_REQUISITES, Integer.valueOf(payload.get("preRequisites").toString()))
						.set(MAJOR_SUBJECT.YEAR_LEVEL, Integer.valueOf(payload.get("yearLevel").toString()))
						.set(MAJOR_SUBJECT.SEM, Integer.valueOf(payload.get("sem").toString()))
						.where(MAJOR_SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).returning().fetchOne()
						.into(MajorSubject.class);

				Map<String, Object> query = dslContext
						.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
								SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
								SUBJECT.PRICE.as("price"), MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"),
								MAJOR_SUBJECT.SEM.as("sem"), MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
								SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"),
								MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"))
						.from(SUBJECT).innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
						.where(SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).fetchOneMap();
				return query;
			} else {
				throw new Exception("%s is at year %d and sem %d".formatted(preRequites.get("subjectTitle").toString(),
						preYear, preSem));
			}
		} else {
			Map<String, Object> subSubject = dslContext
					.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
							SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
							MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM.as("sem"),
							MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
							MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"),
							SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"))
					.from(SUBJECT).innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
					.where(MAJOR_SUBJECT.PRE_REQUISITES.eq(Integer.valueOf(payload.get("subjectCode").toString())))
					.fetchOneMap();
			if (subSubject != null) {
				Integer preYear = Integer.valueOf(subSubject.get("yearLevel").toString());
				Integer payloadYear = Integer.valueOf(payload.get("yearLevel").toString());
				Integer preSem = Integer.valueOf(subSubject.get("sem").toString());
				Integer payloadSem = Integer.valueOf(payload.get("sem").toString());
				if (preYear > payloadYear || (preYear == payloadYear && preSem > payloadSem)) {
					Subject updatedSubject = dslContext.update(SUBJECT)
							.set(SUBJECT.ABBREVATION, payload.get("abbreviation").toString())
							.set(SUBJECT.SUBJECT_TITLE, payload.get("subjectTitle").toString())
							.set(SUBJECT.UNITS, Double.valueOf(payload.get("units").toString()))
							.set(SUBJECT.PRICE, Double.valueOf(payload.get("price").toString()))
							.set(SUBJECT.ACTIVE_DEACTIVE, Boolean.valueOf(payload.get("activeDeactive").toString()))
							.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("subjectCode").toString())))
							.returning().fetchOne().into(Subject.class);
					dslContext.update(MAJOR_SUBJECT)
							.set(MAJOR_SUBJECT.PRE_REQUISITES, Integer.valueOf(payload.get("preRequisites").toString()))
							.set(MAJOR_SUBJECT.YEAR_LEVEL, Integer.valueOf(payload.get("yearLevel").toString()))
							.set(MAJOR_SUBJECT.SEM, Integer.valueOf(payload.get("sem").toString()))
							.where(MAJOR_SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).returning()
							.fetchOne().into(MajorSubject.class);

					Map<String, Object> query = dslContext
							.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
									SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
									SUBJECT.PRICE.as("price"), MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"),
									MAJOR_SUBJECT.SEM.as("sem"), MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
									SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"),
									SUBJECT.ACTIVE_STATUS.as("activeStatus"),
									MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"))
							.from(SUBJECT).innerJoin(MAJOR_SUBJECT)
							.on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
							.where(SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).fetchOneMap();
					return query;
				} else {
					throw new Exception("%s is at year %d and sem %d"
							.formatted(subSubject.get("subjectTitle").toString(), preYear, preSem));
				}
			} else {
				Subject updatedSubject = dslContext.update(SUBJECT)
						.set(SUBJECT.ABBREVATION, payload.get("abbreviation").toString())
						.set(SUBJECT.SUBJECT_TITLE, payload.get("subjectTitle").toString())
						.set(SUBJECT.UNITS, Double.valueOf(payload.get("units").toString()))
						.set(SUBJECT.PRICE, Double.valueOf(payload.get("price").toString()))
						.set(SUBJECT.ACTIVE_DEACTIVE, Boolean.valueOf(payload.get("activeDeactive").toString()))
						.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(payload.get("subjectCode").toString())))
						.returning().fetchOne().into(Subject.class);
				dslContext.update(MAJOR_SUBJECT)
						.set(MAJOR_SUBJECT.PRE_REQUISITES, Integer.valueOf(payload.get("preRequisites").toString()))
						.set(MAJOR_SUBJECT.YEAR_LEVEL, Integer.valueOf(payload.get("yearLevel").toString()))
						.set(MAJOR_SUBJECT.SEM, Integer.valueOf(payload.get("sem").toString()))
						.where(MAJOR_SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).returning().fetchOne()
						.into(MajorSubject.class);

				Map<String, Object> query = dslContext
						.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
								SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
								SUBJECT.PRICE.as("price"), MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"),
								MAJOR_SUBJECT.SEM.as("sem"), MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
								SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"),
								MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"))
						.from(SUBJECT).innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
						.where(SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).fetchOneMap();
				return query;
			}
		}
	}

	public Map<String, Object> deleteMajorSubject(Integer subjectCode) throws Exception {
		Map<String, Object> selectSubject = dslContext
				.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
						SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
						SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"),
						MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM.as("sem"),
						MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
						MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"))
				.from(SUBJECT).join(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
				.where(SUBJECT.SUBJECT_CODE.eq(subjectCode)).fetchOneMap();
		Map<String, Object> subSubject = dslContext
				.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
						SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
						SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"),
						MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM.as("sem"),
						MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
						MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"))
				.from(SUBJECT).join(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
				.where(MAJOR_SUBJECT.PRE_REQUISITES.eq(Integer.valueOf(selectSubject.get("subjectCode").toString()))
						.and(SUBJECT.ACTIVE_DEACTIVE.eq(true)))
				.fetchOneMap();
		if (subSubject != null) {
			throw new Exception("%s is pre-requisites by %s".formatted(selectSubject.get("subjectTitle").toString(),
					subSubject.get("subjectTitle").toString()));
		} else {
			Subject updatedSubject = dslContext.update(SUBJECT).set(SUBJECT.ACTIVE_DEACTIVE, Boolean.valueOf(false))
					.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(selectSubject.get("subjectCode").toString())))
					.returning().fetchOne().into(Subject.class);
			Map<String, Object> query = dslContext
					.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
							SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
							MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM.as("sem"),
							MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
							SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"),
							MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"))
					.from(SUBJECT).innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
					.where(SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode())).fetchOneMap();
			return query;
		}
	}

	public Map<String, Object> deleteMajorSubjectByCourse(Integer subjectCode, Integer curriculumCode)
			throws Exception {
		Map<String, Object> selectSubject = dslContext
				.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
						SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
						SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"),
						MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM.as("sem"),
						MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
						MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"))
				.from(SUBJECT).join(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
				.where(SUBJECT.SUBJECT_CODE.eq(subjectCode).and(MAJOR_SUBJECT.CURRICULUM_CODE.eq(curriculumCode)))
				.fetchOneMap();
		System.out.println(selectSubject + "selectSub");
		return null;
//		Map<String, Object> subSubject = dslContext.select(
//				SUBJECT.SUBJECT_CODE.as("subjectCode"),
//				SUBJECT.ABBREVATION.as("abbreviation"),
//				SUBJECT.SUBJECT_TITLE.as("subjectTitle"), 
//				SUBJECT.UNITS.as("units"),
//				SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), 
//				SUBJECT.ACTIVE_STATUS.as("activeStatus"),
//				MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), 
//				MAJOR_SUBJECT.SEM.as("sem"),
//				MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"), 
//				MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"))
//			.from(SUBJECT)
//			.join(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
//			.where(MAJOR_SUBJECT.PRE_REQUISITES.eq(Integer.valueOf(selectSubject.get("subjectCode").toString()))
//					.and(SUBJECT.ACTIVE_DEACTIVE.eq(true))).limit(1).fetchOneMap();
//		System.out.println(subSubject + "sub");
//		if (subSubject != null) {
//			throw new Exception("%s is pre-requisites by %s".formatted(selectSubject.get("subjectTitle").toString(), subSubject.get("subjectTitle").toString()));
//		} else {
//			Subject updatedSubject = dslContext.update(SUBJECT)
//					.set(SUBJECT.ACTIVE_DEACTIVE, Boolean.valueOf(false))
//					.where(SUBJECT.SUBJECT_CODE.eq(Integer.valueOf(selectSubject.get("subjectCode").toString())))
//					.returning().fetchOne().into(Subject.class);
//			System.out.println(updatedSubject + "updated sub");
//			Map<String, Object> query = dslContext
//					.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION.as("abbreviation"),
//							SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS.as("units"),
//							MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM.as("sem"),
//							MAJOR_SUBJECT.PRE_REQUISITES.as("preRequisites"),
//							SUBJECT.ACTIVE_DEACTIVE.as("activeDeactive"), SUBJECT.ACTIVE_STATUS.as("activeStatus"),
//							MAJOR_SUBJECT.CURRICULUM_CODE.as("curriculumCode"),
//							MAJOR.COURSE_CODE.as("courseCode"))
//					.from(SUBJECT).innerJoin(MAJOR_SUBJECT).on(SUBJECT.SUBJECT_CODE.eq(MAJOR_SUBJECT.SUBJECT_CODE))
//					.join(CURRICULUM).on(MAJOR_SUBJECT.CURRICULUM_CODE.eq(CURRICULUM.CURRICULUM_CODE))
//					.join(MAJOR).on(CURRICULUM.MAJOR_CODE.eq(MAJOR.MAJOR_CODE))
//					.where(SUBJECT.SUBJECT_CODE.eq(updatedSubject.getSubjectCode()).and(MAJOR_SUBJECT.CURRICULUM_CODE.eq(curriculumCode))).fetchOneMap();
//			System.out.println(query + "query");
//			return query;
//		}
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
				.orderBy(GRADES.GRADE_ID).fetchMaps();
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

	// ------------ FOR Submitted Subjects for enrollment of students
	public List<Map<String, Object>> selectSubmittedSubjectsOfstudentPerEnrollment(Integer studentNo, Integer sectionId,
			Integer enrollmentId) {
		return dslContext
				.selectDistinct(SUBMITTED_SUBJECTS_FOR_ENROLLMENT.SUBMITTED_SUBJECTS_ID.as("submittedSubjectsId"),
						STUDENT_ENROLLMENT.STUDENT_NO.as("studentNo"), SUBJECT.ABBREVATION,
						SUBMITTED_SUBJECTS_FOR_ENROLLMENT.STATUS, PROFESSOR_LOAD.LOAD_ID.as("loadId"), PROFESSOR_LOAD.SECTION_ID.as("sectionId"),
						PROFESSOR_LOAD.DAY, PROFESSOR_LOAD.PROFESSOR_NO.as("professorNo"),
						SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS)
				.from(SUBMITTED_SUBJECTS_FOR_ENROLLMENT).innerJoin(STUDENT_ENROLLMENT)
				.on(SUBMITTED_SUBJECTS_FOR_ENROLLMENT.ENROLLMENT_ID.eq(STUDENT_ENROLLMENT.ENROLLMENT_ID))
				.innerJoin(PROFESSOR_LOAD)
				.on(SUBMITTED_SUBJECTS_FOR_ENROLLMENT.SUBJECT_CODE.eq(PROFESSOR_LOAD.SUBJECT_CODE)).innerJoin(SUBJECT)
				.on(SUBMITTED_SUBJECTS_FOR_ENROLLMENT.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE))
				.where(STUDENT_ENROLLMENT.STUDENT_NO.eq(studentNo).and(PROFESSOR_LOAD.SECTION_ID.eq(sectionId))
						.and(SUBMITTED_SUBJECTS_FOR_ENROLLMENT.STATUS.eq("Approved"))
						.and(SUBMITTED_SUBJECTS_FOR_ENROLLMENT.ENROLLMENT_ID.eq(enrollmentId)))
				.orderBy(SUBJECT.SUBJECT_CODE).fetchMaps();
	}

	public List<Map<String, Object>> selectSubmittedSubjectsOfstudentPerEnrollmentId(Integer studentNo,
			Integer enrollmentId) {
		return dslContext
				.selectDistinct(SUBMITTED_SUBJECTS_FOR_ENROLLMENT.SUBMITTED_SUBJECTS_ID.as("submittedSubjectsId"),
						STUDENT_ENROLLMENT.STUDENT_NO.as("studentNo"), SUBJECT.ABBREVATION,
						SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS,
						SUBMITTED_SUBJECTS_FOR_ENROLLMENT.STATUS)
				.from(SUBMITTED_SUBJECTS_FOR_ENROLLMENT).innerJoin(STUDENT_ENROLLMENT)
				.on(SUBMITTED_SUBJECTS_FOR_ENROLLMENT.ENROLLMENT_ID.eq(STUDENT_ENROLLMENT.ENROLLMENT_ID))
				.innerJoin(SUBJECT).on(SUBMITTED_SUBJECTS_FOR_ENROLLMENT.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE))
				.where(STUDENT_ENROLLMENT.STUDENT_NO.eq(studentNo)
						.and(SUBMITTED_SUBJECTS_FOR_ENROLLMENT.ENROLLMENT_ID.eq(enrollmentId)))
				.orderBy(SUBJECT.SUBJECT_CODE).fetchMaps();
	}

	public Map<String, Object> updateSubmittedSubjectsOfstudentPerEnrollmentStatus(Integer submittedSubjectsId,
			String status) {
		SubmittedSubjectsForEnrollment subjectsForEnrollment = dslContext.update(SUBMITTED_SUBJECTS_FOR_ENROLLMENT)
				.set(SUBMITTED_SUBJECTS_FOR_ENROLLMENT.STATUS, status)
				.where(SUBMITTED_SUBJECTS_FOR_ENROLLMENT.SUBMITTED_SUBJECTS_ID.eq(submittedSubjectsId)).returning()
				.fetchOne().into(SubmittedSubjectsForEnrollment.class);

		return dslContext
				.selectDistinct(SUBMITTED_SUBJECTS_FOR_ENROLLMENT.SUBMITTED_SUBJECTS_ID.as("submittedSubjectsId"),
						STUDENT_ENROLLMENT.STUDENT_NO.as("studentNo"), SUBJECT.ABBREVATION,
						SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS,
						SUBMITTED_SUBJECTS_FOR_ENROLLMENT.STATUS)
				.from(SUBMITTED_SUBJECTS_FOR_ENROLLMENT).innerJoin(STUDENT_ENROLLMENT)
				.on(SUBMITTED_SUBJECTS_FOR_ENROLLMENT.ENROLLMENT_ID.eq(STUDENT_ENROLLMENT.ENROLLMENT_ID))
				.innerJoin(PROFESSOR_LOAD)
				.on(SUBMITTED_SUBJECTS_FOR_ENROLLMENT.SUBJECT_CODE.eq(PROFESSOR_LOAD.SUBJECT_CODE)).innerJoin(SUBJECT)
				.on(SUBMITTED_SUBJECTS_FOR_ENROLLMENT.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE))
				.where(SUBMITTED_SUBJECTS_FOR_ENROLLMENT.SUBMITTED_SUBJECTS_ID
						.eq(subjectsForEnrollment.getSubmittedSubjectsId()))
				.fetchOneMap();
	}

}
