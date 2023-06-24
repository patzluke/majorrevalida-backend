package org.ssglobal.training.codes.model;

public class StudentCourseData {
	private Integer userId;
	private Integer curriculumCode;
	private Integer majorCode;
	private Integer courseCode;
	private Integer programCode;
	private String curriculumName;
	private String majorTitle;
	private String courseTitle;
	private String programTitle;

	public StudentCourseData() {
	}

	public StudentCourseData(Integer userId, Integer curriculumCode, Integer majorCode, Integer courseCode,
			Integer programCode, String curriculumName, String majorTitle, String courseTitle, String programTitle) {
		this.userId = userId;
		this.curriculumCode = curriculumCode;
		this.majorCode = majorCode;
		this.courseCode = courseCode;
		this.programCode = programCode;
		this.curriculumName = curriculumName;
		this.majorTitle = majorTitle;
		this.courseTitle = courseTitle;
		this.programTitle = programTitle;
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

	public Integer getMajorCode() {
		return majorCode;
	}

	public void setMajorCode(Integer majorCode) {
		this.majorCode = majorCode;
	}

	public Integer getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(Integer courseCode) {
		this.courseCode = courseCode;
	}

	public Integer getProgramCode() {
		return programCode;
	}

	public void setProgramCode(Integer programCode) {
		this.programCode = programCode;
	}

	public String getCurriculumName() {
		return curriculumName;
	}

	public void setCurriculumName(String curriculumName) {
		this.curriculumName = curriculumName;
	}

	public String getMajorTitle() {
		return majorTitle;
	}

	public void setMajorTitle(String majorTitle) {
		this.majorTitle = majorTitle;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getProgramTitle() {
		return programTitle;
	}

	public void setProgramTitle(String programTitle) {
		this.programTitle = programTitle;
	}

	@Override
	public String toString() {
		return "StudentCourseData [userId=" + userId + ", curriculumCode=" + curriculumCode + ", majorCode=" + majorCode
				+ ", courseCode=" + courseCode + ", programCode=" + programCode + ", curriculumName=" + curriculumName
				+ ", majorTitle=" + majorTitle + ", courseTitle=" + courseTitle + ", programTitle=" + programTitle
				+ "]";
	}

}
