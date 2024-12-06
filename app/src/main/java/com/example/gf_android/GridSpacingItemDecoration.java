package com.example.gf_android;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private final int spanCount;    // Numero di colonne
    private final int spacing;     // Spaziatura calcolata dinamicamente
    private final boolean includeEdge;

    public GridSpacingItemDecoration(int spanCount, int screenWidth, boolean includeEdge) {
        this.spanCount = spanCount;
        this.includeEdge = includeEdge;
        // Calcola la spaziatura dinamica come proporzione della larghezza dello schermo
        this.spacing = (int) (screenWidth * 0.02); // Esempio: 2% della larghezza dello schermo
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // Posizione dell'elemento
        int column = position % spanCount; // Colonna dell'elemento

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount;
            outRect.right = (column + 1) * spacing / spanCount;

            if (position < spanCount) { // Spaziatura in alto per le righe superiori
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // Spaziatura in basso
        } else {
            outRect.left = column * spacing / spanCount;
            outRect.right = spacing - (column + 1) * spacing / spanCount;

            if (position >= spanCount) {
                outRect.top = spacing; // Spaziatura in alto
            }
        }
    }
}