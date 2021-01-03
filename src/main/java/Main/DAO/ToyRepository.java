package Main.DAO;

import Main.DAO.Interfaces.IToyRepository;
import Main.Model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ToyRepository implements IToyRepository {
    Connection connection = SingletonConnection.getConnexion();

    @Override
    public Toy findById(int id) {
        return null;
    }

    @Override
    public List<Toy> getAll() {
        List<Toy> Toylist = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM toys t left JOIN vendors v on t.id=v.id ");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Toy toy = new Toy(rs.getInt("Id"),
                        rs.getString("Name"),rs.getInt("TypeId")
                        ,rs.getString("PicturePath"),rs.getDouble("Price"),rs.getString("v.Name"),
                        rs.getInt("MaxAge"),rs.getInt("MinAge"),
                        rs.getInt("Quantity"));

                Toylist.add(toy);
                System.out.println(Toylist);
                System.out.println("--------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Toylist;
    }

    @Override
    public void add(Toy toy) {

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO toys(Id,Name,TypeId,MinAge,MaxAge,PicturePath,Price,VendorId,Quantity) " +
                    "VALUES (? , ? ,? ,? ,? ,? ,? ,? , ?)") ;
            ps.setInt(1,toy.getId());
            ps.setString(2,toy.getName());
            ps.setInt(3,toy.getType_id());
            ps.setInt(4,toy.getMin_age());
            ps.setInt(5,toy.getMax_age());
            ps.setString(6,toy.getPhoto());
            ps.setDouble(7,toy.getPrice());
            ps.setInt(8,toy.getType_id());
            ps.setDouble(9,toy.getStock());

            ps.executeUpdate();

            System.out.println("toy ajouté");

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("error");

        }


    }

    @Override
    public void delete(int id) {

        try {

            PreparedStatement st = connection.prepareStatement("DELETE FROM toys where Id = ?");
            st.setInt(1, id);
            st.executeUpdate();
            System.out.println("c'est bon");

        } catch (SQLException e) {
            System.out.println("erreur");
        }
    }

    @Override
    public void update(Toy toy) {
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        //Date d=new Date();
//        String req = "UPDATE  toys SET name='" + toy.getName() + "' ,price='" + toy.getPrice() +"' where id ='" + toy.getId() + "';";
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE toys SET Name=? , Price= ? where Id= ?");
            ps.setString(1,toy.getName());
            ps.setDouble(2,toy.getPrice());
            ps.setInt(3,toy.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    @Override
    public Toy getPhotos(String path)throws SQLException {
        String req = "Select * from toys t where t.PicturePath = ?";
        PreparedStatement st = connection.prepareStatement(req);
        st.setString(1, path);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            Toy obj = new Toy();
            obj.setId(rs.getInt("id"));

            obj.setPhoto(rs.getString("PicturePath"));
            System.out.println("path :" + obj.getPhoto());
            return obj;
        }
        return null;    }
    @Override
    public List<String> getAllMails() {
        List<String> emails = new ArrayList<String>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM mailinglist");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String email= rs.getString("Email");

                emails.add(email);
                System.out.println(emails);
                System.out.println("--------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return emails;    }


    @Override
    public List<String> getAllVendors() {
        List<String> Vendorlist = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from vendors ");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vendor vendor = new Vendor(rs.getInt("Id"),
                        rs.getString("Name")
                        ,rs.getString("Email"),rs.getString("Address")
                        ,rs.getString("FacebookUrl")
                       );

                Vendorlist.add(vendor.getName());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Vendorlist;
    }


    @Override
    public List<String> getAllTypes() {
        List<String> ToyTypelist = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * from toytypes ");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ToyType toyType = new ToyType(rs.getInt("Id"),
                        rs.getString("Name"));

                ToyTypelist.add(toyType.getName());

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ToyTypelist;
    }

    @Override
    public void addType(ToyType toyType) {

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO toytypes(Id,Name) " +
                    "VALUES (?,?)") ;
            ps.setInt(1,toyType.getId());
            ps.setString(2,toyType.getName());
            ps.executeUpdate();

            System.out.println("type ajouté");

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("error");

        }
    }

    @Override
    public List<SalesByPerson> GetSalesByPerson(Date startDate, Date endDate) {
        List<SalesByPerson> salesByPersonList = new ArrayList<SalesByPerson>();
        try {
            CallableStatement callableStatement = connection.prepareCall("{call GetSalesByPerson(?,?)}");
            callableStatement.setDate(1, startDate);
            callableStatement.setDate(2, endDate);
            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {
                SalesByPerson salesByPerson = new SalesByPerson();
                int id= rs.getInt("SalesPersonId");
                Double cv = rs.getDouble("SUM(od.Quantity*od.UnitPrice)") ;
                salesByPerson.setId(id);
                salesByPerson.setCv(cv);
                salesByPersonList.add(salesByPerson);
                System.out.println(salesByPersonList);
                System.out.println("--------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salesByPersonList;    }

    @Override
    public List<SalesByPerson> GetMaxSalesByPerson(Date startDate, Date endDate) {
        List<SalesByPerson> MaxsalesByPersonList = new ArrayList<SalesByPerson>();
        try {
            CallableStatement callableStatement = connection.prepareCall("{call GetMaxSalesByPerson(?,?)}");
            callableStatement.setDate(1, startDate);
            callableStatement.setDate(2, endDate);
            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {
                SalesByPerson salesByPerson = new SalesByPerson();
                int id= rs.getInt("SalesPersonId");
                Double cv = rs.getDouble("SUM(od.Quantity*od.UnitPrice)") ;
                salesByPerson.setId(id);
                salesByPerson.setCv(cv);
                MaxsalesByPersonList.add(salesByPerson);
                System.out.println(MaxsalesByPersonList);
                System.out.println("--------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return MaxsalesByPersonList;     }

    @Override
    public List<SalesByToy> GetSalesByToy(Date startDate, Date endDate) {
        List<SalesByToy> salesByToyList = new ArrayList<SalesByToy>();
        try {
            CallableStatement callableStatement = connection.prepareCall("{call GetSalesByToy(?,?)}");
            callableStatement.setDate(1, startDate);
            callableStatement.setDate(2, endDate);
            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {
                SalesByToy salesByToy = new SalesByToy();
                String name= rs.getString("Name");
                Double cv = rs.getDouble("SUM(od.Quantity*od.UnitPrice)") ;
                salesByToy.setName(name);
                salesByToy.setCv(cv);
                salesByToyList.add(salesByToy);
                System.out.println(salesByToyList);
                System.out.println("--------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salesByToyList;    }

    @Override
    public ArrayList<Double> GetTotalSales(Date startDate, Date endDate) {
        ArrayList<Double> totalSalesList = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("{call GetTotalSales(?,?)}");
            callableStatement.setDate(1, startDate);
            callableStatement.setDate(2, endDate);
            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()) {
                Double cvg = rs.getDouble("SUM(od.Quantity*od.UnitPrice)") ;
                totalSalesList.add(cvg);
                System.out.println(totalSalesList);
                System.out.println("--------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalSalesList;    }
}
