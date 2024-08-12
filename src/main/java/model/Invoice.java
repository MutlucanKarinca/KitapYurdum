package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Invoice {
    private String invoiceNumber;
    private LocalDateTime issueDate;
    private BigDecimal totalAmount;
    private Order order;

    public Invoice(String invoiceNumber, LocalDateTime issueDate, BigDecimal totalAmount, Order order) {
        this.invoiceNumber = invoiceNumber;
        this.issueDate = issueDate;
        this.totalAmount = totalAmount;
        this.order = order;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
