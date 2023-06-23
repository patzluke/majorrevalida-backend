package org.ssglobal.training.codes.service;

import java.util.List;

import org.ssglobal.training.codes.model.UserAndAdmin;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.StudentApplicant;

public interface AdminCapabilitiesService {

	UserAndAdmin insertAdminUser(UserAndAdmin userAdmin);

	UserAndAdmin updateAdminUser(UserAndAdmin userAdmin);

	UserAndAdmin deleteAdminUser(Integer userAdminId);

	List<UserAndAdmin> selectAllAdmin();

	UserAndAdmin selectAdmin(Integer adminNo);

	UserAndStudent insertStudent(UserAndStudent student);
	
	List<StudentApplicant> selectAllStudentApplicants();
}
