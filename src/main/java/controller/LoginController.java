package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.UserService;
import controller.UserController;
import utils.MyDatabase;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private Button btnLogin;

    @FXML
    private TextField log_Email;

    @FXML
    private PasswordField log_Password1;

    @FXML
    void LoginAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        String email = log_Email.getText();
        String password = log_Password1.getText();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Établir une connexion à la base de données
            connection = MyDatabase.getInstance().getConnection();

            // Écrire une requête SQL pour vérifier l'utilisateur
            String query = "SELECT role_user FROM user WHERE email = ? AND password = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // L'utilisateur existe, récupérer son rôle
                String role = resultSet.getString("role_user");

                // En fonction du rôle de l'utilisateur, rediriger vers la page appropriée
                switch (role) {
                    case "administrateur":
                        // Redirection vers la page d'administration
                        FXMLLoader loaderAdmin = new FXMLLoader(getClass().getResource("/menu.fxml"));
                        Parent rootAdmin = loaderAdmin.load();
                        log_Email.getScene().setRoot(rootAdmin);
                        break;
                    case "fournisseur":
                        // Redirection vers la page du fournisseur
                        FXMLLoader loaderFournisseur = new FXMLLoader(getClass().getResource("/profileFournisseur.fxml"));
                        Parent rootFournisseur = loaderFournisseur.load();
                        log_Email.getScene().setRoot(rootFournisseur);
                        break;
                    case "livraison":
                        // Redirection vers la page de livraison
                        FXMLLoader loaderLivraison = new FXMLLoader(getClass().getResource("/profileLivraison.fxml"));
                        Parent rootLivraison = loaderLivraison.load();
                        log_Email.getScene().setRoot(rootLivraison);
                        break;
                    case "produit":
                        // Redirection vers la page de gestion des produits
                        FXMLLoader loaderProduit = new FXMLLoader(getClass().getResource("/profileProduit.fxml"));
                        Parent rootProduit = loaderProduit.load();
                        log_Email.getScene().setRoot(rootProduit);
                        break;
                    case "facture":
                        // Redirection vers la page de gestion des factures
                        FXMLLoader loaderFacture = new FXMLLoader(getClass().getResource("/profileFacture.fxml"));
                        Parent rootFacture = loaderFacture.load();
                        log_Email.getScene().setRoot(rootFacture);
                        break;
                    case "client":
                        // Redirection vers la page du client
                        FXMLLoader loaderClient = new FXMLLoader(getClass().getResource("/profileClient.fxml"));
                        Parent rootClient = loaderClient.load();
                        log_Email.getScene().setRoot(rootClient);
                        break;
                    default:
                        alert.setContentText("Erreur: Rôle d'utilisateur non reconnu.");
                        alert.showAndWait();
                        break;
                }
            } else {
                // L'utilisateur n'existe pas ou les informations sont incorrectes
                alert.setContentText("Erreur d'authentification !");
                alert.showAndWait();
            }
        }  catch (SQLException | IOException e) {
            e.printStackTrace();
            // Gérer les erreurs de base de données
        }
    }

}
