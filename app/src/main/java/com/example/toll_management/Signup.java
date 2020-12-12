package com.example.toll_management;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;



public class Signup extends AppCompatActivity {

    MaterialEditText name, vechile_number, phone_number, email, password, confirm_password;
    Button btn_register;
    static String txt_name, txt_vechile, txt_phone, txt_email, txt_password, txt_confirm,vehicle_type;


    final String url_Register = "https://tollpay.herokuapp.com/authentication/welcome";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().setTitle("Registration Page");

        name = findViewById(R.id.name);
        vechile_number = findViewById(R.id.vechile_number);
        phone_number = findViewById(R.id.phone_number);
        email = findViewById(R.id.email);

        password = findViewById(R.id.password);
        confirm_password = findViewById(R.id.confirm_password);
        btn_register = findViewById(R.id.btn_register);

        Spinner spinner = (Spinner) findViewById(R.id.vehicleTypes_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.vehicleTypes_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        vehicle_type = spinner.getSelectedItem().toString();
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_name = name.getText().toString();
                txt_vechile = vechile_number.getText().toString();
                txt_phone = phone_number.getText().toString();
                txt_email = email.getText().toString();
                txt_password = password.getText().toString();
                txt_confirm = confirm_password.getText().toString();


                if (TextUtils.isEmpty(txt_name) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_confirm) || TextUtils.isEmpty(txt_phone) || TextUtils.isEmpty(txt_vechile)) {
                    Toast.makeText(Signup.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else if (!txt_password.equals(txt_confirm)) {
                    Toast.makeText(Signup.this, "Password not matched", Toast.LENGTH_SHORT).show();
                } else if (txt_password.length() < 8) {
                    Toast.makeText(Signup.this, "Password must be at least 8 character", Toast.LENGTH_SHORT).show();
                } else if (txt_phone.length() != 10) {
                    Toast.makeText(Signup.this, "Phone number must be  10 digit", Toast.LENGTH_SHORT).show();

                } else {
                    register();
                }
            }
        });

    }

//    public void test() {
//        System.out.println("njewjjnjh");
//        RequestQueue queue = Volley.newRequestQueue(this);
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_Register,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        System.out.println(response);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                System.out.println("error");
//            }
//
//        });
//        queue.add(stringRequest);
//    }

    public void register() {

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name",txt_name);
            jsonBody.put("password",txt_password);
            jsonBody.put("email",txt_email);
            jsonBody.put("mobileNum",txt_phone);
            jsonBody.put("vehicleNumber",txt_vechile);
            jsonBody.put("vehicleType",vehicle_type);
            final String requestBody = jsonBody.toString();

            System.out.println("njewjjnjh");
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url_Register,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            startActivity(new Intent(Signup.this, StartActivity.class));
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
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };


        queue.add(stringRequest);
    }
        catch (Exception e){
            e.printStackTrace();
        }
}


 }