package com.example.spottersdispatch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class history extends AppCompatActivity implements history_RecyclerAdapter.ItemClickListener {

    RecyclerView recyclerView2;
    history_RecyclerAdapter adapter2;
    ImageView backbtn;
    private ArrayList<history_Product> userListt = new ArrayList<>();
    final static String load_items = "https://spotters.tech/dispatch-it/android/history_request.php";
    String fnamen,lnamen,phonen,fid;
    String rider_name;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_ID = "id";

    final Loadingdialog loadingdialog = new Loadingdialog(history.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        loadingdialog.showLoadingDialog();
        backbtn = findViewById(R.id.backbtn);

        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        sharedPreferences = this.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        phonen = sharedPreferences.getString(KEY_PHONE, null);
        fnamen = sharedPreferences.getString(KEY_FNAME, null);
        lnamen = sharedPreferences.getString(KEY_LNAME, null);
        fid = sharedPreferences.getString(KEY_ID, null);

        build();


        System.out.println(fid);



        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void build() {
        String id = fid;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, load_items, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String distance = object.getString("distance").trim();
                        String order_id = object.getString("order_id").trim();
                        String rec_name = object.getString("rec_name").trim();
                        String rec_phone = object.getString("rec_phone").trim();
                        String rec_address = object.getString("rec_address").trim();
                        String destination = object.getString("destination").trim();

                        history_Product product = new history_Product(rec_name, destination, order_id, rec_phone, rec_address, distance);


                        userListt.add(0, product);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadingdialog.dismissDialog();
                            }
                        }, 1000);
                    }
                    adapter2 = new history_RecyclerAdapter(userListt,history.this);
                    recyclerView2.setAdapter(adapter2);
                    adapter2.notifyDataSetChanged();

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
                params.put("status", "Completed");
                params.put("rider_id",fid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onItemClick(history_Product product) {
        Intent intent = new Intent(getApplicationContext(), History_report.class);
        intent.putExtra("order_id", product.getOrder_id());
        intent.putExtra("name", product.getRec_name());
        intent.putExtra("phone", product.getPhone());
        intent.putExtra("add", product.getRec_address());
        intent.putExtra("destination",product.getDestination());
        intent.putExtra("distance",product.getDistance());
        startActivity(intent);

    }
}