package Main.DAO;

import Main.DAO.Interfaces.IOrderRepository;
import Main.Model.*;

import java.sql.*;
import java.util.List;

public class OrderRepository implements IOrderRepository {
    Connection connection = SingletonConnection.getConnexion();


    @Override
    public Toy findById(int id) {
        return null;
    }

    @Override
    public List<Toy> getAll() {
        return null;
    }

    @Override
    public void add(Toy entity) {

    }


    @Override
    public void delete(int id) {

    }

    @Override
    public void update(Toy entity) {

    }


    @Override
    public void Cart(Order order,Toy toy) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO orders (Id,Date,OrderNumber,salesPersonId,IsValid) " +
                    "VALUES (? ,? ,? ,? ,?)") ;
            PreparedStatement ps1 = connection.prepareStatement("INSERT INTO orderdetails(OrderId,TotyId,Quantity,UnitPrice) VALUES (LAST_INSERT_ID() ,? ,? ,?)") ;

            ps.setInt(1,order.getId());
            ps.setDate(2, Date.valueOf(order.getDate()));
            ps.setString(3,order.getOrderNumber());
            ps.setInt(4,order.getSalesPersonId());
            ps.setInt(5,order.getIsValid());

            ps1.setInt(1,toy.getId());
            ps1.setInt(2,toy.getId());
            ps1.setDouble(3,200);

            ps.executeUpdate();
            ps1.executeUpdate();

            System.out.println("order ajouté");

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("error");

        }

    }


//    @Override
//    public void AddOrderDetails(Order order,Toy toy) {
//
//        try {
//            PreparedStatement ps = connection.prepareStatement("INSERT INTO orderdetails(OrderId,TotyId,Quantity,UnitPrice) " +
//                    "VALUES (? ,? ,? ,?)") ;
//
//            ps.setInt(1,order.getId());
//            ps.setInt(2,toy.getId());
//            ps.setInt(3,toy.getId());
//            ps.setDouble(4,200);
//
//
//            ps.executeUpdate();
//
//            System.out.println("order détails ajouté");
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            System.out.println("error");
//
//        }
//
//
//    }

}
