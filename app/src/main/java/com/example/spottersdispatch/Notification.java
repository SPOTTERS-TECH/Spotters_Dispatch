package com.example.spottersdispatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class Notification extends AppCompatActivity implements Notification_RecyclerAdapter.ItemClickListener {
    RecyclerView recyclerView;
    Notification_RecyclerAdapter adapter;
    String fid;
    private ArrayList<complaint_Product> userList = new ArrayList<>();


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

        adapter = new Notification_RecyclerAdapter(this,userList);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        build();

    }

    private void build() {

        userList.add(new complaint_Product("information to all staff","2022-04-06","we will be having a app maintainance from around 12am to 8am on thursday july 2022,you will not be able to access the application during the maintainance period. Thank you!"));
        userList.add(new complaint_Product("application upgrade ","2022-04-01","we will be having a app maintainance from around 10pm to 8am on thursday july 2022,you will not be able to access the application during the maintainance period. Thank you!"));
        userList.add(new complaint_Product("information to all staff","2022-04-06","we will be having a app maintainance from around 12am to 8am on thursday july 2022,you will not be able to access the application during the maintainance period. Thank you!"));
        userList.add(new complaint_Product("application upgrade ","2022-04-01","we will be having a app maintainance from around 10am to 8am on thursday july 2022,you will not be able to access the application during the maintainance period. Thank you!"));

    }

//    private void load_items() {
//        String id = fid;
//
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, load_items, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String success = jsonObject.getString("success");
//                    JSONArray jsonArray = jsonObject.getJSONArray("read");
//                    if (success.equals("1")) {
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject object = jsonArray.getJSONObject(i);
//                            String title = object.getString("order_id").trim();
//                            String date = object.getString("rec_name").trim();
//                            String subtext = object.getString("rec_phone").trim();
//
//
//                            complaint_Product product = new complaint_Product(title,date,subtext);
//                            userList.clear();
//                            userList.add(product);
//
//                        }
//
//                    }
//                    adapter = new complaint_RecyclerAdapter(Notification.this,userList);
//                    recyclerView.setAdapter(adapter);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(Notification.this, "error"+ e+toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(Notification.this, "Error" +error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> params = new HashMap<>();
//                params.put("id", id);
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
//    }

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