package org.ssglobal.training.codes.service;

import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.ssglobal.training.codes.tables.pojos.AcademicYear;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.StudentApplicant;

public interface StudentApplicantCapabilitiesService {

	StudentApplicant insertStudentApplicant(StudentApplicant studentApplicant) throws DuplicateKeyException, Exception;
	List<Course> selectAllCourses();
	List<Major> selectCourseMajors(Integer courseCode);
	AcademicYear getAvailableAcademicYear();
}
