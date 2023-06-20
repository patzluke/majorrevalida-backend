package org.ssglobal.training.codes.service;

import java.util.List;

import org.jooq.Row6;
import org.ssglobal.training.codes.tables.pojos.AdminUser;

public interface AdminUserService {
	
	List<AdminUser> selectAllAdmin();
	Row6<Integer, String, String, String, String, String> insertAdminUser(AdminUser adminUser);
	Row6<Integer, String, String, String, String, String> updateAdminUser(AdminUser adminUser);
	Row6<Integer, String, String, String, String, String> deleteAdminUser(Integer adminUserId);
}
