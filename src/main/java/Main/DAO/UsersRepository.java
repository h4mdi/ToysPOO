package Main.DAO;

import Main.DAO.Interfaces.IUserRepository;
import Main.DAO.Interfaces.IVendorRepository;
import Main.Model.User;
import Main.Model.Vendor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersRepository implements IUserRepository{
    Connection connection = SingletonConnection.getConnexion();


    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public int GetTotalUsers() {

        int nbusers =0 ;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT count(*) as total from users u where u.isAdmin=0 ");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                nbusers=rs.getInt("total") ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nbusers;

    }

    @Override
    public User findById(int id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void add(User entity) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(User entity) {

    }
}
