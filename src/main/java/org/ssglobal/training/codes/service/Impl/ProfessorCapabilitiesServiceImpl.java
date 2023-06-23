package org.ssglobal.training.codes.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.repository.ProfessorCapabilitiesRepository;
import org.ssglobal.training.codes.service.ProfessorCapabilitiesService;

@Service
public class ProfessorCapabilitiesServiceImpl implements ProfessorCapabilitiesService {
	
	@Autowired
	private ProfessorCapabilitiesRepository repository;

	@Override
	public UserAndProfessor selectProfessor(Integer professorNo) {
		return repository.selectProfessor(professorNo);
	}

	@Override
	public UserAndProfessor insertProfessor(UserAndProfessor userAndProfessor) {
		return repository.insertProfessor(userAndProfessor);
	}

	@Override
	public UserAndProfessor updateProfessor(UserAndProfessor userAndProfessor) {
		return repository.updateProfessor(userAndProfessor);
	}
}
