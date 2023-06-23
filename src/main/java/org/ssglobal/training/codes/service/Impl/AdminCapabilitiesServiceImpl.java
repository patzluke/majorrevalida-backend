package org.ssglobal.training.codes.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.model.UserAndAdmin;
import org.ssglobal.training.codes.model.UserAndStudent;
import org.ssglobal.training.codes.repository.AdminCapabilitiesRepository;
import org.ssglobal.training.codes.service.AdminCapabilitiesService;

@Service
public class AdminCapabilitiesServiceImpl implements AdminCapabilitiesService {

	@Autowired
	private AdminCapabilitiesRepository repository;
	
	@Override
	public UserAndAdmin insertAdminUser(UserAndAdmin userAdmin) {
		return repository.insertAdminUser(userAdmin);
	}

	@Override
	public UserAndAdmin updateAdminUser(UserAndAdmin userAdmin) {
		return repository.updateAdminUser(userAdmin);
	}

	@Override
	public UserAndAdmin deleteAdminUser(Integer userAdminId) {
		return repository.deleteAdminUser(userAdminId);
	}

	@Override
	public UserAndStudent insertStudent(UserAndStudent student) {
		return repository.insertStudent(student);
	}

	@Override
	public List<UserAndAdmin> selectAllAdmin() {
		return repository.selectAllAdmin();
	}

	@Override
	public UserAndAdmin selectAdmin(Integer adminNo) {
		return repository.selectAdmin(adminNo);
	}
}
