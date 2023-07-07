package org.ssglobal.training.codes.service;

import java.util.List;
import java.util.Map;

import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.tables.pojos.ProfessorLoad;
import org.ssglobal.training.codes.tables.pojos.Users;

public interface ProfessorCapabilitiesService {

	public List<Users> selectAllUsers();
	UserAndProfessor selectProfessor(Integer professorNo);
	UserAndProfessor updateProfessor(UserAndProfessor userAndProfessor);
	List<ProfessorLoad> selectAllLoad(Integer professorNo);
	List<Map<String, Object>> selectAllLoads(Integer professorNo);
	List<Map<String, Object>> selectProfessorLoadByProfessorNoAndSubjectCodeAndSection(Integer professorNo, Integer subjectCode, String sectionName);
}
