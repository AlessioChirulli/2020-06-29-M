/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Model;
import it.polito.tdp.imdb.model.RegistiAdiacenti;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnAdiacenti"
    private Button btnAdiacenti; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaAffini"
    private Button btnCercaAffini; // Value injected by FXMLLoader

    @FXML // fx:id="boxAnno"
    private ComboBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxRegista"
    private ComboBox<Director> boxRegista; // Value injected by FXMLLoader

    @FXML // fx:id="txtAttoriCondivisi"
    private TextField txtAttoriCondivisi; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	boxRegista.getItems().clear();
    	if(boxAnno.getValue()!=null) {
    		model.creaGrafo(boxAnno.getValue());
    		txtResult.setText("GRAFO CREATO!\n#VERTICI: "+model.getVertexSize()+"\n#ARCHI: "+model.getEdgeSize()+"\n");
    		boxRegista.getItems().addAll(model.getDirettori());
    	}else {
    		txtResult.setText("Scegli prima un anno!");
    	}
    }

    @FXML
    void doRegistiAdiacenti(ActionEvent event) {
    	txtResult.clear();
    	if(boxRegista.getValue()!=null) {
    		List<RegistiAdiacenti> result=model.getAdiacenze(boxRegista.getValue());
    		txtResult.appendText("Registi adiacenti a:"+boxRegista.getValue()+"\n");
    		for(RegistiAdiacenti d: result) {
    			txtResult.appendText(d+"\n");
    		}
    	}else {
    		txtResult.setText("Scegli prima un direttore!");
    	}
    }

    @FXML
    void doRicorsione(ActionEvent event) {
    	txtResult.clear();
    	try {
    	int c=Integer.parseInt(txtAttoriCondivisi.getText());
    	if(boxRegista.getValue()!=null) {
    	List<Director> result=model.getCammino(c, boxRegista.getValue());
    	for(Director d:result) {
    		txtResult.appendText(d+"\n");
    	}
    	}else {
    		txtResult.setText("Scegli prima un direttore!");
    		return ;
    	}
    	}catch(NumberFormatException nbe) {
    		txtResult.setText("Inserisci un numero valido!");
    	}
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAdiacenti != null : "fx:id=\"btnAdiacenti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaAffini != null : "fx:id=\"btnCercaAffini\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxRegista != null : "fx:id=\"boxRegista\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAttoriCondivisi != null : "fx:id=\"txtAttoriCondivisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
   public void setModel(Model model) {
    	
    	this.model = model;
    	for(int i=2004;i<=2006;i++)
    	boxAnno.getItems().add(i);
    	
    }
    
}
