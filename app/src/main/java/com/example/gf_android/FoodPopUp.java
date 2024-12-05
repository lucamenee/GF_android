package com.example.gf_android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.gf_android.Api.Api;
import com.example.gf_android.Api.Types.Alimento;
import com.example.gf_android.Api.Types.UpdateInsertMsg;


public class FoodPopUp extends DialogFragment {
    private Alimento alimento;


    private OnUpdatesListener listener;

    public void setOnUpdateListener(OnUpdatesListener listener) {
        this.listener = listener;
    }

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

        Button closeButton = view.findViewById(R.id.close_food_popup_button);
        Button removeButton = view.findViewById(R.id.remove_food_button);
        Button updateButton = view.findViewById(R.id.update_food_button);

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

            //buttons' listeners
            // dismiss
            closeButton.setOnClickListener(v -> dismiss());

            // remove food
            removeButton.setOnClickListener(v -> {
                UpdateInsertMsg msg = Api.updateFoodQt(alimento.id_riga_inventario, -alimento.grammi);
                if (msg.rowsAffected > 0) {
                    if (listener != null)
                        listener.onUpdate();
                    dismiss();
                    Toast.makeText(getContext(), "Alimento rimosso: " + alimento.nome_alimento, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Errore durante la rimozione: " + msg.msg, Toast.LENGTH_SHORT).show();
                }
            });
            // TODO: update qt
            //TODO: inserisci logica per modifcare dati (scadenza, quantità, consuma cibo)


        }

        return view;
    }

    public void setAlimento(Alimento alimento) {
        this.alimento = alimento;
    }


}