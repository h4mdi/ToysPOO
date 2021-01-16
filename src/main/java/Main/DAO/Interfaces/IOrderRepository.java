package Main.DAO.Interfaces;

import Main.Model.Order;
import Main.Model.Toy;

public interface IOrderRepository extends IRepository<Toy>
{
    public void Cart(Order order,Toy toy);

//    void AddOrderDetails(Order order, Toy toy);
public int getLastOrderNumber() ;
}
