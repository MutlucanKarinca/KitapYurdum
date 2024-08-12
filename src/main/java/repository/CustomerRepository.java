package repository;

import model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepository {
    private static CustomerRepository instance;
    private List<Customer> customers;
    private CustomerRepository() {
        customers = new ArrayList<>();
    }
    public static CustomerRepository getInstance() {
        if (instance == null) {
            instance = new CustomerRepository();
        }
        return instance;
    }

    public void createCustomer(Customer customer) {
        customers.add(customer);
    }

    public List<Customer> getCustomerList() {
        return customers;
    }

    public boolean existsByEmail(String email) {
        return customers.stream().anyMatch(customer -> customer.getEmail().equals(email));
    }

    public Customer getCustomerByEmail(String email) {
        return customers.stream().filter(customer -> customer.getEmail().equals(email)).findFirst().orElse(null);
    }

    public void update(Customer updatedCustomer) {
        // Müşteriyi güncellemek için email üzerinden arama yapıyoruz
        Optional<Customer> existingCustomerOpt = customers.stream()
                .filter(customer -> customer.getEmail().equals(updatedCustomer.getEmail()))
                .findFirst();

        if (existingCustomerOpt.isPresent()) {
            Customer existingCustomer = existingCustomerOpt.get();
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setSurname(updatedCustomer.getSurname());
            existingCustomer.setPassword(updatedCustomer.getPassword());
            existingCustomer.setCredit(updatedCustomer.getCredit());
            existingCustomer.setPhoneNumber(updatedCustomer.getPhoneNumber());
            existingCustomer.setAddresses(updatedCustomer.getAddresses());
            existingCustomer.setActive(updatedCustomer.getActive());
            existingCustomer.setAccountType(updatedCustomer.getAccountType());
            existingCustomer.setOrderList(updatedCustomer.getOrderList());
        } else {
            throw new RuntimeException("Customer not found for update");
        }
    }

}
