package hexaware.case_study.exception;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(String message) {
        super(message);
    }
}