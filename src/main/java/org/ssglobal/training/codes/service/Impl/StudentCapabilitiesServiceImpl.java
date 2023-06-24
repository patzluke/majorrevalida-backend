package org.ssglobal.training.codes.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class StudentCapabilitiesServiceImpl implements StudentCapabilitiesService {

	@Autowired
	private StudentCapabilitiesRepository repository;

	@Override
	public UserAndStudent viewStudentProfile(Integer studentId) {
		return repository.viewStudentProfile(studentId);
	}

	@Override
	public UserAndStudent updateStudentProfile(UserAndStudent student, Integer studentId) {
		return repository.updateStudentProfile(student, studentId);
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

}
