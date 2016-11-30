package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Manages the Reports. This is service provider.
 */
public class ReportManager {
    /**
     * Accesses the database to get all currently existing quality reports.
     * @return an observable list of all quality reports
     */
    public ObservableList<QualityReport> getAllQualityReports() {
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            String sql = "SELECT reportnumber, name, date, time, location, " +
                    "overallcondition, virusPPM, contaminantPPM FROM QUALITYREPORT";
            ResultSet rs = stmt.executeQuery(sql);
            List<QualityReport> reportList = new ArrayList<>();
            while (rs.next()) {
                QualityReport temp = new QualityReport(rs.getInt("reportnumber"), rs.getString("date"),
                        rs.getString("time"), rs.getString("name"), rs.getString("location"),
                        rs.getString("overallcondition"), rs.getInt("virusPPM"), rs.getInt("contaminantPPM"));
                reportList.add(temp);
            }
            return FXCollections.observableList(reportList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deletes the quality report that is currently selected on the
     * ViewQualityReportScreenController from the database and the view.
     * @param selectedDelete the quality reports that will be deleted
     * @param mainList the list of all quality reports that is seen on ViewQualityReportScreen
     */
    public void deleteQualityReport(Iterable<QualityReport> selectedDelete, Collection<QualityReport> mainList) {
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            for (QualityReport current : selectedDelete) {
                String sql = "DELETE FROM QUALITYREPORT WHERE reportnumber = '" + current.get_reportnumber() + "';";
                stmt.executeUpdate(sql);
                mainList.remove(current);
            }
        } catch (Exception ignored) {

        }
    }

    /**
     * Submits a quality report to be saved in the database.
     * @param user the current manager or worker that is creating the quality report
     * @param location the location of the water in the quality report
     * @param overallCondition the overall condition of the water at this location
     * @param virusPPM the condition of the water in terms of virus PPM at this location
     * @param contaminantPPM the condition of the water in terms of contaminant PPM at this location
     */
    public void submitQualityReport(String user, String location, OverallConditionEnum overallCondition,
                                    String virusPPM, String contaminantPPM) {
        int reportNumber;
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            String sql = "SELECT MAX(reportnumber) AS MAX FROM QUALITYREPORT";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                reportNumber = rs.getInt("MAX") + 1;
            } else {
                reportNumber = 1;
            }
            LocalDateTime now = LocalDateTime.now();
            String date = now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth();
            String time = now.getHour() + ":" + now.getMinute() + ":" + now.getSecond();
            sql = "INSERT INTO `QUALITYREPORT` (`reportnumber`, `date`, `time`, `name`, `location`, " +
                    "`overallcondition`, `virusPPM`, `contaminantPPM`) VALUES ('" + reportNumber + "', '"
                    + date + "', '" + time + "', '" + user + "', '" + location + "', '" + overallCondition
                    + "', '" + virusPPM + "', '" + contaminantPPM + "')";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Accesses the database to get all currently existing water reports.
     * @return an observable list of all water reports
     */
    public ObservableList<WaterReport> getAllWaterReports() {
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            String sql = "SELECT reportnumber, name, date, time, location, watertype, watercondition FROM WATERREPORT";
            ResultSet rs = stmt.executeQuery(sql);
            List<WaterReport> reportList = new ArrayList<>();
            while (rs.next()) {
                WaterReport temp = new WaterReport(rs.getInt("reportnumber"), rs.getString("date"),
                        rs.getString("time"), rs.getString("name"), rs.getString("location"),
                        rs.getString("watertype"), rs.getString("watercondition"));
                reportList.add(temp);
            }
            return FXCollections.observableList(reportList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deletes the water report that is currently selected on the ViewWaterReportScreenController
     * from the database and from the view.
     * @param selectedDelete the water reports that will be deleted
     * @param mainList the list of all water reports that is seen on ViewWaterReportScreen
     */
    public void deleteWaterReport(Iterable<WaterReport> selectedDelete, Collection<WaterReport> mainList) {
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            for (WaterReport current : selectedDelete) {
                String sql = "DELETE FROM WATERREPORT WHERE reportnumber = '" + current.get_reportnumber() + "';";
                stmt.executeUpdate(sql);
                mainList.remove(current);
            }
        } catch (Exception ignored) {

        }
    }

    /**
     * Submits a water report to be saved in the database.
     * @param user the current user that is creating the water report
     * @param location the location of the water in the report
     * @param waterType the type of water at this location
     * @param waterCondition the condition of the water at this location
     */
    public void submitWaterReport(String user, String location, WaterTypeEnum waterType,
                                  WaterConditionEnum waterCondition) {
        int reportNumber;
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            String sql = "SELECT MAX(reportnumber) AS MAX FROM WATERREPORT";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                reportNumber = rs.getInt("MAX") + 1;
            } else {
                reportNumber = 1;
            }
            LocalDateTime now = LocalDateTime.now();
            String date = now.getYear() + "-" + now.getMonthValue() + "-" + now.getDayOfMonth();
            String time = now.getHour() + ":" + now.getMinute() + ":" + now.getSecond();
            sql = "INSERT INTO `WATERREPORT` (`reportnumber`, `date`, `time`, `name`, `location`, " +
                    "`watertype`, `watercondition`) VALUES ('" + reportNumber + "', '" + date + "', '"
                    + time + "', '" + user + "', '" + location + "', '" + waterType + "', '" + waterCondition + "')";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
