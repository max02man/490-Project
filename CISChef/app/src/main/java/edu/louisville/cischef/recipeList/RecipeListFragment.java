package edu.louisville.cischef.recipeList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import edu.louisville.cischef.R;

/**
 * Created by Max02man on 11/24/2016.
 */

public class RecipeListFragment extends Fragment {
    FirebaseDatabase firebaseDatabase =FirebaseDatabase.getInstance();
    DatabaseReference list =firebaseDatabase.getReference().child("recipe");

    public RecipeListFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_recipe_list, container, false);
        return view;
    }
}
