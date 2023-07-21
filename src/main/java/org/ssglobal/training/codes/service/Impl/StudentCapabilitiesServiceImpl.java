package org.ssglobal.training.codes.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.model.StudentCourseData;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.repository.StudentCapabilitiesRepository;
import org.ssglobal.training.codes.service.StudentCapabilitiesService;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.Grades;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.Program;
import org.ssglobal.training.codes.tables.pojos.StudentAttendance;
import org.ssglobal.training.codes.tables.pojos.Users;

@Service
public class StudentCapabilitiesServiceImpl implements StudentCapabilitiesService {

	@Autowired
	private StudentCapabilitiesRepository repository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public List<Users> selectAllUsers() {
		return repository.selectAllUsers();
	}

	@Override
	public UserAndStudent viewStudentProfile(Integer studentId) {
		return repository.viewStudentProfile(studentId);
	}

	@Override
	public UserAndStudent updateStudentProfile(UserAndStudent student) {
		selectAllUsers().forEach(user -> {
			if (!user.getUserId().equals(student.getUserId())) {
				if (user.getUsername().equals(student.getUsername())) {
					throw new DuplicateKeyException("username already exists");
				}
				if (user.getEmail().equals(student.getEmail())) {
					throw new DuplicateKeyException("email already exists");
				}
				
			}
		});
		
		if (!student.getPassword().isEmpty()) {
			student.setPassword(encoder.encode(student.getPassword()));
		}
		return repository.updateStudentProfile(student);
	}

	@Override
	public Grades viewStudentGrade(Integer studentId) {
		return repository.viewStudentGrade(studentId);
	}

	@Override
	public StudentCourseData viewCourse(Integer studentNo) {
		return repository.viewCourse(studentNo);
	}

	@Override
	public Major editMajor(Major editedMajor) {
		return repository.editMajor(editedMajor);
	}

	@Override
	public Course editCourse(Course course) {
		return repository.editCourse(course);
	}

	@Override
	public Program editProgram(Program program) {
		return repository.editProgram(program);
	}

	@Override
	public StudentAttendance viewStudentAttendance(Integer studentNo) {
		return repository.viewStudentAttendance(studentNo);
	}
	
	@Override
	public List<Map<String, Object>> selectAllStudentSubjectEnrolledByStudentNo(Integer studentNo) {
		return repository.selectAllStudentSubjectEnrolledByStudentNo(studentNo);
	}
	
	@Override
	public List<Map<String, Object>> selectStudentAttendanceByAndSubjectAndStudentNo(
			String subjectTitle, Integer studentNo) {
		return repository.selectStudentAttendanceByAndSubjectAndStudentNo(subjectTitle, studentNo);
	}
	
	@Override
	public List<Map<String, Object>> selectAllSubjectGradesOfStudent(Integer studentNo) {
		return repository.selectAllSubjectGradesOfStudent(studentNo);
	}
}
