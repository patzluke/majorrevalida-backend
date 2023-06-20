package org.ssglobal.training.codes.repository;

import java.time.LocalDate;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Row6;
import org.jooq.Row9;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ssglobal.training.codes.tables.pojos.Professor;
import org.ssglobal.training.codes.tables.pojos.Subject;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
public class ProfessorRepository {

	private final org.ssglobal.training.codes.tables.Professor PROFESSOR = org.ssglobal.training.codes.tables.Professor.PROFESSOR;

	@Autowired
	private final DSLContext dslContext;
	
	public List<Professor> selectAllProfessor() {
		return dslContext.selectFrom(PROFESSOR)
				.fetchInto(Professor.class);
	}
	
	public Row9<Integer, Integer, String, String, String, String, String, LocalDate, Boolean> insertProfessor(Professor professor) {
		return dslContext.insertInto(PROFESSOR)
					  	 .set(PROFESSOR.PROFESSOR_NAME, professor.getProfessorName())
					  	 .set(PROFESSOR.PASSWORD, professor.getPassword())
					  	 .set(PROFESSOR.WORK, professor.getWork())
					  	 .set(PROFESSOR.GENDER, professor.getGender())
					  	 .set(PROFESSOR.STATUS, professor.getStatus())
					  	 .set(PROFESSOR.BIRTH_DATE, professor.getBirthDate())
					  	 .set(PROFESSOR.ACTIVE_DEACTIVE, professor.getActiveDeactive())
					  	 .returning()
					  	 .fetchOne()
					  	 .valuesRow();
	}
	
	public Row9<Integer, Integer, String, String, String, String, String, LocalDate, Boolean> updateProfessor(Professor professor) {
		return dslContext.update(PROFESSOR)
					  	 .set(PROFESSOR.PROFESSOR_NAME, professor.getProfessorName())
					  	 .set(PROFESSOR.PASSWORD, professor.getPassword())
					  	 .set(PROFESSOR.WORK, professor.getWork())
					  	 .set(PROFESSOR.GENDER, professor.getGender())
					  	 .set(PROFESSOR.STATUS, professor.getStatus())
					  	 .set(PROFESSOR.BIRTH_DATE, professor.getBirthDate())
					  	 .set(PROFESSOR.ACTIVE_DEACTIVE, professor.getActiveDeactive())
					  	 .where(PROFESSOR.PROFESSOR_ID.eq(professor.getProfessorId()))
					  	 .returning()
					  	 .fetchOne()
					  	 .valuesRow();
	}
	
	public Row9<Integer, Integer, String, String, String, String, String, LocalDate, Boolean> deleteProfessorById(Integer programId) {
		return dslContext.deleteFrom(PROFESSOR)
						 .where(PROFESSOR.PROFESSOR_ID.eq(programId))
					  	 .returning()
					  	 .fetchOne()
					  	 .valuesRow();
	}
}
