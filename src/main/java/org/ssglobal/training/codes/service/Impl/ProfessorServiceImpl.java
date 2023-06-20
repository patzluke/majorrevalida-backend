package org.ssglobal.training.codes.service.Impl;

import java.time.LocalDate;
import java.util.List;

import org.jooq.Row4;
import org.jooq.Row9;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repository.ProfessorRepository;
import org.ssglobal.training.codes.repository.ProgramRepository;
import org.ssglobal.training.codes.repository.SubjectRepository;
import org.ssglobal.training.codes.service.ProfessorService;
import org.ssglobal.training.codes.service.ProgramService;
import org.ssglobal.training.codes.tables.pojos.Professor;
import org.ssglobal.training.codes.tables.pojos.Program;

@Service
public class ProfessorServiceImpl implements ProfessorService {
	
	@Autowired
	private ProfessorRepository repository;

	@Override
	public List<Professor> selectAllProfessor() {
		return repository.selectAllProfessor();
	}

	@Override
	public Row9<Integer, Integer, String, String, String, String, String, LocalDate, Boolean> insertProfessor(
			Professor professor) {
		return repository.insertProfessor(professor);
	}

	@Override
	public Row9<Integer, Integer, String, String, String, String, String, LocalDate, Boolean> updateProfessor(
			Professor professor) {
		return repository.updateProfessor(professor);
	}

	@Override
	public Row9<Integer, Integer, String, String, String, String, String, LocalDate, Boolean> deleteProfessorById(
			Integer programId) {
		return repository.deleteProfessorById(programId);
	}
}
