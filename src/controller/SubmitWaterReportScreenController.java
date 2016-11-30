package controller;

import javafx.scene.control.Alert;
import fxapp.MainFXApplication;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import model.ReportManager;
import model.User;
import model.WaterConditionEnum;
import model.WaterTypeEnum;

/**
 * The conroller for the submit water report screen
 */
public class SubmitWaterReportScreenController {
    private MainFXApplication mainFXApplication;

    private User currentUser;

    private ReportManager reportManager;

    @FXML
    private ComboBox waterConditionField;

    @FXML
    private ComboBox waterTypeField;

    @FXML
    private TextArea locationField;



    /**
     * Gets an instance of the current main application running
     * @param main the instance of the current application running
     *             A reference of this is stored in a local variable
     * @param currentUser the current user using the app
     */
    public void setMainApp(MainFXApplication main, User currentUser) {
        mainFXApplication = main;
        this.currentUser = currentUser;
        reportManager = new ReportManager();
    }

    @FXML
    @SuppressWarnings("unchecked")
    private void initialize() {
        waterConditionField.getItems().clear();
        waterConditionField.setItems(FXCollections.observableArrayList(WaterConditionEnum.values()));
        waterConditionField.setValue(FXCollections.observableArrayList(WaterConditionEnum.values()).get(0));

        waterTypeField.getItems().clear();
        waterTypeField.setItems(FXCollections.observableArrayList(WaterTypeEnum.values()));
        waterTypeField.setValue(FXCollections.observableArrayList(WaterTypeEnum.values()).get(0));
    }

    @FXML
    private void backPressed() {
        mainFXApplication.showMainApplicationScreen();
    }

    @FXML
    private void submitPressed() {
        if ((locationField == null) || (locationField.getText() == null) || locationField.getText().trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainFXApplication.getStage());
            alert.setTitle("Error!");
            alert.setHeaderText("You left some fields blank!");
            alert.setContentText("You have to provide a location!");
            alert.showAndWait();
        } else {
            reportManager.submitWaterReport(currentUser.get_username(), locationField.getText().trim(),
                    (WaterTypeEnum)waterTypeField.getSelectionModel().getSelectedItem(),
                    (WaterConditionEnum)waterConditionField.getSelectionModel().getSelectedItem());
            mainFXApplication.showMainApplicationScreen();
        }
    }
}
