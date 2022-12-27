package com.example.spottersdispatch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Complaint extends AppCompatActivity {

    EditText complain;
    Button complain_btn;
    Spinner comp_category, issue_type;
    String comp_category_text, issue_type_text;
    final static String submit_url = "https://spotters.tech/dispatch-it/android/complaint.php";
    ArrayList<String> complainlist = new ArrayList<>();
    ArrayList<String> issuelist = new ArrayList<>();
    ArrayAdapter<String> adapter, issueadapter;


    String lnamen,fnamen;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);


        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        lnamen = sharedPreferences.getString(KEY_LNAME, null);
        fnamen = sharedPreferences.getString(KEY_FNAME, null);


        complain = findViewById(R.id.Complaint);
        complain_btn = findViewById(R.id.complaint_btn);
        comp_category = findViewById(R.id.comp_category_spinner);
        issue_type = findViewById(R.id.issue_type_spinner);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url_complain = "https:///spotters.tech/dispatch-it/android/spinner_options_comp_category.php";
        String url_issue = "https:///spotters.tech/dispatch-it/android/spinner_options_issue_type.php";


        // THE CODE THAT PUSHES THE SPINNER CATEGORY TO THE DB
        JsonObjectRequest complainjsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_complain, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("complaint_cat");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String complainname = jsonObject.optString("comp_category");
                        complainlist.add(complainname);
                        adapter = new ArrayAdapter<>(Complaint.this, android.R.layout.simple_spinner_item, complainlist);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                        comp_category.setAdapter(adapter);
                        comp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                comp_category_text = parent.getItemAtPosition(position).toString();
                                // Toast.makeText(getApplicationContext(), comp_category_text, Toast.LENGTH_SHORT).show();
                                // System.out.println(comp_category_text);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Complaint.this, "Error!!!" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Complaint.this, "Error!!!" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(complainjsonObjectRequest);
        //..................................Complain Spinner


        JsonObjectRequest issuejsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_issue, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("issue_type");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String issuename = jsonObject.optString("issue_name");
                        issuelist.add(issuename);
                        issueadapter = new ArrayAdapter<>(Complaint.this, android.R.layout.simple_spinner_item, issuelist);
                        issueadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        issue_type.setAdapter(issueadapter);
                        issue_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                issue_type_text = parent.getItemAtPosition(position).toString();
                                //Toast.makeText(getApplicationContext(), issue_type_text, Toast.LENGTH_SHORT).show();
                                // System.out.println(issue_type_text);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Complaint.this, "Error!!!" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Complaint.this, "Error!!!" + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(issuejsonObjectRequest);
        //..................................Issue Spinner


//        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.issue_type_list,
//                android.R.layout.simple_spinner_item);
//        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        issue_type.setAdapter(adapter2);
//        issue_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                issue_type_text = parent.getItemAtPosition(position).toString();
//                Toast.makeText(getApplicationContext(), issue_type_text, Toast.LENGTH_SHORT).show();
//                System.out.println(issue_type_text);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


        // SETONCLICKLISTENER TO SUBMIT ALL SELECTED PREFERENCES
        complain_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String edit_complain = complain.getText().toString().trim();

                if (edit_complain.isEmpty()) {
                    Toast.makeText(Complaint.this, "Empty fields", Toast.LENGTH_SHORT).show();
                } else {
                    submit_complaint();
                }
            }

        });
    }

    private void submit_complaint() {
        final String complaint = this.complain.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, submit_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")) {
                        Toast.makeText(Complaint.this, "Your complaint has been sent successfully", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Complaint.this, "Oops! we could,nt send your complaint try again", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Complaint.this, "Oops! we could,nt send your complaint try again", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> Params = new HashMap<>();
                Params.put("c_message", complaint);
                Params.put("comp_category", comp_category_text);
                Params.put("issue_name", issue_type_text);
                Params.put("c_firstname", fnamen);
                Params.put("c_lastname", lnamen);
                return Params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void backbtn(View view) {
        onBackPressed();
    }
}