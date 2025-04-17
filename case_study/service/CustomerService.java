package hexaware.case_study.service;


import hexaware.case_study.entity.Customer;
import hexaware.case_study.service.interfaces.ICustomerService;
import hexaware.case_study.util.DatabaseContext;
import hexaware.case_study.exception.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;
public class CustomerService implements ICustomerService {

    @Override
    public Customer getCustomerById(int customerId) {
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Customers WHERE CustomerID = ?")) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return map(rs);
            } else {
                throw new CustomerNotFoundException("Customer not found");
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    @Override
    public Customer getCustomerByUsername(String username) {
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Customers WHERE Username = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return map(rs);
            } else {
                throw new CustomerNotFoundException("Customer not found");
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    @Override
    public void registerCustomer(Customer customer) {
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO Customers (FirstName, LastName, Email, PhoneNumber, Address, Username, Password, RegistrationDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhoneNumber());
            stmt.setString(5, customer.getAddress());
            stmt.setString(6, customer.getUsername());
            stmt.setString(7, customer.getPassword());
            stmt.setDate(8, Date.valueOf(customer.getRegistrationDate()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE Customers SET FirstName=?, LastName=?, Email=?, PhoneNumber=?, Address=?, Username=?, Password=?, RegistrationDate=? WHERE CustomerID=?")) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getEmail());
            stmt.setString(4, customer.getPhoneNumber());
            stmt.setString(5, customer.getAddress());
            stmt.setString(6, customer.getUsername());
            stmt.setString(7, customer.getPassword());
            stmt.setDate(8, Date.valueOf(customer.getRegistrationDate()));
            stmt.setInt(9, customer.getCustomerId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }

    @Override
    public void deleteCustomer(int customerId) {
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Customers WHERE CustomerID=?")) {
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.getMessage());
        }
    }
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try (Connection conn = DatabaseContext.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Customer c = new Customer(
                        rs.getInt("customerId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        rs.getString("address"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getDate("registrationDate").toLocalDate()
                );
                customers.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public Customer authenticate(String username, String password) {
        Customer customer = getCustomerByUsername(username);
        if (customer != null && customer.authenticate(password)) {
            return customer;
        }
        return null;
    }


    private Customer map(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getInt("CustomerID"),
                rs.getString("FirstName"),
                rs.getString("LastName"),
                rs.getString("Email"),
                rs.getString("PhoneNumber"),
                rs.getString("Address"),
                rs.getString("Username"),
                rs.getString("Password"),
                rs.getDate("RegistrationDate").toLocalDate()
        );
    }

}
