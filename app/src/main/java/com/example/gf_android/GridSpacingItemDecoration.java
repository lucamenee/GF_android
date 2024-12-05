package com.example.gf_android;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
    {
        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // sinistra
            outRect.right = (column + 1) * spacing / spanCount; // destra

            if (position < spanCount) { // prima riga
                outRect.top = spacing; // sopra
            }
            outRect.bottom = spacing; // sotto
        } else {
            outRect.left = column * spacing / spanCount; // sinistra
            outRect.right = spacing - (column + 1) * spacing / spanCount; // destra
            if (position >= spanCount) {
                outRect.top = spacing; // sopra
            }
        }
    }
}
