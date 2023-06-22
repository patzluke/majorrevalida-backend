package org.ssglobal.training.codes.model;

import java.time.LocalDate;

public class UserAndAdmin {

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
    
    private Integer adminId;
    private Integer adminNo;   
    
    public UserAndAdmin() {}

    public UserAndAdmin(UserAndAdmin value) {
    	//for user
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
        
        //for admin
        this.adminId = value.adminId;
        this.adminNo = value.adminNo;
    }

    public UserAndAdmin(
        Integer   userId,
        String    username,
        String    password,
        String    firstName,
        String    middleName,
        String    lastName,
        String    userType,
        LocalDate birthDate,
        String    address,
        String    civilStatus,
        String    gender,
        String    nationality,
        Boolean   activeDeactive,
        String    image,
        
        Integer adminId,
        Integer adminNo
    ) {
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
        this.adminId = adminId;
        this.adminNo = adminNo;
    }

	public Integer getUserId() {
        return this.userId;
    }

    public UserAndAdmin setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getUsername() {
        return this.username;
    }

    public UserAndAdmin setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return this.password;
    }

    public UserAndAdmin setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public UserAndAdmin setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public UserAndAdmin setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return this.lastName;
    }

    public UserAndAdmin setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public LocalDate getBirthDate() {
        return this.birthDate;
    }

    public UserAndAdmin setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }

    public String getAddress() {
        return this.address;
    }

    public UserAndAdmin setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getCivilStatus() {
        return this.civilStatus;
    }

    public UserAndAdmin setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
        return this;
    }

    public String getGender() {
        return this.gender;
    }

    public UserAndAdmin setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getNationality() {
        return this.nationality;
    }

    public UserAndAdmin setNationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public Boolean getActiveDeactive() {
        return this.activeDeactive;
    }

    public UserAndAdmin setActiveDeactive(Boolean activeDeactive) {
        this.activeDeactive = activeDeactive;
        return this;
    }

    public String getImage() {
        return this.image;
    }

    public UserAndAdmin setImage(String image) {
        this.image = image;
        return this;
    }
    
    public Integer getAdminId() {
        return this.adminId;
    }

    public UserAndAdmin setAdminId(Integer adminId) {
        this.adminId = adminId;
        return this;
    }

    public Integer getAdminNo() {
        return this.adminNo;
    }

    public UserAndAdmin setAdminNo(Integer adminNo) {
        this.adminNo = adminNo;
        return this;
    }

	@Override
	public String toString() {
		return "UserAndAdmin [userId=" + userId + ", username=" + username + ", password=" + password + ", firstName="
				+ firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", userType=" + userType
				+ ", birthDate=" + birthDate + ", address=" + address + ", civilStatus=" + civilStatus + ", gender="
				+ gender + ", nationality=" + nationality + ", activeDeactive=" + activeDeactive + ", image=" + image
				+ ", adminId=" + adminId + ", adminNo=" + adminNo + "]";
	}
}
