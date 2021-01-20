package Main.Controllers;

import Main.DAO.*;
import Main.Model.Session;
import Main.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML private Label hello;

    @FXML
    private Label nbtoys ;
    @FXML
    private Label nborders ;

    @FXML
    private Label nbvendors ;
    @FXML
    private Label nbusers ;
    @FXML
    private Label nbsubs ;

    @FXML
    private Label nbsum ;

    ToyRepository toyRepository = new ToyRepository();
    OrderRepository orderRepository = new OrderRepository();
    VendorRepository vendorRepository = new VendorRepository();
    MailinglistRepository mailinglistRepository = new MailinglistRepository();
    UsersRepository usersRepository = new UsersRepository();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nbtoys.setText(String.valueOf(toyRepository.GetTotaltoys()));
        nborders.setText(String.valueOf(orderRepository.GetTotalOrders()));
        nbvendors.setText(String.valueOf(vendorRepository.GetTotalVendors()));
        nbsubs.setText(String.valueOf(mailinglistRepository.GetTotalSubs()));
        nbusers.setText(String.valueOf(usersRepository.GetTotalUsers()));
        nbsum.setText(String.valueOf(toyRepository.GetTotalSales(null,null).toString().replace("[", "").replace("]", "" ))+" TND");

    }


    /* ================ /*
  * Sidebar Menu *
   ==================*/


    @FXML
    public void OpenHome(ActionEvent actionEvent) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/FXML/Home.fxml"));
        Scene scene = new Scene(parent);
        Stage homeStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        homeStage.setScene(scene);
        homeStage.centerOnScreen();

        homeStage.show();
    }

    @FXML
    public void OpenJouet(ActionEvent actionEvent) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/FXML/Toys.fxml"));
        Scene scene = new Scene(parent);
        Stage homeStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        homeStage.setScene(scene);
        homeStage.centerOnScreen();

        homeStage.show();
    }

    @FXML
    public void OpenFournisseur(ActionEvent actionEvent) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("/FXML/Vendor.fxml"));
        Scene scene = new Scene(parent);
        Stage homeStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        homeStage.setScene(scene);
        homeStage.centerOnScreen();

        homeStage.show();
    }

    @FXML
    public void openVentes(ActionEvent actionEvent) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource(""));
        Scene scene = new Scene(parent);
        Stage statsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        statsStage.setScene(scene);
        statsStage.centerOnScreen();

        statsStage.show();
    }

    @FXML
    public void openstats(ActionEvent actionEvent) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/FXML/Stats.fxml"));
        Scene scene = new Scene(parent);
        Stage statsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        statsStage.setScene(scene);
        statsStage.centerOnScreen();

        statsStage.show();
    }

    @FXML
    public void openUsers(ActionEvent actionEvent) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/FXML/Stats.fxml"));
        Scene scene = new Scene(parent);
        Stage statsStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        statsStage.setScene(scene);
        statsStage.centerOnScreen();

        statsStage.show();
    }



    @FXML
    public void OpenMaillist(ActionEvent actionEvent) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("/FXML/Maillist.fxml"));
        Scene scene = new Scene(parent);
        Stage homeStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        homeStage.setScene(scene);
        homeStage.centerOnScreen();

        homeStage.show();
    }

    @FXML
    public void logout(ActionEvent actionEvent) throws IOException {
        ButtonType yes = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", yes, no);

        alert.setTitle("Confimer la déconnection");
        alert.setHeaderText("Voulez vous vraimenet déconnecter ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes) {
            Parent parent = FXMLLoader.load(getClass().getResource("/FXML/login.fxml"));
            Scene scene = new Scene(parent);
            Stage homeStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            homeStage.setScene(scene);
            homeStage.centerOnScreen();

            homeStage.show();
        } else {
            alert.close();
        } }




}







