package com.example.eventir;

import android.app.Application;

// import com.example.eventir.models.x import models here
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    public void onCreate() {
        super.onCreate();

        //ParseObject.registerSubclass(model.class); replace model with particular model name, such as user.

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("wopR2672aSLGOKqYh6XkanTSRNTHeCCJcqFnXF1t")
                .clientKey("aYSRk3FYwhRPSue7a6lMBTSn3DNiCX4AeO4l7MBF")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}