package org.ssglobal.training.codes.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repository.StudentApplicantCapabilitiesRepository;
import org.ssglobal.training.codes.service.StudentApplicantCapabilitiesService;
import org.ssglobal.training.codes.tables.pojos.StudentApplicant;

@Service
public class StudentApplicantCapabilitiesServiceImpl implements StudentApplicantCapabilitiesService {

	@Autowired
	private StudentApplicantCapabilitiesRepository repository;

	@Override
	public StudentApplicant insertStudentApplicant(StudentApplicant studentApplicant) {
		return repository.insertStudentApplicant(studentApplicant);
	}
}
