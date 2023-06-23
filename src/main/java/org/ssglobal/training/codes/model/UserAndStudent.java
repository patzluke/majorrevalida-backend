package org.ssglobal.training.codes.model;

import java.time.LocalDate;

public class UserAndStudent {
	// This is the attributes for user
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
	private Boolean activeDeactive;
	private String image;

	// This is the attributes for student
	private Integer userId;
	private Integer curriculumCode;
	private Integer parentNo;
	private Integer sem;
	private Integer yearLevel;
	private Integer academicYearId;

	public UserAndStudent() {
	}

	public String getUsername() {
		return username;
	}

	public UserAndStudent(String username, String password, String email, String contactNo, String firstName,
			String middleName, String lastName, String userType, LocalDate birthDate, String address,
			String civilStatus, String gender, String nationality, Boolean activeDeactive, String image, Integer userId,
			Integer curriculumCode, Integer parentNo, Integer sem, Integer yearLevel, Integer academicYearId) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.contactNo = contactNo;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.userType = userType;
		this.birthDate = birthDate;
		this.address = address;
		this.civilStatus = civilStatus;
		this.gender = gender;
		this.nationality = nationality;
		this.activeDeactive = activeDeactive;
		this.image = image;
		this.userId = userId;
		this.curriculumCode = curriculumCode;
		this.parentNo = parentNo;
		this.sem = sem;
		this.yearLevel = yearLevel;
		this.academicYearId = academicYearId;
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCurriculumCode() {
		return curriculumCode;
	}

	public void setCurriculumCode(Integer curriculumCode) {
		this.curriculumCode = curriculumCode;
	}

	public Integer getParentNo() {
		return parentNo;
	}

	public void setParentNo(Integer parentNo) {
		this.parentNo = parentNo;
	}

	public Integer getSem() {
		return sem;
	}

	public void setSem(Integer sem) {
		this.sem = sem;
	}

	public Integer getYearLevel() {
		return yearLevel;
	}

	public void setYearLevel(Integer yearLevel) {
		this.yearLevel = yearLevel;
	}

	public Integer getAcademicYearId() {
		return academicYearId;
	}

	public void setAcademicYearId(Integer academicYearId) {
		this.academicYearId = academicYearId;
	}

	@Override
	public String toString() {
		return "UserAndStudent [username=" + username + ", password=" + password + ", email=" + email + ", contactNo="
				+ contactNo + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName
				+ ", userType=" + userType + ", birthDate=" + birthDate + ", address=" + address + ", civilStatus="
				+ civilStatus + ", gender=" + gender + ", nationality=" + nationality + ", activeDeactive="
				+ activeDeactive + ", image=" + image + ", userId=" + userId + ", curriculumCode=" + curriculumCode
				+ ", parentNo=" + parentNo + ", sem=" + sem + ", yearLevel=" + yearLevel + ", academicYearId="
				+ academicYearId + "]";
	}

}
