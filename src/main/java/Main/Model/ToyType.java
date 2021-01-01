package Main.Model;

public class ToyType {
    int id;
    String name;

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

    public ToyType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ToyType() {
    }
}
