package com.russrezepov.weatherappv4;

/***** Weather App WeatherMap.org API *****/
/** 1 - Design the Layout
 *  2 - Declare the widgets
 *  3 - Json and Website communication strategy
 *  4 - AsyncTasks to receive the Json Data
 *  5 - Create Json objects and Clarify Data
 *  6 - Test QA
 *  7 - WeatherMap.org API_KEY = 201b68fb374f8735ee7faf2c8bc4c8c5
 *  http://api.openweathermap.org/data/2.5/weather?q=London&appid=201b68fb374f8735ee7faf2c8bc4c8c5
 *  NEXT
 *  8 - Get the city name from the cityField(enterCity)
 *  9 - Execute the url above to pull the right data from WeatherMap.org website via API
 *  10 - Convert the pulled from WeatherMap.org Json data to text and make it readable

 */

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView t1_temp, t2_city, t3_description, t4_date;
    Button btnSubmit;
    EditText cityName;

    static final String API_KEY="201b68fb374f8735ee7faf2c8bc4c8c5";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1_temp = (TextView) findViewById(R.id.degView);
        t2_city = (TextView) findViewById(R.id.cityView);
        t3_description = (TextView) findViewById(R.id.skyView);
        t4_date = (TextView) findViewById(R.id.dateView);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        cityName = (EditText) findViewById(R.id.cityName);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cityName.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please Enter City Name", Toast.LENGTH_SHORT).show();
                } else {
                    String enteredCity = cityName.getText().toString().trim();

                    find_weather(enteredCity);
                }
            }
        });//btnSubmit


    }//onCreate


    public void find_weather(String cname){

        String weatherURL="http://api.openweathermap.org/data/2.5/weather?q="+cname+"&appid="+API_KEY;

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, weatherURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONObject main_object = response.getJSONObject("main");
                    JSONArray array = response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);

                    String temp = String.valueOf(main_object.getDouble("temp"));
                    String description = object.getString("description");
                    String city = response.getString("name");

                  //  t1_temp.setText(temp);
                    t2_city.setText(city);
                    t3_description.setText(description);

                    Calendar calendar = Calendar.getInstance();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
                    String formatted_date = sdf.format(calendar.getTime());

                    t4_date.setText(formatted_date);

                    //converting the temperature
                    double temp_int = Double.parseDouble(temp);
                    double centi = (temp_int - 273.15);
                    centi = Math.round(centi);
                    int tempConverted = (int)centi;


                    t1_temp.setText(String.valueOf(tempConverted));

                } catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue vQueue = Volley.newRequestQueue(this);
        vQueue.add(jor);
    }//end of find_weather method

}

