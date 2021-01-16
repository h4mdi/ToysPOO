package Main.DAO;

import Main.DAO.Interfaces.ImailRepository;
import Main.Model.Mailinglist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MailinglistRepository implements ImailRepository {


    @Override
    public void addType(Mailinglist mailinglist) {
        Connection connection = SingletonConnection.getConnexion();

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO mailinglist(Email) " +
                    "VALUES (?)") ;
            ps.setString(1,mailinglist.getMail());

            ps.executeUpdate();

            System.out.println("mail ajouté");

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("error");

        }

    }

    @Override
    public List<Mailinglist> getAllMails() {
        List<Mailinglist> maillist = new ArrayList<>();
        Connection connection = SingletonConnection.getConnexion();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM mailinglist");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Mailinglist mailinglist = new Mailinglist(rs.getString("Email"));

                maillist.add(mailinglist);
                System.out.println(maillist);
                System.out.println("--------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maillist;
    }
}
