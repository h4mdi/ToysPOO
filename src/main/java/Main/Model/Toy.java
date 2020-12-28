package Main.Model;

public class Toy {
    //    Un jouet est caracterise par un identiant, un nom, un type, une plage d'age de
//    la personne cible par le jouet, une photo, un prix, un fournisseur et un stock.
    private int id;
    private String name;
    private int type_id;
    private String photo;
    private double price;
    private String vendor_name;
    private int min_age;
    private int max_age;
    private double stock;


    public Toy(int id, String name, int type_id, String photo, double price, String vendor_name, int min_age, int max_age, double stock) {
        this.id = id;
        this.name = name;
        this.type_id = type_id;
        this.photo = photo;
        this.price = price;
        this.vendor_name = vendor_name;
        this.min_age = min_age;
        this.max_age = max_age;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public int getMin_age() {
        return min_age;
    }

    public void setMin_age(int min_age) {
        this.min_age = min_age;
    }

    public int getMax_age() {
        return max_age;
    }

    public void setMax_age(int max_age) {
        this.max_age = max_age;
    }

    public double getStock() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock = stock;
    }
}
