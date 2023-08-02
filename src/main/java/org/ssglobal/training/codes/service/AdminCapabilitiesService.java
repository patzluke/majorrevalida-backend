package org.ssglobal.training.codes.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;
import org.ssglobal.training.codes.model.EnrollmentData;
import org.ssglobal.training.codes.model.UserAndAdmin;
import org.ssglobal.training.codes.model.UserAndParent;
import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.AcademicYear;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.Department;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.ProfessorLoad;
import org.ssglobal.training.codes.tables.pojos.Program;
import org.ssglobal.training.codes.tables.pojos.Room;
import org.ssglobal.training.codes.tables.pojos.Section;
import org.ssglobal.training.codes.tables.pojos.StudentApplicant;
import org.ssglobal.training.codes.tables.pojos.StudentEnrollment;
import org.ssglobal.training.codes.tables.pojos.Subject;
import org.ssglobal.training.codes.tables.pojos.Users;
import org.ssglobal.training.codes.tables.records.StudentAttendanceRecord;

public interface AdminCapabilitiesService {

	List<Users> selectAllUsers();

	boolean changePassword(String password, String username);

	// ------------------------FOR ADMIN
	List<UserAndAdmin> selectAllAdmin();

	UserAndAdmin selectAdmin(Integer adminNo);

	UserAndAdmin insertAdminUser(UserAndAdmin userAdmin) throws DuplicateKeyException, Exception;

	UserAndAdmin updateAdminUser(UserAndAdmin userAdmin) throws DuplicateKeyException, Exception;

	UserAndAdmin changeAdminAccountStatus(Integer userId, Boolean status);

	UserAndAdmin deleteAdminUser(Integer userAdminId);

	// ------------------------FOR Student
	List<UserAndStudent> selectAllStudent();

	UserAndStudent insertStudent(UserAndStudent student) throws DuplicateKeyException, Exception;

	UserAndStudent updateStudent(UserAndStudent student) throws DuplicateKeyException, Exception;

	UserAndStudent changeStudentAccountStatus(Integer userId, Boolean status);

	UserAndStudent deleteStudent(Integer userId);

	// ------------------------FOR Professor
	List<UserAndProfessor> selectAllProfessor();

	UserAndProfessor insertProfessor(UserAndProfessor userAndProfessor) throws DuplicateKeyException, Exception;

	UserAndProfessor updateProfessor(UserAndProfessor userAndProfessor) throws DuplicateKeyException, Exception;

	UserAndProfessor changeProfessorAccountStatus(Integer userId, Boolean status);

	UserAndProfessor deleteProfessor(Integer userId);

	// ------------------------FOR Professor Load
	List<Map<String, Object>> selectAllProfessorsLoad();

	List<Map<String, Object>> selectProfessorLoad(Integer professorNo);

	List<Map<String, Object>> selectProfessorLoadWithMajorSubjectBySectionAndCurriculumCode(Integer sectionId,
			Integer curriculumCode, Integer yearLevel, Integer sem);

	List<Map<String, Object>> selectProfessorLoadWithMinorSubjectBySectionAndCurriculumCode(Integer sectionId,
			Integer yearLevel, Integer sem);

	Map<String, Object> insertProfessorLoad(ProfessorLoad professorLoad);

	Map<String, Object> updateProfessorLoad(ProfessorLoad professorLoad);

	Map<String, Object> deleteProfessorLoad(Integer loadId);

	// ------------------------FOR Parent
	List<UserAndParent> selectAllParent();

	UserAndParent updateParentInfo(UserAndParent parent) throws DuplicateKeyException, Exception;

	UserAndParent changeParentAccountStatus(Integer userId, Boolean status);

	UserAndParent deleteParent(Integer userId);

	List<Map<String, Object>> getAllStudentWithAcademicYear();

	// ------------------------FOR Student_applicants
	List<StudentApplicant> selectAllStudentApplicants();

	StudentApplicant updateStudentApplicantStatus(StudentApplicant studentApplicant);

	// ------------------------FOR StudentEnrollment
	StudentEnrollment insertStudentEnrollmentData(StudentApplicant studentApplicant);

	List<Map<String, Object>> getAllEnrollmentData();

	EnrollmentData fullyEnrollStudent(EnrollmentData student);
	
	// ------------------------FOR Student Attendance
	AcademicYear selectEnrolledSchoolYearOfStudent(Integer studentNo);

	boolean batchInsertStudentAttendanceBySubject(List<StudentAttendanceRecord> studentAttendanceRecords);
	
	// ------------------------FOR Student Schedule

	// ------------------------FOR Academic year
	List<AcademicYear> selectAllAcademicYear();
	AcademicYear addNewAcademicYear(AcademicYear academicYear);
	AcademicYear updateNewAcademicYear(AcademicYear academicYear);
	
	AcademicYear addAcademicYear(AcademicYear academicYear);

	AcademicYear updateAcademicYearStatus(AcademicYear academicYear);

	// ------------------------FOR Program
	List<Program> selectAllProgram();

	Program addProgram(Program program);

	Program editProgram(Program program);

	// ------------------------FOR Course
	List<Map<String, Object>> selectAllCourses();

	Map<String, Object> addCourse(Course course);

	Map<String, Object> editCourse(Course course);

	// ------------------------FOR Department
	List<Department> selectAllDepartment();
	public Department updateDepartment(Department department);
	public Department insertDepartment(Department department);
	public Department deleteDepartment(Department department);
	
	// ------------------------FOR Major
	List<Major> selectAllMajor();

	// ------------------------FOR Curriculum
	List<Map<String, Object>> selectAllCurriculum();

	// ------------------------FOR Curriculum And Major
	Map<String, Object> addCurriculumAndMajor(Map<String, Object> payload);

	Map<String, Object> editCurriculumAndMajor(Map<String, Object> payload);

	Map<String, Object> deleteCurriculumAndMajor(Integer curriculumCode);

	// ------------------------FOR Subject
	List<Subject> selectAllSubject();

	// ------------------------FOR Section
	List<Map<String, Object>> selectAllSection();
	Map<String, Object> addSection(Section section);
	Map<String, Object> updateSection(Section section);

	// ------------------------FOR Room
	List<Room> selectAllRoom();

	// ------------------------FOR Grades
	List<Map<String, Object>> selectAllStudentsBySection();
	
	List<Map<String, Object>> selectAllBatchYearBySection(Integer sectionId);

	// -------------------------FOR THE MINOR SUBJECTS
	List<Map<String, Object>> selectAllMinorSubjects();

	List<Map<String, Object>> selectAllMajorSubjects();

	Map<String, Object> editMinorSubject(Map<String, Object> payload) throws Exception;

	Map<String, Object> changeMinorSubjectStatus(Integer subjectCode, Boolean activeStatus);

	Map<String, Object> inserMinorSubject(Map<String, Object> payload) throws Exception;

	public Map<String, Object> deleteMinorSubject(Integer subjectCode, Boolean activeStatus) throws Exception;

	// ---------------------- FOR THE MAJOR SUBJECTS
	public Map<String, Object> changeMajorSubjectStatus(Integer subjectCode, Boolean activeStatus);

	public Map<String, Object> changeMajorSubjectStatusByCourse(Integer subjectCode, Boolean activeStatus,
			Integer courseCode);

	public Map<String, Object> editMajorSubject(Map<String, Object> payload) throws Exception;

	public Map<String, Object> editMajorSubjectByAll(Map<String, Object> payload) throws Exception;

	public Map<String, Object> addMajorSubjectByMajor(Map<String, Object> payload) throws Exception;

	public Map<String, Object> addMajorSubjectByAll(Map<String, Object> payload, Integer courseCode) throws Exception;

	public Map<String, Object> deleteMajorSubject(Integer subjectCode) throws Exception;

	public Map<String, Object> deleteMajorSubjectByCourse(Integer subjectCode, Integer curriculumCode) throws Exception;

	public List<Map<String, Object>> selectAllMajorSubjectsByAllCourse(Integer courseCode);

	// ---------------------- FOR THE PASSED MAJOR SUBJECTS
	public List<Map<String, Object>> selectStudentPassedMajorSubject(Integer studentNo);

	// ---------------------- FOR THE PASSED MINOR SUBJECTS
	public List<Map<String, Object>> selectStudentPassedMinorSubject(Integer studentNo);

	// ---------------------- FOR THE FRESHMAN MINOR SUBJECTS
	public List<Map<String, Object>> selectFreshManStudentMajorSubject(Integer studentNo);

	// ---------------------- FOR THE FRESHMAN MINOR SUBJECTS
	public List<Map<String, Object>> selectFreshManStudentMinorSubject(Integer studentNo);
	
	// ---------------------- FOR Submitted Subjects for enrollment of students
	List<Map<String, Object>> selectSubmittedSubjectsOfstudentPerEnrollment(Integer studentNo);
	
	Map<String, Object> updateSubmittedSubjectsOfstudentPerEnrollmentStatus(Integer submittedSubjectsId, String status);
}
