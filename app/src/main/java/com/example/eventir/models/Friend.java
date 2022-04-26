package com.example.eventir.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("User")
public class Friend extends ParseObject {
    public static final String KEY_USERNAME = "username";
    public static final String KEY_IMAGE = "profilePicture";
    public static final String KEY_CREATED = "createdAt";

    public String getName(){ return getString(KEY_USERNAME); }
    public void setName(String username){ put(KEY_USERNAME, username); }

    public ParseFile getImage(){return getParseFile(KEY_IMAGE);}
    public void setImage(ParseFile parseFile){ put(KEY_IMAGE, parseFile);}
}
