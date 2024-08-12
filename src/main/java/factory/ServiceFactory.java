package factory;

import service.CustomerService;
import service.OrderService;
import service.ProductService;
import service.PublisherService;

public class ServiceFactory {
    public static CustomerService getCustomerService() {
        return CustomerService.getInstance();
    }
    public static ProductService getProductService() {
        return ProductService.getInstance();
    }
    public static PublisherService getPublisherService() {
        return PublisherService.getInstance();
    }
    public static OrderService getOrderService() {
        return OrderService.getInstance();
    }
}
