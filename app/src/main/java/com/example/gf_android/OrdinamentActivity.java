package com.example.gf_android;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.gf_android.Api.Api;
import com.example.gf_android.Api.Types.Alimento;

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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, x
        );

        List<Alimento> alimentosList = Api.getAlimenti();

        orderHomeView.setAdapter(adapter);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedOrder = orderHomeView.getSelectedItem().toString();

                // Check sorting condition
                if (selectedOrder.equals("Crescente")) {
                    Collections.sort(alimentosList, new Comparator<Alimento>() {
                        @Override
                        public int compare(Alimento a1, Alimento a2) {
                            // Use Grammi if greater than 0, otherwise Peso_unitario
                            int value1 = (a1.getPeso_unitario() > 0) ? a1.getGrammi() : a1.getPeso_unitario();
                            int value2 = (a2.getPeso_unitario() > 0) ? a2.getGrammi() : a2.getPeso_unitario();
                            return Integer.compare(value1, value2);
                        }
                    });
                    Toast.makeText(context, "Ordine Crescente", Toast.LENGTH_SHORT).show();

                } else if (selectedOrder.equals("Decrescente")) {
                    Collections.sort(alimentosList, new Comparator<Alimento>() {
                        @Override
                        public int compare(Alimento a1, Alimento a2) {
                            // Use Grammi if greater than 0, otherwise Peso_unitario
                            int value1 = (a1.getPeso_unitario() > 0) ? a1.getGrammi() : a1.getPeso_unitario();
                            int value2 = (a2.getPeso_unitario() > 0) ? a2.getGrammi() : a2.getPeso_unitario();
                            return Integer.compare(value2, value1);
                        }
                    });
                    Toast.makeText(context, "Ordine Decrescente", Toast.LENGTH_SHORT).show();

                } else if (selectedOrder.equals("A-Z")) {
                    Collections.sort(alimentosList, new Comparator<Alimento>() {
                        @Override
                        public int compare(Alimento a1, Alimento a2) {
                            return a1.getNome().compareToIgnoreCase(a2.getNome());
                        }
                    });
                    Toast.makeText(context, "Dalla A alla Z", Toast.LENGTH_SHORT).show();
                }

                // Notify any adapters if required
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }

                // Trigger listener update
                if (listener != null) {
                    listener.onUpdate();
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
