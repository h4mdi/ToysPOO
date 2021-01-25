package Main.DAO;

import Main.DAO.Interfaces.IUserRepository;
import Main.DAO.Interfaces.IVendorRepository;
import Main.Model.Toy;
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
    public User auth(String login,String password) {
        User u = new User();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users u WHERE u.login = ? and u.password = ?");
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                u.setLogin(resultSet.getString("u.Login"));
                u.setId(resultSet.getInt("u.Id"));
                u.setPassword(resultSet.getString("u.Password"));
                u.setIsAdmin(resultSet.getInt("u.isAdmin"));


            }
        } catch (SQLException e) {
        }
return u;
    }



    @Override
    public List<User> getAllUsers() {
        List<User> Userlist = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(rs.getInt("Id"),
                        rs.getString("Login"), rs.getString("Password")
                        , rs.getString("Email"), rs.getString("PhotoPath"),
//                        rs.getString("v.Name"),
                        rs.getString("Phone"),
                        rs.getString("FacebookUrl"), rs.getString("SIN"),
                        rs.getInt("IsAdmin"));

                Userlist.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Userlist;
    }
    @Override
    public void addUser(User user) {

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO users(Id,Login,Password,Email,PhotoPath,Phone,FacebookUrl,SIN,IsAdmin) " +
                    "VALUES (? , ? ,? ,? ,? ,? ,? ,? , ?)") ;
            ps.setInt(1,user.getId());
            ps.setString(2,user.getLogin());
            ps.setString(3,user.getPassword());
            ps.setString(4,user.getEmail());
            ps.setString(5,user.getPhoto());
            ps.setString(6,user.getPhone());
            ps.setString(7,user.getFacebook());
            ps.setString(8,user.getSin());
            ps.setInt(9,user.getIsAdmin());

            ps.executeUpdate();

            System.out.println("Utilisateur ajouté");

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("error");

        }

    }

    @Override
    public void deleteUser(int id) {
        try {

            PreparedStatement st = connection.prepareStatement("DELETE FROM users where Id = ?");
            st.setInt(1, id);
            st.executeUpdate();
            System.out.println("c'est bon");

        } catch (SQLException e) {
            System.out.println("erreur");
        }
    }

    @Override
    public void editUser(User user) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE users SET Login=? , Password= ?, Email= ?, PhotoPath= ?, Phone= ?, FacebookUrl= ?, SIN= ? where Id= ?");
            ps.setInt(8,user.getId());
            ps.setString(1,user.getLogin());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getEmail());
            ps.setString(4,user.getPhoto());
            ps.setString(5,user.getPhone());
            ps.setString(6,user.getFacebook());
            ps.setString(7,user.getSin());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();

        }
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

    @Override
    public User getPhotos(String path)throws SQLException {
        String req = "Select * from users t where t.PhotoPath = ?";
        PreparedStatement st = connection.prepareStatement(req);
        st.setString(1, path);
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            User obj = new User();
            obj.setId(rs.getInt("id"));

            obj.setPhoto(rs.getString("PhotoPath"));
            System.out.println("path :" + obj.getPhoto());
            return obj;
        }
        return null;    }

}
