package com.example.gf_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
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

        final Spinner orderHomeView = dialogview.findViewById(R.id.et_order_home_view);
        List<String> x = new ArrayList<>();
        x.add("Crescente");
        x.add("Decrescente");
        x.add("A-Z");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, x
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderHomeView.setAdapter(spinnerAdapter);

        List<Alimento> alimentosList = Api.inventory(MainActivity.idInventario);
        orderHomeView.setAdapter(spinnerAdapter);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String order = orderHomeView.getSelectedItem().toString();

                if (order.equals("Crescente")) {
                    alimentosList.sort(Comparator.comparingInt(Alimento::getGrammi));
                    Toast.makeText(context, "Ordine Crescente", Toast.LENGTH_SHORT).show();

                } else if (order.equals("Decrescente")) {
                    alimentosList.sort(Comparator.comparingInt(Alimento::getGrammi).reversed());
                    Toast.makeText(context, "Ordine Decrescente", Toast.LENGTH_SHORT).show();

                } else if (order.equals("A-Z")) {
                    alimentosList.sort(Comparator.comparing(Alimento::getNome));
                    Toast.makeText(context, "Dalla A alla Z", Toast.LENGTH_SHORT).show();
                }

                spinnerAdapter.notifyDataSetChanged();


                if (listener != null) {
                    listener.onUpdate(alimentosList);
                }


            }});

        builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }
}
