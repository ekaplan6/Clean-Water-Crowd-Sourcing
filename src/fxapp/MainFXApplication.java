package fxapp;


import controller.WelcomeScreenController;
import controller.LoginScreenController;
import controller.MainApplicationScreenController;
import controller.RegistrationScreenController;
import controller.SubmitWaterReportScreenController;
import controller.SubmitQualityReportScreenController;
import controller.UserProfileScreenController;
import controller.ViewWaterReportScreenController;
import controller.ViewQualityReportScreenController;
import controller.ViewMapScreenController;
import controller.GraphParameterScreenController;
import controller.ADMINMainApplicationScreenController;
import controller.BlockUserScreenController;
import controller.UnblockUserScreenController;
import controller.BanUserScreenController;
import controller.UnbanUserScreenController;
import controller.DeleteAccountScreenController;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;


/**
 * This class handles all the scene switching to reuse the main stage.
 */
public class MainFXApplication extends Application{

    private static final double scale = 0.5;

    private Stage mainScreen;
    private User currentUser = new User();
    @Override
    public void start(Stage primaryStage) {
        mainScreen = primaryStage;
        showWelcomeScreen();
    }

    /**
     * Set the stage of the app
     * @return the main stage
     */
    public Stage getStage() {
        return mainScreen;
    }

    private FXMLLoader getLoader(String path) {
            java.net.URL location = this.getClass().getClassLoader().getResource(path);
            return new FXMLLoader(location);
    }

    /**
     * Shows the welcome screen of the app
     */
    public void showWelcomeScreen() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = getLoader("WelcomeScreen.fxml");
            Pane rootLayout = (loader != null) ? loader.load() : null;
            // Give the controller access to the main app.
            WelcomeScreenController controller = (loader != null) ? loader.getController() : null;
            assert controller != null;
            controller.setMainApp(this);

            // find the ImageView object in the layout and set it resizeable
            GridPane gp = (GridPane) ((rootLayout != null) ? rootLayout.getChildren().get(0) : null);
            BorderPane bp = (BorderPane) ((gp != null) ? gp.getChildren().get(0) : null);
            ImageView iv = (ImageView) ((bp != null) ? bp.getCenter() : null);
            if (iv != null) {
                iv.fitHeightProperty().bind(rootLayout.heightProperty().multiply(scale));
                iv.fitWidthProperty().bind(rootLayout.widthProperty().multiply(scale));
            }

            // Set the Main App title
            mainScreen.setTitle("Water Purity Application");

            // Show the scene containing the root layout.
            if (rootLayout != null) {
                Scene scene = new Scene(rootLayout);
                mainScreen.setScene(scene);
                mainScreen.show();
                currentUser = new User();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the login screen of the app
     */
    public void showLoginScreen() {
        try {
            FXMLLoader loader = getLoader("LoginScreen.fxml");
            Pane LoginPane = (loader != null) ? loader.load() : null;

            LoginScreenController controller = (loader != null) ? loader.getController() : null;
            assert controller != null;
            controller.setMainApp(this, currentUser);
            if (LoginPane != null) {
                Scene scene = new Scene(LoginPane);
                mainScreen.setTitle("Login");
                mainScreen.setScene(scene);
                mainScreen.show();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Shows the main application screen
     */
    public void showMainApplicationScreen() {
        try {
            FXMLLoader loader = getLoader("MainApplicationScreen.fxml");
            Pane MainAppPane = (loader != null) ? loader.load() : null;

            MainApplicationScreenController controller = (loader != null) ? loader.getController() : null;
            assert controller != null;
            controller.setMainApp(this, currentUser);
            if (MainAppPane != null) {
                Scene scene = new Scene(MainAppPane);
                mainScreen.setTitle("MainApp");
                mainScreen.setScene(scene);
                mainScreen.show();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Shows the Registration Screen
     */
    public void showRegistrationScreen() {
        try {
            FXMLLoader loader = getLoader("RegistrationScreen.fxml");
            Pane MainAppPane = (loader != null) ? loader.load() : null;

            RegistrationScreenController controller = (loader != null) ? loader.getController() : null;
            assert controller != null;
            controller.setMainApp(this);
            if (MainAppPane != null) {
                Scene scene = new Scene(MainAppPane);
                mainScreen.setTitle("Registration");
                mainScreen.setScene(scene);
                mainScreen.show();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Shows the submitWaterReportScreen
     */
    public void showSubmitWaterReportScreen() {
        try {
            FXMLLoader loader = getLoader("SubmitWaterReportScreen.fxml");
            Pane MainAppPane = (loader != null) ? loader.load() : null;

            SubmitWaterReportScreenController controller = (loader != null) ? loader.getController() : null;
            assert controller != null;
            controller.setMainApp(this, currentUser);
            if (MainAppPane != null) {
                Scene scene = new Scene(MainAppPane);
                mainScreen.setTitle("Submit Water Report!");
                mainScreen.setScene(scene);
                mainScreen.show();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Shows the submitQualityReportScreen
     */
    public void showSubmitQualityReportScreen() {
        try {
            FXMLLoader loader = getLoader("SubmitQualityReportScreen.fxml");
            Pane MainAppPane = (loader != null) ? loader.load() : null;

            SubmitQualityReportScreenController controller = (loader != null) ? loader.getController() : null;
            assert controller != null;
            controller.setMainApp(this, currentUser);
            if (MainAppPane != null) {
                Scene scene = new Scene(MainAppPane);
                mainScreen.setTitle("Submit Quality Report!");
                mainScreen.setScene(scene);
                mainScreen.show();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Shows the EditUserProfileScreen
     */
    public void showEditUserProfileScreen() {
        try {
            FXMLLoader loader = getLoader("UserProfileScreen.fxml");
            Pane MainAppPane = (loader != null) ? loader.load() : null;

            UserProfileScreenController controller = (loader != null) ? loader.getController() : null;
            assert controller != null;
            controller.setMainApp(this, currentUser);
            if (MainAppPane != null) {
                Scene scene = new Scene(MainAppPane);
                mainScreen.setTitle("Edit User Profile");
                mainScreen.setScene(scene);
                mainScreen.show();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * shows the ViewWaterReportScreen
     */
    public void showViewWaterReportScreen() {
        try {
            FXMLLoader loader = getLoader("ViewWaterReportScreen.fxml");
            Pane MainAppPane = (loader != null) ? loader.load() : null;

            ViewWaterReportScreenController controller = (loader != null) ? loader.getController() : null;
            assert controller != null;
            controller.setMainApp(this, currentUser);
            if (MainAppPane != null) {
                Scene scene = new Scene(MainAppPane);
                mainScreen.setTitle("View Water Reports");
                mainScreen.setScene(scene);
                mainScreen.show();
            }
        } catch (IllegalArgumentException ignored) {

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * shows the ViewQualityReportScreen
     */
    public void showViewQualityReportScreen() {
        try {
            FXMLLoader loader = getLoader("ViewQualityReportScreen.fxml");
            Pane MainAppPane = (loader != null) ? loader.load() : null;

            ViewQualityReportScreenController controller = (loader != null) ? loader.getController() : null;
            assert controller != null;
            controller.setMainApp(this, currentUser);
            if (MainAppPane != null) {
                Scene scene = new Scene(MainAppPane);
                mainScreen.setTitle("View Quality Reports");
                mainScreen.setScene(scene);
                mainScreen.show();
            }
        } catch (IllegalArgumentException ignored) {

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Shows the ViewMapScreen
     */
    public void showViewMapScreen() {
        try {
            FXMLLoader loader = getLoader("ViewMapScreen.fxml");
            AnchorPane MainAppPane = (loader != null) ? loader.load() : null;
            Stage mapScreen = new Stage();
            ViewMapScreenController controller = (loader != null) ? loader.getController() : null;
            assert controller != null;
            controller.setMainApp();
            if (MainAppPane != null) {
                Scene scene = new Scene(MainAppPane);
                mapScreen.setTitle("View Map");
                mapScreen.setScene(scene);
                mapScreen.show();
            }
        } catch (IllegalArgumentException ignored) {

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * shows the GraphParameterScreen
     */
    public void showGraphParameterScreen() {
        try {
            FXMLLoader loader = getLoader("GraphParameterScreen.fxml");
            Pane MainAppPane = (loader != null) ? loader.load() : null;

            GraphParameterScreenController controller = (loader != null) ? loader.getController() : null;
            assert controller != null;
            controller.setMainApp(this);
            if (MainAppPane != null) {
                Scene scene = new Scene(MainAppPane);
                mainScreen.setTitle("Graph Parameters");
                mainScreen.setScene(scene);
                mainScreen.show();
            }
        } catch (IllegalArgumentException ignored) {

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * shows the ADMINMainApplicationScreen
     */
    public void showADMINMainApplicationScreen() {
        try {
            FXMLLoader loader = getLoader("ADMINMainApplicationScreen.fxml");
            Pane MainAppPane = (loader != null) ? loader.load() : null;

            ADMINMainApplicationScreenController controller = (loader != null) ? loader.getController() : null;
            assert controller != null;
            controller.setMainApp(this);
            if (MainAppPane != null) {
                Scene scene = new Scene(MainAppPane);
                mainScreen.setTitle("MainApp - ADMIN");
                mainScreen.setScene(scene);
                mainScreen.show();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * shows the BlockUserScreen
     */
    public void showBlockUserScreen() {
        try {
            FXMLLoader loader = getLoader("BlockUserScreen.fxml");
            Pane MainAppPane = (loader != null) ? loader.load() : null;

            BlockUserScreenController controller = (loader != null) ? loader.getController() : null;
            assert controller != null;
            controller.setMainApp(this);
            if (MainAppPane != null) {
                Scene scene = new Scene(MainAppPane);
                mainScreen.setTitle("Block User - ADMIN");
                mainScreen.setScene(scene);
                mainScreen.show();
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (IllegalArgumentException ignored) {

        }

    }

    /**
     * shows the UnblockUserScreen
     */
    public void showUnblockUserScreen() {
        try {
            FXMLLoader loader = getLoader("UnblockUserScreen.fxml");
            Pane MainAppPane = (loader != null) ? loader.load() : null;

            UnblockUserScreenController controller = (loader != null) ? loader.getController() : null;
            assert controller != null;
            controller.setMainApp(this);
            if (MainAppPane != null) {
                Scene scene = new Scene(MainAppPane);
                mainScreen.setTitle("Unblock User - ADMIN");
                mainScreen.setScene(scene);
                mainScreen.show();
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (IllegalArgumentException ignored) {

        }

    }

    /**
     * shows the BanUserScreen
     */
    public void showBanUserScreen() {
        try {
            FXMLLoader loader = getLoader("BanUserScreen.fxml");
            Pane MainAppPane = (loader != null) ? loader.load() : null;

            BanUserScreenController controller = (loader != null) ? loader.getController() : null;
            assert controller != null;
            controller.setMainApp(this);
            if (MainAppPane != null) {
                Scene scene = new Scene(MainAppPane);
                mainScreen.setTitle("Ban User - ADMIN");
                mainScreen.setScene(scene);
                mainScreen.show();
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (IllegalArgumentException ignored) {

        }

    }

    /**
     * shows the UnbanUserScreen
     */
    public void showUnbanUserScreen() {
        try {
            FXMLLoader loader = getLoader("UnbanUserScreen.fxml");
            Pane MainAppPane = (loader != null) ? loader.load() : null;

            UnbanUserScreenController controller = (loader != null) ? loader.getController() : null;
            assert controller != null;
            controller.setMainApp(this);
            if (MainAppPane != null) {
                Scene scene = new Scene(MainAppPane);
                mainScreen.setTitle("Unban User - ADMIN");
                mainScreen.setScene(scene);
                mainScreen.show();
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (IllegalArgumentException ignored) {

        }

    }

    /**
     * shows the DeleteAccountScreen
     */
    public void showDeleteAccountScreen() {
        try {
            FXMLLoader loader = getLoader("DeleteAccountScreen.fxml");
            Pane MainAppPane = (loader != null) ? loader.load() : null;

            DeleteAccountScreenController controller = (loader != null) ? loader.getController() : null;
            assert controller != null;
            controller.setMainApp(this, currentUser);
            if (MainAppPane != null) {
                Scene scene = new Scene(MainAppPane);
                mainScreen.setTitle("DELETE ACCOUNT - ADMIN");
                mainScreen.setScene(scene);
                mainScreen.show();
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (IllegalArgumentException ignored) {

        }

    }


    /**
     * @param args the argument
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * shows an error popup if there is an error with the database
     */
    public void showDatabaseError() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(mainScreen);
        alert.setTitle("Database Error!");
        alert.setHeaderText("The application couldn't connect to the database!");
        alert.setContentText("Try again or call an admin staff if problem persists!");
        alert.showAndWait();
    }
}
