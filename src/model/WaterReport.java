package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Represents a water report in the system
 *
 * Information Holder
 */
public class WaterReport {
    private final SimpleIntegerProperty _reportnumber = new SimpleIntegerProperty();
    private final StringProperty _date = new SimpleStringProperty();
    private final StringProperty _time = new SimpleStringProperty();
    private final StringProperty _name = new SimpleStringProperty();
    private final StringProperty _location = new SimpleStringProperty();
    private final StringProperty _watertype = new SimpleStringProperty();
    private final StringProperty _watercondition = new SimpleStringProperty();

    /**
     * Gets the report number
     * @return the report number
     */
    public int get_reportnumber() {
        return _reportnumber.get();
    }

    /**
     * Gets the date of the submitted water report
     * @return date of report
     */
    public String get_date() {
        return _date.get();
    }

    /**
     * Gets the time of the water report
     * @return the water report time
     */
    public String get_time() {
        return _time.get();
    }

    /**
     * Get the name of the person who submitted the water report
     * @return the person who submitted the water report
     */
    public String get_name() {
        return _name.get();
    }

    /**
     * Gets the location of the water in the report
     * @return location of the water
     */
    public String get_location() {
        return _location.get();
    }

    /**
     * Gets the type of water submitted listed in the WaterTypeEnum
     * @return the water type
     */
    public String get_watertype() {
        return _watertype.get();
    }

    /**
     * Gets the condtion of the water in the report
     * @return condition of the water
     */
    public String get_watercondition() {
        return _watercondition.get();
    }


    /**
     * Sets all the paramters of the water report
     * @param reportnumber The report number of the water report
     * @param date date of the water report
     * @param time time of the report
     * @param name name of the user who submitted it
     * @param location location of the water
     * @param watertype the type of body of water
     * @param waterconditon the condition of the water
     */
    public WaterReport(int reportnumber, String date, String time, String name, String location,
                       String watertype, String waterconditon) {
        _reportnumber.set(reportnumber);
        _date.set(date);
        _time.set(time);
        _name.set(name);
        _location.set(location);
        _watertype.set(watertype);
        _watercondition.set(waterconditon);
    }
}
