package com.example.toll_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Login extends AppCompatActivity {

    MaterialEditText email, password;
    Button btn_login;
    static  String txt_email,txt_password;

    final String url_Login = "https://tollpay.herokuapp.com/authentication/login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login Page");

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_email=email.getText().toString();
                txt_password=password.getText().toString();

                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    Toast.makeText(Login.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else{
                    login();

                }
            }
        });


    }

    public void login() {

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("email",txt_email);
            jsonBody.put("password",txt_password);
            jsonBody.put("token",-1);


            final String requestBody = jsonBody.toString();


            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_Login,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println("wertt");
                           System.out.println(response);

                           DtoObjects.loginDto = new Gson().fromJson(response,LoginDto.class);
                            System.out.println(DtoObjects.loginDto.getEmail());
                            Intent intent =new Intent(Login.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                @Override

                public void onErrorResponse(VolleyError error) {
                    System.out.println("error");
                    Toast.makeText(Login.this, "Details not matched",Toast.LENGTH_SHORT).show();
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
        catch (Exception e){
            e.printStackTrace();
        }
    }


}