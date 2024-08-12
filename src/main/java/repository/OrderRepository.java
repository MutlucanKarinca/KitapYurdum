package repository;

import model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private static OrderRepository instance;
    private List<Order> orders;
    private OrderRepository() {
        orders = new ArrayList<Order>();
    }
    public static OrderRepository getInstance() {
        if (instance == null) {
            instance = new OrderRepository();
        }
        return instance;
    }

    public List<Order> getOrders() {
        return orders;
    }
    public void save(Order order) {
        orders.add(order);
    }

}
