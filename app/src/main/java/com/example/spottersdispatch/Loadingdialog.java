package com.example.spottersdispatch;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class Loadingdialog {

    Activity activity;
    AlertDialog alertDialog;

    Loadingdialog(Activity activity) {
        this.activity = activity;
    }

    void showLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loadng_dialog, null));

        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    void dismissDialog() {
        alertDialog.dismiss();
    }

}
