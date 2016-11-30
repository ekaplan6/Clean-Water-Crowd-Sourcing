package controller;

import fxapp.MainFXApplication;

import javafx.fxml.FXML;


/**
 * controls the welcome screen
 */
public class WelcomeScreenController {

    private MainFXApplication mainFXApplication;

    /**
     * @param main an instance of main application to control main window (add scenes/alerts)
     */
    public void setMainApp(MainFXApplication main) {
        mainFXApplication = main;
    }

    @FXML
    private void loginButtonPressed() {
        mainFXApplication.showLoginScreen();
    }

    @FXML
    private void registerButtonPressed() {
        mainFXApplication.showRegistrationScreen();
    }

}
