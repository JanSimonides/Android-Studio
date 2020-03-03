package com.example.myapp6;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public abstract class MyAlert {
    private AlertDialog.Builder alert;

    public MyAlert(Context context, String title, String message) {
        alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                onClickNegativeBtn();
            }
        });
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                onClickPositiveBtn();
            }
        });
    }

    public void show() {
        alert.show();
    }

    public abstract void onClickPositiveBtn();

    public abstract void onClickNegativeBtn();
}
