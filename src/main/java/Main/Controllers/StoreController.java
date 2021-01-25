package Main.Controllers;

import Main.DAO.Interfaces.IToyRepository;
import Main.DAO.MailinglistRepository;
import Main.DAO.ToyRepository;
import Main.Model.Toy;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
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
    private ComboBox typeFilter;
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
    private Button OldFilter_btn ;

    @FXML
    private Pagination pagination;
    private FilteredList<Toy> filteredData;

    private static final int ROWS_PER_PAGE = 7;
    ToyRepository toyRepository = new ToyRepository();
    MailinglistRepository mailinglistRepository = new MailinglistRepository();

@FXML ImageView img  ;

    @FXML
    Spinner minAge;

    @FXML
    Spinner maxAge;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        typeFilter.setItems(FXCollections.observableArrayList(toyRepository.getAllTypes()));

        /* Groupe pour les buttons radios*/
       
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

                    toyphotoController.setToy(toy);
                    Parent p = Loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(p));
                    stage.show();
                } catch (IOException | SQLException ex) {
                    javax.swing.JOptionPane.showMessageDialog(null,"Erreur de connection","Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        img.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Parent parent = null;
            try {
                parent = FXMLLoader.load(getClass().getResource("/FXML/login.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(parent);
            Stage homeStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            homeStage.setScene(scene);
            homeStage.centerOnScreen();

            homeStage.show();
            event.consume();
        });

        minAge.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10)
        );

        maxAge.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10)
        );
    }


    public void PrixFilter(ActionEvent event){
        Afficher();
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

    public void TypeFilter(ActionEvent event){
        choix = typeFilter.getValue().toString();
        System.out.println(choix);
        toyRepository.findByType(choix);
        oblist = FXCollections.observableArrayList(toyRepository.findByType(choix));


        nom.setCellValueFactory(new PropertyValueFactory<>("name"));
        // type.setCellValueFactory(new PropertyValueFactory<>("type_id"));

        type.setCellValueFactory(cellData -> Bindings.createStringBinding(() ->
                String.valueOf(cellData.getValue().getType_name())));

        prix.setCellValueFactory(new PropertyValueFactory<>("price"));

        pa.setCellValueFactory(cellData -> Bindings.createStringBinding(() ->
                cellData.getValue().getMin_age() + " ans et  " + cellData.getValue().getMax_age()+" ans"));


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


    public void OldFilter(ActionEvent event){


//        System.out.println(minAge.getValue().toString());

        int min = Integer.parseInt(minAge.getValue().toString());
        int max = Integer.parseInt(maxAge.getValue().toString());


        oblist = FXCollections.observableArrayList(toyRepository.findByOld(min,max));


        nom.setCellValueFactory(new PropertyValueFactory<>("name"));
        // type.setCellValueFactory(new PropertyValueFactory<>("type_id"));

        type.setCellValueFactory(cellData -> Bindings.createStringBinding(() ->
                String.valueOf(cellData.getValue().getType_name())));

        prix.setCellValueFactory(new PropertyValueFactory<>("price"));
        //pa.setCellValueFactory(new PropertyValueFactory<>("min_age"));
        // pa.setCellValueFactory(new PropertyValueFactory<>("max_age"));
//        photo.setCellValueFactory(new PropertyValueFactory<>("photo"));

        pa.setCellValueFactory(cellData -> Bindings.createStringBinding(() ->
                cellData.getValue().getMin_age() + " ans et  " + cellData.getValue().getMax_age()+" ans"));


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





    public void maillist() throws IOException {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("S'inscrire au maillist");
        dialog.setContentText("Veuillez introduire votre adresse mail");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(email -> mailinglistRepository.addEmail(email));
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

        oblist = FXCollections .observableArrayList(toyRepository.getAll());


        nom.setCellValueFactory(new PropertyValueFactory<>("name"));
       // type.setCellValueFactory(new PropertyValueFactory<>("type_id"));

        type.setCellValueFactory(cellData -> Bindings.createStringBinding(() ->
                String.valueOf(cellData.getValue().getType_name())));

        prix.setCellValueFactory(new PropertyValueFactory<>("price"));
        //pa.setCellValueFactory(new PropertyValueFactory<>("min_age"));
        // pa.setCellValueFactory(new PropertyValueFactory<>("max_age"));
//        photo.setCellValueFactory(new PropertyValueFactory<>("photo"));

        pa.setCellValueFactory(cellData -> Bindings.createStringBinding(() ->
                cellData.getValue().getMin_age() + " ans et  " + cellData.getValue().getMax_age()+" ans"));


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



