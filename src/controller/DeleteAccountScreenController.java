package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import fxapp.MainFXApplication;
import model.User;
import model.UserManager;

/**
 * controls the delete account screen
 */
public class DeleteAccountScreenController {

    private MainFXApplication mainFXApplication;

    private User currentUser;

    private final UserManager userMan = new UserManager();

    @FXML
    TableView<User> mainTable;

    @FXML
    TableColumn<User, String> username;

    @FXML
    TableColumn<User, String> fullname;

    @FXML
    TableColumn<User, String> type;

    @FXML
    TableColumn<User, String> email;

    @FXML
    TableColumn<User, String> address;


    private ObservableList<User> mainList;
    /**
     * Gets an instance of the current main application running
     * @param main the instance of the current application running
     *             A reference of this is stored in a local variable
     * @param currentUser the current user using the app
     */
    public void setMainApp(MainFXApplication main, User currentUser) {
        mainFXApplication = main;
        this.currentUser = currentUser;
        initializeTable();
    }

    @FXML
    private void initializeTable() {
        mainList = userMan.getUsers(currentUser);
        if ((mainList == null) || mainList.isEmpty()) {
            mainFXApplication.showADMINMainApplicationScreen();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainFXApplication.getStage());
            alert.setTitle("Oops!");
            alert.setHeaderText("There are no accounts to delete!");
            alert.showAndWait();
            throw new IllegalArgumentException();
        } else {
            mainTable.setId("mainTable");
            username.setCellValueFactory(new PropertyValueFactory<>("_username"));
            fullname.setCellValueFactory(new PropertyValueFactory<>("_fullname"));
            type.setCellValueFactory(new PropertyValueFactory<>("_type"));
            email.setCellValueFactory(new PropertyValueFactory<>("_emailaddress"));
            address.setCellValueFactory(new PropertyValueFactory<>("_homeaddress"));
            mainTable.setItems(mainList);
        }
    }

    @FXML
    private void backPressed() {
        mainFXApplication.showADMINMainApplicationScreen();
    }

    @FXML
    private void deletePressed() {
        ObservableList<User> selectedDelete =  mainTable.getSelectionModel().getSelectedItems();
        userMan.deleteUsers(selectedDelete, mainList);
        if (mainList.isEmpty()) {
            mainFXApplication.showADMINMainApplicationScreen();
        }
    }
}
