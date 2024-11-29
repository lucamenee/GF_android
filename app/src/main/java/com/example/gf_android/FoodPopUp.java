package com.example.gf_android;

import android.os.Bundle;
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
        Button closeButton = view.findViewById(R.id.close_button);

        if (alimento != null) {
            // TODO: sistema sto schifo
            textViewNome.setText(alimento.getNome());
            textViewScadenza.setText(alimento.getScadenza());

            // TODO: immagine non viene mostrata: fixa
            imageViewAlimento.setImageResource(getResources().getIdentifier(alimento.getImg(), "drawable", getContext().getPackageName()));
            closeButton.setOnClickListener(v -> dismiss());

            //TODO: inserisci logica per modifcare dati (scadenza, quantità, consuma cibo)
        }

        return view;
    }

    public void setAlimento(Alimento alimento) {
        this.alimento = alimento;
    }
}