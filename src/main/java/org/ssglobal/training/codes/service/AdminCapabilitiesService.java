package org.ssglobal.training.codes.service;

import org.ssglobal.training.codes.model.UserAndAdmin;

public interface AdminCapabilitiesService {

	UserAndAdmin insertAdminUser(UserAndAdmin userAdmin);
	UserAndAdmin updateAdminUser(UserAndAdmin userAdmin);
	UserAndAdmin deleteAdminUser(Integer userAdminId);
}
