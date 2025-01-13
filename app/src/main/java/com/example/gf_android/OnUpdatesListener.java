package com.example.gf_android;

// interface to use when you update the db's data and you want to update the data showed
// checkout MiainActivity.java onUpdate() method for implementing this listener
// checkout FoodPopUp.java listener attribute for using the listener

import com.example.gf_android.Api.Types.Alimento;

import java.util.List;

public interface OnUpdatesListener {
    void onUpdate();
    void onUpdate(List<Alimento> updatedInventory);
}