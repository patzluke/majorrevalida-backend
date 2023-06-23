package org.ssglobal.training.codes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.training.codes.model.UserAndParent;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.service.ParentCapabilitiesService;
import org.ssglobal.training.codes.tables.pojos.Grades;

@RestController
@RequestMapping(value = "/api/parent")
public class ParentCapabilitiesController {
	
	@Autowired
	private ParentCapabilitiesService service;
	
	@GetMapping(value = "/get/parent/{parentNo}")
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
	
	@PostMapping
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
	
	@GetMapping(value = "/get/grades/{studentNo}")
	public ResponseEntity<List<Grades>> selectAllGradesByStudentNo(@PathVariable(name = "studentNo") Integer studentNo) {
		try {
			List<Grades> gradesOfStudent = service.selectAllGrades(studentNo);
			if (gradesOfStudent != null) {
				return ResponseEntity.ok(gradesOfStudent);
			}
		} catch (Exception e) {
			System.out.println("%s".formatted(e));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.badRequest().build();
	}
}
