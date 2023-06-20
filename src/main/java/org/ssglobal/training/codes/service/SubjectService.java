package org.ssglobal.training.codes.service;

import java.util.List;

import org.jooq.Row6;
import org.ssglobal.training.codes.tables.pojos.Subject;

public interface SubjectService {
	
	List<Subject> selectAllSubject();
	Row6<Integer, Integer, String, Integer, String, Boolean> insertSubject(Subject subject);
	Row6<Integer, Integer, String, Integer, String, Boolean> updateSubject(Subject subject);
	Row6<Integer, Integer, String, Integer, String, Boolean> deleteSubjectById(Integer programId);
}
