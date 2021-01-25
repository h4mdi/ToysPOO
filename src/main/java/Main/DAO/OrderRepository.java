package Main.DAO;

import Main.DAO.Interfaces.IOrderRepository;
import Main.Model.*;

import java.sql.*;
import java.util.ArrayList;
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
            ps.setInt(3,order.getOrderNumber());
            ps.setInt(4,order.getSalesPersonId());
            ps.setInt(5,order.getIsValid());

            ps1.setInt(1,toy.getId());
            ps1.setInt(2,1);
            ps1.setDouble(3,toy.getPrice());
            ps1.setInt(4,order.getOrderNumber());


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


    @Override
    public List<Order> getAllOrders(int salesPersonId) {
        List<Order> Order = new ArrayList<>();
        Connection connection = SingletonConnection.getConnexion();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM orders where SalesPersonId = ? Group by orderNumber");
            ps.setInt(1,salesPersonId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order(rs.getInt("Id"),
                        rs.getDate("Date").toLocalDate(),rs.getInt("OrderNumber"),
                        rs.getInt("SalesPersonId"),rs.getInt("IsValid"));

                Order.add(order);
                System.out.println(order);
                System.out.println("--------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Order;
    }

    @Override
    public List<Order> getAllOrdersAdmin() {
        List<Order> Order = new ArrayList<>();
        Connection connection = SingletonConnection.getConnexion();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM orders o WHERE o.IsValid=1 group by o.orderNumber");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order(rs.getInt("Id"),
                        rs.getDate("Date").toLocalDate(),rs.getInt("OrderNumber"),
                        rs.getInt("SalesPersonId"),rs.getInt("IsValid"));

                Order.add(order);
                System.out.println(order);
                System.out.println("--------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Order;
    }

    @Override
    public List<Order> getAllcancelledOrdersAdmin() {
        List<Order> Order = new ArrayList<>();
        Connection connection = SingletonConnection.getConnexion();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM orders o WHERE o.IsValid=0 group by o.orderNumber");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order(rs.getInt("Id"),
                        rs.getDate("Date").toLocalDate(),rs.getInt("OrderNumber"),
                        rs.getInt("SalesPersonId"),rs.getInt("IsValid"));

                Order.add(order);
                System.out.println(order);
                System.out.println("--------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Order;
    }

    @Override
    public List<OrderDetails> getOrdersByNumber(int orderNumber) {
        List<OrderDetails> Order = new ArrayList<>();
        Connection connection = SingletonConnection.getConnexion();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM orderdetails od " +
                    "join toys t on t.id=od.totyId JOIN orders o on o.Id = od.orderID  where o.orderNumber = ? ");
            ps.setInt(1,orderNumber);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderDetails order = new OrderDetails(rs.getInt("OrderId"),
                        rs.getString("t.Name"),
                        rs.getInt("Quantity"),
                        rs.getDouble("t.price"),rs.getInt("orderNumber"),
                        rs.getDate("o.Date").toLocalDate());

                Order.add(order);
                System.out.println(order);
                System.out.println("-----lista---------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Order;
    }

    @Override
    public int GetTotalOrders() {
        int nborders =0 ;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT count(*) as total from orders o group by o.orderNumber ");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                nborders=rs.getInt("total") ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nborders;    }

    @Override
    public void cancelOrder(int id) {

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE orders SET IsValid= 0 where OrderNumber= ?");
            ps.setInt(1, id);


            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

}
