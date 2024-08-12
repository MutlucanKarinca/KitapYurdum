package service;

import model.Product;
import model.Publisher;
import repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductService {
    private ProductRepository productRepository;
    private static ProductService instance;
    private PublisherService publisherService;

    public ProductService(ProductRepository productRepository, PublisherService publisherService) {
        this.productRepository = productRepository;
        this.publisherService = publisherService;
    }
    public static ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService(ProductRepository.getInstance(),PublisherService.getInstance());
        }
        return instance;
    }

    public void save(String name, BigDecimal amount, String description, String publisherName) {

        Optional<Publisher> publisher = publisherService.getByName(publisherName);

        System.out.println(publisherService.hashCode());

        if (publisher.isEmpty()) {
            System.out.println("Publisher not found: " + publisherName);
            throw new RuntimeException("Publisher not found:");
        }

        Product product = new Product(name, amount, description, publisher.get());

        productRepository.save(product);

        System.out.println("Product Service: saved -> " + product);
    }

    public Set<Product> getAll() {
        return productRepository.getAll();
    }

    public void listAll() {
        getAll().forEach(System.out::println);
    }

    public List<Product> getProductsByNames(List<String> productNames) {
        return productNames.stream()
                .map(productRepository::getByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
