package util;

import java.time.LocalDateTime;

public class User {
	private String username;
	private String email;
	private char[] password;
	private String skillLevel;
	private boolean isAdmin;
	private boolean isOneTimePassword;
	private LocalDateTime passwordExpiration;

	public User(String username, String email, char[] password, String skillLevel, boolean isAdmin) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.skillLevel = skillLevel;
		this.isAdmin = isAdmin;
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

	public LocalDateTime getPasswordExpiration() {
		return passwordExpiration;
	}

	public void setPasswordExpiration(LocalDateTime passwordExpiration) {
		this.passwordExpiration = passwordExpiration;
	}
}