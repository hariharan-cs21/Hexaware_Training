package hexaware.case_study.entity;

import java.time.LocalDate;

public class Customer {
    private int customerID;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String username;
    private String password;
    private LocalDate registrationDate;

    public Customer() {}

    public Customer(int customerID, String firstName, String lastName, String email, String phoneNumber,
                    String address, String username, String password, LocalDate registrationDate) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.username = username;
        this.password = password;
        this.registrationDate = registrationDate;
    }

    public boolean authenticate(String password) {
        return this.password.equals(password);
    }
    public int getCustomerId() {
        return customerID;
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

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

}
