package Main.Model;

import java.time.LocalDate;

public class Order {

    private int id;
    private LocalDate date;
    private String orderNumber;
    private int salesPersonId;
    private int isValid;

    public Order() {
    }

    public Order(int id, LocalDate date, String orderNumber, int salesPersonId, int isValid) {
        this.id = id;
        this.date = date;
        this.orderNumber = orderNumber;
        this.salesPersonId = salesPersonId;
        this.isValid = isValid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getSalesPersonId() {
        return salesPersonId;
    }

    public void setSalesPersonId(int salesPersonId) {
        this.salesPersonId = salesPersonId;
    }

    public int getIsValid() {
        return isValid;
    }

    public void setIsValid(int isValid) {
        this.isValid = isValid;
    }
}
