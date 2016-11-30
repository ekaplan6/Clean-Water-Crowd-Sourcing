package controller;

import fxapp.MainFXApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import model.GraphManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Controls the Graph Parameter Screen
 */
public class GraphParameterScreenController {

    private static final int numberOfMonths = 12;

    private MainFXApplication mainFXApplication;

    @FXML
    private ComboBox<Integer> yearField;

    @FXML
    private ComboBox<String> virusField;

    @FXML
    private ComboBox<String> locationField;

    private GraphManager graphManager;

    /**
     * @param main an instance of main application to control main window (add scenes/alerts)
     */
    public void setMainApp(MainFXApplication main) {
        mainFXApplication = main;
        graphManager = new GraphManager();
        initalizeComboBox();
    }

    @FXML
    private void showGraphPressed() {
        Connection conn;
        Statement stmt;
        int[] xval = new int[numberOfMonths];
        for (int i = 0; i < numberOfMonths; i++) {
            xval[i] = i + 1;
        }
        Integer [] yval = new Integer[numberOfMonths];
        int yearSelected = yearField.getSelectionModel().getSelectedItem();
        String locationSelected = locationField.getSelectionModel().getSelectedItem();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            String sql;
            if ("Virus".equals(virusField.getSelectionModel().getSelectedItem().trim())) {
                sql = "SELECT AVG(virusPPM) AS AVERAGE, MONTH(date) AS MNTH FROM QUALITYREPORT WHERE YEAR(date) = '" +
                        yearSelected + "' AND LOWER(location) = LOWER('" + locationSelected + "') GROUP BY MONTH(date)";
            } else {
                sql = "SELECT AVG(contaminantPPM) AS AVERAGE, MONTH(date)" +
                        " AS MNTH FROM QUALITYREPORT WHERE YEAR(date) = '"
                        + yearSelected + "' AND LOWER(location) = LOWER('"
                        + locationSelected + "') GROUP BY MONTH(date)";
            }
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                yval[rs.getInt("MNTH") - 1] = rs.getInt("AVERAGE");
            }
            graphManager.plotGraph(xval, yval, virusField.getSelectionModel().getSelectedItem(),
                    locationField.getSelectionModel().getSelectedItem(),
                    yearField.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initalizeComboBox() {
        ObservableList<Integer> years = graphManager.getYears();
        if (((years != null) ? years.size() : 0) == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainFXApplication.getStage());
            alert.setTitle("Database Error!");
            alert.setHeaderText("The application couldn't connect to the database!");
            alert.setContentText("Try again or call an admin staff if problem persists!");
            alert.showAndWait();
            mainFXApplication.showMainApplicationScreen();
        } else {
            virusField.getItems().setAll("Virus", "Contaminant");
            virusField.setValue("Virus");
            yearField.getItems().clear();

            yearField.setItems(years);
            yearField.valueProperty().addListener((ov, notUsed, yearSelected) -> {
                ObservableList<String> location = graphManager.getLocations(yearSelected);
                locationField.getItems().clear();
                locationField.setItems(location);
                locationField.setValue((location != null) ? location.get(0) : null);
            });
            yearField.setValue(years.get(0));
        }


    }

    @FXML
    private void backPressed() {
        mainFXApplication.showMainApplicationScreen();
    }

}
