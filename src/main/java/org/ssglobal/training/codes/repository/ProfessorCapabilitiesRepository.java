package org.ssglobal.training.codes.repository;

import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.tables.pojos.Professor;
import org.ssglobal.training.codes.tables.pojos.ProfessorLoad;
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
	private final org.ssglobal.training.codes.tables.StudentAttendance STUDENT_ATTENDANCE = org.ssglobal.training.codes.tables.StudentAttendance.STUDENT_ATTENDANCE;
	
	public UserAndProfessor selectProfessor(Integer professorNo) {
		return dslContext
				.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.EMAIL, USERS.CONTACT_NO, USERS.FIRST_NAME,
						USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE, USERS.ADDRESS,
						USERS.CIVIL_STATUS, USERS.GENDER, USERS.NATIONALITY, USERS.ACTIVE_DEACTIVE, USERS.IMAGE,
						PROFESSOR.PROFESSOR_NO, PROFESSOR.WORK)
				.from(USERS).innerJoin(PROFESSOR).on(USERS.USER_ID.eq(PROFESSOR.USER_ID))
				.where(PROFESSOR.PROFESSOR_NO.eq(professorNo)).fetchOneInto(UserAndProfessor.class);
	}

	public UserAndProfessor updateProfessor(UserAndProfessor userAndProfessor) {
		Users updatedUser = dslContext.update(USERS).set(USERS.USERNAME, userAndProfessor.getUsername())
				.set(USERS.PASSWORD, userAndProfessor.getPassword()).set(USERS.EMAIL, userAndProfessor.getEmail())
				.set(USERS.CONTACT_NO, userAndProfessor.getContactNo()).set(USERS.FIRST_NAME, userAndProfessor.getFirstName())
				.set(USERS.MIDDLE_NAME, userAndProfessor.getMiddleName()).set(USERS.LAST_NAME, userAndProfessor.getLastName())
				.set(USERS.USER_TYPE, userAndProfessor.getUserType()).set(USERS.BIRTH_DATE, userAndProfessor.getBirthDate())
				.set(USERS.ADDRESS, userAndProfessor.getAddress()).set(USERS.CIVIL_STATUS, userAndProfessor.getCivilStatus())
				.set(USERS.GENDER, userAndProfessor.getGender()).set(USERS.NATIONALITY, userAndProfessor.getNationality())
				.set(USERS.ACTIVE_DEACTIVE, userAndProfessor.getActiveDeactive()).set(USERS.IMAGE, userAndProfessor.getImage())
				.where(USERS.USER_ID.eq(userAndProfessor.getUserId()))
				.returning().fetchOne().into(Users.class);
		
		Professor updatedProfessor = dslContext.update(PROFESSOR)
											.set(PROFESSOR.WORK, userAndProfessor.getWork())
											.where(PROFESSOR.PROFESSOR_NO.eq(userAndProfessor.getProfessorNo()))
											.returning()
											.fetchOne().into(Professor.class);

		if (updatedUser != null && updatedProfessor != null) {
			UserAndProfessor newuserAndProfessor = new UserAndProfessor(updatedUser.getUserId(), updatedUser.getUsername(),
					updatedUser.getPassword(), updatedUser.getEmail(), updatedUser.getContactNo(),
					updatedUser.getFirstName(), updatedUser.getMiddleName(), updatedUser.getLastName(),
					updatedUser.getUserType(), updatedUser.getBirthDate(), updatedUser.getAddress(),
					updatedUser.getCivilStatus(), updatedUser.getGender(), updatedUser.getNationality(),
					updatedUser.getActiveDeactive(), updatedUser.getImage(), updatedProfessor.getProfessorId(),
					updatedProfessor.getProfessorNo(), updatedProfessor.getWork());
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
		return dslContext.select(PROFESSOR_LOAD.LOAD_ID, PROFESSOR_LOAD.PROFESSOR_NO, PROFESSOR_LOAD.SUBJECT_CODE, PROFESSOR_LOAD.SECTION_ID, PROFESSOR_LOAD.ROOM_ID, PROFESSOR_LOAD.DEPT_CODE, PROFESSOR_LOAD.DAY,
				PROFESSOR_LOAD.START_TIME, PROFESSOR_LOAD.END_TIME, SUBJECT.SUBJECT_CODE, SUBJECT.ABBREVATION, SUBJECT.SUBJECT_TITLE, SUBJECT.UNITS, SUBJECT.ACTIVE_DEACTIVE)
				.from(PROFESSOR_LOAD).innerJoin(SUBJECT).on(PROFESSOR_LOAD.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE)).where(PROFESSOR_LOAD.PROFESSOR_NO.eq(professorNo)).fetchMaps();
	}
	
//	List of Students Attendance in that particular Professor's Load
	public List<Map<String, Object>> selectAllAttendance(Integer loadId) {
		return dslContext.select(STUDENT_ATTENDANCE.STUDENT_ATTENDANCE_ID, STUDENT_ATTENDANCE.STUDENT_NO, STUDENT_ATTENDANCE.LOAD_ID, STUDENT_ATTENDANCE.ATTENDANCE_DATE, STUDENT_ATTENDANCE.STATUS)
				.from(STUDENT_ATTENDANCE).innerJoin(STUDENT).on(STUDENT_ATTENDANCE.STUDENT_NO.eq(STUDENT.STUDENT_NO))
				.where(STUDENT_ATTENDANCE.LOAD_ID.eq(loadId)).fetchMaps();
	}

}
