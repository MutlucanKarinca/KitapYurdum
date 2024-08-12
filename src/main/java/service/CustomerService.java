package service;

import model.Customer;
import model.Invoice;
import model.Order;
import model.Product;
import model.enums.AccountType;
import repository.CustomerRepository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

public class CustomerService {

    private CustomerRepository customerRepository;
    private static CustomerService instance;
    private OrderService orderService;
    private ProductService productService;

    private CustomerService(CustomerRepository customerRepository,OrderService orderService,ProductService productService) {
        this.customerRepository = customerRepository;
        this.orderService = orderService;
        this.productService = productService;
    }

    public static CustomerService getInstance() {
        if (instance == null) {
            instance = new CustomerService(CustomerRepository.getInstance(),OrderService.getInstance(),ProductService.getInstance());
        }
        return instance;
    }

    public void save(String name, String surname, String email, String password) {
        if(customerRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }
        String hashedPassword = hashPassword(password);
        Customer customer = new Customer(name, surname, email, hashedPassword); // hash olarak tutulsun.

        customerRepository.createCustomer(customer);

    }

    public List<Customer> getCustomerList() {
        return customerRepository.getCustomerList();
    }

    public void changeAccountType(String email, AccountType accountType) {

        Optional<Customer> foundCustomer = getCustomerList()
                .stream()
                .filter(customer -> customer.getEmail().equals(email))
                .findFirst();

        if (foundCustomer.isPresent()) {
            foundCustomer.get().setAccountType(accountType);
        }
    }

    public void changeAccountTypeByCredit(String email) { //ödev
        Customer customer = customerRepository.getCustomerByEmail(email);
        if (customer !=null){
            double points = customer.getCredit();
            if (points >= 4000) {
                customer.setAccountType(AccountType.PLATINUM);
            } else if (points >= 2000) {
                customer.setAccountType(AccountType.GOLD);
            } else if (points >= 1000) {
                customer.setAccountType(AccountType.SILVER);
            }
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes());
            return bytesToHex(encodedhash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Byte dizisini hex stringe çeviren yardımcı yöntem
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public void saveOrder(String email,List<String> productNames){
        Customer customer = customerRepository.getCustomerByEmail(email);
        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }
        List<Product> productList = productService.getProductsByNames(productNames);
        if (productList.isEmpty()) {
            throw new RuntimeException("No products found for the given names");
        }
        Order order = orderService.save(productList);
        customer.getOrderList().add(order);
        double totalAmount = orderService.getTotalAmount(order).doubleValue() * 0.02;
        customer.setCredit(customer.getCredit() + totalAmount);
        changeAccountTypeByCredit(customer.getEmail());
    }

    public List<Order> getOrderList(String email){
        Customer customer = customerRepository.getCustomerByEmail(email);
        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }
        return customer.getOrderList();
    }
}
