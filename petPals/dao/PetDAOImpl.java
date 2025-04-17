package hexaware.petPals.dao;


import hexaware.petPals.DBConnUtil;
import hexaware.petPals.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDAOImpl implements PetDAO {
    private Connection conn;

    public PetDAOImpl() {
        try {
            conn = DBConnUtil.getConnection("db.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPet(Pet pet) {
        String sql = "INSERT INTO pets (name, age, breed, type, color) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pet.getName());
            ps.setInt(2, pet.getAge());
            ps.setString(3, pet.getBreed());

            if (pet instanceof Dog) {
                ps.setString(4, "Dog");
                ps.setString(5, ((Dog) pet).getDogColor());
            } else if (pet instanceof Cat) {
                ps.setString(4, "Cat");
                ps.setString(5, ((Cat) pet).getCatColor());
            } else {
                ps.setString(4, "Other");
                ps.setString(5, "");
            }

            ps.executeUpdate();
            System.out.println("Pet added to database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pet> getAllPets() {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pets where available=true";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String breed = rs.getString("breed");
                String type = rs.getString("type");
                String color = rs.getString("color");

                Pet pet;
                if ("Dog".equalsIgnoreCase(type)) {
                    pet = new Dog(name, age, breed, color);
                } else if ("Cat".equalsIgnoreCase(type)) {
                    pet = new Cat(name, age, breed, color);
                } else {
                    pet = new Pet(name, age, breed);
                }

                pets.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pets;
    }
    public void markPetAsAdopted(String petName) {
        String sql = "UPDATE pets SET available = false WHERE name = ? AND available = true";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, petName);
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println(petName + " has been successfully adopted");
            } else {
                System.out.println("Pet " + petName + " is not available.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
