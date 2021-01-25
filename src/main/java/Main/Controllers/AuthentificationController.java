package Main.Controllers;

import Main.DAO.SingletonConnection;
import Main.DAO.UsersRepository;
import Main.Model.Session;
import Main.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AuthentificationController implements Initializable {

    @FXML
    TextField tflogin ;

    @FXML
    PasswordField tfpassword ;

    @FXML
    Button submit ;




    public void loginAction(ActionEvent actionEvent) throws IOException {
        String login = tflogin.getText();
        String password = tfpassword.getText();
        UsersRepository usersRepository = new UsersRepository();
        User u = usersRepository.auth(login,password);
                if (u.getIsAdmin() == 1) {
                    infoBox("Connexion effectuée avec succès!", null, "Succès de connexion");
                    Parent parent = FXMLLoader.load(getClass().getResource("/FXML/Home.fxml"));
                    Scene scene = new Scene(parent);
                    Stage homeStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    homeStage.setScene(scene);
                    homeStage.centerOnScreen();
                    Session.start(u.getId());

                    homeStage.show();


                } else if (u.getIsAdmin() == 0) {
                    infoBox("Connexion effectuée avec succès!", null, "Succès de connexion");
                    Parent parent = FXMLLoader.load(getClass().getResource("/FXML/cashier.fxml"));


                    Scene scene = new Scene(parent);
                    Stage homeStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    homeStage.setScene(scene);
                    homeStage.centerOnScreen();
                    Session.start(u.getId());


                    homeStage.show();


            }

            else  {

                infoBox("Veuillez vérifier votre login et mot de passe", null, "Erreur de connexion");


            }
        }




    public  void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    public void storeAction(ActionEvent actionEvent) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/FXML/store.fxml"));
        Scene scene = new Scene(parent);
        Stage homeStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        homeStage.setScene(scene);
        homeStage.centerOnScreen();

        homeStage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
