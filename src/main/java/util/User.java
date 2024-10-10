package util;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

public class User {
	private String username;
	private String email;
	private char[] password;
	private String skillLevel;
	private boolean isAdmin;
	private boolean isOneTimePassword;
	private Time passwordExpiration;
	private boolean isStudent, isInstructor;

	public User(String username, String email, char[] password, boolean isOneTimePassword, Time passwordExpiration, String skillLevel, boolean isAdmin, boolean isStudent, boolean isInstructor) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.skillLevel = skillLevel;
		this.isAdmin = isAdmin;
		this.isStudent = isStudent;
		this.isInstructor = isInstructor;
		this.passwordExpiration = passwordExpiration;
	}

	public void setUsername(String firstName) {
		this.username = firstName;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public char[] getPassword() {
		return password;
	}

	public String getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(String skillLevel) {
		this.skillLevel = skillLevel;
	}

	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}

	public void setIsOneTimePassword(boolean isOneTimePassword) {
		isOneTimePassword = isOneTimePassword;
	}

	public boolean isOneTimePassword() {
		return isOneTimePassword;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setOneTimePassword(boolean oneTimePassword) {
		isOneTimePassword = oneTimePassword;
	}

	public Time getPasswordExpiration() {
		return passwordExpiration;
	}

	public void setPasswordExpiration(Time passwordExpiration) {
		this.passwordExpiration = passwordExpiration;
	}

	public boolean isStudent() {
		return isStudent;
	}

	public void setStudent(boolean student) {
		isStudent = student;
	}

	public boolean isInstructor() {
		return isInstructor;
	}

	public void setInstructor(boolean instructor) {
		isInstructor = instructor;
	}
}