package org.ssglobal.training.codes.service.Impl;

import java.time.LocalDate;
import java.util.List;

import org.jooq.Row15;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repository.StudentRepository;
import org.ssglobal.training.codes.service.StudentService;
import org.ssglobal.training.codes.tables.pojos.Student;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository repository;

	@Override
	public List<Student> selectAllStudent() {
		return repository.selectAllAdmin();
	}

	@Override
	public Row15<Integer, Integer, Integer, String, String, String, String, LocalDate, Integer, Integer, String, String, Boolean, String, String> insertStudent(
			Student student) {
		return repository.insertStudent(student);
	}

	@Override
	public Row15<Integer, Integer, Integer, String, String, String, String, LocalDate, Integer, Integer, String, String, Boolean, String, String> updateStudent(
			Student student) {
		return repository.updateStudent(student);
	}

	@Override
	public Row15<Integer, Integer, Integer, String, String, String, String, LocalDate, Integer, Integer, String, String, Boolean, String, String> deleteStudent(
			Integer studentId) {
		return repository.deleteStudent(studentId);
	}

}
