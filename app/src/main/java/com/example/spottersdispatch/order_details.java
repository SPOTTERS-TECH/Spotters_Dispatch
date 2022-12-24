package com.example.spottersdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class order_details extends AppCompatActivity {

    TextView sender_name,receiver_name,sender_address,sender_destination,sender_phone,package_type,sender_order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        sender_name = findViewById(R.id.sender_name);
        receiver_name = findViewById(R.id.receiver_name);
        sender_address = findViewById(R.id.sender_address);
        sender_destination = findViewById(R.id.sender_destination);
        sender_phone = findViewById(R.id.sender_phone);
        package_type = findViewById(R.id.package_type);
        sender_order_id = findViewById(R.id.Sender_order_id);

       String s_name = getIntent().getStringExtra("sender_name");
        String S_phone = getIntent().getStringExtra("sender_phone");
        String S_address = getIntent().getStringExtra("add");
        String s_destination = getIntent().getStringExtra("destination");
        String p_type = getIntent().getStringExtra("package_name");
        String package_weight = getIntent().getStringExtra("package_weight");
        String r_name = getIntent().getStringExtra("receiver_name");
        String receiver_phone = getIntent().getStringExtra("receiver_phone");
        String order_id = getIntent().getStringExtra("id");

        sender_name.setText(s_name);
        receiver_name.setText(r_name);
        sender_address.setText(S_address);
        sender_destination.setText(s_destination);
        sender_phone.setText(S_phone);
        package_type.setText(p_type);
        sender_order_id.setText(order_id);

    }

    public void backbtn(View view) {
        onBackPressed();
    }
}