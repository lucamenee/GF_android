package com.example.gf_android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.gf_android.Api.Types.Alimento;


public class FoodPopUp extends DialogFragment {
    private Alimento alimento;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.food_popup_fragment_layout, container, false);

        TextView textViewNome = view.findViewById(R.id.textViewNome);
        ImageView imageViewAlimento = view.findViewById(R.id.imageViewAlimento);
        TextView textViewScadenza = view.findViewById(R.id.textViewScadenza);
        TextView textViewInserimento = view.findViewById(R.id.textViewInserimento);
        TextView textViewQt = view.findViewById(R.id.textViewQt);
        TextView textViewKcal = view.findViewById(R.id.textViewKcal);
        Button closeButton = view.findViewById(R.id.close_button);

        if (alimento != null) {
            textViewNome.setText(alimento.getNome());
            textViewScadenza.setText(alimento.getScadenza());
            textViewInserimento.setText(alimento.getDataInserimento());
            textViewKcal.setText(String.valueOf(alimento.getKcal()) + "/100g");
            if (alimento.peso_unitario != 0) {
                textViewQt.setText(String.valueOf(alimento.grammi / alimento.peso_unitario));
            } else {
                textViewQt.setText(String.valueOf(alimento.grammi) + " g");
            }

            //setting image for food using alimento.getImg()
            String imgRes = alimento.getImg().substring(0, alimento.getImg().length() - 4);
            imageViewAlimento.setImageResource(getResources().getIdentifier(imgRes, "drawable", requireContext().getPackageName()));
            closeButton.setOnClickListener(v -> dismiss());

            //TODO: inserisci logica per modifcare dati (scadenza, quantità, consuma cibo)

            //TODO: inserisci pulsante per eliminare del tutto alimento -> usa Api.updateFoodQt() passando -alimento.grammi

        }

        return view;
    }

    public void setAlimento(Alimento alimento) {
        this.alimento = alimento;
    }
}