package com.example.eventir.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Events {
        public String attraction;
        public String date;
        public String imageUrl;
        public String genre;
        public String venue;

        public Events() {
        }

        public static Events fromJson(JSONObject jsonObject) throws JSONException {
            Events event = new Events();

            JSONObject venueObject = jsonObject.getJSONObject("_embedded");
            JSONArray venueArray = venueObject.getJSONArray("venues");
            venueObject = venueArray.getJSONObject(0);
            event.venue = venueObject.getString("name");

            event.attraction = jsonObject.getString("name");

            JSONArray jsonArray = jsonObject.getJSONArray("images");
            JSONObject image = jsonArray.getJSONObject(0);
            event.imageUrl = image.getString("url");

            JSONObject dateObject = jsonObject.getJSONObject("dates");
            dateObject = dateObject.getJSONObject("start");
            event.date = dateObject.getString("localDate");

            JSONArray genreArray = jsonObject.getJSONArray("classifications");
            JSONObject genreObject = genreArray.getJSONObject(0);
            genreObject = genreObject.getJSONObject("segment");
            event.genre = genreObject.getString("name");

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
