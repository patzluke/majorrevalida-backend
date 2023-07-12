package org.ssglobal.training.codes.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.tables.pojos.Professor;
import org.ssglobal.training.codes.tables.pojos.ProfessorLoad;
import org.ssglobal.training.codes.tables.pojos.StudentAttendance;
import org.ssglobal.training.codes.tables.pojos.Users;

@Repository
public class ProfessorCapabilitiesRepository {

	@Autowired
	private DSLContext dslContext;

	private final org.ssglobal.training.codes.tables.Users USERS = org.ssglobal.training.codes.tables.Users.USERS;
	private final org.ssglobal.training.codes.tables.Professor PROFESSOR = org.ssglobal.training.codes.tables.Professor.PROFESSOR;
	private final org.ssglobal.training.codes.tables.ProfessorLoad PROFESSOR_LOAD = org.ssglobal.training.codes.tables.ProfessorLoad.PROFESSOR_LOAD;
	private final org.ssglobal.training.codes.tables.Subject SUBJECT = org.ssglobal.training.codes.tables.Subject.SUBJECT;
	private final org.ssglobal.training.codes.tables.Student STUDENT = org.ssglobal.training.codes.tables.Student.STUDENT;
	private final org.ssglobal.training.codes.tables.StudentEnrollment STUDENT_ENROLLMENT = org.ssglobal.training.codes.tables.StudentEnrollment.STUDENT_ENROLLMENT;
	private final org.ssglobal.training.codes.tables.StudentAttendance STUDENT_ATTENDANCE = org.ssglobal.training.codes.tables.StudentAttendance.STUDENT_ATTENDANCE;
	private final org.ssglobal.training.codes.tables.TSubjectDetailHistory T_SUBJECT_DETAIL_HISTORY = org.ssglobal.training.codes.tables.TSubjectDetailHistory.T_SUBJECT_DETAIL_HISTORY;
	private final org.ssglobal.training.codes.tables.Grades GRADES = org.ssglobal.training.codes.tables.Grades.GRADES;
	private final org.ssglobal.training.codes.tables.Section SECTION = org.ssglobal.training.codes.tables.Section.SECTION;
	private final org.ssglobal.training.codes.tables.Room ROOM = org.ssglobal.training.codes.tables.Room.ROOM;
	private final org.ssglobal.training.codes.tables.Department DEPARTMENT = org.ssglobal.training.codes.tables.Department.DEPARTMENT;

	public List<Users> selectAllUsers() {
		return dslContext.selectFrom(USERS).fetchInto(Users.class);
	}
	
	public UserAndProfessor selectProfessor(Integer professorNo) {
		return dslContext
				.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.EMAIL, USERS.CONTACT_NO, USERS.FIRST_NAME,
						USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE, USERS.ADDRESS,
						USERS.CIVIL_STATUS, USERS.GENDER, USERS.NATIONALITY, USERS.ACTIVE_STATUS, USERS.ACTIVE_DEACTIVE, 
						USERS.IMAGE, PROFESSOR.PROFESSOR_ID, PROFESSOR.PROFESSOR_NO, PROFESSOR.WORK)
				.from(USERS).innerJoin(PROFESSOR).on(USERS.USER_ID.eq(PROFESSOR.USER_ID))
				.where(USERS.ACTIVE_DEACTIVE.eq(true).and(PROFESSOR.PROFESSOR_NO.eq(professorNo)))
				.fetchOneInto(UserAndProfessor.class);
	}

	public UserAndProfessor updateProfessor(UserAndProfessor userAndProfessor) {
		Users updatedUser = dslContext.update(USERS)
				.set(USERS.USERNAME, userAndProfessor.getUsername()).set(USERS.EMAIL, userAndProfessor.getEmail())
				.set(USERS.CONTACT_NO, userAndProfessor.getContactNo()).set(USERS.FIRST_NAME, userAndProfessor.getFirstName())
				.set(USERS.MIDDLE_NAME, userAndProfessor.getMiddleName()).set(USERS.LAST_NAME, userAndProfessor.getLastName())
				.set(USERS.USER_TYPE, userAndProfessor.getUserType()).set(USERS.BIRTH_DATE, userAndProfessor.getBirthDate())
				.set(USERS.ADDRESS, userAndProfessor.getAddress()).set(USERS.CIVIL_STATUS, userAndProfessor.getCivilStatus())
				.set(USERS.GENDER, userAndProfessor.getGender()).set(USERS.NATIONALITY, userAndProfessor.getNationality())
				.set(USERS.IMAGE, userAndProfessor.getImage()).where(USERS.USER_ID.eq(userAndProfessor.getUserId()))
				.returning().fetchOne().into(Users.class);
		
		if (!userAndProfessor.getPassword().isBlank()) {
			dslContext.update(USERS).set(USERS.PASSWORD, userAndProfessor.getPassword())
			.where(USERS.USER_ID.eq(userAndProfessor.getUserId()))
			.execute();
		}
		
		Professor updatedProfessor = dslContext.update(PROFESSOR)
											.set(PROFESSOR.WORK, userAndProfessor.getWork())
											.where(PROFESSOR.PROFESSOR_NO.eq(userAndProfessor.getProfessorNo()))
											.returning()
											.fetchOne().into(Professor.class);

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
	
//	Select All loads in ProfessorLoad table
	public List<ProfessorLoad> selectAllLoad(Integer professorNo) {
		return dslContext.selectFrom(PROFESSOR_LOAD).where(PROFESSOR_LOAD.PROFESSOR_NO.eq(professorNo)).fetchInto(ProfessorLoad.class);
	}

//	Select All loads in ProfessorLoad with inner join of it's subject table
	public List<Map<String, Object>> selectAllLoads(Integer professorNo) {
		return dslContext
		.select(PROFESSOR_LOAD.LOAD_ID.as("loadId"), PROFESSOR_LOAD.PROFESSOR_NO.as("professorNo"),
				SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION, SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS,
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
		.where(PROFESSOR_LOAD.PROFESSOR_NO.eq(professorNo))
		.fetchMaps();
	}
	
	public List<Map<String, Object>> selectProfessorLoadByProfessorNoAndSubjectCodeAndSection(Integer professorNo, Integer subjectCode, String sectionName) {
		return dslContext
		.select(PROFESSOR_LOAD.LOAD_ID.as("loadId"), PROFESSOR_LOAD.PROFESSOR_NO.as("professorNo"),
				SUBJECT.SUBJECT_CODE.as("subjectCode"), SUBJECT.ABBREVATION, SUBJECT.SUBJECT_TITLE.as("subjectTitle"), SUBJECT.UNITS,
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
		.where(PROFESSOR_LOAD.PROFESSOR_NO.eq(professorNo)
			   .and(PROFESSOR_LOAD.SUBJECT_CODE.eq(subjectCode)
			   .and(SECTION.SECTION_NAME.eq(sectionName))))
		.fetchMaps();
	}
	
//	List of Students Attendance in that particular Professor's Load
	public List<Map<String, Object>> selectAllAttendance(Integer loadId) {
		return dslContext.select(STUDENT_ATTENDANCE.STUDENT_ATTENDANCE_ID, STUDENT_ATTENDANCE.STUDENT_NO, STUDENT_ATTENDANCE.LOAD_ID, 
								 STUDENT_ATTENDANCE.ATTENDANCE_DATE, STUDENT_ATTENDANCE.STATUS)
				.from(STUDENT_ATTENDANCE).innerJoin(STUDENT).on(STUDENT_ATTENDANCE.STUDENT_NO.eq(STUDENT.STUDENT_NO))
				.where(STUDENT_ATTENDANCE.LOAD_ID.eq(loadId)).fetchMaps();
	}
	
//	List of students that is enrolled in professor's load subject
	public List<Map<String, Object>> selectAllStudentsBySection() {
		return dslContext
				.select(GRADES.GRADE_ID.as("gradeId"), GRADES.STUDENT_NO.as("studentNo"),
						USERS.FIRST_NAME.as("firstName"), USERS.MIDDLE_NAME.as("middleName"),
						USERS.LAST_NAME.as("lastName"), USERS.EMAIL, GRADES.PRELIM_GRADE.as("prelimGrade"),
						GRADES.FINALS_GRADE.as("finalGrade"), GRADES.COMMENT, GRADES.REMARKS,
						SECTION.SECTION_NAME.as("sectionName"), T_SUBJECT_DETAIL_HISTORY.SUBJECT_CODE.as("subjectCode"),
						SUBJECT.SUBJECT_TITLE.as("subjectTitle"))
				.from(GRADES)
				.innerJoin(STUDENT).on(GRADES.STUDENT_NO.eq(STUDENT.STUDENT_NO))
				.innerJoin(USERS).on(STUDENT.USER_ID.eq(USERS.USER_ID))
				.innerJoin(STUDENT_ENROLLMENT).on(STUDENT.STUDENT_NO.eq(STUDENT_ENROLLMENT.STUDENT_NO))
				.innerJoin(SECTION).on(STUDENT_ENROLLMENT.SECTION_ID.eq(SECTION.SECTION_ID))
				.innerJoin(T_SUBJECT_DETAIL_HISTORY).on(GRADES.SUBJECT_DETAIL_HIS_ID.eq(T_SUBJECT_DETAIL_HISTORY.SUBJECT_DETAIL_HIS_ID))
				.innerJoin(SUBJECT).on(T_SUBJECT_DETAIL_HISTORY.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE))
				.fetchMaps();
	}

//	List of students attendance
	public List<Map<String, Object>> selectStudentAttendanceByAndSubjectAndSectionAndProfessorNoAndDate(
			String subjectTitle, String sectionName, Integer professorNo, String date) {
		
		return dslContext.select(STUDENT_ATTENDANCE.STUDENT_ATTENDANCE_ID.as("studentAttendanceId"), USERS.LAST_NAME.as("lastName"), 
								 USERS.FIRST_NAME.as("firstName"), USERS.MIDDLE_NAME.as("middleName"), STUDENT_ATTENDANCE.STATUS, 
								 STUDENT_ATTENDANCE.ATTENDANCE_DATE.as("attendanceDate"))
						 .from(STUDENT_ATTENDANCE)
						 .innerJoin(STUDENT).on(STUDENT_ATTENDANCE.STUDENT_NO.eq(STUDENT.STUDENT_NO))
						 .innerJoin(USERS).on(STUDENT.USER_ID.eq(USERS.USER_ID))
						 .innerJoin(PROFESSOR_LOAD).on(STUDENT_ATTENDANCE.LOAD_ID.eq(PROFESSOR_LOAD.LOAD_ID))
						 .innerJoin(SECTION).on(PROFESSOR_LOAD.SECTION_ID.eq(SECTION.SECTION_ID))
						 .innerJoin(SUBJECT).on(PROFESSOR_LOAD.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE))			 
						 .where(SUBJECT.SUBJECT_TITLE.eq(subjectTitle)
						 .and(SECTION.SECTION_NAME.eq(sectionName))
						 .and(PROFESSOR_LOAD.PROFESSOR_NO.eq(professorNo))
						 .and(STUDENT_ATTENDANCE.ATTENDANCE_DATE.eq(LocalDate.parse(date))))
						 .orderBy(STUDENT_ATTENDANCE.STUDENT_ATTENDANCE_ID)
						 .fetchMaps();
	}
	
	public Map<String, Object> updateStudentAttendance(
			String subjectTitle, String sectionName, Integer professorNo, String date, String status, Integer studentAttendanceId) {
		
		StudentAttendance updatedStudentAttendance = dslContext.update(STUDENT_ATTENDANCE)
				  .set(STUDENT_ATTENDANCE.STATUS, status)
				  .where(STUDENT_ATTENDANCE.STUDENT_ATTENDANCE_ID.eq(studentAttendanceId))
				  .returning()
				  .fetchOne()
				  .into(StudentAttendance.class);
		
		return dslContext.select(STUDENT_ATTENDANCE.STUDENT_ATTENDANCE_ID.as("studentAttendanceId"), USERS.LAST_NAME.as("lastName"), 
								 USERS.FIRST_NAME.as("firstName"), USERS.MIDDLE_NAME.as("middleName"), STUDENT_ATTENDANCE.STATUS, 
								 STUDENT_ATTENDANCE.ATTENDANCE_DATE.as("attendanceDate"))
						 .from(STUDENT_ATTENDANCE)
						 .innerJoin(STUDENT).on(STUDENT_ATTENDANCE.STUDENT_NO.eq(STUDENT.STUDENT_NO))
						 .innerJoin(USERS).on(STUDENT.USER_ID.eq(USERS.USER_ID))
						 .innerJoin(PROFESSOR_LOAD).on(STUDENT_ATTENDANCE.LOAD_ID.eq(PROFESSOR_LOAD.LOAD_ID))
						 .innerJoin(SECTION).on(PROFESSOR_LOAD.SECTION_ID.eq(SECTION.SECTION_ID))
						 .innerJoin(SUBJECT).on(PROFESSOR_LOAD.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE))			 
						 .where(STUDENT_ATTENDANCE.STUDENT_ATTENDANCE_ID.eq(updatedStudentAttendance.getStudentAttendanceId()))
						 .fetchOneMap();
	}
	
//	List of students attendance
	public List<StudentAttendance> selectStudentAttendanceByAttendanceDateDistinct() {
		return dslContext.selectDistinct(STUDENT_ATTENDANCE.ATTENDANCE_DATE)
						 .from(STUDENT_ATTENDANCE)
						 .orderBy(STUDENT_ATTENDANCE.ATTENDANCE_DATE)
						 .fetchInto(StudentAttendance.class);
	}
	
}
