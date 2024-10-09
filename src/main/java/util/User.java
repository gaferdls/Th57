package util;

public class User {
	private String username;
	private String email;
	private char[] password;
	private SkillLevel skillLevel;
	private boolean isAdmin;

	public User(String username, String email, char[] password, SkillLevel skillLevel, boolean isAdmin) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.skillLevel = skillLevel;
		this.isAdmin = isAdmin;
	}

	public void setUsername(String firstName) {
		this.username = firstName;
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

	public SkillLevel getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(SkillLevel skillLevel) {
		this.skillLevel = skillLevel;
	}

	public void setAdmin(boolean admin) {
		isAdmin = admin;
	}
}