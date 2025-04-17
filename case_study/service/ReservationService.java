package hexaware.case_study.service;


import hexaware.case_study.entity.Reservation;
import hexaware.case_study.exception.DatabaseConnectionException;
import hexaware.case_study.exception.ReservationException;
import hexaware.case_study.service.interfaces.IReservationService;
import hexaware.case_study.util.DatabaseContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements IReservationService {

    @Override
    public Reservation getReservationById(int reservationId) {
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Reservations WHERE ReservationID = ?")) {
            stmt.setInt(1, reservationId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return map(rs);
            else throw new ReservationException("Reservation not found");
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    @Override

    public void createReservation(Reservation reservation) {
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO Reservations (CustomerID, VehicleID, StartDate, EndDate, TotalCost, Status) VALUES (?, ?, ?, ?, ?, ?)")) {
            stmt.setInt(1, reservation.getCustomerID());
            stmt.setInt(2, reservation.getVehicleID());
            stmt.setTimestamp(3, Timestamp.valueOf(reservation.getStartDate()));
            stmt.setTimestamp(4, Timestamp.valueOf(reservation.getEndDate()));
            stmt.setDouble(5, reservation.getTotalCost());
            stmt.setString(6, reservation.getStatus());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    @Override
    public void updateReservation(Reservation reservation) {
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE Reservations SET StartDate=?, EndDate=?, TotalCost=?, Status=? WHERE ReservationID=?")) {
            stmt.setTimestamp(1, Timestamp.valueOf(reservation.getStartDate()));
            stmt.setTimestamp(2, Timestamp.valueOf(reservation.getEndDate()));
            stmt.setDouble(3, reservation.getTotalCost());
            stmt.setString(4, reservation.getStatus());
            stmt.setInt(5, reservation.getReservationID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    @Override
    public void cancelReservation(int reservationId) {
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Reservations WHERE ReservationId = ?")) {
            stmt.setInt(1, reservationId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new IllegalArgumentException("No reservation found with ID: " + reservationId);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    private Reservation map(ResultSet rs) throws SQLException {
        return new Reservation(
                rs.getInt("reservationId"),
                rs.getInt("CustomerID"),
                rs.getInt("vehicleId"),
                rs.getTimestamp("startDate").toLocalDateTime(),
                rs.getTimestamp("endDate").toLocalDateTime(),
                rs.getDouble("totalCost"),
                rs.getString("status")
        );
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM reservations";
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Reservation r = new Reservation();
                r.setReservationId(rs.getInt("reservationId"));
                r.setCustomerId(rs.getInt("customerId"));
                r.setVehicleId(rs.getInt("vehicleId"));
                r.setStartDate(rs.getTimestamp("startDate").toLocalDateTime());
                r.setEndDate(rs.getTimestamp("endDate").toLocalDateTime());
                r.setStatus(rs.getString("status"));
                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<Reservation> getReservationsByCustomerId(int customerId) {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations WHERE customerId = ?";
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reservation r = new Reservation();
                r.setReservationId(rs.getInt("reservationId"));
                r.setCustomerId(rs.getInt("customerId"));
                r.setVehicleId(rs.getInt("vehicleId"));
                r.setStartDate(rs.getTimestamp("startDate").toLocalDateTime());
                r.setEndDate(rs.getTimestamp("endDate").toLocalDateTime());
                r.setTotalCost(rs.getDouble("totalCost"));
                r.setStatus(rs.getString("status"));
                reservations.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

}
