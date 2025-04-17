package hexaware.petPals;

import hexaware.petPals.dao.*;
import hexaware.petPals.entity.*;
import hexaware.petPals.exceptions.*;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        PetDAO petDAO = new PetDAOImpl();
        DonationDAO donationDAO = new DonationDAOImpl();
        AdoptionEventDAO eventDAO = new AdoptionEventDAOImpl();

        while (true) {
            System.out.println("1. add Pet");
            System.out.println("2. show available pets");
            System.out.println("3. make cash donation");
            System.out.println("4. create adoption event");
            System.out.println("5. view adoption events");
            System.out.println("6. register for adoption event");
            System.out.println("7. adopt a pet");
            System.out.println("8. view event participants");
            System.out.println("9. view all donations");
            System.out.println("10. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter Pet Type Dog,Cat: ");
                        String type = sc.nextLine();
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();
                        sc.nextLine();
                        if (age <= 0) throw new InvalidPetAgeException("Age must be a positive number.");
                        System.out.print("Enter Breed: ");
                        String breed = sc.nextLine();

                        Pet pet;
                        if (type.equalsIgnoreCase("Dog")) {
                            System.out.print("Enter Dog Color: ");
                            String color = sc.nextLine();
                            pet = new Dog(name, age, breed, color);
                        } else if (type.equalsIgnoreCase("Cat")) {
                            System.out.print("Enter Cat Color: ");
                            String color = sc.nextLine();
                            pet = new Cat(name, age, breed, color);
                        } else {
                            pet = new Pet(name, age, breed);
                        }

                        petDAO.addPet(pet);
                    } catch (InvalidPetAgeException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        List<Pet> pets = petDAO.getAllPets();
                        if (pets.isEmpty()) {
                            System.out.println("No pets available for adoption.");
                        } else {
                            System.out.println("\nAvailable Pets:");
                            for (Pet pet : pets) {
                                if (pet.getName() == null || pet.getAge() == 0) {
                                    throw new NullPointerException("No information");
                                }
                                System.out.println(pet);
                            }
                        }
                    } catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        System.out.print("Enter Donor Name: ");
                        String donor = sc.nextLine();
                        System.out.print("Enter Amount: ");
                        double amount = sc.nextDouble();
                        sc.nextLine();
                        if (amount < 100.0) throw new InsufficientFundsException("Minimum donation is Rs 100");
                        LocalDate today = LocalDate.now();

                        CashDonation donation = new CashDonation(donor, amount, today);
                        donationDAO.recordCashDonation(donation);
                    } catch (InsufficientFundsException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        System.out.print("Enter Event Name: ");
                        String eventName = sc.nextLine();
                        System.out.print("Enter Event Date (yyyy-mm-dd): ");
                        String eventDateStr = sc.nextLine();
                        Date eventDate = Date.valueOf(eventDateStr);

                        eventDAO.createEvent(eventName, eventDate);
                    } catch (Exception e) {
                        System.out.println("Error creating event: " + e.getMessage());
                    }
                    break;

                case 5:
                    try {
                        eventDAO.listEvents();
                    } catch (SQLException e) {
                        System.out.println("Could not retrieve events: " + e.getMessage());
                    }
                    break;

                case 6:
                    try {
                        System.out.print("Enter Your Name: ");
                        String pname = sc.nextLine();
                        System.out.print("Enter Event ID to Register: ");
                        int eid = sc.nextInt();
                        sc.nextLine();

                        eventDAO.registerParticipant(pname, eid);
                    } catch (SQLException e) {
                        System.out.println("Error registering for event " + e.getMessage());
                    }
                    break;



                case 7:
                    System.out.print("Enter Pet Name to Adopt: ");
                    String petName = sc.nextLine();
                    petDAO.markPetAsAdopted(petName);
                    break;

                case 8:
                    try {
                        eventDAO.listParticipantsByEvent();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 9:
                    try {
                        donationDAO.listAllDonations();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 10:
                    System.out.println("Bye");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
