package repository;

import model.Invoice;

import java.util.ArrayList;
import java.util.List;

public class InvoiceRepository {
    private static InvoiceRepository instance;
    private List<Invoice> invoices;
    private InvoiceRepository() {
        invoices = new ArrayList<Invoice>();
    }
    public static InvoiceRepository getInstance() {
        if (instance == null) {
            instance = new InvoiceRepository();
        }
        return instance;
    }

    public void save(Invoice invoice) {
        invoices.add(invoice);
    }
}
