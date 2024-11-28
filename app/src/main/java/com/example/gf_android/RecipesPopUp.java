package com.example.gf_android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.gf_android.Api.Api;
import com.example.gf_android.Api.Types.Ricetta;

import java.util.List;

public class RecipesPopUp  extends DialogFragment {
    private int idInventario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipes_popup_fragment_layout, container, false);

        // close popup when button is clicked
        view.findViewById(R.id.close_button).setOnClickListener(v -> dismiss());

        List<Ricetta> recipes = Api.suggestRecepies(idInventario);

        if (recipes != null) {
            TextView textView = view.findViewById(R.id.dialog_message);

            // building msg to show
            StringBuilder rec = new StringBuilder();
            for (Ricetta r : recipes) {
                Log.i("RecipesPopUp", r.nome_ricetta);
                rec.append(r.nome_ricetta).append("\n");
                for (Ricetta.Ingrediente i : r.ingredienti) {
                    rec.append("\n").append(i.nome_alimento).append(": ").append(i.grammi).append("g");
                }
                rec.append("\n");
            }


            if (rec.length() > 0) {
                textView.setText(rec.toString());
            }
        }

        return view;
    }


    public void setData(int idInventario) {
        this.idInventario = idInventario;
    }
}
