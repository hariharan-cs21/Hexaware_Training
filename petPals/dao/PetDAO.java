package hexaware.petPals.dao;

import hexaware.petPals.entity.Pet;

import java.util.List;


public interface PetDAO {
    void addPet(Pet pet);
    List<Pet> getAllPets();
    void markPetAsAdopted(String petName);
}
