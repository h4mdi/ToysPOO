package Main.Controllers;

import Main.DAO.SingletonConnection;
import Main.Model.Toy;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminJouetsController implements Initializable {
    @FXML
    private TabPane tab;
    @FXML
    private ComboBox prixFilter;

    String choix="";

    @FXML
    private TextField nameFilter ;

    @FXML
    private Pagination pagination;
    private FilteredList<Toy> filteredData;

    private static final int ROWS_PER_PAGE = 4;
    @FXML
    private TableView<Toy> tableView;


    @FXML
    private TableColumn<Toy,Integer> id;
    @FXML
    private TableColumn<Toy,String> nom;

    
    @FXML
    private TableColumn<Toy,String> type;

    @FXML
    private TableColumn<Toy,Double> prix;


    @FXML
    private TableColumn<Toy,String> fseur;

    @FXML
    private TableColumn<Toy,Double> stock;

    @FXML
    private TableColumn<Toy,String> pa;

    @FXML
    private TableColumn<Toy,String> photo;




    ObservableList<Toy> oblist = FXCollections.observableArrayList();
    BufferedImage image;
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Connection connection = SingletonConnection.getConnexion();


        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM toys t left JOIN vendors v on t.id=v.id ");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                oblist.add(new Toy (rs.getInt("Id"),
                        rs.getString("Name"),rs.getInt("TypeId")
                        ,rs.getString("PicturePath"),rs.getDouble("Price"),rs.getString("v.Name"),
                        rs.getInt("MaxAge"),rs.getInt("MinAge"),
                        rs.getInt("Quantity")));




            }

        } catch (SQLException  e) {
            e.printStackTrace();
        }



        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        nom.setCellValueFactory(new PropertyValueFactory<>("name"));
        type.setCellValueFactory(new PropertyValueFactory<>("type_id"));
        prix.setCellValueFactory(new PropertyValueFactory<>("price"));
        fseur.setCellValueFactory(new PropertyValueFactory<>("vendor_name"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pa.setCellValueFactory(new PropertyValueFactory<>("min_age"));
        photo.setCellValueFactory(new PropertyValueFactory<>("photo"));



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



//
//        // 3. Wrap the FilteredList in a SortedList.
//        SortedList<Toy> sortedData = new SortedList<>(filteredData);
//
//        // 4. Bind the SortedList comparator to the TableView comparator.
//        // 	  Otherwise, sorting the TableView would have no effect.
//        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
//
//        // 5. Add sorted (and filtered) data to the table.
//        tableView.setItems(sortedData);


    }


    public void PrixFilter(ActionEvent event){
        choix = prixFilter.getValue().toString();
        System.out.println(choix);
//stateACS is a toggle button
        if (choix.equals("Prix croissant")) {
            Comparator<Toy> comparator = Comparator.comparing(Toy::getPrice);
            changeTableView2(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);

            //Sort in asc order
            oblist.sort(comparator);
        }else if (choix.equals("Prix décroissant")) {
            Comparator<Toy> comparator = Comparator.comparing(Toy::getPrice).reversed();
            changeTableView2(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);

            oblist.sort(comparator);



        }

    }



    public void logout(ActionEvent actionEvent) throws IOException {
        ButtonType yes = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"",yes,no);

        alert.setTitle("Confimer la déconnection");
        alert.setHeaderText("Voulez vous vraimenet déconnecter ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == yes ){
            Parent parent = FXMLLoader.load(getClass().getResource("/FXML/login.fxml"));
            Scene scene = new Scene(parent);
            Stage homeStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            homeStage.setScene(scene);
            homeStage.centerOnScreen();

            homeStage.show();
        } else {
            alert.close();        }



    }

//    public void onEditChanged(TableColumn.CellEditEvent<User, String> userStringCellEditEvent) {
//        User user =tableView.getSelectionModel().getSelectedItem();
//        user.setLogin(userStringCellEditEvent.getNewValue());
//        System.out.println(userStringCellEditEvent.getNewValue());
//
//    }

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
}







