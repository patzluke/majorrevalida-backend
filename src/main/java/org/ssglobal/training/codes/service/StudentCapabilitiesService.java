package org.ssglobal.training.codes.service;

import org.ssglobal.training.codes.model.StudentCourseData;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.Grades;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.Program;
import org.ssglobal.training.codes.tables.pojos.StudentAttendance;

public interface StudentCapabilitiesService {

	UserAndStudent viewStudentProfile(Integer studentId);

	UserAndStudent updateStudentProfile(UserAndStudent student, Integer studentId);

	Grades viewStudentGrade(Integer studentId);

	StudentCourseData viewCourse(Integer studentNo);

	Major editMajor(Major editedMajor);

	Course editCourse(Course course);

	Program editProgram(Program program);

	StudentAttendance viewStudentAttendance(Integer studentNo);

}
