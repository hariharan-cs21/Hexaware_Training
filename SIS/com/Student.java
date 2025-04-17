package SIS.com;

import java.util.*;

public class Student {
    private String studentId;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String email;
    private String phoneNumber;
    private List<Course> enrolledCourses;
    private List<Payment> paymentHistory;

    public Student(String studentId, String firstName, String lastName, Date dateOfBirth, String email, String phoneNumber) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.enrolledCourses = new ArrayList<>();
        this.paymentHistory = new ArrayList<>();
    }

    public void enrollInCourse(Course course) {
        enrolledCourses.add(course);
    }

    public void updateStudentInfo(String firstName, String lastName, Date dateOfBirth, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void makePayment(Payment payment) {
        paymentHistory.add(payment);
    }

    public void displayStudentInfo() {
        System.out.println("Student ID: " + studentId);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("Email: " + email);
        System.out.println("Phone Number: " + phoneNumber);
    }
    public String getStudentId(){
        return studentId;
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public List<Payment> getPaymentHistory() {
        return paymentHistory;
    }
}
