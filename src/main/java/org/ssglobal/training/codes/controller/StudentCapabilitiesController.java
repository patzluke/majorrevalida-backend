package org.ssglobal.training.codes.controller;

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
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.training.codes.model.StudentCourseData;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.service.StudentCapabilitiesService;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.Grades;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.Program;
import org.ssglobal.training.codes.tables.pojos.StudentAttendance;

@RestController
@RequestMapping(value = "/api/student")
public class StudentCapabilitiesController {

	@Autowired
	private StudentCapabilitiesService service;

	@GetMapping(value = "/get/{studentId}")
	public ResponseEntity<UserAndStudent> viewStudentProfile(@PathVariable("studentId") Integer studentId) {
		try {
			UserAndStudent studentProfile = service.viewStudentProfile(studentId);
			if (studentProfile != null) {
				return ResponseEntity.ok(studentProfile);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@SuppressWarnings("rawtypes")
	@PutMapping(value = "/update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity updateStudentProfile(@RequestBody UserAndStudent student) {
		try {
			UserAndStudent updatedStudent = service.updateStudentProfile(student);
			if (updatedStudent != null) {
				return ResponseEntity.ok(updatedStudent);
			}
		} catch (DuplicateKeyException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.badRequest().body("something went wrong");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@GetMapping(value = "/view/course/{studentId}")
	public ResponseEntity<StudentCourseData> viewStudentCourse(@PathVariable("studentId") Integer studentNo) {
		try {
			StudentCourseData studentCourse = service.viewCourse(studentNo);
			if (studentCourse != null) {
				return ResponseEntity.ok(studentCourse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping(value = "/edit/major", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Major> editMajor(@RequestBody Major editedMajor) {
		try {
			Major updatedMajor = service.editMajor(editedMajor);
			if (updatedMajor != null) {
				return ResponseEntity.ok(updatedMajor);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping(value = "/edit/course", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Course> editCourse(@RequestBody Course editedMajor) {
		try {
			Course updatedCourse = service.editCourse(editedMajor);
			if (updatedCourse != null) {
				return ResponseEntity.ok(updatedCourse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping(value = "/edit/program", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Program> editMajor(@RequestBody Program editedProgram) {
		try {
			Program updatedProgram = service.editProgram(editedProgram);
			if (updatedProgram != null) {
				return ResponseEntity.ok(updatedProgram);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping(value = "/view/attendance/{studentId}")
	public ResponseEntity<StudentAttendance> viewStudentAttendance(@PathVariable("studentId") Integer studentNo) {
		try {
			StudentAttendance studentAttendance = service.viewStudentAttendance(studentNo);
			if (studentAttendance != null) {
				return ResponseEntity.ok(studentAttendance);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

	@GetMapping(value = "/view/grades/{studentId}")
	public ResponseEntity<Grades> viewStudentGrades(@PathVariable("studentId") Integer studentId) {
		try {
			Grades studentGrades = service.viewStudentGrade(studentId);
			if (studentGrades != null) {
				return ResponseEntity.ok(studentGrades);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}

}
