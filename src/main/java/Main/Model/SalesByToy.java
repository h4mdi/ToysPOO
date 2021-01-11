package Main.Model;

public class SalesByToy {
    String name;
    Double cv;

    public SalesByToy(String name, Double cv) {
        this.name = name;
        this.cv = cv;
    }

    public SalesByToy() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCv() {
        return cv;
    }

    public void setCv(Double cv) {
        this.cv = cv;
    }
}
