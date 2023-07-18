package org.ssglobal.training.codes.repository;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.model.StudentCourseData;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.Curriculum;
import org.ssglobal.training.codes.tables.pojos.Grades;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.Program;
import org.ssglobal.training.codes.tables.pojos.Student;
import org.ssglobal.training.codes.tables.pojos.StudentAttendance;
import org.ssglobal.training.codes.tables.pojos.Users;

@Repository
public class StudentCapabilitiesRepository {

	@Autowired
	private DSLContext dslContext;

	private final org.ssglobal.training.codes.tables.Student STUDENT = org.ssglobal.training.codes.tables.Student.STUDENT;
	private final org.ssglobal.training.codes.tables.Users USERS = org.ssglobal.training.codes.tables.Users.USERS;
	private final org.ssglobal.training.codes.tables.Grades GRADES = org.ssglobal.training.codes.tables.Grades.GRADES;
	private final org.ssglobal.training.codes.tables.Curriculum CURRICULUM = org.ssglobal.training.codes.tables.Curriculum.CURRICULUM;
	private final org.ssglobal.training.codes.tables.Major MAJOR = org.ssglobal.training.codes.tables.Major.MAJOR;
	private final org.ssglobal.training.codes.tables.Course COURSE = org.ssglobal.training.codes.tables.Course.COURSE;
	private final org.ssglobal.training.codes.tables.Program PROGRAM = org.ssglobal.training.codes.tables.Program.PROGRAM;
	private final org.ssglobal.training.codes.tables.StudentAttendance STUDENT_ATTENDANCE = org.ssglobal.training.codes.tables.StudentAttendance.STUDENT_ATTENDANCE;

	public List<Users> selectAllUsers() {
		return dslContext.selectFrom(USERS).fetchInto(Users.class);
	}
	
	public UserAndStudent viewStudentProfile(Integer studentNo) {

		// Get the student's data via student table
		Student studentData = dslContext.selectFrom(STUDENT).where(STUDENT.STUDENT_NO.eq(studentNo))
				.fetchOneInto(Student.class);
		
		// Get the student's data via users table
		Users userData = dslContext.selectFrom(USERS).where(USERS.USER_ID.eq(studentData.getUserId()))
				.fetchOneInto(Users.class);

		// Return all the information of the updated student
		UserAndStudent information = new UserAndStudent(userData.getUserId(), userData.getUsername(),
				userData.getPassword(), userData.getEmail(), userData.getContactNo(), userData.getFirstName(),
				userData.getMiddleName(), userData.getLastName(), userData.getUserType(), userData.getBirthDate(),
				userData.getAddress(), userData.getCivilStatus(), userData.getGender(), userData.getNationality(),
				userData.getActiveStatus(), userData.getActiveDeactive(), userData.getImage(),
				studentData.getStudentId(), studentData.getStudentNo(), studentData.getParentNo(),
				studentData.getCurriculumCode(), studentData.getYearLevel(), studentData.getAcademicYearId());

		return information;

	}

	public UserAndStudent updateStudentProfile(UserAndStudent student) {
		/*
		 * This will add the User's data limited to: username, password, email,
		 * contactNo, first_name, middle_name, last_name, birth_date, address,
		 * civil_status, gender, nationality, active_deactive, and image
		 */
		Users updatedUser = dslContext.update(USERS)
				.set(USERS.USERNAME, student.getUsername()).set(USERS.EMAIL, student.getEmail())
				.set(USERS.CONTACT_NO, student.getContactNo()).set(USERS.FIRST_NAME, student.getFirstName())
				.set(USERS.MIDDLE_NAME, student.getMiddleName()).set(USERS.LAST_NAME, student.getLastName())
				.set(USERS.USER_TYPE, student.getUserType()).set(USERS.BIRTH_DATE, student.getBirthDate())
				.set(USERS.ADDRESS, student.getAddress()).set(USERS.CIVIL_STATUS, student.getCivilStatus())
				.set(USERS.GENDER, student.getGender()).set(USERS.NATIONALITY, student.getNationality())
				.set(USERS.IMAGE, student.getImage()).where(USERS.USER_ID.eq(student.getUserId()))
				.returning().fetchOne().into(Users.class);
		
		if (!student.getPassword().isBlank()) {
			dslContext.update(USERS).set(USERS.PASSWORD, student.getPassword())
			.where(USERS.USER_ID.eq(student.getUserId()))
			.execute();
		}

		Student updatedStudent = dslContext.update(STUDENT).set(STUDENT.STUDENT_NO, student.getStudentNo())
				.set(STUDENT.USER_ID, student.getUserId()).set(STUDENT.PARENT_NO, student.getParentNo())
				.set(STUDENT.CURRICULUM_CODE, student.getCurriculumCode())
				.set(STUDENT.ACADEMIC_YEAR_ID, student.getAcademicYearId())
				.where(STUDENT.STUDENT_NO.eq(student.getStudentNo()))
				.returning().fetchOne().into(Student.class);
		
		if (!updatedStudent.equals(null) && !updatedUser.equals(null)) {
			UserAndStudent information = new UserAndStudent(updatedUser.getUserId(), updatedUser.getUsername(),
					updatedUser.getPassword(), updatedUser.getEmail(), updatedUser.getContactNo(),
					updatedUser.getFirstName(), updatedUser.getMiddleName(), updatedUser.getLastName(),
					updatedUser.getUserType(), updatedUser.getBirthDate(), updatedUser.getAddress(),
					updatedUser.getCivilStatus(), updatedUser.getGender(), updatedUser.getNationality(),
					updatedUser.getActiveStatus(), updatedUser.getActiveDeactive(), updatedUser.getImage(),
					updatedStudent.getStudentId(), updatedStudent.getStudentNo(), updatedStudent.getParentNo(),
					updatedStudent.getCurriculumCode(), updatedStudent.getYearLevel(), updatedStudent.getAcademicYearId());
			return information;	
		}
		return null;
	}

	public StudentCourseData viewCourse(Integer studentNo) {
		// get the student's data
		Student student = dslContext.selectFrom(STUDENT).where(STUDENT.STUDENT_NO.eq(studentNo))
				.fetchOneInto(Student.class);

		// get the major_code AND curriculum_name from the curriculum table
		Curriculum curriculum = dslContext.select(CURRICULUM.MAJOR_CODE, CURRICULUM.CURRICULUM_NAME).from(CURRICULUM)
				.where(CURRICULUM.CURRICULUM_CODE.eq(student.getCurriculumCode())).fetchOneInto(Curriculum.class);

		// get the course_code AND major_title from major table
		Major major = dslContext.select(MAJOR.COURSE_CODE, MAJOR.MAJOR_TITLE).from(MAJOR)
				.where(MAJOR.MAJOR_CODE.eq(curriculum.getMajorCode())).fetchOneInto(Major.class);

		// get the program_code and the course_title from course table
		Course course = dslContext.select(COURSE.PROGRAM_CODE, COURSE.COURSE_TITLE).from(COURSE)
				.where(COURSE.COURSE_CODE.eq(major.getCourseCode())).fetchOneInto(Course.class);

		// get the program_title
		Program program = dslContext.select(PROGRAM.PROGRAM_TITLE).from(PROGRAM)
				.where(PROGRAM.PROGRAM_CODE.eq(course.getProgramCode())).fetchOneInto(Program.class);

		/*
		 * Return the userId, curriculum_code, major_code, course_code, program_code
		 * curriculum_name, major_title, course_title, program_title
		 */

		StudentCourseData studentData = new StudentCourseData(student.getUserId(), student.getCurriculumCode(),
				curriculum.getMajorCode(), major.getCourseCode(), course.getProgramCode(),
				curriculum.getCurriculumName(), major.getMajorTitle(), course.getCourseTitle(),
				program.getProgramTitle());

		return studentData;
	}

	public Major editMajor(Major editedMajor) {
		/*
		 * the major can be edited limited to the ff data: major_title and course_code
		 */
		return dslContext.update(MAJOR).set(MAJOR.MAJOR_TITLE, editedMajor.getMajorTitle())
				.set(MAJOR.COURSE_CODE, editedMajor.getCourseCode())
				.where(MAJOR.MAJOR_CODE.eq(editedMajor.getMajorCode())).returning().fetchOne().into(Major.class);
	}

	public Course editCourse(Course course) {
		/*
		 * The program data added is limited to: program_code and course_title
		 */
		Course editCourse = dslContext.update(COURSE).set(COURSE.PROGRAM_CODE, course.getProgramCode())
				.set(COURSE.COURSE_TITLE, course.getCourseTitle()).where(COURSE.COURSE_CODE.eq(course.getCourseCode()))
				.returning().fetchOne().into(Course.class);
		return editCourse;
	}

	public Program editProgram(Program program) {
		/*
		 * The program data added is limited to: program_title
		 */
		Program editProgram = dslContext.update(PROGRAM).set(PROGRAM.PROGRAM_TITLE, program.getProgramTitle())
				.where(PROGRAM.PROGRAM_CODE.eq(program.getProgramCode())).returning().fetchOne().into(Program.class);
		return editProgram;
	}

	public StudentAttendance viewStudentAttendance(Integer studentNo) {
		// get all the student attendance data
		return dslContext.selectFrom(STUDENT_ATTENDANCE).where(STUDENT_ATTENDANCE.STUDENT_NO.eq(studentNo))
				.fetchOneInto(StudentAttendance.class);
	}

	public Grades viewStudentGrade(Integer studentNo) {
		// Get the grade where the studentId equal to the Grade's table student_no
		return dslContext.selectFrom(GRADES).where(GRADES.STUDENT_NO.eq(studentNo)).fetchOneInto(Grades.class);
	}

}
