package edu.louisville.cischef.recipeList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.louisville.cischef.R;

/**
 * Created by Max02man on 11/24/2016.
 */

public class RecipeListFragment extends Fragment {
    public RecipeListFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }
}
