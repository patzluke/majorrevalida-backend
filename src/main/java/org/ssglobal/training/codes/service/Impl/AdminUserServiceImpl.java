package org.ssglobal.training.codes.service.Impl;

import java.util.List;

import org.jooq.Row6;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ssglobal.training.codes.repository.AdminUserRepository;
import org.ssglobal.training.codes.service.AdminUserService;
import org.ssglobal.training.codes.tables.pojos.AdminUser;

@Service
public class AdminUserServiceImpl implements AdminUserService {
	
	@Autowired
	private AdminUserRepository repository;
	
	@Override
	public List<AdminUser> selectAllAdmin() {
		return repository.selectAllAdmin();
	}

	@Override
	public Row6<Integer, String, String, String, String, String> insertAdminUser(AdminUser adminUser) {
		return repository.insertAdminUser(adminUser);
	}

	@Override
	public Row6<Integer, String, String, String, String, String> updateAdminUser(AdminUser adminUser) {
		return repository.updateAdminUser(adminUser);
	}
	
	@Override
	public Row6<Integer, String, String, String, String, String> deleteAdminUser(Integer adminUserId) {
		return repository.deleteAdminUser(adminUserId);
	}
	
}
