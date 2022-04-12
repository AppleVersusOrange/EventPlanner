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
                .applicationId("maanf6quU5aSI6nhP7oOmVRuLDjfOGgG2FvikfMi")
                .clientKey("vDMN5vBlN87JGWLhMnNxRVpAgg87dGtCZCfPpiAO")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
