package org.ssglobal.training.codes.repository;

import java.util.List;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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

	public StudentApplicant insertStudentApplicant(StudentApplicant studentApplicant) {
		StudentApplicant applicant = dslContext.insertInto(STUDENT_APPLICANT)
									.set(STUDENT_APPLICANT.STUDENT_TYPE, studentApplicant.getStudentType())
									.set(STUDENT_APPLICANT.SELECTED_COURSE_CODE, studentApplicant.getSelectedCourseCode())
									.set(STUDENT_APPLICANT.SELECTED_MAJOR_CODE, studentApplicant.getSelectedMajorCode())
									.set(STUDENT_APPLICANT.YEAR_LEVEL, studentApplicant.getYearLevel())
									.set(STUDENT_APPLICANT.SCHOOL_YEAR, studentApplicant.getSchoolYear())
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
									
									.set(STUDENT_APPLICANT.FATHER_FIRST_NAME, studentApplicant.getFatherFirstName())
									.set(STUDENT_APPLICANT.FATHER_MIDDLE_NAME, studentApplicant.getFatherMiddleName())
									.set(STUDENT_APPLICANT.FATHER_LAST_NAME, studentApplicant.getFatherLastName())
									.set(STUDENT_APPLICANT.FATHER_SUFFIX_NAME, studentApplicant.getFatherSuffixName())
									.set(STUDENT_APPLICANT.FATHER_MOBILE_NO, studentApplicant.getFatherMobileNo())
									.set(STUDENT_APPLICANT.FATHER_EMAIL, studentApplicant.getFatherEmail())
									.set(STUDENT_APPLICANT.FATHER_OCCUPATION, studentApplicant.getFatherOccupation())

									.set(STUDENT_APPLICANT.MOTHER_FIRST_NAME, studentApplicant.getMotherFirstName())
									.set(STUDENT_APPLICANT.MOTHER_MIDDLE_NAME, studentApplicant.getMotherMiddleName())
									.set(STUDENT_APPLICANT.MOTHER_LAST_NAME, studentApplicant.getMotherLastName())
									.set(STUDENT_APPLICANT.MOTHER_SUFFIX_NAME, studentApplicant.getMotherSuffixName())
									.set(STUDENT_APPLICANT.MOTHER_MOBILE_NO, studentApplicant.getMotherMobileNo())
									.set(STUDENT_APPLICANT.MOTHER_EMAIL, studentApplicant.getMotherEmail())
									.set(STUDENT_APPLICANT.MOTHER_OCCUPATION, studentApplicant.getMotherOccupation())

									.set(STUDENT_APPLICANT.DATE_APPLIED, studentApplicant.getDateApplied())
									.returning()
									.fetchOne()
									.into(StudentApplicant.class);
		
		return applicant;
	}
	
	public List<Course> selectAllCourses() {
		return dslContext.selectFrom(COURSE).fetchInto(Course.class);
	}
	
	public List<Major> selectCourseMajors(Integer courseCode) {
		return dslContext.selectFrom(MAJOR)
						 .where(MAJOR.COURSE_CODE.eq(courseCode))
						 .fetchInto(Major.class);
	}
}
