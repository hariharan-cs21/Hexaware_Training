package hexaware.petPals.entity;

import java.time.LocalDate;

public class CashDonation extends Donation {
    private LocalDate donationDate;

    public CashDonation(String donorName, double amount, LocalDate donationDate) {
        super(donorName, amount);
        this.donationDate = donationDate;
    }



    public String getDonationDate() {
        return donationDate.toString();
    }
}
