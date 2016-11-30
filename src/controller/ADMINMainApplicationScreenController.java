package controller;

import fxapp.MainFXApplication;
import javafx.fxml.FXML;


/**
 * Control the admin application view
 */
public class ADMINMainApplicationScreenController {

    private MainFXApplication mainFXApplication;

    /**
     * Gets an instance of the current main application running
     * @param main the instance of the current application running
     *             A reference of this is stored in a local variable
     */
    public void setMainApp(MainFXApplication main) {
        mainFXApplication = main;
    }

    @FXML
    private void logoutButtonPressed() {
        mainFXApplication.showWelcomeScreen();
    }

    @FXML
    private void editUserProfilePressed() {
        mainFXApplication.showEditUserProfileScreen();
    }

    @FXML
    private void banPressed() {
        mainFXApplication.showBanUserScreen();
    }

    @FXML
    private void unbanPressed() {
        mainFXApplication.showUnbanUserScreen();
    }

    @FXML
    private void blockPressed() {
        mainFXApplication.showBlockUserScreen();
    }

    @FXML
    private void unblockPressed() {
        mainFXApplication.showUnblockUserScreen();
    }

    @FXML
    private void deleteAccountPressed() {
        mainFXApplication.showDeleteAccountScreen();
    }
}
