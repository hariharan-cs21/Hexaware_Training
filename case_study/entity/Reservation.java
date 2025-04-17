package hexaware.case_study.entity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Reservation {
    private int reservationID;
    private int customerID;
    private int vehicleID;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double totalCost;
    private String status;

    public Reservation() {}

    public Reservation(int reservationID, int customerID, int vehicleID,
                       LocalDateTime startDate, LocalDateTime endDate, double totalCost, String status) {
        this.reservationID = reservationID;
        this.customerID = customerID;
        this.vehicleID = vehicleID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
        this.status = status;
    }

    public double calculateTotalCost(double dailyRate) {
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        return days * dailyRate;
    }
    public int getReservationID() {
        return reservationID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public String getStatus() {
        return status;
    }
    public void setCustomerId(int customerId) {
        this.customerID = customerId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleID = vehicleId;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setReservationId(int reservationId) {
        this.reservationID=reservationId;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}

