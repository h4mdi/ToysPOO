package Main.Controllers;

import Main.Model.Toy;
import Main.Model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class UserPhotoController implements Initializable {

    @FXML
    private ImageView idImg;
//    @FXML
//    private Label btn_exit;
//    @FXML
//    private void aaaa(MouseEvent event) {
//      System.exit(0);
//    }

    private User user;
    private static UserPhotoController instance;

    public UserPhotoController() {
        instance = this;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public User getUser() { return this.user; }

    public void setUser(User user) {
        this.user = user;
        Image im = new Image(user.getPhoto());
        idImg.setImage(im);
        System.out.println(im);

    }





    static public UserPhotoController getInstance() {
        return instance;
    }

}
