package Main.Controllers;

import Main.DAO.Interfaces.IOrderRepository;
import Main.DAO.Interfaces.IToyRepository;
import Main.DAO.OrderRepository;
import Main.DAO.ToyRepository;
import Main.Model.Order;
import Main.Model.Session;
import Main.Model.Toy;
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
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;


public class CashierController implements Initializable {
    @FXML
    private TabPane tab;


    String choix = "";

    @FXML
    private TextField nameFilter;

    @FXML TextField total ;

    @FXML
    private Pagination pagination;
    private FilteredList<Toy> filteredData;

    private static final int ROWS_PER_PAGE = 7;
    @FXML
    private TableView<Toy> tableView;

    @FXML
    private TableView<Toy> OrderList;


    @FXML
    private TableColumn<Toy, Integer> id;
    @FXML
    private TableColumn<Toy, String> nom;
    @FXML
    private TableColumn<Toy, Double> prix;

    @FXML
    private TableColumn<Toy, Integer> idP;
    @FXML
    private TableColumn<Toy, String> nomP;
    @FXML
    private TableColumn<Toy, Double> prixP;

    @FXML
    private Button btn_afficherPhoto;
    @FXML
    private Button btn_addToy;
    @FXML
    private Button btn_updateToy;
    ObservableList<Toy> oblist ;

    IToyRepository toyRepository = new ToyRepository();
    IOrderRepository orderRepository = new OrderRepository();
    private String absolutePathPhoto;
    Double S = 0.0;
    int orderNumber =0 ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Afficher();

        //double clicks row recuperer le jouet et le mettre dans la table 2
        tableView.setRowFactory( tv -> {
            TableRow<Toy> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Toy rowData = row.getItem();
                    System.out.println(rowData.getId());

//                    idP.setCellValueFactory(new PropertyValueFactory<Toy, String>(String.valueOf(rowData.getId())));

                    OrderList.getItems().add(rowData);

                    idP.setCellValueFactory(new PropertyValueFactory<>("id"));

                    nomP.setCellValueFactory(new PropertyValueFactory<>("name"));
                    prixP.setCellValueFactory(new PropertyValueFactory<>("price"));
                    orderNumber =orderRepository.getLastOrderNumber()+1;
                    S= S+ rowData.getPrice();
                    total.setText(String.valueOf((S)));
                }
            });
            return row ; });





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

    void Afficher() {

        oblist = FXCollections.observableArrayList(toyRepository.getAll());

        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        nom.setCellValueFactory(new PropertyValueFactory<>("name"));
        prix.setCellValueFactory(new PropertyValueFactory<>("price"));

        // pa.setCellValueFactory(new PropertyValueFactory<>("max_age"));
//        photo.setCellValueFactory(new PropertyValueFactory<>("photo"));




        filteredData = new FilteredList<>(oblist, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        nameFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(toy -> newValue == null || newValue.isEmpty() || toy.getName().toLowerCase()
                    .contains(newValue.toLowerCase()));
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
    public void refresh(){
        oblist.clear();
        Afficher();
    }

    private void changeTableView(int index, int limit) {

        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, oblist.size());

        int minIndex = Math.min(toIndex, filteredData.size());
        SortedList<Toy> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);

    }

    private void changeTableView2(int index, int limit) {

        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, oblist.size());

        int minIndex = Math.min(toIndex, filteredData.size());
        SortedList<Toy> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        Comparator<Toy> comparator = Comparator.comparing(Toy::getPrice).reversed();
        oblist.sort(comparator);
        tableView.setItems(oblist);

    }



    @FXML
    private void ShowPhoto(ActionEvent event) throws SQLException {

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/FXML/ToyPhoto.fxml"));

        try {
            Loader.load();
            Toy toy = new Toy();
            ToyRepository toyRepository = new ToyRepository();
            ToyphotoController toyphotoController = Loader.getController();

            toy = toyRepository.getPhotos(tableView.getSelectionModel().getSelectedItem().getPhoto());

            toyphotoController.setProduit(toy);
            Parent p = Loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    @FXML
    private void imprimer(ActionEvent event) throws Exception {

//        Document document=new Document(PageSize.A4);
//
//        document.addAuthor("MBA&&CBYTE");
//        document.addTitle("Payement");
//        PdfWriter.getInstance(document, new FileOutputStream("Facture.pdf"));
//
//        document.open();
//        for(int i=0;i<oblist.size();i++)
//        {
//            Paragraph paragraph=new Paragraph(oblist.get(i).toString());
//            document.add(paragraph);
//        }
//
//        document.close();


        for (Toy toy : OrderList.getItems()) {
            Order order = new Order();
//            order.setId();
            order.setDate(LocalDate.now());
            order.setOrderNumber(String.valueOf(orderNumber));
            order.setIsValid(1);
            order.setSalesPersonId(Session.getCurrentSession());
//            System.out.println(Session.getCurrentSession());
           //add order
            orderRepository.Cart(order,toy);
//            System.out.println("le c est "+c);
//             orderRepository.AddOrderDetails(order,toy);
        }
        OrderList.getItems().clear();

    }



}
