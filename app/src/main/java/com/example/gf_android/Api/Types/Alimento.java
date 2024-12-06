package com.example.gf_android.Api.Types;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Alimento {

    public int id_alimento;
    public String nome_alimento;
    public int id_cat;
    public String img;
    public int kcal;
    public int peso_unitario;
    public String nome_categoria;
    public int durata_media;
    public int grammi;
    public Date data_scadenza;
    public Date data_inserimento;
    public boolean essenziale;
    public int id_riga_inventario;
    public int id_inventario;

    public int expiresIn() {
        return (int) ((data_scadenza.getTime() - new Date().getTime()) / 1000 / 60 / 60 / 24);
    }

    public String getNome() { return nome_alimento; }

    public String getScadenza()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); //forse ce un metodo migliore
        return sdf.format(data_scadenza);
    }

    public int getPeso_unitario() { return peso_unitario; }

    public String getImg() { return img; }

    public int getId_alimento() { return id_alimento; }

    public int getId_cat() { return id_cat; }

    public int getKcal() { return kcal; }

    public String getNome_categoria() { return nome_categoria; }

    public int getDurata_media() { return durata_media; }

    public int getGrammi() { return grammi; }

    public String getDataInserimento()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); //forse ce un metodo migliore
        return sdf.format(data_inserimento);
    }

    public boolean isEssenziale() { return essenziale; }

    public boolean isExpired() { return  this.data_scadenza.before(new Date()); }

    @NonNull
    @Override
    public String toString() {
        return this.nome_alimento;
    }
}
