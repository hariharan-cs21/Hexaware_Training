package hexaware.case_study.service.interfaces;


import hexaware.case_study.entity.Customer;

public interface ICustomerService {
    Customer getCustomerById(int customerId);
    Customer getCustomerByUsername(String username);
    void registerCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(int customerId);
}
