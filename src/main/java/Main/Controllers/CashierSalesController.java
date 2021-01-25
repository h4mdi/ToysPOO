package Main.Controllers;

import Main.DAO.Interfaces.IOrderRepository;
import Main.DAO.Interfaces.IToyRepository;
import Main.DAO.OrderRepository;
import Main.DAO.ToyRepository;
import Main.Model.Order;
import Main.Model.OrderDetails;
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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class CashierSalesController implements Initializable {

    @FXML
    private TabPane tab;


    String choix = "";

    @FXML
    private TextField nameFilter;

    @FXML TextField total ;

    @FXML
    private Pagination pagination;
    private FilteredList<Order> filteredData;

    private static final int ROWS_PER_PAGE = 7;
    @FXML
    private TableView<Order> tableView;

//    @FXML
//    private TableView<Toy> OrderList;


    @FXML
    private TableColumn<Order, Integer> id;
    @FXML
    private TableColumn<Order, LocalDate> date;
    @FXML
    private TableColumn<Order, Integer> numordre;

    ObservableList<Order> oblist ;

    IToyRepository toyRepository = new ToyRepository();
    IOrderRepository orderRepository = new OrderRepository();





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Afficher();
            tableView.setItems(FXCollections.observableArrayList(orderRepository.getAllOrders(Session.getCurrentSession())));

        } catch (Exception e) {
            e.printStackTrace();
        }



    }


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


    void Afficher() throws Exception {

        oblist = FXCollections.observableArrayList(orderRepository.getAllOrders(Session.getCurrentSession()));

        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        numordre.setCellValueFactory(new PropertyValueFactory<>("OrderNumber"));


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

    // actualiser la liste aprés chaque opération
    public void refresh() throws Exception {
        oblist.clear();
        Afficher();
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


    public void OpenCashierHome(ActionEvent actionEvent) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("/FXML/Cashier.fxml"));
        Scene scene = new Scene(parent);
        Stage homeStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        homeStage.setScene(scene);
        homeStage.centerOnScreen();

        homeStage.show();

    }

    public void ShowDetails(ActionEvent actionEvent) throws SQLException {

        FXMLLoader Loader = new FXMLLoader();
             Loader = new FXMLLoader(getClass().getResource("/FXML/OrderDetails.fxml"));


        try {
            Loader.load();
            List<OrderDetails> orders ;
            OrderDetailsController controller =  Loader.getController();

            orders= orderRepository.getOrdersByNumber(tableView.getSelectionModel().getSelectedItem().getOrderNumber());
            controller.setOrders(orders);
            System.out.println(orders+"hahomma");

            Parent p = Loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
