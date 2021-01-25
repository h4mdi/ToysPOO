/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Controllers;

import Main.Model.Toy;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ToyphotoController implements Initializable {

   
    @FXML
    private ImageView idImg;


    private Toy toy;
    private static ToyphotoController instance;
    
    public ToyphotoController() {
        instance = this;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
     public Toy getToy() { return this.toy; }
    
    public void setToy(Toy toy) {
        this.toy = toy;
        Image im = new Image(toy.getPhoto());
        idImg.setImage(im);

    }


  
   
    
    static public ToyphotoController getInstance() {
        return instance;
    }
  

}
