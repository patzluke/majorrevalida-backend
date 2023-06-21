//	package org.ssglobal.training.codes.controller;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import org.jooq.Row15;
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
//import org.ssglobal.training.codes.service.StudentService;
//import org.ssglobal.training.codes.tables.pojos.Student;
//
//@RestController
//@RequestMapping(value = "/api/student")
//public class StudentController {
//
//	@Autowired
//	private StudentService studentService;
//
//	@GetMapping(value = "/get")
//	public ResponseEntity<List<Student>> getStudent() {
//		List<Student> studentList = studentService.selectAllStudent();
//		return !studentList.isEmpty() ? new ResponseEntity<>(studentList, HttpStatus.OK)
//				: ResponseEntity.notFound().build();
//	}
//
//	@PostMapping(value = "/insert", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
//			MediaType.APPLICATION_JSON_VALUE })
//	public ResponseEntity<Student> createUser(@RequestBody Student student) {
//		try {
//			Row15<Integer, Integer, Integer, String, String, String, String, LocalDate, Integer, Integer, String, String, Boolean, String, String> addedStudent = studentService
//					.insertStudent(student);
//			if (addedStudent != null) {
//				Student newStudent = new Student(Integer.parseInt(addedStudent.field(0).getName()),
//						Integer.parseInt(addedStudent.field(1).getName()),
//						Integer.parseInt(addedStudent.field(2).getName()), addedStudent.field(3).getName(),
//						addedStudent.field(4).getName(), addedStudent.field(5).getName(),
//						addedStudent.field(6).getName(), LocalDate.parse(addedStudent.field(7).getName()),
//						Integer.parseInt(addedStudent.field(8).getName()),
//						Integer.parseInt(addedStudent.field(9).getName()), addedStudent.field(10).getName(),
//						addedStudent.field(11).getName(), Boolean.getBoolean(addedStudent.field(12).getName()),
//						addedStudent.field(13).getName(), addedStudent.field(14).getName());
//				return ResponseEntity.ok(newStudent);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().build();
//		}
//		return ResponseEntity.internalServerError().build();
//	}
//
//	@PutMapping(value = "/update", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
//			MediaType.APPLICATION_JSON_VALUE })
//	public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
//		try {
//			Row15<Integer, Integer, Integer, String, String, String, String, LocalDate, Integer, Integer, String, String, Boolean, String, String> addedStudent = studentService
//					.updateStudent(student);
//			if (addedStudent != null) {
//				Student newStudent = new Student(Integer.parseInt(addedStudent.field(0).getName()),
//						Integer.parseInt(addedStudent.field(1).getName()),
//						Integer.parseInt(addedStudent.field(2).getName()), addedStudent.field(3).getName(),
//						addedStudent.field(4).getName(), addedStudent.field(5).getName(),
//						addedStudent.field(6).getName(), LocalDate.parse(addedStudent.field(7).getName()),
//						Integer.parseInt(addedStudent.field(8).getName()),
//						Integer.parseInt(addedStudent.field(9).getName()), addedStudent.field(10).getName(),
//						addedStudent.field(11).getName(), Boolean.getBoolean(addedStudent.field(12).getName()),
//						addedStudent.field(13).getName(), addedStudent.field(14).getName());
//				return ResponseEntity.ok(newStudent);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().build();
//		}
//		return ResponseEntity.internalServerError().build();
//	}
//
//	@DeleteMapping(value = "/delete/{studentId}", produces = { MediaType.APPLICATION_JSON_VALUE })
//	public ResponseEntity<Student> deleteStudent(@PathVariable(name = "studentId") Integer studentId) {
//		try {
//			Row15<Integer, Integer, Integer, String, String, String, String, LocalDate, Integer, Integer, String, String, Boolean, String, String> addedStudent = studentService
//					.deleteStudent(studentId);
//			if (addedStudent != null) {
//				Student newStudent = new Student(Integer.parseInt(addedStudent.field(0).getName()),
//						Integer.parseInt(addedStudent.field(1).getName()),
//						Integer.parseInt(addedStudent.field(2).getName()), addedStudent.field(3).getName(),
//						addedStudent.field(4).getName(), addedStudent.field(5).getName(),
//						addedStudent.field(6).getName(), LocalDate.parse(addedStudent.field(7).getName()),
//						Integer.parseInt(addedStudent.field(8).getName()),
//						Integer.parseInt(addedStudent.field(9).getName()), addedStudent.field(10).getName(),
//						addedStudent.field(11).getName(), Boolean.getBoolean(addedStudent.field(12).getName()),
//						addedStudent.field(13).getName(), addedStudent.field(14).getName());
//				return ResponseEntity.ok(newStudent);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().build();
//		}
//		return ResponseEntity.internalServerError().build();
//	}
//}
