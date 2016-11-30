package controller;

import fxapp.MainFXApplication;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.UserManager;
import model.UserTypeEnum;

import java.sql.ResultSet;


/**
 * Controls the registraion screen
 */
public class RegistrationScreenController {

    private MainFXApplication mainFXApplication;

    private final UserManager userManager = new UserManager();

    /**
     * Gets an instance of the current main application running
     * @param main the instance of the current application running
     *             A reference of this is stored in a local variable
     */
    public void setMainApp(MainFXApplication main) {
        mainFXApplication = main;
    }

    @FXML
    private TextField usernameField;

    @FXML
    private TextField fullnameField;

    @FXML
    private TextField passwordField;

    @FXML
    private ComboBox<UserTypeEnum> usertypeField;

    @FXML
    private void initialize() {
        usertypeField.getItems().clear();
        usertypeField.setItems(FXCollections.observableArrayList(UserTypeEnum.values()));
        usertypeField.setValue(FXCollections.observableArrayList(UserTypeEnum.values()).get(0));
    }

    @FXML
    private void backPressed() {
        mainFXApplication.showWelcomeScreen();
    }

    @FXML
    private void registerPressed() {
        try{
            javafx.stage.Stage m1 = mainFXApplication.getStage();
            ResultSet rs = userManager.getUsersWithUsername(usernameField.getText().trim());

            if (rs.next()) {
                alertDuplicate(m1);
            } else if ((usernameField.getText() == null) || (passwordField.getText() == null)
                    || (fullnameField.getText() == null)
                    || usernameField.getText().trim().isEmpty() ||
                    passwordField.getText().trim().isEmpty() || fullnameField.getText().trim().isEmpty()) {
                alertBlank(m1);
            } else {
                UserTypeEnum usertype = usertypeField.getSelectionModel().getSelectedItem();
                userManager.createUser(usernameField.getText().trim(), passwordField.getText(),
                        fullnameField.getText().trim(), usertype);
                mainFXApplication.showWelcomeScreen();
            }
        } catch(Exception e){
            mainFXApplication.showDatabaseError();
            e.printStackTrace();
        }
    }

    private void alertDuplicate(javafx.stage.Stage m1) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(m1);
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("There is already a registered user with this username");
        alert.setContentText("Kindly use something else!");
        alert.showAndWait();
    }
    private void alertBlank(javafx.stage.Stage m1) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(m1);
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("You left one or more fields blank!");
        alert.setContentText("You must provide your name, a un" +
                "ique username and password for access to your account!");
        alert.showAndWait();
    }
}
