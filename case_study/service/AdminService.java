package hexaware.case_study.service;


import  hexaware.case_study.entity.Admin;
import  hexaware.case_study.util.DatabaseContext;
import  hexaware.case_study.service.interfaces.IAdminService;
import  hexaware.case_study.exception.*;

import java.sql.*;

public class AdminService implements IAdminService {

    @Override
    public Admin getAdminById(int adminId) {
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Admins WHERE AdminID = ?")) {
            stmt.setInt(1, adminId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return map(rs);
            else throw new AdminNotFoundException("Admin not found");
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    @Override
    public Admin getAdminByUsername(String username) {
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Admins WHERE Username = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return map(rs);
            else throw new AdminNotFoundException("Admin not found");
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    @Override
    public void registerAdmin(Admin admin) {
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO Admins (FirstName, LastName, Email, PhoneNumber, Username, Password, Role, JoinDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, admin.getFirstName());
            stmt.setString(2, admin.getLastName());
            stmt.setString(3, admin.getEmail());
            stmt.setString(4, admin.getPhoneNumber());
            stmt.setString(5, admin.getUsername());
            stmt.setString(6, admin.getPassword());
            stmt.setString(7, admin.getRole());
            stmt.setDate(8, Date.valueOf(admin.getJoinDate()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    @Override
    public void updateAdmin(Admin admin) {
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE Admins SET FirstName=?, LastName=?, Email=?, PhoneNumber=?, Username=?, Password=?, Role=?, JoinDate=? WHERE AdminID=?")) {
            stmt.setString(1, admin.getFirstName());
            stmt.setString(2, admin.getLastName());
            stmt.setString(3, admin.getEmail());
            stmt.setString(4, admin.getPhoneNumber());
            stmt.setString(5, admin.getUsername());
            stmt.setString(6, admin.getPassword());
            stmt.setString(7, admin.getRole());
            stmt.setDate(8, Date.valueOf(admin.getJoinDate()));
            stmt.setInt(9, admin.getAdminID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }
    public Admin authenticate(String username, String password) {
        Admin admin = getAdminByUsername(username);
        if (admin != null && admin.authenticate(password)) {
            return admin;
        }
        return null;
    }
    @Override
    public void deleteAdmin(int adminId) {
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Admins WHERE AdminID=?")) {
            stmt.setInt(1, adminId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    private Admin map(ResultSet rs) throws SQLException {
        return new Admin(
                rs.getInt("AdminID"),
                rs.getString("FirstName"),
                rs.getString("LastName"),
                rs.getString("Email"),
                rs.getString("PhoneNumber"),
                rs.getString("Username"),
                rs.getString("Password"),
                rs.getString("Role"),
                rs.getDate("JoinDate").toLocalDate()
        );
    }
}
