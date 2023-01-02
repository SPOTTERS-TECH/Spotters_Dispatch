package com.example.spottersdispatch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Edit_profile extends AppCompatActivity {

    EditText userphone,fname,lname,email;
    Button btneditprofile;
    TextView profile_name;
    String fid;
    final  int CODE_GALLERY_REQUEST = 999;
    ImageView profile_img;
    final static String url_profile = "https://spotters.tech/dispatch-it/android/edit_profile.php";

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
        setContentView(R.layout.activity_edit_profile);
        userphone = findViewById(R.id.userphone);
        fname = findViewById(R.id.Fname);
        lname = findViewById(R.id.Lname);
        profile_img = findViewById(R.id.profile_img);
        email = findViewById(R.id.Email);
        btneditprofile = findViewById(R.id.Editprofile);


        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String Phonen = sharedPreferences.getString(KEY_PHONE,null);
        String fnamen = sharedPreferences.getString(KEY_FNAME,null);
        String lnamen = sharedPreferences.getString(KEY_LNAME,null);
        fid = sharedPreferences.getString(KEY_ID,null);
        String emailn = sharedPreferences.getString(KEY_EMAIL,null);
        if(Phonen != null && fnamen != null && fid != null){
            userphone.setText(Phonen);
            fname.setText(fnamen);
            lname.setText(lnamen);
            email.setText(emailn);
        }

        profile_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        Edit_profile.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALLERY_REQUEST
                );
            }
        });

        btneditprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Tfname = fname.getText().toString().trim();
                String Tlname = lname.getText().toString().trim();
                String Temail = email.getText().toString().trim();
                String Tphone = userphone.getText().toString().trim();

                if (Tfname.isEmpty() || Tlname.isEmpty() || Temail.isEmpty() || Tphone.isEmpty()) {
                    fname.setError("Empty field");
                    lname.setError("Empty field");
                    email.setError("Empty field");
                    userphone.setError("Empty field");
                } else if (Tfname.equals(fnamen) && Tphone.equals(lnamen) && Temail.equals(emailn) && Tphone.equals(Phonen)) {
                    Toast.makeText(Edit_profile.this, "No change was detected", Toast.LENGTH_SHORT).show();
                } else {
                    editprofile();
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CODE_GALLERY_REQUEST){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Image"),CODE_GALLERY_REQUEST);
            }else{
                Toast.makeText(getApplicationContext(),"Permission is not granted", Toast.LENGTH_LONG).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                profile_img.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void editprofile() {
        UserLogin userLogin = new UserLogin();
        final String firstname = this.fname.getText().toString().trim();
        final String lastname = this.lname.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String phone = this.userphone.getText().toString().trim();
        final String id = fid;


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_profile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    if (success.equals("1")){
                        Toast.makeText(Edit_profile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(Edit_profile.this,UserLogin.class);
                        startActivity(intent);
                        finish();
                        System.out.println(fid);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Edit_profile.this, "profile updated Failed" + e.toString(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Edit_profile.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("rider_fn", firstname);
                params.put("rider_ln", lastname);
                params.put("rider_em", email);
                params.put("rider_ph", phone);
                params.put("rider_id", id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void backbtn(View view) {
        onBackPressed();
    }
}