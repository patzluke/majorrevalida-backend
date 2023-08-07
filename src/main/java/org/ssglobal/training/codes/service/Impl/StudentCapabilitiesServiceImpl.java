package org.ssglobal.training.codes.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.model.StudentCourseData;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.repository.StudentCapabilitiesRepository;
import org.ssglobal.training.codes.service.StudentCapabilitiesService;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.EvaluationQuestionAnswer;
import org.ssglobal.training.codes.tables.pojos.Grades;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.Program;
import org.ssglobal.training.codes.tables.pojos.StudentAttendance;
import org.ssglobal.training.codes.tables.pojos.SubmittedSubjectsForEnrollment;
import org.ssglobal.training.codes.tables.pojos.Users;
import org.ssglobal.training.codes.tables.pojos.WebsiteActivationToggle;
import org.ssglobal.training.codes.tables.records.SubmittedSubjectsForEnrollmentRecord;

@Service
public class StudentCapabilitiesServiceImpl implements StudentCapabilitiesService {
	private final org.ssglobal.training.codes.tables.SubmittedSubjectsForEnrollment SUBMITTED_SUBJECTS_FOR_ENROLLMENT = org.ssglobal.training.codes.tables.SubmittedSubjectsForEnrollment.SUBMITTED_SUBJECTS_FOR_ENROLLMENT;

	@Autowired
	private DSLContext dslContext;
	
	@Autowired
	private StudentCapabilitiesRepository repository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public List<Users> selectAllUsers() {
		return repository.selectAllUsers();
	}

	@Override
	public UserAndStudent viewStudentProfile(Integer studentId) {
		return repository.viewStudentProfile(studentId);
	}

	@Override
	public UserAndStudent updateStudentProfile(UserAndStudent student) {
		selectAllUsers().forEach(user -> {
			if (!user.getUserId().equals(student.getUserId())) {
				if (user.getUsername().equals(student.getUsername())) {
					throw new DuplicateKeyException("username already exists");
				}
				if (user.getEmail().equals(student.getEmail())) {
					throw new DuplicateKeyException("email already exists");
				}
				
			}
		});
		
		if (!student.getPassword().isEmpty()) {
			student.setPassword(encoder.encode(student.getPassword()));
		}
		return repository.updateStudentProfile(student);
	}

	@Override
	public Grades viewStudentGrade(Integer studentId) {
		return repository.viewStudentGrade(studentId);
	}

	@Override
	public StudentCourseData viewCourse(Integer studentNo) {
		return repository.viewCourse(studentNo);
	}

	@Override
	public Major editMajor(Major editedMajor) {
		return repository.editMajor(editedMajor);
	}

	@Override
	public Course editCourse(Course course) {
		return repository.editCourse(course);
	}

	@Override
	public Program editProgram(Program program) {
		return repository.editProgram(program);
	}

	@Override
	public StudentAttendance viewStudentAttendance(Integer studentNo) {
		return repository.viewStudentAttendance(studentNo);
	}
	
	@Override
	public List<Map<String, Object>> selectAllStudentSubjectEnrolledByStudentNo(Integer studentNo) {
		return repository.selectAllStudentSubjectEnrolledByStudentNo(studentNo);
	}
	
	@Override
	public List<Map<String, Object>> selectStudentAttendanceByAndSubjectAndStudentNo(
			String subjectTitle, Integer studentNo) {
		return repository.selectStudentAttendanceByAndSubjectAndStudentNo(subjectTitle, studentNo);
	}
	
	@Override
	public List<Map<String, Object>> selectAllSubjectGradesOfStudent(Integer studentNo) {
		return repository.selectAllSubjectGradesOfStudent(studentNo);
	}
	
	@Override
	public List<Map<String, Object>> selectEnrolledSchoolYearOfStudent(Integer studentNo) {
		return repository.selectEnrolledSchoolYearOfStudent(studentNo);

	}
	
	@Override
	public List<Map<String, Object>> selectScheduleOfStudent(Integer studentNo, Integer academicYearId) {
		return repository.selectScheduleOfStudent(studentNo, academicYearId);
	}
	
	@Override
	public List<Map<String, Object>> selectAllMajorSubjectsInACurriculumOfStudent(Integer studentNo) {
		return repository.selectAllMajorSubjectsInACurriculumOfStudent(studentNo);
	}
	
	@Override
	public List<Map<String, Object>> selectAllMinorSubjectsInACurriculumOfStudent(Integer studentNo) {
		return repository.selectAllMinorSubjectsInACurriculumOfStudent(studentNo);
	}
	
	@Override
	public List<Map<String, Object>> selectAllFailedMajorSubjectPreviouslyOfStudent(Integer studentNo) {
		List<Map<String, Object>> backLogs = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> majorLogs = repository.selectListOfBackLogsMajorSubject(studentNo);
		List<Map<String, Object>> majorFaileds = repository.selectAllFailedMajorSubjectPreviouslyOfStudent(studentNo);
		majorLogs.forEach((log) -> {
			backLogs.add(log);
		});
		majorFaileds.forEach((fail) -> {
			backLogs.add(fail);
		});
		return backLogs;
	}
	
	@Override
	public List<Map<String, Object>> selectAllFailedMinorSubjectPreviouslyOfStudent(Integer studentNo) {
		return repository.selectAllFailedMinorSubjectPreviouslyOfStudent(studentNo);
	}
	
	@Override
	public Map<String, Object> selectStudentEnrollmentData(Integer studentNo) {
		return repository.selectStudentEnrollmentData(studentNo);
	}
	
	@Override
	public List<Map<String, Object>> selectAllMajorSubjectsToEnrollPerYearAndSem(Integer yearLevel, Integer sem) {
		return repository.selectAllMajorSubjectsToEnrollPerYearAndSem(yearLevel, sem);
	}
	
	@Override
	public List<Map<String, Object>> selectAllMinorSubjectsToEnrollPerYearAndSem(Integer yearLevel, Integer sem) {
		return repository.selectAllMinorSubjectsToEnrollPerYearAndSem(yearLevel, sem);
	}
	
	@Override
	public boolean insertIntoSubmittedSubjectsForEnrollment(List<SubmittedSubjectsForEnrollment> submittedSubjectsForEnrollment) {
		List<SubmittedSubjectsForEnrollmentRecord> subjectsForEnrollmentRecords = new ArrayList<>();
		submittedSubjectsForEnrollment.forEach(subject -> {
			SubmittedSubjectsForEnrollmentRecord subjectRecord = dslContext.newRecord(SUBMITTED_SUBJECTS_FOR_ENROLLMENT);
			subjectRecord.setSubjectCode(subject.getSubjectCode());
			subjectRecord.setEnrollmentId(subject.getEnrollmentId());
			subjectsForEnrollmentRecords.add(subjectRecord);
		});
		
		return repository.insertIntoSubmittedSubjectsForEnrollment(subjectsForEnrollmentRecords);
	}
	
	@Override
	public List<SubmittedSubjectsForEnrollment> checkIfThereIsSubmittedSubjectsForEnrollment(Integer enrollmentId) {
		return repository.checkIfThereIsSubmittedSubjectsForEnrollment(enrollmentId);
	}
	
	@Override
	public List<Map<String, Object>> selectAllEvaluationQuestionAnswerStudentNoAndSubjectCode(Integer studentNo,
			Integer subjectCode) {
		return repository.selectAllEvaluationQuestionAnswerStudentNoAndSubjectCode(studentNo, subjectCode);
	}
	
	@Override
	public boolean updateEvaluationQuestionAnswer(List<EvaluationQuestionAnswer> questionAnswers) {
		return repository.updateEvaluationQuestionAnswer(questionAnswers);
	}
	
	@Override
	public WebsiteActivationToggle selectWebsiteActivationToggle() {
		return repository.selectWebsiteActivationToggle();
	}
}
