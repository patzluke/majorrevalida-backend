package org.ssglobal.training.codes.model;

import java.time.LocalDate;

public class UserAndParent {
	
	private Integer   userId;
    private String    username;
    private String    password;
    private String    firstName;
    private String    middleName;
    private String    lastName;
    private String    userType;
    private LocalDate birthDate;
    private String    address;
    private String    civilStatus;
    private String    gender;
    private String    nationality;
    private Boolean   activeDeactive;
    private String    image;
    
    private Integer parentId;
    
    public UserAndParent() {}

	public UserAndParent(Integer userId, String username, String password, String firstName, String middleName,
			String lastName, String userType, LocalDate birthDate, String address, String civilStatus, String gender,
			String nationality, Boolean activeDeactive, String image, Integer parentId) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
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
		this.parentId = parentId;
	}
	
	public UserAndParent (UserAndParent value) {
		this.userId = value.userId;
        this.username = value.username;
        this.password = value.password;
        this.firstName = value.firstName;
        this.middleName = value.middleName;
        this.lastName = value.lastName;
        this.userType = value.userType;
        this.birthDate = value.birthDate;
        this.address = value.address;
        this.civilStatus = value.civilStatus;
        this.gender = value.gender;
        this.nationality = value.nationality;
        this.activeDeactive = value.activeDeactive;
        this.image = value.image;
        
        this.parentId = value.parentId;
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

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Override
	public String toString() {
		return "UserAndParent [userId=" + userId + ", username=" + username + ", password=" + password + ", firstName="
				+ firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", userType=" + userType
				+ ", birthDate=" + birthDate + ", address=" + address + ", civilStatus=" + civilStatus + ", gender="
				+ gender + ", nationality=" + nationality + ", activeDeactive=" + activeDeactive + ", image=" + image
				+ ", parentId=" + parentId + "]";
	}
    
}
