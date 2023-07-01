package org.ssglobal.training.codes.model;

import java.time.LocalDate;

public class UserAndStudent {
	// This is the attributes for user
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

	// This is the attributes for student
	private Integer studentId;
	private Integer studentNo;
	private Integer parentNo;
	private Integer curriculumCode;
	private Integer academicYearId;
	
	private Integer yearLevel;
	private String courseTitle;
	private String majorTitle;

	public UserAndStudent() {
	}

	public UserAndStudent(Integer userId, String username, String password, String email, String contactNo,
			String firstName, String middleName, String lastName, String userType, LocalDate birthDate, String address,
			String civilStatus, String gender, String nationality, Boolean activeStatus, Boolean activeDeactive, String image,
			Integer studentId, Integer studentNo, Integer parentNo, Integer curriculumCode, Integer academicYearId,
			Integer yearLevel, String courseTitle, String majorTitle) {
		super();
		this.userId = userId;
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
		this.activeStatus = activeStatus;
		this.activeDeactive = activeDeactive;
		this.image = image;
		this.studentId = studentId;
		this.studentNo = studentNo;
		this.parentNo = parentNo;
		this.curriculumCode = curriculumCode;
		this.academicYearId = academicYearId;
		this.yearLevel = yearLevel;
		this.courseTitle = courseTitle;
		this.majorTitle = majorTitle;
	}



	public UserAndStudent(Integer userId, String username, String password, String email, String contactNo,
			String firstName, String middleName, String lastName, String userType, LocalDate birthDate, String address,
			String civilStatus, String gender, String nationality, Boolean activeStatus, Boolean activeDeactive, String image,
			Integer studentId, Integer studentNo, Integer parentNo, Integer curriculumCode,
			Integer academicYearId) {
		this.userId = userId;
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
		this.activeStatus = activeStatus;
		this.activeDeactive = activeDeactive;
		this.image = image;
		this.studentId = studentId;
		this.studentNo = studentNo;
		this.parentNo = parentNo;
		this.curriculumCode = curriculumCode;
		this.academicYearId = academicYearId;
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

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(Integer studentNo) {
		this.studentNo = studentNo;
	}

	public Integer getParentNo() {
		return parentNo;
	}

	public void setParentNo(Integer parentNo) {
		this.parentNo = parentNo;
	}

	public Integer getCurriculumCode() {
		return curriculumCode;
	}

	public void setCurriculumCode(Integer curriculumCode) {
		this.curriculumCode = curriculumCode;
	}

	public Integer getAcademicYearId() {
		return academicYearId;
	}

	public void setAcademicYearId(Integer academicYearId) {
		this.academicYearId = academicYearId;
	}

	public Integer getYearLevel() {
		return yearLevel;
	}

	public void setYearLevel(Integer yearLevel) {
		this.yearLevel = yearLevel;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getMajorTitle() {
		return majorTitle;
	}

	public void setMajorTitle(String majorTitle) {
		this.majorTitle = majorTitle;
	}

	@Override
	public String toString() {
		return "UserAndStudent [userId=" + userId + ", username=" + username + ", password=" + password + ", email="
				+ email + ", contactNo=" + contactNo + ", firstName=" + firstName + ", middleName=" + middleName
				+ ", lastName=" + lastName + ", userType=" + userType + ", birthDate=" + birthDate + ", address="
				+ address + ", civilStatus=" + civilStatus + ", gender=" + gender + ", nationality=" + nationality
				+ ", activeStatus=" + activeStatus + ", activeDeactive=" + activeDeactive + ", image=" + image
				+ ", studentId=" + studentId + ", studentNo=" + studentNo + ", parentNo=" + parentNo
				+ ", curriculumCode=" + curriculumCode + ", academicYearId=" + academicYearId + ", yearLevel="
				+ yearLevel + ", courseTitle=" + courseTitle + ", majorTitle=" + majorTitle + "]";
	}
}
