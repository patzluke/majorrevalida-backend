package org.ssglobal.training.codes.repository;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.model.UserAndAdmin;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.AcademicYear;
import org.ssglobal.training.codes.tables.pojos.Admin;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.Curriculum;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.Program;
import org.ssglobal.training.codes.tables.pojos.Student;
import org.ssglobal.training.codes.tables.pojos.StudentApplicant;
import org.ssglobal.training.codes.tables.pojos.Users;

@Repository
public class AdminCapabilitiesRepository {

	@Autowired
	private DSLContext dslContext;

	private final org.ssglobal.training.codes.tables.Users USERS = org.ssglobal.training.codes.tables.Users.USERS;
	private final org.ssglobal.training.codes.tables.Admin ADMIN = org.ssglobal.training.codes.tables.Admin.ADMIN;
	private final org.ssglobal.training.codes.tables.Student STUDENT = org.ssglobal.training.codes.tables.Student.STUDENT;
	private final org.ssglobal.training.codes.tables.StudentApplicant STUDENT_APPLICANT = org.ssglobal.training.codes.tables.StudentApplicant.STUDENT_APPLICANT;
	private final org.ssglobal.training.codes.tables.AcademicYear ACADEMIC_YEAR = org.ssglobal.training.codes.tables.AcademicYear.ACADEMIC_YEAR;
	private final org.ssglobal.training.codes.tables.Program PROGRAM = org.ssglobal.training.codes.tables.Program.PROGRAM;
	private final org.ssglobal.training.codes.tables.Course COURSE = org.ssglobal.training.codes.tables.Course.COURSE;
	private final org.ssglobal.training.codes.tables.Major MAJOR = org.ssglobal.training.codes.tables.Major.MAJOR;
	private final org.ssglobal.training.codes.tables.Curriculum CURRICULUM = org.ssglobal.training.codes.tables.Curriculum.CURRICULUM;

	// ------------------------FOR ADMIN
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
					insertedUser.getActiveDeactive(), insertedUser.getImage(), insertedAdmin.getAdminId(),
					insertedAdmin.getAdminNo());
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
					updatedUser.getPassword(), updatedUser.getEmail(), updatedUser.getContactNo(),
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
		Users deletedUser = dslContext.update(USERS).set(USERS.ACTIVE_DEACTIVE, false)
				.where(USERS.USER_ID.eq(userAdminId)).returning().fetchOne().into(Users.class);

		Admin deletedAdmin = dslContext.selectFrom(ADMIN).where(ADMIN.USER_ID.eq(deletedUser.getUserId())).fetchOne()
				.into(Admin.class);

		if (deletedUser != null && deletedAdmin != null) {
			UserAndAdmin newUserAdmin = new UserAndAdmin(deletedUser.getUserId(), deletedUser.getUsername(),
					deletedUser.getPassword(), deletedUser.getEmail(), deletedUser.getContactNo(),
					deletedUser.getFirstName(), deletedUser.getMiddleName(), deletedUser.getLastName(),
					deletedUser.getUserType(), deletedUser.getBirthDate(), deletedUser.getAddress(),
					deletedUser.getCivilStatus(), deletedUser.getGender(), deletedUser.getNationality(),
					deletedUser.getActiveDeactive(), deletedUser.getImage(), deletedAdmin.getAdminId(),
					deletedAdmin.getAdminNo());
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
		 * This will add the User's data limited to: username, password, email,
		 * contactNo first_name, middle_name, last_name, birth_date, address,
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
		 * This will add the Student's data limited to: user_id, curriculumCode,
		 * parentNo, sem, yearLevel, academicYearId
		 */

		Student insertStudent = dslContext.insertInto(STUDENT).set(STUDENT.USER_ID, insertUser.getUserId())
				.set(STUDENT.CURRICULUM_CODE, student.getCurriculumCode()).set(STUDENT.PARENT_NO, student.getParentNo())
				.set(STUDENT.SEM, student.getSem()).set(STUDENT.YEAR_LEVEL, student.getYearLevel())
				.set(STUDENT.ACADEMIC_YEAR_ID, student.getAcademicYearId()).returning().fetchOne().into(Student.class);

		// Return all the information of the student
		UserAndStudent information = new UserAndStudent(insertUser.getUsername(), insertUser.getPassword(),
				insertUser.getEmail(), insertUser.getContactNo(), insertUser.getFirstName(), insertUser.getMiddleName(),
				insertUser.getLastName(), insertUser.getUserType(), insertUser.getBirthDate(), insertUser.getAddress(),
				insertUser.getCivilStatus(), insertUser.getGender(), insertUser.getNationality(),
				insertUser.getActiveDeactive(), insertUser.getImage(), insertStudent.getUserId(),
				insertStudent.getCurriculumCode(), insertStudent.getParentNo(), insertStudent.getSem(),
				insertStudent.getYearLevel(), insertStudent.getAcademicYearId());

		return information;
	}

	// ------------------------FOR Applicants

	public List<StudentApplicant> selectAllStudentApplicants() {
		List<StudentApplicant> students = dslContext.selectFrom(STUDENT_APPLICANT).fetchInto(StudentApplicant.class);
		return students;
	}

	public StudentApplicant updateStudentApplicantStats(StudentApplicant studentApplicant) {
		/*
		 * This will add the User's data limited to: username, password, first_name,
		 * middle_name, last_name, birth_date, address, civil_status, gender,
		 * nationality, active_deactive, image
		 */
		StudentApplicant applicant = dslContext.update(STUDENT_APPLICANT)
				.set(STUDENT_APPLICANT.EMAIL, studentApplicant.getEmail())
				.set(STUDENT_APPLICANT.CONTACT_NO, studentApplicant.getContactNo())
				.set(STUDENT_APPLICANT.FIRST_NAME, studentApplicant.getFirstName())
				.set(STUDENT_APPLICANT.MIDDLE_NAME, studentApplicant.getMiddleName())
				.set(STUDENT_APPLICANT.LAST_NAME, studentApplicant.getLastName())
				.set(STUDENT_APPLICANT.BIRTH_DATE, studentApplicant.getBirthDate())
				.set(STUDENT_APPLICANT.ADDRESS, studentApplicant.getAddress())
				.set(STUDENT_APPLICANT.CIVIL_STATUS, studentApplicant.getCivilStatus())
				.set(STUDENT_APPLICANT.GENDER, studentApplicant.getGender())
				.set(STUDENT_APPLICANT.NATIONALITY, studentApplicant.getNationality())
				.set(STUDENT_APPLICANT.DATE_APPLIED, studentApplicant.getDateApplied())
				.set(STUDENT_APPLICANT.DATE_ACCEPTED, studentApplicant.getDateAccepted())
				.set(STUDENT_APPLICANT.STATUS, studentApplicant.getStatus())
				.set(STUDENT_APPLICANT.STUDENT_TYPE, studentApplicant.getStudentType())
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

	// -------------------------- FOR PROGRAM
	public Program addProgram(Program program) {
		/*
		 * The program data added is limited to: program_title
		 */
		Program addedProgram = dslContext.insertInto(PROGRAM).set(PROGRAM.PROGRAM_TITLE, program.getProgramTitle())
				.returning().fetchOne().into(Program.class);

		return addedProgram;
	}

	// -------------------------- FOR COURSE
	public Course addCourse(Course course) {
		/*
		 * The program data added is limited to: program_code and course_title
		 */
		Course addedCourse = dslContext.insertInto(COURSE).set(COURSE.PROGRAM_CODE, course.getProgramCode())
				.set(COURSE.COURSE_TITLE, course.getCourseTitle()).returning().fetchOne().into(Course.class);

		return addedCourse;
	}

	// -------------------------- FOR MAJOR
	public Major addMajor(Major major) {
		/*
		 * The program data added is limited to: course_code and major_title
		 */
		Major addedMajor = dslContext.insertInto(MAJOR).set(MAJOR.COURSE_CODE, major.getCourseCode())
				.set(MAJOR.MAJOR_TITLE, major.getMajorTitle()).returning().fetchOne().into(Major.class);

		return addedMajor;
	}

	// -------------------------- FOR CURRICULUM
	public Curriculum addCurriculum(Curriculum curriculum) {
		/*
		 * The program data added is limited to: major_code and curriculum_name
		 */
		Curriculum addedCurriculum = dslContext.insertInto(CURRICULUM)
				.set(CURRICULUM.MAJOR_CODE, curriculum.getMajorCode())
				.set(CURRICULUM.CURRICULUM_NAME, curriculum.getCurriculumName()).returning().fetchOne()
				.into(Curriculum.class);

		return addedCurriculum;

	}

}
