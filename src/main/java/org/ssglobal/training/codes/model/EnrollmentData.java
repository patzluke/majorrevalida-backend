package org.ssglobal.training.codes.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class EnrollmentData {
	private Integer enrollmentId;
	private Integer studentNo;
	private String firstName;
	private String middleName;
	private String lastName;
	private Integer majorCode;
	private String majorTitle;
	private Integer courseCode;
	private String courseTitle;
	private Integer yearLevel;
	private Integer semester;
	private String status;
	private Integer sectionId;
	private String paymentStatus;
	private Integer curriculumCode;
	private Integer academicYearId;

	public EnrollmentData() {

	}

	public EnrollmentData(Integer studentNo, String firstName, String middleName, String lastName, Integer majorCode,
			String majorTitle, Integer courseCode, String courseTitle, Integer yearLevel, String status,
			Integer sectionId, String paymentStatus, Integer curriculumCode, Integer semester, Integer enrollmentId,
			Integer academicYearId) {
		this.studentNo = studentNo;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.majorCode = majorCode;
		this.majorTitle = majorTitle;
		this.courseCode = courseCode;
		this.courseTitle = courseTitle;
		this.yearLevel = yearLevel;
		this.status = status;
		this.sectionId = sectionId;
		this.paymentStatus = paymentStatus;
		this.curriculumCode = curriculumCode;
		this.semester = semester;
		this.enrollmentId = enrollmentId;
		this.academicYearId = academicYearId;
	}
}
