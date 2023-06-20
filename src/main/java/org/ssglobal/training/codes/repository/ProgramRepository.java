package org.ssglobal.training.codes.repository;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Row4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ssglobal.training.codes.tables.pojos.Program;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
public class ProgramRepository {

	private final org.ssglobal.training.codes.tables.Program PROGRAM = org.ssglobal.training.codes.tables.Program.PROGRAM;

	@Autowired
	private final DSLContext dslContext;
	
	public List<Program> selectAllProgram() {
		return dslContext.selectFrom(PROGRAM)
				.fetchInto(Program.class);
	}
	
	public Row4<Integer, Integer, String, String> insertProgram(Program program) {
		return dslContext.insertInto(PROGRAM)
					  	 .set(PROGRAM.PROGRAM_TITLE, program.getProgramTitle())
					  	 .set(PROGRAM.MAJOR, program.getMajor())
					  	 .returning()
					  	 .fetchOne()
					  	 .valuesRow();
	}
	
	public Row4<Integer, Integer, String, String> updateProgram(Program program) {
		return dslContext.update(PROGRAM)
					  	 .set(PROGRAM.PROGRAM_ID, program.getProgramId())
					  	 .set(PROGRAM.PROGRAM_CODE, program.getProgramCode())
					  	 .set(PROGRAM.PROGRAM_TITLE, program.getProgramTitle())
					  	 .set(PROGRAM.MAJOR, program.getMajor())
					  	 .where(PROGRAM.PROGRAM_ID.eq(program.getProgramId()))
					  	 .returning()
					  	 .fetchOne()
					  	 .valuesRow();
	}
	
	public Row4<Integer, Integer, String, String> deleteProgramById(Integer programId) {
		return dslContext.deleteFrom(PROGRAM)
						 .where(PROGRAM.PROGRAM_ID.eq(programId))
					  	 .returning()
					  	 .fetchOne()
					  	 .valuesRow();
	}
}
