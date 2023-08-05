package org.ssglobal.training.codes.repository;

import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.model.ParentStudentAttendanceCopy;
import org.ssglobal.training.codes.model.StudentGrades;
import org.ssglobal.training.codes.model.UserAndParent;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.AcademicYear;
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
	private final org.ssglobal.training.codes.tables.StudentEnrollment STUDENT_ENROLLMENT = org.ssglobal.training.codes.tables.StudentEnrollment.STUDENT_ENROLLMENT;
	private final org.ssglobal.training.codes.tables.AcademicYear ACADEMIC_YEAR = org.ssglobal.training.codes.tables.AcademicYear.ACADEMIC_YEAR;
	private final org.ssglobal.training.codes.tables.Subject SUBJECT = org.ssglobal.training.codes.tables.Subject.SUBJECT;
	private final org.ssglobal.training.codes.tables.TSubjectDetailHistory T_SUBJECT_DETAIL_HISTORY = org.ssglobal.training.codes.tables.TSubjectDetailHistory.T_SUBJECT_DETAIL_HISTORY;
	private final org.ssglobal.training.codes.tables.StudentAttendance STUDENT_ATTENDANCE = org.ssglobal.training.codes.tables.StudentAttendance.STUDENT_ATTENDANCE;
	private final org.ssglobal.training.codes.tables.ProfessorLoad PROFESSOR_LOAD = org.ssglobal.training.codes.tables.ProfessorLoad.PROFESSOR_LOAD;

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

		Parent updatedParent = dslContext.selectFrom(PARENT).where(PARENT.USER_ID.eq(updatedUser.getUserId()))
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

	public List<StudentGrades> selectAllGrades(Integer studentNo) {
		return dslContext
				.select(GRADES.GRADE_ID, GRADES.STUDENT_NO, GRADES.SUBJECT_DETAIL_HIS_ID, GRADES.ENROLL_SUBJECT_ID,
						GRADES.PRELIM_GRADE, GRADES.FINALS_GRADE, GRADES.TOTAL_GRADE, GRADES.TOTAL_GRADE,
						GRADES.COMMENT, GRADES.DATE_PRELIM_GRADE_INSERTED, GRADES.DATE_FINALS_GRADE_INSERTED,
						GRADES.DATE_PRELIM_GRADE_MODIFIED, GRADES.DATE_FINALS_GRADE_MODIFIED, GRADES.REMARKS,
						GRADES.IS_SUBMITTED, SUBJECT.ABBREVATION, SUBJECT.SUBJECT_TITLE, SUBJECT.UNITS, 
						ACADEMIC_YEAR.ACADEMIC_YEAR_, ACADEMIC_YEAR.SEMESTER)
				.from(GRADES).innerJoin(T_SUBJECT_DETAIL_HISTORY)
				.on(GRADES.SUBJECT_DETAIL_HIS_ID.eq(T_SUBJECT_DETAIL_HISTORY.SUBJECT_DETAIL_HIS_ID)).innerJoin(SUBJECT)
				.on(T_SUBJECT_DETAIL_HISTORY.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE))
				.join(ACADEMIC_YEAR).on(ACADEMIC_YEAR.ACADEMIC_YEAR_ID.eq(T_SUBJECT_DETAIL_HISTORY.ACADEMIC_YEAR_ID))
				.where(GRADES.STUDENT_NO.eq(studentNo)).fetchInto(StudentGrades.class);
	}

	public List<Map<String, Object>> selectEnrolledSchoolYearOfStudent(Integer studentNo) {
		return dslContext
				.select(STUDENT_ENROLLMENT.ENROLLMENT_ID.as("enrollmentId"),
						ACADEMIC_YEAR.ACADEMIC_YEAR_.as("academicYear"), ACADEMIC_YEAR.SEMESTER,
						STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID.as("academicYearId"))
				.from(STUDENT_ENROLLMENT).innerJoin(ACADEMIC_YEAR)
				.on(STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID.eq(ACADEMIC_YEAR.ACADEMIC_YEAR_ID))
				.where(STUDENT_ENROLLMENT.STUDENT_NO.eq(studentNo).and(STUDENT_ENROLLMENT.STATUS.eq("Enrolled")))
				.orderBy(ACADEMIC_YEAR.ACADEMIC_YEAR_, ACADEMIC_YEAR.SEMESTER).fetchMaps();
	}

	public List<ParentStudentAttendanceCopy> selectStudentAttendanceByAttendanceDateDistinct(Integer studentNo) {
		AcademicYear academicYear = dslContext.select(ACADEMIC_YEAR.START_DATE, ACADEMIC_YEAR.END_DATE)
				.from(STUDENT_ENROLLMENT).innerJoin(ACADEMIC_YEAR)
				.on(STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID.eq(ACADEMIC_YEAR.ACADEMIC_YEAR_ID))
				.where(STUDENT_ENROLLMENT.STUDENT_NO.eq(studentNo)
						.and(STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID.eq(dslContext
								.select(DSL.max(STUDENT_ENROLLMENT.ACADEMIC_YEAR_ID)).from(STUDENT_ENROLLMENT))))
				.fetchOneInto(AcademicYear.class);

		return dslContext
				.selectDistinct(STUDENT_ATTENDANCE.ATTENDANCE_DATE, STUDENT_ATTENDANCE.STUDENT_ATTENDANCE_ID,
						STUDENT_ATTENDANCE.STUDENT_NO, STUDENT_ATTENDANCE.LOAD_ID, STUDENT_ATTENDANCE.STATUS,
						PROFESSOR_LOAD.PROFESSOR_NO, PROFESSOR_LOAD.SUBJECT_CODE, PROFESSOR_LOAD.SECTION_ID,
						PROFESSOR_LOAD.ROOM_ID, PROFESSOR_LOAD.DEPT_CODE, PROFESSOR_LOAD.DAY, PROFESSOR_LOAD.START_TIME,
						PROFESSOR_LOAD.END_TIME, PROFESSOR_LOAD.ACTIVE_DEACTIVE, SUBJECT.SUBJECT_ID,
						SUBJECT.ABBREVATION, SUBJECT.SUBJECT_TITLE, SUBJECT.UNITS, SUBJECT.PRICE)
				.from(STUDENT_ATTENDANCE).rightJoin(STUDENT_ENROLLMENT)
				.on(STUDENT_ATTENDANCE.STUDENT_NO.eq(STUDENT_ENROLLMENT.STUDENT_NO)).innerJoin(PROFESSOR_LOAD)
				.on(STUDENT_ATTENDANCE.LOAD_ID.eq(PROFESSOR_LOAD.LOAD_ID)).innerJoin(SUBJECT)
				.on(PROFESSOR_LOAD.SUBJECT_CODE.eq(SUBJECT.SUBJECT_CODE))
				.where(STUDENT_ATTENDANCE.ATTENDANCE_DATE
						.between(academicYear.getStartDate(), academicYear.getEndDate())
						.and(STUDENT_ATTENDANCE.STUDENT_NO.eq(studentNo)))
				.orderBy(STUDENT_ATTENDANCE.ATTENDANCE_DATE).fetchInto(ParentStudentAttendanceCopy.class);

	}

}
