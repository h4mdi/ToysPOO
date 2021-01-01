package Main.Model;

public class SalesByPerson {
    Integer id;
    Double cv;

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCv() {
        return cv;
    }

    public void setCv(Double cv) {
        this.cv = cv;
    }

    public SalesByPerson(Integer id, Double cv) {
        this.id = id;
        this.cv = cv;
    }

    public SalesByPerson() {
    }

}
