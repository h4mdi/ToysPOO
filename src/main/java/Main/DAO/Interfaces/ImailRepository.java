package Main.DAO.Interfaces;

import Main.Model.Mailinglist;

import java.util.List;

public interface ImailRepository {
    public void addEmail(String email);
    public List<Mailinglist> getAllMails();
    public int GetTotalSubs() ;

}
