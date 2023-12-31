package org.ssglobal.training.codes.service;

import java.util.List;
import java.util.Map;

import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.tables.pojos.Grades;
import org.ssglobal.training.codes.tables.pojos.ProfessorLoad;
import org.ssglobal.training.codes.tables.pojos.StudentAttendance;
import org.ssglobal.training.codes.tables.pojos.Users;
import org.ssglobal.training.codes.tables.pojos.WebsiteActivationToggle;

public interface ProfessorCapabilitiesService {

	public List<Users> selectAllUsers();
	UserAndProfessor selectProfessor(Integer professorNo);
	UserAndProfessor updateProfessor(UserAndProfessor userAndProfessor);
	List<ProfessorLoad> selectAllLoad(Integer professorNo);
	List<Map<String, Object>> selectAllLoads(Integer professorNo);
	List<Map<String, Object>> selectProfessorLoadByProfessorNoAndSubjectCodeAndSection(Integer professorNo, Integer subjectCode, String sectionName);
	List<Map<String, Object>> selectAllStudentsBySection();
	
	List<Map<String, Object>> selectStudentAttendanceByStudentNoAndSubjectAndSectionAndProfessorNo
							(String subjectTitle, String sectionName, Integer professorNo, String date);
	
	Map<String, Object> updateStudentAttendance(Map<String, Object> payload);
	List<Grades> updateStudentGradesIsSubmitted(List<Grades> studentGrades);
	List<Map<String, Object>> updateStudentGrades(List<Grades> studentGrades);
	
	List<StudentAttendance> selectStudentAttendanceByAttendanceDateDistinct(Integer loadId);
	WebsiteActivationToggle selectWebsiteActivationToggle();

}
