package test.java.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Assert;

/**
 * Created by abhaydalmia on 11/13/16.
 */
public class GraphManagerTest {
    @Test
    public void getLocationsTest() {
        ReportManager reportManager = new ReportManager();
        GraphManager graphManager = new GraphManager();

        //Test 1: Check that current locations are correct
        ObservableList<String> allLocations = graphManager.getLocations(2016);
        assertEquals("The method does NOT only return unique locations." +
                " Returns too many locations!", allLocations.size(), 2);
        String[] actualLocations = {"Brittain Dining Hall, Techwood Drive, " +
                "Atlanta, GA", "Apt. Mitra Oasis B903 Senen, Jakarta Pusat " +
                "Indonesia, 10410"};
        for (int i = 0; i < allLocations.size(); i++) {
            allLocations.set(i, allLocations.get(i).replaceAll("(\\t|\\r?\\n)+", " "));
        }

        for (int i = 0; i < actualLocations.length; i++) {
            assertTrue("The method does not return all addresses in the " +
                    "database", allLocations.contains(actualLocations[i]));

        }

        //Test 2: Add a quality report to system.
        reportManager.submitQualityReport("TESTUSER", "Test Location",
                OverallConditionEnum.SAFE, "0", "0");
        ObservableList<String> newLocations = graphManager.getLocations(2016);
        assertEquals("The method does NOT only return unique locations." +
                " Returns too many locations!", newLocations.size(), 3);
        assertTrue("The method does not return the added addresses in the " +
                        "database", newLocations.contains("Test Location"));

        //Test 3: Delete a quality report from system
        deleteQualityReport(9);
        ObservableList<String> oldLocations = graphManager.getLocations(2016);
        assertEquals("The method does still returns a location" +
                "that was deleted!", oldLocations.size(), 2);
    }

    private static void deleteQualityReport(int reportNumber) {
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            String sql = "DELETE FROM QUALITYREPORT WHERE reportnumber = '" + reportNumber + "';";
            stmt.executeUpdate(sql);
            stmt.close();
            conn.close();
        } catch (Exception ignored) {

        }
    }

}