package org.ssglobal.training.codes.controller;

import java.util.List;

import org.jooq.Row5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ssglobal.training.codes.service.ParentService;
import org.ssglobal.training.codes.tables.pojos.Parent;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "/api/parent")
@Log4j2
public class ParentController {
	
	@Autowired
	private ParentService  parentService;
	
	@GetMapping(value = "/get")
	public ResponseEntity<List<Parent>> selectParent() {
		List<Parent> parentList = parentService.selectAllParent();
		return !parentList.isEmpty() ? new ResponseEntity<>(parentList, HttpStatus.OK) : ResponseEntity.notFound().build();
	}
	
	@PostMapping(value = "/insert", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Parent> createParent(@RequestBody Parent parent) {
		try {
			Row5<Integer, Integer, String, String, String> addParent = parentService.insertParent(parent);
			if (addParent != null) {
				Parent newParent = new Parent(Integer.parseInt(addParent.field(0).getName()), Integer.parseInt(addParent.field(1).getName()),
												addParent.field(2).getName(), addParent.field(3).getName(), addParent.field(4).getName());
				return ResponseEntity.ok(newParent);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.internalServerError().build();
	}
	
	@PutMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Parent> updateParent(@RequestBody Parent parent) {
		try {
			Row5<Integer, Integer, String, String, String> addParent = parentService.updateParent(parent);
			if (addParent != null) {
				Parent newParent = new Parent(Integer.parseInt(addParent.field(0).getName()), Integer.parseInt(addParent.field(1).getName()),
												addParent.field(2).getName(), addParent.field(3).getName(), addParent.field(4).getName());
				return ResponseEntity.ok(newParent);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.internalServerError().build();
	}
}
