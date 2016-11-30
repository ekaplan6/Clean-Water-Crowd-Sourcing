package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.WritableIntegerValue;


/**
 * Represents a single user in the system
 *
 * Information Holder
 */
public class User {
    private final StringProperty _username = new SimpleStringProperty();
    private final StringProperty _fullname = new SimpleStringProperty();
    private final WritableIntegerValue _ban = new SimpleIntegerProperty();
    private final StringProperty _type = new SimpleStringProperty();
    private final StringProperty _emailaddress = new SimpleStringProperty();
    private final StringProperty _homeaddress = new SimpleStringProperty();
    private final StringProperty _company = new SimpleStringProperty();
    private final StringProperty _jobtitle = new SimpleStringProperty();
    private final StringProperty _department = new SimpleStringProperty();

    /**
     * This Constructor is the default constuctor and sets all values to null
     */
    public User() {

    }

    /**
     * This constructor sets the parameters for the users
     * @param username The username of the user
     * @param fullname The first and last name of the user
     * @param ban If the user is banned or not
     * @param type The types of users listed in the User Type Enum
     * @param emailaddress The user's email address
     * @param homeaddress The home address of the user
     * @param company The company the user may work for
     * @param jobtitle The job title of the user
     * @param department What department the user works for
     */
    public User(String username, String fullname, int ban, String type, String emailaddress,
                String homeaddress, String company, String jobtitle, String department) {
        _username.set(username);
        _fullname.set(fullname);
        _ban.set(ban);
        _type.set(type);
        _emailaddress.set(emailaddress);
        _homeaddress.set(homeaddress);
        _company.set(company);
        _jobtitle.set(jobtitle);
        _department.set(department);
    }

    /**
     * Get the user's username
     * @return the user's username
     */
    public String get_username() {
        return _username.get();
    }

    /**
     * Sets the user's username
     * @param _username the user's username for login
     */
    public void set_username(String _username) {
        this._username.set(_username);
    }

    /**
     * Get the full name of the user
     * @return the user's full name
     */
    public String get_fullname() {
        return _fullname.get();
    }

    /**
     * Set the full name
     * @param _fullname The user's first and last name
     */
    public void set_fullname(String _fullname) {
        this._fullname.set(_fullname);
    }

    /**
     * Get if the user is banned
     * @return If the user is banned
     */
    public int get_ban() {
        return _ban.get();
    }

    /**
     * Set the user to banned
     * @param _ban if the user is banned
     */
    public void set_ban(int _ban) {
        this._ban.set(_ban);
    }

    /**
     * Get the type of user
     * @return the type of user
     */
    public String get_type() {
        return _type.get();
    }

    /**
     * Set the type of user
     * @param _type the type of user
     */
    public void set_type(String _type) {
        this._type.set(_type);
    }

    /**
     * Get the email address of the user
     * @return the email address
     */
    public String get_emailaddress() {
        return _emailaddress.get();
    }

    /**
     * Set the email address of the user
     * @param _emailaddress the email address
     */
    public void set_emailaddress(String _emailaddress) {
        this._emailaddress.set(_emailaddress);
    }

    /**
     * Get the home address of the user
     * @return the home address
     */
    public String get_homeaddress() {
        return _homeaddress.get();
    }

    /**
     * Set the home address of the user
     * @param _homeaddress the home address
     */
    public void set_homeaddress(String _homeaddress) {
        this._homeaddress.set(_homeaddress);
    }

    /**
     * Get the company of the user
     * @return the company the user works for
     */
    public String get_company() {
        return _company.get();
    }

    /**
     * Set the company of the user
     * @param _company the company the user works for
     */
    public void set_company(String _company) {
        this._company.set(_company);
    }

    /**
     * Get the job title of the user
     * @return the user's job title
     */
    public String get_jobtitle() {
        return _jobtitle.get();
    }

    /**
     * Set the user's job title
     * @param _jobtitle the user's job title
     */
    public void set_jobtitle(String _jobtitle) {
        this._jobtitle.set(_jobtitle);
    }

    /**
     * Get the department the user works for
     * @return the department of the user
     */
    public String get_department() {
        return _department.get();
    }

    /**
     * Set the department of the user
     * @param _department the user's department
     */
    public void set_department(String _department) {
        this._department.set(_department);
    }







}
