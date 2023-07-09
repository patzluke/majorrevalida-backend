package org.ssglobal.training.codes.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.repository.ProfessorCapabilitiesRepository;
import org.ssglobal.training.codes.service.ProfessorCapabilitiesService;
import org.ssglobal.training.codes.tables.pojos.ProfessorLoad;
import org.ssglobal.training.codes.tables.pojos.Users;

@Service
public class ProfessorCapabilitiesServiceImpl implements ProfessorCapabilitiesService {
	
	@Autowired
	private ProfessorCapabilitiesRepository repository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	
	@Override
	public List<Users> selectAllUsers() {
		return repository.selectAllUsers();
	}

	@Override
	public UserAndProfessor selectProfessor(Integer professorNo) {
		return repository.selectProfessor(professorNo);
	}

	@Override
	public UserAndProfessor updateProfessor(UserAndProfessor userAndProfessor) {
		selectAllUsers().forEach(user -> {
			if (!user.getUserId().equals(userAndProfessor.getUserId())) {
				if (user.getUsername().equals(userAndProfessor.getUsername())) {
					throw new DuplicateKeyException("username already exists");
				}
				if (user.getEmail().equals(userAndProfessor.getEmail())) {
					throw new DuplicateKeyException("email already exists");
				}
				
			}
		});
		
		if (!userAndProfessor.getPassword().isEmpty()) {
			userAndProfessor.setPassword(encoder.encode(userAndProfessor.getPassword()));

		}
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
	
	@Override
	public List<Map<String, Object>> selectProfessorLoadByProfessorNoAndSubjectCodeAndSection(Integer professorNo,
			Integer subjectCode, String sectionName) {
		return selectProfessorLoadByProfessorNoAndSubjectCodeAndSection(professorNo, subjectCode, sectionName);
	}
	
	@Override
	public List<UserAndStudent> selectAllStudentsBySectionId(String sectionName) {
		return repository.selectAllStudentsBySectionId(sectionName);
	}
}
