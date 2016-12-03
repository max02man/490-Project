package edu.louisville.cischef.showrecipe;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.louisville.cischef.R;

/**
 * Created by Max02man on 12/2/2016.
 */

public class showrecipe extends Fragment {

    public showrecipe(){}
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_recipe, container, false);
        return view;
    }
}
