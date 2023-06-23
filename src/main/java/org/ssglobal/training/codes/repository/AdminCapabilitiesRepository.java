package org.ssglobal.training.codes.repository;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.model.UserAndAdmin;
import org.ssglobal.training.codes.model.UserAndParent;
import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.AcademicYear;
import org.ssglobal.training.codes.tables.pojos.Admin;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.Curriculum;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.Parent;
import org.ssglobal.training.codes.tables.pojos.Professor;
import org.ssglobal.training.codes.tables.pojos.Program;
import org.ssglobal.training.codes.tables.pojos.Student;
import org.ssglobal.training.codes.tables.pojos.StudentApplicant;
import org.ssglobal.training.codes.tables.pojos.Users;

@Repository
public class AdminCapabilitiesRepository {

	@Autowired
	private DSLContext dslContext;

	private final org.ssglobal.training.codes.tables.Users USERS = org.ssglobal.training.codes.tables.Users.USERS;
	private final org.ssglobal.training.codes.tables.Admin ADMIN = org.ssglobal.training.codes.tables.Admin.ADMIN;
	private final org.ssglobal.training.codes.tables.Student STUDENT = org.ssglobal.training.codes.tables.Student.STUDENT;
	private final org.ssglobal.training.codes.tables.Professor PROFESSOR = org.ssglobal.training.codes.tables.Professor.PROFESSOR;
	private final org.ssglobal.training.codes.tables.Parent PARENT = org.ssglobal.training.codes.tables.Parent.PARENT;
	private final org.ssglobal.training.codes.tables.StudentApplicant STUDENT_APPLICANT = org.ssglobal.training.codes.tables.StudentApplicant.STUDENT_APPLICANT;
	private final org.ssglobal.training.codes.tables.AcademicYear ACADEMIC_YEAR = org.ssglobal.training.codes.tables.AcademicYear.ACADEMIC_YEAR;
	private final org.ssglobal.training.codes.tables.Program PROGRAM = org.ssglobal.training.codes.tables.Program.PROGRAM;
	private final org.ssglobal.training.codes.tables.Course COURSE = org.ssglobal.training.codes.tables.Course.COURSE;
	private final org.ssglobal.training.codes.tables.Major MAJOR = org.ssglobal.training.codes.tables.Major.MAJOR;
	private final org.ssglobal.training.codes.tables.Curriculum CURRICULUM = org.ssglobal.training.codes.tables.Curriculum.CURRICULUM;

	
	public List<Users> selectAllUsers() {
		return dslContext.selectFrom(USERS).fetchInto(Users.class);
	}
	
	// ------------------------FOR ADMIN
	public List<UserAndAdmin> selectAllAdmin() {
		return dslContext
				.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.EMAIL, USERS.CONTACT_NO, USERS.FIRST_NAME,
						USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE, USERS.ADDRESS,
						USERS.CIVIL_STATUS, USERS.GENDER, USERS.NATIONALITY, USERS.ACTIVE_DEACTIVE, USERS.IMAGE,
						ADMIN.ADMIN_ID, ADMIN.ADMIN_NO)
				.from(USERS).innerJoin(ADMIN).on(USERS.USER_ID.eq(ADMIN.USER_ID)).fetchInto(UserAndAdmin.class);
	}

	public UserAndAdmin selectAdmin(Integer adminNo) {
		return dslContext
				.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.EMAIL, USERS.CONTACT_NO, USERS.FIRST_NAME,
						USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE, USERS.ADDRESS,
						USERS.CIVIL_STATUS, USERS.GENDER, USERS.NATIONALITY, USERS.ACTIVE_DEACTIVE, USERS.IMAGE,
						ADMIN.ADMIN_ID, ADMIN.ADMIN_NO)
				.from(USERS).innerJoin(ADMIN).on(USERS.USER_ID.eq(ADMIN.USER_ID)).where(ADMIN.ADMIN_NO.eq(adminNo))
				.fetchOneInto(UserAndAdmin.class);
	}

	public UserAndAdmin insertAdminUser(UserAndAdmin userAdmin) {
		Users insertedUser = dslContext.insertInto(USERS).set(USERS.USERNAME, userAdmin.getUsername())
				.set(USERS.PASSWORD, userAdmin.getPassword()).set(USERS.EMAIL, userAdmin.getEmail())
				.set(USERS.CONTACT_NO, userAdmin.getContactNo()).set(USERS.FIRST_NAME, userAdmin.getFirstName())
				.set(USERS.MIDDLE_NAME, userAdmin.getMiddleName()).set(USERS.LAST_NAME, userAdmin.getLastName())
				.set(USERS.USER_TYPE, userAdmin.getUserType()).set(USERS.BIRTH_DATE, userAdmin.getBirthDate())
				.set(USERS.ADDRESS, userAdmin.getAddress()).set(USERS.CIVIL_STATUS, userAdmin.getCivilStatus())
				.set(USERS.GENDER, userAdmin.getGender()).set(USERS.NATIONALITY, userAdmin.getNationality())
				.set(USERS.ACTIVE_DEACTIVE, userAdmin.getActiveDeactive()).set(USERS.IMAGE, userAdmin.getImage())
				.returning().fetchOne().into(Users.class);

		Admin insertedAdmin = dslContext.insertInto(ADMIN).set(ADMIN.USER_ID, insertedUser.getUserId()).returning()
				.fetchOne().into(Admin.class);

		if (insertedUser != null && insertedAdmin != null) {
			UserAndAdmin newUserAdmin = new UserAndAdmin(insertedUser.getUserId(), insertedUser.getUsername(),
					insertedUser.getPassword(), insertedUser.getEmail(), insertedUser.getContactNo(),
					insertedUser.getFirstName(), insertedUser.getMiddleName(), insertedUser.getLastName(),
					insertedUser.getUserType(), insertedUser.getBirthDate(), insertedUser.getAddress(),
					insertedUser.getCivilStatus(), insertedUser.getGender(), insertedUser.getNationality(),
					insertedUser.getActiveDeactive(), insertedUser.getImage(), insertedAdmin.getAdminId(),
					insertedAdmin.getAdminNo());
			return newUserAdmin;
		}
		return null;
	}

	public UserAndAdmin updateAdminUser(UserAndAdmin userAdmin) {
		Users updatedUser = dslContext.update(USERS).set(USERS.USERNAME, userAdmin.getUsername())
				.set(USERS.PASSWORD, userAdmin.getPassword()).set(USERS.EMAIL, userAdmin.getEmail())
				.set(USERS.CONTACT_NO, userAdmin.getContactNo()).set(USERS.FIRST_NAME, userAdmin.getFirstName())
				.set(USERS.MIDDLE_NAME, userAdmin.getMiddleName()).set(USERS.LAST_NAME, userAdmin.getLastName())
				.set(USERS.USER_TYPE, userAdmin.getUserType()).set(USERS.BIRTH_DATE, userAdmin.getBirthDate())
				.set(USERS.ADDRESS, userAdmin.getAddress()).set(USERS.CIVIL_STATUS, userAdmin.getCivilStatus())
				.set(USERS.GENDER, userAdmin.getGender()).set(USERS.NATIONALITY, userAdmin.getNationality())
				.set(USERS.ACTIVE_DEACTIVE, userAdmin.getActiveDeactive()).set(USERS.IMAGE, userAdmin.getImage())
				.where(USERS.USER_ID.eq(userAdmin.getUserId())).returning().fetchOne().into(Users.class);

		if (updatedUser != null) {
			UserAndAdmin newUserAdmin = new UserAndAdmin(updatedUser.getUserId(), updatedUser.getUsername(),
					updatedUser.getPassword(), updatedUser.getEmail(), updatedUser.getContactNo(),
					updatedUser.getFirstName(), updatedUser.getMiddleName(), updatedUser.getLastName(),
					updatedUser.getUserType(), updatedUser.getBirthDate(), updatedUser.getAddress(),
					updatedUser.getCivilStatus(), updatedUser.getGender(), updatedUser.getNationality(),
					updatedUser.getActiveDeactive(), updatedUser.getImage(), userAdmin.getAdminId(),
					userAdmin.getAdminNo());
			return newUserAdmin;
		}

		return null;
	}

	public UserAndAdmin deactivateAdminUser(Integer userId) {
		Users deletedUser = dslContext.update(USERS).set(USERS.ACTIVE_DEACTIVE, false)
				.where(USERS.USER_ID.eq(userId)).returning().fetchOne().into(Users.class);

		Admin deletedAdmin = dslContext.selectFrom(ADMIN).where(ADMIN.USER_ID.eq(deletedUser.getUserId())).fetchOne()
				.into(Admin.class);

		if (deletedUser != null && deletedAdmin != null) {
			UserAndAdmin newUserAdmin = new UserAndAdmin(deletedUser.getUserId(), deletedUser.getUsername(),
					deletedUser.getPassword(), deletedUser.getEmail(), deletedUser.getContactNo(),
					deletedUser.getFirstName(), deletedUser.getMiddleName(), deletedUser.getLastName(),
					deletedUser.getUserType(), deletedUser.getBirthDate(), deletedUser.getAddress(),
					deletedUser.getCivilStatus(), deletedUser.getGender(), deletedUser.getNationality(),
					deletedUser.getActiveDeactive(), deletedUser.getImage(), deletedAdmin.getAdminId(),
					deletedAdmin.getAdminNo());
			return newUserAdmin;
		}

		return null;
	}

	// ------------------------FOR STUDENTS

	// Return all the student's data
	public List<UserAndStudent> selectAllStudent() {
		return dslContext.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.FIRST_NAME, USERS.MIDDLE_NAME,
				USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE, USERS.ADDRESS, USERS.CIVIL_STATUS, USERS.GENDER,
				USERS.NATIONALITY, USERS.ACTIVE_DEACTIVE, USERS.IMAGE, STUDENT.CURRICULUM_CODE, STUDENT.PARENT_NO,
				STUDENT.SEM, STUDENT.YEAR_LEVEL, STUDENT.ACADEMIC_YEAR_ID)
				.from(USERS)
				.innerJoin(ADMIN).on(USERS.USER_ID.eq(ADMIN.USER_ID)).fetchInto(UserAndStudent.class);
	}
	
	public UserAndStudent selectStudent(Integer studentNo) {
		return dslContext.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.FIRST_NAME, USERS.MIDDLE_NAME,
				USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE, USERS.ADDRESS, USERS.CIVIL_STATUS, USERS.GENDER,
				USERS.NATIONALITY, USERS.ACTIVE_DEACTIVE, USERS.IMAGE, STUDENT.CURRICULUM_CODE, STUDENT.PARENT_NO,
				STUDENT.SEM, STUDENT.YEAR_LEVEL, STUDENT.ACADEMIC_YEAR_ID)
				.from(USERS)
				.innerJoin(ADMIN).on(USERS.USER_ID.eq(ADMIN.USER_ID))
				.where(STUDENT.STUDENT_NO.eq(studentNo))
				.fetchOneInto(UserAndStudent.class);
	}

	public UserAndStudent insertStudent(UserAndStudent student) {
		/*
		 * This will add the User's data limited to: username, password, email,
		 * contactNo first_name, middle_name, last_name, birth_date, address,
		 * civil_status, gender, nationality, active_deactive, and image
		 */
		Users insertUser = dslContext.insertInto(USERS).set(USERS.USERNAME, student.getUsername())
				.set(USERS.PASSWORD, student.getPassword()).set(USERS.EMAIL, student.getEmail())
				.set(USERS.CONTACT_NO, student.getContactNo()).set(USERS.FIRST_NAME, student.getFirstName())
				.set(USERS.MIDDLE_NAME, student.getMiddleName()).set(USERS.LAST_NAME, student.getLastName())
				.set(USERS.USER_TYPE, student.getUserType()).set(USERS.BIRTH_DATE, student.getBirthDate())
				.set(USERS.ADDRESS, student.getAddress()).set(USERS.CIVIL_STATUS, student.getCivilStatus())
				.set(USERS.GENDER, student.getGender()).set(USERS.NATIONALITY, student.getNationality())
				.set(USERS.ACTIVE_DEACTIVE, student.getActiveDeactive()).set(USERS.IMAGE, student.getImage())
				.returning().fetchOne().into(Users.class);

		/*
		 * This will add the Student's data limited to: user_id, curriculumCode,
		 * parentNo, sem, yearLevel, academicYearId
		 */

		Student insertStudent = dslContext.insertInto(STUDENT).set(STUDENT.USER_ID, insertUser.getUserId())
				.set(STUDENT.CURRICULUM_CODE, student.getCurriculumCode()).set(STUDENT.PARENT_NO, student.getParentNo())
				.set(STUDENT.SEM, student.getSem()).set(STUDENT.YEAR_LEVEL, student.getYearLevel())
				.set(STUDENT.ACADEMIC_YEAR_ID, student.getAcademicYearId())
				.returning().fetchOne().into(Student.class);

		if (insertUser != null && insertStudent != null) {
			// Return all the information of the student
			UserAndStudent information = new UserAndStudent(insertUser.getUserId(), insertUser.getUsername(),
					insertUser.getPassword(), insertUser.getEmail(), insertUser.getContactNo(),
					insertUser.getFirstName(), insertUser.getMiddleName(), insertUser.getLastName(),
					insertUser.getUserType(), insertUser.getBirthDate(), insertUser.getAddress(),
					insertUser.getCivilStatus(), insertUser.getGender(), insertUser.getNationality(),
					insertUser.getActiveDeactive(), insertUser.getImage(),
					insertStudent.getStudentId(), insertStudent.getStudentNo(),
					insertStudent.getCurriculumCode(), insertStudent.getParentNo(), 
					insertStudent.getSem(),insertStudent.getYearLevel(), insertStudent.getAcademicYearId());

			return information;
		}
		return null;
	}
	
	public UserAndStudent updateStudent(UserAndStudent student) {
		Users updatedUser = dslContext.update(USERS).set(USERS.USERNAME, student.getUsername())
				.set(USERS.PASSWORD, student.getPassword()).set(USERS.EMAIL, student.getEmail())
				.set(USERS.CONTACT_NO, student.getContactNo()).set(USERS.FIRST_NAME, student.getFirstName())
				.set(USERS.MIDDLE_NAME, student.getMiddleName()).set(USERS.LAST_NAME, student.getLastName())
				.set(USERS.USER_TYPE, student.getUserType()).set(USERS.BIRTH_DATE, student.getBirthDate())
				.set(USERS.ADDRESS, student.getAddress()).set(USERS.CIVIL_STATUS, student.getCivilStatus())
				.set(USERS.GENDER, student.getGender()).set(USERS.NATIONALITY, student.getNationality())
				.set(USERS.ACTIVE_DEACTIVE, student.getActiveDeactive()).set(USERS.IMAGE, student.getImage())
				.returning().fetchOne().into(Users.class);

		Student updatedStudent = dslContext.update(STUDENT)
				.set(STUDENT.CURRICULUM_CODE, student.getCurriculumCode())
				.set(STUDENT.PARENT_NO, student.getParentNo())
				.set(STUDENT.SEM, student.getSem()).set(STUDENT.YEAR_LEVEL, student.getYearLevel())
				.set(STUDENT.ACADEMIC_YEAR_ID, student.getAcademicYearId())
				.returning().fetchOne().into(Student.class);

		if (updatedUser != null && updatedStudent != null) {
			
			UserAndStudent information = new UserAndStudent(updatedUser.getUserId(), updatedUser.getUsername(),
					updatedUser.getPassword(), updatedUser.getEmail(), updatedUser.getContactNo(),
					updatedUser.getFirstName(), updatedUser.getMiddleName(), updatedUser.getLastName(),
					updatedUser.getUserType(), updatedUser.getBirthDate(), updatedUser.getAddress(),
					updatedUser.getCivilStatus(), updatedUser.getGender(), updatedUser.getNationality(),
					updatedUser.getActiveDeactive(), updatedUser.getImage(),
					updatedStudent.getStudentId(), updatedStudent.getStudentNo(),
					updatedStudent.getCurriculumCode(), updatedStudent.getParentNo(), 
					updatedStudent.getSem(),updatedStudent.getYearLevel(), updatedStudent.getAcademicYearId());
			return information;
		}
		return null;
	}
	
	public UserAndStudent deactivateStudent(Integer userId) {
		Users deactivatedUser = dslContext.update(USERS)
				.set(USERS.ACTIVE_DEACTIVE, false)
				.where(USERS.USER_ID.eq(userId))
				.returning()
				.fetchOne()
				.into(Users.class);

		Student deactivatedStudent = dslContext.selectFrom(PROFESSOR)
				.where(STUDENT.USER_ID.eq(deactivatedUser.getUserId()))
				.fetchOne()
				.into(Student.class);

		if (deactivatedUser != null && deactivatedStudent != null) {
			UserAndStudent information = new UserAndStudent(deactivatedUser.getUserId(), deactivatedUser.getUsername(),
					deactivatedUser.getPassword(), deactivatedUser.getEmail(), deactivatedUser.getContactNo(),
					deactivatedUser.getFirstName(), deactivatedUser.getMiddleName(), deactivatedUser.getLastName(),
					deactivatedUser.getUserType(), deactivatedUser.getBirthDate(), deactivatedUser.getAddress(),
					deactivatedUser.getCivilStatus(), deactivatedUser.getGender(), deactivatedUser.getNationality(),
					deactivatedUser.getActiveDeactive(), deactivatedUser.getImage(), deactivatedStudent.getStudentId(),
					deactivatedStudent.getStudentNo(), deactivatedStudent.getCurriculumCode(),
					deactivatedStudent.getParentNo(), deactivatedStudent.getSem(), deactivatedStudent.getYearLevel(),
					deactivatedStudent.getAcademicYearId());
			return information;
		}
		return null;
	}
	
	// ------------------------FOR PROFESSOR
	
	public List<UserAndProfessor> selectAllProfessor() {
		return dslContext
				.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.EMAIL, USERS.CONTACT_NO, USERS.FIRST_NAME,
						USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE, USERS.ADDRESS,
						USERS.CIVIL_STATUS, USERS.GENDER, USERS.NATIONALITY, USERS.ACTIVE_DEACTIVE, USERS.IMAGE,
						PROFESSOR.PROFESSOR_NO, PROFESSOR.WORK)
				.from(USERS).innerJoin(PROFESSOR).on(USERS.USER_ID.eq(PROFESSOR.USER_ID))
				.fetchInto(UserAndProfessor.class);
	}
	
	public UserAndProfessor selectProfessor(Integer professorNo) {
		return dslContext
				.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.EMAIL, USERS.CONTACT_NO, USERS.FIRST_NAME,
						USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE, USERS.ADDRESS,
						USERS.CIVIL_STATUS, USERS.GENDER, USERS.NATIONALITY, USERS.ACTIVE_DEACTIVE, USERS.IMAGE,
						PROFESSOR.PROFESSOR_NO, PROFESSOR.WORK)
				.from(USERS).innerJoin(PROFESSOR).on(USERS.USER_ID.eq(PROFESSOR.USER_ID))
				.where(PROFESSOR.PROFESSOR_NO.eq(professorNo)).fetchOneInto(UserAndProfessor.class);
	}

	public UserAndProfessor insertProfessor(UserAndProfessor userAndProfessor) {
		Users insertedUser = dslContext.insertInto(USERS).set(USERS.USERNAME, userAndProfessor.getUsername())
				.set(USERS.PASSWORD, userAndProfessor.getPassword()).set(USERS.EMAIL, userAndProfessor.getEmail())
				.set(USERS.CONTACT_NO, userAndProfessor.getContactNo()).set(USERS.FIRST_NAME, userAndProfessor.getFirstName())
				.set(USERS.MIDDLE_NAME, userAndProfessor.getMiddleName()).set(USERS.LAST_NAME, userAndProfessor.getLastName())
				.set(USERS.USER_TYPE, userAndProfessor.getUserType()).set(USERS.BIRTH_DATE, userAndProfessor.getBirthDate())
				.set(USERS.ADDRESS, userAndProfessor.getAddress()).set(USERS.CIVIL_STATUS, userAndProfessor.getCivilStatus())
				.set(USERS.GENDER, userAndProfessor.getGender()).set(USERS.NATIONALITY, userAndProfessor.getNationality())
				.set(USERS.ACTIVE_DEACTIVE, userAndProfessor.getActiveDeactive()).set(USERS.IMAGE, userAndProfessor.getImage())
				.returning().fetchOne().into(Users.class);
		
		Professor insertedProfessor = dslContext.insertInto(PROFESSOR)
												.set(PROFESSOR.USER_ID, insertedUser.getUserId())
												.set(PROFESSOR.WORK, userAndProfessor.getWork())
												.returning()
												.fetchOne().into(Professor.class);

		if (insertedUser != null && insertedProfessor != null) {
			UserAndProfessor newuserAndProfessor = new UserAndProfessor(insertedUser.getUserId(), insertedUser.getUsername(),
					insertedUser.getPassword(), insertedUser.getEmail(), insertedUser.getContactNo(),
					insertedUser.getFirstName(), insertedUser.getMiddleName(), insertedUser.getLastName(),
					insertedUser.getUserType(), insertedUser.getBirthDate(), insertedUser.getAddress(),
					insertedUser.getCivilStatus(), insertedUser.getGender(), insertedUser.getNationality(),
					insertedUser.getActiveDeactive(), insertedUser.getImage(), insertedProfessor.getProfessorId(),
					insertedProfessor.getProfessorNo(), insertedProfessor.getWork());
			return newuserAndProfessor;
		}
		return null;
	}

	public UserAndProfessor updateProfessor(UserAndProfessor userAndProfessor) {
		Users updatedUser = dslContext.update(USERS).set(USERS.USERNAME, userAndProfessor.getUsername())
				.set(USERS.PASSWORD, userAndProfessor.getPassword()).set(USERS.EMAIL, userAndProfessor.getEmail())
				.set(USERS.CONTACT_NO, userAndProfessor.getContactNo()).set(USERS.FIRST_NAME, userAndProfessor.getFirstName())
				.set(USERS.MIDDLE_NAME, userAndProfessor.getMiddleName()).set(USERS.LAST_NAME, userAndProfessor.getLastName())
				.set(USERS.USER_TYPE, userAndProfessor.getUserType()).set(USERS.BIRTH_DATE, userAndProfessor.getBirthDate())
				.set(USERS.ADDRESS, userAndProfessor.getAddress()).set(USERS.CIVIL_STATUS, userAndProfessor.getCivilStatus())
				.set(USERS.GENDER, userAndProfessor.getGender()).set(USERS.NATIONALITY, userAndProfessor.getNationality())
				.set(USERS.ACTIVE_DEACTIVE, userAndProfessor.getActiveDeactive()).set(USERS.IMAGE, userAndProfessor.getImage())
				.where(USERS.USER_ID.eq(userAndProfessor.getUserId()))
				.returning().fetchOne().into(Users.class);
		
		Professor updatedProfessor = dslContext.update(PROFESSOR)
											.set(PROFESSOR.WORK, userAndProfessor.getWork())
											.where(PROFESSOR.PROFESSOR_NO.eq(userAndProfessor.getProfessorNo()))
											.returning()
											.fetchOne().into(Professor.class);

		if (updatedUser != null && updatedProfessor != null) {
			UserAndProfessor newuserAndProfessor = new UserAndProfessor(updatedUser.getUserId(), updatedUser.getUsername(),
					updatedUser.getPassword(), updatedUser.getEmail(), updatedUser.getContactNo(),
					updatedUser.getFirstName(), updatedUser.getMiddleName(), updatedUser.getLastName(),
					updatedUser.getUserType(), updatedUser.getBirthDate(), updatedUser.getAddress(),
					updatedUser.getCivilStatus(), updatedUser.getGender(), updatedUser.getNationality(),
					updatedUser.getActiveDeactive(), updatedUser.getImage(), updatedProfessor.getProfessorId(),
					updatedProfessor.getProfessorNo(), updatedProfessor.getWork());
			return newuserAndProfessor;
		}
		return null;
	}
	
	public UserAndProfessor deactivateProfessor(Integer userId) {
		Users deactivatedUser = dslContext.update(USERS)
				.set(USERS.ACTIVE_DEACTIVE, false)
				.where(USERS.USER_ID.eq(userId))
				.returning()
				.fetchOne()
				.into(Users.class);

		Professor deactivatedProfessor = dslContext.selectFrom(PROFESSOR)
				.where(PROFESSOR.USER_ID.eq(deactivatedUser.getUserId()))
				.fetchOne()
				.into(Professor.class);

		if (deactivatedUser != null && deactivatedProfessor != null) {
			UserAndProfessor deactivatedUserProfessor = new UserAndProfessor(deactivatedUser.getUserId(), deactivatedUser.getUsername(),
					deactivatedUser.getPassword(), deactivatedUser.getEmail(), deactivatedUser.getContactNo(),
					deactivatedUser.getFirstName(), deactivatedUser.getMiddleName(), deactivatedUser.getLastName(),
					deactivatedUser.getUserType(), deactivatedUser.getBirthDate(), deactivatedUser.getAddress(),
					deactivatedUser.getCivilStatus(), deactivatedUser.getGender(), deactivatedUser.getNationality(),
					deactivatedUser.getActiveDeactive(), deactivatedUser.getImage(), deactivatedProfessor.getProfessorId(),
					deactivatedProfessor.getProfessorNo(), deactivatedProfessor.getWork());
			return deactivatedUserProfessor;
		}
		return null;
	}
	
	// ------------------------FOR Parent
	public List<UserAndParent> selectAllParent() {
		return dslContext.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.FIRST_NAME,
				USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE,
				USERS.ADDRESS, USERS.CIVIL_STATUS, USERS.GENDER, USERS.NATIONALITY,
				USERS.ACTIVE_DEACTIVE, USERS.IMAGE, PARENT.PARENT_ID)
				.from(USERS).innerJoin(PARENT).on(USERS.USER_ID.eq(PARENT.USER_ID))
				.fetchInto(UserAndParent.class);
	}
	
	public UserAndParent selectParent(Integer parentNo) {
		return dslContext.select(USERS.USER_ID, USERS.USERNAME, USERS.PASSWORD, USERS.FIRST_NAME,
				USERS.MIDDLE_NAME, USERS.LAST_NAME, USERS.USER_TYPE, USERS.BIRTH_DATE,
				USERS.ADDRESS, USERS.CIVIL_STATUS, USERS.GENDER, USERS.NATIONALITY,
				USERS.ACTIVE_DEACTIVE, USERS.IMAGE, PARENT.PARENT_ID)
				.from(USERS).innerJoin(PARENT).on(USERS.USER_ID.eq(PARENT.USER_ID))
				.where(PARENT.PARENT_ID.eq(parentNo))
				.fetchOneInto(UserAndParent.class);
	}
	
	public UserAndParent updateParentInfo(UserAndParent parent) {
		Users updatedUser = dslContext.update(USERS)
			    			.set(USERS.USERNAME, parent.getUsername())
			    			.set(USERS.PASSWORD, parent.getPassword())
			    			.set(USERS.FIRST_NAME, parent.getFirstName())
			    			.set(USERS.MIDDLE_NAME, parent.getMiddleName())
			    			.set(USERS.LAST_NAME, parent.getLastName())
			    			.set(USERS.USER_TYPE, parent.getUserType())
			    			.set(USERS.BIRTH_DATE, parent.getBirthDate())
			    			.set(USERS.ADDRESS, parent.getAddress())
			    			.set(USERS.CIVIL_STATUS, parent.getCivilStatus())
			    			.set(USERS.GENDER, parent.getGender())
			    			.set(USERS.NATIONALITY, parent.getNationality())
			    			.set(USERS.ACTIVE_DEACTIVE, parent.getActiveDeactive())
			    			.set(USERS.IMAGE, parent.getImage())
			    			.where(USERS.USER_ID.eq(parent.getUserId()))
			    			.returning()
			    			.fetchOne()
			    			.into(Users.class);
		if (updatedUser != null) {
			UserAndParent newParentInfo = new UserAndParent(updatedUser.getUserId(), updatedUser.getUsername(), updatedUser.getPassword(), 
					updatedUser.getFirstName(), updatedUser.getMiddleName(), updatedUser.getLastName(), 
					updatedUser.getUserType(), updatedUser.getBirthDate(), updatedUser.getAddress(), 
					updatedUser.getCivilStatus(), updatedUser.getGender(), updatedUser.getNationality(), 
					updatedUser.getActiveDeactive(), updatedUser.getImage(), parent.getParentId());
			return newParentInfo;
			
		}
		return null;
	}
	
	public UserAndParent deactivateParent(Integer userId) {
		Users deactivatedUser = dslContext.update(USERS)
				.set(USERS.ACTIVE_DEACTIVE, false)
				.where(USERS.USER_ID.eq(userId))
				.returning()
				.fetchOne()
				.into(Users.class);

		Parent deactivatedParent = dslContext.selectFrom(PARENT)
				.where(PARENT.USER_ID.eq(deactivatedUser.getUserId()))
				.fetchOne()
				.into(Parent.class);

		if (deactivatedUser != null && deactivatedParent != null) {
			UserAndParent deactivatedUserParent = new UserAndParent(deactivatedUser.getUserId(),
					deactivatedUser.getUsername(), deactivatedUser.getPassword(), deactivatedUser.getFirstName(),
					deactivatedUser.getMiddleName(), deactivatedUser.getLastName(), deactivatedUser.getUserType(),
					deactivatedUser.getBirthDate(), deactivatedUser.getAddress(), deactivatedUser.getCivilStatus(),
					deactivatedUser.getGender(), deactivatedUser.getNationality(), deactivatedUser.getActiveDeactive(),
					deactivatedUser.getImage(), deactivatedParent.getParentId());
			return deactivatedUserParent;
		}
		return null;
	}

	// ------------------------FOR Applicants
	public List<StudentApplicant> selectAllStudentApplicants() {
		List<StudentApplicant> students = dslContext.selectFrom(STUDENT_APPLICANT).fetchInto(StudentApplicant.class);
		return students;
	}

	public StudentApplicant updateStudentApplicantStatus(StudentApplicant studentApplicant) {
		/*
		 * This will add the User's data limited to: username, password, first_name,
		 * middle_name, last_name, birth_date, address, civil_status, gender,
		 * nationality, active_deactive, image
		 */
		StudentApplicant applicant = dslContext.update(STUDENT_APPLICANT)
				.set(STUDENT_APPLICANT.EMAIL, studentApplicant.getEmail())
				.set(STUDENT_APPLICANT.CONTACT_NO, studentApplicant.getContactNo())
				.set(STUDENT_APPLICANT.FIRST_NAME, studentApplicant.getFirstName())
				.set(STUDENT_APPLICANT.MIDDLE_NAME, studentApplicant.getMiddleName())
				.set(STUDENT_APPLICANT.LAST_NAME, studentApplicant.getLastName())
				.set(STUDENT_APPLICANT.BIRTH_DATE, studentApplicant.getBirthDate())
				.set(STUDENT_APPLICANT.ADDRESS, studentApplicant.getAddress())
				.set(STUDENT_APPLICANT.CIVIL_STATUS, studentApplicant.getCivilStatus())
				.set(STUDENT_APPLICANT.GENDER, studentApplicant.getGender())
				.set(STUDENT_APPLICANT.NATIONALITY, studentApplicant.getNationality())
				.set(STUDENT_APPLICANT.DATE_APPLIED, studentApplicant.getDateApplied())
				.set(STUDENT_APPLICANT.DATE_ACCEPTED, studentApplicant.getDateAccepted())
				.set(STUDENT_APPLICANT.STATUS, studentApplicant.getStatus())
				.set(STUDENT_APPLICANT.STUDENT_TYPE, studentApplicant.getStudentType())
				.where(STUDENT_APPLICANT.STUDENT_APPLICANT_ID.eq(studentApplicant.getStudentApplicantId())).returning()
				.fetchOne().into(StudentApplicant.class);
		return applicant;
	}

	// -------------------------- FOR ACADEMIC YEAR
	public AcademicYear addAcademicYear(AcademicYear academicYear) {
		/*
		 * The academic data added is limited to: academic_year and status
		 */
		AcademicYear addedAcademicYear = dslContext.insertInto(ACADEMIC_YEAR)
				.set(ACADEMIC_YEAR.ACADEMIC_YEAR_, academicYear.getAcademicYear())
				.set(ACADEMIC_YEAR.STATUS, academicYear.getStatus()).returning().fetchOne().into(AcademicYear.class);
		return addedAcademicYear;
	}
	
	public AcademicYear updateAcademicYearStatus(AcademicYear academicYear) {
		AcademicYear addedAcademicYear = dslContext.update(ACADEMIC_YEAR)
				.set(ACADEMIC_YEAR.STATUS, academicYear.getStatus())
				.where(ACADEMIC_YEAR.ACADEMIC_YEAR_ID.eq(academicYear.getAcademicYearId()))
				.returning()
				.fetchOne()
				.into(AcademicYear.class);
		return addedAcademicYear;
	}

	// -------------------------- FOR PROGRAM
	public Program addProgram(Program program) {
		/*
		 * The program data added is limited to: program_title
		 */
		Program addedProgram = dslContext.insertInto(PROGRAM).set(PROGRAM.PROGRAM_TITLE, program.getProgramTitle())
				.returning().fetchOne().into(Program.class);

		return addedProgram;
	}
	
	public Program editProgram(Program program) {
		/*
		 * The program data added is limited to: program_title
		 */
		Program editProgram = dslContext.update(PROGRAM)
				.set(PROGRAM.PROGRAM_TITLE, program.getProgramTitle())
				.where(PROGRAM.PROGRAM_CODE.eq(program.getProgramCode()))
				.returning()
				.fetchOne()
				.into(Program.class);
		return editProgram;
	}

	// -------------------------- FOR COURSE
	public Course addCourse(Course course) {
		/*
		 * The program data added is limited to: program_code and course_title
		 */
		Course editCourse = dslContext.insertInto(COURSE)
				.set(COURSE.PROGRAM_CODE, course.getProgramCode())
				.set(COURSE.COURSE_TITLE, course.getCourseTitle())
				.returning().fetchOne().into(Course.class);

		return editCourse;
	}
	
	public Course editCourse(Course course) {
		/*
		 * The program data added is limited to: program_code and course_title
		 */
		Course editCourse = dslContext.update(COURSE)
				.set(COURSE.PROGRAM_CODE, course.getProgramCode())
				.set(COURSE.COURSE_TITLE, course.getCourseTitle())
				.where(COURSE.COURSE_CODE.eq(course.getCourseCode()))
				.returning().fetchOne()
				.into(Course.class);
		return editCourse;
	}

	// -------------------------- FOR MAJOR
	public Major addMajor(Major major) {
		/*
		 * The program data added is limited to: course_code and major_title
		 */
		Major addedMajor = dslContext.insertInto(MAJOR).set(MAJOR.COURSE_CODE, major.getCourseCode())
				.set(MAJOR.MAJOR_TITLE, major.getMajorTitle()).returning().fetchOne().into(Major.class);

		return addedMajor;
	}
	
	public Major editMajor(Major major) {
		/*
		 * The program data added is limited to: course_code and major_title
		 */
		Major editMajor = dslContext.insertInto(MAJOR)
				.set(MAJOR.COURSE_CODE, major.getCourseCode())
				.set(MAJOR.MAJOR_TITLE, major.getMajorTitle())
				.returning().fetchOne()
				.into(Major.class);
		return editMajor;
	}

	// -------------------------- FOR CURRICULUM
	public Curriculum addCurriculum(Curriculum curriculum) {
		/*
		 * The program data added is limited to: major_code and curriculum_name
		 */
		Curriculum addedCurriculum = dslContext.insertInto(CURRICULUM)
				.set(CURRICULUM.MAJOR_CODE, curriculum.getMajorCode())
				.set(CURRICULUM.CURRICULUM_NAME, curriculum.getCurriculumName()).returning().fetchOne()
				.into(Curriculum.class);
		return addedCurriculum;
	}
	
	public Curriculum editCurriculum(Curriculum curriculum) {
		/*
		 * The program data added is limited to: major_code and curriculum_name
		 */
		Curriculum editCurriculum = dslContext.update(CURRICULUM)
				.set(CURRICULUM.MAJOR_CODE, curriculum.getMajorCode())
				.set(CURRICULUM.CURRICULUM_NAME, curriculum.getCurriculumName())
				.where(CURRICULUM.CURRICULUM_CODE.eq(curriculum.getCurriculumCode()))
				.returning()
				.fetchOne()
				.into(Curriculum.class);
		return editCurriculum;
	}

}
