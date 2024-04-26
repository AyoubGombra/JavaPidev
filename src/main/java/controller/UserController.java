package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.TouchEvent;
import models.User;
import services.UserService;

import java.net.PasswordAuthentication;
import java.sql.SQLException;

import java.util.Arrays;
import java.util.List;




public class UserController {

    @FXML
    private TableColumn<?, ?> ColDepartement;

    @FXML
    private TableColumn<?, ?> ColEmail;

    @FXML
    private TableColumn<?, ?> ColID;

    @FXML
    private TableColumn<?, ?> ColNom;

    @FXML
    private TableColumn<?, ?> ColPassword;

    @FXML
    private TableColumn<?, ?> ColPrenom;

    @FXML
    private TableColumn<?, ?> ColRole;

    @FXML
    private TableColumn<?, ?> ColSalaire;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    @FXML
    private TextField tDepartement;

    @FXML
    private TextField tId;

    @FXML
    private TextField tEmail;

    @FXML
    private TextField tName;

    @FXML
    private TextField tPassword;

    @FXML
    private TextField tPrenom;

    @FXML
    private ChoiceBox<String> choiceRole;

    @FXML
    private ChoiceBox<String> choiceDep;

    @FXML
    private TextField tSalaire;

    @FXML
    private TableView<User> tableView;

    @FXML
    void AjouterUser(ActionEvent event) throws SQLException {
        if (!verifierSaisie()) {
            return;
        }
        User u = new User(Integer.parseInt(tSalaire.getText()),tEmail.getText(),choiceRole.getValue(), tPassword.getText(), tName.getText(), tPrenom.getText() , choiceDep.getValue());
        UserService us = new UserService();
        try {
            us.create(u);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("User insérée avec succés!");
            alert.show();

            tId.clear();
            tName.clear();
            tPrenom.clear();
            choiceRole.getSelectionModel().clearSelection();
            choiceDep.getSelectionModel().clearSelection();
            tSalaire.clear();
            tEmail.clear();
            tPassword.clear();

            initialize();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText(e.getMessage());
            alert.show();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void ModifierUsee(ActionEvent event) {
        User u = new User(Integer.parseInt(tId.getText()),Integer.parseInt(tSalaire.getText()),tEmail.getText(),choiceRole.getValue(), tPassword.getText(), tName.getText(), tPrenom.getText() , choiceDep.getValue());
        UserService us = new UserService();
        try {
            us.update(u);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("User modifier avec succés!");
            alert.show();

            tId.clear();
            tName.clear();
            tPrenom.clear();
            choiceRole.getSelectionModel().clearSelection();
            choiceDep.getSelectionModel().clearSelection();
            tSalaire.clear();
            tEmail.clear();
            tPassword.clear();

            initialize();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText(e.getMessage());
            alert.show();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void SupprimerUser(ActionEvent event) {
        UserService us = new UserService();
        int id1 = Integer.parseInt(tId.getText());
        try {
            us.delete(id1);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("User supprimer avec succés!");
            alert.show();

            tId.clear();
            tName.clear();
            tPrenom.clear();
            choiceRole.getSelectionModel().clearSelection();
            choiceDep.getSelectionModel().clearSelection();
            tSalaire.clear();
            tEmail.clear();
            tPassword.clear();

            initialize();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText(e.getMessage());
            alert.show();
            throw new RuntimeException(e);
        }
    }

    @FXML
    void initialize() throws SQLException {
        UserService us = new UserService();
        try {
            ObservableList<User> observableliste = FXCollections.observableList(us.read());
            tableView.setItems(observableliste);
            // Remplir les colonnes avec les propriétés des objets User
            ColID.setCellValueFactory(new PropertyValueFactory<>("id"));
            ColNom.setCellValueFactory(new PropertyValueFactory<>("name"));
            ColPrenom.setCellValueFactory(new PropertyValueFactory<>("prename"));
            ColRole.setCellValueFactory(new PropertyValueFactory<>("role_Useer"));
            ColDepartement.setCellValueFactory(new PropertyValueFactory<>("departement"));
            ColSalaire.setCellValueFactory(new PropertyValueFactory<>("salaire"));
            ColEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            ColPassword.setCellValueFactory(new PropertyValueFactory<>("password"));

            // Masquer le TextField tId
            tId.setVisible(false);

            ObservableList<String> roles = FXCollections.observableArrayList("administrateur", "fournisseur", "livraison", "produit", "facture", "client");
            choiceRole.setItems(roles);

            ObservableList<String> Dep = FXCollections.observableArrayList("administrateur", "fournisseur", "livraison", "produit", "facture", "client");
            choiceDep.setItems(Dep);

            initialiserMessagesTransparent();

            // Configurer la gestion des événements de clic sur la TableView
            tableview();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void tableview() {
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                User selectedUser = tableView.getSelectionModel().getSelectedItem(); 
                if (selectedUser != null) {
                    tId.setText(String.valueOf(selectedUser.getId()));
                    tName.setText(selectedUser.getName());
                    tPrenom.setText(selectedUser.getPrename());;
                    choiceRole.setValue(selectedUser.getRole_Useer());
                    choiceDep.setValue(selectedUser.getDepartement());
                    tSalaire.setText(String.valueOf(selectedUser.getSalaire()));
                    tEmail.setText(selectedUser.getEmail());
                    tPassword.setText(selectedUser.getPassword());
                }
            }
        });
    }

    /*private void sendEmail(String to, String subject, String body) {
        final String username = "votre@email.com";
        final String password = "votreMotDePasse";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("E-mail envoyé avec succès !");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }*/

    private boolean verifierSaisie() {
        // Récupération des valeurs des TextField
        String salaireText = tSalaire.getText();
        String emailText = tEmail.getText();
        String passwordText = tPassword.getText();
        String nomText = tName.getText();
        String prenomText = tPrenom.getText();

        // Vérifier si tous les champs sont remplis
        if (salaireText.isEmpty() || emailText.isEmpty() || passwordText.isEmpty() || nomText.isEmpty() || prenomText.isEmpty()) {
            afficherAlerteErreur("Erreur", "Veuillez remplir tous les champs.");
            return false;
        }

        // Vérifier le format du salaire
        if (!salaireText.matches("\\d+")) {
            afficherAlerteErreur("Erreur", "Le salaire doit contenir uniquement des chiffres.");
            return false;
        }

        // Vérifier le format de l'email
        if (!emailText.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            afficherAlerteErreur("Erreur", "Veuillez saisir une adresse e-mail valide.");
            return false;
        }

        // Vérifier le format du password
        if (!passwordText.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
            afficherAlerteErreur("Erreur", "Le mot de passe doit contenir au moins une majuscule, une minuscule et un chiffre, et faire au moins 8 caractères.");
            return false;
        }

        // Vérifier que le nom et le prénom ne contiennent que des lettres
        if (!nomText.matches("[a-zA-Z]+") || !prenomText.matches("[a-zA-Z]+")) {
            afficherAlerteErreur("Erreur", "Le nom et le prénom ne peuvent contenir que des lettres.");
            return false;
        }

        if (choiceRole.getSelectionModel().isEmpty()) {
            afficherAlerteErreur("Erreur", "Veuillez sélectionner un rôle.");
            return false;
        }

        if (choiceDep.getSelectionModel().isEmpty()) {
            afficherAlerteErreur("Erreur", "Veuillez sélectionner un departement.");
            return false;
        }

        return true;
    }

    private void afficherAlerteErreur(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.show();
    }

    // Méthode pour initialiser les messages transparents dans les TextField
    private void initialiserMessagesTransparent() {
        initialiserMessageTransparent(tName, "écrit le nom");
        initialiserMessageTransparent(tPrenom, "écrit le prenom");
        initialiserMessageTransparent(choiceRole, "sélectionnez le rôle");
        initialiserMessageTransparent(choiceDep, "écrit département");
        initialiserMessageTransparent(tSalaire, "écrit le salaire");
        initialiserMessageTransparent(tEmail, "écrit l'émail");
        initialiserMessageTransparent(tPassword, "écrit le mot de passe");
    }

    // Méthode pour initialiser un message transparent dans un TextField spécifique
    /*private void initialiserMessageTransparent(TextField textField, String message) {
        textField.setText(message);
        textField.setStyle("-fx-text-fill: gray;"); // Définir la couleur du texte en gris

        // Ajouter un écouteur de changement de focus
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && textField.getText().isEmpty()) { // Si le focus est perdu et le texte est vide
                textField.setText(message); // Restaurer le message transparent
                textField.setStyle("-fx-text-fill: gray;"); // Définir la couleur du texte en gris
            } else if (newValue && textField.getText().equals(message)) { // Si le focus est sur le TextField et le texte est le message transparent
                textField.setText(""); // Effacer le texte pour permettre à l'utilisateur de saisir du texte
                textField.setStyle("-fx-text-fill: black;"); // Définir la couleur du texte en noir
            }
        });
    }*/

    // Méthode pour initialiser un message transparent dans un champ spécifique
    private void initialiserMessageTransparent(Control control, String message) {
        if (control instanceof TextInputControl) {
            TextInputControl textInputControl = (TextInputControl) control;
            textInputControl.setText(message);
            textInputControl.setStyle("-fx-text-fill: gray;"); // Définir la couleur du texte en gris

            // Ajouter un écouteur de changement de focus
            textInputControl.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue && textInputControl.getText().isEmpty()) { // Si le focus est perdu et le texte est vide
                    textInputControl.setText(message); // Restaurer le message transparent
                    textInputControl.setStyle("-fx-text-fill: gray;"); // Définir la couleur du texte en gris
                } else if (newValue && textInputControl.getText().equals(message)) { // Si le focus est sur le champ et le texte est le message transparent
                    textInputControl.setText(""); // Effacer le texte pour permettre à l'utilisateur de saisir du texte
                    textInputControl.setStyle("-fx-text-fill: black;"); // Définir la couleur du texte en noir
                }
            });
        } else if (control instanceof ChoiceBox) {
            ChoiceBox choiceBox = (ChoiceBox) control;
            choiceBox.getItems().add(message);
            choiceBox.setValue(message);
            choiceBox.setStyle("-fx-text-fill: gray;"); // Définir la couleur du texte en gris

            // Ajouter un écouteur de changement de valeur
            choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.equals(message)) { // Si la valeur sélectionnée est le message transparent
                    choiceBox.setStyle("-fx-text-fill: gray;"); // Définir la couleur du texte en gris
                } else {
                    choiceBox.setStyle("-fx-text-fill: black;"); // Définir la couleur du texte en noir
                }
            });
        }
    }



}
