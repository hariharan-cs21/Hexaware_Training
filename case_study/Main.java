package hexaware.case_study;

import hexaware.case_study.entity.*;
import hexaware.case_study.service.*;
import hexaware.case_study.exception.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static boolean isValidEmail(String email) {
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    private static boolean isValidMobile(String phone) {
        return phone.matches("^[6-9]\\d{9}$");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomerService customerService = new CustomerService();
        VehicleService vehicleService = new VehicleService();
        ReservationService reservationService = new ReservationService();
        AdminService adminService = new AdminService();

        boolean running = true;

        while (running) {
            System.out.println("\nVehicle Rental System ");
            System.out.println("1. Customer Login");
            System.out.println("2. Customer Register");
            System.out.println("3. Admin Login");
            System.out.println("4. Admin Register");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int mainOption = scanner.nextInt();
            scanner.nextLine();

            switch (mainOption) {
                case 1 -> handleCustomerLogin(scanner, customerService, vehicleService, reservationService);
                case 2 -> handleCustomerRegister(scanner, customerService);
                case 3 -> handleAdminLogin(scanner, adminService, vehicleService, reservationService, customerService);
                case 4 -> handleAdminRegister(scanner, adminService);
                case 5 -> {
                    System.out.println("Goodbye");
                    running = false;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
        scanner.close();
    }

    private static void handleCustomerLogin(Scanner scanner, CustomerService customerService, VehicleService vehicleService, ReservationService reservationService) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        try {
        Customer loggedInCustomer = customerService.authenticate(username, password);
        if (loggedInCustomer == null) {
            System.out.println("Login failed.");
            return;
        }


        System.out.println("Welcome, " + loggedInCustomer.getFirstName());

        boolean logout = false;
        while (!logout) {
            System.out.println("\nCustomer Menu");
            System.out.println("1. View Available Vehicles");
            System.out.println("2. Make Reservation");
            System.out.println("3. View My Reservations");
            System.out.println("4. Cancel Reservations");
            System.out.println("5. Logout");
            System.out.print("Choose: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    List<Vehicle> vehicles = vehicleService.getAvailableVehicles();
                    for (Vehicle v : vehicles) {
                        System.out.println(v.getVehicleId() + ": " + v.getMake() + " " + v.getModel() + " ₹" + v.getDailyRate());
                    }
                }
                case 2 -> {
                    System.out.print("Vehicle ID: ");
                    int vehicleId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Start Date (yyyy-mm-dd): ");
                    String startStr = scanner.nextLine();
                    System.out.print("End Date (yyyy-mm-dd): ");
                    String endStr = scanner.nextLine();

                    Reservation reservation = new Reservation();
                    reservation.setCustomerId(loggedInCustomer.getCustomerId());
                    reservation.setVehicleId(vehicleId);
                    reservation.setStartDate(LocalDateTime.parse(startStr + "T00:00:00"));
                    reservation.setEndDate(LocalDateTime.parse(endStr + "T00:00:00"));
                    reservation.setStatus("Pending");
                    Vehicle vehicle = vehicleService.getVehicleById(vehicleId);
                    double totalCost = reservation.calculateTotalCost(vehicle.getDailyRate());
                    reservation.setTotalCost(totalCost);
                    System.out.println("Inserting reservation with total cost: " + reservation.getTotalCost());

                    reservationService.createReservation(reservation);
                    vehicleService.updateVehicleAvailability(vehicleId);
                    System.out.println("Reservation created.");
                }
                case 3 -> {
                    System.out.println("\nYour Reservations");
                    List<Reservation> reservations = reservationService.getReservationsByCustomerId(loggedInCustomer.getCustomerId());
                    if (reservations.isEmpty()) {
                        System.out.println("You have no reservations.");
                    } else {
                        for (Reservation r : reservations) {
                            System.out.println("Reservation ID: " + r.getReservationID());
                            System.out.println("Vehicle ID: " + r.getVehicleID());
                            System.out.println("Start: " + r.getStartDate());
                            System.out.println("End: " + r.getEndDate());
                            System.out.println("Status: " + r.getStatus());
                            System.out.println("Total Cost: ₹" + r.getTotalCost());
                            System.out.println("--------------------------------");
                        }
                    }
                }
                case 4 -> {
                    System.out.print("Vehicle ID: ");
                    int vehicleId = scanner.nextInt();
                    try {
                        reservationService.cancelReservation(vehicleId);
                        System.out.println("Reservation deleted.");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (DatabaseConnectionException e) {
                        System.out.println("Database error occurred: " + e.getMessage());
                    }
                }
                case 5 -> logout = true;
                default -> System.out.println("Invalid.");
            }
        }
        } catch (CustomerNotFoundException e) {
            System.out.println("Login failed: " + e.getMessage());
        } catch (DatabaseConnectionException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static void handleCustomerRegister(Scanner scanner, CustomerService customerService) {
        System.out.println("Register New Customer");
        System.out.print("First Name: ");
        String fname = scanner.nextLine();
        System.out.print("Last Name: ");
        String lname = scanner.nextLine();
        String email;
        while (true) {
            System.out.print("Email: ");
            email = scanner.nextLine();
            if (isValidEmail(email)) break;
            System.out.println("Invalid email format");
        }

        String phone;
        while (true) {
            System.out.print("Phone (10 digits): ");
            phone = scanner.nextLine();
            if (isValidMobile(phone)) break;
            System.out.println("Invalid mobile number. Must start with 6-9 and be 10 digits.");
        }
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Username: ");
        String uname = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        Customer customer = new Customer(0, fname, lname, email, phone, address, uname, pass, LocalDate.now());
        customerService.registerCustomer(customer);
        System.out.println("Customer registered.");
    }

    private static void handleAdminLogin(Scanner scanner, AdminService adminService, VehicleService vehicleService, ReservationService reservationService, CustomerService customerService) {
        System.out.print("Admin Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
try{
        Admin admin = adminService.authenticate(username, password);
        if (admin == null) {
            System.out.println("Admin login failed.");
            return;
        }

        System.out.println("Welcome, Admin " + admin.getFirstName());

        boolean logout = false;
        while (!logout) {
            System.out.println("\n Admin Menu ");
            System.out.println("1. View All Customers");
            System.out.println("2. View All Reservations");
            System.out.println("3. View All Vehicles");
            System.out.println("4. Add Vehicle");
            System.out.println("5. Remove Vehicle");
            System.out.println("6. Logout");
            System.out.print("Choose: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    List<Customer> customers = customerService.getAllCustomers();
                    for (Customer c : customers) {
                        System.out.println(c.getCustomerId() + ": " + c.getFirstName() + " " + c.getLastName());
                    }
                }
                case 2 -> {
                    List<Reservation> reservations = reservationService.getAllReservations();
                    if (reservations.isEmpty()) {
                        System.out.println("There are no reservations.");
                    }
                    else {
                        for (Reservation r : reservations) {
                            System.out.println("Reservation #" + r.getReservationID() + " | Customer ID: " + r.getCustomerID() + " | Vehicle ID: " + r.getVehicleID());
                        }
                    }
                }
                case 3 -> {
                    List<Vehicle> vehicles = vehicleService.getAllVehicles();
                    for (Vehicle v : vehicles) {
                        System.out.println(v.getVehicleId() + ": " + v.getMake() + " " + v.getModel());
                    }
                }
                case 4 -> {
                    System.out.println("\n Add New Vehicle ");
                    System.out.print("Model: ");
                    String model = scanner.nextLine();
                    System.out.print("Make: ");
                    String make = scanner.nextLine();
                    System.out.print("Year: ");
                    int year = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Color: ");
                    String color = scanner.nextLine();
                    System.out.print("Registration Number: ");
                    String regNo = scanner.nextLine();
                    System.out.print("Available (true/false): ");
                    boolean availability = scanner.nextBoolean();
                    System.out.print("Daily Rate: ");
                    double dailyRate = scanner.nextDouble();
                    scanner.nextLine();

                    Vehicle newVehicle = new Vehicle(model, make, year, color, regNo, availability, dailyRate);
                    vehicleService.addVehicle(newVehicle);
                    System.out.println("Vehicle added successfully.");
                }
                case 5 -> {
                    System.out.println("\nRemove Vehicle");
                    System.out.print("Enter Vehicle ID to remove: ");
                    int vehicleId = scanner.nextInt();
                    scanner.nextLine();

                    vehicleService.removeVehicle(vehicleId);
                }
                case 6 -> logout = true;
                default -> System.out.println("Invalid.");
            }
        }
} catch (CustomerNotFoundException e) {
    System.out.println("Login failed: " + e.getMessage());
} catch (DatabaseConnectionException e) {
    System.out.println("Database error: " + e.getMessage());
} catch (Exception e) {
    System.out.println("An unexpected error occurred: " + e.getMessage());
}
    }

    private static void handleAdminRegister(Scanner scanner, AdminService adminService) {
        System.out.println(" Register New Admin ");
        System.out.print("First Name: ");
        String fname = scanner.nextLine();
        System.out.print("Last Name: ");
        String lname = scanner.nextLine();
        String email;
        while (true) {
            System.out.print("Email: ");
            email = scanner.nextLine();
            if (isValidEmail(email)) break;
            System.out.println("Invalid email format");
        }

        String phone;
        while (true) {
            System.out.print("Phone (10 digits): ");
            phone = scanner.nextLine();
            if (isValidMobile(phone)) break;
            System.out.println("Invalid mobile number. Must start with 6-9 and be 10 digits.");
        }
        System.out.print("Username: ");
        String uname = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();
        System.out.print("Role (ex: manager, staff): ");
        String role = scanner.nextLine();

        Admin admin = new Admin(0, fname, lname, email, phone, uname, pass, role, LocalDate.now());
        adminService.registerAdmin(admin);
    }
}
