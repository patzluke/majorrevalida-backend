package org.ssglobal.training.codes.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class ParentStudentAttendanceCopy {

	// Attendance Data
	private Integer studentAttendanceId;
	private Integer studentNo;
	private Integer loadId;
	private LocalDate attendanceDate;
	private String status;
	
	// Load Data
	private Integer professorNo;
	private Integer subjectCode;
	private Integer sectionId;
	private Integer roomId;
	private Integer deptCode;
	private String day;
	private LocalTime startTime;
	private LocalTime endTime;
	private Boolean activeDeactive;
	
	// Subject Data
	private Integer subjectId;
	private String abbrevation;
	private String subjectTitle;
	private Double units;
	private Double price;

	public ParentStudentAttendanceCopy() {
	}

	public ParentStudentAttendanceCopy(Integer studentAttendanceId, Integer studentNo, Integer loadId,
			LocalDate attendanceDate, String status, Integer professorNo, Integer subjectCode, Integer sectionId,
			Integer roomId, Integer deptCode, String day, LocalTime startTime, LocalTime endTime,
			Boolean activeDeactive, Integer subjectId, String abbrevation, String subjectTitle, Double units,
			Double price) {
		this.studentAttendanceId = studentAttendanceId;
		this.studentNo = studentNo;
		this.loadId = loadId;
		this.attendanceDate = attendanceDate;
		this.status = status;
		this.professorNo = professorNo;
		this.subjectCode = subjectCode;
		this.sectionId = sectionId;
		this.roomId = roomId;
		this.deptCode = deptCode;
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.activeDeactive = activeDeactive;
		this.subjectId = subjectId;
		this.abbrevation = abbrevation;
		this.subjectTitle = subjectTitle;
		this.units = units;
		this.price = price;
	}

	public Integer getStudentAttendanceId() {
		return studentAttendanceId;
	}

	public void setStudentAttendanceId(Integer studentAttendanceId) {
		this.studentAttendanceId = studentAttendanceId;
	}

	public Integer getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(Integer studentNo) {
		this.studentNo = studentNo;
	}

	public Integer getLoadId() {
		return loadId;
	}

	public void setLoadId(Integer loadId) {
		this.loadId = loadId;
	}

	public LocalDate getAttendanceDate() {
		return attendanceDate;
	}

	public void setAttendanceDate(LocalDate attendanceDate) {
		this.attendanceDate = attendanceDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getProfessorNo() {
		return professorNo;
	}

	public void setProfessorNo(Integer professorNo) {
		this.professorNo = professorNo;
	}

	public Integer getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(Integer subjectCode) {
		this.subjectCode = subjectCode;
	}

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(Integer deptCode) {
		this.deptCode = deptCode;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public Boolean getActiveDeactive() {
		return activeDeactive;
	}

	public void setActiveDeactive(Boolean activeDeactive) {
		this.activeDeactive = activeDeactive;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getAbbrevation() {
		return abbrevation;
	}

	public void setAbbrevation(String abbrevation) {
		this.abbrevation = abbrevation;
	}

	public String getSubjectTitle() {
		return subjectTitle;
	}

	public void setSubjectTitle(String subjectTitle) {
		this.subjectTitle = subjectTitle;
	}

	public Double getUnits() {
		return units;
	}

	public void setUnits(Double units) {
		this.units = units;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ParentStudentAttendanceCopy [studentAttendanceId=" + studentAttendanceId + ", studentNo=" + studentNo
				+ ", loadId=" + loadId + ", attendanceDate=" + attendanceDate + ", status=" + status + ", professorNo="
				+ professorNo + ", subjectCode=" + subjectCode + ", sectionId=" + sectionId + ", roomId=" + roomId
				+ ", deptCode=" + deptCode + ", day=" + day + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", activeDeactive=" + activeDeactive + ", subjectId=" + subjectId + ", abbrevation=" + abbrevation
				+ ", subjectTitle=" + subjectTitle + ", units=" + units + ", price=" + price + "]";
	}

}
