package com.example.gf_android.Api.Types;

import android.util.Pair;

import java.util.List;

public class Ricetta {
    public int id_ricetta;
    public String nome_ricetta;

    public class Ingrediente {
        public int nome_alimento;
        public int grammi;
    }

    public List<Ingrediente> ingredienti;
}
