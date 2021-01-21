package Main.DAO.Interfaces;

import Main.Model.Order;
import Main.Model.OrderDetails;
import Main.Model.Toy;

import java.util.List;

public interface IOrderRepository extends IRepository<Toy>
{
    public void Cart(Order order,Toy toy);

//    void AddOrderDetails(Order order, Toy toy);
public int getLastOrderNumber() ;
    public List<Order> getAllOrders(int salesPersonId) ;
    public List<Order> getAllOrdersAdmin() ;
    public List<OrderDetails> getOrdersByNumber(int orderNumber);
    public int GetTotalOrders() ;
    }
