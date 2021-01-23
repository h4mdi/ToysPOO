package Main.DAO.Interfaces;

import Main.Model.SalesByPerson;
import Main.Model.SalesByToy;
import Main.Model.Toy;
import Main.Model.ToyType;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface IToyRepository extends IRepository<Toy> {
    public List<String> getAllMails();
    public Toy getPhotos(String photo) throws SQLException;
    public List<String> getAllVendors();
    public List<String> getAllTypes();
    public void addType(ToyType toyType);
    public List<SalesByPerson> GetSalesByPerson(Date startDate, Date endDat);
    public List<SalesByPerson> GetMaxSalesByPerson(Date startDate, Date endDat);
    public List<Double> GetTotalSales(Date startDate, Date endDat);
    public List<SalesByToy> GetSalesByToy(Date startDate, Date endDat);
    public List<Toy> findByType(String type) ;
    public List<Toy> findByOld(int min , int max) ;


        public Toy getToy() ;
    public int GetTotaltoys();

}
