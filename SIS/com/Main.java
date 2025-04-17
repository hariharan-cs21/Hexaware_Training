package SIS.com;
import SIS.com.UserDefinedExceptions.CourseExceptions;

import java.util.*;

public class Main {
    public static void main(String[] args) throws CourseExceptions.TeacherNotFoundException, CourseExceptions.InvalidCourseDataException {
        Teacher teacher1 = new Teacher("T001", "Hari", "H", "hari@gmail.com");

        Course course1 = new Course("C001", "Java Programming", "CS101", "Hari");
        Course course2 = new Course("C002", "DSA", "CS102", "Hari");

        course1.assignTeacher(teacher1);
        course2.assignTeacher(teacher1);

        Student student1 = new Student("S001", "Student", "1", new Date(), "student1@gmail.com", "9987300198");

        student1.enrollInCourse(course1);
        student1.enrollInCourse(course2);

        Payment payment1 = new Payment("P001", student1, 500.0, new Date());
        student1.makePayment(payment1);

        student1.displayStudentInfo();

        System.out.println("\nEnrolled Courses:");
        int ind=1;
        for (Course c : student1.getEnrolledCourses()) {
            System.out.println(ind + ". ");
            c.displayCourseInfo();
            ind++;

        }

        System.out.println("\nPayment History:");
        for (Payment p : student1.getPaymentHistory()) {
            System.out.println("Payment ID: " + p.getPaymentId() + " Amount: " + p.getPaymentAmount() + " Date: " + p.getPaymentDate());
        }
        System.out.println();

        teacher1.displayTeacherInfo();

        System.out.println("\nCourse Information:");
        course1.displayCourseInfo();
        System.out.println("Assigned Teacher: " + course1.getAssignedTeacher().getFirstName() + " " + course1.getAssignedTeacher().getLastName());
    }
}

