package org.ssglobal.training.codes.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.training.codes.model.UserAndAdmin;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.service.StudentApplicantCapabilitiesService;
import org.ssglobal.training.codes.service.StudentCapabilitiesService;
import org.ssglobal.training.codes.tables.pojos.StudentApplicant;

@RestController
@RequestMapping(value = "/api/studentapplicant")
public class StudentApplicantCapabilitiesController {


	@Autowired
	private StudentApplicantCapabilitiesService service;
	
	@PostMapping(value = "/insert", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<StudentApplicant> createUser(@RequestBody StudentApplicant studentApplicant) {
		try {
			studentApplicant.setDateApplied(LocalDateTime.now());
			StudentApplicant addedApplicant = service.insertStudentApplicant(studentApplicant);
			if (addedApplicant != null) {
				return ResponseEntity.ok(addedApplicant);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}
}	
