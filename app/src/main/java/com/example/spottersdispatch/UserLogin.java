package com.example.spottersdispatch;


import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class UserLogin extends AppCompatActivity {

    ConstraintLayout cont_lay;
    TextView cont_msg;
    private static final int REQUEST_CODE = 101010;
    EditText phone, password;
    Button log_btn;
    ProgressBar load;
    ImageView fingerprintimg;
    boolean passwordvisible;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private final static String Url_login = "https://spotters.tech/dispatch-it/android/login.php";

    //    importing shared prefrrence
    SharedPreferences sharedPreferences;
    //    creating a variablle for the sharedprefrence
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ID = "id";
    private static final String KEY_STATUS = "status";
    private static final String KEY_COMPANY_ID = "company_id";

    //    private static final String KEY_PHONE = "phone";
    String Phonen;


//    public void methfing(SwitchCompat fing) {
//        fingerprintimg.setVisibility(View.VISIBLE);
//    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);

        cont_lay = findViewById(R.id.cont_lay);
        cont_msg = findViewById(R.id.cont_msg);

        phone = findViewById(R.id.log_phone);
        password = findViewById(R.id.log_password);
        log_btn = findViewById(R.id.loginbtn);
        fingerprintimg = findViewById(R.id.fingerimg);

        load = findViewById(R.id.progressBar);


        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);

        Phonen = sharedPreferences.getString(KEY_PHONE, null);
        String fnamen = sharedPreferences.getString(KEY_FNAME, null);
        String fid = sharedPreferences.getString(KEY_ID, null);
        String lnamen = sharedPreferences.getString(KEY_LNAME, null);
        String lphone = sharedPreferences.getString(KEY_PHONE, null);
        String lpassword = sharedPreferences.getString(KEY_PASSWORD, null);
        String company_id = sharedPreferences.getString(KEY_COMPANY_ID, null);
//        if(Phonen != null && fnamen != null && lnamen != null && fid != null ){
//            Intent gotoWelcomeActivity = new Intent(UserLogin.this, MainActivity .class);
//            startActivity(gotoWelcomeActivity);
//            finish();
//        }


        boolean finger = getIntent().getBooleanExtra("key", false);
        if (finger == true) {
            fingerprintimg.setVisibility(View.VISIBLE);

        }

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= password.getRight() - password.getCompoundDrawables()[Right].getBounds().width()) {
                        int selection = password.getSelectionEnd();
                        if (passwordvisible) {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility_off, 0);
                            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordvisible = false;
                        } else {
                            password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_visibility_24, 0);
                            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordvisible = true;
                        }
                        password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Log.d("MY_APP_TAG", "App can authenticate using biometrics.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this, "Fingerprint Sensor not available", Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Log.e("MY_APP_TAG", "Biometric features are currently unavailable.");
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // Prompts the user to create credentials that your app accepts.
                final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                startActivityForResult(enrollIntent, REQUEST_CODE);
                break;
        }

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(UserLogin.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Intent gotoWelcomeActivity = new Intent(UserLogin.this, MainActivity.class);
                startActivity(gotoWelcomeActivity);
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed",
                                Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();


        fingerprintimg.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
        });


        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phoneT = phone.getText().toString().trim();
                final String passwordT = password.getText().toString().trim();

                if (phoneT.isEmpty() || passwordT.isEmpty()) {
                    phone.setError("Empty field");
                    password.setError("Empty field");

                } else {
                    proceedlogin();
                }


            }
        });


    }

    public void proceedlogin() {


        log_btn.setVisibility(View.GONE);
        load.setVisibility(View.VISIBLE);
        final String phone = this.phone.getText().toString().trim();
        final String password = this.password.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, Url_login, new Response.Listener<String>() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("login");
                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String firstname = object.getString("firstname").trim();
                            String lastname = object.getString("lastname").trim();
                            String phone = object.getString("phone").trim();
                            String email = object.getString("email").trim();
                            String id = object.getString("ids").trim();
                            String status = object.getString("status").trim();
                            String company_id = object.getString("company_id").trim();
                            // String sign_up_date = object.getString("sign_up_date").t
                            // rim();

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(KEY_PASSWORD, password.toString());
                            editor.putString(KEY_PHONE, phone.toString());
                            editor.putString(KEY_FNAME, firstname.toString());
                            editor.putString(KEY_EMAIL, email.toString());
                            editor.putString(KEY_LNAME, lastname.toString());
                            editor.putString(KEY_ID, id.toString());
                            editor.putString(KEY_STATUS, status.toString());
                            editor.putString(KEY_COMPANY_ID, company_id.toString());

                            editor.apply();

//                            sessionManager.createSession(firstname, lastname, phone, email, id, sign_up_date);

                            // gotoWelcomeActivity.putExtra("sign_up_date", sign_up_date);
                            load.setVisibility(View.GONE);
                            createdialog(firstname, lastname, phone, email, id, status, company_id);
                            // finish();
                        }

                    } else {
                        load.setVisibility(View.GONE);
                        cont_lay.setBackgroundColor(R.color.red);
                        cont_msg.setText("Phone digits or password is incorrect");
                        cont_lay.setVisibility(View.VISIBLE);
                        log_btn.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(UserLogin.this, "Oops! you encountered some error while logging in, please try again", Toast.LENGTH_LONG).show();
                    load.setVisibility(View.GONE);
                    log_btn.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserLogin.this, "Oops! you encountered some error while logging in, please try again", Toast.LENGTH_LONG).show();
                load.setVisibility(View.GONE);
                log_btn.setVisibility(View.VISIBLE);

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("rider_ph", phone);
                params.put("rider_password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @SuppressLint("ResourceAsColor")
    private void createdialog(String firstname, String lastname, String phone, String email, String id, String status, String company_id) {
        cont_lay.setVisibility(View.VISIBLE);
        cont_msg.setText("Login Successful");
        cont_lay.setVisibility(View.VISIBLE);
        cont_lay.setBackgroundColor(R.color.green);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cont_lay.setVisibility(View.GONE);
                Intent intent = new Intent(UserLogin.this, MainActivity.class);
                intent.putExtra("firstname", firstname);
                intent.putExtra("lastname", lastname);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                intent.putExtra("id", id);
                intent.putExtra("status", status);
                intent.putExtra("company_id", company_id);

                UserLogin.this.startActivity(intent);
                UserLogin.this.finish();
            }
        }, 2000);
    }
}