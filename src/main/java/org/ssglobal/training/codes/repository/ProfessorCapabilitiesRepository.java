package org.ssglobal.training.codes.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.model.UserAndProfessor;
import org.ssglobal.training.codes.tables.pojos.Professor;
import org.ssglobal.training.codes.tables.pojos.Users;

@Repository
public class ProfessorCapabilitiesRepository {

	@Autowired
	private DSLContext dslContext;

	private final org.ssglobal.training.codes.tables.Users USERS = org.ssglobal.training.codes.tables.Users.USERS;
	private final org.ssglobal.training.codes.tables.Professor PROFESSOR = org.ssglobal.training.codes.tables.Professor.PROFESSOR;

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

}
