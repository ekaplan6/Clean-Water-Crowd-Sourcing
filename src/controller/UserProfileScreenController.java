package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.User;
import model.UserTypeEnum;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


/**
 * controls the user profile screen
 */
public class UserProfileScreenController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField fullnameField;

    @FXML
    private TextField emailaddressField;

    @FXML
    private TextArea homeaddressField;

    @FXML
    private TextField companyField;

    @FXML
    private TextField jobtitleField;

    @FXML
    private TextField departmentField;

    private MainFXApplication mainFXApplication;

    private User currentUser;
    /**
     * Gets an instance of the current main application running
     * @param main the instance of the current application running
     *             A reference of this is stored in a local variable
     * @param currentUser the current user using the app
     */
    public void setMainApp(MainFXApplication main, User currentUser) {
        mainFXApplication = main;
        this.currentUser = currentUser;
        initializeFields();
    }

    @FXML
    private void initializeFields() {
        usernameField.setText(currentUser.get_username());
        fullnameField.setText(currentUser.get_fullname());
        emailaddressField.setText(currentUser.get_emailaddress());
        homeaddressField.setText(currentUser.get_homeaddress());
        companyField.setText(currentUser.get_company());
        jobtitleField.setText(currentUser.get_jobtitle());
        departmentField.setText(currentUser.get_department());
    }

    @FXML
    private void backPressed() {
        if (currentUser.get_type().equals(UserTypeEnum.ADMIN.toString())) {
            mainFXApplication.showADMINMainApplicationScreen();
        } else {
            mainFXApplication.showMainApplicationScreen();
        }
    }

    @FXML
    private void savePressed() {
        try {
            Connection conn;
            Statement stmt;
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            String sql = "UPDATE USER SET fullname = '" + fullnameField.getText() + "', emailaddress = '" +
                    emailaddressField.getText() + "', homeaddress = '" + homeaddressField.getText() +
                    "', company = '" + companyField.getText() + "', jobtitle = '" + jobtitleField.getText() +
                    "', department = '" + departmentField.getText() + "' WHERE username = '" +
                    currentUser.get_username() + "'";
            stmt.executeUpdate(sql);
            updateUser();
            mainFXApplication.showMainApplicationScreen();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainFXApplication.getStage());
            alert.setTitle("Database Error!");
            alert.setHeaderText("The application couldn't connect to the database!");
            alert.setContentText("Try again or call an admin staff if problem persists!");
            alert.showAndWait();
        }
    }

    private void updateUser() {
        currentUser.set_fullname(fullnameField.getText());
        currentUser.set_emailaddress(emailaddressField.getText());
        currentUser.set_homeaddress(homeaddressField.getText());
        currentUser.set_company(companyField.getText());
        currentUser.set_jobtitle(jobtitleField.getText());
        currentUser.set_department(departmentField.getText());
    }
}
