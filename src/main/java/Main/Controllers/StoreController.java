package Main.Controllers;

import Main.DAO.Interfaces.IToyRepository;
import Main.DAO.SingletonConnection;
import Main.DAO.ToyRepository;
import Main.Model.Toy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

public class StoreController implements Initializable {

    @FXML
    private TextField nameFilter ;
    @FXML
    private ComboBox prixFilter;
    @FXML
    private TableView<Toy> tableView;
    String choix="";
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
    @FXML
    private RadioButton plageA0_6 ;
    @FXML
    private RadioButton plageA6_12 ;
    @FXML
    private RadioButton plageA12_18 ;
    @FXML
    private RadioButton plageA18_24 ;
    @FXML
    private RadioButton plageA24_36 ;
    @FXML
    private RadioButton plageA3_5 ;
    @FXML
    private RadioButton plageA6_9 ;
    @FXML
    private RadioButton plageA9plus ;
    @FXML
    private Pagination pagination;
    private FilteredList<Toy> filteredData;

    private static final int ROWS_PER_PAGE = 4;
    IToyRepository toyRepository = new ToyRepository();




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /* Groupe pour les buttons radios*/
        ToggleGroup plageAgeGroupe = new ToggleGroup();
        plageA0_6.setToggleGroup(plageAgeGroupe);
        plageA6_12.setToggleGroup(plageAgeGroupe);
        plageA12_18.setToggleGroup(plageAgeGroupe);
        plageA18_24.setToggleGroup(plageAgeGroupe);
        plageA24_36.setToggleGroup(plageAgeGroupe);
        plageA3_5.setToggleGroup(plageAgeGroupe);
        plageA6_9.setToggleGroup(plageAgeGroupe);
        plageA9plus.setToggleGroup(plageAgeGroupe);

        Afficher();
        tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

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
                } catch (IOException | SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }


    public void PrixFilter(ActionEvent event){
        choix = prixFilter.getValue().toString();
        System.out.println(choix);
        if (choix.equals("Prix croissant")) {
            Comparator<Toy> comparator = Comparator.comparing(Toy::getPrice);
            changeTableView2(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
            oblist.sort(comparator);
        }else if (choix.equals("Prix décroissant")) {
            Comparator<Toy> comparator = Comparator.comparing(Toy::getPrice).reversed();
            changeTableView2(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);

            oblist.sort(comparator);



        }

    }


    public void maillist() throws IOException {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("S'inscrire au maillist");
        dialog.setContentText("Veuillez introduire votre adresse mail");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Your name: " + result.get());
        }

// The Java 8 way to get the response value (with lambda expression).
        result.ifPresent(name -> System.out.println("Your name: " + name));
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

    void Afficher() {

        oblist = FXCollections.observableArrayList(toyRepository.getAll());


        nom.setCellValueFactory(new PropertyValueFactory<>("name"));
        type.setCellValueFactory(new PropertyValueFactory<>("type_id"));
        prix.setCellValueFactory(new PropertyValueFactory<>("price"));
        pa.setCellValueFactory(new PropertyValueFactory<>("min_age"));
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



}



