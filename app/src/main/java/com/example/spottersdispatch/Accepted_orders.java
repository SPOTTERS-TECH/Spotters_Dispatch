package com.example.spottersdispatch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
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

public class Accepted_orders extends AppCompatActivity implements Accepted_Adapter.onItemClickListener {
    RecyclerView recyclerView2;
    Accepted_Adapter adapter2;
    ImageView no_order;
    String phonen, fid, fnamen, lnamen, status, rider_name, company_id;
    private ArrayList<Product> userListt = new ArrayList<>();
    final static String load_items_accepted = "https://spotters.tech/dispatch-it/android/accepted_order_track.php";

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_ID = "id";
    private static final String KEY_STATUS = "status";
    private static final String KEY_COMPANY_ID = "company_id";
    final Loadingdialog loadingdialog = new Loadingdialog(Accepted_orders.this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_orders);

        loadingdialog.showLoadingDialog();
        no_order = findViewById(R.id.no_order);

        sharedPreferences = this.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        phonen = sharedPreferences.getString(KEY_PHONE, null);
        fnamen = sharedPreferences.getString(KEY_FNAME, null);
        lnamen = sharedPreferences.getString(KEY_LNAME, null);
        status = sharedPreferences.getString(KEY_STATUS, null);
        fid = sharedPreferences.getString(KEY_ID, null);
        company_id = sharedPreferences.getString(KEY_COMPANY_ID, null);
        rider_name = fnamen + " " + lnamen;

        //  RECYCLERVIEW2
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext());
        recyclerView2.setLayoutManager(layoutManager2);
        layoutManager2.setOrientation(RecyclerView.VERTICAL);
        recyclerView2.setHasFixedSize(true);

        load_items_accepted();

    }

    private void load_items_accepted() {
        String id = fid;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, load_items_accepted, new Response.Listener<String>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String package_name = object.getString("package_name").trim();
                        String order_id = object.getString("order_id").trim();
                        String rec_name = object.getString("rec_name").trim();
                        String rec_phone = object.getString("rec_phone").trim();
                        String rec_address = object.getString("rec_address").trim();
                        String destination = object.getString("destination").trim();
                        String package_weight = object.getString("package_weight").trim();
                        String receiver_name = object.getString("receiver_name").trim();
                        String receiver_phone = object.getString("receiver_phone").trim();
                        String statusofitem = object.getString("status").trim();

                        Product product = new Product(order_id, rec_name, rec_phone, rec_address, package_name, package_weight, receiver_name, receiver_phone, destination, statusofitem);

                        userListt.add(0, product);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadingdialog.dismissDialog();
                            }
                        }, 1000);
                    }


                    adapter2 = new Accepted_Adapter(Accepted_orders.this, userListt);
                    recyclerView2.setAdapter(adapter2);
                    adapter2.notifyDataSetChanged();
                    int count = 0;
                    if (adapter2 != null) {
                        count = adapter2.getItemCount();
                        String counts = String.valueOf(count);
                        if (counts.equals("0")) {
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loadingdialog.dismissDialog();
                                }
                            }, 1000);
                            no_order.setVisibility(View.VISIBLE);
                        }
                    }

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
                params.put("rider_id", fid);
                params.put("status", "Accepted");
                params.put("created_by_id", company_id);

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
    public void onItemClickproduct(Product product) {
        Intent intent = new Intent(getApplicationContext(), order_details.class);
        intent.putExtra("id", product.getOrder_id());
        intent.putExtra("sender_name", product.getReceipient_name());
        intent.putExtra("sender_phone", product.getReceipient_Phone());
        intent.putExtra("add", product.getReciepient_Address());
        intent.putExtra("destination", product.getDestination());
        intent.putExtra("package_name", product.getPackage_name());
        intent.putExtra("package_weight", product.getPackage_weight());
        intent.putExtra("receiver_name", product.getReceiver_name());
        intent.putExtra("receiver_phone", product.getReceiver_phone());
        intent.putExtra("statusofitem", product.getStatusoforder());
        startActivity(intent);
    }
}