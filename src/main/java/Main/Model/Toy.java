package Main.Model;

public class Toy {
    //    Un jouet est caracterise par un identiant, un nom, un type, une plage d'age de
//    la personne cible par le jouet, une photo, un prix, un fournisseur et un stock.
    private int id;
    private String name;
    private String photo;
    private double price;
    private int min_age;
    private int max_age;
    private double stock;
    private int type_id;
    private String type_name;
    private int vendorID;
    private String vendor_name;

    public Toy(int id, String name, String photo, double price, int min_age, int max_age, double stock, int type_id, String type_name, int vendorID, String vendor_name) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.price = price;
        this.min_age = min_age;
        this.max_age = max_age;
        this.stock = stock;
        this.type_id = type_id;
        this.type_name = type_name;
        this.vendorID = vendorID;
        this.vendor_name = vendor_name;
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

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public int getVendorID() {
        return vendorID;
    }

    public void setVendorID(int vendorID) {
        this.vendorID = vendorID;
    }

    public String getVendor_name() {
        return vendor_name;
    }

    public void setVendor_name(String vendor_name) {
        this.vendor_name = vendor_name;
    }

    public Toy() {
    }
}
