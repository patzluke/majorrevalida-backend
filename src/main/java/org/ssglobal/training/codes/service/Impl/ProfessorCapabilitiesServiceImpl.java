package org.ssglobal.training.codes.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.repository.ProfessorCapabilitiesRepository;
import org.ssglobal.training.codes.service.ProfessorCapabilitiesService;
import org.ssglobal.training.codes.tables.pojos.ProfessorLoad;

@Service
public class ProfessorCapabilitiesServiceImpl implements ProfessorCapabilitiesService {
	
	@Autowired
	private ProfessorCapabilitiesRepository repository;

	@Override
	public UserAndProfessor selectProfessor(Integer professorNo) {
		return repository.selectProfessor(professorNo);
	}

	@Override
	public UserAndProfessor updateProfessor(UserAndProfessor userAndProfessor) {
		return repository.updateProfessor(userAndProfessor);
	}

	@Override
	public List<ProfessorLoad> selectAllLoad(Integer professorNo) {
		return repository.selectAllLoad(professorNo);
	}

	@Override
	public List<Map<String, Object>> selectAllLoads(Integer professorNo) {
		return repository.selectAllLoads(professorNo);
	}
}
