package com.dev.liamgens.quickbin.objects;

/**
 * Created by liamgensel on 9/16/16.
 */
public class Profile {

    private String _displayName, _profileImg, _levelTitle;
    private int _level;

    public Profile(String displayName, int level){
        _displayName = displayName;
        _level = level;
        set_level_title();
    }

    public Profile(String displayName, String profileImg, int level){
        _displayName = displayName;
        _profileImg = profileImg;
        _level = level;
        set_level_title();

    }

    public String get_displayName() {
        return _displayName;
    }

    public void set_displayName(String _displayName) {
        this._displayName = _displayName;
    }

    public String get_profileImg() {
        return _profileImg;
    }

    public void set_profileImg(String _profileImg) {
        this._profileImg = _profileImg;
    }

    public int get_level() {
        return _level;
    }

    public void set_level(int _level) {
        this._level = _level;
    }

    public void set_level_title(){
        int level = get_level();
        if(level >= 1 && level < 5){ _levelTitle = "Trash"; }
        else if(level >= 5 && level < 10){ _levelTitle = "Compost"; }
        else if(level >= 10){ _levelTitle = "Renewable"; }
    }
    public String get_levelTitle() {
        return _levelTitle;
    }
}
