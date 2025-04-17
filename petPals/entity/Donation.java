package hexaware.petPals.entity;

public abstract class Donation {
    protected String donorName;
    protected double amount;

    public Donation(String donorName, double amount) {
        this.donorName = donorName;
        this.amount = amount;
    }

    public String getDonorName() {
        return donorName;
    }

    public double getAmount() {
        return amount;
    }

}