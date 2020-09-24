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

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button click;
    public static TextView name;
    public static TextView description;
    public static TextView locations;
    public ListView listView;
    public ListAdapter adapter;
    private static String url = "https://api.hackillinois.org/event/";
    ArrayList<HashMap<String, String>> eventList;
    JSONArray events = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventList = new ArrayList<HashMap<String, String>>();
//        click = (Button) findViewById(R.id.button);
//
//        click.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new JSONParse().execute();
//            }
//        });

        new JSONParse().execute();

        listView = (ListView) findViewById(R.id.list);
//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override

        protected void onPreExecute() {
            super.onPreExecute();
            name = (TextView)findViewById(R.id.name);
            description = (TextView)findViewById(R.id.description);
            locations = (TextView)findViewById(R.id.locations);
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
                    // Adding value HashMap key => value
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("Name", "Event: " + name);
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
    }


}