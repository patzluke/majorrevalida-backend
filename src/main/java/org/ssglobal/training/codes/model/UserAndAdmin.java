package org.ssglobal.training.codes.model;

import java.time.LocalDate;

public class UserAndAdmin {

	private Integer userId;
	private String username;
	private String password;
	private String email;
    private String contactNo;
	private String firstName;
	private String middleName;
	private String lastName;
	private String userType;
	private LocalDate birthDate;
	private String address;
	private String civilStatus;
	private String gender;
	private String nationality;
	private Boolean activeStatus;
	private Boolean activeDeactive;
	private String image;

	private Integer adminId;
	private Integer adminNo;

	public UserAndAdmin() {
	}

	public UserAndAdmin(UserAndAdmin value) {
		// for user
		this.userId = value.userId;
		this.username = value.username;
		this.password = value.password;
		this.email = value.email;
        this.contactNo = value.contactNo;
		this.firstName = value.firstName;
		this.middleName = value.middleName;
		this.lastName = value.lastName;
		this.userType = value.userType;
		this.birthDate = value.birthDate;
		this.address = value.address;
		this.civilStatus = value.civilStatus;
		this.gender = value.gender;
		this.nationality = value.nationality;
		this.activeStatus = value.activeStatus;
		this.activeDeactive = value.activeDeactive;
		this.image = value.image;

		// for admin
		this.adminId = value.adminId;
		this.adminNo = value.adminNo;
	}

	public UserAndAdmin(Integer userId, String username, String password, String email, String contactNo,
			String firstName, String middleName, String lastName, String userType, LocalDate birthDate, String address,
			String civilStatus, String gender, String nationality, Boolean activeStatus, Boolean activeDeactive, String image,

			Integer adminId, Integer adminNo) {
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.email = email;
		this.contactNo = contactNo;
		this.middleName = middleName;
		this.lastName = lastName;
		this.userType = userType;
		this.birthDate = birthDate;
		this.address = address;
		this.civilStatus = civilStatus;
		this.gender = gender;
		this.nationality = nationality;
		this.activeStatus = activeStatus;
		this.activeDeactive = activeDeactive;
		this.image = image;
		this.adminId = adminId;
		this.adminNo = adminNo;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCivilStatus() {
		return civilStatus;
	}

	public void setCivilStatus(String civilStatus) {
		this.civilStatus = civilStatus;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public Boolean getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Boolean getActiveDeactive() {
		return activeDeactive;
	}

	public void setActiveDeactive(Boolean activeDeactive) {
		this.activeDeactive = activeDeactive;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Integer getAdminNo() {
		return adminNo;
	}

	public void setAdminNo(Integer adminNo) {
		this.adminNo = adminNo;
	}

	@Override
	public String toString() {
		return "UserAndAdmin [userId=" + userId + ", username=" + username + ", password=" + password + ", email="
				+ email + ", contactNo=" + contactNo + ", firstName=" + firstName + ", middleName=" + middleName
				+ ", lastName=" + lastName + ", userType=" + userType + ", birthDate=" + birthDate + ", address="
				+ address + ", civilStatus=" + civilStatus + ", gender=" + gender + ", nationality=" + nationality
				+ ", activeStatus=" + activeStatus + ", activeDeactive=" + activeDeactive + ", image=" + image
				+ ", adminId=" + adminId + ", adminNo=" + adminNo + "]";
	}
}
