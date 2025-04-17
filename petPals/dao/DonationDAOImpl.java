package hexaware.petPals.dao;

import hexaware.petPals.DBConnUtil;
import hexaware.petPals.entity.CashDonation;

import java.sql.*;

public class DonationDAOImpl implements DonationDAO {
    private Connection conn;

    public DonationDAOImpl() {
        try {
            conn = DBConnUtil.getConnection("db.properties");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void recordCashDonation(CashDonation donation) throws SQLException {
        String sql = "INSERT INTO donations (donor_name, amount, donation_date) VALUES (?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, donation.getDonorName());
        ps.setDouble(2, donation.getAmount());
        ps.setDate(3, Date.valueOf(donation.getDonationDate()));
        ps.executeUpdate();
        System.out.println("Cash donation recorded in DB");
    }

    @Override
    public void listAllDonations() throws SQLException {
        String sql = "SELECT * FROM donations";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        System.out.println("\nDonation History");
        while (rs.next()) {
            String donorName = rs.getString("donor_name");
            double amount = rs.getDouble("amount");
            Date donationDate = rs.getDate("donation_date");

            System.out.println("Donor: " + donorName + ", Amount: Rs" + amount + ", Date: " + donationDate);
        }
    }

}

