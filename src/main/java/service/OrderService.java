package service;

import model.Order;
import model.Product;
import model.enums.OrderStatus;
import repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderService {
    private OrderRepository orderRepository;
    private static OrderService instance;
    private InvoiceService invoiceService;
    public OrderService(OrderRepository orderRepository, InvoiceService invoiceService) {
        this.orderRepository = orderRepository;
        this.invoiceService = invoiceService;
    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService(OrderRepository.getInstance(),InvoiceService.getInstance());
        }
        return instance;
    }

    public Order save(List<Product> productList) {
        String orderCode = generateOrderCode();
        Order order = new Order(productList, orderCode);
        orderRepository.save(order);
        invoiceService.save(order);
        return order;
    }

    private String generateOrderCode() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        return uuid.substring(0, 10);
    }

    public BigDecimal getTotalAmount(Order order) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Product product : order.getProductList()) {
            totalAmount = totalAmount.add(product.getAmount());
        }
        return totalAmount;
    };

}


