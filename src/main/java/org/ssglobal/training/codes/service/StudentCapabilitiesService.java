package org.ssglobal.training.codes.service;

import java.util.List;
import java.util.Map;

import org.ssglobal.training.codes.model.StudentCourseData;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.EvaluationQuestionAnswer;
import org.ssglobal.training.codes.tables.pojos.Grades;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.Program;
import org.ssglobal.training.codes.tables.pojos.StudentAttendance;
import org.ssglobal.training.codes.tables.pojos.SubmittedSubjectsForEnrollment;
import org.ssglobal.training.codes.tables.pojos.Users;
import org.ssglobal.training.codes.tables.pojos.WebsiteActivationToggle;

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
	
	List<Map<String, Object>> selectScheduleOfStudent(Integer studentNo, Integer academicYearId);
	
	List<Map<String, Object>> selectAllMajorSubjectsInACurriculumOfStudent(Integer studentNo);
	
	List<Map<String, Object>> selectAllMinorSubjectsInACurriculumOfStudent(Integer studentNo);
	
	Map<String, Object> selectStudentEnrollmentData(Integer studentNo);
	
	List<Map<String, Object>> selectListOfBackLogsMajorSubject(Integer studentNo);
	
	List<Map<String, Object>> selectListOfBackLogsSubjects(Integer studentNo);
	
	List<Map<String, Object>> selectAllMajorSubjectsToEnrollPerYearAndSem(Integer yearLevel, Integer sem, Integer studentNo);
	
	List<Map<String, Object>> selectAllMinorSubjectsToEnrollPerYearAndSem(Integer yearLevel, Integer sem, Integer studentNo);
	
	List<Map<String, Object>> selectAllFailedMajorSubjectPreviouslyOfStudent(Integer studentNo);
	
	List<Map<String, Object>> selectAllFailedMinorSubjectPreviouslyOfStudent(Integer studentNo);
	
	List<Map<String, Object>> selectAllPassedSubjectOfStudent(Integer studentNo);
	
	boolean insertIntoSubmittedSubjectsForEnrollment(List<SubmittedSubjectsForEnrollment> submittedSubjectsForEnrollment);
	
	List<SubmittedSubjectsForEnrollment> checkIfThereIsSubmittedSubjectsForEnrollment(Integer enrollmentId);
	
	List<Map<String, Object>> selectAllEvaluationQuestionAnswerStudentNoAndSubjectCode(Integer studentNo, Integer subjectCode);
	
	boolean updateEvaluationQuestionAnswer(List<EvaluationQuestionAnswer> questionAnswers);
	
	WebsiteActivationToggle selectWebsiteActivationToggle();
}
