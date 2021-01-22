package Main.Controllers;

import Main.DAO.Interfaces.IVendorRepository;
import Main.DAO.ToyRepository;
import Main.DAO.UsersRepository;
import Main.DAO.VendorRepository;
import Main.Model.Toy;
import Main.Model.User;
import Main.Model.Vendor;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class VendorController implements Initializable {
    @FXML
    private TabPane tab;
    @FXML
    private ComboBox prixFilter;

    String choix = "";

    @FXML
    private TextField nameFilter;

    @FXML
    private Pagination pagination;
    private FilteredList<Vendor> filteredData;

    private static final int ROWS_PER_PAGE = 7;
    @FXML
    private TableView<Vendor> tableView;

    @FXML
    private TableColumn<Vendor, String> nom;
    @FXML
    private TableColumn<Vendor, String> adresse;
    @FXML
    private TableColumn<Vendor, String> email;
    @FXML
    private TableColumn<Vendor, String> facebook;

    @FXML
    private Button btn_afficherPhoto;
    @FXML
    private Button btn_addToy;
    @FXML
    private Button btn_updateToy;

    @FXML
    private TextField update_name;
    @FXML
    private TextField update_vendor;
    @FXML
    private TextField update_price;

    @FXML
    private TextField m_f_nom;
    @FXML
    private TextField m_f_adr;
    @FXML
    private TextField m_f_mail;
    @FXML
    private TextField m_f_fac;
    @FXML
    private Button PhotoUpload;
    @FXML
    private ComboBox<String> update_type;

    @FXML
    private TextField toyphoto;
    @FXML
    private TextField insert_name;
    @FXML
    private TextField insert_email;
    @FXML
    private TextField insert_adress;
    @FXML
    private TextField insert_facebook;
    @FXML
    private TextField photoProduit;
    @FXML
    private ComboBox<String> insert_Vendor;

    @FXML
    private ComboBox<String> insert_type1;

    @FXML
    private Button btn_delVendor;
    ObservableList<Vendor> oblist;

    IVendorRepository VendorRepository = new VendorRepository();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Afficher();
    }


    void Afficher() {

        oblist = FXCollections.observableArrayList(VendorRepository.getAllVendors());

        nom.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("address"));
        facebook.setCellValueFactory(new PropertyValueFactory<>("facebook"));

        // pa.setCellValueFac tory(new PropertyValueFactory<>("max_age"));
//        photo.setCellValueFactory(new PropertyValueFactory<>("photo"));


        filteredData = new FilteredList<>(oblist, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        nameFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(vendor -> newValue == null || newValue.isEmpty() || vendor.getName().toLowerCase()
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
    public void refresh(){
        oblist.clear();
        Afficher();
    }

    private void changeTableView(int index, int limit) {

        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, oblist.size());

        int minIndex = Math.min(toIndex, filteredData.size());
        SortedList<Vendor> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        tableView.setItems(sortedData);

    }





    @FXML
    private void SaveAddToy(ActionEvent event) {


        Vendor vendor = new Vendor();

        vendor.setName(insert_name.getText());
        vendor.setEmail(insert_email.getText());
        vendor.setAddress(insert_adress.getText());
        vendor.setFacebook(insert_facebook.getText());

        System.out.println(insert_facebook.getText());

        if (insert_name.getText().equals("") || insert_adress.getText().equals("") ||
                insert_facebook.getText().equals("") || insert_email.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alerte");
            alert.setHeaderText("Vous devez remplir TOUT LES CHAMPS SVP");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                tab.getSelectionModel().select(1);
                Afficher();
                insert_name.setText("");
                insert_adress.setText("");
                insert_facebook.setText("");
                insert_email.setText("");

            }

        } else {
            VendorRepository.addVendors(vendor);
            Afficher();
            insert_name.setText("");
            insert_adress.setText("");
            insert_facebook.setText("");
            insert_email.setText("");
            tab.getSelectionModel().select(0);

              }

    }

    @FXML
    private void show_updateForm(ActionEvent event) {


        m_f_nom.setText(tableView.getSelectionModel().getSelectedItem().getName());
        m_f_adr.setText(tableView.getSelectionModel().getSelectedItem().getAddress());
        m_f_mail.setText(tableView.getSelectionModel().getSelectedItem().getEmail());
        m_f_fac.setText(tableView.getSelectionModel().getSelectedItem().getFacebook());


        tab.getSelectionModel().select(2);
    }

    @FXML
    private void save_Update(ActionEvent event) {

        Vendor vendor = new Vendor() ;

        vendor.setId(tableView.getSelectionModel().getSelectedItem().getId());
        vendor.setName(m_f_nom.getText());
        vendor.setEmail(m_f_mail.getText()); ;//type
        vendor.setAddress(m_f_adr.getText()); ;
        vendor.setFacebook(m_f_fac.getText());





        if (m_f_nom.getText().equals("") || m_f_mail.getText().equals("") ||  m_f_adr.getText()==null ||
                m_f_fac.getText()==null   ) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alerte");
            alert.setHeaderText("Vous devez remplir TOUT LES CHAMPS SVP");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                tab.getSelectionModel().select(2);
                Afficher();
                m_f_nom.setText("");
                m_f_mail.setText("");
                m_f_adr.setText("");
                m_f_fac.setText("");


            }

        }

        else {  VendorRepository.editVendor(vendor);
            Afficher();
            m_f_nom.setText("");
            m_f_mail.setText("");
            m_f_adr.setText("");
            m_f_fac.setText("");
            tab.getSelectionModel().select(0);


        }


    }

    @FXML
    private void photoChooser(ActionEvent event) {

    }


    @FXML
    private void show_addForm(ActionEvent event) {
        tab.getSelectionModel().select(1);
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


    public void delete_Vendor(ActionEvent actionEvent) {
        if (!tableView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suppression d'un utilisateur");
            alert.setHeaderText("Etes-vous sur de vouloir supprimer l'utilisateur "
                    + tableView.getSelectionModel().getSelectedItem().getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                VendorRepository vendorRepository = new VendorRepository();
                System.out.println("aywah"+tableView.getSelectionModel().getSelectedItem().getId());
                vendorRepository.deleteVendor(tableView.getSelectionModel().getSelectedItem().getId());
                refresh();

            }

        }
    }
}






