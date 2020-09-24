package com.example.myhackpage;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ListAdapter;

//import com.google.android.gms.appindexing.Action;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static java.lang.Long.parseLong;

public class MainActivity extends AppCompatActivity {

    Button seventh;
    Button eighth;
    Button ninth;
    int dayOfWeek;
    public static TextView name;
    public static TextView description;
    public static TextView locations;
    public static TextView header;
    public ListView listView;
    public ListAdapter adapter;
    private static String url = "https://api.hackillinois.org/event/";
    ArrayList<HashMap<String, String>> eventList;
    JSONArray events = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new JSONParse().execute();
        dayOfWeek = 5;
        eventList = new ArrayList<HashMap<String, String>>();
        seventh = (Button) findViewById(R.id.seventh);
        seventh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        listView = (ListView) findViewById(R.id.list);
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            name = (TextView)findViewById(R.id.name);
            description = (TextView)findViewById(R.id.description);
            locations = (TextView)findViewById(R.id.locations);
            header = (TextView)findViewById(R.id.header);
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            header.setText("HackThis 2020: August 7-15");
            try {
                // Getting JSON Array from URL
                events = json.getJSONArray("events");
                for(int i = 0; i < events.length(); i++){
                    JSONObject event = events.getJSONObject(i);
                    // Storing  JSON item in a Variable
                    String name = event.getString("name");
                    String description = event.getString("description");
                    JSONArray locations = event.getJSONArray("locations");
                    String locs = "";
                    for (int j = 0; j < locations.length(); j++) {
                        locs += locations.getString(j);
                        if (j < locations.length() - 1) {
                            locs += ", ";
                        }
                    }
                    if (locs.equals("")) {
                        locs = "Unavailable";
                    }
                    String date = event.getString("startTime");
                    Date currentDate = new Date(parseLong(date));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentDate);
                    HashMap<String, String> map = new HashMap<String, String>();
//                     calendar.get(Calendar.DAY_OF_WEEK)
//                    System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
//                    if (calendar.get(Calendar.DAY_OF_WEEK) == dayOfWeek) {
                        map.put("Name", name);
                        map.put("Description", "Description: " + description);
                        map.put("Locations", "Locations: " + locs);
                        eventList.add(map);
                    listView = (ListView)findViewById(R.id.list);
                    SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, eventList,
                            R.layout.event_display,
                            new String[] { "Name","Description", "Locations" }, new int[] {
                            R.id.name,R.id.description, R.id.locations});
                    listView.setAdapter(adapter);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void chooseDay(int day) {

        }
    }


}