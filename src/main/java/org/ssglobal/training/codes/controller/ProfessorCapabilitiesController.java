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
import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.service.ProfessorCapabilitiesService;

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
	
	@PutMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserAndProfessor> updateProfessor(@RequestBody UserAndProfessor userAndProfessor) {
		try {
			UserAndProfessor updatedProfessor = service.updateProfessor(userAndProfessor);
			if (updatedProfessor != null) {
				return ResponseEntity.ok(updatedProfessor);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}
}	
