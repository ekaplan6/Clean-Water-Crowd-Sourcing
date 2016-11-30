package controller;

import javafx.scene.control.Alert;
import javafx.util.Pair;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;


import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import netscape.javascript.JSObject;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.List;
import java.util.ArrayList;


/**
 * controls the view map screen
 */
public class ViewMapScreenController implements Initializable, MapComponentInitializedListener {

    // --Commented out by Inspection START (11/7/16, 1:26 PM):
//    @FXML
//    private AnchorPane AnchorPane;
// --Commented out by Inspection STOP (11/7/16, 1:26 PM)

    private static final double atlLat = 33.780064;
    private static final int zoomLevel = 12;
    private static final double atlLong = -84.389363;

    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;

    private Map<String, Pair<String, String>> uniqueLocationTable;

    private GeocodingService geocodingService;
    /*
    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;

    @FXML
    private void initialize() {
        mapView.addMapInializedListener(this);
    }
    */


    /**
     * Initializes the map
     */
    public void setMainApp() {
        uniqueLocationTable = new HashMap<>();

        mapView.addMapInializedListener(this);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }


    @Override
    public void mapInitialized() {
        geocodingService = new GeocodingService();
        markerInitialize();


        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(atlLat, atlLong))
                .mapType(MapTypeIdEnum.ROADMAP)
                .mapMarker(true)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(zoomLevel);

        map = mapView.createMap(mapOptions);


        /*InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
        infoWindowOptions.content("<h2>Fred Wilkie</h2>"
                + "Current Location: Safeway<br>"
                + "ETA: 45 minutes" );

        InfoWindow fredWilkeInfoWindow = new InfoWindow(infoWindowOptions);
        fredWilkeInfoWindow.open(map, myLocationMarker);*/
    }

    private void markerInitialize() {
        getAllReports();
        String[] uniqueLocations = uniqueLocationTable.keySet().toArray(new String[0]);
        List<LatLong> coordinateList = new ArrayList<>();

        for (String uniqueLocation : uniqueLocations) {
            String currentAddress = uniqueLocation.replace("\n", ",");

            geocodingService.geocode(currentAddress, (GeocodingResult[] results, GeocoderStatus status) -> {
                LatLong latLong = null;
                if (status == GeocoderStatus.ZERO_RESULTS) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "No matching address found");
                    alert.show();
                } else if (results.length > 1) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Multiple results found, showing the first one.");
                    alert.show();
                    latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(),
                            results[0].getGeometry().getLocation().getLongitude());
                } else {
                    latLong = new LatLong(results[0].getGeometry().getLocation().getLatitude(),
                            results[0].getGeometry().getLocation().getLongitude());
                }

                coordinateList.add(latLong);
                createMarker(coordinateList, uniqueLocations);
            });

        }



    }

    private void createMarker(List<LatLong> coordinateList, String[] uniqueLocations) {
        for (int i = 0; i < coordinateList.size(); i++) {
            LatLong latLong = coordinateList.get(i);
            if (latLong != null) {
                String address = uniqueLocations[i];
                Pair<String, String> current = uniqueLocationTable.get(address);
                createMarker(latLong, "Water Type: " + current.getKey() + "<br>" +
                        "Water Condition: " + current.getValue());
            }
        }
    }


    private void createMarker(LatLong latLong, String content) {
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(latLong);
        markerOptions1.visible(true);
        Marker myLocationMarker = new Marker(markerOptions1);
        map.addMarker(myLocationMarker);

        map.addUIEventHandler(myLocationMarker, UIEventType.click, (JSObject obj) -> {
            InfoWindow information = new InfoWindow();
            information.setContent(content);
            information.open(map, myLocationMarker);
        });



    }
    private void getAllReports() {
        Connection conn;
        Statement stmt;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/bitsplease", "bitsplease", "bitsplease");
            stmt = conn.createStatement();
            String sql = "SELECT reportnumber, name, date, time, location, watertype, " +
                    "watercondition FROM WATERREPORT ORDER BY date DESC, time DESC";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (!uniqueLocationTable.containsKey(rs.getString("location").replace("\n",","))) {
                    uniqueLocationTable.put(rs.getString("location").replace("\n",","),
                            new Pair<>(rs.getString("watertype"), rs.getString("watercondition")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
