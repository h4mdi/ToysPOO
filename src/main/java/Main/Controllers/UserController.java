package Main.Controllers;

import Main.DAO.ToyRepository;
import Main.DAO.UsersRepository;
import Main.Model.Toy;
import Main.Model.User;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private TabPane tab;
    @FXML
    private ComboBox prixFilter;

    String choix = "";

    @FXML
    private TextField nameFilter;

    @FXML
    private Pagination pagination;
    private FilteredList<User> filteredData;

    private static final int ROWS_PER_PAGE = 4;
    @FXML
    private TableView<User> tableView;


    @FXML
    private TableColumn<User, Integer> id;
    @FXML
    private TableColumn<User, String> nom;


    @FXML
    private TableColumn<User, String> mdp;

    @FXML
    private TableColumn<User, String> tel;


    @FXML
    private TableColumn<User, String> sin;

    @FXML
    private TableColumn<User, String> email;

    @FXML
    private TableColumn<User, String> fb;



    @FXML
    private TextField User_photo;
    @FXML
    private TextField insert_name;
    @FXML
    private TextField insert_pwd;
    @FXML
    private TextField insert_Sin;
    @FXML
    private TextField insert_email;
    @FXML
    private TextField insert_tel;
    @FXML
    private TextField insert_fb;

    @FXML
    private TextField User_photoEdit;
    @FXML
    private TextField insert_nameEdit;
    @FXML
    private TextField insert_pwdEdit;
    @FXML
    private TextField insert_SinEdit;
    @FXML
    private TextField insert_emailEdit;
    @FXML
    private TextField insert_telEdit;
    @FXML
    private TextField insert_fbEdit;


    ObservableList<User> oblist ;

    UsersRepository userRepository = new UsersRepository();

    private String absolutePathPhoto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Afficher();
        tableView.setItems(FXCollections.observableArrayList(userRepository.getAllUsers()));
    }


    void Afficher() {

        oblist = FXCollections.observableArrayList(userRepository.getAllUsers());

        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        nom.setCellValueFactory(new PropertyValueFactory<>("login"));
        mdp.setCellValueFactory(new PropertyValueFactory<>("password"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tel.setCellValueFactory(new PropertyValueFactory<>("phone"));
        sin.setCellValueFactory(new PropertyValueFactory<>("sin"));
        fb.setCellValueFactory(new PropertyValueFactory<>("facebook"));
        // pa.setCellValueFactory(new PropertyValueFactory<>("max_age"));
//        photo.setCellValueFactory(new PropertyValueFactory<>("photo"));


        filteredData = new FilteredList<>(oblist, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        nameFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(user -> newValue == null || newValue.isEmpty() || user.getLogin().toLowerCase()
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
        SortedList<User> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);

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


    public void show_updateForm(ActionEvent actionEvent) {
        insert_nameEdit.setText(tableView.getSelectionModel().getSelectedItem().getLogin());
        insert_pwdEdit.setText(tableView.getSelectionModel().getSelectedItem().getPassword());
        insert_SinEdit.setText(tableView.getSelectionModel().getSelectedItem().getSin());
        insert_emailEdit.setText(tableView.getSelectionModel().getSelectedItem().getEmail());
        insert_telEdit.setText(tableView.getSelectionModel().getSelectedItem().getPhone());
        User_photoEdit.setText(tableView.getSelectionModel().getSelectedItem().getPhoto());
        insert_fbEdit.setText(tableView.getSelectionModel().getSelectedItem().getFacebook());


        tab.getSelectionModel().select(2);
    }

    @FXML
    private void ShowPhoto(ActionEvent event) throws SQLException {

        FXMLLoader Loader = new FXMLLoader();
        Loader.setLocation(getClass().getResource("/FXML/UserPhoto.fxml"));



        try {
            Loader.load();
            User user = new User();
            UsersRepository userRepository = new UsersRepository();
            UserPhotoController userPhotoController = Loader.getController();

            user = userRepository.getPhotos(tableView.getSelectionModel().getSelectedItem().getPhoto());
            System.out.println(tableView.getSelectionModel().getSelectedItem().getPhoto());

            userPhotoController.setUser(user);
            Parent p = Loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void show_addForm(ActionEvent actionEvent) {
        tab.getSelectionModel().select(1);
    }

    public void photoChooser(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File choix = fileChooser.showOpenDialog(null);
        if (choix != null) {

            absolutePathPhoto = choix.getAbsolutePath();
            User_photo.setText(choix.getName());
        } else {
            System.out.println("Image introuvable");
        }
    }

    public void upphotoChooser(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File choix = fileChooser.showOpenDialog(null);
        if (choix != null) {

            absolutePathPhoto = choix.getAbsolutePath();

            User_photoEdit.setText(choix.getName());
        } else {
            System.out.println("Image introuvable");
        }
    }


    public void SaveAddUser(ActionEvent actionEvent) {
        deplacerVers(User_photo, absolutePathPhoto,"C:\\Users\\hp\\Sesame\\3Java_Sesame\\Projet_POO_mvn_fin\\src\\main\\resources\\usersphoto");
        deplacerVers(User_photo, absolutePathPhoto,"C:\\wamp64\\www\\toys\\userphotos");
        User user = new User() ;

        user.setLogin(insert_name.getText());
        user.setPassword(insert_pwd.getText()); ;//type
        user.setPhoto("http://localhost/toys/userphotos/"+User_photo.getText()) ;
        user.setEmail(insert_email.getText());
        user.setSin(insert_Sin.getText());

        user.setPhone(insert_tel.getText());
        user.setFacebook(insert_fb.getText());
        user.setIsAdmin(0);




        if (insert_name.getText().equals("") || insert_pwd.getText().equals("") ||  insert_fb.getText()==null ||
                insert_email.getText()==null || User_photo.getText()==null || insert_Sin.getText()==null || insert_tel.getText().equals("")  ) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alerte");
            alert.setHeaderText("Vous devez remplir TOUT LES CHAMPS SVP");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                tab.getSelectionModel().select(1);
                Afficher();
                insert_name.setText("");
                insert_pwd.setText("");
                insert_email.setText("");
                User_photo.setText("");
                insert_Sin.setText("");
                insert_tel.setText("");
                insert_fb.setText("");

            }

        }

        else {  userRepository.addUser(user);
            Afficher();
            insert_name.setText("");
            insert_name.setText("");
            insert_pwd.setText("");
            insert_email.setText("");
            User_photo.setText("");
            insert_Sin.setText("");
            insert_tel.setText("");
            insert_fb.setText("");
            tab.getSelectionModel().select(0);


        }
    }

    public void deplacerVers(TextField txtField, String path, String copyTo) {
        if (!(txtField.getText().equals("")) ){
            try {
                String[] args = { "CMD", "/C", "COPY", "/Y", path, copyTo };
                Process p = Runtime.getRuntime().exec(args);
                p.waitFor();
                System.out.println("executé");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void save_Update(ActionEvent actionEvent) {
        deplacerVers(User_photoEdit, absolutePathPhoto,"C:\\Users\\hp\\Sesame\\3Java_Sesame\\Projet_POO_mvn_fin\\src\\main\\resources\\usersphoto");
        deplacerVers(User_photoEdit, absolutePathPhoto,"C:\\wamp64\\www\\toys\\userphotos");
        User user = new User() ;

        user.setId(tableView.getSelectionModel().getSelectedItem().getId());
        user.setLogin(insert_nameEdit.getText());
        user.setPassword(insert_pwdEdit.getText()); ;//type
//        user.setPhoto("http://localhost/toys/userphotos/"+User_photoEdit.getText()) ;
        user.setPhoto("http://localhost/toys/userphotos/"+User_photoEdit.getText()) ;

        user.setEmail(insert_emailEdit.getText());
        user.setSin(insert_SinEdit.getText());
        user.setPhone(insert_telEdit.getText());
        user.setFacebook(insert_fbEdit.getText());
        user.setIsAdmin(0);




        if (insert_nameEdit.getText().equals("") || insert_pwdEdit.getText().equals("") ||  insert_fbEdit.getText()==null ||
                insert_emailEdit.getText()==null || User_photoEdit.getText()==null || insert_SinEdit.getText()==null || insert_telEdit.getText().equals("")  ) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alerte");
            alert.setHeaderText("Vous devez remplir TOUT LES CHAMPS SVP");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                tab.getSelectionModel().select(2);
                Afficher();
                insert_nameEdit.setText("");
                insert_pwdEdit.setText("");
                insert_emailEdit.setText("");
                User_photoEdit.setText("");
                insert_SinEdit.setText("");
                insert_telEdit.setText("");
                insert_fbEdit.setText("");

            }

        }

        else {  userRepository.editUser(user);
            Afficher();
            insert_nameEdit.setText("");
            insert_nameEdit.setText("");
            insert_pwdEdit.setText("");
            insert_emailEdit.setText("");
            User_photoEdit.setText("");
            insert_SinEdit.setText("");
            insert_telEdit.setText("");
            insert_fbEdit.setText("");
            tab.getSelectionModel().select(0);


        }

    }

    public void delete_User(ActionEvent actionEvent) {
        if (!tableView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suppression d'un utilisateur");
            alert.setHeaderText("Etes-vous sur de vouloir supprimer l'utilisateur "
                    + tableView.getSelectionModel().getSelectedItem().getLogin() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                UsersRepository userRepository = new UsersRepository();
                System.out.println("aywah"+tableView.getSelectionModel().getSelectedItem().getId());
                userRepository.deleteUser(tableView.getSelectionModel().getSelectedItem().getId());
                refresh();

            }

        }
    }
}
