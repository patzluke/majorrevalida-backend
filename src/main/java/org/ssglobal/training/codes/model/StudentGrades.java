package org.ssglobal.training.codes.model;

import java.time.LocalDate;

public class StudentGrades {
	private Integer gradeId;
	private Integer studentNo;
	private Integer subjectDetailHisId;
	private Integer enrollSubjectId;
	private Double prelimGrade;
	private Double finalsGrade;
	private Double totalGrade;
	private String comment;
	private LocalDate datePrelimGradeInserted;
	private LocalDate dateFinalsGradeInserted;
	private LocalDate datePrelimGradeModified;
	private LocalDate dateFinalsGradeModified;
	private String remarks;
	private boolean isSubmitted;
	private String abbrevation;
	private String subjectTitle;
	private Integer units;
	
	private Integer academicYear;
	private Integer semester;

	public StudentGrades() {
	}

	public StudentGrades(Integer gradeId, Integer studentNo, Integer subjectDetailHisId, Integer enrollSubjectId,
			Double prelimGrade, Double finalsGrade, Double totalGrade, String comment,
			LocalDate datePrelimGradeInserted, LocalDate dateFinalsGradeInserted, LocalDate datePrelimGradeModified,
			LocalDate dateFinalsGradeModified, String remarks, boolean isSubmitted, String abbrevation,
			String subjectTitle, Integer units, Integer academicYear, Integer semester) {
		super();
		this.gradeId = gradeId;
		this.studentNo = studentNo;
		this.subjectDetailHisId = subjectDetailHisId;
		this.enrollSubjectId = enrollSubjectId;
		this.prelimGrade = prelimGrade;
		this.finalsGrade = finalsGrade;
		this.totalGrade = totalGrade;
		this.comment = comment;
		this.datePrelimGradeInserted = datePrelimGradeInserted;
		this.dateFinalsGradeInserted = dateFinalsGradeInserted;
		this.datePrelimGradeModified = datePrelimGradeModified;
		this.dateFinalsGradeModified = dateFinalsGradeModified;
		this.remarks = remarks;
		this.isSubmitted = isSubmitted;
		this.abbrevation = abbrevation;
		this.subjectTitle = subjectTitle;
		this.units = units;
		this.academicYear = academicYear;
		this.semester = semester;
	}

	public StudentGrades(Integer gradeId, Integer studentNo, Integer subjectDetailHisId, Integer enrollSubjectId,
			Double prelimGrade, Double finalsGrade, Double totalGrade, String comment,
			LocalDate datePrelimGradeInserted, LocalDate dateFinalsGradeInserted, LocalDate datePrelimGradeModified,
			LocalDate dateFinalsGradeModified, String remarks, boolean isSubmitted, String abbrevation,
			String subjectTitle, Integer units) {
		this.gradeId = gradeId;
		this.studentNo = studentNo;
		this.subjectDetailHisId = subjectDetailHisId;
		this.enrollSubjectId = enrollSubjectId;
		this.prelimGrade = prelimGrade;
		this.finalsGrade = finalsGrade;
		this.totalGrade = totalGrade;
		this.comment = comment;
		this.datePrelimGradeInserted = datePrelimGradeInserted;
		this.dateFinalsGradeInserted = dateFinalsGradeInserted;
		this.datePrelimGradeModified = datePrelimGradeModified;
		this.dateFinalsGradeModified = dateFinalsGradeModified;
		this.remarks = remarks;
		this.isSubmitted = isSubmitted;
		this.abbrevation = abbrevation;
		this.subjectTitle = subjectTitle;
		this.units = units;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public Integer getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(Integer studentNo) {
		this.studentNo = studentNo;
	}

	public Integer getSubjectDetailHisId() {
		return subjectDetailHisId;
	}

	public void setSubjectDetailHisId(Integer subjectDetailHisId) {
		this.subjectDetailHisId = subjectDetailHisId;
	}

	public Integer getEnrollSubjectId() {
		return enrollSubjectId;
	}

	public void setEnrollSubjectId(Integer enrollSubjectId) {
		this.enrollSubjectId = enrollSubjectId;
	}

	public Double getPrelimGrade() {
		return prelimGrade;
	}

	public void setPrelimGrade(Double prelimGrade) {
		this.prelimGrade = prelimGrade;
	}

	public Double getFinalsGrade() {
		return finalsGrade;
	}

	public void setFinalsGrade(Double finalsGrade) {
		this.finalsGrade = finalsGrade;
	}

	public Double getTotalGrade() {
		return totalGrade;
	}

	public void setTotalGrade(Double totalGrade) {
		this.totalGrade = totalGrade;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDate getDatePrelimGradeInserted() {
		return datePrelimGradeInserted;
	}

	public void setDatePrelimGradeInserted(LocalDate datePrelimGradeInserted) {
		this.datePrelimGradeInserted = datePrelimGradeInserted;
	}

	public LocalDate getDateFinalsGradeInserted() {
		return dateFinalsGradeInserted;
	}

	public void setDateFinalsGradeInserted(LocalDate dateFinalsGradeInserted) {
		this.dateFinalsGradeInserted = dateFinalsGradeInserted;
	}

	public LocalDate getDatePrelimGradeModified() {
		return datePrelimGradeModified;
	}

	public void setDatePrelimGradeModified(LocalDate datePrelimGradeModified) {
		this.datePrelimGradeModified = datePrelimGradeModified;
	}

	public LocalDate getDateFinalsGradeModified() {
		return dateFinalsGradeModified;
	}

	public void setDateFinalsGradeModified(LocalDate dateFinalsGradeModified) {
		this.dateFinalsGradeModified = dateFinalsGradeModified;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isSubmitted() {
		return isSubmitted;
	}

	public void setSubmitted(boolean isSubmitted) {
		this.isSubmitted = isSubmitted;
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

	public Integer getUnits() {
		return units;
	}

	public void setUnits(Integer units) {
		this.units = units;
	}

	public Integer getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(Integer academicYear) {
		this.academicYear = academicYear;
	}

	public Integer getSemester() {
		return semester;
	}

	public void setSemester(Integer semester) {
		this.semester = semester;
	}

	@Override
	public String toString() {
		return "StudentGrades [gradeId=" + gradeId + ", studentNo=" + studentNo + ", subjectDetailHisId="
				+ subjectDetailHisId + ", enrollSubjectId=" + enrollSubjectId + ", prelimGrade=" + prelimGrade
				+ ", finalsGrade=" + finalsGrade + ", totalGrade=" + totalGrade + ", comment=" + comment
				+ ", datePrelimGradeInserted=" + datePrelimGradeInserted + ", dateFinalsGradeInserted="
				+ dateFinalsGradeInserted + ", datePrelimGradeModified=" + datePrelimGradeModified
				+ ", dateFinalsGradeModified=" + dateFinalsGradeModified + ", remarks=" + remarks + ", isSubmitted="
				+ isSubmitted + ", abbrevation=" + abbrevation + ", subjectTitle=" + subjectTitle + ", units=" + units
				+ ", academicYear=" + academicYear + ", semester=" + semester + "]";
	}

}
