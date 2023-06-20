package org.ssglobal.training.codes.service.Impl;

import java.util.List;

import org.jooq.Row5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repository.ParentRepository;
import org.ssglobal.training.codes.service.ParentService;
import org.ssglobal.training.codes.tables.pojos.Parent;

@Service
public class ParentServiceImpl implements ParentService{
	
	@Autowired
	private ParentRepository repository;

	@Override
	public List<Parent> selectAllParent() {
		return repository.selectAllParent();
	}

	@Override
	public Row5<Integer, Integer, String, String, String> insertParent(Parent parent) {
		return repository.insertParent(parent);
	}

	@Override
	public Row5<Integer, Integer, String, String, String> updateParent(Parent parent) {
		return repository.updateParent(parent);
	}

	@Override
	public Row5<Integer, Integer, String, String, String> deleteParent(Integer parentId) {
		return repository.deleteParent(parentId);
	}
}
