package org.ssglobal.training.codes.service;

import java.time.LocalDate;
import java.util.List;

import org.jooq.Row15;
import org.ssglobal.training.codes.tables.pojos.Student;

public interface StudentService {

	List<Student> selectAllStudent();

	Row15<Integer, Integer, Integer, String, String, String, String, LocalDate, Integer, Integer, String, String, Boolean, String, String> insertStudent(
			Student student);

	Row15<Integer, Integer, Integer, String, String, String, String, LocalDate, Integer, Integer, String, String, Boolean, String, String> updateStudent(
			Student student);

	Row15<Integer, Integer, Integer, String, String, String, String, LocalDate, Integer, Integer, String, String, Boolean, String, String> deleteStudent(
			Integer studentId);
}
