package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Manages the historical graph information. This is service provi
 */
public class GraphManager {

    private static final int width = 800;
    private static final int length = 600;

    /**
     * Gets all of the distinct years in the quality report database.
     * @return an observable list of integers
     */
    public ObservableList<Integer> getYears() {
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            String sql = "SELECT DISTINCT YEAR(date) AS year FROM QUALITYREPORT";
            ResultSet rs = stmt.executeQuery(sql);
            ObservableList<Integer> years = FXCollections.observableArrayList();
            while (rs.next()) {
                years.add(rs.getInt("year"));
            }
            return years;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets all of the distinct locations in the quality report database in the specified year.
     * @param yearSelected a specific year to look at quality reports
     * @return an observable list of the locations
     */
    public ObservableList<String> getLocations(int yearSelected) {
        ObservableList<String> location = FXCollections.observableArrayList();
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            String sql = "SELECT DISTINCT location FROM QUALITYREPORT WHERE YEAR(date) = '" + yearSelected + "'";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                location.add(rs.getString("location"));
            }
            return location;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates the historical graph in a new window/stage for the given virus, location, and year.
     * @param xval the months in the year (1 through 12)
     * @param yval the value of the virus PPM
     * @param virus the virus name
     * @param location the location name
     * @param year the year that is being evaluated in the quality report
     */
    @SuppressWarnings("unchecked")
    public void plotGraph(int[] xval, Integer[] yval, String virus, String location, Integer year) {
        Stage graphStage = new Stage();
        graphStage.setTitle("Historical Graph View!");

        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");
        yAxis.setLabel(virus);

        final LineChart<Number,Number> lineChart = new LineChart<>(xAxis, yAxis);
        XYChart.Series series = new XYChart.Series();
        lineChart.setTitle(virus + " PPM for " + location + " for " + year);
        lineChart.setLegendVisible(false);
        for (int i = 0; i < xval.length; i++) {
            if (yval[i] != null) {
                //series.getData().add(new XYChart.Data(xval[i], yval[i]));
                ObservableList data = series.getData();
                data.add(new XYChart.Data(xval[i], yval[i]));
            }
        }

        Scene scene  = new Scene(lineChart, width, length);
        //lineChart.getData().add(series);
        ObservableList data = lineChart.getData();
        data.add(series);

        graphStage.setScene(scene);
        graphStage.show();
    }
}
