package com.example.spottersdispatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class History_report extends AppCompatActivity {

    TextView name,phone,order_id,destination,address,distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_report);

        name = findViewById(R.id.sender_name);
        phone = findViewById(R.id.sender_phone);
        order_id = findViewById(R.id.Sender_order_id);
        destination = findViewById(R.id.sender_destination);
        address = findViewById(R.id.sender_address);
        distance = findViewById(R.id.distance_covered);

        String  t_name = getIntent().getStringExtra("name");
        String  t_phone = getIntent().getStringExtra("phone");
        String t_order_id = getIntent().getStringExtra("order_id");
        String t_destination = getIntent().getStringExtra("destination");
        String t_address = getIntent().getStringExtra("add");
        String t_distance = getIntent().getStringExtra("distance");

        name.setText(t_name);
        phone.setText(t_phone);
        order_id.setText(t_order_id);
        destination.setText(t_destination);
        address.setText(t_address);
        distance.setText(t_distance);
    }

    public void backbtn(View view) {
        onBackPressed();
    }


}