package javaMysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDao {
    private final Connection conn;

    public DoctorDao() {
        this.conn = DataBaseConnection.getConnection();
    }

    public void fetchDoctors() {
        String query = "SELECT * FROM doctors";
        try (PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("doctor_id");
                String name = rs.getString("first_name");
                System.out.println("ID: " + id + ", Name: " + name);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching doctors: " + e.getMessage());
        }
    }

    public void insertDoctor(int doctorId, String firstName, String lastName, String specialty) {
        String query = "INSERT INTO doctors (doctor_id, first_name, last_name, specialization) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, specialty);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Doctor inserted successfully.");
            } else {
                System.out.println("Doctor insertion failed.");
            }
        } catch (SQLException e) {
            System.err.println("Error inserting doctor: " + e.getMessage());
        }
    }
}
