package org.ssglobal.training.codes.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repository.AdminCapabilitiesRepository;
import org.ssglobal.training.codes.repository.StudentApplicantCapabilitiesRepository;
import org.ssglobal.training.codes.service.StudentApplicantCapabilitiesService;
import org.ssglobal.training.codes.tables.pojos.AcademicYear;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.StudentApplicant;

@Service
public class StudentApplicantCapabilitiesServiceImpl implements StudentApplicantCapabilitiesService {

	@Autowired
	private StudentApplicantCapabilitiesRepository repository;
	
	@Autowired
	private AdminCapabilitiesRepository adminRepository;

	@Override
	public StudentApplicant insertStudentApplicant(StudentApplicant studentApplicant) throws DuplicateKeyException, Exception {
		adminRepository.selectAllStudentApplicants().forEach(applicant -> {
			if (studentApplicant.getEmail().equals(applicant.getEmail())) {
				throw new DuplicateKeyException("email already exists");
			}
		});
		return repository.insertStudentApplicant(studentApplicant);
	}
	
	@Override
	public List<Course> selectAllCourses() {
		return repository.selectAllCourses();
	}
	
	@Override
	public List<Major> selectCourseMajors(Integer courseCode) {
		return repository.selectCourseMajors(courseCode);
	}
	
	@Override
	public AcademicYear getAvailableAcademicYear() {
		return repository.getAvailableAcademicYear();
	}
}
