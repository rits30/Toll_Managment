package com.example.toll_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    Button btn_profile, btn_prepayment , btn_automatic;
    TextView email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            email= findViewById(R.id.name);
        email.setText("Welcome" + "  " + DtoObjects.loginDto.getEmail() );

        btn_profile= (Button) findViewById(R.id.buttonProfile);
        btn_prepayment= findViewById(R.id.buttonPrePayment);
        btn_automatic= findViewById(R.id.buttonAutomatic);

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get api call with user token
                try {
                    showProfile();
                }
                catch (Exception e){

                }

               // ProfileDto profileDto = new ProfileDto("Ritesh","rporwal@gmail.com","9929828090","RJ06CA1210","Car",200.00);

            }
        });

        btn_prepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    checkLocationRange();
                }
                catch (Exception e){

                }
            }
        });

        btn_automatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Automatic Payment Successfully",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkLocationRange() throws JSONException {
        JSONObject jsonCoordinates = new JSONObject();
        jsonCoordinates.put("latitude",22.01475);
        jsonCoordinates.put("longitude",73.11543);
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("coordinates",jsonCoordinates);
        jsonBody.put("token",DtoObjects.loginDto.getToken());

        final String requestBody = jsonBody.toString();
        String url ="https://tollpay.herokuapp.com/gps/pre/coordinates";

// Request a string response from the provided URL.
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        Toast.makeText(MainActivity.this, "Payment Successfully",Toast.LENGTH_SHORT).show();
                        DtoObjects.rangeStatus = new Gson().fromJson(response,RangeStatus.class);
                        if(DtoObjects.rangeStatus.getTollCharges()<=DtoObjects.rangeStatus.getCurrentBalance()){
                        }
                        //Display notification to choose to pay the toll charges
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error");
            }

        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }


            }
        };


        queue.add(stringRequest);
    }


    public void showProfile() throws JSONException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("token",DtoObjects.loginDto.getToken());
        final String requestBody = jsonBody.toString();
        String url ="https://tollpay.herokuapp.com/profile";

// Request a string response from the provided URL.
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        System.out.println("System");
                        DtoObjects.profileDto = new Gson().fromJson(response,ProfileDto.class);
                        Intent intent =new Intent(MainActivity.this, Profile.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Profile Clicked",Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error");
            }

        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }


            }
        };


        queue.add(stringRequest);
    }

}
