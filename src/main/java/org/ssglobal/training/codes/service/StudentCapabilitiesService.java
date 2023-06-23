package org.ssglobal.training.codes.service;

import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.Grades;

public interface StudentCapabilitiesService {

	UserAndStudent viewStudentProfile(Integer studentId);

	UserAndStudent updateStudentProfile(UserAndStudent student, Integer studentId);
	
	Grades viewStudentGrade(Integer studentId);
}
