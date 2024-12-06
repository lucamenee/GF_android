package com.example.gf_android;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gf_android.Api.Types.Alimento;
import com.example.gf_android.Api.Types.Utente;

import java.util.List;

/*
public class AlimentoAdapter extends RecyclerView.Adapter<AlimentoAdapter.AlimentoViewHolder> {

    private Context context;
    private List<Alimento> alimentoList;
    private Utente user;

    public AlimentoAdapter(Context context, List<Alimento> alimentoList, Utente user)
    {
        this.context = context;
        this.alimentoList = alimentoList;
        this.user = user;
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


        int giorni = alimento.expiresIn();
        if (giorni > 0) {
            holder.textViewScadenza.setText(context.getString(R.string.scade_in, giorni));
        } else if (giorni < 0) {
            holder.textViewScadenza.setText(context.getString(R.string.scaduto_da, Math.abs(giorni)));
        } else {
            holder.textViewScadenza.setText(context.getString(R.string.scade_oggi));
        }

        // Cambia il colore del container in base ai giorni mancanti
        if (giorni > 7) {
            holder.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.buono)); // Colore verde
        } else if (giorni > 0 || giorni == 0) {
            holder.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.quasiscaduto)); // Colore giallo
        } else {
            holder.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.scaduto)); // Colore rosso
        }

        // Imposta la quantità
        if (alimento.getPeso_unitario() > 0) {
            holder.textViewQuantita.setText(String.valueOf(alimento.getGrammi() / alimento.getPeso_unitario())); //boh ce un problema ma non ciò più cazzi per oggi
        } else {
            holder.textViewQuantita.setText(String.valueOf(alimento.getGrammi()) + " g");
        }

        //Carica img
        String imgRes = alimento.getImg();
        if (imgRes != null && !imgRes.isEmpty()) {
            imgRes = imgRes.substring(0, imgRes.lastIndexOf("."));
            int resId = context.getResources().getIdentifier(imgRes, "drawable", context.getPackageName());
            if (resId != 0) {
                holder.imageViewAlimento.setImageResource(resId);
            } else {
                holder.imageViewAlimento.setImageResource(R.drawable.logo);
            }
        } else {
            holder.imageViewAlimento.setImageResource(R.drawable.logo);
        }

        // Imposta il click listener
        holder.itemView.setOnClickListener(v -> {
            FoodPopUp foodPopUp = new FoodPopUp();
            foodPopUp.setData(alimento, this.user);
            foodPopUp.setOnUpdateListener((MainActivity) context);
            foodPopUp.show(((MainActivity) context).getSupportFragmentManager(), "foodPopUp");
        });
    }


    @Override
    public int getItemCount() {
        return alimentoList.size();
    }

    public static class AlimentoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNome, textViewQuantita, textViewScadenza;
        ImageView imageViewAlimento;
        CardView container;

        public AlimentoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNome = itemView.findViewById(R.id.textViewNome);
            textViewQuantita = itemView.findViewById(R.id.textViewQuantita);
            textViewScadenza = itemView.findViewById(R.id.textViewScadenza);
            imageViewAlimento = itemView.findViewById(R.id.imageViewAlimento);
            container = itemView.findViewById(R.id.container);
        }
    }

    public void updateData(List<Alimento> newInventario) {
        this.alimentoList = newInventario;
        notifyDataSetChanged();
    }
}
*/

public class AlimentoAdapter extends BaseAdapter {

    private Context context;
    private List<Alimento> alimentoList;
    private Utente user;

    public AlimentoAdapter(Context context, List<Alimento> alimentoList, Utente user)
    {
        this.context = context;
        this.alimentoList = alimentoList;
        this.user = user;
    }


    @Override
    public int getCount() {
        return alimentoList.size();
    }

    @Override
    public Object getItem(int position) {
        return alimentoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            // Inflating the item layout
            convertView = LayoutInflater.from(context).inflate(R.layout.item_alimento, parent, false);
            holder = new ViewHolder();
            holder.textViewNome = convertView.findViewById(R.id.textViewNome);
            holder.textViewQuantita = convertView.findViewById(R.id.textViewQuantita);
            holder.textViewScadenza = convertView.findViewById(R.id.textViewScadenza);
            holder.imageViewAlimento = convertView.findViewById(R.id.imageViewAlimento);
            holder.container = convertView.findViewById(R.id.container); // Aggiungi questa riga se usi un CardView
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Associa i dati dell'alimento all'interfaccia utente
        Alimento alimento = alimentoList.get(position);
        holder.textViewNome.setText(alimento.getNome());

        // Gestisci la scadenza
        int giorni = alimento.expiresIn();
        if (giorni > 0) {
            holder.textViewScadenza.setText(context.getString(R.string.scade_in, giorni));
        } else if (giorni < 0) {
            holder.textViewScadenza.setText(context.getString(R.string.scaduto_da, Math.abs(giorni)));
        } else {
            holder.textViewScadenza.setText(context.getString(R.string.scade_oggi));
        }

        // Cambia il colore del container in base ai giorni mancanti
        if (giorni > 7) {
            holder.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.buono)); // Colore verde
        } else if (giorni > 0 || giorni == 0) {
            holder.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.quasiscaduto)); // Colore giallo
        } else {
            holder.container.setCardBackgroundColor(ContextCompat.getColor(context, R.color.scaduto)); // Colore rosso
        }

        // Imposta la quantità
        if (alimento.getPeso_unitario() > 0) {
            holder.textViewQuantita.setText(String.valueOf(alimento.getGrammi() / alimento.getPeso_unitario())); //boh ce un problema ma non ciò più cazzi per oggi
        } else {
            holder.textViewQuantita.setText(String.valueOf(alimento.getGrammi()) + " g");
        }

        // Gestisci l'immagine
        String imgRes = alimento.getImg();
        if (imgRes != null && !imgRes.isEmpty()) {
            imgRes = imgRes.substring(0, imgRes.lastIndexOf("."));
            int resId = context.getResources().getIdentifier(imgRes, "drawable", context.getPackageName());
            if (resId != 0) {
                holder.imageViewAlimento.setImageResource(resId);
            } else {
                holder.imageViewAlimento.setImageResource(R.drawable.logo);
            }
        } else {
            holder.imageViewAlimento.setImageResource(R.drawable.logo);
        }

        // Imposta il click listener
        convertView.setOnClickListener(v -> {
            FoodPopUp foodPopUp = new FoodPopUp();
            foodPopUp.setData(alimento, this.user);
            foodPopUp.setOnUpdateListener((MainActivity) context);
            foodPopUp.show(((MainActivity) context).getSupportFragmentManager(), "foodPopUp");
        });


        return convertView;
    }

    private static class ViewHolder {
        TextView textViewNome;
        TextView textViewQuantita;
        TextView textViewScadenza;
        ImageView imageViewAlimento;
        CardView container;
    }

    public void updateData(List<Alimento> newInventario) {
        this.alimentoList = newInventario;
        notifyDataSetChanged();
    }
}
