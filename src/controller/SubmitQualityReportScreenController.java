package controller;

import javafx.scene.control.Alert;
import fxapp.MainFXApplication;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.OverallConditionEnum;
import model.ReportManager;
import model.User;


/**
 * Controls the submit quality report screen
 */
public class SubmitQualityReportScreenController {

    private MainFXApplication mainFXApplication;

    private User currentUser;

    @FXML
    private ComboBox overallConditionField;

    @FXML
    private TextArea locationField;

    @FXML
    private TextField virusPPM;

    @FXML
    private TextField contaminantPPM;


    /**
     * Gets an instance of the current main application running
     * @param main the instance of the current application running
     *             A reference of this is stored in a local variable
     * @param currentUser The current user using the app
     */
    public void setMainApp(MainFXApplication main, User currentUser) {
        mainFXApplication = main;
        this.currentUser = currentUser;
    }

    @FXML
    @SuppressWarnings("unchecked")
    private void initialize() {
        overallConditionField.getItems().clear();
        overallConditionField.setItems(FXCollections.observableArrayList(OverallConditionEnum.values()));
        overallConditionField.setValue(FXCollections.observableArrayList(OverallConditionEnum.values()).get(0));

    }

    @FXML
    private void backPressed() {
        mainFXApplication.showMainApplicationScreen();
    }

    @FXML
    private void submitPressed() {
        if ((locationField == null) || (locationField.getText() == null) || locationField.getText().trim().isEmpty()
                || (virusPPM == null) || (virusPPM.getText() == null) || virusPPM.getText().trim().isEmpty()
                || (contaminantPPM == null) || (contaminantPPM.getText() == null) ||
                contaminantPPM.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainFXApplication.getStage());
            alert.setTitle("Error!");
            alert.setHeaderText("You left some fields blank!");
            alert.setContentText("You have to provide a location!");
            alert.showAndWait();
        } else {
            ReportManager reportManager = new ReportManager();
            reportManager.submitQualityReport(currentUser.get_username(), locationField.getText().trim(),
                    (OverallConditionEnum)overallConditionField.getSelectionModel().getSelectedItem(),
                    virusPPM.getText(), contaminantPPM.getText());
            mainFXApplication.showMainApplicationScreen();

        }
    }
}
