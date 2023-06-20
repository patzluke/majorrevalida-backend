package org.ssglobal.training.codes.service.Impl;

import java.util.List;

import org.jooq.Row6;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repository.SubjectRepository;
import org.ssglobal.training.codes.service.SubjectService;
import org.ssglobal.training.codes.tables.pojos.Subject;

@Service
public class SubjectServiceImpl implements SubjectService {
	
	@Autowired
	private SubjectRepository repository;

	@Override
	public List<Subject> selectAllSubject() {
		return repository.selectAllSubject();
	}

	@Override
	public Row6<Integer, Integer, String, Integer, String, Boolean> insertSubject(Subject subject) {
		return repository.insertSubject(subject);
	}

	@Override
	public Row6<Integer, Integer, String, Integer, String, Boolean> updateSubject(Subject subject) {
		return repository.updateSubject(subject);
	}

	@Override
	public Row6<Integer, Integer, String, Integer, String, Boolean> deleteSubjectById(Integer programId) {
		return repository.deleteSubjectById(programId);
	}
}
