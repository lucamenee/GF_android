package com.example.gf_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;

import com.example.gf_android.Api.Types.Alimento;

import java.util.List;

public class AlimentoAdapter extends RecyclerView.Adapter<AlimentoAdapter.AlimentoViewHolder> {

    private Context context;
    private List<Alimento> alimentoList;

    public AlimentoAdapter(Context context, List<Alimento> alimentoList) {
        this.context = context;
        this.alimentoList = alimentoList;
    }

    @NonNull
    @Override
    public AlimentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_alimento, parent, false);
        return new AlimentoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlimentoViewHolder holder, int position)
    {
        Alimento alimento = alimentoList.get(position);

        // Imposta i dati
        holder.textViewNome.setText(alimento.getNome());
        //holder.textViewQuantita.setText("Quantità: " + alimento.getPeso_unitario());
        holder.textViewScadenza.setText(alimento.getScadenza());


        // Carica l'immagine (usa Glide o Picasso)
        //Glide.with(context)
                //.load(alimento.getImg())
                //.placeholder(R.drawable.logo)
                //.into(holder.imageViewAlimento);
    }


    @Override
    public int getItemCount() {
        return alimentoList.size();
    }

    public static class AlimentoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNome, textViewQuantita, textViewScadenza;
        ImageView imageViewAlimento;

        public AlimentoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewNome);
            //textViewQuantita = itemView.findViewById(R.id.textViewQuantita);
            textViewScadenza = itemView.findViewById(R.id.textViewScadenza);
            imageViewAlimento = itemView.findViewById(R.id.imageViewAlimento);
        }
    }
}
