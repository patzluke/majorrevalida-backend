package org.ssglobal.training.codes.service;

import org.ssglobal.training.codes.model.UserAndAdmin;
import org.ssglobal.training.codes.model.UserAndStudent;

public interface AdminCapabilitiesService {

	UserAndAdmin insertAdminUser(UserAndAdmin userAdmin);

	UserAndAdmin updateAdminUser(UserAndAdmin userAdmin);

	UserAndAdmin deleteAdminUser(Integer userAdminId);

	UserAndStudent insertStudent(UserAndStudent student);
}
