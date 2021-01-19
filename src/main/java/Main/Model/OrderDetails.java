package Main.Model;

import java.time.LocalDate;

public class OrderDetails {
    int orderId ;
    String name ;
    int quantity ;
    double unitPrice ;
    int orderNumber ;
    LocalDate date ;

    public OrderDetails() {
    }

    public OrderDetails(int orderId, String name, int quantity, double unitPrice, int orderNumber, LocalDate date) {
        this.orderId = orderId;
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.orderNumber = orderNumber;
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }
}
