package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import model.User;
import model.UserTypeEnum;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Controls the Login Screen
 */
public class LoginScreenController {
    private MainFXApplication mainFXApplication;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private User currentUser;

    /**
     * @param main a reference (link) to our main class
     * @param currentUser the current user using the app
     */
    public void setMainApp(MainFXApplication main, User currentUser) {
        mainFXApplication = main;
        this.currentUser = currentUser;
    }

    @FXML
    private void backPressed() {
        mainFXApplication.showWelcomeScreen();
    }

    @FXML
    private void loginPressed() {

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            String sql = "SELECT username, password, fullname, " +
                    "ban, attempt, type, emailaddress, homeaddress, company," +
                    " jobtitle, department FROM USER WHERE username = '" + usernameField.getText().trim() + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(mainFXApplication.getStage());
                alert.setTitle("Invalid Fields");
                alert.setHeaderText("Please correct invalid fields");
                alert.setContentText("Incorrect username!");
                alert.showAndWait();
            } else {
                addUser(rs);
            }
        } catch (Exception e) {
            mainFXApplication.showDatabaseError();
            e.printStackTrace();
        } finally {
            close (stmt, conn);

        }
    }
    private void addUser(ResultSet rs) throws Exception {
        if (checkUserCredentials(rs)) {
            currentUser.set_username(rs.getString("username"));
            currentUser.set_fullname(rs.getString("fullname"));
            currentUser.set_ban(rs.getInt("ban"));
            currentUser.set_type(rs.getString("type"));
            currentUser.set_emailaddress(rs.getString("emailaddress"));
            currentUser.set_homeaddress(rs.getString("homeaddress"));
            currentUser.set_company(rs.getString("company"));
            currentUser.set_jobtitle(rs.getString("jobtitle"));
            currentUser.set_department(rs.getString("department"));
            if (currentUser.get_type().equals(UserTypeEnum.ADMIN.toString())) {
                mainFXApplication.showADMINMainApplicationScreen();
            } else {
                mainFXApplication.showMainApplicationScreen();
            }

        }
    }
    private void close(Statement stmt, Connection conn) {
        try{
            if(stmt!=null) {
                stmt.close();
            }
        } catch(SQLException ignored) {
        }
        try{
            if(conn!=null) {
                conn.close();
            }
        } catch(SQLException ignored){
        }
    }


    private boolean checkUserCredentials(ResultSet rs) {
        try {
            if ((rs.getInt("attempt") > 2) && !rs.getString("type").equals(UserTypeEnum.ADMIN.toString())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(mainFXApplication.getStage());
                alert.setTitle("Account Locked!");
                alert.setHeaderText("You have more than 3 incorrect logins OR your account was locked by an admin!");
                alert.setContentText("Contact an Admin to resolve!");
                alert.showAndWait();
                return false;
            } else {
                if ((rs.getString("password").equals(passwordField.getText()))) {
                    Connection conn;
                    Statement stmt;
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease",
                            "bitsplease", "bitsplease");
                    stmt = conn.createStatement();
                    String sql = "UPDATE USER SET attempt = '0' WHERE username = '" + usernameField.getText() + "'";
                    stmt.executeUpdate(sql);
                    return true;
                } else {
                    int newAttempt = rs.getInt("attempt") + 1;
                    Connection conn;
                    Statement stmt;
                    Class.forName("com.mysql.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease",
                            "bitsplease", "bitsplease");
                    stmt = conn.createStatement();
                    String sql = "UPDATE USER SET attempt = '" + newAttempt +
                            "' WHERE username = '" + usernameField.getText() + "'";
                    stmt.executeUpdate(sql);

                    badFields();
                    return false;


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    private void badFields() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(mainFXApplication.getStage());
        alert.setTitle("Invalid Fields");
        alert.setHeaderText("Please correct invalid fields");
        alert.setContentText("Incorrect password!");
        alert.showAndWait();
    }
}
