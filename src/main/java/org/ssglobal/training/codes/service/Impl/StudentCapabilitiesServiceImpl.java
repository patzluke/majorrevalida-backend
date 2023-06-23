package org.ssglobal.training.codes.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.repository.StudentCapabilitiesRepository;
import org.ssglobal.training.codes.service.StudentCapabilitiesService;

@Service
public class StudentCapabilitiesServiceImpl implements StudentCapabilitiesService {

	@Autowired
	private StudentCapabilitiesRepository repository;

	@Override
	public UserAndStudent viewStudentProfile(Integer studentId) {
		return repository.viewStudentProfile(studentId);
	}

	@Override
	public UserAndStudent updateStudent(UserAndStudent student, Integer studentId) {
		return repository.updateStudent(student, studentId);
	}

}
