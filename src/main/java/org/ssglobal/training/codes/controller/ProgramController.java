//package org.ssglobal.training.codes.controller;
//
//import java.util.List;
//
//import org.jooq.Row4;
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
//import org.ssglobal.training.codes.service.ProgramService;
//import org.ssglobal.training.codes.tables.pojos.Program;
//
//import lombok.extern.log4j.Log4j2;
//
//@RestController
//@RequestMapping(value = "/api/program")
//@Log4j2
//public class ProgramController {
//
//	@Autowired
//	private ProgramService programService;
//
//	@GetMapping(value = "/get")
//	public ResponseEntity<List<Program>> selectCartByUser() {
//		List<Program> cartList = programService.selectAllProgram();
//		return !cartList.isEmpty() ? new ResponseEntity<>(cartList, HttpStatus.OK) : ResponseEntity.notFound().build();
//	}
//	
//	@PostMapping(value = "/insert", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public ResponseEntity<Program> createUser(@RequestBody Program program) {
//		try {
//			Row4<Integer, Integer, String, String> addedAdmin = programService.insertProgram(program);
//			if (addedAdmin != null) {
//				Program newProgram = new Program(Integer.parseInt(addedAdmin.field(0).getName()), Integer.parseInt(addedAdmin.field(1).getName()), 
//												    addedAdmin.field(2).getName(), addedAdmin.field(3).getName());
//				return ResponseEntity.ok(newProgram);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().build();
//		}
//		return ResponseEntity.internalServerError().build();
//	}
//	
//	@PutMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public ResponseEntity<Program> updateUser(@RequestBody Program program) {
//		try {
//			Row4<Integer, Integer, String, String> addedAdmin = programService.updateProgram(program);
//			if (addedAdmin != null) {
//				Program newProgram = new Program(Integer.parseInt(addedAdmin.field(0).getName()), Integer.parseInt(addedAdmin.field(1).getName()), 
//												    addedAdmin.field(2).getName(), addedAdmin.field(3).getName());
//				return ResponseEntity.ok(newProgram);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().build();
//		}
//		return ResponseEntity.internalServerError().build();
//	}
//	
//	@DeleteMapping(value = "/delete/{programId}", produces = {MediaType.APPLICATION_JSON_VALUE})
//	public ResponseEntity<Program> updateUser(@PathVariable(name = "programId") Integer programId) {
//		try {
//			Row4<Integer, Integer, String, String> addedAdmin = programService.deleteProgramById(programId);
//			if (addedAdmin != null) {
//				Program newProgram = new Program(Integer.parseInt(addedAdmin.field(0).getName()), Integer.parseInt(addedAdmin.field(1).getName()), 
//												    addedAdmin.field(2).getName(), addedAdmin.field(3).getName());
//				return ResponseEntity.ok(newProgram);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().build();
//		}
//		return ResponseEntity.internalServerError().build();
//	}
//}