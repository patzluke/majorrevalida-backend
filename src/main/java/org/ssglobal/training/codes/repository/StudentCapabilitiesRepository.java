package org.ssglobal.training.codes.repository;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ssglobal.training.codes.tables.pojos.Student;
import org.ssglobal.training.codes.tables.pojos.Users;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
public class StudentCapabilitiesRepository {

	private final org.ssglobal.training.codes.tables.Student STUDENT = org.ssglobal.training.codes.tables.Student.STUDENT;
	private final org.ssglobal.training.codes.tables.Users USERS = org.ssglobal.training.codes.tables.Users.USERS;
	private final org.ssglobal.training.codes.tables.Curriculum CURRICULUM = org.ssglobal.training.codes.tables.Curriculum.CURRICULUM;
	private final org.ssglobal.training.codes.tables.AcademicYear ACADEMIC_YEAR = org.ssglobal.training.codes.tables.AcademicYear.ACADEMIC_YEAR;

	@Autowired
	private final DSLContext dslContext;

	// Return all the student's data
	public List<Student> selectAllStudent() {
		List<Student> students = dslContext.selectFrom(STUDENT).fetchInto(Student.class);
		return students;
	}
	
	

}
