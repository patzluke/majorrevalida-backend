package org.ssglobal.training.codes.service.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.model.EnrollmentData;
import org.ssglobal.training.codes.model.UserAndAdmin;
import org.ssglobal.training.codes.model.UserAndParent;
import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.repository.AdminCapabilitiesRepository;
import org.ssglobal.training.codes.service.AdminCapabilitiesService;
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
import org.ssglobal.training.codes.tables.pojos.StudentSubjectEnrolled;
import org.ssglobal.training.codes.tables.pojos.Subject;
import org.ssglobal.training.codes.tables.pojos.Users;
import org.ssglobal.training.codes.tables.records.StudentAttendanceRecord;
import org.ssglobal.training.codes.tables.records.StudentScheduleRecord;

@Service
public class AdminCapabilitiesServiceImpl implements AdminCapabilitiesService {
	private final org.ssglobal.training.codes.tables.StudentAttendance STUDENT_ATTENDANCE = org.ssglobal.training.codes.tables.StudentAttendance.STUDENT_ATTENDANCE;
	private final org.ssglobal.training.codes.tables.StudentSchedule STUDENT_SCHEDULE = org.ssglobal.training.codes.tables.StudentSchedule.STUDENT_SCHEDULE;

	@Autowired
	private DSLContext dslContext;

	@Autowired
	private AdminCapabilitiesRepository repository;
	
	@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}

	@Override
	public List<Users> selectAllUsers() {
		return repository.selectAllUsers();
	}
	
	@Override
	public UserAndAdmin selectAdmin(Integer adminNo) {
		return repository.selectAdmin(adminNo);
	}
	
	@Override
	public boolean changePassword(String password, String username) {
		return repository.changePassword(encoder().encode(password), username);
	}
	
	// ------------------------FOR ADMIN
	@Override
	public List<UserAndAdmin> selectAllAdmin() {
		return repository.selectAllAdmin();
	}
	
	@Override
	public UserAndAdmin insertAdminUser(UserAndAdmin userAdmin) throws DuplicateKeyException, Exception {
		selectAllUsers().forEach(user -> {
			if (user.getUsername().equals(userAdmin.getUsername())) {
				throw new DuplicateKeyException("username already exists");
			}
			if (user.getEmail().equals(userAdmin.getEmail())) {
				throw new DuplicateKeyException("email already exists");
			}
		});
		userAdmin.setPassword(encoder().encode(userAdmin.getContactNo().concat(userAdmin.getUsername())));
		return repository.insertAdminUser(userAdmin);
	}

	@Override
	public UserAndAdmin updateAdminUser(UserAndAdmin userAdmin) throws DuplicateKeyException, Exception {
		selectAllUsers().forEach(user -> {
			if (!user.getUserId().equals(userAdmin.getUserId())) {
				if (user.getUsername().equals(userAdmin.getUsername())) {
					throw new DuplicateKeyException("username already exists");
				}
				if (user.getEmail().equals(userAdmin.getEmail())) {
					throw new DuplicateKeyException("email already exists");
				}
				
			}
		});
		
		if (!userAdmin.getPassword().isEmpty()) {
			userAdmin.setPassword(encoder().encode(userAdmin.getPassword()));

		}
		return repository.updateAdminUser(userAdmin);
	}
	
	@Override
	public UserAndAdmin changeAdminAccountStatus(Integer userId, Boolean status) {
		return repository.changeAdminAccountStatus(userId, status);
	}

	@Override
	public UserAndAdmin deleteAdminUser(Integer userAdminId) {
		return repository.deleteAdminUser(userAdminId);
	}
	

	// ------------------------FOR Student
	@Override
	public List<UserAndStudent> selectAllStudent() {
		return repository.selectAllStudent();
	}
	
	@Override
	public UserAndStudent insertStudent(UserAndStudent student) throws DuplicateKeyException, Exception {
		selectAllUsers().forEach(user -> {
			if (user.getUsername().equals(student.getUsername())) {
				throw new DuplicateKeyException("username already exists");
			}
			if (user.getEmail().equals(student.getEmail())) {
				throw new DuplicateKeyException("email already exists");
			}
		});
		student.setPassword(encoder().encode(student.getContactNo().concat(student.getUsername())));
		return repository.insertStudent(student);
	}
	
	@Override
	public UserAndStudent updateStudent(UserAndStudent student) {
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
		return repository.updateStudent(student);
	}
	
	@Override
	public UserAndStudent changeStudentAccountStatus(Integer userId, Boolean status) {
		return repository.changeStudentAccountStatus(userId, status);
	}
	
	@Override
	public UserAndStudent deleteStudent(Integer userId) {
		return repository.deleteStudent(userId);
	}

	// ------------------------FOR Professor
	@Override
	public List<UserAndProfessor> selectAllProfessor() {
		return repository.selectAllProfessor();
	}
	
	@Override
	public UserAndProfessor insertProfessor(UserAndProfessor userAndProfessor) throws DuplicateKeyException, Exception {
		selectAllUsers().forEach(user -> {
			if (user.getUsername().equals(userAndProfessor.getUsername())) {
				throw new DuplicateKeyException("username already exists");
			}
			if (user.getEmail().equals(userAndProfessor.getEmail())) {
				throw new DuplicateKeyException("email already exists");
			}
		});
		userAndProfessor.setPassword(encoder().encode(userAndProfessor.getContactNo().concat(userAndProfessor.getUsername())));
		return repository.insertProfessor(userAndProfessor);
	}
	
	@Override
	public UserAndProfessor updateProfessor(UserAndProfessor userAndProfessor) throws DuplicateKeyException, Exception {
		selectAllUsers().forEach(user -> {
			if (!user.getUserId().equals(userAndProfessor.getUserId())) {
				if (user.getUsername().equals(userAndProfessor.getUsername())) {
					throw new DuplicateKeyException("username already exists");
				}
				if (user.getEmail().equals(userAndProfessor.getEmail())) {
					throw new DuplicateKeyException("email already exists");
				}
			}
		});
		return repository.updateProfessor(userAndProfessor);
	}
	
	@Override
	public UserAndProfessor changeProfessorAccountStatus(Integer userId, Boolean status) {
		return repository.changeProfessorAccountStatus(userId, status);
	}
	
	@Override
	public UserAndProfessor deleteProfessor(Integer userId) {
		return repository.deleteProfessor(userId);
	}
	
	// ------------------------FOR Professor
	@Override
	public List<Map<String, Object>> selectAllProfessorsLoad() {
		return repository.selectAllProfessorsLoad();
	}
	
	@Override
	public List<Map<String, Object>> selectProfessorLoad(Integer professorNo) {
		return repository.selectProfessorLoad(professorNo);
	}
	
	@Override
	public List<Map<String, Object>> selectProfessorLoadWithMajorSubjectBySectionAndCurriculumCode(Integer sectionId,
			Integer curriculumCode, Integer yearLevel, Integer sem) {
		return repository.selectProfessorLoadWithMajorSubjectBySectionAndCurriculumCode(sectionId, curriculumCode, yearLevel, sem);
	}
	
	@Override
	public List<Map<String, Object>> selectProfessorLoadWithMinorSubjectBySectionAndCurriculumCode(Integer sectionId, Integer yearLevel, Integer sem) {
		return repository.selectProfessorLoadWithMinorSubjectBySectionAndCurriculumCode(sectionId, yearLevel, sem);
	}
	
	@Override
	public Map<String, Object> insertProfessorLoad(ProfessorLoad professorLoad) {
		return repository.insertProfessorLoad(professorLoad);
	}
	
	@Override
	public Map<String, Object> updateProfessorLoad(ProfessorLoad professorLoad) {
		return repository.updateProfessorLoad(professorLoad);
	}
	
	@Override
	public Map<String, Object> deleteProfessorLoad(Integer loadId) {
		return repository.deleteProfessorLoad(loadId);
	}
	
	// ------------------------FOR Parent
	@Override
	public List<UserAndParent> selectAllParent() {
		return repository.selectAllParent();
	}
	
	@Override
	public UserAndParent updateParentInfo(UserAndParent parent) {
		selectAllUsers().forEach(user -> {
			if (!user.getUserId().equals(parent.getUserId())) {
				if (user.getUsername().equals(parent.getUsername())) {
					throw new DuplicateKeyException("username already exists");
				}
				if (user.getEmail().equals(parent.getEmail())) {
					throw new DuplicateKeyException("email already exists");
				}
			}
		});
		return repository.updateParentInfo(parent);
	}
	
	@Override
	public UserAndParent changeParentAccountStatus(Integer userId, Boolean status) {
		return repository.changeParentAccountStatus(userId, status);
	}
	
	@Override
	public UserAndParent deleteParent(Integer userId) {
		return repository.deleteParent(userId);
	}
	
	// ------------------------FOR Student_Applicants
	@Override
	public List<StudentApplicant> selectAllStudentApplicants() {
		return repository.selectAllStudentApplicants();
	}
	
	@Override
	public StudentApplicant updateStudentApplicantStatus(StudentApplicant studentApplicant) {
		if (studentApplicant.getAcceptanceStatus().equalsIgnoreCase("Accepted")) {
			studentApplicant.setDateAccepted(LocalDateTime.now());
		}
		return repository.updateStudentApplicantStatus(studentApplicant);
	}
	
	// ------------------------FOR academic_year
	@Override
	public List<AcademicYear> selectAllAcademicYear() {
		return repository.selectAllAcademicYear();
	}
	
	@Override
	public AcademicYear addNewAcademicYear(AcademicYear academicYear) {
		return repository.addNewAcademicYear(academicYear);
	}
	
	@Override
	public AcademicYear updateNewAcademicYear(AcademicYear academicYear) {
		return repository.updateNewAcademicYear(academicYear);
	}
	
	@Override
	public AcademicYear addAcademicYear(AcademicYear academicYear) {
		return repository.addAcademicYear(academicYear);
	}
	
	@Override
	public AcademicYear updateAcademicYearStatus(AcademicYear academicYear) {
		return repository.updateAcademicYearStatus(academicYear);
	}

	// ------------------------FOR program
	@Override
	public List<Program> selectAllProgram() {
		return repository.selectAllProgram();
	}
	
	@Override
	public Program addProgram(Program program) {
		return repository.addProgram(program);
	}
	
	@Override
	public Program editProgram(Program program) {
		return repository.editProgram(program);
	}

	// ------------------------FOR course
	@Override
	public List<Map<String, Object>> selectAllCourses() {
		return repository.selectAllCourses();
	}
	
	@Override
	public Map<String, Object> addCourse(Course course) {
		return repository.addCourse(course);
	}
	
	@Override
	public Map<String, Object> editCourse(Course course) {
		return repository.editCourse(course);
	}
	
	// ------------------------FOR Department
	@Override
	public List<Department> selectAllDepartment() {
		return repository.selectAllDepartment();
	}
	
	@Override
	public Department updateDepartment(Department department) {
		return repository.updateDepartment(department);
	}
	
	@Override
	public Department insertDepartment(Department department) {
		return repository.insertDepartment(department);
	}
	
	@Override
	public Department deleteDepartment(Department department) {
		return repository.deleteDepartment(department);
	}


	// ------------------------FOR Major
	@Override
	public List<Major> selectAllMajor() {
		return repository.selectAllMajor();
	}

	// ------------------------FOR Curriculum
	@Override
	public List<Map<String, Object>> selectAllCurriculum() {
		return repository.selectAllCurriculum();
	}
	
	// ------------------------FOR Curriculum And Major
	@Override
	public Map<String, Object> addCurriculumAndMajor(Map<String, Object> payload) {
		return repository.addCurriculumAndMajor(payload);
	}
	
	@Override
	public Map<String, Object> editCurriculumAndMajor(Map<String, Object> payload) {
		return repository.editCurriculumAndMajor(payload);
	}
	
	@Override
	public Map<String, Object> deleteCurriculumAndMajor(Integer curriculumCode) {
		return repository.deleteCurriculumAndMajor(curriculumCode);
	}
	
	// ------------------------FOR Subject
	@Override
	public List<Subject> selectAllSubject() {
		return repository.selectAllSubject();
	}
	
	// ------------------------FOR Section
	@Override
	public List<Map<String, Object>> selectAllSection() {
		return repository.selectAllSection();
	}
	
	@Override
	public Map<String, Object> addSection(Section section) {
		return repository.addSection(section);
	}
	
	@Override
	public Map<String, Object> updateSection(Section section) {
		return repository.updateSection(section);
	}
	
	// ------------------------FOR Room
	@Override
	public List<Room> selectAllRoom() {
		return repository.selectAllRoom();
	}
	
	// ------------------------FOR Room
	@Override
	public List<Map<String, Object>> selectAllStudentsBySection() {
		return repository.selectAllStudentsBySection();
	}

	// ------------------------FOR Minor Subjects
	@Override
	public List<Map<String, Object>> selectAllMinorSubjects() {
		return repository.selectAllMinorSubjects();
	}

	@Override
	public List<Map<String, Object>> selectAllMajorSubjects() {
		return repository.selectAllMajorSubjects();
	}
	
	@Override
	public List<Map<String, Object>> selectAllMajorSubjectsByAllCourse(Integer courseCode) {
		return repository.selectAllMajorSubjectsByAllCourse(courseCode);
	}
	
	@Override
	public Map<String, Object> editMinorSubject(Map<String, Object> payload) throws Exception {
		return repository.editMinorSubject(payload);
	}
	
	@Override
	public Map<String, Object> editMajorSubjectByAll(Map<String, Object> payload) throws Exception {
		return repository.editMajorSubjectByAll(payload);
	}
	
	@Override
	public Map<String, Object> deleteMinorSubject(Integer subjectCode, Boolean activeStatus) throws Exception {
		return repository.deleteMinorSubject(subjectCode, activeStatus);
	}

	@Override
	public Map<String, Object> changeMinorSubjectStatus(Integer subjectCode, Boolean activeStatus) {
		return repository.changeMinorSubjectStatus(subjectCode, activeStatus);
	}
	
	@Override
	public Map<String, Object> inserMinorSubject(Map<String, Object> payload) throws Exception {
		return repository.inserMinorSubject(payload);
	}

	@Override
	public Map<String, Object> changeMajorSubjectStatus(Integer subjectCode, Boolean activeStatus) {
		return repository.changeMajorSubjectStatus(subjectCode, activeStatus);
	}
	
	@Override
	public Map<String, Object> changeMajorSubjectStatusByCourse(Integer subjectCode, Boolean activeStatus, Integer courseCode) {
		return repository.changeMajorSubjectStatusByCourse(subjectCode, activeStatus, courseCode);
	}
	
	@Override
	public Map<String, Object> addMajorSubjectByMajor(Map<String, Object> payload) throws Exception {
		return repository.addMajorSubjectByMajor(payload);
	}
	
	@Override
	public Map<String, Object> addMajorSubjectByAll(Map<String, Object> payload, Integer courseCode) throws Exception {
		return repository.addMajorSubjectByAll(payload, courseCode);
	}

	@Override
	public Map<String, Object> editMajorSubject(Map<String, Object> payload) throws Exception {
		return repository.editMajorSubject(payload);
	}
	public List<Map<String, Object>> selectStudentPassedMajorSubject(Integer studentNo) {
		return repository.selectStudentPassedMajorSubject(studentNo);
	}
	
	@Override
	public Map<String, Object> deleteMajorSubject(Integer subjectCode) throws Exception {
		return repository.deleteMajorSubject(subjectCode);
	}
	
	@Override
	public Map<String, Object> deleteMajorSubjectByCourse(Integer subjectCode, Integer curriculumCode) throws Exception {
		return repository.deleteMajorSubjectByCourse(subjectCode, curriculumCode);
	}

	@Override
	public List<Map<String, Object>> selectStudentPassedMinorSubject(Integer studentNo) {
		return repository.selectStudentPassedMinorSubject(studentNo);
	}

	@Override
	public List<Map<String, Object>> selectFreshManStudentMajorSubject(Integer studentNo) {
		return repository.selectFreshManStudentMajorSubject(studentNo);
	}

	@Override
	public List<Map<String, Object>> selectFreshManStudentMinorSubject(Integer studentNo) {
		return repository.selectFreshManStudentMinorSubject(studentNo);
	}

	@Override
	public List<Map<String, Object>> getAllStudentWithAcademicYear() {
		return repository.getAllStudentWithAcademicYear();
	}

	@Override
	public StudentEnrollment insertStudentEnrollmentData(StudentApplicant studentApplicant) {		
		return repository.insertStudentEnrollmentData(studentApplicant);
	}

	@Override
	public List<Map<String, Object>> getAllEnrollmentData() {
		return repository.getAllEnrollmentData();
	}

	@Override
	public EnrollmentData fullyEnrollStudent(EnrollmentData student) {
		List<StudentAttendanceRecord> studentAttendanceRecords = new ArrayList<>();
		List<StudentScheduleRecord> studentScheduleRecords = new ArrayList<>();

		EnrollmentData enrolledStudent = repository.fullyEnrollStudent(student);
		selectProfessorLoadWithMajorSubjectBySectionAndCurriculumCode(enrolledStudent.getSectionId(),
				enrolledStudent.getCurriculumCode(), enrolledStudent.getYearLevel(), enrolledStudent.getSemester()).forEach(data -> {
					
					StudentSubjectEnrolled studentSubjectEnrolled = repository.fullyEnrollStudentSubjects(Integer.valueOf(data.get("loadId").toString()), 
														  enrolledStudent.getEnrollmentId());
					repository.insertGradesAndt_subject_detail_history(Integer.valueOf(data.get("professorNo").toString()), Integer.valueOf(data.get("subjectCode").toString()), 
																	   enrolledStudent.getAcademicYearId(), enrolledStudent.getStudentNo(), studentSubjectEnrolled.getEnrollSubjectId());
					
					AcademicYear enrolledAcademicYear = selectEnrolledSchoolYearOfStudent(enrolledStudent.getStudentNo());
					LocalDate startDate = enrolledAcademicYear.getStartDate();
			        LocalDate endDate = enrolledAcademicYear.getEndDate();
			        LocalDate currentDate = startDate;
					while (!currentDate.equals(endDate)) {
						StudentAttendanceRecord record = dslContext.newRecord(STUDENT_ATTENDANCE);
						record.setStudentNo(enrolledStudent.getStudentNo());
						record.setLoadId(Integer.valueOf(data.get("loadId").toString()));
						record.setAttendanceDate(currentDate);
						studentAttendanceRecords.add(record);
			            currentDate = currentDate.plusDays(1);
					}
					StudentScheduleRecord record = dslContext.newRecord(STUDENT_SCHEDULE);
					record.setStudentNo(enrolledStudent.getStudentNo());
					record.setLoadId(Integer.valueOf(data.get("loadId").toString()));
					record.setAcademicYearId(enrolledStudent.getAcademicYearId());
					studentScheduleRecords.add(record);
				});
		selectProfessorLoadWithMinorSubjectBySectionAndCurriculumCode(enrolledStudent.getSectionId(),
				enrolledStudent.getYearLevel(), enrolledStudent.getSemester()).forEach(data -> {
					StudentSubjectEnrolled studentSubjectEnrolled = repository.fullyEnrollStudentSubjects(Integer.valueOf(data.get("loadId").toString()), 
							  							  enrolledStudent.getEnrollmentId());
					repository.insertGradesAndt_subject_detail_history(Integer.valueOf(data.get("professorNo").toString()), Integer.valueOf(data.get("subjectCode").toString()), 
							   enrolledStudent.getAcademicYearId(), enrolledStudent.getStudentNo(), studentSubjectEnrolled.getEnrollSubjectId());
					
					AcademicYear enrolledAcademicYear = selectEnrolledSchoolYearOfStudent(enrolledStudent.getStudentNo());
					LocalDate startDate = enrolledAcademicYear.getStartDate();
			        LocalDate endDate = enrolledAcademicYear.getEndDate();
			        LocalDate currentDate = startDate;
					while (!currentDate.equals(endDate)) {
						StudentAttendanceRecord record = dslContext.newRecord(STUDENT_ATTENDANCE);
						record.setStudentNo(enrolledStudent.getStudentNo());
						record.setLoadId(Integer.valueOf(data.get("loadId").toString()));
						record.setAttendanceDate(currentDate);
						studentAttendanceRecords.add(record);
			            currentDate = currentDate.plusDays(1);
					}
					StudentScheduleRecord record = dslContext.newRecord(STUDENT_SCHEDULE);
					record.setStudentNo(enrolledStudent.getStudentNo());
					record.setLoadId(Integer.valueOf(data.get("loadId").toString()));
					record.setAcademicYearId(enrolledStudent.getAcademicYearId());
					studentScheduleRecords.add(record);
				});
		repository.batchInsertStudentAttendanceBySubject(studentAttendanceRecords);
		repository.batchInsertStudentScheduleBySubject(studentScheduleRecords);
		return enrolledStudent;
	}
	
	@Override
	public AcademicYear selectEnrolledSchoolYearOfStudent(Integer studentNo) {
		return repository.selectEnrolledSchoolYearOfStudent(studentNo);
	}
	
	@Override
	public boolean batchInsertStudentAttendanceBySubject(List<StudentAttendanceRecord> studentAttendanceRecords) {
		return repository.batchInsertStudentAttendanceBySubject(studentAttendanceRecords);
	}
}
