package org.ssglobal.training.codes.service;

import java.util.List;

import org.jooq.Row5;
import org.ssglobal.training.codes.tables.pojos.Parent;

public interface ParentService {
	
	List<Parent> selectAllParent();
	Row5<Integer, Integer, String, String, String> insertParent(Parent parent);
	Row5<Integer, Integer, String, String, String> updateParent(Parent parent);
	Row5<Integer, Integer, String, String, String> deleteParent(Integer parentId);
}
