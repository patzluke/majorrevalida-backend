package org.ssglobal.training.codes.repository;

import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Row6;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ssglobal.training.codes.tables.pojos.AdminUser;

import lombok.RequiredArgsConstructor;

@Repository
@Transactional
@RequiredArgsConstructor
public class AdminUserRepository {

	private final org.ssglobal.training.codes.tables.AdminUser ADMIN_USER = org.ssglobal.training.codes.tables.AdminUser.ADMIN_USER;

	@Autowired
	private final DSLContext dslContext;
	
	public List<AdminUser> selectAllAdmin() {
		return dslContext.selectFrom(ADMIN_USER)
				.fetchInto(AdminUser.class);
	}
	
	public Row6<Integer, String, String, String, String, String> insertAdminUser(AdminUser adminUser) {
		return dslContext.insertInto(ADMIN_USER)
				  .set(ADMIN_USER.USERNAME, adminUser.getUsername())
				  .set(ADMIN_USER.PASSWORD, adminUser.getPassword())
				  .set(ADMIN_USER.FIRST_NAME, adminUser.getFirstName())
				  .set(ADMIN_USER.LAST_NAME, adminUser.getLastName())
				  .set(ADMIN_USER.ADMIN_TYPE, adminUser.getAdminType())
				  .returning(ADMIN_USER.ADMIN_ID, ADMIN_USER.USERNAME, ADMIN_USER.PASSWORD, 
						  ADMIN_USER.FIRST_NAME, ADMIN_USER.LAST_NAME, ADMIN_USER.ADMIN_TYPE)
				  .fetchOne()
				  .valuesRow();
	}
	
	public Row6<Integer, String, String, String, String, String> updateAdminUser(AdminUser adminUser) {
		return dslContext.update(ADMIN_USER)
				  .set(ADMIN_USER.USERNAME, adminUser.getUsername())
				  .set(ADMIN_USER.PASSWORD, adminUser.getPassword())
				  .set(ADMIN_USER.FIRST_NAME, adminUser.getFirstName())
				  .set(ADMIN_USER.LAST_NAME, adminUser.getLastName())
				  .set(ADMIN_USER.ADMIN_TYPE, adminUser.getAdminType())
				  .where(ADMIN_USER.ADMIN_ID.eq(adminUser.getAdminId()))
				  .returning(ADMIN_USER.ADMIN_ID, ADMIN_USER.USERNAME, ADMIN_USER.PASSWORD, 
						  ADMIN_USER.FIRST_NAME, ADMIN_USER.LAST_NAME, ADMIN_USER.ADMIN_TYPE)
				  .fetchOne()
				  .valuesRow();
	}
	
	public Row6<Integer, String, String, String, String, String> deleteAdminUser(Integer adminUserId) {
		return dslContext.deleteFrom(ADMIN_USER)
						 .where(ADMIN_USER.ADMIN_ID.eq(adminUserId))
						 .returning(ADMIN_USER.ADMIN_ID, ADMIN_USER.USERNAME, ADMIN_USER.PASSWORD, 
								  ADMIN_USER.FIRST_NAME, ADMIN_USER.LAST_NAME, ADMIN_USER.ADMIN_TYPE)
						 .fetchOne()
						 .valuesRow();
	}
}
