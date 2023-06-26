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
import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.service.ProfessorCapabilitiesService;
import org.ssglobal.training.codes.tables.pojos.ProfessorLoad;

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
	
	@GetMapping(value = "/get/loads/{professorNo}")
	public ResponseEntity<List<ProfessorLoad>> selecAllLoad(Integer professorNo) {
		try {
			List<ProfessorLoad> selectedLoads = service.selectAllLoad(professorNo);
			if (selectedLoads != null) {
				return ResponseEntity.ok(selectedLoads);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping(value = "/get/loads/subject/{professorNo}")
	public ResponseEntity<List<Map<String, Object>>> selecAllLoads(Integer professorNo) {
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
}	
