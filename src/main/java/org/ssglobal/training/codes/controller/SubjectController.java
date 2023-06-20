package org.ssglobal.training.codes.controller;

import java.util.List;

import org.jooq.Row6;
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
import org.ssglobal.training.codes.service.SubjectService;
import org.ssglobal.training.codes.tables.pojos.Subject;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/api/subject")
@Log4j2
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	@GetMapping(value = "/get")
	public ResponseEntity<List<Subject>> selectCartByUser() {
		List<Subject> cartList = subjectService.selectAllSubject();
		return !cartList.isEmpty() ? new ResponseEntity<>(cartList, HttpStatus.OK) : ResponseEntity.notFound().build();
	}
	
	@PostMapping(value = "/insert", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {
		try {
			Row6<Integer, Integer, String, Integer, String, Boolean> addedSubject = subjectService.insertSubject(subject);
			if (addedSubject != null) {
				Subject newSubject = new Subject(Integer.parseInt(addedSubject.field(0).getName()), Integer.parseInt(addedSubject.field(1).getName()), 
												 addedSubject.field(2).getName(), Integer.parseInt(addedSubject.field(3).getName()),
												 addedSubject.field(4).getName(), Boolean.parseBoolean(addedSubject.field(5).getName()));
				return ResponseEntity.ok(newSubject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.internalServerError().build();
	}
	
	@PutMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Subject> updateSubject(@RequestBody Subject subject) {
		try {
			Row6<Integer, Integer, String, Integer, String, Boolean> updatedSubject = subjectService.updateSubject(subject);
			if (updatedSubject != null) {
				Subject newSubject = new Subject(Integer.parseInt(updatedSubject.field(0).getName()), Integer.parseInt(updatedSubject.field(1).getName()), 
												 updatedSubject.field(2).getName(), Integer.parseInt(updatedSubject.field(3).getName()),
												 updatedSubject.field(4).getName(), Boolean.parseBoolean(updatedSubject.field(5).getName()));
				return ResponseEntity.ok(newSubject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.internalServerError().build();
	}
	
	@DeleteMapping(value = "/delete/{subjectId}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Subject> deleteSubject(@PathVariable(name = "subjectId") Integer subjectId) {
		try {
			Row6<Integer, Integer, String, Integer, String, Boolean> deletedSubject = subjectService.deleteSubjectById(subjectId);
			if (deletedSubject != null) {
				Subject newSubject = new Subject(Integer.parseInt(deletedSubject.field(0).getName()), Integer.parseInt(deletedSubject.field(1).getName()), 
												 deletedSubject.field(2).getName(), Integer.parseInt(deletedSubject.field(3).getName()),
												 deletedSubject.field(4).getName(), Boolean.parseBoolean(deletedSubject.field(5).getName()));
				return ResponseEntity.ok(newSubject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.internalServerError().build();
	}
}