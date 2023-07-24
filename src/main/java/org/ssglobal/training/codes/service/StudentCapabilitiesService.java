package org.ssglobal.training.codes.service;

import java.util.List;
import java.util.Map;

import org.ssglobal.training.codes.model.StudentCourseData;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.Grades;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.Program;
import org.ssglobal.training.codes.tables.pojos.StudentAttendance;
import org.ssglobal.training.codes.tables.pojos.Users;

public interface StudentCapabilitiesService {
	
	List<Users> selectAllUsers();

	UserAndStudent viewStudentProfile(Integer studentId);

	UserAndStudent updateStudentProfile(UserAndStudent student);

	Grades viewStudentGrade(Integer studentId);

	StudentCourseData viewCourse(Integer studentNo);

	Major editMajor(Major editedMajor);

	Course editCourse(Course course);

	Program editProgram(Program program);

	StudentAttendance viewStudentAttendance(Integer studentNo);
	
	List<Map<String, Object>> selectAllStudentSubjectEnrolledByStudentNo(Integer studentNo);
	
	List<Map<String, Object>> selectStudentAttendanceByAndSubjectAndStudentNo(String subjectTitle, Integer studentNo);
	
	List<Map<String, Object>> selectAllSubjectGradesOfStudent(Integer studentNo);
	
	List<Map<String, Object>> selectEnrolledSchoolYearOfStudent(Integer studentNo);
}
