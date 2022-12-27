package com.example.spottersdispatch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter2.onItemClickListener {

    BottomSheetDialog dialog;
    private int selectedTab = 1;
    RecyclerView recyclerView2;
    RecyclerAdapter2 adapter2;
    String fnamen, lnamen, phonen, status;
    String rider_name, fid;
    TextView today_orders, username, user_phone, txtonline;
    String avalability = "Offline";
    ImageView imgonline, refresh;

    private ArrayList<Product> userListt = new ArrayList<>();
    final static String load_items_accepted = "https://spotters.tech/dispatch-it/android/order_request.php";
    final static String url_updatestatus = "https://spotters.tech/dispatch-it/android/status_change.php";

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_ID = "id";
    private static final String KEY_STATUS = "status";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (TextView) findViewById(R.id.username);
        txtonline = (TextView) findViewById(R.id.txtonline);
        user_phone = (TextView) findViewById(R.id.user_phone);
        imgonline = (ImageView) findViewById(R.id.offlineimg);
        today_orders = findViewById(R.id.checkorder);
        refresh = (ImageView) findViewById(R.id.refresh);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });


        sharedPreferences = this.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        phonen = sharedPreferences.getString(KEY_PHONE, null);
        fnamen = sharedPreferences.getString(KEY_FNAME, null);
        lnamen = sharedPreferences.getString(KEY_LNAME, null);
        status = sharedPreferences.getString(KEY_STATUS, null);
        fid = sharedPreferences.getString(KEY_ID, null);
        rider_name = fnamen + " " + lnamen;
        if (phonen != null && fnamen != null && fid != null) {
            username.setText("Hello, " + lnamen);
            user_phone.setText(phonen);
            txtonline.setText(status);
            String checkingimgonline = txtonline.getText().toString();
            if (checkingimgonline.equals("Online")) {
                imgonline.setBackgroundResource(R.drawable.ic_baseline_visibility_24);
                txtonline.setTextColor(getResources().getColor(R.color.green));
            }
        }

        LinearLayout home_lay = findViewById(R.id.home_lay);
        LinearLayout setting_lay = findViewById(R.id.setting_lay);
        LinearLayout request_lay = findViewById(R.id.request_lay);

        TextView home_txt = findViewById(R.id.hometxt);
        TextView setting_txt = findViewById(R.id.setting_txt);
        TextView request_txt = findViewById(R.id.requesttxt);

        ImageView setting_img = findViewById(R.id.setting_img);
        ImageView home_img = findViewById(R.id.home_img);
        ImageView request_img = findViewById(R.id.request_img);
        dialog = new BottomSheetDialog(this);

        home_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedTab != 1) {

                    setting_txt.setVisibility(View.GONE);
                    request_txt.setVisibility(View.GONE);

                    setting_lay.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    request_lay.setBackgroundColor(getResources().getColor(android.R.color.transparent));


                    home_txt.setVisibility(View.VISIBLE);
                    home_lay.setBackgroundResource(R.drawable.nav_back);

                    ScaleAnimation animation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    animation.setDuration(200);
                    animation.setFillAfter(true);
                    home_lay.startAnimation(animation);

                    selectedTab = 1;
                }

            }
        });

        request_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedTab != 2) {

                    home_txt.setVisibility(View.GONE);
                    setting_txt.setVisibility(View.GONE);

                    setting_lay.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    home_lay.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    request_txt.setVisibility(View.VISIBLE);
                    request_lay.setBackgroundResource(R.drawable.nav_back);

                    ScaleAnimation animation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    animation.setDuration(200);
                    animation.setFillAfter(true);
                    request_lay.startAnimation(animation);

                    selectedTab = 2;
                }
                startActivity(new Intent(getApplicationContext(), Accepted_orders.class));


            }
        });

        setting_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedTab != 3) {

                    // home_txt.setVisibility(View.GONE);

                    //home_lay.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    // setting_txt.setVisibility(View.VISIBLE);
                    // setting_lay.setBackgroundResource(R.drawable.nav_back);

                    ScaleAnimation animation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    animation.setDuration(200);
                    animation.setFillAfter(true);
                    setting_lay.startAnimation(animation);

                    selectedTab = 3;

                }

                createdialog();

            }
        });

        //  RECYCLERVIEW2
        recyclerView2 = (RecyclerView) findViewById(R.id.recyclerview2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext());
        recyclerView2.setLayoutManager(layoutManager2);
        layoutManager2.setOrientation(RecyclerView.VERTICAL);
        recyclerView2.setHasFixedSize(true);

        load_items_accepted();


        updatestatus();


        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    private void updatestatus() {
        imgonline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (avalability.equals("Offline")) {
                    imgonline.setBackgroundResource(R.drawable.ic_baseline_visibility_24);
                    txtonline.setText("Online");
                    txtonline.setTextColor(getResources().getColor(R.color.green));
                    avalability = "Online";
                } else {
                    imgonline.setBackgroundResource(R.drawable.visibility_off);
                    txtonline.setText("Offline");
                    txtonline.setTextColor(getResources().getColor(R.color.red));
                    avalability = "Offline";
                }
                String checkonline = txtonline.getText().toString();
                System.out.println(checkonline);

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url_updatestatus, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                // Toast.makeText(getApplicationContext(), "You have accepted " + name + " request", Toast.LENGTH_LONG).show();
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
                        params.put("status", checkonline);
                        params.put("ID", fid);

                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);


            }
        });


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


                    }


                    adapter2 = new RecyclerAdapter2(MainActivity.this, userListt);
                    recyclerView2.setAdapter(adapter2);
                    adapter2.notifyDataSetChanged();
                    int count = 0;
                    if (adapter2 != null) {
                        count = adapter2.getItemCount();
                        String counts = String.valueOf(count);
                        today_orders.setText(counts + " total orders");
                        System.out.println("hey" + count);
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
                params.put("status", "Pending");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void createdialog() {
        View view = getLayoutInflater().inflate(R.layout.settings_dialog, null, false);
        LinearLayout history = (LinearLayout) view.findViewById(R.id.history_lay);
        LinearLayout edit_password = (LinearLayout) view.findViewById(R.id.password_edit_lay);
        LinearLayout complaint = (LinearLayout) view.findViewById(R.id.complaint_lay);
        LinearLayout terms = (LinearLayout) view.findViewById(R.id.terms_lay);
        LinearLayout policies = (LinearLayout) view.findViewById(R.id.policies_lay);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout abouut = (LinearLayout) view.findViewById(R.id.about_lay);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout profile = (LinearLayout) view.findViewById(R.id.profile_lay);

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), com.example.spottersdispatch.history.class));
            }
        });

        abouut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), com.example.spottersdispatch.About.class));
            }
        });


        policies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), com.example.spottersdispatch.Privacy_policy.class));
            }
        });

        complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), com.example.spottersdispatch.Complaint.class));
            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), com.example.spottersdispatch.Terms_of_service.class));
            }
        });
        edit_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), com.example.spottersdispatch.Security.class));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), com.example.spottersdispatch.Edit_profile.class));
            }
        });
        dialog.setContentView(view);
        dialog.show();
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

    public void gotonotification(View view) {
        startActivity(new Intent(getApplicationContext(), Notification.class));
    }

}