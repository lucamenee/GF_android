package com.example.gf_android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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

        List<Ricetta> recipes = Api.suggestRecepies(idInventario);


        TextView existingTextViewName = view.findViewById(R.id.recipe_name);
        TextView existingTextViewIngredients = view.findViewById(R.id.recipes_ingredients);

        LinearLayout parentLayout = view.findViewById(R.id.pop_up_recipes);



        if (recipes != null) {

            for (Ricetta r : recipes) {
                Log.i("RecipesPopUp", r.nome_ricetta);

                TextView newTextViewName = new TextView(this.getContext());
                TextView newTextViewIngredients = new TextView(this.getContext());
                copyAttributes(existingTextViewName, newTextViewName);
                copyAttributes(existingTextViewIngredients, newTextViewIngredients);
                newTextViewName.setText(r.nome_ricetta);

                StringBuilder rec = new StringBuilder();
                for (Ricetta.Ingrediente i : r.ingredienti) {
                    rec.append(i.nome_alimento).append(": ").append(i.grammi).append("g").append("\n");
                }
                rec.deleteCharAt(rec.length() - 1);
                newTextViewIngredients.setText(rec.toString());

                parentLayout.addView(newTextViewName);
                parentLayout.addView(newTextViewIngredients);

                parentLayout.removeView(existingTextViewName);
                parentLayout.removeView(existingTextViewIngredients);
            }

        }

        Button closeButton = view.findViewById(R.id.close_button);
        parentLayout.removeView(closeButton);
        parentLayout.addView(closeButton); // so button is replaced on the bottom
        // close popup when button is clicked
        closeButton.setOnClickListener(v -> dismiss());

        return view;
    }

    private void copyAttributes(TextView existingTextView, TextView newTextView) {
        newTextView.setText(existingTextView.getText());
        newTextView.setTextSize(existingTextView.getTextSize());
        newTextView.setTextColor(existingTextView.getTextColors());
        newTextView.setTypeface(existingTextView.getTypeface(), existingTextView.getTypeface().getStyle());
        newTextView.setLayoutParams(existingTextView.getLayoutParams());
        newTextView.setTextSize(existingTextView.getTextSize());
    }


    public void setData(int idInventario) {
        this.idInventario = idInventario;
    }
}
