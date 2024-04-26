package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerMenu implements Initializable {
    @FXML
    private StackPane contentArea;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       /* try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/FXML/menu.fxml"));
            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll();
        }catch (IOException e){
            Logger.getLogger(ControllerMenu.class.getName()).log(Level.SEVERE,null,e);
        }*/
    }

    public void operation(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/user.fxml"));
        contentArea.getChildren().clear(); // Clear existing content
        contentArea.getChildren().add(fxml); // Add new content
    }


}

