package org.ssglobal.training.codes.model;

import java.time.LocalDate;

public class UserAndStudent {
	private Integer sem;
	private Integer year_level;
	private String username;
	private String password;
	private String first_name;
	private String middle_name;
	private String last_name;
	private String user_type;
	private LocalDate birth_date;
	private String address;
	private String civil_status;
	private String gender;
	private String nationality;
	private Boolean active_deactive;
	private String image;

	public UserAndStudent() {
	}

	public UserAndStudent(Integer sem, Integer year_level, String username, String password, String first_name,
			String middle_name, String last_name, String user_type, LocalDate birth_date, String address,
			String civil_status, String gender, String nationality, Boolean active_deactive, String image) {
		this.sem = sem;
		this.year_level = year_level;
		this.username = username;
		this.password = password;
		this.first_name = first_name;
		this.middle_name = middle_name;
		this.last_name = last_name;
		this.user_type = user_type;
		this.birth_date = birth_date;
		this.address = address;
		this.civil_status = civil_status;
		this.gender = gender;
		this.nationality = nationality;
		this.active_deactive = active_deactive;
		this.image = image;
	}

	public Integer getSem() {
		return sem;
	}

	public void setSem(Integer sem) {
		this.sem = sem;
	}

	public Integer getYear_level() {
		return year_level;
	}

	public void setYear_level(Integer year_level) {
		this.year_level = year_level;
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

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getMiddle_name() {
		return middle_name;
	}

	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public LocalDate getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(LocalDate birth_date) {
		this.birth_date = birth_date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCivil_status() {
		return civil_status;
	}

	public void setCivil_status(String civil_status) {
		this.civil_status = civil_status;
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

	public Boolean getActive_deactive() {
		return active_deactive;
	}

	public void setActive_deactive(Boolean active_deactive) {
		this.active_deactive = active_deactive;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "UserAndStudent [sem=%i, year_level=%i, username=%s, password=%s, "
				+ "first_name=%s, middle_name=%s, last_name=%s, user_type=%s, birth_date=%s, address=%s, "
				+ "civil_status=%s, gender=%s, nationality=%s, active_deactive=%s, image=%s]".formatted(sem, year_level,
						username, password, first_name, middle_name, last_name, user_type, birth_date, address,
						civil_status, gender, nationality, active_deactive, image);
	}

}
