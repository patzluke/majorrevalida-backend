package org.ssglobal.training.codes.repository;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.tables.pojos.AcademicYear;
import org.ssglobal.training.codes.tables.pojos.Course;
import org.ssglobal.training.codes.tables.pojos.Major;
import org.ssglobal.training.codes.tables.pojos.StudentApplicant;

@Repository
public class StudentApplicantCapabilitiesRepository {

	@Autowired
	private DSLContext dslContext;

	private final org.ssglobal.training.codes.tables.StudentApplicant STUDENT_APPLICANT = org.ssglobal.training.codes.tables.StudentApplicant.STUDENT_APPLICANT;
	private final org.ssglobal.training.codes.tables.Course COURSE = org.ssglobal.training.codes.tables.Course.COURSE;
	private final org.ssglobal.training.codes.tables.Major MAJOR = org.ssglobal.training.codes.tables.Major.MAJOR;
	private final org.ssglobal.training.codes.tables.AcademicYear ACADEMIC_YEAR = org.ssglobal.training.codes.tables.AcademicYear.ACADEMIC_YEAR;
	
	public StudentApplicant insertStudentApplicant(StudentApplicant studentApplicant) {
		StudentApplicant applicant = dslContext.insertInto(STUDENT_APPLICANT)
									.set(STUDENT_APPLICANT.STUDENT_TYPE, studentApplicant.getStudentType())
									.set(STUDENT_APPLICANT.SELECTED_COURSE_CODE, studentApplicant.getSelectedCourseCode())
									.set(STUDENT_APPLICANT.SELECTED_MAJOR_CODE, studentApplicant.getSelectedMajorCode())
									.set(STUDENT_APPLICANT.YEAR_LEVEL, studentApplicant.getYearLevel())
									.set(STUDENT_APPLICANT.SCHOOL_YEAR, studentApplicant.getSchoolYear())
									.set(STUDENT_APPLICANT.SEMESTER, studentApplicant.getSemester())
									
									.set(STUDENT_APPLICANT.FIRST_NAME, studentApplicant.getFirstName())
									.set(STUDENT_APPLICANT.MIDDLE_NAME, studentApplicant.getMiddleName())
									.set(STUDENT_APPLICANT.LAST_NAME, studentApplicant.getLastName())
									.set(STUDENT_APPLICANT.SUFFIX_NAME, studentApplicant.getSuffixName())
									.set(STUDENT_APPLICANT.GENDER, studentApplicant.getGender())
									.set(STUDENT_APPLICANT.CIVIL_STATUS, studentApplicant.getCivilStatus())
									.set(STUDENT_APPLICANT.CITIZENSHIP, studentApplicant.getCitizenship())
									.set(STUDENT_APPLICANT.BIRTH_DATE, studentApplicant.getBirthDate())
									.set(STUDENT_APPLICANT.BIRTH_PLACE, studentApplicant.getBirthPlace())
									.set(STUDENT_APPLICANT.RELIGION, studentApplicant.getReligion())
									.set(STUDENT_APPLICANT.ADDRESS, studentApplicant.getAddress())
									.set(STUDENT_APPLICANT.TELEPHONE_NO, studentApplicant.getTelephoneNo())
									.set(STUDENT_APPLICANT.MOBILE_NO, studentApplicant.getMobileNo())
									.set(STUDENT_APPLICANT.EMAIL, studentApplicant.getEmail())
									
									.set(STUDENT_APPLICANT.GUARDIAN_FIRST_NAME, studentApplicant.getGuardianFirstName())
									.set(STUDENT_APPLICANT.GUARDIAN_MIDDLE_NAME, studentApplicant.getGuardianMiddleName())
									.set(STUDENT_APPLICANT.GUARDIAN_LAST_NAME, studentApplicant.getGuardianLastName())
									.set(STUDENT_APPLICANT.GUARDIAN_SUFFIX_NAME, studentApplicant.getGuardianSuffixName())
									.set(STUDENT_APPLICANT.GUARDIAN_MOBILE_NO, studentApplicant.getGuardianMobileNo())
									.set(STUDENT_APPLICANT.GUARDIAN_EMAIL, studentApplicant.getGuardianEmail())
									.set(STUDENT_APPLICANT.GUARDIAN_OCCUPATION, studentApplicant.getGuardianOccupation())
									.set(STUDENT_APPLICANT.GUARDIAN_RELATION, studentApplicant.getGuardianRelation())
									
									.set(STUDENT_APPLICANT.DATE_APPLIED, studentApplicant.getDateApplied())
									.returning()
									.fetchOne()
									.into(StudentApplicant.class);
		
		return applicant;
	}
	
	public List<Course> selectAllCourses() {
		return dslContext.selectDistinct(COURSE.COURSE_ID.as("courseId"), COURSE.COURSE_CODE.as("courseCode"), COURSE.PROGRAM_CODE.as("programCode"), 
								 COURSE.DEPT_CODE.as("deptCode"), COURSE.COURSE_TITLE.as("courseTitle"))
						 .from(MAJOR)
						 
						 .innerJoin(COURSE).on(MAJOR.COURSE_CODE.eq(COURSE.COURSE_CODE))
						 .fetchInto(Course.class);
	}
	
	public List<Major> selectCourseMajors(Integer courseCode) {
		return dslContext.selectFrom(MAJOR)
						 .where(MAJOR.COURSE_CODE.eq(courseCode))
						 .fetchInto(Major.class);
	}
	
	// -------------------------- GETTING THE AVAILABLE OF SCHOOL YEAR THAT CAN BE ENROLL
	public AcademicYear getAvailableAcademicYear(){
		AcademicYear availableSchoolYear = dslContext.selectFrom(ACADEMIC_YEAR)
			.where(ACADEMIC_YEAR.STATUS.eq("Process"))
			.fetchOneInto(AcademicYear.class);
			
		return availableSchoolYear;
	} 
		
}
