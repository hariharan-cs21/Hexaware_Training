package SIS.com;
import SIS.com.UserDefinedExceptions.CourseExceptions.*;

import java.util.*;

public class Course {
    private String courseId;
    private String courseName;
    private String courseCode;
    private String instructorName;
    private Teacher assignedTeacher;
    private List<Enrollment> enrollments;

    public Course(String courseId, String courseName, String courseCode, String instructorName) throws InvalidCourseDataException {
        if (courseId == null || courseName == null || courseCode == null || instructorName == null) {
            throw new InvalidCourseDataException("Invalid course data provided. All fields must be non-null.");
        }
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.instructorName = instructorName;
        this.enrollments = new ArrayList<>();
    }

    public void assignTeacher(Teacher teacher) throws TeacherNotFoundException {
        if (teacher == null) {
            throw new TeacherNotFoundException("Cannot assign a null teacher to the course.");
        }
        this.assignedTeacher = teacher;
    }

    public void updateCourseInfo(String courseCode, String courseName, String instructor) throws InvalidCourseDataException {
        if (courseCode == null || courseName == null || instructor == null) {
            throw new InvalidCourseDataException("Invalid course update data. Fields cannot be null.");
        }
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.instructorName = instructor;
    }

    public String getCourseId() {
        return courseId;
    }

    public void displayCourseInfo() {
        System.out.println("Course ID: " + courseId);
        System.out.println("Course Name: " + courseName);
        System.out.println("Course Code: " + courseCode);
        System.out.println("Instructor: " + instructorName);
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public Teacher getAssignedTeacher() {
        return assignedTeacher;
    }
}
