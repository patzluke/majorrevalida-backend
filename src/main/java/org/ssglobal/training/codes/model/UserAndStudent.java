package org.ssglobal.training.codes.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
	private String curriculumName;
	private Integer academicYearId;
	
	private Integer yearLevel;
	private Integer courseCode;
	private String courseTitle;
	private Integer majorCode;
	private String majorTitle;
	
	// This is the attributes for student
	private String sectionName;
	
	public UserAndStudent() {
	}

	public UserAndStudent(Integer userId, String username, String password, String email, String contactNo,
			String firstName, String middleName, String lastName, String userType, LocalDate birthDate, String address,
			String civilStatus, String gender, String nationality, Boolean activeStatus, Boolean activeDeactive, String image,
			Integer studentId, Integer studentNo, Integer parentNo, Integer curriculumCode, Integer yearLevel,
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
		this.yearLevel = yearLevel;
		this.academicYearId = academicYearId;
	}
}
