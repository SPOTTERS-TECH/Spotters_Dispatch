package com.example.spottersdispatch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class order_details extends AppCompatActivity {

    TextView sender_name, receiver_name, sender_address, sender_destination, sender_phone, package_type, sender_order_id;
    Button accept_order;
    final static String url_updatestatus = "https://spotters.tech/dispatch_app/android/accept_order_status_change.php";
    String phonen, fid, fnamen, lnamen, status, rider_name;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_ID = "id";
    private static final String KEY_STATUS = "status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        sharedPreferences = this.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        phonen = sharedPreferences.getString(KEY_PHONE, null);
        fnamen = sharedPreferences.getString(KEY_FNAME, null);
        lnamen = sharedPreferences.getString(KEY_LNAME, null);
        status = sharedPreferences.getString(KEY_STATUS, null);
        fid = sharedPreferences.getString(KEY_ID, null);
        rider_name = fnamen + " " + lnamen;

        sender_name = findViewById(R.id.sender_name);
        receiver_name = findViewById(R.id.receiver_name);
        sender_address = findViewById(R.id.sender_address);
        sender_destination = findViewById(R.id.sender_destination);
        sender_phone = findViewById(R.id.sender_phone);
        package_type = findViewById(R.id.package_type);
        sender_order_id = findViewById(R.id.Sender_order_id);
        accept_order = findViewById(R.id.acceptorder);

        String s_name = getIntent().getStringExtra("sender_name");
        String S_phone = getIntent().getStringExtra("sender_phone");
        String S_address = getIntent().getStringExtra("add");
        String s_destination = getIntent().getStringExtra("destination");
        String p_type = getIntent().getStringExtra("package_name");
        String package_weight = getIntent().getStringExtra("package_weight");
        String r_name = getIntent().getStringExtra("receiver_name");
        String receiver_phone = getIntent().getStringExtra("receiver_phone");
        String order_id = getIntent().getStringExtra("id");
        String statusofitem = getIntent().getStringExtra("statusofitem");

        if (statusofitem.equals("Accepted")) {
            accept_order.setVisibility(View.GONE);
        }

        sender_name.setText(s_name);
        receiver_name.setText(r_name);
        sender_address.setText(S_address);
        sender_destination.setText(s_destination);
        sender_phone.setText(S_phone);
        package_type.setText(p_type);
        sender_order_id.setText(order_id);

        acceptorderclick(order_id, s_name);

    }

    private void acceptorderclick(String orderid, String name) {
        accept_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_updatestatus, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                Toast.makeText(getApplicationContext(), "You have accepted " + name + " request", Toast.LENGTH_LONG).show();
                                // startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                //onRestart();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("status", "Accepted");
                        params.put("rider_id", fid);
                        params.put("order_id", orderid);

                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);

            }
        });
    }

    public void backbtn(View view) {
        onBackPressed();
    }
}