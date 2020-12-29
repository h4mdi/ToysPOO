package Main.Metier;

import Main.DAO.SingletonConnection;
import Main.Model.Toy;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ToyRepository implements IServiceToy {
    Connection connection = SingletonConnection.getConnexion();
    @Override
    public Toy findById(int id_toy) throws SQLException {
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
    public void addToy(Toy toy) {

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
    public void updateToy(Toy toy) {
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
    public Toy getPhotos(String path) throws SQLException {

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
        return null;
    }

    @Override
    public void deleteToy(int id) {


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

        return emails;

    }


}
