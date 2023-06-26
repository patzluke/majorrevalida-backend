package org.ssglobal.training.codes.service;

import java.util.List;
import java.util.Map;

import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.tables.pojos.ProfessorLoad;

public interface ProfessorCapabilitiesService {

	UserAndProfessor selectProfessor(Integer professorNo);
	UserAndProfessor updateProfessor(UserAndProfessor userAndProfessor);
	List<ProfessorLoad> selectAllLoad(Integer professorNo);
	List<Map<String, Object>> selectAllLoads(Integer professorNo);
}
