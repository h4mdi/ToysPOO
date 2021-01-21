package Main.Controllers;

import Main.DAO.Interfaces.IToyRepository;
import Main.DAO.OrderRepository;
import Main.DAO.ToyRepository;
import Main.Model.Order;
import Main.Model.OrderDetails;
import Main.Model.Toy;
import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderDetailsController implements Initializable {
    ObservableList<OrderDetails> oblist ;
    OrderRepository orderRepository = new OrderRepository();
Double s =0.0 ;

    @FXML
    private TableView<OrderDetails> tableView;

    @FXML
    private TableColumn<OrderDetails, String> toy;
    @FXML
    private TableColumn<OrderDetails, Integer> qt;

    @FXML
    private TableColumn<OrderDetails, Double> pu;


    @FXML
    private Button btn_imprimer ;

    private List<OrderDetails> orders;

    private List<OrderDetails> o2 =new ArrayList<>();


    private static OrderDetailsController instance;

    public OrderDetailsController() {
        instance = this;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public Button getBtn_imprimer() {
        return btn_imprimer;
    }

    public void setBtn_imprimer(Button btn_imprimer) {
        this.btn_imprimer = btn_imprimer;
    }

    public List<OrderDetails> getOrders() {
        return this.orders;
    }

    public void setOrders(List<OrderDetails> orders) {
        this.orders = orders;
//        System.out.println(orders.get(0).getOrderId());
        tableView.getItems().addAll(orders);
        toy.setCellValueFactory(new PropertyValueFactory<>("name"));

        qt.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        pu.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

    }

    public static OrderDetailsController getInstance() {
        return instance;
    }

    public static void setInstance(OrderDetailsController instance) {
        OrderDetailsController.instance = instance;
    }

    @FXML
    public void print_bill() throws IOException, DocumentException {
        Document document = new Document(PageSize.A4);
        PdfPTable table = new PdfPTable(new float[] { 2, 1, 2 });
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell("Jouet");
        table.addCell("Quantité");
        table.addCell("Prix Unitaire");
        table.setHeaderRows(1);
        table.setSpacingBefore(90);
        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j=0;j<cells.length;j++){
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }
        for (int i=0;i<orders.size();i++){
            table.addCell(orders.get(i).getName());
            table.addCell(String.valueOf(orders.get(i).getQuantity()));
            table.addCell(String.valueOf(orders.get(i).getUnitPrice()));
            s=s+orders.get(i).getUnitPrice();
        }
        PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/Factures/Facture"+orders.get(0).getOrderNumber()+".pdf"));
        document.open();
        Paragraph p1,p2,header,num;
        header=new Paragraph("MBA&&CBYTE");
        header.setAlignment(Element.ALIGN_CENTER);
        num = new Paragraph("Facture commande N° "+orders.get(0).getOrderNumber());

        num.setAlignment(Element.ALIGN_LEFT);
        document.add(num);
        document.add(header);
        LocalDate dt=orders.get(0).getDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-YYYY");
//        System.out.println(formatter.format(dt));
        p1 = new Paragraph("Date :"+formatter.format(dt));
        p1.setAlignment(Element.ALIGN_LEFT);
        document.add(p1);
        document.add(new Paragraph(""));
        document.add(table);
        String filename = "src/main/resources/images/cache.png";
        Image image = Image.getInstance(filename);
        image.setAbsolutePosition(15f, 90f);
        document.add(image);
        p2 = new Paragraph("Total TTC :"+s+" TND");
        p2.setAlignment(Element.ALIGN_RIGHT);
        document.add(p2);

        document.close();
        //ouvrir le pdf
        Desktop.getDesktop().open(new File("src/main/resources/Factures/Facture"+orders.get(0).getOrderNumber()+".pdf"));

        System.out.println("Done");
    }









}
