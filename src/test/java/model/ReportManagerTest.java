package test.java.model;

import model.ReportManager;
import model.WaterReport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import static org.junit.Assert.*;

public class ReportManagerTest {
    private List<WaterReport> reportList;
    private List<WaterReport> deletedList;

    @Before
    public void setUp() throws Exception {
        //Get the reports from the database.
        reportList = new ArrayList<>();
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();

            String sql = "SELECT reportnumber, name, date, time, location, watertype, watercondition FROM WATERREPORT";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                WaterReport temp = new WaterReport(rs.getInt("reportnumber"), rs.getString("date"),
                        rs.getString("time"), rs.getString("name"), rs.getString("location"),
                        rs.getString("watertype"), rs.getString("watercondition"));
                reportList.add(temp);
            }
        } catch (Exception ignored) {

        }
    }

    @After
    public void tearDown() throws Exception {
        //Adds back all reports that were in the database.
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            for (WaterReport current : deletedList) {
                stmt = conn.createStatement();
                String sql = "INSERT INTO `WATERREPORT` (`reportnumber`, `date`, `time`, `name`, `location`, " +
                        "`watertype`, `watercondition`) VALUES ('" + current.get_reportnumber() + "', '"
                        + current.get_date() + "', '" + current.get_time() + "', '" + current.get_name() + "', '"
                        + current.get_location() + "', '" + current.get_watertype() + "', '"
                        + current.get_watercondition() + "')";
                stmt.executeUpdate(sql);
            }
        } catch (Exception ignored) {

        }
    }

    @Test
    public void deleteWaterReportTest() throws Exception {
        int count = reportList.size();
        ReportManager reportManager = new ReportManager();

        //check with null for selectedDelete list
        reportManager.deleteWaterReport(null, reportList);
        assertEquals(count, reportList.size());
        assertEquals(count, countReportsInDatabase());

        //check with empty list
        reportManager.deleteWaterReport(new ArrayList<>(), reportList);
        assertEquals(count, reportList.size());
        assertEquals(count, countReportsInDatabase());

        //check with empty list and empty mainList
        reportManager.deleteWaterReport(new ArrayList<>(), new ArrayList<>());
        assertEquals(count, reportList.size());
        assertEquals(count, countReportsInDatabase());

        List<WaterReport> modifiedReportList = new ArrayList<>(reportList);
        deletedList = new ArrayList<>(reportList);

        //check with one item in selectedDelete
        if (count > 1) {
            List<WaterReport> selectedDelete = new ArrayList<>();
            Random rand = new Random();
            selectedDelete.add(reportList.get(rand.nextInt(count - 1)));
            reportManager.deleteWaterReport(selectedDelete, modifiedReportList);
            assertEquals(count - 1, modifiedReportList.size());
            assertEquals(count - 1, countReportsInDatabase());

            assertFalse(modifiedReportList.contains(selectedDelete.get(0)));
        } else if (count == 1) {
            List<WaterReport> selectedDelete = new ArrayList<>();
            Random rand = new Random();
            selectedDelete.add(reportList.get(0));
            reportManager.deleteWaterReport(selectedDelete, modifiedReportList);
            assertEquals(count - 1, modifiedReportList.size());
            assertEquals(count - 1, countReportsInDatabase());

            assertFalse(modifiedReportList.contains(selectedDelete.get(0)));
        }

        //check with two items in selectedDelete
        if (count >= 4) {
            List<WaterReport> selectedDelete = new ArrayList<>();
            Random rand = new Random();
            int randInt1 = rand.nextInt(count - 2);
            int randInt2 = rand.nextInt(count - 3);
            while (randInt1 == randInt2) {
                randInt2 = rand.nextInt(count - 3);
            }
            selectedDelete.add(reportList.get(randInt1));
            selectedDelete.add(reportList.get(randInt2));

            reportManager.deleteWaterReport(selectedDelete, modifiedReportList);
            assertEquals(count - 1, modifiedReportList.size());
            assertEquals(count - 1, countReportsInDatabase());

            assertFalse(modifiedReportList.contains(selectedDelete.get(0)));
            assertFalse(modifiedReportList.contains(selectedDelete.get(1)));
        }

        //check with objects not in the database or in the mainList (should delete all)
        if (count >= 1) {
            reportManager.deleteWaterReport(reportList, modifiedReportList);
            assertEquals(0, modifiedReportList.size());
            assertEquals(0, countReportsInDatabase());
        }

        System.out.println("tested deleting water report");
    }

    private int countReportsInDatabase() {
        Connection conn;
        Statement stmt;
        int count = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();

            String sql = "SELECT reportnumber, name, date, time, location, watertype, watercondition FROM WATERREPORT";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.last()) {
                count = rs.getRow();
            }
        } catch (Exception ignored) {

        }
        return count;
    }
}