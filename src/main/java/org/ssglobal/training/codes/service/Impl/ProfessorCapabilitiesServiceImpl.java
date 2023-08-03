package org.ssglobal.training.codes.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.repository.ProfessorCapabilitiesRepository;
import org.ssglobal.training.codes.service.ProfessorCapabilitiesService;
import org.ssglobal.training.codes.tables.pojos.Grades;
import org.ssglobal.training.codes.tables.pojos.ProfessorLoad;
import org.ssglobal.training.codes.tables.pojos.StudentAttendance;
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
	public List<Map<String, Object>> selectAllStudentsBySection() {
		return repository.selectAllStudentsBySection();
	}
	
	@Override
	public List<Map<String, Object>> selectStudentAttendanceByStudentNoAndSubjectAndSectionAndProfessorNo(
			String subjectTitle, String sectionName, Integer professorNo, String date) {
		return repository.selectStudentAttendanceByAndSubjectAndSectionAndProfessorNoAndDate(subjectTitle, sectionName, professorNo, date);
	}
	
	@Override
	public Map<String, Object> updateStudentAttendance(Map<String, Object> payload) {
		String subjectTitle = payload.get("subjectTitle").toString();
		String sectionName = payload.get("sectionName").toString();
		Integer professorNo = Integer.valueOf(payload.get("professorNo").toString());
		String date = payload.get("date").toString();
		String status = payload.get("status").toString();
		Integer studentAttendanceId = Integer.valueOf(payload.get("studentAttendanceId").toString());

		return repository.updateStudentAttendance(subjectTitle, sectionName, professorNo, date, status, studentAttendanceId);
	}
	
	@Override
	public List<Map<String, Object>> updateStudentGrades(List<Grades> studentGrades) {
		return repository.updateStudentGrades(studentGrades);
	}
	
	@Override
	public List<Grades> updateStudentGradesIsSubmitted(List<Grades> studentGrades) {
		return repository.updateStudentGradesIsSubmitted(studentGrades);
	}
	
	@Override
	public List<StudentAttendance> selectStudentAttendanceByAttendanceDateDistinct(Integer loadId) {
		return repository.selectStudentAttendanceByAttendanceDateDistinct(loadId);
	}
}
