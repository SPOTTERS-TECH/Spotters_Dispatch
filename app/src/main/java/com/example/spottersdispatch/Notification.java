package com.example.spottersdispatch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Notification extends AppCompatActivity implements Notification_RecyclerAdapter.ItemClickListener {
    RecyclerView recyclerView;
    Notification_RecyclerAdapter adapter;
    String fid;
    private ArrayList<complaint_Product> userList = new ArrayList<>();
    final static String load_items = "https://spotters.tech/dispatch-it/android/rider_notification.php";


    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String Phonen = sharedPreferences.getString(KEY_PHONE,null);
        String fnamen = sharedPreferences.getString(KEY_FNAME,null);
        fid = sharedPreferences.getString(KEY_ID,null);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        adapter = new Notification_RecyclerAdapter(this, userList);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        load_items();

    }


    private void load_items() {
        String id = fid;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, load_items, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String title = object.getString("title").trim();
                        String date = object.getString("date").trim();
                        String subtext = object.getString("message").trim();

                        complaint_Product product = new complaint_Product(title, date, subtext);

                        userList.add(0, product);
                    }


                    adapter = new Notification_RecyclerAdapter(Notification.this, userList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    int count = 0;

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "error" + e + toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getContext(), "error"+ error+toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void backbtn(View view) {
        onBackPressed();
    }

    @Override
    public void onItemClick(complaint_Product complaint_product) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(complaint_product.getTitle());
        builder.setMessage(complaint_product.getSub_text());
        builder.show();


    }
}