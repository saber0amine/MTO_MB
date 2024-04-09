package com.example.meteo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    TextInputLayout textInputLayout ;
    EditText editTextVille  ;

    BottomNavigationView bottomNavigationView ;


    //private EditText editTextVille;
    private ListView listViewMeteo;
    //private ImageButton buttonOk;
    List<MeteoItem> data = new ArrayList<>();

    private MeteoListModel  model ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         listViewMeteo = findViewById(R.id.listViewMeteo);
         textInputLayout = findViewById(R.id.textInputLayout);
        editTextVille = textInputLayout.getEditText();
        AppCompatButton buttonOk = findViewById(R.id.buttonOk);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.action_settings)
                {
                    Toast.makeText(MainActivity.this, "Settings clicked", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this , "Profile clicked", Toast.LENGTH_LONG).show();
                }
                return true;
            }
            });


             model = new MeteoListModel(getApplicationContext(),R.layout.list_item_layout,data)  ;
        listViewMeteo.setAdapter(model);


        buttonOk.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                Log.i("MyLog","......");
                if (data.size()!=0){
                data.clear();
                model.notifyDataSetChanged();
                }
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String ville = editTextVille.getText().toString();
                Log.i("MyLog",ville);
                String url = "https://samples.openweathermap.org/data/2.5/forecast?q=" + ville + "&appid=657576bfb3a7710e32796c4080593e6f";
                Log.i("MyLog","avant request");

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

                    public void onResponse(String response) {
                        Log.i("MyLog","inside respose");

                        try {
                            Log.i("MyLog","----------------------------");
                            Log.i("MyLog","#####################RESPONS API#################"+response) ;
                             //List<MeteoItem> meteoItems = new ArrayList<>();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("list");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                MeteoItem meteoItem = new MeteoItem();
                                JSONObject d = jsonArray.getJSONObject(i);
                                Date date = new Date(d.getLong("dt") * 1000);
                                SimpleDateFormat sfd = new SimpleDateFormat("dd-MMM-yyyy'T'HH:mm");
                                String dateString = sfd.format(date);
                                JSONObject main = d.getJSONObject("main");
                                JSONArray weather = d.getJSONArray("weather");
                                int tempMin = (int) (main.getDouble("temp_min") - 273.15);
                                int tempMax = (int) (main.getDouble("temp_max") - 273.15);
                                int pression = main.getInt("pressure");
                                int humdity = main.getInt("humidity");
                                meteoItem.tempMin = tempMin;
                                meteoItem.tempMax = tempMax;
                                meteoItem.date = dateString;
                                meteoItem.humidite = humdity;
                                meteoItem.pression = pression;
                                meteoItem.image = weather.getJSONObject(0).getString("main");
                                Log.i("MyLog", "main valeur "+String.valueOf(main)) ;
                                Log.i("MyLog", "pression valeur "+String.valueOf(pression)) ;



                                //meteoItems.add(meteoItem);
                                data.add(meteoItem);

                                Log.i("MyLog", "MeteoItem created: " + meteoItem.toString());
                                Log.i("MyLog","data valeur "+data.toString()) ;


                                Log.i("MyLog", "Data list size after adding items: " + data.size());

                            }
                            Log.i("MyLog", "after Notified adapter of data set change");

                            Log.i("MyLog", "outside response Adapter count: " + model.getCount());

                            model.notifyDataSetChanged();




                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.i("myLog" , "connection problem") ;
                            }
                        } );

                queue.add(stringRequest);


            }
        });



            }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(item.getItemId() == R.id.action_settings)
        {
            Toast.makeText(MainActivity.this, "Settings clicked", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(MainActivity.this , "Profile clicked", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}



