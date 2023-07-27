package org.ssglobal.training.codes.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.training.codes.model.EnrollmentData;
import org.ssglobal.training.codes.model.UserAndAdmin;
import org.ssglobal.training.codes.model.UserAndParent;
import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.service.AdminCapabilitiesService;
import org.ssglobal.training.codes.tables.pojos.AcademicYear;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.Department;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.ProfessorLoad;
import org.ssglobal.training.codes.tables.pojos.Program;
import org.ssglobal.training.codes.tables.pojos.Room;
import org.ssglobal.training.codes.tables.pojos.StudentApplicant;
import org.ssglobal.training.codes.tables.pojos.StudentEnrollment;
import org.ssglobal.training.codes.tables.pojos.Subject;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminCapabilitiesController {

	@Autowired
	private AdminCapabilitiesService service;

	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/update/password", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity changePassword(@RequestBody Map<String, String> payload) {		
		return service.changePassword(payload.get("password"), payload.get("username")) ? ResponseEntity.ok().build()
				: ResponseEntity.badRequest().build();
	}

	// -------- For Admin
	@GetMapping(value = "/get/admin", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<UserAndAdmin>> selectAllAdmin() {
		try {
			List<UserAndAdmin> updatedAdmin = service.selectAllAdmin();
			if (!updatedAdmin.isEmpty()) {
				return ResponseEntity.ok(updatedAdmin);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping(value = "/get/admin/{adminNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserAndAdmin> selectAdmin(@PathVariable(name = "adminNo") Integer adminNo) {
		try {
			UserAndAdmin selectedAdmin = service.selectAdmin(adminNo);
			if (selectedAdmin != null) {
				return ResponseEntity.ok(selectedAdmin);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/insert/admin", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity createUser(@RequestBody UserAndAdmin userAndAdmin) {
		UserAndAdmin addedAdmin;
		try {
			addedAdmin = service.insertAdminUser(userAndAdmin);
			if (addedAdmin != null) {
				return ResponseEntity.ok(addedAdmin);
			}
		} catch (DuplicateKeyException e1) {
			return ResponseEntity.badRequest().body(e1.getMessage());
		} catch (Exception e1) {
			e1.printStackTrace();
			return ResponseEntity.badRequest().body("something went wrong");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/update/admin", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity updateAdminUser(@RequestBody UserAndAdmin userAndAdmin) {
		UserAndAdmin updatedAdmin;
		try {
			updatedAdmin = service.updateAdminUser(userAndAdmin);
			if (updatedAdmin != null) {
				return ResponseEntity.ok(updatedAdmin);
			}
		} catch (DuplicateKeyException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("something went wrong");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@PutMapping(value = "/deactivate/admin", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserAndAdmin> changeAdminAccountStatus(@RequestBody Map<String, Object> payload) {
		try {
			UserAndAdmin updatedAdmin = service.changeAdminAccountStatus(
					Integer.valueOf(payload.get("userId").toString()),
					Boolean.valueOf(payload.get("status").toString()));
			if (updatedAdmin != null) {
				return ResponseEntity.ok(updatedAdmin);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@DeleteMapping(value = "/delete/admin/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserAndAdmin> deleteAdminUser(@PathVariable(name = "userId") Integer userId) {
		try {
			UserAndAdmin updatedAdmin = service.deleteAdminUser(userId);
			if (updatedAdmin != null) {
				return ResponseEntity.ok(updatedAdmin);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	// -------- For Student
	@GetMapping(value = "/get/student")
	public ResponseEntity<List<UserAndStudent>> selectAllStudent() {
		try {
			List<UserAndStudent> studentProfile = service.selectAllStudent();
			if (studentProfile != null) {
				return ResponseEntity.ok(studentProfile);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/insert/student", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity insertStudent(@RequestBody UserAndStudent student) {
		UserAndStudent addedStudent;
		try {
			addedStudent = service.insertStudent(student);
			if (addedStudent != null) {
				return ResponseEntity.ok(addedStudent);
			}
		} catch (DuplicateKeyException e1) {
			return ResponseEntity.badRequest().body(e1.getMessage());
		} catch (Exception e1) {
			e1.printStackTrace();
			return ResponseEntity.badRequest().body("something went wrong");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/update/student", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity updateStudent(@RequestBody UserAndStudent userAndStudent) {
		UserAndStudent updatedAdmin;
		try {
			updatedAdmin = service.updateStudent(userAndStudent);
			if (updatedAdmin != null) {
				return ResponseEntity.ok(updatedAdmin);
			}
		} catch (DuplicateKeyException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("something went wrong");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@PutMapping(value = "/deactivate/student", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserAndStudent> changeStudentAccountStatus(@RequestBody Map<String, Object> payload) {
		try {
			UserAndStudent updatedStudent = service.changeStudentAccountStatus(
					Integer.valueOf(payload.get("userId").toString()),
					Boolean.valueOf(payload.get("status").toString()));
			if (updatedStudent != null) {
				return ResponseEntity.ok(updatedStudent);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@DeleteMapping(value = "/delete/student/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserAndStudent> deleteStudent(@PathVariable(name = "userId") Integer userId) {
		try {
			UserAndStudent deletedStudent = service.deleteStudent(userId);
			if (deletedStudent != null) {
				return ResponseEntity.ok(deletedStudent);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping(value ="/get/student/studentyear", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<Map<String, Object>>> getAllStudentWithAcademicYear(){
		try {
			List<Map<String, Object>> student = service.getAllStudentWithAcademicYear();
			if (!student.isEmpty()) {
				return ResponseEntity.ok(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	// -------- For Student Applicants
	@GetMapping(value = "/get/studentapplicant")
	public ResponseEntity<List<StudentApplicant>> selectAllStudentApplicants() {
		try {
			List<StudentApplicant> studentProfile = service.selectAllStudentApplicants();
			if (studentProfile != null) {
				return ResponseEntity.ok(studentProfile);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping(value = "/update/studentapplicant", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<StudentApplicant> updateStudentApplicantStatus(
			@RequestBody StudentApplicant studentApplicant) {
		try {
			StudentApplicant updatedAdmin = service.updateStudentApplicantStatus(studentApplicant);
			if (updatedAdmin != null) {
				return ResponseEntity.ok(updatedAdmin);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	// -------- For Professor
	@GetMapping(value = "/get/professor", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<UserAndProfessor>> selectAllProfessor() {
		try {
			List<UserAndProfessor> professorList = service.selectAllProfessor();
			if (!professorList.isEmpty()) {
				return ResponseEntity.ok(professorList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/insert/professor", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity insertProfessor(@RequestBody UserAndProfessor userAndProfessor) {
		UserAndProfessor addedProfessor;
		try {
			addedProfessor = service.insertProfessor(userAndProfessor);
			if (addedProfessor != null) {
				return ResponseEntity.ok(addedProfessor);
			}
		} catch (DuplicateKeyException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("something went wrong");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/update/professor", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity updateProfessor(@RequestBody UserAndProfessor userAndProfessor) {
		UserAndProfessor updatedProfessor;
		try {
			updatedProfessor = service.updateProfessor(userAndProfessor);
			if (updatedProfessor != null) {
				return ResponseEntity.ok(updatedProfessor);
			}
		} catch (DuplicateKeyException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("something went wrong");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@PutMapping(value = "/deactivate/professor", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserAndProfessor> changeProfessorAccountStatus(@RequestBody Map<String, Object> payload) {
		try {
			UserAndProfessor updatedProfessor = service.changeProfessorAccountStatus(
					Integer.valueOf(payload.get("userId").toString()),
					Boolean.valueOf(payload.get("status").toString()));
			if (updatedProfessor != null) {
				return ResponseEntity.ok(updatedProfessor);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping(value = "/delete/professor/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserAndProfessor> deleteProfessor(@PathVariable(name = "userId") Integer userId) {
		try {
			UserAndProfessor deletedProfessor = service.deleteProfessor(userId);
			if (deletedProfessor != null) {
				return ResponseEntity.ok(deletedProfessor);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	// -------- For Professor Load
	@GetMapping(value = "/get/professorload", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Map<String, Object>>> selectAllProfessorsLoad() {
		try {
			List<Map<String, Object>> selectedProfessorsLoad = service.selectAllProfessorsLoad();
			if (!selectedProfessorsLoad.isEmpty()) {
				return ResponseEntity.ok(selectedProfessorsLoad);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping(value = "/get/professorload/{professorNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Map<String, Object>>> selectProfessorLoad(
			@PathVariable(name = "professorNo") Integer professorNo) {
		try {
			List<Map<String, Object>> selectedProfessorLoad = service.selectProfessorLoad(professorNo);
			if (selectedProfessorLoad != null) {
				return ResponseEntity.ok(selectedProfessorLoad);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/insert/professorload", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity insertProfessorLoad(@RequestBody ProfessorLoad professorLoad) {
		Map<String, Object> addedProfessorLoad;
		try {
			addedProfessorLoad = service.insertProfessorLoad(professorLoad);
			if (addedProfessorLoad != null) {
				return ResponseEntity.ok(addedProfessorLoad);
			}
		} catch (DuplicateKeyException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("something went wrong");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/update/professorload", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity updateProfessorLoad(@RequestBody ProfessorLoad professorLoad) {
		Map<String, Object> updatedProfessorLoad;
		try {
			updatedProfessorLoad = service.updateProfessorLoad(professorLoad);
			if (updatedProfessorLoad != null) {
				return ResponseEntity.ok(updatedProfessorLoad);
			}
		} catch (DuplicateKeyException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("something went wrong");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@SuppressWarnings("rawtypes")
	@DeleteMapping(value = "/delete/professorload/{loadId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity deleteProfessorLoad(@PathVariable(name = "loadId") Integer loadId) {
		Map<String, Object> deletedProfessorLoad;
		try {
			deletedProfessorLoad = service.deleteProfessorLoad(loadId);
			if (deletedProfessorLoad != null) {
				return ResponseEntity.ok(deletedProfessorLoad);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("something went wrong");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	// -------- For Parent
	@GetMapping(value = "/get/parent", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<UserAndParent>> selectAllParent() {
		try {
			List<UserAndParent> parents = service.selectAllParent();
			if (!parents.isEmpty()) {
				return ResponseEntity.ok(parents);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/update/parent", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity updateProfessor(@RequestBody UserAndParent userAndParent) {
		UserAndParent updatedProfessor;
		try {
			updatedProfessor = service.updateParentInfo(userAndParent);
			if (updatedProfessor != null) {
				return ResponseEntity.ok(updatedProfessor);
			}
		} catch (DuplicateKeyException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("something went wrong");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@PutMapping(value = "/deactivate/parent", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserAndParent> changeParentAccountStatus(@RequestBody Map<String, Object> payload) {
		try {
			UserAndParent updatedProfessor = service.changeParentAccountStatus(
					Integer.valueOf(payload.get("userId").toString()),
					Boolean.valueOf(payload.get("status").toString()));
			if (updatedProfessor != null) {
				return ResponseEntity.ok(updatedProfessor);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping(value = "/delete/parent/{userId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserAndParent> deleteParent(@PathVariable(name = "userId") Integer userId) {
		try {
			UserAndParent deletedProfessor = service.deleteParent(userId);
			if (deletedProfessor != null) {
				return ResponseEntity.ok(deletedProfessor);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	// -------- For Academic Year
	@PostMapping(value = "/insert/academic-year", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<AcademicYear> insertAcademicYear(@RequestBody AcademicYear academicYear) {
		try {
			AcademicYear addedAcademicYear = service.addAcademicYear(academicYear);
			if (addedAcademicYear != null) {
				return ResponseEntity.ok(addedAcademicYear);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	// -------- For Program
	@GetMapping(value = "/get/program", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Program>> selectAllProgram() {
		try {
			List<Program> updatedAdmin = service.selectAllProgram();
			if (!updatedAdmin.isEmpty()) {
				return ResponseEntity.ok(updatedAdmin);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@PostMapping(value = "/insert/program", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Program> insertProgram(@RequestBody Program program) {
		try {
			Program addedProgram = service.addProgram(program);
			if (addedProgram != null) {
				return ResponseEntity.ok(addedProgram);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PutMapping(value = "/update/program", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Program> updateProgram(@RequestBody Program program) {
		try {
			Program addedProgram = service.editProgram(program);
			if (addedProgram != null) {
				return ResponseEntity.ok(addedProgram);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	// -------- For Course
	@GetMapping(value = "/get/course/dept/program", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Map<String, Object>>> selectAllCourses() {
		try {
			List<Map<String, Object>> updatedAdmin = service.selectAllCourses();
			if (!updatedAdmin.isEmpty()) {
				return ResponseEntity.ok(updatedAdmin);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@PostMapping(value = "/insert/course", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, Object>> insertCourse(@RequestBody Course course) {
		try {
			Map<String, Object> addedCourse = service.addCourse(course);
			if (addedCourse != null) {
				return ResponseEntity.ok(addedCourse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping(value = "/update/course", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, Object>> editCourse(@RequestBody Course course) {
		try {
			Map<String, Object> addedCourse = service.editCourse(course);
			if (addedCourse != null) {
				return ResponseEntity.ok(addedCourse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	// -------- For Department
	@GetMapping(value = "/get/department", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Department>> selectAllDepartment() {
		try {
			List<Department> departments = service.selectAllDepartment();
			if (!departments.isEmpty()) {
				return ResponseEntity.ok(departments);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PostMapping(value = "/add/department", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
		try {
			Department departments = service.insertDepartment(department);
			if (departments.getDeptCode() != null) {
				return ResponseEntity.ok(departments);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PutMapping(value = "/update/department", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Department> updateDepartment(@RequestBody Department department) {
		try {
			Department departments = service.updateDepartment(department);
			if (departments.getDeptCode() != null) {
				return ResponseEntity.ok(departments);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PutMapping(value = "/delete/department", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Department> deleteDepartment(@RequestBody Department department) {
		try {
			Department departments = service.deleteDepartment(department);
			if (departments.getDeptCode() != null) {
				return ResponseEntity.ok(departments);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	// -------- For Major
	@GetMapping(value = "/get/major", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Major>> selectAllMajor() {
		try {
			List<Major> departments = service.selectAllMajor();
			if (!departments.isEmpty()) {
				return ResponseEntity.ok(departments);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	// -------- For Curriculum
	@GetMapping(value = "/get/curriculum/major", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Map<String, Object>>> selectCourseMajors() {
		try {
			List<Map<String, Object>> selectedCourseMajor = service.selectAllCurriculum();
			if (!selectedCourseMajor.isEmpty()) {
				return ResponseEntity.ok(selectedCourseMajor);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	// -------- For Curriculum And Major
	@PostMapping(value = "/insert/curriculum/major", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, Object>> addCurriculumAndMajor(@RequestBody Map<String, Object> payload) {
		try {
			Map<String, Object> addedCurriculum = service.addCurriculumAndMajor(payload);
			if (addedCurriculum != null) {
				return ResponseEntity.ok(addedCurriculum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping(value = "/update/curriculum/major", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, Object>> editCurriculumAndMajor(@RequestBody Map<String, Object> payload) {
		System.out.println(payload);
		try {
			Map<String, Object> updatedCurriculum = service.editCurriculumAndMajor(payload);
			if (updatedCurriculum != null) {
				return ResponseEntity.ok(updatedCurriculum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@DeleteMapping(value = "/delete/curriculum/major/{curriculumCode}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, Object>> deleteCurriculumAndMajor(
			@PathVariable(name = "curriculumCode") Integer curriculumCode) {
		try {
			Map<String, Object> updatedAdmin = service.deleteCurriculumAndMajor(curriculumCode);
			if (!updatedAdmin.isEmpty()) {
				return ResponseEntity.ok(updatedAdmin);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	// -------- For Subject
	@GetMapping(value = "/get/subject", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Subject>> selectAllSubject() {
		try {
			List<Subject> subjects = service.selectAllSubject();
			if (!subjects.isEmpty()) {
				return ResponseEntity.ok(subjects);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	// -------- For Section
	@GetMapping(value = "/get/section", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Map<String, Object>>> selectAllSection() {
		try {
			List<Map<String, Object>> sections = service.selectAllSection();
			System.out.println(sections);
			if (!sections.isEmpty()) {
				return ResponseEntity.ok(sections);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	// -------- For Room
	@GetMapping(value = "/get/room", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Room>> selectAllRoom() {
		try {
			List<Room> rooms = service.selectAllRoom();
			if (!rooms.isEmpty()) {
				return ResponseEntity.ok(rooms);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	// -------- For Student grades
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/get/studentgrades", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity selectAllStudentsBySection() {
		try {
			List<Map<String, Object>> rooms = service.selectAllStudentsBySection();
			System.out.println(rooms);
			if (!rooms.isEmpty()) {
				return ResponseEntity.ok(rooms);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	// ---------FOR THE MINOR SUBJECTS
	@GetMapping(value = "/get/subjects/minor", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Map<String, Object>>> selectAllMinorSubjects() {
		try {
			List<Map<String, Object>> updatedAdmin = service.selectAllMinorSubjects();
			if (!updatedAdmin.isEmpty()) {
				return ResponseEntity.ok(updatedAdmin);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/update/subjects/minor", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity editMinorSubject(@RequestBody Map<String, Object> payload) {
		try {
			Map<String, Object> updatedMinorSubject = service.editMinorSubject(payload);
			if (!updatedMinorSubject.isEmpty()) {
				return ResponseEntity.ok(updatedMinorSubject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@PutMapping(value = "/deactive/subjects/minor", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, Object>> deactivelMinorSubjects(@RequestBody Map<String, Object> payload) {
		try {
			Map<String, Object> updatedMinorSubject = service.changeMinorSubjectStatus(
					Integer.valueOf(payload.get("subjectCode").toString()),
					Boolean.valueOf(payload.get("activeStatus").toString()));
			if (!updatedMinorSubject.isEmpty()) {
				return ResponseEntity.ok(updatedMinorSubject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/delete/subjects/minor", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity deleteMinorSubjects(@RequestBody Map<String, Object> payload) {
		try {
			Map<String, Object> updatedMinorSubject = service.deleteMinorSubject(
					Integer.valueOf(payload.get("subjectCode").toString()),
					Boolean.valueOf(payload.get("activeDeactive").toString()));
			if (!updatedMinorSubject.isEmpty()) {
				return ResponseEntity.ok(updatedMinorSubject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/add/subjects/minor", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity inserMinorSubject(@RequestBody Map<String, Object> payload) {
		try {
			Map<String, Object> newMinorSubject = service.inserMinorSubject(payload);
			if (!newMinorSubject.isEmpty()) {
				return ResponseEntity.ok(newMinorSubject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	// ---------FOR THE MAJOR SUBJECTS
	@GetMapping(value = "/get/subjects/major", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Map<String, Object>>> selectAllSubjectsByCurriculum() {
		try {
			List<Map<String, Object>> allSubjects = service.selectAllMajorSubjects();
			if (!allSubjects.isEmpty()) {
				return ResponseEntity.ok(allSubjects);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@GetMapping(value = "/get/subjects/major/all/{courseCode}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Map<String, Object>>> selectAllSubjectsByAllCourses(@PathVariable(name = "courseCode") Integer courseCode) {
		try {
			List<Map<String, Object>> allSubjects = service.selectAllMajorSubjectsByAllCourse(courseCode);
			if (!allSubjects.isEmpty()) {
				return ResponseEntity.ok(allSubjects);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@PutMapping(value = "/deactive/subjects/major", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, Object>> deactivelMajorSubjects(@RequestBody Map<String, Object> payload) {
		try {
			System.out.println(Boolean.valueOf(payload.get("activeStatus").toString()));
			Map<String, Object> updatedMinorSubject = service.changeMajorSubjectStatus(
					Integer.valueOf(payload.get("subjectCode").toString()),
					Boolean.valueOf(payload.get("activeStatus").toString()));
			if (!updatedMinorSubject.isEmpty()) {
				return ResponseEntity.ok(updatedMinorSubject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PutMapping(value = "/deactive/subjects/major/all", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String, Object>> deactivelMajorSubjectsByCourse(@RequestBody Map<String, Object> payload) {
		try {
			System.out.println(Boolean.valueOf(payload.get("activeStatus").toString()));
			Map<String, Object> updatedMinorSubject = service.changeMajorSubjectStatusByCourse(
					Integer.valueOf(payload.get("subjectCode").toString()),
					Boolean.valueOf(payload.get("activeStatus").toString()),
					Integer.valueOf(payload.get("courseCode").toString())
					);
			if (!updatedMinorSubject.isEmpty()) {
				return ResponseEntity.ok(updatedMinorSubject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/add/subjects/major", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity insertMajorSubjectByMajor(@RequestBody Map<String, Object> payload) {
		try {
			Map<String, Object> newMajorSubject = service.addMajorSubjectByMajor(payload);
			if (!newMajorSubject.isEmpty()) {
				return ResponseEntity.ok(newMajorSubject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/add/subjects/major/all/{courseCode}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity insertMajorSubjectByCourse(@RequestBody Map<String, Object> payload, @PathVariable("courseCode") Integer courseCode) {
		try {
			Map<String, Object> newMajorSubject = service.addMajorSubjectByAll(payload, courseCode);
			if (!newMajorSubject.isEmpty()) {
				return ResponseEntity.ok(newMajorSubject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/update/subjects/major/all", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity editMajorSubjectByCourse(@RequestBody Map<String, Object> payload) {
		try {
			Map<String, Object> updatedMinorSubject = service.editMajorSubjectByAll(payload);
			if (!updatedMinorSubject.isEmpty()) {
				return ResponseEntity.ok(updatedMinorSubject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Backend");
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/update/subjects/major", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity editMajorSubject(@RequestBody Map<String, Object> payload) {
		try {
			Map<String, Object> updatedMinorSubject = service.editMajorSubject(payload);
			if (!updatedMinorSubject.isEmpty()) {
				return ResponseEntity.ok(updatedMinorSubject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Backend");
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping(value = "/delete/subjects/major/{subjectCode}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity deleteMajorSubject(@PathVariable("subjectCode") Integer subjectCode) {
		try {
			Map<String, Object> updatedMinorSubject = service.deleteMajorSubject(subjectCode);
			if (!updatedMinorSubject.isEmpty()) {
				return ResponseEntity.ok(updatedMinorSubject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Backend");
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping(value = "/delete/subjects/major/all/{subjectCode}/{curriculumCode}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity deleteMajorSubjectByCourse(@PathVariable("subjectCode") Integer subjectCode,@PathVariable("curriculumCode") Integer curriculumCode) {
		try {
			Map<String, Object> updatedMinorSubject = service.deleteMajorSubjectByCourse(subjectCode, curriculumCode);
			if (!updatedMinorSubject.isEmpty()) {
				return ResponseEntity.ok(updatedMinorSubject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Backend");
	}

	@GetMapping(value = "/get/majorsubject/remarks/{studentApplicantId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Map<String, Object>>> selectStudentPassedMajorSubject(
			@PathVariable(name = "studentApplicantId") Integer studentApplicantId) {
		try {
			List<Map<String, Object>> passedMajor = service.selectStudentPassedMajorSubject(studentApplicantId);
			if (!passedMajor.isEmpty()) {
				return ResponseEntity.ok(passedMajor);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@GetMapping(value = "/get/minorsubject/remarks/{studentApplicantId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Map<String, Object>>> selectStudentPassedMinorSubject(
			@PathVariable(name = "studentApplicantId") Integer studentApplicantId) {
		try {
			List<Map<String, Object>> passedMinor = service.selectStudentPassedMinorSubject(studentApplicantId);
			if (!passedMinor.isEmpty()) {
				return ResponseEntity.ok(passedMinor);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	
	@GetMapping(value = "/get/majorsubject/freshman/{studentApplicantId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Map<String, Object>>> selectFreshManStudentMajorSubject(
			@PathVariable(name = "studentApplicantId") Integer studentApplicantId) {
		try {
			List<Map<String, Object>> freshmanMajor = service.selectFreshManStudentMajorSubject(studentApplicantId);
			if (!freshmanMajor.isEmpty()) {
				return ResponseEntity.ok(freshmanMajor);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@GetMapping(value = "/get/minorsubject/freshman/{studentApplicantId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Map<String, Object>>> selectFreshManStudentMinorSubject(
			@PathVariable(name = "studentApplicantId") Integer studentApplicantId) {
		try {
			List<Map<String, Object>> freshmanMinor = service.selectFreshManStudentMinorSubject(studentApplicantId);
			if (!freshmanMinor.isEmpty()) {
				return ResponseEntity.ok(freshmanMinor);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PostMapping(value = "/enroll/applicant", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<StudentEnrollment> insertStudentEnrollmentData(@RequestBody StudentApplicant studentApplicant) {
		try {
			StudentEnrollment applicant = service.insertStudentEnrollmentData(studentApplicant);
			if (applicant != null) {
				return ResponseEntity.ok(applicant);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@GetMapping(value = "/get/enrollment/all-data", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Map<String, Object>>> getAllEnrollmentData() {
		try {
			List<Map<String, Object>> enrollee = service.getAllEnrollmentData();
			if (!enrollee.isEmpty()) {
				return ResponseEntity.ok(enrollee);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PutMapping(value = "/update/enrollment", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<EnrollmentData> fullyEnrollStudent(@RequestBody EnrollmentData student){
		try {
			EnrollmentData enrollee = service.fullyEnrollStudent(student);
			if (enrollee != null) {
				return ResponseEntity.ok(enrollee);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
