package SIS.com;
import java.util.*;

public class Enrollment {
    private String enrollmentId;
    private Student student;
    private Course course;
    private Date enrollmentDate;

    public Enrollment(String enrollmentId, Student student, Course course, Date enrollmentDate) {
        this.enrollmentId = enrollmentId;
        this.student = student;
        this.course = course;
        this.enrollmentDate = enrollmentDate;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }
}
