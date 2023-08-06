package org.ssglobal.training.codes.repository;

import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.model.StudentCourseData;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.Curriculum;
import org.ssglobal.training.codes.tables.pojos.EvaluationQuestionAnswer;
import org.ssglobal.training.codes.tables.pojos.Grades;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.Program;
import org.ssglobal.training.codes.tables.pojos.Student;
import org.ssglobal.training.codes.tables.pojos.StudentAttendance;
import org.ssglobal.training.codes.tables.pojos.SubmittedSubjectsForEnrollment;
import org.ssglobal.training.codes.tables.pojos.Users;
import org.ssglobal.training.codes.tables.pojos.WebsiteActivationToggle;
import org.ssglobal.training.codes.tables.records.SubmittedSubjectsForEnrollmentRecord;

@Repository
public class StudentCapabilitiesRepository {

	@Autowired
	private DSLContext dslContext;

	private final org.ssglobal.training.codes.tables.Student STUDENT = org.ssglobal.training.codes.tables.Student.STUDENT;
	private final org.ssglobal.training.codes.tables.Users USERS = org.ssglobal.training.codes.tables.Users.USERS;
	private final org.ssglobal.training.codes.tables.Grades GRADES = org.ssglobal.training.codes.tables.Grades.GRADES;
	private final org.ssglobal.training.codes.tables.Section SECTION = org.ssglobal.training.codes.tables.Section.SECTION;
	private final org.ssglobal.training.codes.tables.Subject SUBJECT = org.ssglobal.training.codes.tables.Subject.SUBJECT;
	private final org.ssglobal.training.codes.tables.MajorSubject MAJOR_SUBJECT = org.ssglobal.training.codes.tables.MajorSubject.MAJOR_SUBJECT;
	private final org.ssglobal.training.codes.tables.MinorSubject MINOR_SUBJECT = org.ssglobal.training.codes.tables.MinorSubject.MINOR_SUBJECT;
	private final org.ssglobal.training.codes.tables.TSubjectDetailHistory T_SUBJECT_DETAIL_HISTORY = org.ssglobal.training.codes.tables.TSubjectDetailHistory.T_SUBJECT_DETAIL_HISTORY;
	private final org.ssglobal.training.codes.tables.Curriculum CURRICULUM = org.ssglobal.training.codes.tables.Curriculum.CURRICULUM;
	private final org.ssglobal.training.codes.tables.Room ROOM = org.ssglobal.training.codes.tables.Room.ROOM;
	private final org.ssglobal.training.codes.tables.Major MAJOR = org.ssglobal.training.codes.tables.Major.MAJOR;
	private final org.ssglobal.training.codes.tables.Course COURSE = org.ssglobal.training.codes.tables.Course.COURSE;
	private final org.ssglobal.training.codes.tables.Program PROGRAM = org.ssglobal.training.codes.tables.Program.PROGRAM;
	private final org.ssglobal.training.codes.tables.StudentAttendance STUDENT_ATTENDANCE = org.ssglobal.training.codes.tables.StudentAttendance.STUDENT_ATTENDANCE;
	private final org.ssglobal.training.codes.tables.StudentSubjectEnrolled STUDENT_SUBJECT_ENROLLED = org.ssglobal.training.codes.tables.StudentSubjectEnrolled.STUDENT_SUBJECT_ENROLLED;
	private final org.ssglobal.training.codes.tables.StudentSchedule STUDENT_SCHEDULE = org.ssglobal.training.codes.tables.StudentSchedule.STUDENT_SCHEDULE;
	private final org.ssglobal.training.codes.tables.StudentEnrollment STUDENT_ENROLLMENT = org.ssglobal.training.codes.tables.StudentEnrollment.STUDENT_ENROLLMENT;
	private final org.ssglobal.training.codes.tables.AcademicYear ACADEMIC_YEAR = org.ssglobal.training.codes.tables.AcademicYear.ACADEMIC_YEAR;
	private final org.ssglobal.training.codes.tables.Professor PROFESSOR = org.ssglobal.training.codes.tables.Professor.PROFESSOR;
	private final org.ssglobal.training.codes.tables.ProfessorLoad PROFESSOR_LOAD = org.ssglobal.training.codes.tables.ProfessorLoad.PROFESSOR_LOAD;
	private final org.ssglobal.training.codes.tables.SubmittedSubjectsForEnrollment SUBMITTED_SUBJECTS_FOR_ENROLLMENT = org.ssglobal.training.codes.tables.SubmittedSubjectsForEnrollment.SUBMITTED_SUBJECTS_FOR_ENROLLMENT;
	private final org.ssglobal.training.codes.tables.EvaluationQuestion EVALUATION_QUESTION = org.ssglobal.training.codes.tables.EvaluationQuestion.EVALUATION_QUESTION;
	private final org.ssglobal.training.codes.tables.EvaluationQuestionAnswer EVALUATION_QUESTION_ANSWER = org.ssglobal.training.codes.tables.EvaluationQuestionAnswer.EVALUATION_QUESTION_ANSWER;
	private final org.ssglobal.training.codes.tables.WebsiteActivationToggle WEBSITE_ACTIVATION_TOGGLE = org.ssglobal.training.codes.tables.WebsiteActivationToggle.WEBSITE_ACTIVATION_TOGGLE;

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

		Users currentUserData = dslContext.selectFrom(USERS).where(USERS.USER_ID.eq(student.getUserId()))
				.fetchOneInto(Users.class);

		Users updatedUser = dslContext.update(USERS).set(USERS.USERNAME, student.getUsername())
				.set(USERS.EMAIL, student.getEmail()).set(USERS.CONTACT_NO, student.getContactNo())
				.set(USERS.PASSWORD, student.getPassword()).set(USERS.FIRST_NAME, student.getFirstName())
				.set(USERS.MIDDLE_NAME, student.getMiddleName()).set(USERS.LAST_NAME, student.getLastName())
				.set(USERS.USER_TYPE, student.getUserType()).set(USERS.BIRTH_DATE, student.getBirthDate())
				.set(USERS.ADDRESS, student.getAddress()).set(USERS.CIVIL_STATUS, student.getCivilStatus())
				.set(USERS.GENDER, student.getGender()).set(USERS.NATIONALITY, student.getNationality())
				.set(USERS.IMAGE, student.getImage()).where(USERS.USER_ID.eq(student.getUserId())).returning()
				.fetchOne().into(Users.class);

		// if the sent password data is blank update back the current password
		if (student.getPassword().isBlank()) {
			dslContext.update(USERS).set(USERS.PASSWORD, currentUserData.getPassword())
					.where(USERS.USER_ID.eq(student.getUserId())).execute();
		}

		// if the sent image data is blank update back the current image
		if (student.getImage().equals("http://localhost:8080/api/file/images/undefined")) {
			dslContext.update(USERS).set(USERS.IMAGE, currentUserData.getImage())
					.where(USERS.USER_ID.eq(updatedUser.getUserId())).execute();
		}

		Student updatedStudent = dslContext.update(STUDENT).set(STUDENT.STUDENT_NO, student.getStudentNo())
				.set(STUDENT.USER_ID, student.getUserId()).where(STUDENT.STUDENT_NO.eq(student.getStudentNo()))
				.returning().fetchOne().into(Student.class);

		if (!updatedStudent.equals(null) && !updatedUser.equals(null)) {
			UserAndStudent information = new UserAndStudent(updatedUser.getUserId(), updatedUser.getUsername(),
					updatedUser.getPassword(), updatedUser.getEmail(), updatedUser.getContactNo(),
					updatedUser.getFirstName(), updatedUser.getMiddleName(), updatedUser.getLastName(),
					updatedUser.getUserType(), updatedUser.getBirthDate(), updatedUser.getAddress(),
					updatedUser.getCivilStatus(), updatedUser.getGender(), updatedUser.getNationality(),
					updatedUser.getActiveStatus(), updatedUser.getActiveDeactive(), updatedUser.getImage(),
					updatedStudent.getStudentId(), updatedStudent.getStudentNo(), updatedStudent.getParentNo(),
					updatedStudent.getCurriculumCode(), updatedStudent.getYearLevel(),
					updatedStudent.getAcademicYearId());
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

	public List<Map<String, Object>> selectAllStudentSubjectEnrolledByStudentNo(Integer studentNo) {
		return dslContext
				.selectDistinct(STUDENT_ENROLLMENT.STUDENT_NO.as("studentNo"), SUBJECT.SUBJECT_CODE.as("subjectCode"),
						SUBJECT.ABBREVATION, SUBJECT.SUBJECT_TITLE.as("subjectTitle"), PROFESSOR_LOAD.LOAD_ID.as("loadId"),
						PROFESSOR_LOAD.PROFESSOR_NO.as("professorNo"), USERS.FIRST_NAME.as("firstName"), USERS.MIDDLE_NAME.as("middleName"),
						USERS.LAST_NAME.as("lastName"), USERS.IMAGE)
				.from(STUDENT_SUBJECT_ENROLLED)
				.innerJoin(STUDENT_ENROLLMENT).on(STUDENT_SUBJECT_ENROLLED.ENROLLMENT_ID.eq(STUDENT_ENROLLMENT.ENROLLMENT_ID))
				.innerJoin(PROFESSOR_LOAD).on(STUDENT_SUBJECT_ENROLLED.LOAD_ID.eq(PROFESSOR_LOAD.LOAD_ID))
				.innerJoin(PROFESSOR).on(PROFESSOR_LOAD.PROFESSOR_NO.eq(PROFESSOR.PROFESSOR_NO))
				.innerJoin(USERS).on(PROFESSOR.USER_ID.eq(USERS.USER_ID))
				.innerJoin(SUBJECT).on(PROFESSOR_LOAD.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE))
				.where(STUDENT_ENROLLMENT.STUDENT_NO.eq(studentNo)
						.and(STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID.eq(dslContext.select(DSL.max(STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID)).from(STUDENT_ENROLLMENT)))
					  ).orderBy(SUBJECT.SUBJECT_CODE).fetchMaps();
	}

//	List of students attendance
	public List<Map<String, Object>> selectStudentAttendanceByAndSubjectAndStudentNo(String subjectTitle,
			Integer studentNo) {

		return dslContext
				.select(STUDENT_ATTENDANCE.STUDENT_ATTENDANCE_ID.as("studentAttendanceId"),
						USERS.LAST_NAME.as("lastName"), USERS.FIRST_NAME.as("firstName"),
						USERS.MIDDLE_NAME.as("middleName"), STUDENT_ATTENDANCE.STATUS,
						STUDENT_ATTENDANCE.ATTENDANCE_DATE.as("attendanceDate"))
				.from(STUDENT_ATTENDANCE).innerJoin(STUDENT).on(STUDENT_ATTENDANCE.STUDENT_NO.eq(STUDENT.STUDENT_NO))
				.innerJoin(USERS).on(STUDENT.USER_ID.eq(USERS.USER_ID)).innerJoin(PROFESSOR_LOAD)
				.on(STUDENT_ATTENDANCE.LOAD_ID.eq(PROFESSOR_LOAD.LOAD_ID)).innerJoin(SECTION)
				.on(PROFESSOR_LOAD.SECTION_ID.eq(SECTION.SECTION_ID)).innerJoin(SUBJECT)
				.on(PROFESSOR_LOAD.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE))
				.where(SUBJECT.SUBJECT_TITLE.eq(subjectTitle).and(STUDENT.STUDENT_NO.eq(studentNo)))
				.orderBy(STUDENT_ATTENDANCE.ATTENDANCE_DATE).fetchMaps();
	}

	// ------------ FOR GRADES
	public List<Map<String, Object>> selectAllSubjectGradesOfStudent(Integer studentNo) {
		return dslContext
				.selectDistinct(GRADES.GRADE_ID.as("gradeId"), GRADES.STUDENT_NO.as("studentNo"),
						USERS.FIRST_NAME.as("firstName"), USERS.MIDDLE_NAME.as("middleName"),
						USERS.LAST_NAME.as("lastName"), USERS.EMAIL, USERS.ADDRESS, GRADES.PRELIM_GRADE.as("prelimGrade"),
						GRADES.FINALS_GRADE.as("finalsGrade"), GRADES.TOTAL_GRADE.as("totalGrade"), GRADES.COMMENT,
						GRADES.REMARKS, T_SUBJECT_DETAIL_HISTORY.SUBJECT_CODE.as("subjectCode"),
						SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.ABBREVATION, SUBJECT.UNITS,
						ACADEMIC_YEAR.ACADEMIC_YEAR_.as("academicYear"), ACADEMIC_YEAR.SEMESTER, 
						COURSE.COURSE_TITLE.as("courseTitle"), MAJOR.MAJOR_TITLE.as("majorTitle"))
				.from(GRADES).innerJoin(T_SUBJECT_DETAIL_HISTORY)
				.on(GRADES.SUBJECT_DETAIL_HIS_ID.eq(T_SUBJECT_DETAIL_HISTORY.SUBJECT_DETAIL_HIS_ID))
				.innerJoin(ACADEMIC_YEAR)
				.on(T_SUBJECT_DETAIL_HISTORY.ACADEMIC_YEAR_ID.eq(ACADEMIC_YEAR.ACADEMIC_YEAR_ID)).innerJoin(SUBJECT)
				.on(T_SUBJECT_DETAIL_HISTORY.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE)).innerJoin(STUDENT)
				.on(GRADES.STUDENT_NO.eq(STUDENT.STUDENT_NO)).innerJoin(USERS).on(STUDENT.USER_ID.eq(USERS.USER_ID))
				.join(CURRICULUM).on(CURRICULUM.CURRICULUM_CODE.eq(STUDENT.CURRICULUM_CODE))
				.join(MAJOR).on(MAJOR.MAJOR_CODE.eq(CURRICULUM.MAJOR_CODE))
				.join(COURSE).on(COURSE.COURSE_CODE.eq(MAJOR.COURSE_CODE))
				.where(GRADES.STUDENT_NO.eq(studentNo)).orderBy(T_SUBJECT_DETAIL_HISTORY.SUBJECT_CODE).fetchMaps();
	}

	// ------------ FOR GRADES
	public List<Map<String, Object>> selectEnrolledSchoolYearOfStudent(Integer studentNo) {
		return dslContext
				.select(STUDENT_ENROLLMENT.ENROLLMENT_ID.as("enrollmentId"),
						ACADEMIC_YEAR.ACADEMIC_YEAR_.as("academicYear"), ACADEMIC_YEAR.SEMESTER,
						STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID.as("academicYearId"))
				.from(STUDENT_ENROLLMENT).innerJoin(ACADEMIC_YEAR)
				.on(STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID.eq(ACADEMIC_YEAR.ACADEMIC_YEAR_ID))
				.where(STUDENT_ENROLLMENT.STUDENT_NO.eq(studentNo))
				.orderBy(ACADEMIC_YEAR.ACADEMIC_YEAR_, ACADEMIC_YEAR.SEMESTER).fetchMaps();
	}

	// ------------ FOR Student Schedule
	public List<Map<String, Object>> selectScheduleOfStudent(Integer studentNo, Integer academicYearId) {
		return dslContext
				.select(STUDENT_SCHEDULE.STUDENT_NO.as("studentNo"), STUDENT_SCHEDULE.LOAD_ID.as("loadId"),
						SUBJECT.ABBREVATION, SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS,
						PROFESSOR_LOAD.DAY, PROFESSOR_LOAD.START_TIME.as("startTime"),
						PROFESSOR_LOAD.END_TIME.as("endTime"), ROOM.ROOM_NO.as("roomNo"),
						USERS.FIRST_NAME.as("firstName"), USERS.MIDDLE_NAME.as("middleName"),
						USERS.LAST_NAME.as("lastName"))
				.from(STUDENT_SCHEDULE).innerJoin(PROFESSOR_LOAD)
				.on(STUDENT_SCHEDULE.LOAD_ID.eq(PROFESSOR_LOAD.LOAD_ID)).innerJoin(SUBJECT)
				.on(PROFESSOR_LOAD.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE)).innerJoin(ROOM)
				.on(PROFESSOR_LOAD.ROOM_ID.eq(ROOM.ROOM_ID)).innerJoin(PROFESSOR)
				.on(PROFESSOR_LOAD.PROFESSOR_NO.eq(PROFESSOR.PROFESSOR_NO)).innerJoin(USERS)
				.on(PROFESSOR.USER_ID.eq(USERS.USER_ID))
				.where(STUDENT_SCHEDULE.STUDENT_NO.eq(studentNo)
						.and(STUDENT_SCHEDULE.ACADEMIC_YEAR_ID.eq(academicYearId)))
				.orderBy(SUBJECT.SUBJECT_TITLE).fetchMaps();
	}

	// ------------ FOR Major Subject (for curriculum display)
	public List<Map<String, Object>> selectAllMajorSubjectsInACurriculumOfStudent(Integer studentNo) {
		org.ssglobal.training.codes.tables.Subject SUBJECT2 = org.ssglobal.training.codes.tables.Subject.SUBJECT
				.as("SUBJECT2");
		Student student = dslContext.selectFrom(STUDENT).where(STUDENT.STUDENT_NO.eq(studentNo))
				.fetchOneInto(Student.class);

		return dslContext
				.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION,
						SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS,
						MAJOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MAJOR_SUBJECT.SEM,
						SUBJECT2.SUBJECT_TITLE.as("preRequisite"))
				.from(MAJOR_SUBJECT).innerJoin(SUBJECT).on(MAJOR_SUBJECT.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE))
				.join(SUBJECT2).on(MAJOR_SUBJECT.PRE_REQUISITES.eq(SUBJECT2.SUBJECT_CODE))
				.where(MAJOR_SUBJECT.CURRICULUM_CODE.eq(student.getCurriculumCode()))
				.orderBy(MAJOR_SUBJECT.YEAR_LEVEL, MAJOR_SUBJECT.SEM).fetchMaps();
	}

	// ------------ FOR Minor Subject (for curriculum display)
	public List<Map<String, Object>> selectAllMinorSubjectsInACurriculumOfStudent(Integer studentNo) {
		org.ssglobal.training.codes.tables.Subject SUBJECT2 = org.ssglobal.training.codes.tables.Subject.SUBJECT
				.as("SUBJECT2");

		return dslContext
				.select(SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION,
						SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS,
						MINOR_SUBJECT.YEAR_LEVEL.as("yearLevel"), MINOR_SUBJECT.SEM,
						SUBJECT2.SUBJECT_TITLE.as("preRequisite"))
				.from(MINOR_SUBJECT).innerJoin(SUBJECT).on(MINOR_SUBJECT.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE))
				.join(SUBJECT2).on(MINOR_SUBJECT.PRE_REQUISITES.eq(SUBJECT2.SUBJECT_CODE))
				.orderBy(MINOR_SUBJECT.YEAR_LEVEL, MINOR_SUBJECT.SEM).fetchMaps();
	}

	// ------------ FOR Student Enrollment
	public Map<String, Object> selectStudentEnrollmentData(Integer studentNo) {
		return dslContext
				.select(STUDENT_ENROLLMENT.ENROLLMENT_ID.as("enrollmentId"),
						STUDENT_ENROLLMENT.STUDENT_NO.as("studentNo"), USERS.FIRST_NAME.as("firstName"),
						USERS.MIDDLE_NAME.as("middleName"), USERS.LAST_NAME.as("lastName"),
						STUDENT.CURRICULUM_CODE.as("curriculumCode"), STUDENT.YEAR_LEVEL.as("yearLevel"),
						CURRICULUM.CURRICULUM_NAME.as("curriculumName"),
						ACADEMIC_YEAR.ACADEMIC_YEAR_.as("academicYear"), ACADEMIC_YEAR.SEMESTER)
				.from(STUDENT_ENROLLMENT)
				.innerJoin(ACADEMIC_YEAR).on(STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID.eq(ACADEMIC_YEAR.ACADEMIC_YEAR_ID))
				.innerJoin(STUDENT).on(STUDENT_ENROLLMENT.STUDENT_NO.eq(STUDENT.STUDENT_NO))
				.innerJoin(USERS).on(STUDENT.USER_ID.eq(USERS.USER_ID))
				.innerJoin(CURRICULUM).on(STUDENT.CURRICULUM_CODE.eq(CURRICULUM.CURRICULUM_CODE))
				.where(ACADEMIC_YEAR.STATUS.eq("Process").and(STUDENT.STUDENT_NO.eq(studentNo))).fetchOneMap();
	}

	// ------------ FOR Major Subject (for curriculum display)
	public List<Map<String, Object>> selectAllMajorSubjectsToEnrollPerYearAndSem(Integer yearLevel, Integer sem) {
		return dslContext
				.select(SUBJECT.SUBJECT_ID.as("subjectId"), SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION,
						SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS, SUBJECT.PRICE)
				.from(MAJOR_SUBJECT).innerJoin(SUBJECT).on(MAJOR_SUBJECT.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE))
				.where(MAJOR_SUBJECT.YEAR_LEVEL.eq(yearLevel).and(MAJOR_SUBJECT.SEM.eq(sem)))
				.orderBy(MAJOR_SUBJECT.YEAR_LEVEL, MAJOR_SUBJECT.SEM).fetchMaps();
	}

	// ------------ FOR Minor Subject (for curriculum display)
	public List<Map<String, Object>> selectAllMinorSubjectsToEnrollPerYearAndSem(Integer yearLevel, Integer sem) {
		return dslContext
				.select(SUBJECT.SUBJECT_ID.as("subjectId"), SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION,
						SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS, SUBJECT.PRICE)
				.from(MINOR_SUBJECT).innerJoin(SUBJECT).on(MINOR_SUBJECT.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE))
				.where(MINOR_SUBJECT.YEAR_LEVEL.eq(yearLevel).and(MINOR_SUBJECT.SEM.eq(sem)))
				.orderBy(MINOR_SUBJECT.YEAR_LEVEL, MINOR_SUBJECT.SEM).fetchMaps();
	}
	
	// ------------ FOR Submitted subjects for enrollment
	public boolean insertIntoSubmittedSubjectsForEnrollment(List<SubmittedSubjectsForEnrollmentRecord> submittedSubjectsForEnrollment) {
		 try {
			 dslContext.batchInsert(submittedSubjectsForEnrollment).execute();
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		 return true;
	}
	
	public List<SubmittedSubjectsForEnrollment>  checkIfThereIsSubmittedSubjectsForEnrollment(Integer enrollmentId) {
		return dslContext.selectFrom(SUBMITTED_SUBJECTS_FOR_ENROLLMENT)
				.where(SUBMITTED_SUBJECTS_FOR_ENROLLMENT.ENROLLMENT_ID.eq(enrollmentId))
				.fetchInto(SubmittedSubjectsForEnrollment.class);		 
	}
	
	public List<Map<String, Object>> selectAllEvaluationQuestionAnswerStudentNoAndSubjectCode(Integer studentNo, Integer subjectCode) {
		return dslContext
				.select(EVALUATION_QUESTION_ANSWER.EVALUATION_QUESTION_ANSWER_ID.as("evaluationQuestionAnswerId"),
						EVALUATION_QUESTION_ANSWER.EVALUATION_QUESTION_ID.as("evaluationQuestionId"),
						EVALUATION_QUESTION.QUESTION, EVALUATION_QUESTION_ANSWER.PROFESSOR_NO.as("professorNo"),
						EVALUATION_QUESTION_ANSWER.SUBJECT_CODE.as("subjectCode"),
						EVALUATION_QUESTION_ANSWER.ENROLLMENT_ID.as("enrollmentId"),
						EVALUATION_QUESTION_ANSWER.RATING)
				.from(EVALUATION_QUESTION_ANSWER)
				.innerJoin(EVALUATION_QUESTION).on(EVALUATION_QUESTION_ANSWER.EVALUATION_QUESTION_ID.eq(EVALUATION_QUESTION.EVALUATION_QUESTION_ID))
				.innerJoin(STUDENT_ENROLLMENT).on(EVALUATION_QUESTION_ANSWER.ENROLLMENT_ID.eq(STUDENT_ENROLLMENT.ENROLLMENT_ID))
				.where(STUDENT_ENROLLMENT.STUDENT_NO.eq(studentNo)
						.and(EVALUATION_QUESTION_ANSWER.SUBJECT_CODE.eq(subjectCode))
						.and(STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID.eq(dslContext.select(DSL.max(STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID)).from(STUDENT_ENROLLMENT)))
				)
				.orderBy(EVALUATION_QUESTION_ANSWER.EVALUATION_QUESTION_ID)
				.fetchMaps();
	}
	
	public boolean updateEvaluationQuestionAnswer(List<EvaluationQuestionAnswer> questionAnswers) {
		if (!questionAnswers.isEmpty()) {
			for (EvaluationQuestionAnswer answer : questionAnswers) {
				dslContext.update(EVALUATION_QUESTION_ANSWER)
				  .set(EVALUATION_QUESTION_ANSWER.RATING, answer.getRating())
				  .where(EVALUATION_QUESTION_ANSWER.EVALUATION_QUESTION_ANSWER_ID.eq(answer.getEvaluationQuestionAnswerId()))
				  .execute();			
			}
			return true;
		}
		return false;
	}
	
	// ------------ FOR WEBSITE ACTIVATION TOGGLE
	public WebsiteActivationToggle selectWebsiteActivationToggle() {
		return dslContext.selectFrom(WEBSITE_ACTIVATION_TOGGLE).fetchOneInto(WebsiteActivationToggle.class);
	}

}
