package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Information Holder
 * This is data for a single Quality Report
 */
public class QualityReport {
    private final SimpleIntegerProperty _reportnumber = new SimpleIntegerProperty();
    private final StringProperty _date = new SimpleStringProperty();
    private final StringProperty _time = new SimpleStringProperty();
    private final StringProperty _name = new SimpleStringProperty();
    private final StringProperty _location = new SimpleStringProperty();
    private final StringProperty _overallcondition = new SimpleStringProperty();
    private final SimpleIntegerProperty _virusPPM = new SimpleIntegerProperty();
    private final SimpleIntegerProperty _contaminantPPM = new SimpleIntegerProperty();

    /**
     * @return the report nubmer
     */
    public int get_reportnumber() {
        return _reportnumber.get();
    }

// --Commented out by Inspection START (10/29/16, 7:07 PM):
//    public void set_reportnumber(int _reportnumber) {
//        this._reportnumber.set(_reportnumber);
//    }
// --Commented out by Inspection STOP (10/29/16, 7:07 PM)

    /**
     * Get the date
     * @return the date
     */
    public String get_date() {
        return _date.get();
    }

    /**
     * Set the date
     * @param _date the date
     */
    public void set_date(String _date) {
        this._date.set(_date);
    }

    /**
     * Get the time of the report
     * @return the time of the report
     */
    public String get_time() {
        return _time.get();
    }

    /**
     * Set the time of the report
     * @param _time the time of the report
     */
    public void set_time(String _time) {
        this._time.set(_time);
    }

    /**
     * Get the name of the user who submitted the quality report
     * @return the name of the user who submitted the quality report
     */
    public String get_name() {
        return _name.get();
    }

    /**
     * Set the name of the user who submitted the quality report
     * @param _name the name of the user who submitted the quality report
     */
    public void set_name(String _name) {
        this._name.set(_name);
    }

    /**
     * Get location of the water
     * @return location of the water
     */
    public String get_location() {
        return _location.get();
    }

    /**
     * Set the location of the water
     * @param _location the location of the water
     */
    public void set_location(String _location) {
        this._location.set(_location);
    }

    /**
     * Get the overall condition of the water
     * @return The overall condition of the water
     */
    public String get_overallcondition() {
        return _overallcondition.get();
    }

    /**
     * Set the overall condition of the water
     * @param _overallcondition the overall condition of the water
     */
    public void set_overallcondition(String _overallcondition) {
        this._overallcondition.set(_overallcondition);
    }

    /**
     * Get the virus in parts per million in the water
     * @return the virus in parts per million in the water
     */
    public int get_virusPPM() {
        return _virusPPM.get();
    }

    /**
     * Set the virus in parts per million in the water
     * @param _virusPPM the virus in parts per million in the water
     */
    public void set_virusPPM(int _virusPPM) {
        this._virusPPM.set(_virusPPM);
    }

    /**
     * Get the contaminant in parts per million in the water
     * @return the contaminant in parts per million in the water
     */
    public int get_contaminantPPM() {
        return _contaminantPPM.get();
    }

    /**
     * Set the contomainant in parts per million in the water
     * @param _contaminantPPM the contaminant in parts per million in the water
     */
    public void set_contaminantPPM(int _contaminantPPM) {
        this._contaminantPPM.set(_contaminantPPM);
    }

    /**
     * This sets all the parameters for the Quality Report
     * @param reportnumber the report number
     * @param date the date
     * @param time the time
     * @param name the name of the user who submitted it
     * @param location the location of the water
     * @param overallcondition the overall condition of the water
     * @param virusPPM the virus in parts per million
     * @param contaminantPPM the contaminant in parts per million
     */
    public QualityReport(int reportnumber, String date, String time,
                         String name, String location, String overallcondition, int virusPPM, int contaminantPPM) {
        _reportnumber.set(reportnumber);
        _date.set(date);
        _time.set(time);
        _name.set(name);
        _location.set(location);
        _overallcondition.set(overallcondition);
        _virusPPM.set(virusPPM);
        _contaminantPPM.set(contaminantPPM);

    }
}
