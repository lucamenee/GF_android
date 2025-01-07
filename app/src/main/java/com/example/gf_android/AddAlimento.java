package com.example.gf_android;

import static com.example.gf_android.Api.Api.addFoodInventory;
import static com.example.gf_android.Api.Api.getAlimenti;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import com.example.gf_android.Api.Types.Alimento;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AddAlimento {
    public static void showAddProductDialog(final Context context, OnUpdatesListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Aggiungi prodotto");

        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_alimento, null);
        builder.setView(dialogView);

        TextView gramsTag = dialogView.findViewById(R.id.grams_tag);

        final Spinner spinnerProductName = dialogView.findViewById(R.id.et_product_name);
        final  Button btnSelectDate = dialogView.findViewById(R.id.btn_select_date);
        final EditText Quantity = dialogView.findViewById(R.id.et_quantity);

        List<Alimento> productNames = getAlimenti();
        ArrayAdapter<Alimento> adapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, productNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProductName.setAdapter(adapter);
        // default expiring date: today + alimento.durata_media
        spinnerProductName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Alimento selectedProduct = (Alimento) parent.getSelectedItem();
                if (selectedProduct != null) {
                    int durataMedia = selectedProduct.durata_media; // Assumi che `durata_media` sia un campo di `Alimento`
                    Calendar defaultDate = Calendar.getInstance();
                    defaultDate.add(Calendar.DAY_OF_MONTH, durataMedia);

                    btnSelectDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(defaultDate.getTime()));

                    if (selectedProduct.peso_unitario != 0) {
                        gramsTag.setText("unità");
                    } else {
                        gramsTag.setText("g.");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });


        final Calendar calendar = Calendar.getInstance();
        btnSelectDate.setOnClickListener(v -> {
            new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
                // Update the button text with the selected date
                String selectedDate = dayOfMonth + "/" + (month+1) + "/" + year;
                btnSelectDate.setText(selectedDate);
            },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });


        builder.setPositiveButton("Aggiungi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                boolean isUnit = false;
                Alimento selectedProduct =  (Alimento) spinnerProductName.getSelectedItem();
                String productName = selectedProduct != null ? selectedProduct.nome_alimento : "";

                String expirationDate = btnSelectDate.getText().toString();
                int quantity = Integer.parseInt(Quantity.getText().toString());

                if (quantity>0 && selectedProduct!=null && !expirationDate.isEmpty()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Log.i("AddAlimento", "expirationDate: " + expirationDate);
                    if (selectedProduct.peso_unitario != 0)
                        quantity *= selectedProduct.peso_unitario;
                    try {
                        Date date = dateFormat.parse(expirationDate);
                        // TODO: ricordati la storia dei grammi e del peso unitario
                        addFoodInventory(MainActivity.idInventario, selectedProduct.id_alimento, quantity, date);
                        if (listener != null)
                            listener.onUpdate();
                        Toast.makeText(context, "Prodotto aggiunto: " + productName, Toast.LENGTH_SHORT).show();
                    } catch (ParseException e) {
                        Toast.makeText(context, "Data non valida", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Compila tutti i campi", Toast.LENGTH_SHORT).show();
                }
            }
        });


        builder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

}
