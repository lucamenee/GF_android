package com.example.gf_android.Api.Types;

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

    public int expiresIn() {
        return (int) ((data_scadenza.getTime() - new Date().getTime()) / 1000 / 60 / 60 / 24);
    }

}
