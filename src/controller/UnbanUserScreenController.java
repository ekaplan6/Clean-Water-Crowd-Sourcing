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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


/**
 * controls the unban user screen
 */
public class UnbanUserScreenController {

    private MainFXApplication mainFXApplication;

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
     */
    public void setMainApp(MainFXApplication main) {
        mainFXApplication = main;
        initializeTable();
    }

    @FXML
    private void initializeTable() {
        mainList = new UserManager().getBannedUsers();
        if ((mainList == null) || mainList.isEmpty()) {
            mainFXApplication.showADMINMainApplicationScreen();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainFXApplication.getStage());
            alert.setTitle("Oops!");
            alert.setHeaderText("There are no users to unban!");
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
    private void unbanPressed() {
            ObservableList<User> selectedDelete =  mainTable.getSelectionModel().getSelectedItems();
            Connection conn;
            Statement stmt;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease",
                        "bitsplease", "bitsplease");
                stmt = conn.createStatement();
                for (User current : selectedDelete) {
                    String sql = "UPDATE USER SET ban = '0' WHERE username = '" + current.get_username() + "';";
                    stmt.executeUpdate(sql);
                    mainList.remove(current);
                }
                if (mainList.isEmpty()) {
                    mainFXApplication.showADMINMainApplicationScreen();
                }
            } catch (Exception ignored) {

            }
    }
}
