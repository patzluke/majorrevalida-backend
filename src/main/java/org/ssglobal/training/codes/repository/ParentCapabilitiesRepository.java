package org.ssglobal.training.codes.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.ssglobal.training.codes.model.UserAndParent;

@Repository
public class ParentCapabilitiesRepository {
	
	@Autowired
	private DSLContext dslContext;
	
	private final org.ssglobal.training.codes.tables.Parent PARENT = org.ssglobal.training.codes.tables.Parent.PARENT;
	
	public UserAndParent selectParent(Integer parentId) {
		
		return null;
	}

}
