package com.example.myfinalproject3000;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText editTextName;
    Button btnSearch;
    ImageView imageView;
    TextView tvTitle, tvYear, tvActor, tvCounrty, tvPlot;
    RequestQueue requestQueue;
    String url;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editName);
        btnSearch = findViewById(R.id.btnSearch);
        imageView = findViewById(R.id.imageView);
        tvTitle = findViewById(R.id.tvTitle);
        tvYear = findViewById(R.id.tvYear);
        tvActor = findViewById(R.id.tvActor);
        tvCounrty = findViewById(R.id.tvCountry);
        tvPlot = findViewById(R.id.tvPlot);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseJsonData();
            }
        });
        parseJsonData();


    }


    private void parseJsonData() {
        requestQueue = Volley.newRequestQueue( this);
        name = editTextName.getText().toString();
        url="http://www.omdbapi.com/?t="+name+"&plot=full&apikey=61bf0ecd";

        StringRequest request = new StringRequest(Request.Method.GET, url, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            String title = object.getString("Title");
                            String year = object.getString("Year");
                            String actor = object.getString("Actor");
                            String country = object.getString("Country");
                            String plot = object.getString("Plot");
                            String image = object.getString("Poster");
                            Glide.with(MainActivity.this).load(image).into(imageView);
                            tvTitle.setText(title);
                            tvYear.setText(year);
                            tvActor.setText(actor);
                            tvCounrty.setText(country);
                            tvPlot.setText(plot);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(request);
    }






    public void logout (View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
        finish();
    }
}
