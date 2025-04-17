package SIS.com;

import java.util.*;

public class Teacher {
    private String teacherId;
    private String firstName;
    private String lastName;
    private String email;
    private List<Course> assignedCourses;

    public Teacher(String teacherId, String firstName, String lastName, String email) {
        this.teacherId = teacherId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.assignedCourses = new ArrayList<>();
    }
    public String getTeacherId() {
        return teacherId;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void updateTeacherInfo(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void displayTeacherInfo() {
        System.out.println("Teacher ID: " + teacherId);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Email: " + email);
    }

    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }

    public void assignCourse(Course course) {
        assignedCourses.add(course);
    }
}

