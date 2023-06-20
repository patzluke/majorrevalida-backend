package org.ssglobal.training.codes.repository;

import java.time.LocalDate;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Row15;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ssglobal.training.codes.tables.pojos.Student;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
public class StudentRepository {

	private final org.ssglobal.training.codes.tables.Student STUDENT = org.ssglobal.training.codes.tables.Student.STUDENT;

	@Autowired
	private final DSLContext dslContext;

	public List<Student> selectAllAdmin() {
		return dslContext.selectFrom(STUDENT).fetchInto(Student.class);
	}

	public Row15<Integer, Integer, Integer, String, String, String, String, LocalDate, Integer, Integer, String, String, Boolean, String, String> insertStudent(
			Student student) {
		return dslContext.insertInto(STUDENT)
				.set(STUDENT.PROGRAM_ID, student.getProgramId())
				.set(STUDENT.PASSWORD, student.getPassword())
				.set(STUDENT.FIRST_NAME, student.getFirstName())
				.set(STUDENT.MIDDLE_NAME, student.getMiddleName())
				.set(STUDENT.LAST_NAME, student.getLastName())
				.set(STUDENT.BIRTH_DATE, student.getBirthDate())
				.set(STUDENT.SEM, student.getSem())
				.set(STUDENT.YEAR_LEVEL, student.getYearLevel())
				.set(STUDENT.ACADEMIC_YEAR, student.getAcademicYear())
				.set(STUDENT.STATUS, student.getStatus())
				.set(STUDENT.ACTIVE_DEACTIVE, student.getActiveDeactive())
				.set(STUDENT.COURSE, student.getCourse())
				.set(STUDENT.IMAGE, student.getImage())
				.returning()
				.fetchOne()
				.valuesRow();
	}

	public Row15<Integer, Integer, Integer, String, String, String, String, LocalDate, Integer, Integer, String, String, Boolean, String, String> updateStudent(
			Student student) {
		return dslContext.update(STUDENT).set(STUDENT.PASSWORD, student.getPassword())
				.set(STUDENT.FIRST_NAME, student.getFirstName()).set(STUDENT.MIDDLE_NAME, student.getMiddleName())
				.set(STUDENT.LAST_NAME, student.getLastName()).set(STUDENT.BIRTH_DATE, student.getBirthDate())
				.set(STUDENT.SEM, student.getSem()).set(STUDENT.YEAR_LEVEL, student.getYearLevel())
				.set(STUDENT.ACADEMIC_YEAR, student.getAcademicYear()).set(STUDENT.STATUS, student.getStatus())
				.set(STUDENT.ACTIVE_DEACTIVE, student.getActiveDeactive()).set(STUDENT.COURSE, student.getCourse())
				.set(STUDENT.IMAGE, student.getImage())
				.returning(STUDENT.STUDENT_ID, STUDENT.STUDENT_NO, STUDENT.PROGRAM_ID, STUDENT.PASSWORD,
						STUDENT.FIRST_NAME, STUDENT.MIDDLE_NAME, STUDENT.LAST_NAME, STUDENT.BIRTH_DATE, STUDENT.SEM,
						STUDENT.YEAR_LEVEL, STUDENT.ACADEMIC_YEAR, STUDENT.STATUS, STUDENT.ACTIVE_DEACTIVE,
						STUDENT.COURSE, STUDENT.IMAGE)
				.fetchOne().valuesRow();
	}

	public Row15<Integer, Integer, Integer, String, String, String, String, LocalDate, Integer, Integer, String, String, Boolean, String, String> deleteStudent(
			Integer studentId) {
		return dslContext.deleteFrom(STUDENT).where(STUDENT.STUDENT_ID.eq(studentId))
				.returning(STUDENT.STUDENT_ID, STUDENT.STUDENT_NO, STUDENT.PROGRAM_ID, STUDENT.PASSWORD,
						STUDENT.FIRST_NAME, STUDENT.MIDDLE_NAME, STUDENT.LAST_NAME, STUDENT.BIRTH_DATE, STUDENT.SEM,
						STUDENT.YEAR_LEVEL, STUDENT.ACADEMIC_YEAR, STUDENT.STATUS, STUDENT.ACTIVE_DEACTIVE,
						STUDENT.COURSE, STUDENT.IMAGE)
				.fetchOne().valuesRow();
	}
	
	
	
	

}
