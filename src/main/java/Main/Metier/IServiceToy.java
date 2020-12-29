package Main.Metier;

import Main.Model.Toy;

import java.sql.SQLException;
import java.util.List;

public interface IServiceToy {

    public Toy findById(int id_toy)throws SQLException;
    public List<Toy> getAll() ;
    public void addToy(Toy toy);
    public void deleteToy(int id);
    public void updateToy(Toy toy);
    public List<String> getAllMails() ;
  public Toy getPhotos(String path) throws SQLException;




}
