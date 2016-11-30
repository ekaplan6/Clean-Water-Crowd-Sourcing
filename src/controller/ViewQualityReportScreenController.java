package controller;

import javafx.collections.ObservableList;
import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.QualityReport;
import model.ReportManager;
import model.User;
import model.UserTypeEnum;


/**
 * controls the view quality report screen
 */
public class ViewQualityReportScreenController {
    private MainFXApplication mainFXApplication;

    private User currentUser;

    private ReportManager reportManager;

    @FXML
    TableView<QualityReport> mainTable;

    @FXML
    TableColumn<QualityReport, Integer> reportnumber;

    @FXML
    TableColumn<QualityReport, String> date1;

    @FXML
    TableColumn<QualityReport, String> time1;

    @FXML
    TableColumn<QualityReport, String> location1;

    @FXML
    TableColumn<QualityReport, String> overallcondition;

    @FXML
    TableColumn<QualityReport, Integer> virusPPM;

    @FXML
    TableColumn<QualityReport, Integer> contaminantPPM;

    private ObservableList<QualityReport> mainList;
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
        initializeTable();
    }

    @FXML
    private void initializeTable() {
        mainList = reportManager.getAllQualityReports();
        if ((mainList == null) || mainList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainFXApplication.getStage());
            alert.setTitle("Error!");
            alert.setHeaderText("There are no reports in database to show!");
            alert.setContentText("Add a report first!");
            alert.showAndWait();
            throw new IllegalArgumentException();
        } else {
            reportnumber.setCellValueFactory(new PropertyValueFactory<>("_reportnumber"));
            date1.setCellValueFactory(new PropertyValueFactory<>("_date"));
            time1.setCellValueFactory(new PropertyValueFactory<>("_time"));
            location1.setCellValueFactory(new PropertyValueFactory<>("_location"));
            overallcondition.setCellValueFactory(new PropertyValueFactory<>("_overallcondition"));
            virusPPM.setCellValueFactory(new PropertyValueFactory<>("_virusPPM"));
            contaminantPPM.setCellValueFactory(new PropertyValueFactory<>("_contaminantPPM"));

            mainTable.setItems(mainList);
        }
    }

    @FXML
    private void backPressed() {
        mainFXApplication.showMainApplicationScreen();
    }

    @FXML
    private void deletePressed() {
        if (currentUser.get_type().equals(UserTypeEnum.MANAGER.toString())) {
            if (mainList.isEmpty()) {
                mainFXApplication.showMainApplicationScreen();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(mainFXApplication.getStage());
                alert.setTitle("All Reports Deleted!");
                alert.setHeaderText("There are no reports in database to show!");
                alert.setContentText("You'll be redirected to the Main Application Screen.");
                alert.showAndWait();
            } else {
                ObservableList<QualityReport> selectedDelete = mainTable.getSelectionModel().getSelectedItems();
                reportManager.deleteQualityReport(selectedDelete, mainList);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainFXApplication.getStage());
            alert.setTitle("Error!");
            alert.setHeaderText("Only managers can delete reports!!");
            alert.setContentText("Talk to a system admin if you think this is in error!");
            alert.showAndWait();
        }
    }

}
