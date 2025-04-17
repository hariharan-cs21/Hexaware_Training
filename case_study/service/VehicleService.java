package hexaware.case_study.service;

import hexaware.case_study.entity.Vehicle;
import hexaware.case_study.util.DatabaseContext;
import hexaware.case_study.service.interfaces.IVehicleService;
import hexaware.case_study.exception.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleService implements IVehicleService {

    @Override
    public Vehicle getVehicleById(int vehicleId) {
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vehicles WHERE VehicleID = ?")) {
            stmt.setInt(1, vehicleId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return map(rs);
            } else {
                throw new VehicleNotFoundException("Vehicle not found");
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    @Override
    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Vehicles WHERE Availability = 1");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                vehicles.add(map(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
        return vehicles;
    }

    @Override
    public void addVehicle(Vehicle vehicle) {
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO Vehicles (Model, Make, Year, Color, RegistrationNumber, Availability, DailyRate) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, vehicle.getModel());
            stmt.setString(2, vehicle.getMake());
            stmt.setInt(3, vehicle.getYear());
            stmt.setString(4, vehicle.getColor());
            stmt.setString(5, vehicle.getRegistrationNumber());
            stmt.setBoolean(6, vehicle.isAvailability());
            stmt.setDouble(7, vehicle.getDailyRate());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

//    @Override
//    public void updateVehicle(Vehicle vehicle) {
//        try (Connection conn = DatabaseContext.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(
//                     "UPDATE Vehicles SET Model=?, Make=?, Year=?, Color=?, RegistrationNumber=?, Availability=?, DailyRate=? WHERE VehicleID=?")) {
//            stmt.setString(1, vehicle.getModel());
//            stmt.setString(2, vehicle.getMake());
//            stmt.setInt(3, vehicle.getYear());
//            stmt.setString(4, vehicle.getColor());
//            stmt.setString(5, vehicle.getRegistrationNumber());
//            stmt.setBoolean(6, vehicle.isAvailability());
//            stmt.setDouble(7, vehicle.getDailyRate());
//            stmt.setInt(8, vehicle.getVehicleId());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            throw new DatabaseConnectionException(e.getMessage());
//        }
//    }

    @Override
    public void removeVehicle(int vehicleId) {
        try (Connection conn = DatabaseContext.getConnection()) {
            try (PreparedStatement deleteReservationsStmt = conn.prepareStatement("DELETE FROM Reservations WHERE VehicleID = ?")) {
                deleteReservationsStmt.setInt(1, vehicleId);
                int reservationsDeleted = deleteReservationsStmt.executeUpdate();
                System.out.println(reservationsDeleted + " reservation(s) deleted.");

                try (PreparedStatement deleteVehicleStmt = conn.prepareStatement("DELETE FROM Vehicles WHERE VehicleID = ?")) {
                    deleteVehicleStmt.setInt(1, vehicleId);
                    int vehicleDeleted = deleteVehicleStmt.executeUpdate();
                    if (vehicleDeleted == 0) {
                        System.out.println("No vehicle found with the given ID.");
                    } else {
                        System.out.println("Vehicle removed successfully.");
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error while deleting vehicle or reservations: " + e.getMessage());
        }
    }


    private Vehicle map(ResultSet rs) throws SQLException {
        return new Vehicle(
                rs.getInt("VehicleID"),
                rs.getString("Model"),
                rs.getString("Make"),
                rs.getInt("Year"),
                rs.getString("Color"),
                rs.getString("RegistrationNumber"),
                rs.getBoolean("Availability"),
                rs.getDouble("DailyRate")
        );
    }
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicles";
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vehicle v = new Vehicle();
                v.setVehicleId(rs.getInt("vehicleId"));
                v.setMake(rs.getString("make"));
                v.setModel(rs.getString("model"));
                v.setYear(rs.getInt("year"));
                v.setDailyRate(rs.getDouble("dailyRate"));
                vehicles.add(v);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }
    public void updateVehicleAvailability(int vehicleId) {
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Vehicles SET availability = 0 WHERE VehicleId = ?")) {
            stmt.setInt(1, vehicleId);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Vehicle availability updated to unavailable.");
            } else {
                System.out.println("Vehicle ID not found ");
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error updating vehicle availability: " + e.getMessage());
        }
    }



}
