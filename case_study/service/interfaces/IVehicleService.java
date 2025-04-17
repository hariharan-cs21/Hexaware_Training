package hexaware.case_study.service.interfaces;


import hexaware.case_study.entity.Vehicle;
import java.util.List;

public interface IVehicleService {
    Vehicle getVehicleById(int vehicleId);
    List<Vehicle> getAvailableVehicles();
    void addVehicle(Vehicle vehicle);
    void updateVehicleAvailability(int vehicleId);
    void removeVehicle(int vehicleId);
}
