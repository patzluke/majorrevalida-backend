//
//package org.ssglobal.training.codes.controller;
//
//import java.util.HashMap;
//import java.util.List;
//
//import org.jooq.Row6;
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
//import org.ssglobal.training.codes.service.AdminUserService;
//import org.ssglobal.training.codes.tables.pojos.AdminUser;
//
//@RestController
//@RequestMapping(value = "/api/adminuser")
//public class AdminUserController {
//
//	@Autowired
//	private AdminUserService adminUserService;
//
//	@GetMapping(value = "/get")
//	public ResponseEntity<List<AdminUser>> selectCartByUser() {
//		List<AdminUser> cartList = adminUserService.selectAllAdmin();
//		return !cartList.isEmpty() ? new ResponseEntity<>(cartList, HttpStatus.OK) : ResponseEntity.notFound().build();
//	}
//	
//	@PostMapping(value = "/insert", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public ResponseEntity<AdminUser> createUser(@RequestBody AdminUser adminUser) {
//		try {
//			Row6<Integer, String, String, String, String, String> addedAdmin = adminUserService.insertAdminUser(adminUser);
//			if (addedAdmin != null) {
//				AdminUser newUser = new AdminUser(Integer.parseInt(addedAdmin.field(0).getName()), addedAdmin.field(1).getName(), 
//												    addedAdmin.field(2).getName(), addedAdmin.field(3).getName(), 
//												    addedAdmin.field(4).getName(), addedAdmin.field(5).getName());
//				return ResponseEntity.ok(newUser);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().build();
//		}
//		return ResponseEntity.internalServerError().build();
//	}
//	
//	@PutMapping(value = "/update", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//	public ResponseEntity<AdminUser> updateUser(@RequestBody UserAndStudent adminUser) {
//		try {
//			Row6<Integer, String, String, String, String, String> addedAdmin = adminUserService.updateAdminUser(adminUser);
//			if (addedAdmin != null) {
//				AdminUser newUser = new AdminUser(Integer.parseInt(addedAdmin.field(0).getName()), addedAdmin.field(1).getName(), 
//												    addedAdmin.field(2).getName(), addedAdmin.field(3).getName(), 
//												    addedAdmin.field(4).getName(), addedAdmin.field(5).getName());
//				return ResponseEntity.ok(newUser);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().build();
//		}
//		return ResponseEntity.internalServerError().build();
//	}
//	
//	@DeleteMapping(value = "/delete/{adminUserId}", produces = {MediaType.APPLICATION_JSON_VALUE})
//	public ResponseEntity<AdminUser> deleteAdminUser(@PathVariable(name = "adminUserId") Integer adminUserId) {
//		try {
//			Row6<Integer, String, String, String, String, String> addedAdmin = adminUserService.deleteAdminUser(adminUserId);
//			if (addedAdmin != null) {
//				AdminUser newUser = new AdminUser(Integer.parseInt(addedAdmin.field(0).getName()), addedAdmin.field(1).getName(), 
//												    addedAdmin.field(2).getName(), addedAdmin.field(3).getName(), 
//												    addedAdmin.field(4).getName(), addedAdmin.field(5).getName());
//				return ResponseEntity.ok(newUser);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().build();
//		}
//		return ResponseEntity.internalServerError().build();
//	}
//}