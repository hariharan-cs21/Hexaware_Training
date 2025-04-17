package hexaware.petPals.entity;


public class Dog extends Pet {
    private String dogColor;

    public Dog(String name, int age, String breed, String dogColor) {
        super(name, age, breed);
        this.dogColor = dogColor;
    }

    public String getDogColor() { return dogColor; }

    public String toString() {
        return super.toString() + ", Dog Color=" + dogColor;
    }
}

