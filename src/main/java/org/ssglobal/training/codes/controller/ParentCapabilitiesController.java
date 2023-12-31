package org.ssglobal.training.codes.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.training.codes.model.ParentStudentAttendanceCopy;
import org.ssglobal.training.codes.model.StudentGrades;
import org.ssglobal.training.codes.model.UserAndParent;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.service.ParentCapabilitiesService;

@RestController
@RequestMapping(value = "/api/parent")
public class ParentCapabilitiesController {

	@Autowired
	private ParentCapabilitiesService service;

	@GetMapping(value = "/get/{parentNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserAndParent> selectParent(@PathVariable(name = "parentNo") Integer parentNo) {
		try {
			UserAndParent parentInfo = service.selectParent(parentNo);
			if (parentInfo != null) {
				return ResponseEntity.ok(parentInfo);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping(value = "/update", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserAndParent> updateParent(@RequestBody UserAndParent userAndParent) {
		try {
			UserAndParent updatedParentInfo = service.updateParentInfo(userAndParent);
			if (updatedParentInfo != null) {
				return ResponseEntity.ok(updatedParentInfo);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping(value = "/get/children/{parentNo}")
	public ResponseEntity<List<UserAndStudent>> selectAllChildren(@PathVariable(name = "parentNo") Integer parentNo) {
		try {
			List<UserAndStudent> listOfChild = service.selectAllChildren(parentNo);
			if (listOfChild != null) {
				return ResponseEntity.ok(listOfChild);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	// -------- For Student grades
	@GetMapping(value = "/get/academicyear/{studentNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Map<String, Object>>> selectEnrolledSchoolYearOfStudent(
			@PathVariable(name = "studentNo") Integer studentNo) {
		try {
			List<Map<String, Object>> rooms = service.selectEnrolledSchoolYearOfStudent(studentNo);
			if (!rooms.isEmpty()) {
				return ResponseEntity.ok(rooms);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping(value = "/get/grades/{studentNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<StudentGrades>> selectAllGradesByStudentNo(
			@PathVariable(name = "studentNo") Integer studentNo) {
		try {
			List<StudentGrades> gradesOfStudent = service.selectAllGrades(studentNo);
			if (gradesOfStudent != null) {
				return ResponseEntity.ok(gradesOfStudent);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping(value = "/get/studentattendance/attendancedate/{studentNo}")
	public ResponseEntity<List<ParentStudentAttendanceCopy>> selectStudentAttendanceByAttendanceDateDistinct(
			@PathVariable(name = "studentNo") Integer studentNo) {
		try {
			List<ParentStudentAttendanceCopy> students = service.selectStudentAttendanceByAttendanceDateDistinct(studentNo);
			if (students != null) {
				return ResponseEntity.ok(students);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
}
