package org.ssglobal.training.codes.service.Impl;

import java.util.List;

import org.jooq.Row4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repository.ProgramRepository;
import org.ssglobal.training.codes.service.ProgramService;
import org.ssglobal.training.codes.tables.pojos.Program;

@Service
public class ProgramServiceImpl implements ProgramService {
	
	@Autowired
	private ProgramRepository repository;

	@Override
	public List<Program> selectAllProgram() {
		return repository.selectAllProgram();
	}

	@Override
	public Row4<Integer, Integer, String, String> insertProgram(Program program) {
		return repository.insertProgram(program);
	}

	@Override
	public Row4<Integer, Integer, String, String> updateProgram(Program program) {
		return repository.updateProgram(program);
	}

	@Override
	public Row4<Integer, Integer, String, String> deleteProgramById(Integer programId) {
		return repository.deleteProgramById(programId);
	}
}
