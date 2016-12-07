package edu.louisville.cischef.favorite;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.louisville.cischef.R;

/**
 * Created by Max02man on 12/6/2016.
 */

public class FavoriteFragment extends Fragment {
    public FavoriteFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

}
