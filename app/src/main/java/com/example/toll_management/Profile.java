package com.example.toll_management;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.BreakIterator;

public class Profile extends AppCompatActivity {
     EditText txt_input;
    TextView name, email, mobile, vechileN, vechileT,rupee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


      name=findViewById(R.id.nameid);
      name.setText(DtoObjects.profileDto.getName());
      email =findViewById(R.id.emailid);
      email.setText(DtoObjects.profileDto.getEmail());
      mobile= findViewById(R.id.mobile);
      mobile.setText(DtoObjects.profileDto.getMobileNum());
      vechileN=findViewById(R.id.vechile_number);
      vechileN.setText(DtoObjects.profileDto.getVehicleNumber());
      vechileT=findViewById(R.id.vechile_type);
      vechileT.setText(DtoObjects.profileDto.getVehicleType());
      rupee=findViewById(R.id.balance);
      rupee.setText(DtoObjects.profileDto.getWalletAmount()+"");

    }

    public void btn_adder(View view) {
        System.out.println("start");
        final AlertDialog.Builder alert = new AlertDialog.Builder(Profile.this);
        View mView = getLayoutInflater().inflate(R.layout.custom_dialog, null);

         txt_input = (EditText) mView.findViewById(R.id.txt_input);
        Button btn_add = (Button) mView.findViewById(R.id.btn_add);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        System.out.println("startbf");


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addAmount();
                    alertDialog.dismiss();
                } catch (Exception e) {

                }

            }

        });
        alertDialog.show();

    }

    public void signOut(View view) throws JSONException {
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("token",DtoObjects.loginDto.getToken());
        System.out.println(DtoObjects.loginDto.getToken());
        final String requestBody = jsonBody.toString();
        String url ="https://tollpay.herokuapp.com/authentication/logout";

        RequestQueue queue = Volley.newRequestQueue( this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        if(response.equals("Logout Successful")){
                            Intent intent =new Intent(Profile.this,StartActivity.class);
                            startActivity(intent);
                        }
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
        public void addAmount() throws JSONException {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("amount",Double.parseDouble(String.valueOf(txt_input.getText())));
            jsonBody.put("token",DtoObjects.loginDto.getToken());
            System.out.println(DtoObjects.loginDto.getToken());
            final String requestBody = jsonBody.toString();
            String url ="https://tollpay.herokuapp.com/addAmount";

            RequestQueue queue = Volley.newRequestQueue( this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            if(response.equals("Amount added Successfully")){
                            DtoObjects.profileDto.setWalletAmount(DtoObjects.profileDto.getWalletAmount()+ Double.parseDouble(String.valueOf(txt_input.getText())));
                            rupee.setText(DtoObjects.profileDto.getWalletAmount()+"");
                            }
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
