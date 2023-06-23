package org.ssglobal.training.codes.service;

import org.ssglobal.training.codes.model.UserAndProfessor;

public interface ProfessorCapabilitiesService {

	UserAndProfessor selectProfessor(Integer professorNo);
	UserAndProfessor insertProfessor(UserAndProfessor userAndProfessor);
	UserAndProfessor updateProfessor(UserAndProfessor userAndProfessor);
}
