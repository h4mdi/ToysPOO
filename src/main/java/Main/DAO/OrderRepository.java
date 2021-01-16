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
            PreparedStatement ps1 = connection.prepareStatement("INSERT INTO orderdetails(OrderId,TotyId,Quantity,UnitPrice,orderNumber) VALUES (LAST_INSERT_ID() ,? ,? ,?,?)") ;

            ps.setInt(1,order.getId());
            ps.setDate(2, Date.valueOf(order.getDate()));
            ps.setString(3,order.getOrderNumber());
            ps.setInt(4,order.getSalesPersonId());
            ps.setInt(5,order.getIsValid());

            ps1.setInt(1,toy.getId());
            ps1.setInt(2,toy.getId());
            ps1.setDouble(3,toy.getPrice());
            ps1.setString(4,order.getOrderNumber());


            ps.executeUpdate();
            ps1.executeUpdate();

            System.out.println("order ajouté");

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("error");

        }

    }

    @Override
    public int getLastOrderNumber() {
        int last=0 ;

        try {
            PreparedStatement ps = connection.prepareStatement("  select * from orders order by OrderNumber desc limit 1") ;

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
               last= rs.getInt("OrderNumber") ;
                System.out.println(last+"e5er wehed");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("error");

    }
        return last ;


}

}
