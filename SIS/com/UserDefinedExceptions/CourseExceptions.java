package SIS.com.UserDefinedExceptions;

public class CourseExceptions {

    public static class InvalidCourseDataException extends Exception {
        public InvalidCourseDataException(String message) {
            super(message);
        }
    }

    public static class TeacherNotFoundException extends Exception {
        public TeacherNotFoundException(String message) {
            super(message);
        }
    }
}
