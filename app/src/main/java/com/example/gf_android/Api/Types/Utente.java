package com.example.gf_android.Api.Types;

import androidx.annotation.NonNull;

import java.util.List;

public class Utente {

    public int id_utente;
    public String username;
    public String email;
    public int obiettivo_kcal;
    public int id_inventario;

    @NonNull
    public String toString() {
        return username;
    }

    public boolean isIn(List<Utente> lu) {
        for (Utente u : lu) {
            if (u.id_utente == this.id_utente)
                return true;
        }
        return false;
    }

    public Utente getMyself(List<Utente> lu) {
        for (Utente u : lu) {
            if (u.id_utente == this.id_utente)
                return u;
        }
        return null;
    }
}
