package com.example.eventir.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Events {
        public String title;
        public String date;
        public String imageUrl;

        public Events() {
        }

        public static Events fromJson(JSONObject jsonObject) throws JSONException {
            Events event = new Events();

            event.title = jsonObject.getString("name");
            //event.date = jsonObject.getString("created_at");
            //event.imageUrl = jsonObject.getString("created_at");

            return event;
        }

        public static List<Events> fromJsonArray(JSONArray jsonArray) throws JSONException {
            List<Events> events = new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++){
                events.add(fromJson(jsonArray.getJSONObject(i)));
            }
            return events;
        }
}
