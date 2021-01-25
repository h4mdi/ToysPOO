package Main.Controllers;

import Main.DAO.Interfaces.IOrderRepository;
import Main.DAO.Interfaces.IToyRepository;
import Main.DAO.OrderRepository;
import Main.DAO.ToyRepository;
import Main.DAO.UsersRepository;
import Main.DAO.VendorRepository;
import Main.Model.Order;
import Main.Model.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class SalesController implements Initializable {

    @FXML
    private TabPane tab;


    String choix = "";

    @FXML
    private TextField nameFilter;
    @FXML
    private TextField nameFilter2;

    @FXML TextField total ;

    @FXML
    private Pagination pagination;
    @FXML
    private Pagination pagination2;
    private FilteredList<Order> filteredData;
    private FilteredList<Order> filteredData2;

    private static final int ROWS_PER_PAGE = 7;
    @FXML
    private TableView<Order> tableView;
    @FXML
    private TableView<Order> tableView2;

//    @FXML
//    private TableView<Toy> OrderList;


    @FXML
    private TableColumn<Order, Integer> id;
    @FXML
    private TableColumn<Order, LocalDate> date;
    @FXML
    private TableColumn<Order, Integer> numordre;


    @FXML
    private TableColumn<Order, Integer> id2;
    @FXML
    private TableColumn<Order, LocalDate> date2;
    @FXML
    private TableColumn<Order, Integer> numordre2;

    //
//    @FXML
//    private TableColumn<Toy, Integer> idP;
//    @FXML
//    private TableColumn<Toy, String> nomP;
//    @FXML
//    private TableColumn<Toy, Double> prixP;
//
//    @FXML
//    private Button btn_afficherPhoto;
//    @FXML
//    private Button btn_addToy;
//    @FXML
//    private Button btn_updateToy;
    ObservableList<Order> oblist ;
    ObservableList<Order> oblist2 ;

    IToyRepository toyRepository = new ToyRepository();
    IOrderRepository orderRepository = new OrderRepository();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            Afficher();
            Afficher2();
            tableView.setItems(FXCollections.observableArrayList(orderRepository.getAllOrdersAdmin()));
            tableView2.setItems(FXCollections.observableArrayList(orderRepository.getAllcancelledOrdersAdmin()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void Afficher() throws Exception {

        oblist = FXCollections.observableArrayList(orderRepository.getAllOrdersAdmin());
//        oblist2 = FXCollections.observableArrayList(orderRepository.getAllcancelledOrdersAdmin());

        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        numordre.setCellValueFactory(new PropertyValueFactory<>("OrderNumber"));

//        id2.setCellValueFactory(new PropertyValueFactory<>("id"));
//        date2.setCellValueFactory(new PropertyValueFactory<>("date"));
//        numordre2.setCellValueFactory(new PropertyValueFactory<>("OrderNumber"));


        filteredData = new FilteredList<>(oblist, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        nameFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(order -> newValue == null || newValue.isEmpty() || String.valueOf(order.getOrderNumber())
                    .contains(newValue));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });
        pagination.setPageCount((int) (Math.ceil(filteredData.size() * 1.0 / ROWS_PER_PAGE)));


        int totalPage = (int) (Math.ceil(oblist.size() * 1.0 / ROWS_PER_PAGE));
        pagination.setPageCount(totalPage);
        pagination.setCurrentPageIndex(0);
        changeTableView(0, ROWS_PER_PAGE);
        pagination.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> changeTableView(newValue.intValue(), ROWS_PER_PAGE));


    }

    void Afficher2() throws Exception {

        oblist2 = FXCollections.observableArrayList(orderRepository.getAllcancelledOrdersAdmin());


        id2.setCellValueFactory(new PropertyValueFactory<>("id"));
        date2.setCellValueFactory(new PropertyValueFactory<>("date"));
        numordre2.setCellValueFactory(new PropertyValueFactory<>("OrderNumber"));

        filteredData2 = new FilteredList<>(oblist2, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        nameFilter2.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData2.setPredicate(order -> newValue == null || newValue.isEmpty() || String.valueOf(order.getOrderNumber())
                    .contains(newValue));
            changeTableView(pagination2.getCurrentPageIndex(), ROWS_PER_PAGE);
        });
        pagination2.setPageCount((int) (Math.ceil(filteredData2.size() * 1.0 / ROWS_PER_PAGE)));


        int totalPage = (int) (Math.ceil(oblist2.size() * 1.0 / ROWS_PER_PAGE));
        pagination2.setPageCount(totalPage);
        pagination2.setCurrentPageIndex(0);
        changeTableView2(0, ROWS_PER_PAGE);
        pagination2.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> changeTableView2(newValue.intValue(), ROWS_PER_PAGE));



    }



    // actualiser la liste aprés chaque opération
    public void refresh() throws Exception {
        oblist.clear();
        oblist2.clear();
        Afficher();
        Afficher2();

    }

    private void changeTableView(int index, int limit) {

        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, oblist.size());

        int minIndex = Math.min(toIndex, filteredData.size());
        SortedList<Order> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);

    }

    private void changeTableView2(int index, int limit) {

        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, oblist2.size());

        int minIndex = Math.min(toIndex, filteredData2.size());
        SortedList<Order> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData2.subList(Math.min(fromIndex, minIndex), minIndex)));
        sortedData.comparatorProperty().bind(tableView2.comparatorProperty());

        tableView2.setItems(sortedData);

    }



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
    public void OpenVentes(ActionEvent actionEvent) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/FXML/Sales.fxml"));
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
    public void OpenUsers(ActionEvent actionEvent) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/FXML/User.fxml"));
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


    public void delete_Order(ActionEvent actionEvent) throws Exception {
        if (!tableView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Annulation vente");
            alert.setHeaderText("Etes-vous sur de vouloir annuler la vente "
                    + tableView.getSelectionModel().getSelectedItem().getOrderNumber() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                OrderRepository orderRepository = new OrderRepository();
                orderRepository.cancelOrder(tableView.getSelectionModel().getSelectedItem().getOrderNumber());

                refresh();

            }
        }


    }
}
