package controller;

import javafx.collections.ObservableList;
import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ReportManager;
import model.UserTypeEnum;
import model.WaterReport;
import model.User;


/**
 * controls the view water report screen
 */
public class ViewWaterReportScreenController {
    private MainFXApplication mainFXApplication;

    private User currentUser;

    private ReportManager reportManager;

    @FXML
    TableView<WaterReport> mainTable;

    @FXML
    TableColumn<WaterReport, Integer> reportnumber;

    @FXML
    TableColumn<WaterReport, String> date1;

    @FXML
    TableColumn<WaterReport, String> time1;

    @FXML
    TableColumn<WaterReport, String> location1;

    @FXML
    TableColumn<WaterReport, String> watertype;

    @FXML
    TableColumn<WaterReport, String> watercondition;

    private ObservableList<WaterReport> mainList;
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
        mainList = reportManager.getAllWaterReports();
        if ((mainList == null) || mainList.isEmpty()) {
            mainFXApplication.showMainApplicationScreen();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainFXApplication.getStage());
            alert.setTitle("Error!");
            alert.setHeaderText("There are no reports in database to show!");
            alert.setContentText("Add a report first!");
            alert.showAndWait();
        } else {
            reportnumber.setCellValueFactory(new PropertyValueFactory<>("_reportnumber"));
            date1.setCellValueFactory(new PropertyValueFactory<>("_date"));
            time1.setCellValueFactory(new PropertyValueFactory<>("_time"));
            location1.setCellValueFactory(new PropertyValueFactory<>("_location"));
            watertype.setCellValueFactory(new PropertyValueFactory<>("_watertype"));
            watercondition.setCellValueFactory(new PropertyValueFactory<>("_watercondition"));
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
            ObservableList<WaterReport> selectedDelete =  mainTable.getSelectionModel().getSelectedItems();
            reportManager.deleteWaterReport(selectedDelete, mainList);

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
