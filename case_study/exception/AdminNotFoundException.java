package hexaware.case_study.exception;


public class AdminNotFoundException extends RuntimeException {
    public AdminNotFoundException(String message) {
        super(message);
    }
}

