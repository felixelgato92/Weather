package com.example.blink.whether;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {



    public SparseArray<String> cities = new SparseArray<>();//{"San Francisco, CA", "New York, NY", "Salt Lake City, UT"};
    private String myApiKey = "b6907d289e10d714a6e88b30761fae22";
    private ListView listView;

    private String sampleResponse = "{\"coord\":{\"lon\":145.77,\"lat\":-16.92},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"base\":\"stations\",\"main\":{\"temp\":300.15,\"pressure\":1007,\"humidity\":74,\"temp_min\":300.15,\"temp_max\":300.15},\"visibility\":10000,\"wind\":{\"speed\":3.6,\"deg\":160},\"clouds\":{\"all\":40},\"dt\":1485790200,\"sys\":{\"type\":1,\"id\":8166,\"message\":0.2064,\"country\":\"AU\",\"sunrise\":1485720272,\"sunset\":1485766550},\"id\":2172797,\"name\":\"Cairns\",\"cod\":200}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cities.put(5780993, "Salt Lake City" );
        cities.put(5391959, "San Francisco" );
        cities.put(5128638, "New York" );
    }

    @Override
    protected void onStart() {
        super.onStart();
        listView = (ListView)findViewById(R.id.listView_cities) ;
        try {
            updateCities();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateCities() throws ExecutionException, InterruptedException {
        List<RowItem> rows = new ArrayList<>();
        for (int i = 0; i < cities.size(); i++) {
            int key = cities.keyAt(i);
            String jsonResponse = new CityTask().execute(key).get();
            if (jsonResponse != null) {
                String cityName = getJsonString(jsonResponse, "name");
                String cityTemperature = getJsonString(jsonResponse, "main:temp");
                String iconName = getJsonString(jsonResponse, ":0:weather:icon");
                String lowTemperature = getJsonString(jsonResponse, "main:temp_min");
                String highTemperature = getJsonString(jsonResponse, "main:temp_max");
                String chanceOfPrecipitation = getJsonString(jsonResponse, "clouds:all");
                String windSpeed = getJsonString(jsonResponse, "wind:speed");
                String humidity = getJsonString(jsonResponse, "main:humidity");

                Drawable drawable = new IconTask().execute(iconName).get();//getIconFromUrl(iconName);
                RowItem rowItem = new RowItem(drawable, cityName, cityTemperature);
                rowItem.setLowTemperature(lowTemperature);
                rowItem.setHighTemperature(highTemperature);
                rowItem.setChanceOfPrecipitation(chanceOfPrecipitation);
                rowItem.setWindSpeed(windSpeed);
                rowItem.setHumidity(humidity);

                rows.add(rowItem);
            }

            // get the object by the key.
        }
        IconArrayAdapter citiesArray = new IconArrayAdapter(this, R.layout.whether_list_row, rows);
        listView.setAdapter(citiesArray);

    }

    private String getJsonString(String jsonResponse, String str) {
        String value = "";
        try {
            String[] path = str.split(":");

            JSONObject obj = new JSONObject(jsonResponse);
            for (int i = 0; i < (path.length - 1) ; i++) {
                if (path[i].equals("")){
                    JSONArray array = obj.getJSONArray(path[i + 2]);
                    i++;
                    obj = array.getJSONObject(Integer.parseInt(path[i]));
                    i++;
                    continue;
                }
                obj = obj.getJSONObject(path[i]);
            }
            value = obj.getString(path[path.length - 1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return value;
    }

    private class CityTask extends AsyncTask<Integer, Void, String>{

        @Override
        protected String doInBackground(Integer... integers) {
            //TODO samples should be replaced, but there is an invalid API issue that it's not resolve yet
//            http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22
            String apiCall = "http://openweathermap.org/data/2.5/weather?id=" + integers[0] + "&appid=" + myApiKey;
            try {
                // Create a URL indicating where the server is running, and which
                // web API operation we want to call
                URL url = new URL(apiCall);

                // Start constructing our HTTP request
                HttpURLConnection http = (HttpURLConnection)url.openConnection();

                // Specify that we are sending an HTTP GET request
                http.setRequestMethod("GET");
                // Indicate that this request will not contain an HTTP request body
                http.setDoOutput(false);

                // Connect to the server and send the HTTP request
                http.connect();

                // By the time we get here, the HTTP response has been received from the server.
                // Check to make sure that the HTTP response from the server contains a 200
                // status code, which means "success".  Treat anything else as a failure.
                if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    // Get the input stream containing the HTTP response body
                    InputStream respBody = http.getInputStream();
                    // Extract JSON data from the HTTP response body
                    String respData = readString(respBody);
                    // Display the JSON data returned from the server
                    Log.e("Main Yo!", respData);
                    return respData;
                }
                else {
                    // The HTTP response status code indicates an error
                    // occurred, so print out the message from the HTTP response
                    Log.e("ERROR: ",  http.getResponseMessage());
                }
            }
            catch (IOException e) {
                // An exception was thrown, so display the exception's stack trace
                Log.e("Main yo!", "OOOOOHHHH NOOOOO!!!! " + e.getMessage());
                e.printStackTrace();
            }

            return null;
        }
    }

    /*
        The readString method shows how to read a String from an InputStream.
    */
    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    /*
        The writeString method shows how to write a String to an OutputStream.
    */
    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    private class IconTask extends AsyncTask<String, Void, Drawable>{

        @Override
        protected Drawable doInBackground(String... strings) {
            String url = "http://openweathermap.org/img/w/" + strings[0] + ".png";
            try {
                InputStream is = (InputStream) new URL(url).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                return d;
            } catch (Exception e) {
                Log.e("Main yo!", "Exception while loading icon" + e.getMessage());
                return null;
            }
        }
    }
}


