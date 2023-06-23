package org.ssglobal.training.codes.service;

import java.util.List;

import org.ssglobal.training.codes.model.UserAndParent;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.tables.pojos.Grades;

public interface ParentCapabilitiesService {
	
	UserAndParent selectParent(Integer parentNo);
	
	UserAndParent updateParentInfo(UserAndParent parent);
	
	List<UserAndStudent> selectAllChildren(Integer parentNo);
	
	List<Grades> selectAllGrades(Integer studentNo);
}
