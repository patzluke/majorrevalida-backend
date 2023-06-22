package org.ssglobal.training.codes.service;

import java.util.List;

import org.ssglobal.training.codes.model.UserAndAdmin;

public interface AdminCapabilitiesService {

	UserAndAdmin insertAdminUser(UserAndAdmin userAdmin);
	UserAndAdmin updateAdminUser(UserAndAdmin userAdmin);
	UserAndAdmin deleteAdminUser(Integer userAdminId);
	List<UserAndAdmin> selectAllAdmin();
	UserAndAdmin selectAdmin(Integer adminNo);
}
