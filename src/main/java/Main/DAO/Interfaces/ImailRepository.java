package Main.DAO.Interfaces;

import Main.Model.Mailinglist;

import java.util.List;

public interface ImailRepository {
    public void addType(Mailinglist mailinglist);
    public List<Mailinglist> getAllMails();

}
