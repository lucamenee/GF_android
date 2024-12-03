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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import com.example.gf_android.Api.Types.Alimento;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AddAlimento {
    public static void showAddProductDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Aggiungi prodotto");

        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_add_alimento, null);
        builder.setView(dialogView);


        final Spinner spinnerProductName = dialogView.findViewById(R.id.et_product_name);
        final  Button btnSelectDate = dialogView.findViewById(R.id.btn_select_date);
        final EditText Quantity = dialogView.findViewById(R.id.et_quantity);
        final CheckBox Essential = dialogView.findViewById(R.id.cb_essential);



        List<Alimento> productNames = getAlimenti();
        ArrayAdapter<Alimento> adapter = new ArrayAdapter<>(
                context, android.R.layout.simple_spinner_item, productNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProductName.setAdapter(adapter);

        final Calendar calendar = Calendar.getInstance();
        btnSelectDate.setOnClickListener(v -> {
            new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
                // Update the button text with the selected date
                String selectedDate = dayOfMonth + "/" + month + "/" + year;
                btnSelectDate.setText(selectedDate);
            },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });


        builder.setPositiveButton("Aggiungi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Alimento selectedProduct =  (Alimento) spinnerProductName.getSelectedItem();
                String productName = selectedProduct != null ? selectedProduct.nome_alimento : "";

                String expirationDate = btnSelectDate.getText().toString();
                int quantity = Integer.parseInt(Quantity.getText().toString());
                boolean isEssential = Essential.isChecked();

                if (quantity>0 || selectedProduct!=null || !expirationDate.isEmpty()) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
                    try {
                        Date date = dateFormat.parse(expirationDate);
                        // TODO: nella data inserita viene fuori il mese succcessivo a quello selezionato, ricordati la stpria dei grammi e del peso unitario
                        addFoodInventory(MainActivity.idInventario, selectedProduct.id_alimento, quantity, date, isEssential);
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
