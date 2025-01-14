package com.example.gf_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.gf_android.Api.Api;
import com.example.gf_android.Api.Types.Alimento;
import com.example.gf_android.Api.Types.Utente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrdinamentActivity {
    public static void showOrderDialog(final Context context, OnUpdatesListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Disponi i tuoi alimenti in ordine:");

        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogview = inflater.inflate(R.layout.activity_ordinament, null);
        builder.setView(dialogview);


        LinearLayout date_asc = dialogview.findViewById(R.id.date_asc_order);
        LinearLayout date_desc = dialogview.findViewById(R.id.date_desc_order);
        LinearLayout az = dialogview.findViewById(R.id.az_order);
        LinearLayout za = dialogview.findViewById(R.id.za_order);
        List<LinearLayout> linearLayouts = new ArrayList<>();
        linearLayouts.add(date_asc);
        linearLayouts.add(date_desc);
        linearLayouts.add(az);
        linearLayouts.add(za);



        List<Alimento> alimentosList = Api.inventory(MainActivity.idInventario);


        date_desc.setOnClickListener(e -> {
            alimentosList.sort(Comparator.comparing(Alimento::expiresIn).reversed());
            Toast.makeText(context, "Data di scadenza decrescente", Toast.LENGTH_SHORT).show();
            if (listener != null) {
                listener.onUpdate(alimentosList);
            }
            for (LinearLayout linearLayout : linearLayouts) {
                linearLayout.setBackgroundColor(context.getColor(R.color.light_blue));
            }
            date_desc.setBackgroundColor(context.getResources().getColor(R.color.primary_dark_color));

        });
        date_asc.setOnClickListener(e -> {
            alimentosList.sort(Comparator.comparing(Alimento::expiresIn));
            Toast.makeText(context, "Data di scadenza", Toast.LENGTH_SHORT).show();
            if (listener != null) {
                listener.onUpdate(alimentosList);
            }
            for (LinearLayout linearLayout : linearLayouts) {
                linearLayout.setBackgroundColor(context.getColor(R.color.light_blue));
            }
            date_asc.setBackgroundColor(context.getResources().getColor(R.color.primary_dark_color));
        });
        az.setOnClickListener(e -> {
            alimentosList.sort(Comparator.comparing(Alimento::getNome));
            Toast.makeText(context, "Dalla A alla Z", Toast.LENGTH_SHORT).show();
            if (listener != null) {
                listener.onUpdate(alimentosList);
            }
            for (LinearLayout linearLayout : linearLayouts) {
                linearLayout.setBackgroundColor(context.getColor(R.color.light_blue));
            }
            az.setBackgroundColor(context.getResources().getColor(R.color.primary_dark_color));

        });
        za.setOnClickListener(e -> {
            alimentosList.sort(Comparator.comparing(Alimento::getNome).reversed());
            Toast.makeText(context, "Dalla Z alla A", Toast.LENGTH_SHORT).show();
            if (listener != null) {
                listener.onUpdate(alimentosList);
            }
            for (LinearLayout linearLayout : linearLayouts) {
                linearLayout.setBackgroundColor(context.getColor(R.color.light_blue));
            }
            za.setBackgroundColor(context.getResources().getColor(R.color.primary_dark_color));
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    listener.onUpdate(alimentosList);
                }
            }
        });

        builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alimentosList.sort(Comparator.comparing(Alimento::expiresIn));
                if (listener != null) {
                    listener.onUpdate(alimentosList);
                }
                dialog.dismiss();
            }
        });

        builder.create().show();
    }
}
