package org.ssglobal.training.codes.model;

public class EnrollmentData {
	private Integer studentNo;
	private String firstName;
	private String middleName;
	private String lastName;
	private Integer majorCode;
	private String majorTitle;
	private Integer courseCode;
	private String courseTitle;
	private Integer yearLevel;
	private String status;
	private Integer sectionId;
	private String paymentStatus;

	public EnrollmentData() {

	}

	public EnrollmentData(Integer studentNo, String firstName, String middleName, String lastName, Integer majorCode,
			String majorTitle, Integer courseCode, String courseTitle, Integer yearLevel, String status,
			Integer sectionId, String paymentStatus) {
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
	}

	public Integer getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(Integer studentNo) {
		this.studentNo = studentNo;
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

	public Integer getMajorCode() {
		return majorCode;
	}

	public void setMajorCode(Integer majorCode) {
		this.majorCode = majorCode;
	}

	public String getMajorTitle() {
		return majorTitle;
	}

	public void setMajorTitle(String majorTitle) {
		this.majorTitle = majorTitle;
	}

	public Integer getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(Integer courseCode) {
		this.courseCode = courseCode;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public Integer getYearLevel() {
		return yearLevel;
	}

	public void setYearLevel(Integer yearLevel) {
		this.yearLevel = yearLevel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Override
	public String toString() {
		return "EnrollmentData [studentNo=" + studentNo + ", firstName=" + firstName + ", middleName=" + middleName
				+ ", lastName=" + lastName + ", majorCode=" + majorCode + ", majorTitle=" + majorTitle + ", courseCode="
				+ courseCode + ", courseTitle=" + courseTitle + ", yearLevel=" + yearLevel + ", status=" + status
				+ ", sectionId=" + sectionId + ", paymentStatus=" + paymentStatus + "]";
	}

}
