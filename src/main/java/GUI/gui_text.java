package GUI;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import util.Role;
import util.SkillLevel;


public class gui_text extends Application {
    private String email;
    private String username;
    private char[] password;  // Stored securely in a char array
    private boolean oneTimePassword;
    private LocalDateTime passwordExpiration;
    private String firstName;
    private String middleName;
    private String lastName;
    private String preferredFirstName;
    private SkillLevel skillLevel;
    private List<Role> roles;

    public void User(String username, char[] password, boolean oneTimePassword, LocalDateTime passwordExpiration) {
        this.setUsername(username);
        this.setPassword(password);
        this.setOneTimePassword(oneTimePassword);
        this.setPasswordExpiration(passwordExpiration);
        this.setRoles(new ArrayList<>());
    }

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	

    // Getters and Setters
	
	
	
	
	
	
	
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public SkillLevel getSkillLevel() {
		return skillLevel;
	}

	public void setSkillLevel(SkillLevel skillLevel) {
		this.skillLevel = skillLevel;
	}

	public String getPreferredFirstName() {
		return preferredFirstName;
	}

	public void setPreferredFirstName(String preferredFirstName) {
		this.preferredFirstName = preferredFirstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public LocalDateTime getPasswordExpiration() {
		return passwordExpiration;
	}

	public void setPasswordExpiration(LocalDateTime passwordExpiration) {
		this.passwordExpiration = passwordExpiration;
	}

	public boolean isOneTimePassword() {
		return oneTimePassword;
	}

	public void setOneTimePassword(boolean oneTimePassword) {
		this.oneTimePassword = oneTimePassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

