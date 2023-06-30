package org.ssglobal.training.codes.service.Impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.model.UserAndAdmin;
import org.ssglobal.training.codes.model.UserAndParent;
import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.repository.AdminCapabilitiesRepository;
import org.ssglobal.training.codes.service.AdminCapabilitiesService;
import org.ssglobal.training.codes.tables.pojos.AcademicYear;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.Curriculum;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.ProfessorLoad;
import org.ssglobal.training.codes.tables.pojos.Program;
import org.ssglobal.training.codes.tables.pojos.StudentApplicant;
import org.ssglobal.training.codes.tables.pojos.Users;

@Service
public class AdminCapabilitiesServiceImpl implements AdminCapabilitiesService {

	@Autowired
	private AdminCapabilitiesRepository repository;
	
	@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}

	@Override
	public List<Users> selectAllUsers() {
		return repository.selectAllUsers();
	}
	
	@Override
	public boolean changePassword(String password, String username) {
		return repository.changePassword(encoder().encode(password), username);
	}
	
	// ------------------------FOR ADMIN
	@Override
	public List<UserAndAdmin> selectAllAdmin() {
		return repository.selectAllAdmin();
	}

	@Override
	public UserAndAdmin selectAdmin(Integer adminNo) {
		return repository.selectAdmin(adminNo);
	}
	
	@Override
	public UserAndAdmin insertAdminUser(UserAndAdmin userAdmin) throws DuplicateKeyException, Exception {
		selectAllUsers().forEach(user -> {
			if (user.getUsername().equals(userAdmin.getUsername())) {
				throw new DuplicateKeyException("username already exists");
			}
			if (user.getEmail().equals(userAdmin.getEmail())) {
				throw new DuplicateKeyException("email already exists");
			}
		});
		userAdmin.setPassword(encoder().encode(userAdmin.getContactNo().concat(userAdmin.getUsername())));
		return repository.insertAdminUser(userAdmin);
	}

	@Override
	public UserAndAdmin updateAdminUser(UserAndAdmin userAdmin) throws DuplicateKeyException, Exception {
		selectAllUsers().forEach(user -> {
			if (!user.getUserId().equals(userAdmin.getUserId())) {
				if (user.getUsername().equals(userAdmin.getUsername())) {
					throw new DuplicateKeyException("username already exists");
				}
				if (user.getEmail().equals(userAdmin.getEmail())) {
					throw new DuplicateKeyException("email already exists");
				}
			}
		});
		return repository.updateAdminUser(userAdmin);
	}

	@Override
	public UserAndAdmin deactivateAdminUser(Integer userAdminId) {
		return repository.deactivateAdminUser(userAdminId);
	}
	

	// ------------------------FOR Student
	@Override
	public List<UserAndStudent> selectAllStudent() {
		return repository.selectAllStudent();
	}
	
	@Override
	public UserAndStudent selectStudent(Integer studentNo) {
		return repository.selectStudent(studentNo);
	}
	
	@Override
	public UserAndStudent insertStudent(UserAndStudent student) throws DuplicateKeyException, Exception {
		selectAllUsers().forEach(user -> {
			if (user.getUsername().equals(student.getUsername())) {
				throw new DuplicateKeyException("username already exists");
			}
			if (user.getEmail().equals(student.getEmail())) {
				throw new DuplicateKeyException("email already exists");
			}
		});
		student.setPassword(encoder().encode(student.getContactNo().concat(student.getUsername())));
		return repository.insertStudent(student);
	}
	
	@Override
	public UserAndStudent updateStudent(UserAndStudent student) {
		selectAllUsers().forEach(user -> {
			if (!user.getUserId().equals(student.getUserId())) {
				if (user.getUsername().equals(student.getUsername())) {
					throw new DuplicateKeyException("username already exists");
				}
				if (user.getEmail().equals(student.getEmail())) {
					throw new DuplicateKeyException("email already exists");
				}
			}
		});
		return repository.updateStudent(student);
	}
	
	@Override
	public UserAndStudent deactivateStudent(Integer userId) {
		return repository.deactivateStudent(userId);
	}

	// ------------------------FOR Professor
	@Override
	public List<UserAndProfessor> selectAllProfessor() {
		return repository.selectAllProfessor();
	}
	
	@Override
	public UserAndProfessor selectProfessor(Integer professorNo) {
		return repository.selectProfessor(professorNo);
	}
	
	@Override
	public UserAndProfessor insertProfessor(UserAndProfessor userAndProfessor) throws DuplicateKeyException, Exception {
		selectAllUsers().forEach(user -> {
			if (user.getUsername().equals(userAndProfessor.getUsername())) {
				throw new DuplicateKeyException("username already exists");
			}
			if (user.getEmail().equals(userAndProfessor.getEmail())) {
				throw new DuplicateKeyException("email already exists");
			}
		});
		userAndProfessor.setPassword(encoder().encode(userAndProfessor.getContactNo().concat(userAndProfessor.getUsername())));
		return repository.insertProfessor(userAndProfessor);
	}
	
	@Override
	public UserAndProfessor updateProfessor(UserAndProfessor userAndProfessor) throws DuplicateKeyException, Exception {
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
		return repository.updateProfessor(userAndProfessor);
	}
	
	@Override
	public UserAndProfessor changeProfessorAccountStatus(Integer userId, Boolean status) {
		return repository.changeProfessorAccountStatus(userId, status);
	}
	
	// ------------------------FOR Professor
	@Override
	public List<Map<String, Object>> selectAllProfessorsLoad() {
		return repository.selectAllProfessorsLoad();
	}
	
	@Override
	public List<Map<String, Object>> selectProfessorLoad(Integer professorNo) {
		return repository.selectProfessorLoad(professorNo);
	}
	
	@Override
	public ProfessorLoad insertProfessorLoad(ProfessorLoad professorLoad) {
		return repository.insertProfessorLoad(professorLoad);
	}
	
	@Override
	public ProfessorLoad updateProfessorLoad(ProfessorLoad professorLoad) {
		return repository.updateProfessorLoad(professorLoad);
	}
	
	@Override
	public ProfessorLoad deleteProfessorLoad(ProfessorLoad professorLoad) {
		return repository.deleteProfessorLoad(professorLoad);
	}
	
	// ------------------------FOR Parent
	@Override
	public List<UserAndParent> selectAllParent() {
		return repository.selectAllParent();
	}

	@Override
	public UserAndParent selectParent(Integer parentNo) {
		return repository.selectParent(parentNo);
	}
	
	@Override
	public UserAndParent updateParentInfo(UserAndParent parent) {
		selectAllUsers().forEach(user -> {
			if (!user.getUserId().equals(parent.getUserId())) {
				if (user.getUsername().equals(parent.getUsername())) {
					throw new DuplicateKeyException("username already exists");
				}
				if (user.getEmail().equals(parent.getEmail())) {
					throw new DuplicateKeyException("email already exists");
				}
			}
		});
		return repository.updateParentInfo(parent);
	}
	
	@Override
	public UserAndParent deactivateParent(Integer userId) {
		return repository.deactivateParent(userId);
	}
	
	// ------------------------FOR Student_Applicants
	@Override
	public List<StudentApplicant> selectAllStudentApplicants() {
		return repository.selectAllStudentApplicants();
	}
	
	@Override
	public StudentApplicant updateStudentApplicantStatus(StudentApplicant studentApplicant) {
		if (studentApplicant.getAcceptanceStatus().equalsIgnoreCase("Accepted")) {
			studentApplicant.setDateAccepted(LocalDateTime.now());
		}
		return repository.updateStudentApplicantStatus(studentApplicant);
	}
	
	// ------------------------FOR academic_year
	@Override
	public AcademicYear addAcademicYear(AcademicYear academicYear) {
		return repository.addAcademicYear(academicYear);
	}
	
	@Override
	public AcademicYear updateAcademicYearStatus(AcademicYear academicYear) {
		return repository.updateAcademicYearStatus(academicYear);
	}

	// ------------------------FOR program
	@Override
	public Program addProgram(Program program) {
		return repository.addProgram(program);
	}
	
	@Override
	public Program editProgram(Program program) {
		return repository.editProgram(program);
	}

	// ------------------------FOR course
	@Override
	public List<Map<String, Object>> selectAllCourses() {
		return repository.selectAllCourses();
	}
	
	@Override
	public Course addCourse(Course course) {
		return repository.addCourse(course);
	}
	
	@Override
	public Course editCourse(Course course) {
		return repository.editCourse(course);
	}

	// ------------------------FOR Major
	@Override
	public Major addMajor(Major major) {
		return repository.addMajor(major);
	}
	
	@Override
	public Major editMajor(Major major) {
		return repository.editMajor(major);
	}

	// ------------------------FOR Curriculum
	@Override
	public List<Map<String, Object>> selectAllCurriculum() {
		return repository.selectAllCurriculum();
	}
	
	@Override
	public Curriculum addCurriculum(Curriculum curriculum) {
		return repository.addCurriculum(curriculum);
	}
	
	@Override
	public Curriculum editCurriculum(Curriculum curriculum) {
		return repository.editCurriculum(curriculum);
	}
	
	// ------------------------FOR INNER JOIN OF Curriculum, Major, Course, Department and Program;
	@Override
	public List<Map<String, Object>> selectAllCurriculumInnerJoinOnMajorAndCourseAndDepartmentAndProgram() {
		return repository.selectAllCurriculumInnerJoinOnMajorAndCourseAndDepartmentAndProgram();
	}
}
