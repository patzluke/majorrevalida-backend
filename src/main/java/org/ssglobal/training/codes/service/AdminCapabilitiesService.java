package org.ssglobal.training.codes.service;

import java.util.List;

import org.ssglobal.training.codes.model.UserAndAdmin;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.AcademicYear;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.Curriculum;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.Program;

public interface AdminCapabilitiesService {

	UserAndAdmin insertAdminUser(UserAndAdmin userAdmin);

	UserAndAdmin updateAdminUser(UserAndAdmin userAdmin);

	UserAndAdmin deleteAdminUser(Integer userAdminId);

	List<UserAndAdmin> selectAllAdmin();

	UserAndAdmin selectAdmin(Integer adminNo);

	UserAndStudent insertStudent(UserAndStudent student);

	AcademicYear addAcademicYear(AcademicYear academicYear);

	Program addProgram(Program program);

	Course addCourse(Course course);

	Major addMajor(Major major);

	Curriculum addCurriculum(Curriculum curriculum);

}
