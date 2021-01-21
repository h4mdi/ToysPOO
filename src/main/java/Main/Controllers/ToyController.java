package Main.Controllers;

import Main.DAO.Interfaces.IToyRepository;
import Main.DAO.ToyRepository;
import Main.Model.Toy;
import Main.Model.ToyType;
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

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class ToyController implements Initializable {
    @FXML
    private TabPane tab;
    @FXML
    private ComboBox prixFilter;

    String choix = "";

    @FXML
    private TextField nameFilter;

    @FXML
    private Pagination pagination;
    private FilteredList<Toy> filteredData;

    private static final int ROWS_PER_PAGE = 4;
    @FXML
    private TableView<Toy> tableView;


    @FXML
    private TableColumn<Toy, Integer> id;
    @FXML
    private TableColumn<Toy, String> nom;


    @FXML
    private TableColumn<Toy, String> type;

    @FXML
    private TableColumn<Toy, Double> prix;


    @FXML
    private TableColumn<Toy, String> fseur;

    @FXML
    private TableColumn<Toy, Double> stock;

    @FXML
    private TableColumn<Toy, String> pa;

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
    private TextField update_stock;
    @FXML
    private TextField update_picture;
    @FXML
    private TextField update_min;
    @FXML
    private TextField update_max;
    @FXML
    private Button PhotoUpload;
    @FXML
    private ComboBox<String> update_type;

    @FXML
    private TextField toyphoto;
    @FXML
    private TextField insert_name;
    @FXML
    private TextField insert_vendor;
    @FXML
    private TextField insert_price;
    @FXML
    private TextField insert_stock;
    @FXML
    private TextField photoProduit;
    @FXML
    private ComboBox<String> insert_Vendor;

    @FXML
    private ComboBox<String> insert_type1;

@FXML
    private Button btn_delToy ;
    ObservableList<Toy> oblist ;

    ToyRepository toyRepository = new ToyRepository();
    private String absolutePathPhoto;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Afficher();
        insert_Vendor.setItems(FXCollections.observableArrayList(toyRepository.getAllVendors()));
        insert_type1.setItems(FXCollections.observableArrayList(toyRepository.getAllTypes()));

            }




    public void PrixFilter(ActionEvent event) {
        choix = prixFilter.getValue().toString();
        System.out.println(choix);
//stateACS is a toggle button
        if (choix.equals("Prix croissant")) {
            Comparator<Toy> comparator = Comparator.comparing(Toy::getPrice);
            changeTableView2(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);

            //Sort in asc order
            oblist.sort(comparator);
        } else if (choix.equals("Prix décroissant")) {
            Comparator<Toy> comparator = Comparator.comparing(Toy::getPrice).reversed();
            changeTableView2(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);

            oblist.sort(comparator);


        }

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

            toyphotoController.setToy(toy);
            Parent p = Loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(p));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    @FXML
    private void show_addForm(ActionEvent event) {
        tab.getSelectionModel().select(1);
    }



    @FXML
    private void show_updateForm(ActionEvent event) {

        update_name.setText(tableView.getSelectionModel().getSelectedItem().getName());
        update_vendor.setText(tableView.getSelectionModel().getSelectedItem().getVendor_name());
        update_price.setText(Double.toString(tableView.getSelectionModel().getSelectedItem().getPrice()));
        update_min.setText(Integer.toString(tableView.getSelectionModel().getSelectedItem().getMin_age()));
        update_max.setText(Integer.toString(tableView.getSelectionModel().getSelectedItem().getMax_age()));
        update_stock.setText(Double.toString(tableView.getSelectionModel().getSelectedItem().getStock()));
        update_picture.setText(tableView.getSelectionModel().getSelectedItem().getPhoto());


        tab.getSelectionModel().select(2);
        //prenom.setText(utilisateur.getSelectionModel().getSelectedItem().getPrenom());

        // valider.setVisible(false);
//        btn_suppProd.setVisible(true);
        //  modifier.setVisible(false);
        //annuler.setVisible(false);
        // ajouter.setVisible(true);
    }
    @FXML
    private void save_Update(ActionEvent event) {
        if (!tableView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modification d'un produit");
            alert.setHeaderText("Etes-vous sur de vouloir modifier "
                    + tableView.getSelectionModel().getSelectedItem().getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {

                ToyRepository toyRepository = new ToyRepository();
                Toy toy = new Toy(tableView.getSelectionModel().getSelectedItem().getId(),
                        update_name.getText(),
                        Integer.parseInt(update_min.getText()),
                        toyphoto.getText(),

                        Double.parseDouble(update_price.getText()),
                        update_vendor.getText(),

                        Integer.parseInt(update_min.getText()),
                        Integer.parseInt(update_max.getText()),
                        Double.parseDouble(update_stock.getText()),tableView.getSelectionModel().getSelectedItem().getVendorID()

                        );

                toyRepository.update(toy);
                System.out.println(toy);
                System.out.println("___________a_________");

                refresh();
            }

            update_name.setText("");
            update_vendor.setText("");
            update_price.setText("");
            update_min.setText("");
            update_max.setText("");

            //afficher.setVisible(false);
            tab.getSelectionModel().select(0);

        }
    }

    @FXML
    private void photoChooser(ActionEvent event)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File choix = fileChooser.showOpenDialog(null);
        if (choix != null) {

            absolutePathPhoto = choix.getAbsolutePath();
            toyphoto.setText(choix.getName());
        } else {
            System.out.println("Image introuvable");
        }

    }

    @FXML
    private void SaveAddToy(ActionEvent event) {


        deplacerVers(toyphoto, absolutePathPhoto,"C:\\Users\\hp\\Sesame\\3Java_Sesame\\Projet_POO_mvn_fin\\src\\main\\resources\\toysphoto");
        deplacerVers(toyphoto, absolutePathPhoto,"C:\\wamp64\\www\\toys\\photos");
        Toy toy = new Toy() ;

               toy.setName(insert_name.getText());
        toy.setType_id(Integer.parseInt(insert_price.getText())) ;//type
            toy.setPhoto(toyphoto.getText()) ;
               toy.setPrice( Double.parseDouble(insert_price.getText())) ;

                toy.setVendor_name(insert_vendor.getText());
                toy.setMin_age(Integer.parseInt(insert_price.getText())) ;//minage
        toy.setMax_age(Integer.parseInt(insert_price.getText())); //maxage

        toy.setStock(Double.parseDouble(insert_stock.getText()));


        if (insert_name.getText().equals("") || insert_price.getText().equals("") ||
                insert_price.getText()==null || toyphoto.getText()==null || insert_vendor.getText()==null || insert_stock.getText().equals("")  ) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alerte");
            alert.setHeaderText("Vous devez remplir TOUT LES CHAMPS SVP");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                tab.getSelectionModel().select(1);
                Afficher();
                insert_name.setText("");
                insert_price.setText("");
                insert_vendor.setText("");
                toyphoto.setText("");
                insert_stock.setText("");

            }

        }

        else {  toyRepository.add(toy);
            Afficher();
            insert_name.setText("");
            insert_price.setText("");
            insert_vendor.setText("");
            photoProduit.setText("");
            insert_stock.setText("");
            tab.getSelectionModel().select(0);

            //maillist
            List<String> emails = toyRepository.getAllMails();
            String subject = "Nouvelle collection MBA && CBYTE !";
            // message
            String message = "La nouvelle collection est arrivée ! Découvrez-vite les nouveautés ! .\n";
            // send
            mailingList(subject, emails, message);
        }

    }


    @FXML
    private void deleteToy(ActionEvent event) {
        if (!tableView.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suppression d'un produit");
            alert.setHeaderText("Etes-vous sur de vouloir supprimer le jouet "
                    + tableView.getSelectionModel().getSelectedItem().getName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                ToyRepository toyRepository = new ToyRepository();
                System.out.println("aywah"+tableView.getSelectionModel().getSelectedItem().getId());
                toyRepository.delete(tableView.getSelectionModel().getSelectedItem().getId());
                refresh();

            }

        }
    }


    void Afficher() {

        oblist = FXCollections.observableArrayList(toyRepository.getAll());

        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        nom.setCellValueFactory(new PropertyValueFactory<>("name"));
        type.setCellValueFactory(new PropertyValueFactory<>("type_id"));
        prix.setCellValueFactory(new PropertyValueFactory<>("price"));
        fseur.setCellValueFactory(new PropertyValueFactory<>("vendor_name"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
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


    public void mailingList(final String subject, final List<String> emailToAddresses,
                                   final String emailBodyText) {

        // from email address
        final String username = "3laiag.fsegn@gmail.com";
        // make sure you put your correct password
        final String password = "3laiag2020";
        // smtp email server
        final String smtpHost = "smtp.googlemail.com";
        // We will put some properties for smtp configurations
        Properties props = new Properties();
        // do not change - start
        props.put("mail.smtp.user", "username");
        props.put("mail.smtp.host", smtpHost);
        // props.put("mail.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.debug", "true");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        // do not change - end

        // we authentcate using your email and password and on successful
        // we create the session
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        String emails = null;

        try {
            // we create new message
            Message message = new MimeMessage(session);

            // set the from 'email address'
            message.setFrom(new InternetAddress(username));

            // set email subject
            message.setSubject(subject);

            // set email message
            // this will send html mail to the intended recipients
            // if you do not want to send html mail then you do not need to wrap the message
            // inside html tags
            String content = "<html>\n<body>\n";
            content += emailBodyText + "\n";
            content += "\n";
            content += "</body>\n</html>";
            message.setContent(content, "text/html");

            // form all emails in a comma separated string
            StringBuilder sb = new StringBuilder();

            int i = 0;
            for (String email : emailToAddresses) {
                sb.append(email);
                i++;
                if (emailToAddresses.size() > i) {
                    sb.append(", ");
                }
            }

            emails = sb.toString();

            // set 'to email address'
            // you can set also CC or TO for recipient type
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(sb.toString()));

            System.out.println("Sending Email to " + emails + " from " + username + " with Subject - " + subject);

            // send the email
            Transport.send(message);

            System.out.println("Email successfully sent to " + emails);
        } catch (MessagingException e) {
            System.out.println("Email sending failed to " + emails);
            System.out.println(e);
        }
    }



    @FXML
    private void ToysTypeAdd(ActionEvent event) throws SQLException {

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Ajouter un nouveau type");
        dialog.setContentText("Veuillez introduire le nouveau type désiré");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            if(result.get()!=""){
            //System.out.println("Type: " + result.get());
                ToyType toyType = new ToyType();
                toyType.setName(result.get());
                toyRepository.addType(toyType);
                insert_type1.setItems(FXCollections.observableArrayList(toyRepository.getAllTypes()));
           }
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



    /**** Sidebar Menu*/

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




