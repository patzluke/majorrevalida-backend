package org.ssglobal.training.codes.controller;

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
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.service.StudentCapabilitiesService;

@RestController
@RequestMapping(value = "/api/student")
public class StudentCapabilitiesController {

	@Autowired
	private StudentCapabilitiesService service;

	@GetMapping(value = "/view/{studentId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
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

	@PutMapping(value = "/update/{studentId}", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UserAndStudent> updateStudent(@RequestBody UserAndStudent student,
			@PathVariable("studentId") Integer studentId) {
		try {
			UserAndStudent updatedStudent = service.updateStudent(student, studentId);
			if (updatedStudent != null) {
				return ResponseEntity.ok(updatedStudent);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}
<<<<<<< HEAD
}	
=======

}
>>>>>>> branch 'master' of https://github.com/patzluke/majorrevalida-backend.git