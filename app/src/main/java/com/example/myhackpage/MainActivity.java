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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import static java.lang.Long.parseLong;

public class MainActivity extends AppCompatActivity {

    Button seventh;
    Button eighth;
    Button ninth;
    Button tenth;
    Button eleventh;
    Button twelfth;
    Button thirteenth;
    Button fourteenth;
    Button fifteenth;
    int date;
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
        date = 0;
        eventList = new ArrayList<HashMap<String, String>>();
        seventh = (Button) findViewById(R.id.seventh);
        seventh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventList.clear();
                date = 7;
                new JSONParse().execute();
            }
        });

        eighth = (Button) findViewById(R.id.eighth);
        eighth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventList.clear();
                date = 8;
                new JSONParse().execute();
            }
        });

        ninth = (Button) findViewById(R.id.ninth);
        ninth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventList.clear();
                date = 9;
                new JSONParse().execute();
            }
        });

        tenth = (Button) findViewById(R.id.tenth);
        tenth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventList.clear();
                date = 10;
                new JSONParse().execute();
            }
        });
        eleventh = (Button) findViewById(R.id.eleventh);
        eleventh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventList.clear();
                date = 11;
                new JSONParse().execute();
            }
        });

        twelfth = (Button) findViewById(R.id.twelfth);
        twelfth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventList.clear();
                date = 12;
                new JSONParse().execute();
            }
        });

        thirteenth = (Button) findViewById(R.id.thirteenth);
        thirteenth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventList.clear();
                date = 13;
                new JSONParse().execute();
            }
        });


        fourteenth = (Button) findViewById(R.id.fourteenth);
        fourteenth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventList.clear();
                date = 14;
                new JSONParse().execute();
            }
        });


        fifteenth = (Button) findViewById(R.id.fifteenth);
        fifteenth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventList.clear();
                date = 15;
                new JSONParse().execute();
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
                for(int i = 0; i < events.length(); i++) {
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
                    String dateString = event.getString("startTime");
                    Date newDate = new Date(parseLong(dateString)*1000L);
                    // format of the date
                    SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
                    jdf.setTimeZone(TimeZone.getTimeZone("GMT-6"));
                    Calendar cal = jdf.getCalendar();
                    cal.setTime(newDate);
                    HashMap<String, String> map = new HashMap<String, String>();
                    if (cal.get(Calendar.DATE) == date) {
                        map.put("Name", name);
                        map.put("Description", "Description: " + description);
                        map.put("Locations", "Locations: " + locs);
                        eventList.add(map);
                    }
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