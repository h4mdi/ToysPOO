package Main.Controllers;

import Main.DAO.Interfaces.IToyRepository;
import Main.DAO.OrderRepository;
import Main.DAO.ToyRepository;
import Main.Model.Order;
import Main.Model.OrderDetails;
import Main.Model.Toy;
import com.itextpdf.text.*;
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
        Afficher();

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
//        Document document=new Document(PageSize.A4);
//
//        document.addAuthor("MBA&&CBYTE");
//        document.addTitle("Facture");
//        PdfWriter.getInstance(document, new FileOutputStream("Facture.pdf"));
//        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Fact.pdf"));
//
//        document.open();
//       " Paragraph p,header;
//        header=new Paragraph("MBA&&CBYTE");
//        header.setAlignment(Element.ALIGN_CENTER);
//        p = new Paragraph("Facture");
//
//        p.setAlignment(Element.ALIGN_LEFT);
//        document.add(p);
//        document.add(header);
//        LocalDate dt=orders.get(0).getDate();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-YYYY");
////        System.out.println(formatter.format(dt));
//        p = new Paragraph(formatter.format(dt));
//        p.setAlignment(Element.ALIGN_RIGHT);
//        document.add(p);"
//        Rectangle small = new Rectangle(290,100);
//        Font smallfont = new Font(Font.FontFamily.HELVETICA, 10);
//        document.open();
//        PdfPTable table = new PdfPTable(2);
//        table.setTotalWidth(new float[]{ 160, 120 });
//        table.setLockedWidth(true);
//        PdfContentByte cb = writer.getDirectContent();
//        // first row
//        PdfPCell cell = new PdfPCell(new Phrase("Some text here"));
//        cell.setFixedHeight(30);
//        cell.setBorder(Rectangle.NO_BORDER);
//        cell.setColspan(2);
//        table.addCell(cell);
//        // second row
//        cell = new PdfPCell(new Phrase("Some more text", smallfont));
//        cell.setFixedHeight(30);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        cell.setBorder(Rectangle.NO_BORDER);
//        table.addCell(cell);
//        Barcode128 code128 = new Barcode128();
//        code128.setCode("14785236987541");
//        code128.setCodeType(Barcode128.CODE128);
//        Image code128Image = code128.createImageWithBarcode(cb, null, null);
//        cell = new PdfPCell(code128Image, true);
//        cell.setBorder(Rectangle.NO_BORDER);
//        cell.setFixedHeight(30);
//        table.addCell(cell);
//        // third row
//        table.addCell(cell);
//        cell = new PdfPCell(new Phrase("and something else here", smallfont));
//        cell.setBorder(Rectangle.NO_BORDER);
//        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        table.addCell(cell);
//        document.add(table);
//        document.close();
//
//
////
////
////        for(int i=0;i<orders.size();i++)
////        {
////            Paragraph paragraph=new Paragraph(orders.get(i).getName());
////            document.add(paragraph);
////        }
////
////        document.close();

        Document document = new Document();
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
        }
        PdfWriter.getInstance(document, new FileOutputStream("Facture"+orders.get(0).getOrderNumber()+".pdf"));
        document.open();
        Paragraph p1,header,num;
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
        image.setAbsolutePosition(0f, 90f);
        document.add(image);
        document.close();
        System.out.println("Done");
    }


    void Afficher() {

        oblist = FXCollections.observableArrayList(o2);
        System.out.println("liste");

//        toy.setCellValueFactory(new PropertyValueFactory<>("quantity"));

//        qt.setCellValueFactory(new PropertyValueFactory<>("quantity"));
//        pu.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        // pa.setCellValueFactory(new PropertyValueFactory<>("max_age"));
//        photo.setCellValueFactory(new PropertyValueFactory<>("photo"));

    }







}
