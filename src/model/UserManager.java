package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Manages the user information. This is service provider.
 */
public class UserManager {
    /**
     * Accesses the database to get all users that have not been banned.
     * This finds only users with the actual "user" distinction.
     * @return an observable list of all the unbanned users
     */
    public ObservableList<User> getUnbannedUsers() {
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            String sql = "SELECT username, password, fullname, ban, attempt, " +
                    "type, emailaddress, homeaddress, company, jobtitle, department FROM USER";
            ResultSet rs = stmt.executeQuery(sql);
            List<User> reportList = new ArrayList<>();
            while (rs.next()) {
                String userType = UserTypeEnum.USER.toString();
                String responseType = rs.getString("type");
                if (userType.equals(responseType) && (rs.getInt("ban") == 0)) {
                    User temp = new User(rs.getString("username"), rs.getString("fullname"),
                            rs.getInt("ban"), rs.getString("type"), rs.getString("emailaddress"),
                            rs.getString("homeaddress"), rs.getString("company"), rs.getString("jobtitle"),
                            rs.getString("department"));
                    reportList.add(temp);
                }

            }
            return FXCollections.observableList(reportList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Accesses the database to get all users that have been banned.
     * This finds only users with the actual "user" distinction.
     * @return an observable list of all the banned users
     */
    public ObservableList<User> getBannedUsers() {
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            String sql = "SELECT username, password, fullname, ban, attempt, type, emailaddress, " +
                    "homeaddress, company, " +
                    "jobtitle, department FROM USER";
            ResultSet rs = stmt.executeQuery(sql);
            List<User> reportList = new ArrayList<>();
            while (rs.next()) {
                String responseType = rs.getString("type");
                String userType = UserTypeEnum.USER.toString();
                if (userType.equals(responseType) && (rs.getInt("ban") == 1)) {
                    User temp = new User(rs.getString("username"), rs.getString("fullname"), rs.getInt("ban"),
                            rs.getString("type"), rs.getString("emailaddress"), rs.getString("homeaddress"),
                            rs.getString("company"), rs.getString("jobtitle"), rs.getString("department"));
                    reportList.add(temp);
                }

            }
            return FXCollections.observableList(reportList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Accesses the database to get all users that have been blocked.
     * This finds users with the actual "user", "manager", and "worker"
     * distinctions.
     * @return an observable list of all the blocked users
     */
    public ObservableList<User> getUnblockedUsers() {
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            String sql = "SELECT username, password, fullname, ban, attempt, type, " +
                    "emailaddress, homeaddress, company, jobtitle, department FROM USER";
            ResultSet rs = stmt.executeQuery(sql);
            List<User> reportList = new ArrayList<>();
            while (rs.next()) {
                String responseType = rs.getString("type");
                String adminType = UserTypeEnum.ADMIN.toString();
                if (!adminType.equals(responseType) && (rs.getInt("attempt") < 3)) {
                    User temp = new User(rs.getString("username"),
                            rs.getString("fullname"), rs.getInt("ban"), rs.getString("type"),
                            rs.getString("emailaddress"), rs.getString("homeaddress"), rs.getString("company"),
                            rs.getString("jobtitle"), rs.getString("department"));
                    reportList.add(temp);
                }

            }
            return FXCollections.observableList(reportList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Accesses the database to get all users that have not been blocked.
     * This finds users with the actual "user", "manager", and "worker" distinctions.
     * @return an observable list of all the unblocked users
     */
    public ObservableList<User> getBlockedUsers() {
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            String sql = "SELECT username, password, fullname, ban, attempt, type, emailaddress, homeaddress, " +
                    "company, jobtitle, department FROM USER";
            ResultSet rs = stmt.executeQuery(sql);
            List<User> reportList = new ArrayList<>();
            while (rs.next()) {
                String responseType = rs.getString("type");
                String adminType = UserTypeEnum.ADMIN.toString();
                if (!adminType.equals(responseType) && (rs.getInt("attempt") > 2)) {
                    User temp = new User(rs.getString("username"), rs.getString("fullname"), rs.getInt("ban"),
                            rs.getString("type"), rs.getString("emailaddress"), rs.getString("homeaddress"),
                            rs.getString("company"), rs.getString("jobtitle"), rs.getString("department"));
                    reportList.add(temp);
                }

            }
            return FXCollections.observableList(reportList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets an observable list of all the users in the database to be used by the controller of the app.
     * @param currentUser A reference to the current user so that the current user is the only user not
     *                    included in the observable list
     * @return ObservableList of all users in database. (Used by Admin to delete users).
     */
    public ObservableList<User> getUsers(User currentUser) {
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            String sql = "SELECT username, password, " +
                    "fullname, ban, attempt, type, emailad" +
                    "dress, homeaddress, company, jobtitle, department FROM USER";
            ResultSet rs = stmt.executeQuery(sql);
            List<User> reportList = new ArrayList<>();
            while (rs.next()) {
                String username = rs.getString("username");
                String curUser = currentUser.get_username();
                if (!username.equals(curUser)) {
                    User temp = new User(rs.getString("username"),
                            rs.getString("fullname"), rs.getInt("ban"), rs.getString("type"),
                            rs.getString("emailaddress"), rs.getString("homeaddress"),
                            rs.getString("company"), rs.getString("jobtitle"), rs.getString("department"));
                    reportList.add(temp);
                }

            }
            return FXCollections.observableList(reportList);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Bans the selected users from submitting water reports and removes them from the list shown in the view.
     * @param selectedDelete a list of users to ban
     * @param mainList a list of all users that could be banned
     */
    public void banUsers(Iterable<User> selectedDelete, Collection<User> mainList) {
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:" +
                    "3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            for (User current : selectedDelete) {
                String sql = "UPDATE USER SET ban = '1' WHERE username = '" + current.get_username() + "';";
                stmt.executeUpdate(sql);
                mainList.remove(current);
            }
        } catch (Exception ignored) {

        }
    }

    /**
     * Blocks user from logging in and using the application. Removes them from the list of users shown in the view.
     * @param selectedDelete a list of users to block
     * @param mainList a list of all users that could be blocked
     */
    public void blockUsers(Iterable<User> selectedDelete, Collection<User> mainList) {
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4fre" +
                    "e.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            for (User current : selectedDelete) {
                String sql = "UPDATE USER SET attempt = '99' WHERE username = '" + current.get_username() + "';";
                stmt.executeUpdate(sql);
                mainList.remove(current);
            }
        } catch (Exception ignored) {

        }
    }

    /**
     * Deletes user from the application database. Removes them from the list of users shown in the view.
     * @param selectedDelete a list of users to delete
     * @param mainList a list of all users that could be detleted
     */
    public void deleteUsers(Iterable<User> selectedDelete, Collection<User> mainList) {
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://d" +
                    "b4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            for (User current : selectedDelete) {
                String sql = "DELETE FROM USER WHERE username = '" + current.get_username() + "';";
                stmt.executeUpdate(sql);
                mainList.remove(current);
            }
        } catch (Exception ignored) {

        }
    }

    /**
     * Creates the specified user in the database.
     *
     * @param username the user's desired username
     * @param password the user's desired password
     * @param fullName the user's full name
     * @param userType the user's user type
     */
    public void createUser(String username, String password, String fullName, UserTypeEnum userType) {
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            String sql = "INSERT INTO `USER` (`username`, `password`, `fullname`, `ban`, " +
                    "`attempt`, `type`) VALUES ('" + username + "', '" + password + "', '" + fullName + "', '0', '0', '"
                    + userType.toString() +"')";
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Accesses the database to get any users with the inputted username.
     * @param username a string of a username that a user wants to have
     * @return the users with the specified username
     */
    public ResultSet getUsersWithUsername(String username) {
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            String sql = "SELECT username FROM USER WHERE username = '" + username + "'";
            return stmt.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
