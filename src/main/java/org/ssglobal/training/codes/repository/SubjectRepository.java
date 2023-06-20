package org.ssglobal.training.codes.repository;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Row6;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ssglobal.training.codes.tables.pojos.Subject;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
public class SubjectRepository {

	private final org.ssglobal.training.codes.tables.Subject SUBJECT = org.ssglobal.training.codes.tables.Subject.SUBJECT;

	@Autowired
	private final DSLContext dslContext;
	
	public List<Subject> selectAllSubject() {
		return dslContext.selectFrom(SUBJECT)
				.fetchInto(Subject.class);
	}
	
	public Row6<Integer, Integer, String, Integer, String, Boolean> insertSubject(Subject subject) {
		return dslContext.insertInto(SUBJECT)
					  	 .set(SUBJECT.SUBJECT_TITLE, subject.getSubjectTitle())
					  	 .set(SUBJECT.UNITS, subject.getUnits())
					  	 .set(SUBJECT.SUBJECT_TITLE, subject.getSubjectTitle())
					  	 .set(SUBJECT.UNITS, subject.getUnits())
					  	 .set(SUBJECT.PRE_REQUISITES, subject.getPreRequisites())
					  	 .set(SUBJECT.ACTIVE_DEACTIVE, subject.getActiveDeactive())
					  	 .returning()
					  	 .fetchOne()
					  	 .valuesRow();
	}
	
	public Row6<Integer, Integer, String, Integer, String, Boolean> updateSubject(Subject subject) {
		return dslContext.update(SUBJECT)
					  	 .set(SUBJECT.SUBJECT_TITLE, subject.getSubjectTitle())
					  	 .set(SUBJECT.UNITS, subject.getUnits())
					  	 .set(SUBJECT.SUBJECT_TITLE, subject.getSubjectTitle())
					  	 .set(SUBJECT.UNITS, subject.getUnits())
					  	 .set(SUBJECT.PRE_REQUISITES, subject.getPreRequisites())
					  	 .set(SUBJECT.ACTIVE_DEACTIVE, subject.getActiveDeactive())
					  	 .where(SUBJECT.SUBJECT_ID.eq(subject.getSubjectId()))
					  	 .returning()
					  	 .fetchOne()
					  	 .valuesRow();
	}
	
	public Row6<Integer, Integer, String, Integer, String, Boolean> deleteProgramById(Integer programId) {
		return dslContext.deleteFrom(SUBJECT)
			  	 .where(SUBJECT.SUBJECT_ID.eq(programId))
			  	 .returning()
			  	 .fetchOne()
			  	 .valuesRow();
	}
}
