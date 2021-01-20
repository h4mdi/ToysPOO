package Main.DAO.Interfaces;

import Main.Model.User;
import Main.Model.Vendor;

import java.util.List;

public interface IUserRepository extends IRepository<User> {
    public List<User> getAllUsers();
    public void addUser(User user);
    public int GetTotalUsers() ;
}
