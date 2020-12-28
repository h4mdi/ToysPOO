package Main.Metier;

import Main.DAO.SingletonConnection;
import Main.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MetierUser implements IMetier {

    @Override
    public void addUser(User u) {
        Connection connection = SingletonConnection.getConnexion();
        try {
            PreparedStatement ps = connection.prepareStatement("insert into User values(?,?,?)") ;
            ps.setString(1,u.getLogin());
            ps.setString(2,u.getPassword());
            ps.setInt(3,u.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
