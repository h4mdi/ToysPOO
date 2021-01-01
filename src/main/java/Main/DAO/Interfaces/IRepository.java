package Main.DAO.Interfaces;

import Main.Model.Toy;
import Main.Model.Vendor;

import java.sql.SQLException;
import java.util.List;

public interface IRepository<T> {

    public T findById(int id);
    public List<T> getAll() ;
    public void add(T entity);
    public void delete(int id);
    public void update(T entity);

}
