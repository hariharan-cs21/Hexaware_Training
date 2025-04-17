package hexaware.case_study.entity;

import java.time.LocalDate;

public class Admin {
    private int adminID;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String username;
    private String password;
    private String role;
    private LocalDate joinDate;

    public Admin() {}

    public Admin(int adminID, String firstName, String lastName, String email, String phoneNumber,
                 String username, String password, String role, LocalDate joinDate) {
        this.adminID = adminID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.password = password;
        this.role = role;
        this.joinDate = joinDate;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
    public int getAdminID() {
        return adminID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

}
