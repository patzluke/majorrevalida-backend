package org.ssglobal.training.codes.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.training.codes.model.EmailDetails;
import org.ssglobal.training.codes.service.StudentApplicantCapabilitiesService;
import org.ssglobal.training.codes.service.Impl.EmailServiceImpl;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.StudentApplicant;

@RestController
@RequestMapping(value = "/api/studentapplicant")
public class StudentApplicantCapabilitiesController {


	@Autowired
	private StudentApplicantCapabilitiesService service;
	
	@Autowired
	private EmailServiceImpl emailService;
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/insert", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity createUser(@RequestBody StudentApplicant studentApplicant) {
		StudentApplicant addedApplicant;
		try {
			studentApplicant.setDateApplied(LocalDateTime.now());
			addedApplicant = service.insertStudentApplicant(studentApplicant);
			if (addedApplicant != null) {
				EmailDetails emailDetails = new EmailDetails();
				emailDetails.setRecipient(studentApplicant.getEmail());
				emailDetails.setSubject("Colegio De Seven Seven Applicaiton!");
				emailDetails.setMsgBody("Congratulations! You are now just a few steps behind to enroll into our College Program\n"
									  + "To proceed with your application. Please follow the instructions carefully on attached pdf below\n"
									  + "Goodluck!");
				emailService.sendMailWithAttachment(emailDetails);
				return ResponseEntity.ok(addedApplicant);
			}
		} catch (DuplicateKeyException e1) {
			e1.printStackTrace();
			return ResponseEntity.badRequest().body(e1.getMessage());
		} catch (Exception e1) {
			e1.printStackTrace();
			return ResponseEntity.badRequest().body("something went wrong");
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@GetMapping(value = "/get/course")
	public ResponseEntity<List<Course>> selectAllCourses() {
		try {
			List<Course> courses = service.selectAllCourses();
			if (courses != null) {
				return ResponseEntity.ok(courses);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping(value = "/get/major/{courseCode}")
	public ResponseEntity<List<Major>> selectCourseMajors(@PathVariable(name = "courseCode") Integer courseCode) {
		try {
			List<Major> courseMajors = service.selectCourseMajors(courseCode);
			if (courseMajors != null) {
				return ResponseEntity.ok(courseMajors);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}
}	
