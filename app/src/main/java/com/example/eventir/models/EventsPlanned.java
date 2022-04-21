package com.example.eventir.models;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Event")
public class EventsPlanned extends ParseObject {
    public static final String KEY_EVENT_TITLE = "Attraction";
    public static final String KEY_EVENT_DATE = "userdate";
    public static final String KEY_GENRE = "genre";
    public static final String KEY_OWNERID = "ownerID";

    //setters
    public void setUser(ParseUser user){
        put(KEY_OWNERID,user);
    }

    public void setAttraction(String attraction){
        put(KEY_EVENT_TITLE, attraction);      //put: associates key with description from Parser
    }

    public void setGenre(String genre){
        put(KEY_GENRE, genre);
    }
    //Warning when user inputs date then I am not sure if it will cause issues according to format of back4app
    public void setUserDate(String date){
        put(KEY_EVENT_DATE,date);
    }


    //getters
    public ParseUser getUser(){
        return getParseUser(KEY_OWNERID);
    }

    public String getAttraction(){
        return getString(KEY_EVENT_TITLE);
    }

    public String getGenre(){
        return getString(KEY_GENRE);
    }

    public String getUserDate(){
        return getString(KEY_EVENT_DATE);
    }




}
