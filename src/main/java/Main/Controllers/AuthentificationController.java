package Main.Controllers;

import Main.DAO.SingletonConnection;
import Main.Metier.IMetier;
import Main.Model.Session;
import Main.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthentificationController implements IMetier{

    @FXML
    TextField tflogin ;

    @FXML
    PasswordField tfpassword ;

    @FXML
    Button submit ;


    public void addUser(User u) {
        Connection connection = SingletonConnection.getConnexion();
        try {
            PreparedStatement ps = connection.prepareStatement("insert into utilisateur(login,password,id) values(?,?,?)") ;
            ps.setString(1,u.getLogin());
            ps.setString(2,u.getPassword());
            ps.setInt(3,u.getId());
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


//
//    public void addUser(ActionEvent actionEvent) {
//        String login = tflogin.getText();
//        String password = tfpassword.getText();
//        User u = new User(login,password,1920) ;
//        addUser(u);
//
//    }
    public void loginAction(ActionEvent actionEvent) throws IOException {
        String login = tflogin.getText();
        String password = tfpassword.getText();

//        User u = new User(login,password,1920) ;

        Connection connection = SingletonConnection.getConnexion();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users u WHERE u.login = ? and u.password = ?");
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet resultSet = ps.executeQuery();
            User u = new User();


            if (resultSet.next()) {
                u.setLogin(resultSet.getString("u.Login"));
                u.setId(resultSet.getInt("u.Id"));
                u.setPassword(resultSet.getString("u.Password"));
                u.setIsAdmin(resultSet.getInt("u.isAdmin"));

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
            }

            else  {

                infoBox("Veuillez vérifier votre login et mot de passe", null, "Erreur de connexion");


            }
        }

        catch(Exception e){
            e.printStackTrace();
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

}
