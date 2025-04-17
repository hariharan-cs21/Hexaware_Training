package hexaware.case_study.exception;

public class DatabaseConnectionException extends RuntimeException {
    public DatabaseConnectionException(String message) {
        super(message);
    }
}