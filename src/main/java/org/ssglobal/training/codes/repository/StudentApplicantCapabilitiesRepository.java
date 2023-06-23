package org.ssglobal.training.codes.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.Student;
import org.ssglobal.training.codes.tables.pojos.StudentApplicant;
import org.ssglobal.training.codes.tables.pojos.Users;

@Repository
public class StudentApplicantCapabilitiesRepository {

	@Autowired
	private DSLContext dslContext;

	private final org.ssglobal.training.codes.tables.StudentApplicant STUDENT_APPLICANT = org.ssglobal.training.codes.tables.StudentApplicant.STUDENT_APPLICANT;

	public StudentApplicant insertStudentApplicant(StudentApplicant studentApplicant) {
		StudentApplicant applicant = dslContext.insertInto(STUDENT_APPLICANT)
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
									.returning()
									.fetchOne()
									.into(StudentApplicant.class);
		return applicant;
	}

}