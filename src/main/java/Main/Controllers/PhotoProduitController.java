/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Controllers;

import Main.Model.Toy;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author user
 */
public class PhotoProduitController implements Initializable {

   
    @FXML
    private ImageView idImg;
    @FXML
    private Label btn_exit;
    @FXML
    private void aaaa(MouseEvent event) {
      System.exit(0);
    }
   
   /* private Commentaire c;
    CommentaireService co = new CommentaireService();
    UserService userService = new UserService();
    User user;*/
    private Toy toy;
    private static PhotoProduitController instance;
    
    public PhotoProduitController() {
        instance = this;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
     public Toy getProduit() { return this.toy; }
    
    public void setProduit(Toy toy) {
        this.toy = toy;
        Image im = new Image(toy.getPhoto());
        idImg.setImage(im);
       
       //idDateS.setValue(this.programme.getDateSortie());
        //idDateP.setValue(this.programme.getDateProjection());
       
        System.out.println(toy.getId());
     
    }
  
   
    
    static public PhotoProduitController getInstance() { 
        return instance;
    }
  

  /*  @FXML
    public void handleBtnAction (ActionEvent event) throws SQLException{
        UserSession session = UserSession.getInstance(null,null,null,null,null,null,null);
        c.setId_programme(programme.getId());
        int idd = userService.get(session.getUserName()).getId();
        c.setUser(idd);
        c.setContenu(contenu.getText());
        co.ajouter(c);
    }
  */
}
