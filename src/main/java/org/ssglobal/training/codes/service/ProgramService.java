package org.ssglobal.training.codes.service;

import java.util.List;

import org.jooq.Row4;
import org.ssglobal.training.codes.tables.pojos.Program;

public interface ProgramService {
	
	List<Program> selectAllProgram();
	Row4<Integer, Integer, String, String> insertProgram(Program program);
	Row4<Integer, Integer, String, String> updateProgram(Program program);
	Row4<Integer, Integer, String, String> deleteProgramById(Integer programId);
}
