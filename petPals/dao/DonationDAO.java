package hexaware.petPals.dao;

import hexaware.petPals.entity.CashDonation;
import java.sql.SQLException;

public interface DonationDAO {
    void recordCashDonation(CashDonation donation) throws SQLException;
    void listAllDonations() throws SQLException;
}