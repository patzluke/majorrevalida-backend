package org.ssglobal.training.codes.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.model.UserAndAdmin;
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
}
