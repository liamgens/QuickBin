package com.dev.liamgens.quickbin.objects;

/**
 * Created by liamgensel on 9/16/16.
 */
public class Bin {

    private int _binType, _verifyCounter;
    private String _user, _title, _description, _binPicture, _date;
    private double _latitude, _longitude;

    public Bin(int binType, String user, String title, String description, double longitude, double latitude,
               int verifyCounter, String binPicture, String date){
        _binType = binType;
        _user = user;
        _verifyCounter = verifyCounter;
        _title = title;
        _description = description;
        _latitude = latitude;
        _longitude = longitude;
        _binPicture = binPicture;
        _date = date;

    }

    public int get_binType() {
        return _binType;
    }

    public void set_binType(int _binType) {
        this._binType = _binType;
    }

    public int get_verifyCounter() {
        return _verifyCounter;
    }

    public void set_verifyCounter(int _verifyCounter) {
        this._verifyCounter = _verifyCounter;
    }

    public String get_user() {
        return _user;
    }

    public void set_user(String _user) {
        this._user = _user;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_binPicture() {
        return _binPicture;
    }

    public void set_binPicture(String _binPicture) {
        this._binPicture = _binPicture;
    }

    public double get_latitude() {
        return _latitude;
    }

    public void set_latitude(double _latitude) {
        this._latitude = _latitude;
    }

    public double get_longitude() {
        return _longitude;
    }

    public void set_longitude(double _longitude) {
        this._longitude = _longitude;
    }

    public void set_location(double _latitude, double _longitude){
        this._latitude = _latitude;
        this._longitude = _longitude;
    }

    public double[] get_location(double _latitude, double _longitude) {
        return new double[] {get_latitude(),get_longitude()};
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }
}

