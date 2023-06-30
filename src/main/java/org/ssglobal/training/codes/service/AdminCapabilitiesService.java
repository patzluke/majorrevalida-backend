package org.ssglobal.training.codes.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.ssglobal.training.codes.model.UserAndAdmin;
import org.ssglobal.training.codes.model.UserAndParent;
import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.AcademicYear;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.Curriculum;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.ProfessorLoad;
import org.ssglobal.training.codes.tables.pojos.Program;
import org.ssglobal.training.codes.tables.pojos.StudentApplicant;
import org.ssglobal.training.codes.tables.pojos.Users;

public interface AdminCapabilitiesService {
	
	List<Users> selectAllUsers();
	boolean changePassword(String password, String username);
	
	// ------------------------FOR ADMIN
	List<UserAndAdmin> selectAllAdmin();
	UserAndAdmin selectAdmin(Integer adminNo);
	UserAndAdmin insertAdminUser(UserAndAdmin userAdmin) throws DuplicateKeyException, Exception;
	UserAndAdmin updateAdminUser(UserAndAdmin userAdmin) throws DuplicateKeyException, Exception;
	UserAndAdmin deactivateAdminUser(Integer userAdminId);
	
	// ------------------------FOR Student
	List<UserAndStudent> selectAllStudent();
	UserAndStudent selectStudent(Integer studentNo);
	UserAndStudent insertStudent(UserAndStudent student) throws DuplicateKeyException, Exception;
	UserAndStudent updateStudent(UserAndStudent student) throws DuplicateKeyException, Exception;
	UserAndStudent deactivateStudent(Integer userId);
	
	// ------------------------FOR Professor
	List<UserAndProfessor> selectAllProfessor();
	UserAndProfessor selectProfessor(Integer professorNo);
	UserAndProfessor insertProfessor(UserAndProfessor userAndProfessor) throws DuplicateKeyException, Exception;
	UserAndProfessor updateProfessor(UserAndProfessor userAndProfessor) throws DuplicateKeyException, Exception;
	UserAndProfessor changeProfessorAccountStatus(Integer userId, Boolean status);
	
	// ------------------------FOR Professor Load
	List<Map<String, Object>> selectAllProfessorsLoad();
	List<Map<String, Object>> selectProfessorLoad(Integer professorNo);
	ProfessorLoad insertProfessorLoad(ProfessorLoad professorLoad);
	ProfessorLoad updateProfessorLoad(ProfessorLoad professorLoad);
	ProfessorLoad deleteProfessorLoad(ProfessorLoad professorLoad);
	
	// ------------------------FOR Parent
	List<UserAndParent> selectAllParent();
	UserAndParent selectParent(Integer parentNo);
	UserAndParent updateParentInfo(UserAndParent parent) throws DuplicateKeyException, Exception;
	UserAndParent deactivateParent(Integer userId);
	
	// ------------------------FOR Student_applicants
	List<StudentApplicant> selectAllStudentApplicants();
	StudentApplicant updateStudentApplicantStatus(StudentApplicant studentApplicant);
	
	// ------------------------FOR Academic year
	AcademicYear addAcademicYear(AcademicYear academicYear);
	AcademicYear updateAcademicYearStatus(AcademicYear academicYear);
	
	// ------------------------FOR Program
	Program addProgram(Program program);
	Program editProgram(Program program);
	
	// ------------------------FOR Course
	List<Map<String, Object>> selectAllCourses();
	Course addCourse(Course course);
	Course editCourse(Course course);
	
	// ------------------------FOR Major
	Major addMajor(Major major);
	Major editMajor(Major major);
	
	// ------------------------FOR Curriculum
	List<Map<String, Object>> selectAllCurriculum();
	Curriculum addCurriculum(Curriculum curriculum);
	Curriculum editCurriculum(Curriculum curriculum);
	
	// ------------------------FOR INNER JOIN OF Curriculum, Major, Course, Department and Program;
	List<Map<String, Object>> selectAllCurriculumInnerJoinOnMajorAndCourseAndDepartmentAndProgram();
}
