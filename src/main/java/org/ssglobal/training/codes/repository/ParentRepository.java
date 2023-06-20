package org.ssglobal.training.codes.repository;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Row5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ssglobal.training.codes.tables.pojos.Parent;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
public class ParentRepository {
	
	private final org.ssglobal.training.codes.tables.Parent PARENT = org.ssglobal.training.codes.tables.Parent.PARENT;
	
	@Autowired
	private final DSLContext dslContext;
	
	public List<Parent> selectAllParent() {
		return dslContext.selectFrom(PARENT).fetchInto(Parent.class);
	}
	
	public Row5<Integer, Integer, String, String, String> insertParent(Parent parent) {
		return dslContext.insertInto(PARENT)
				.set(PARENT.STUDENT_ID, parent.getStudentId())
				.set(PARENT.PASSWORD, parent.getPassword())
				.set(PARENT.USERNAME, parent.getUsername())
				.set(PARENT.LAST_NAME, parent.getLastName())
				.returning(PARENT.PARENT_ID, PARENT.STUDENT_ID, PARENT.LAST_NAME,
						PARENT.PASSWORD, PARENT.USERNAME).fetchOne().valuesRow();
	}
	
	public Row5<Integer, Integer, String, String, String> updateParent(Parent parent) {
		return dslContext.update(PARENT)
				.set(PARENT.STUDENT_ID, parent.getStudentId())
				.set(PARENT.PASSWORD, parent.getPassword())
				.set(PARENT.USERNAME, parent.getUsername())
				.set(PARENT.LAST_NAME, parent.getLastName())
				.returning(PARENT.PARENT_ID, PARENT.STUDENT_ID, PARENT.LAST_NAME,
						PARENT.PASSWORD, PARENT.USERNAME).fetchOne().valuesRow();
	}
	
	public Row5<Integer, Integer, String, String, String> deleteParent(Integer parentId) {
		return dslContext.deleteFrom(PARENT)
				.where(PARENT.PARENT_ID.eq(parentId))
				.returning(PARENT.PARENT_ID, PARENT.STUDENT_ID, PARENT.LAST_NAME,
						PARENT.PASSWORD, PARENT.USERNAME).fetchOne().valuesRow();
	}
}
