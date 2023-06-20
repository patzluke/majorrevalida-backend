package org.ssglobal.training.codes.service;

import java.time.LocalDate;
import java.util.List;

import org.jooq.Row9;
import org.ssglobal.training.codes.tables.pojos.Professor;

public interface ProfessorService {
	
	List<Professor> selectAllProfessor();
	Row9<Integer, Integer, String, String, String, String, String, LocalDate, Boolean> insertProfessor(Professor professor);
	Row9<Integer, Integer, String, String, String, String, String, LocalDate, Boolean> updateProfessor(Professor professor);
	Row9<Integer, Integer, String, String, String, String, String, LocalDate, Boolean> deleteProfessorById(Integer programId);
}
