package com.example.eventir;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("ScheduleList")
public class ScheduleList extends ParseObject {
    public static final String KEY_OWNERID = "ownerID";
    public static final String KEY_OBJECTID = "objectID";               //optional for now: index of a schedule list
    public static final String KEY_SCHEDULENAME = "scheduleName";

    //getter for name
    public String getDescription(){
        return getString(KEY_SCHEDULENAME);
    }

    public void setDescription(String description){
        put(KEY_SCHEDULENAME, description);      //put: associates key with description from Parser
    }

    //setter and getter for user associated with each schedule list reminder
    public ParseUser getUser(){
        return getParseUser(KEY_OWNERID);
    }
    public void setUser(ParseUser user){
        put(KEY_OWNERID,user);
    }

}
