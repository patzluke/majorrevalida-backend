//package org.ssglobal.training.codes.controller;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import org.jooq.Row6;
//import org.jooq.Row9;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.ssglobal.training.codes.service.ProfessorService;
//import org.ssglobal.training.codes.service.SubjectService;
//import org.ssglobal.training.codes.tables.pojos.Professor;
//import org.ssglobal.training.codes.tables.pojos.Subject;
//
//import lombok.extern.log4j.Log4j2;
//
//@RestController
//@RequestMapping(value = "/api/professor")
//@Log4j2
//public class ProfessorController {
//
//	@Autowired
//	private ProfessorService professorService;
//
//	@GetMapping(value = "/get")
//	public ResponseEntity<List<Professor>> selectAllProfessor() {
//		List<Professor> cartList = professorService.selectAllProfessor();
//		return !cartList.isEmpty() ? new ResponseEntity<>(cartList, HttpStatus.OK) : ResponseEntity.notFound().build();
//	}
//	
//	@PostMapping(value = "/insert", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public ResponseEntity<Professor> createSubject(@RequestBody Professor professor) {
//		try {
//			Row9<Integer, Integer, String, String, String, String, String, LocalDate, Boolean> addedProfessor = professorService.insertProfessor(professor);
//			if (addedProfessor != null) {
//				Professor newProfessor = new Professor(Integer.parseInt(addedProfessor.field(0).getName()), Integer.parseInt(addedProfessor.field(1).getName()),
//											     addedProfessor.field(2).getName(), addedProfessor.field(3).getName(), addedProfessor.field(4).getName(),
//											     addedProfessor.field(5).getName(), addedProfessor.field(6).getName(),
//												 LocalDate.parse(addedProfessor.field(7).getName()), Boolean.parseBoolean(addedProfessor.field(8).getName()));
//				return ResponseEntity.ok(newProfessor);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().build();
//		}
//		return ResponseEntity.internalServerError().build();
//	}
//	
//	@PutMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public ResponseEntity<Professor> updateProfessor(@RequestBody Professor professor) {
//		try {
//			Row9<Integer, Integer, String, String, String, String, String, LocalDate, Boolean> updatedProfessor = professorService.updateProfessor(professor);
//			if (updatedProfessor != null) {
//				Professor newUpdatedProfessor = new Professor(Integer.parseInt(updatedProfessor.field(0).getName()), Integer.parseInt(updatedProfessor.field(1).getName()),
//																updatedProfessor.field(2).getName(), updatedProfessor.field(3).getName(), updatedProfessor.field(4).getName(),
//																updatedProfessor.field(5).getName(), updatedProfessor.field(6).getName(),
//																LocalDate.parse(updatedProfessor.field(7).getName()), Boolean.parseBoolean(updatedProfessor.field(8).getName()));
//				return ResponseEntity.ok(newUpdatedProfessor);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().build();
//		}
//		return ResponseEntity.internalServerError().build();
//	}
//	
//	@DeleteMapping(value = "/delete/{professorId}", produces = {MediaType.APPLICATION_JSON_VALUE})
//	public ResponseEntity<Professor> deleteProfessorById(@PathVariable(name = "professorId") Integer professorId) {
//		try {
//			Row9<Integer, Integer, String, String, String, String, String, LocalDate, Boolean> deletedProfessor = professorService.deleteProfessorById(professorId);
//			if (deletedProfessor != null) {
//				Professor newUpdatedProfessor = new Professor(Integer.parseInt(deletedProfessor.field(0).getName()), Integer.parseInt(deletedProfessor.field(1).getName()),
//																deletedProfessor.field(2).getName(), deletedProfessor.field(3).getName(), deletedProfessor.field(4).getName(),
//																deletedProfessor.field(5).getName(), deletedProfessor.field(6).getName(),
//																LocalDate.parse(deletedProfessor.field(7).getName()), Boolean.parseBoolean(deletedProfessor.field(8).getName()));
//				return ResponseEntity.ok(newUpdatedProfessor);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().build();
//		}
//		return ResponseEntity.internalServerError().build();
//	}
//}