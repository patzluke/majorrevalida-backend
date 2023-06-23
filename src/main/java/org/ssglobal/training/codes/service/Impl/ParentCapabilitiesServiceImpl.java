package org.ssglobal.training.codes.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.model.UserAndParent;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.repository.ParentCapabilitiesRepository;
import org.ssglobal.training.codes.service.ParentCapabilitiesService;
import org.ssglobal.training.codes.tables.pojos.Grades;

@Service
public class ParentCapabilitiesServiceImpl implements ParentCapabilitiesService{
	
	@Autowired
	private ParentCapabilitiesRepository repository;
	
	@Override
	public UserAndParent selectParent(Integer parentNo) {
		return repository.selectParent(parentNo);
	}

	@Override
	public UserAndParent updateParentInfo(UserAndParent parent) {
		return repository.updateParentInfo(parent);
	}

	@Override
	public List<UserAndStudent> selectAllChildren(Integer parentNo) {
		return repository.selectAllChildren(parentNo);
	}

	@Override
	public List<Grades> selectAllGrades(Integer studentNo) {
		return repository.selectAllGrades(studentNo);
	}

}
