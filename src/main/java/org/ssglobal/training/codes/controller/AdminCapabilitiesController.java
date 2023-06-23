package org.ssglobal.training.codes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.ssglobal.training.codes.model.UserAndAdmin;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.service.AdminCapabilitiesService;
import org.ssglobal.training.codes.tables.pojos.AcademicYear;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.Curriculum;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.Program;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminCapabilitiesController {

	@Autowired
	private AdminCapabilitiesService service;

	// -------- For Admin
	@PostMapping(value = "/insert/admin", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserAndAdmin> createUser(@RequestBody UserAndAdmin userAndAdmin) {
		System.out.println(userAndAdmin);
		try {
			UserAndAdmin addedAdmin = service.insertAdminUser(userAndAdmin);
			if (addedAdmin != null) {
				return ResponseEntity.ok(addedAdmin);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping(value = "/update/admin", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserAndAdmin> updateAdminUser(@RequestBody UserAndAdmin userAndAdmin) {
		System.out.println(userAndAdmin);
		try {
			UserAndAdmin updatedAdmin = service.updateAdminUser(userAndAdmin);
			if (updatedAdmin != null) {
				return ResponseEntity.ok(updatedAdmin);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
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
			UserAndAdmin updatedAdmin = service.selectAdmin(adminNo);
			if (updatedAdmin != null) {
				return ResponseEntity.ok(updatedAdmin);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	// -------- For Student
	@PostMapping(value = "/insert/student", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserAndStudent> insertStudent(@RequestBody UserAndStudent student) {
		try {
			UserAndStudent addedStudent = service.insertStudent(student);
			if (addedStudent != null) {
				return ResponseEntity.ok(addedStudent);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

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

	// -------- For Course
	@PostMapping(value = "/insert/course", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Course> insertCourse(@RequestBody Course course) {
		try {
			Course addedCourse = service.addCourse(course);
			if (addedCourse != null) {
				return ResponseEntity.ok(addedCourse);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	// -------- For Major
	@PostMapping(value = "/insert/major", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Major> insertMajor(@RequestBody Major major) {
		try {
			Major addedMajor = service.addMajor(major);
			if (addedMajor != null) {
				return ResponseEntity.ok(addedMajor);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	// -------- For Curriculum
	@PostMapping(value = "/insert/curriculum", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Curriculum> insertCurriculum(@RequestBody Curriculum curriculum) {
		try {
			Curriculum addedCurriculum = service.addCurriculum(curriculum);
			if (addedCurriculum != null) {
				return ResponseEntity.ok(addedCurriculum);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

}
