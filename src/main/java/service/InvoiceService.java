package service;

import model.Invoice;
import model.Order;
import model.Product;
import repository.InvoiceRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class InvoiceService {
    private InvoiceRepository invoiceRepository;
    private static InvoiceService instance;
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }
    public static InvoiceService getInstance() {
        if (instance == null) {
            instance = new InvoiceService(InvoiceRepository.getInstance());
        }
        return instance;
    }
    public Invoice save(Order order) {
        String invoiceNumber = generateInvoiceNumber();
        BigDecimal totalAmount = order.getProductList().stream()
                .map(Product::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Invoice invoice = new Invoice(invoiceNumber, LocalDateTime.now(),totalAmount,order);
        invoiceRepository.save(invoice);
        return invoice;
    }

    private String generateInvoiceNumber() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        return uuid.substring(0, 6);
    }

}
