package org.ssglobal.training.codes.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.service.ProfessorCapabilitiesService;
import org.ssglobal.training.codes.tables.pojos.Grades;
import org.ssglobal.training.codes.tables.pojos.ProfessorLoad;
import org.ssglobal.training.codes.tables.pojos.StudentAttendance;

@RestController
@RequestMapping(value = "/api/professor")
public class ProfessorCapabilitiesController {

	@Autowired
	private ProfessorCapabilitiesService service;
	
	@GetMapping(value = "/get/{professorNo}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserAndProfessor> selectProfessor(@PathVariable(name = "professorNo") Integer professorNo) {
		try {
			UserAndProfessor selectedProfessor = service.selectProfessor(professorNo);
			if (selectedProfessor != null) {
				return ResponseEntity.ok(selectedProfessor);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity updateProfessor(@RequestBody UserAndProfessor userAndProfessor) {
		try {
			UserAndProfessor updatedProfessor = service.updateProfessor(userAndProfessor);
			if (updatedProfessor != null) {
				return ResponseEntity.ok(updatedProfessor);
			}
		} catch (DuplicateKeyException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.badRequest().body("something went wrong");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@GetMapping(value = "/get/loads/{professorNo}")
	public ResponseEntity<List<ProfessorLoad>> selecAllLoad(@PathVariable(name = "professorNo") Integer professorNo) {
		try {
			List<ProfessorLoad> selectedLoads = service.selectAllLoad(professorNo);
			if (!selectedLoads.isEmpty()) {
				return ResponseEntity.ok(selectedLoads);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping(value = "/get/loads/subject/{professorNo}")
	public ResponseEntity<List<Map<String, Object>>> selecAllLoads(@PathVariable(name = "professorNo") Integer professorNo) {
		try {
			List<Map<String, Object>> selectedLoads = service.selectAllLoads(professorNo);
			if (selectedLoads != null) {
				return ResponseEntity.ok(selectedLoads);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping(value = "/get/section/subject/{professorNo}")
	public ResponseEntity<List<Map<String, Object>>> selectProfessorLoadByProfessorNoAndSubjectCodeAndSection(@RequestParam(name = "professorNo") Integer professorNo,
																											  @RequestParam(name = "subjectCode") Integer subjectCode,
																											  @RequestParam(name = "sectionName") String sectionName) {
		try {
			List<Map<String, Object>> selectedLoads = service.selectProfessorLoadByProfessorNoAndSubjectCodeAndSection(professorNo, subjectCode, sectionName);
			if (selectedLoads != null) {
				return ResponseEntity.ok(selectedLoads);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping(value = "/get/student")
	public ResponseEntity<List<Map<String, Object>>> selectAllStudentsBySection() {
		try {
			List<Map<String, Object>> students = service.selectAllStudentsBySection();
			if (students != null) {
				return ResponseEntity.ok(students);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@GetMapping(value = "/get/studentattendance")
	public ResponseEntity<List<Map<String, Object>>> selectStudentAttendanceByStudentNoAndSubjectAndSectionAndProfessorNo(@RequestParam(name = "subjectTitle") String subjectTitle,
																			 											@RequestParam(name = "sectionName") String sectionName,
																			 											@RequestParam(name = "professorNo") Integer professorNo,
																			 											@RequestParam(name = "date") String date) {
		try {
			List<Map<String, Object>> students = 
					service.selectStudentAttendanceByStudentNoAndSubjectAndSectionAndProfessorNo(subjectTitle, sectionName, professorNo, date);
			if (students != null) {
				return ResponseEntity.ok(students);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@GetMapping(value = "/get/studentattendance/attendancedate")
	public ResponseEntity<List<StudentAttendance>> selectStudentAttendanceByAttendanceDateDistinct() {
		try {
			List<StudentAttendance> students = service.selectStudentAttendanceByAttendanceDateDistinct();
			if (students != null) {
				return ResponseEntity.ok(students);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/update/studentattendance", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity updateStudentAttendance(@RequestBody Map<String, Object> payload) {
		Map<String, Object> updatedStudentAttendance;
		try {
			updatedStudentAttendance = service.updateStudentAttendance(payload);
			if (!updatedStudentAttendance.isEmpty()) {
				return ResponseEntity.ok(updatedStudentAttendance);
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
	@PutMapping(value = "/update/studentgrades", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity updateStudentGrades(@RequestBody List<Grades> payload) {
		List<Grades> updatedStudentGrades;
		updatedStudentGrades = service.updateStudentGrades(payload);
		try {
			if (!updatedStudentGrades.isEmpty()) {
				return ResponseEntity.ok(updatedStudentGrades);
			}
		} catch (DuplicateKeyException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("something went wrong");
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/update/studentgrades/submit", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity updateStudentGradesIsSubmitted(@RequestBody List<Grades> payload) {
		List<Grades> updatedStudentGrades;
		updatedStudentGrades = service.updateStudentGradesIsSubmitted(payload);
		try {
			if (!updatedStudentGrades.isEmpty()) {
				return ResponseEntity.ok(updatedStudentGrades);
			}
		} catch (DuplicateKeyException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("something went wrong");
		}
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
}	
