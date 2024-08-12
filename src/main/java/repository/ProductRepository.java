package repository;

import model.Customer;
import model.Product;

import java.util.*;

public class ProductRepository {
    private static ProductRepository instance;
    private Set<Product> products;
    private ProductRepository() {
        products = new HashSet<>();
    }
    public static ProductRepository getInstance() {
        if (instance == null) {
            instance = new ProductRepository();
        }
        return instance;
    }

    public void save(Product product) {
        products.add(product);
    }

    public Set<Product> getAll() {
        return products;
    }

    public Optional<Product> getByName(String name) {
        return products.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst();
    }
}
