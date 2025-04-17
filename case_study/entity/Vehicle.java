package hexaware.case_study.entity;


public class Vehicle {
    private int vehicleID;
    private String model;
    private String make;
    private int year;
    private String color;
    private String registrationNumber;
    private boolean availability;
    private double dailyRate;

    public Vehicle() {}

    public Vehicle(int vehicleID, String model, String make, int year, String color,
                   String registrationNumber, boolean availability, double dailyRate) {
        this.vehicleID = vehicleID;
        this.model = model;
        this.make = make;
        this.year = year;
        this.color = color;
        this.registrationNumber = registrationNumber;
        this.availability = availability;
        this.dailyRate = dailyRate;
    }
    public Vehicle(String model, String make, int year, String color,
                   String registrationNumber, boolean availability, double dailyRate) {
        this.model = model;
        this.make = make;
        this.year = year;
        this.color = color;
        this.registrationNumber = registrationNumber;
        this.availability = availability;
        this.dailyRate = dailyRate;
    }


    public int getVehicleId() {
        return vehicleID;
    }

    public String getModel() {
        return model;
    }

    public String getMake() {
        return make;
    }

    public int getYear() {
        return year;
    }

    public String getColor() {
        return color;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public boolean isAvailability() {
        return availability;
    }

    public double getDailyRate() {
        return dailyRate;
    }


    public void setVehicleId(int vehicleId) {
        this.vehicleID = vehicleId;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }


}
